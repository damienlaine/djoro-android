package com.thermlabs.djoro.app.model.json;

import com.google.api.client.util.Key;
import com.thermlabs.djoro.app.model.site.TempControlMode;
import com.thermlabs.djoro.tools.DjoroTime;

public class HourSchedule implements Cloneable {

    @Key
    private int hour;

    @Key
    private int minute;

    @Key
    private TempControlMode temperature;


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public TempControlMode getTemperature() {
        return temperature;
    }

    public void setTemperature(TempControlMode temperature) {
        this.temperature = temperature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || ((Object)this).getClass() != o.getClass()) return false;

        HourSchedule that = (HourSchedule) o;

        if (hour != that.hour) return false;
        if (minute != that.minute) return false;
        if (temperature != that.temperature) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = hour;
        result = 31 * result + minute;
        result = 31 * result + (temperature != null ? temperature.hashCode() : 0);
        return result;
    }

    public void setTime(DjoroTime time) {
        this.setHour(time.getHour());
        this.setMinute(time.getMinute());
    }

    public DjoroTime getTime() {
        return new DjoroTime(hour, minute);
    }
}
