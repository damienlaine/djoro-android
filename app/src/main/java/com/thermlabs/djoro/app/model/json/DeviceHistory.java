package com.thermlabs.djoro.app.model.json;

import com.google.api.client.util.Key;

public class DeviceHistory {

    @Key
    private int light;

    @Key
    private float setPointTemp;

    @Key
    private float measuredTemp;

    @Key
    private String createdOn;


    public int getLight() {
        return light;
    }

    public float getSetPointTemp() {
        return setPointTemp;
    }

    public float getMeasuredTemp() {
        return measuredTemp;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public void setSetPointTemp(float setPointTemp) {
        this.setPointTemp = setPointTemp;
    }

    public void setMeasuredTemp(float measuredTemp) {
        this.measuredTemp = measuredTemp;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DeviceHistory that = (DeviceHistory) o;

        if (light != that.light) return false;
        if (setPointTemp != that.setPointTemp) return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = light;
        result = 31 * result + (setPointTemp != +0.0f ? Float.floatToIntBits(setPointTemp) : 0);
        result = 31 * result + (measuredTemp != +0.0f ? Float.floatToIntBits(measuredTemp) : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        return result;
    }
}
