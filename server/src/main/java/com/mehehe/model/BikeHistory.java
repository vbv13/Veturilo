package com.mehehe.model;

import java.time.LocalDateTime;

import static com.mehehe.repository.VeturiloRepository.STATION_NR_TO_NAME;

public class BikeHistory {
    private int bikeNumber;
    private int stationNumber;
    private LocalDateTime creationDate;
    private String stationName;

    public BikeHistory(int bikeNumber, int stationNumber, LocalDateTime now) {
        this.bikeNumber = bikeNumber;
        this.stationNumber = stationNumber;
        this.creationDate = now;
        this.stationName = STATION_NR_TO_NAME.getOrDefault(stationNumber, "NOT_FOUND");
    }

    public int getBikeNumber() {
        return bikeNumber;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getStationName() {
        return stationName;
    }

    @Override
    public String toString() {
        return "BikeHistory{" +
            "bikeNumber=" + bikeNumber +
            ", stationNumber=" + stationNumber +
            ", stationName= \"" + stationName +
            "\", creationDate=" + creationDate +
            '}';
    }
}
