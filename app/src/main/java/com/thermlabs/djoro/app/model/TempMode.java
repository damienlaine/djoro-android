package com.thermlabs.djoro.app.model;


import com.google.api.client.util.Key;
import com.thermlabs.djoro.app.model.site.TempControlMode;

public class TempMode {

    @Key
    private TempControlMode mode;

    @Key
    private float targetTemp;

    public TempMode() {
    }

    public TempMode(TempControlMode mode, float targetTemp) {
        this.mode = mode;
        this.targetTemp = targetTemp;
    }

    public TempControlMode getMode() {
        return mode;
    }

    public void setMode(TempControlMode mode) {
        this.mode = mode;
    }

    public float getTargetTemp() {
        return targetTemp;
    }

    public void setTargetTemp(float targetTemp) {
        this.targetTemp = targetTemp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TempMode tempMode = (TempMode) o;

        if (Float.compare(tempMode.targetTemp, targetTemp) != 0) return false;
        if (mode != tempMode.mode) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mode != null ? mode.hashCode() : 0;
        result = 31 * result + (targetTemp != +0.0f ? Float.floatToIntBits(targetTemp) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TempMode{" +
                "mode=" + mode +
                ", targetTemp=" + targetTemp +
                '}';
    }
}
