package com.thermlabs.djoro.app.model.site;

public class SiteState {
    private TempControlState tempState;
    private float savings;

    public TempControlState getTempState() {
        return tempState;
    }

    public void setTempState(TempControlState tempState) {
        this.tempState = tempState;
    }

    public float getSavings() {
        return savings;
    }

    public void setSavings(float savings) {
        this.savings = savings;
    }
}
