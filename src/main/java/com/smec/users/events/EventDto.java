package com.smec.users.events;

import java.util.Date;

public class EventDto {
    private int id;
    private String type;
    private Date time;
    private int accountId;

    public EventDto(String type, int accountId) {
        this.type = type;
        this.accountId = accountId;
    }

    public EventDto() {
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

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}