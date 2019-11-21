package com.mehehe.dto;

public class StationDTO {
    private int id;
    private String name;
    private int freeBikes;

    public StationDTO(int id, String name, int freeBikes) {
        this.id = id;
        this.name = name;
        this.freeBikes = freeBikes;
    }

    public StationDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFreeBikes() {
        return freeBikes;
    }

    public void setFreeBikes(int freeBikes) {
        this.freeBikes = freeBikes;
    }
}
