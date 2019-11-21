package com.mehehe.model;

import java.time.LocalDateTime;

public class StationHistory {
    private Station station;
    private LocalDateTime creationDate;

    public StationHistory(Station station, LocalDateTime now) {
        this.station = station;
        this.creationDate = now;
    }

    public Station getStation() {
        return station;
    }

    void setStation(Station station) {
        this.station = station;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return creationDate + " " + station.toStringHistory();
    }
}
