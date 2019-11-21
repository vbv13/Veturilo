package com.mehehe.resources;

import com.codahale.metrics.annotation.Timed;
import com.mehehe.VeturiloService;
import com.mehehe.dto.MapStationDTO;
import com.mehehe.model.BikeHistory;
import javafx.util.Pair;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

import static com.mehehe.repository.VeturiloRepository.STATION_NR_TO_LAT_LNG;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Path("/bike")
@Produces(MediaType.APPLICATION_JSON)
public class BikeResource {

    private final VeturiloService veturiloService;

    public BikeResource(VeturiloService veturiloService) {
        this.veturiloService = veturiloService;
    }

    @GET
    @Timed
    @Path("/{id}/points")
    public List<MapStationDTO> getPoints(@PathParam("id") Integer id) {
        List<BikeHistory> bikeHistoryList = veturiloService.getBikeHistory(id)
                .sorted(Comparator.comparing(BikeHistory::getCreationDate))
                .collect(toList());

        Map<Integer, List<BikeHistory>> collect1 = bikeHistoryList.stream()
                .collect(groupingBy(BikeHistory::getStationNumber));

        if (bikeHistoryList.isEmpty()) {
            return Collections.emptyList();
        } else if (bikeHistoryList.size() == 1) {
            BikeHistory next = bikeHistoryList.iterator().next();
            return singletonList(mapMapStation(next, next));
        }

        List<MapStationDTO> result = new ArrayList<>();
        BikeHistory begin = bikeHistoryList.get(0);
        for (int i = 1; i < bikeHistoryList.size(); i++) {
            BikeHistory previous = bikeHistoryList.get(i - 1);
            BikeHistory current = bikeHistoryList.get(i);

            if (i == bikeHistoryList.size() - 1) {
                result.add(mapMapStation(begin, current));
            } else if (begin.getStationNumber() != current.getStationNumber()) {
                result.add(mapMapStation(begin, previous));
                begin = current;
            }
        }

        return result;
    }

    private MapStationDTO mapMapStation(BikeHistory start, BikeHistory end) {
        Pair<Double, Double> latLong = STATION_NR_TO_LAT_LNG.get(start.getStationNumber());
        return new MapStationDTO(latLong.getKey(), latLong.getValue(),
                start.getCreationDate(), end.getCreationDate(), start.getStationName());
    }
}
