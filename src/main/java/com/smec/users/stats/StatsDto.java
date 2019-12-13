package com.smec.users.stats;

import java.util.Date;

public class StatsDto {
    private String type;
    private int year;
    private int month;
    private int day;
    private long count;



    public StatsDto() {
    }

    public String getType() {
        return type;
    }

    public void setType(String name) {
        this.type = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}