package com.mehehe.model.gson;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Place {
    Long uid;
    double lat;
    double lng;
    String name;
    Integer spot;
    Integer number;
    Integer bikes;
    Integer bike_racks;
    Integer free_racks;
    Integer place_type;
    Integer rack_locks;
    String bike_numbers;
//    Map<Integer, Integer> bike_types;

    public List<Integer> getBikeNumbers() {
        if (bike_numbers.isEmpty()) {
            return Collections.emptyList();
        }

        return Optional.ofNullable(bike_numbers)
                       .map(s -> s.split(","))
                       .map(Arrays::asList)
                       .orElse(Collections.emptyList())
                       .stream()
                       .map(Integer::parseInt)
                       .collect(Collectors.toList());
    }

    public Long getUid() {
        return uid;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getName() {
        return name;
    }

    public Integer getSpot() {
        return spot;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getBikes() {
        return bikes;
    }

    public Integer getBikeRacks() {
        return bike_racks;
    }

    public Integer getFreeRacks() {
        return free_racks;
    }

    public Integer getPlaceType() {
        return place_type;
    }

    public Integer getRackLocks() {
        return rack_locks;
    }

//    public Map<Integer, Integer> getBikeTypes() {
//        return bike_types;
//    }
}
