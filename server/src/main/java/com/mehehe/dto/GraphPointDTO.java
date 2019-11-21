package com.mehehe.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GraphPointDTO {
    private LocalDateTime x;
    private int y;

    public GraphPointDTO(LocalDateTime x, int y) {
        this.x = x;
        this.y = y;
    }

    public GraphPointDTO() {
    }

    public String getX() {
        return x.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public void setX(LocalDateTime x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
