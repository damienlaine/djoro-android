package com.thermlabs.djoro.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class DateToolbox {

    public static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static final String format(Date date) {
        DateFormat df = new SimpleDateFormat(ISO8601_DATE_FORMAT);
        return df.format(date);
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public static Date addHours(Date date, int hours) {
        Date newDate = new Date();
        //Add the amount of milliseconds that correspond to the number of hours
        newDate.setTime(date.getTime() + hours * 60 * 60 * 1000);
        return newDate;
    }
}
