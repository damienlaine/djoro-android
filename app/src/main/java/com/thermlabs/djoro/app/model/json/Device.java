package com.thermlabs.djoro.app.model.json;

import com.google.api.client.util.Key;

public class Device extends IdentifiedResource<Integer> {

    @Key
    private DeviceStatus status;

    @Key
    private DeviceSchedule schedule;

    @Key
    private MoneySaved moneySaved;

    public DeviceStatus getStatus() {
        return status;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    public DeviceSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(DeviceSchedule schedule) {
        this.schedule = schedule;
    }

    public MoneySaved getMoneySaved() {
        return moneySaved;
    }

    public void setMoneySaved(MoneySaved moneySaved) {
        this.moneySaved = moneySaved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Device device = (Device) o;

        if (moneySaved != null ? !moneySaved.equals(device.moneySaved) : device.moneySaved != null)
            return false;
        if (schedule != null ? !schedule.equals(device.schedule) : device.schedule != null)
            return false;
        if (status != null ? !status.equals(device.status) : device.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (schedule != null ? schedule.hashCode() : 0);
        result = 31 * result + (moneySaved != null ? moneySaved.hashCode() : 0);
        return result;
    }
}
