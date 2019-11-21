package com.mehehe.dto;

import com.mehehe.model.BikeHistory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class FreeBikeDTO {
    private int id;
    private long availableFromMinutes;

    public FreeBikeDTO(int stationNumber, int id, Stream<BikeHistory> bikeHistoryStream) {
        this.id = id;
        long availableFrom = calculateAvailableFrom(stationNumber, bikeHistoryStream);
        this.availableFromMinutes = availableFrom == 0L ? 1L : availableFrom;
    }

    public FreeBikeDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAvailableFromMinutes() {
        return availableFromMinutes;
    }

    public void setAvailableFromMinutes(long availableFromMinutes) {
        this.availableFromMinutes = availableFromMinutes;
    }

    private static long calculateAvailableFrom(int stationNumber, Stream<BikeHistory> bikeHistoryStream) {
        LocalDateTime now = LocalDateTime.now();
        List<BikeHistory> bikeHistoryList = bikeHistoryStream
                .sorted(Comparator.comparing(BikeHistory::getCreationDate).reversed())
                .collect(toList());

        for (BikeHistory bikeHistory : bikeHistoryList) {
            if (bikeHistory.getStationNumber() != stationNumber) {
                return bikeHistory.getCreationDate().until(now, ChronoUnit.MINUTES);
            }
        }

        return bikeHistoryList.get(bikeHistoryList.size() - 1).getCreationDate().until(now, ChronoUnit.MINUTES);
    }
}
