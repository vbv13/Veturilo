package com.mehehe.resources;

import com.codahale.metrics.annotation.Timed;
import com.mehehe.VeturiloService;
import com.mehehe.model.BikeHistory;
import com.mehehe.model.Station;

import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Old functions, for debug purposes
 */
@Deprecated
@Path("/veturilo")
@Produces(MediaType.APPLICATION_JSON)
public class VeturiloResource {

    private final VeturiloService veturiloService;

    public VeturiloResource(VeturiloService veturiloService) {
        this.veturiloService = veturiloService;
    }

    @GET
    @Timed
    @Path("/")
    public String getJsonFromWeb() {
        List<Station> stationList = veturiloService.getStations();
        List<Integer> bikeNumbers = stationList.stream().flatMap(x -> x.bikeNumbers.stream()).distinct().sorted().collect(Collectors.toList());
        List<Integer> stationNumbers = stationList.stream().map(x -> x.number).distinct().sorted().collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        stationList.forEach(x -> printStation(sb, x));
        sb
                .append("\n\n\n")
                .append(bikeNumbers.size())
                .append(" bikeNumbers: ")
                .append(bikeNumbers.toString())
                .append("\n\n\n")
                .append(stationNumbers.size())
                .append(" stationNumbers: ")
                .append(stationNumbers.toString())
                .append("\n");
        return sb.toString();
    }

    @GET
    @Timed
    @Path("/{id}")
    public String getStation(@PathParam("id") Integer id) {
        return veturiloService
                .getStation(id)
                .map(x -> printStation(new StringBuilder(), x)
                        .toString())
                .orElse("NOT_FOUND");
    }

    @GET
    @Timed
    @Path("/{id}/history")
    public String getStationHistory(@PathParam("id") Integer id) {
        return veturiloService.getStationHistoryAsString(id);
    }

    @GET
    @Timed
    @Path("/name/{name}")
    public String getStationByName(@PathParam("name") String name) {
        return veturiloService
                .getStations()
                .stream()
                .filter(station -> station.name.toLowerCase().contains(name.toLowerCase()))
                .map(x -> printStation(new StringBuilder(), x).toString())
                .collect(Collectors.joining());
    }

    @GET
    @Timed
    @Path("/bike-history/{id}")
    public String getBikeHistory(@PathParam("id") Integer id) {
        return veturiloService.getBikeHistory(id).map(BikeHistory::toString).collect(Collectors.joining("\n"));
    }

    @GET
    @Timed
    @Path("/reload")
    public String reload() {
        veturiloService.reloadStations();
        List<Station> stationList = veturiloService.getStations();
        List<Integer> bikeNumbers = stationList.stream().flatMap(x -> x.bikeNumbers.stream()).distinct().sorted().collect(Collectors.toList());
        List<Integer> stationNumbers = stationList.stream().map(x -> x.number).distinct().sorted().collect(Collectors.toList());
        return bikeNumbers.size() + " " + stationNumbers.size();
    }

    private StringBuilder printStation(StringBuilder sb, Station x) {
        return sb
                .append(x.number)
                .append(": ")
                .append(x.bikeNumbers.size())
                .append("/")
                .append(x.bikeRacks)
                .append(" ")
                .append(x.name)
                .append(" ")
                .append(x.bikeNumbers)
                .append("\n");
    }
}
