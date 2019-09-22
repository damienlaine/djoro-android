package com.thermlabs.djoro.app.model.json;

import com.google.api.client.util.Key;

import java.util.List;

public class Site extends IdentifiedResource<Integer> {

    @Key
    private List<Device> devices;

    public List<Device> getDevices() {
        return devices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Site site = (Site) o;

        if (devices != null ? !devices.equals(site.devices) : site.devices != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (devices != null ? devices.hashCode() : 0);
        return result;
    }
}
