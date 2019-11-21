package com.mehehe.dto;

import java.time.LocalDateTime;

public class MapStationDTO {
    private double lat;
    private double lng;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String stationName;

    public MapStationDTO() {
    }

    public MapStationDTO(double lat, double lng, LocalDateTime startDateTime, LocalDateTime endDateTime, String stationName) {
        this.lat = lat;
        this.lng = lng;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.stationName = stationName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getLabel() {
        return String.format("Na stacji od %s do %s", startDateTime, endDateTime);
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
