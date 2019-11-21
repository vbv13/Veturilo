package com.mehehe.model;

import com.mehehe.model.gson.Place;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.mehehe.repository.VeturiloRepository.STATION_NR_TO_NAME;

public class Station {
    public final Long uid;
    public final Double lat;
    public final Double lng;
    public final String name;
    public final Integer spot;
    public final int number;
    public final int bikes;
    public final int bikeRacks;
    public final int freeRacks;
    public final Integer placeType;
    public final Integer rackLocks;
    public final List<Integer> bikeNumbers;
    public final Map<Integer, Integer> bikeTypes;

    private Station(Long uid, Double lat, Double lng, String name, Integer spot, Integer number, Integer bikes, Integer bikeRacks, Integer freeRacks, Integer placeType, Integer rackLocks,
                    List<Integer> bikeNumbers, Map<Integer, Integer> bikeTypes) {
        this.uid = uid;
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.spot = spot;
        this.number = number;
        this.bikes = bikes;
        this.bikeRacks = bikeRacks;
        this.freeRacks = freeRacks;
        this.placeType = placeType;
        this.rackLocks = rackLocks;
        this.bikeNumbers = bikeNumbers;
        this.bikeTypes = bikeTypes;
    }

    public Station(Place place) {
        this(place.getUid(), place.getLat(), place.getLng(), place.getName(), place.getSpot(), place.getNumber(), place.getBikes(), place.getBikeRacks(), place.getFreeRacks(),
             place.getPlaceType(), place.getRackLocks(), place.getBikeNumbers(), Collections.emptyMap());
//             place.getBikeTypes());
    }

    public Station(int number, int bikes, int bikeRacks, int freeRacks) {
        this(null, null, null, STATION_NR_TO_NAME.getOrDefault(number, "NOT_FOUND"), null, number, bikes, bikeRacks, freeRacks, null, null, new ArrayList<>(), null);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Station)) {
            return false;
        }
        Station station = (Station) o;
        return uid.equals(station.uid) &&
            Double.compare(station.lat, lat) == 0 &&
            Double.compare(station.lng, lng) == 0 &&
            spot.equals(station.spot) &&
            number == station.number &&
            bikes == station.bikes &&
            bikeRacks == station.bikeRacks &&
            freeRacks == station.freeRacks &&
            placeType.equals(station.placeType) &&
            rackLocks.equals(station.rackLocks) &&
            Objects.equals(name, station.name) &&
            Objects.equals(bikeNumbers, station.bikeNumbers) &&
            Objects.equals(bikeTypes, station.bikeTypes);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uid, lat, lng, name, spot, number, bikes, bikeRacks, freeRacks, placeType, rackLocks, bikeNumbers, bikeTypes);
    }

    @Override
    public String toString() {
        return "Station{" +
            "uid=" + uid +
            ", lat=" + lat +
            ", lng=" + lng +
            ", name='" + name + '\'' +
            ", spot=" + spot +
            ", number=" + number +
            ", bikes=" + bikes +
            ", bikeRacks=" + bikeRacks +
            ", freeRacks=" + freeRacks +
            ", placeType=" + placeType +
            ", rackLocks=" + rackLocks +
            ", bikeNumbers=" + bikeNumbers +
            ", bikeTypes=" + bikeTypes +
            '}';
    }

    public String toStringHistory() {
        return "Station{" +
            "name='" + name + '\'' +
            ", number=" + number +
            ", bikes=" + bikes +
            ", bikeRacks=" + bikeRacks +
            ", freeRacks=" + freeRacks +
            ", bikeNumbers=" + bikeNumbers +
            '}';
    }

    public boolean equalsIgnoreBikeList(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Station)) {
            return false;
        }
        Station station = (Station) o;
        return uid.equals(station.uid) &&
            Double.compare(station.lat, lat) == 0 &&
            Double.compare(station.lng, lng) == 0 &&
            spot.equals(station.spot) &&
            number == station.number &&
            bikeRacks == station.bikeRacks &&
            placeType.equals(station.placeType) &&
            rackLocks.equals(station.rackLocks) &&
            Objects.equals(name, station.name);
    }
}
