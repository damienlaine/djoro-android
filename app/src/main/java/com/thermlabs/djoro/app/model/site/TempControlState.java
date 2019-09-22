package com.thermlabs.djoro.app.model.site;

import com.thermlabs.djoro.app.model.TempMode;

public class TempControlState {
    private float currentMeasuredTemp;
    private TempMode currentMode;

    public TempControlState(float currentMeasuredTemp, TempMode currentMode) {
        this.currentMeasuredTemp = currentMeasuredTemp;
        this.currentMode = currentMode;
    }

    public float getCurrentMeasuredTemp() {
        return currentMeasuredTemp;
    }

    public void setCurrentMeasuredTemp(float currentMeasuredTemp) {
        this.currentMeasuredTemp = currentMeasuredTemp;
    }

    public TempMode getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(TempMode currentMode) {
        this.currentMode = currentMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TempControlState that = (TempControlState) o;

        return Float.compare(that.currentMeasuredTemp, currentMeasuredTemp) == 0 && currentMode == that.currentMode;

    }

    @Override
    public int hashCode() {
        int result = (currentMeasuredTemp != +0.0f ? Float.floatToIntBits(currentMeasuredTemp) : 0);
        result = 31 * result + (currentMode != null ? currentMode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TempControlState{" +
                "currentMeasuredTemp=" + currentMeasuredTemp +
                ", currentMode=" + currentMode +
                '}';
    }
}
