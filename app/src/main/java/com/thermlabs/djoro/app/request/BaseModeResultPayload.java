package com.thermlabs.djoro.app.request;

import com.google.api.client.util.Key;
import com.thermlabs.djoro.app.model.TempMode;

public class BaseModeResultPayload {

    @Key
    private TempMode currentMode;

    @Key
    private float currentMeasuredTemp;

    @Key
    private TempMode nextMode;

    @Key
    private String nextModeScheduleDate;

    public TempMode getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(TempMode currentMode) {
        this.currentMode = currentMode;
    }

    public float getCurrentMeasuredTemp() {
        return currentMeasuredTemp;
    }

    public void setCurrentMeasuredTemp(float currentMeasuredTemp) {
        this.currentMeasuredTemp = currentMeasuredTemp;
    }

    public TempMode getNextMode() {
        return nextMode;
    }

    public void setNextMode(TempMode nextMode) {
        this.nextMode = nextMode;
    }

    public String getNextModeScheduleDate() {
        return nextModeScheduleDate;
    }

    public void setNextModeScheduleDate(String nextModeScheduleDate) {
        this.nextModeScheduleDate = nextModeScheduleDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseModeResultPayload that = (BaseModeResultPayload) o;

        if (Float.compare(that.currentMeasuredTemp, currentMeasuredTemp) != 0) return false;
        if (currentMode != null ? !currentMode.equals(that.currentMode) : that.currentMode != null)
            return false;
        if (nextMode != null ? !nextMode.equals(that.nextMode) : that.nextMode != null)
            return false;
        if (nextModeScheduleDate != null ? !nextModeScheduleDate.equals(that.nextModeScheduleDate) : that.nextModeScheduleDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = currentMode != null ? currentMode.hashCode() : 0;
        result = 31 * result + (currentMeasuredTemp != +0.0f ? Float.floatToIntBits(currentMeasuredTemp) : 0);
        result = 31 * result + (nextMode != null ? nextMode.hashCode() : 0);
        result = 31 * result + (nextModeScheduleDate != null ? nextModeScheduleDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BaseModeResultPayload{" +
                "currentMode=" + currentMode +
                ", currentMeasuredTemp=" + currentMeasuredTemp +
                ", nextMode=" + nextMode +
                ", nextModeScheduleDate='" + nextModeScheduleDate + '\'' +
                '}';
    }
}
