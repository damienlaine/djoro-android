package com.thermlabs.djoro.app.request;

import com.google.api.client.util.Key;

public class UpdateThermpointResultPayload {

    @Key
    private float tempAway;

    @Key
    private float boostTempNight;

    @Key
    private float boostTempDay;

    @Key
    private float tempNight;

    @Key
    private float tempDay;

    @Key
    private String createdOn;


    public float getTempAway() {
        return tempAway;
    }

    public void setTempAway(float tempAway) {
        this.tempAway = tempAway;
    }

    public float getBoostTempNight() {
        return boostTempNight;
    }

    public void setBoostTempNight(float boostTempNight) {
        this.boostTempNight = boostTempNight;
    }

    public float getBoostTempDay() {
        return boostTempDay;
    }

    public void setBoostTempDay(float boostTempDay) {
        this.boostTempDay = boostTempDay;
    }

    public float getTempNight() {
        return tempNight;
    }

    public void setTempNight(float tempNight) {
        this.tempNight = tempNight;
    }

    public float getTempDay() {
        return tempDay;
    }

    public void setTempDay(float tempDay) {
        this.tempDay = tempDay;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateThermpointResultPayload that = (UpdateThermpointResultPayload) o;

        if (Float.compare(that.boostTempDay, boostTempDay) != 0) return false;
        if (Float.compare(that.boostTempNight, boostTempNight) != 0) return false;
        if (Float.compare(that.tempAway, tempAway) != 0) return false;
        if (Float.compare(that.tempDay, tempDay) != 0) return false;
        if (Float.compare(that.tempNight, tempNight) != 0) return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (tempAway != +0.0f ? Float.floatToIntBits(tempAway) : 0);
        result = 31 * result + (boostTempNight != +0.0f ? Float.floatToIntBits(boostTempNight) : 0);
        result = 31 * result + (boostTempDay != +0.0f ? Float.floatToIntBits(boostTempDay) : 0);
        result = 31 * result + (tempNight != +0.0f ? Float.floatToIntBits(tempNight) : 0);
        result = 31 * result + (tempDay != +0.0f ? Float.floatToIntBits(tempDay) : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        return result;
    }
}
