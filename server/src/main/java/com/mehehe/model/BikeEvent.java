package com.mehehe.model;

import java.time.LocalDateTime;

public class BikeEvent {
    public int bikeNumber;
    LocalDateTime localDateTime;
    public BikeEventType type;
    Integer relatedSourceStationNumber;
    Integer relatedDestinationStationNumber;

    public BikeEvent(int bikeNumber, LocalDateTime localDateTime, BikeEventType type, Integer relatedSourceStationNumber, Integer relatedDestinationStationNumber) {
        this.bikeNumber = bikeNumber;
        this.localDateTime = localDateTime;
        this.type = type;
        this.relatedSourceStationNumber = relatedSourceStationNumber;
        this.relatedDestinationStationNumber = relatedDestinationStationNumber;
    }

    @Override
    public String toString() {
        return "BikeEvent{" +
            "bikeNumber= " + bikeNumber +
            ", localDateTime= " + localDateTime +
            ", type= " + type +
            ", " + relatedSourceStationNumber + " ==> " + relatedDestinationStationNumber +
            "}\n";
    }
}
