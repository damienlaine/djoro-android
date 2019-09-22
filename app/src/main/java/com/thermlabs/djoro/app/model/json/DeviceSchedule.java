package com.thermlabs.djoro.app.model.json;

import com.google.api.client.util.Key;

public class DeviceSchedule {

    @Key
    private Schedule now;

    public Schedule getNow() {
        return now;
    }

    public void setNow(Schedule now) {
        this.now = now;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceSchedule that = (DeviceSchedule) o;

        if (now != null ? !now.equals(that.now) : that.now != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return now != null ? now.hashCode() : 0;
    }
}
