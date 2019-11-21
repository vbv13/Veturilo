package com.mehehe.model;

import java.time.LocalDateTime;

public class StationEvent {
    public int stationNumber;
    LocalDateTime localDateTime;
    public StationEventType type;
    public int previousBikeNumber;
    public int currentBikeNumber;

    public StationEvent(int stationNumber, LocalDateTime localDateTime, int previousBikeNumber, int currentBikeNumber) {
        this.stationNumber = stationNumber;
        this.localDateTime = localDateTime;
        this.type = previousBikeNumber > currentBikeNumber ? StationEventType.BIKE_NUMBER_DECREASED : StationEventType.BIKE_NUMBER_INCREASED;
        this.previousBikeNumber = previousBikeNumber;
        this.currentBikeNumber = currentBikeNumber;
    }

    int getBikeNumberDifference() {
        return currentBikeNumber - previousBikeNumber;
    }

    @Override
    public String toString() {
        return "StationEvent{" +
            "stationNumber= " + stationNumber +
            ", localDateTime= " + localDateTime +
            ", type= " + type +
            ", bikeNumberDifference= " + getBikeNumberDifference() + ": " + previousBikeNumber + " ==> " + currentBikeNumber +
            "}\n";
    }
}
