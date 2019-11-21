package com.mehehe.resources;

import com.codahale.metrics.annotation.Timed;
import com.mehehe.VeturiloService;
import com.mehehe.dto.FreeBikeDTO;
import com.mehehe.dto.GraphPointDTO;
import com.mehehe.dto.StationDTO;
import com.mehehe.model.BikeHistory;
import com.mehehe.model.Station;
import com.mehehe.model.StationHistory;
import javafx.util.Pair;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Path("/station")
@Produces(MediaType.APPLICATION_JSON)
public class StationResource {

    private final VeturiloService veturiloService;

    public StationResource(VeturiloService veturiloService) {
        this.veturiloService = veturiloService;
    }

    @GET
    @Timed
    @Path("/selected/{ids}")
    public List<StationDTO> getSelectedStations(@PathParam("ids") String ids) {
        String[] split = ids.split(",");
        return Stream.of(split)
                .mapToInt(Integer::parseInt)
                .mapToObj(veturiloService::getStation)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::mapStation)
                .collect(Collectors.toList());
    }

    @GET
    @Timed
    @Path("/{id}/graph-data")
    public List<GraphPointDTO> getGraphData(@PathParam("id") Integer id) {
        return veturiloService.getStationHistory(id)
                .map(this::mapGraphPoint)
                .sorted(Comparator.comparing(GraphPointDTO::getX))
                .collect(Collectors.toList());
    }

    @GET
    @Timed
    @Path("/{id}/free-bikes")
    public List<FreeBikeDTO> getBikesOnStation(@PathParam("id") Integer id) {
        List<Integer> bikeNumbers = veturiloService
                .getStations()
                .stream()
                .filter(station -> station.number == id)
                .findFirst()
                .map(station -> station.bikeNumbers)
                .orElse(Collections.emptyList());

        List<Pair<Integer, Stream<BikeHistory>>> bikeNumberToHistory = bikeNumbers.stream()
                .map(bikeNumber -> new Pair<>(bikeNumber, veturiloService.getBikeHistory(bikeNumber)))
                .collect(toList());

        return bikeNumberToHistory.stream()
                .map(bikeHistory -> mapFreeBike(id, bikeHistory))
                .collect(toList());
    }

    private FreeBikeDTO mapFreeBike(int id, Pair<Integer, Stream<BikeHistory>> bikeHistory) {
        return new FreeBikeDTO(id, bikeHistory.getKey(), bikeHistory.getValue());
    }

    private StationDTO mapStation(Station station) {
        return new StationDTO(station.number, station.name, station.bikes);
    }

    private GraphPointDTO mapGraphPoint(StationHistory stationHistory) {
        return new GraphPointDTO(stationHistory.getCreationDate(), stationHistory.getStation().bikes);
    }
}
