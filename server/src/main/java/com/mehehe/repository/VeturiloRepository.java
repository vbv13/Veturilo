package com.mehehe.repository;

import com.mehehe.dao.BikeHistoryDAO;
import com.mehehe.dao.StationHistoryDAO;
import com.mehehe.model.BikeEvent;
import com.mehehe.model.BikeEventType;
import com.mehehe.model.BikeHistory;
import com.mehehe.model.Station;
import com.mehehe.model.StationEvent;
import com.mehehe.model.StationHistory;
import javafx.util.Pair;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VeturiloRepository {
    public static final Map<Integer, String> STATION_NR_TO_NAME = new HashMap<>();
    public static final Map<Integer, Pair<Double, Double>> STATION_NR_TO_LAT_LNG = new HashMap<>();
    private List<Station> list = new ArrayList<>();

    private final StationHistoryDAO stationHistoryDAO;
    private final BikeHistoryDAO bikeHistoryDAO;

    public VeturiloRepository(StationHistoryDAO stationHistoryDAO, BikeHistoryDAO bikeHistoryDAO) {
        this.stationHistoryDAO = stationHistoryDAO;
        this.bikeHistoryDAO = bikeHistoryDAO;
    }

    public List<Station> findAll() {
        return list;
    }

    public Optional<Station> findOne(int id) {
        return stationHistoryDAO
                .findByNumber(id)
                .stream()
                .findFirst()
                .map(StationHistory::getStation);
    }

    public Stream<StationHistory> findOneStationHistory(int id) {
        Map<LocalDateTime, List<BikeHistory>> byCreationDate = bikeHistoryDAO
                .findByStationNumber(id)
                .stream()
                .collect(Collectors.groupingBy(BikeHistory::getCreationDate));
        List<StationHistory> stationHistoryList = stationHistoryDAO.findByNumber(id);
        Consumer<StationHistory> x = stationHistory -> {
            List<Integer> bikeNumbersAtTime = byCreationDate
                    .getOrDefault(stationHistory.getCreationDate(), new ArrayList<>())
                    .stream()
                    .map(BikeHistory::getBikeNumber)
                    .collect(Collectors.toList());

            stationHistory.getStation().bikeNumbers.addAll(bikeNumbersAtTime);
        };
        return stationHistoryList.stream();
    }

    public Stream<BikeHistory> findOneBikeHistory(int id) {
        List<BikeEvent> events = new ArrayList<>();
        List<BikeHistory> byNumber = bikeHistoryDAO.findByNumber(id);
        for (int i = 1; i < byNumber.size(); i++) {
            BikeHistory previous = byNumber.get(i - 1);
            BikeHistory current = byNumber.get(i);
            if (previous.getStationNumber() != current.getStationNumber()) {
                long secondsBetween = Duration.between(previous.getCreationDate(), current.getCreationDate()).getSeconds();
                if (30 < secondsBetween && secondsBetween < 90) {
                    events.add(new BikeEvent(current.getBikeNumber(), current.getCreationDate(), BikeEventType.STATION_CHANGED, previous.getStationNumber(), current.getStationNumber()));
                } else {
                    events.add(new BikeEvent(current.getBikeNumber(), current.getCreationDate(), BikeEventType.APPEARED, null, current.getStationNumber()));
                    events.add(new BikeEvent(previous.getBikeNumber(), previous.getCreationDate(), BikeEventType.DISAPPEARED, previous.getStationNumber(), null));
                }
            }
        }
        return byNumber.stream();
    }

    public void save(List<Station> list) {
        list.sort(Comparator.comparing(x -> x.number));
        LocalDateTime now = LocalDateTime.now();
        STATION_NR_TO_NAME.clear();
        STATION_NR_TO_NAME.putAll(list.stream().collect(Collectors.toMap(x -> x.number, x -> x.name)));
        STATION_NR_TO_LAT_LNG.clear();
        STATION_NR_TO_LAT_LNG.putAll(list.stream().collect(Collectors.toMap(x -> x.number, x -> new Pair<>(x.lat, x.lng))));
        List<Station> diffIgnoreBikeList = diffIgnoreBikeList(list);
        List<Station> diff = diff(list, now);
        System.out.println(LocalDateTime.now() + " diff sizes " +
                (diff != null ? diff.size() : -1)
                + " " +
                (diffIgnoreBikeList != null ? diffIgnoreBikeList.size() : -1));
        this.list = list;
        list
                .stream()
                .collect(Collectors.groupingBy(station -> station.number))
                .forEach((key, value) -> {
                    Station station = value.iterator().next();
                    List<StationHistory> historyList = new ArrayList<>();
                    historyList.add(new StationHistory(station, now));
                    station.bikeNumbers.forEach(bikeNumber -> {
                        List<BikeHistory> bikeHistoryList = new ArrayList<>();
                        bikeHistoryList.add(new BikeHistory(bikeNumber, station.number, now));
                        bikeHistoryDAO.insert(bikeHistoryList);
                    });
                    stationHistoryDAO.insert(historyList);
                });
    }

    private List<Station> diffIgnoreBikeList(List<Station> list) {
        if (this.list.size() != list.size()) {
            System.out.println("Different number of stations " + this.list.size() + " " + list.size());
            return null;
        } else {
            List<Station> result = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Station prev = this.list.get(i);
                Station curr = list.get(i);
                if (!prev.equalsIgnoreBikeList(curr)) {
                    result.add(curr);
                }
            }
            return result;
        }
    }

    private List<Station> diff(List<Station> list, LocalDateTime now) {
        if (this.list.size() != list.size()) {
            System.out.println("Different number of stations " + this.list.size() + " " + list.size());
            return null;
        } else {
            List<Station> result = new ArrayList<>();
            List<BikeEvent> bikeEvents = new ArrayList<>();
            List<StationEvent> stationEvents = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Station prev = this.list.get(i);
                Station curr = list.get(i);
                if (!prev.equals(curr)) {
                    result.add(curr);
                    List<Integer> bikeNumbersPrev = prev.bikeNumbers;
                    List<Integer> bikeNumbersCurr = curr.bikeNumbers;
                    bikeNumbersPrev.stream().filter(x -> !bikeNumbersCurr.contains(x)).forEach(bikeNumber -> bikeEvents.add(new BikeEvent(bikeNumber, now, BikeEventType.DISAPPEARED, prev.number, null)));
                    bikeNumbersCurr.stream().filter(x -> !bikeNumbersPrev.contains(x)).forEach(bikeNumber -> bikeEvents.add(new BikeEvent(bikeNumber, now, BikeEventType.APPEARED, null, curr.number)));
                    if (bikeNumbersPrev.size() != bikeNumbersCurr.size()) {
                        stationEvents.add(new StationEvent(curr.number, now, bikeNumbersPrev.size(), bikeNumbersCurr.size()));
                    }
                }
            }
            Map<Integer, List<BikeEvent>> collect = bikeEvents.stream().collect(Collectors.groupingBy(x -> x.bikeNumber));
            Map<BikeEventType, List<BikeEvent>> byType = bikeEvents.stream().collect(Collectors.groupingBy(x -> x.type));
            System.out.println(stationEvents);
            System.out.println(bikeEvents);
            return result;
        }
    }
}
