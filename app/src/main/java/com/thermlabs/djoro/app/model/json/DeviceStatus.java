package com.thermlabs.djoro.app.model.json;

import com.google.api.client.util.Key;

public class DeviceStatus {
    @Key
    private float setPoint;

    @Key
    private float measuredTemp;

    @Key
    private int boilerOn;

    @Key
    private String measuredOn;

    @Key
    private String createdOn;

    public float getSetPoint() {
        return setPoint;
    }

    public void setSetPoint(float setPoint) {
        this.setPoint = setPoint;
    }

    public float getMeasuredTemp() {
        return measuredTemp;
    }

    public void setMeasuredTemp(float measuredTemp) {
        this.measuredTemp = measuredTemp;
    }

    public int getBoilerOn() {
        return boilerOn;
    }

    public void setBoilerOn(int boilerOn) {
        this.boilerOn = boilerOn;
    }

    public String getMeasuredOn() {
        return measuredOn;
    }

    public void setMeasuredOn(String measuredOn) {
        this.measuredOn = measuredOn;
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

        DeviceStatus that = (DeviceStatus) o;

        if (boilerOn != that.boilerOn) return false;
        if (Float.compare(that.measuredTemp, measuredTemp) != 0) return false;
        if (Float.compare(that.setPoint, setPoint) != 0) return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null)
            return false;
        if (measuredOn != null ? !measuredOn.equals(that.measuredOn) : that.measuredOn != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (setPoint != +0.0f ? Float.floatToIntBits(setPoint) : 0);
        result = 31 * result + (measuredTemp != +0.0f ? Float.floatToIntBits(measuredTemp) : 0);
        result = 31 * result + boilerOn;
        result = 31 * result + (measuredOn != null ? measuredOn.hashCode() : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        return result;
    }
}
