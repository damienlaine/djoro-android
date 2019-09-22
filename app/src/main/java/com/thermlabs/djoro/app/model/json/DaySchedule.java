package com.thermlabs.djoro.app.model.json;

import com.google.api.client.util.Key;

import java.util.ArrayList;
import java.util.List;

public class DaySchedule implements Cloneable {

    @Key
    private int day;

    @Key
    private List<HourSchedule> list;

    @Key
    private boolean offDay;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public List<HourSchedule> getList() {
        return list;
    }

    public void setList(List<HourSchedule> list) {
        this.list = list;
    }

    public boolean isOffDay() {
        return offDay;
    }

    public void setOffDay(boolean offDay) {
        this.offDay = offDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || ((Object)this).getClass() != o.getClass()) return false;

        DaySchedule that = (DaySchedule) o;

        if (day != that.day) return false;
        if (offDay != that.offDay) return false;
        if (list != null ? !list.equals(that.list) : that.list != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = day;
        result = 31 * result + (list != null ? list.hashCode() : 0);
        result = 31 * result + (offDay ? 1 : 0);
        return result;
    }
}
