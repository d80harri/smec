package com.smec.users.base;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static final long DAY_IN_MILLS = 1000*60*60*24;

    private Calendar date;

    public DateUtils(Date date) {
        this.date = Calendar.getInstance();
        this.date.setTime(date);
    }

    public int year() {
        return date.get(Calendar.YEAR);
    }
    public int month() {
        return date.get(Calendar.MONTH + 1);
    }
    public int day() {
        return date.get(Calendar.DAY_OF_MONTH);
    }

    public static Date today() {
        return new Date();
    }

    public static Date today(int days) {
        return new Date(System.currentTimeMillis() + days * DAY_IN_MILLS);
    }

    public static Date tomorrow() {
        return new Date(System.currentTimeMillis() + DAY_IN_MILLS);
    }

    public static Date yesterday() {
        return new Date(System.currentTimeMillis() - DAY_IN_MILLS);
    }

    public static Date create(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }
}