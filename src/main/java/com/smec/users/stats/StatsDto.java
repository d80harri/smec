package com.smec.users.stats;

import java.util.Date;

public class StatsDto {
    private int id;
    private String type;
    private Date time;

    public StatsDto(String type, Date time) {
        this.type = type;
        this.time = time;
    }

    public StatsDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String name) {
        this.type = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}