package com.thermlabs.djoro.app.model.json;

import com.google.api.client.util.Key;

public class Schedule {

    @Key
    private float temperature;

    @Key
    private String thermPointName;

    @Key
    private int priority;

    @Key
    private String type;

    @Key
    private String startDate;

    @Key
    private String endDate;

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getThermPointName() {
        return thermPointName;
    }

    public void setThermPointName(String thermPointName) {
        this.thermPointName = thermPointName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        if (priority != schedule.priority) return false;
        if (Float.compare(schedule.temperature, temperature) != 0) return false;
        if (endDate != null ? !endDate.equals(schedule.endDate) : schedule.endDate != null)
            return false;
        if (startDate != null ? !startDate.equals(schedule.startDate) : schedule.startDate != null)
            return false;
        if (thermPointName != null ? !thermPointName.equals(schedule.thermPointName) : schedule.thermPointName != null)
            return false;
        if (type != null ? !type.equals(schedule.type) : schedule.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (temperature != +0.0f ? Float.floatToIntBits(temperature) : 0);
        result = 31 * result + (thermPointName != null ? thermPointName.hashCode() : 0);
        result = 31 * result + priority;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }
}
