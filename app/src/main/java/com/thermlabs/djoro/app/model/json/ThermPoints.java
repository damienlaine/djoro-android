package com.thermlabs.djoro.app.model.json;

import com.google.api.client.util.Key;

public class ThermPoints {

    @Key
    private float thermAway;

    @Key
    private float thermDay;

    @Key
    private float thermNight;

    public float getThermAway() {
        return thermAway;
    }

    public float getThermDay() {
        return thermDay;
    }

    public float getThermNight() {
        return thermNight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThermPoints that = (ThermPoints) o;

        if (Float.compare(that.thermAway, thermAway) != 0) return false;
        if (Float.compare(that.thermDay, thermDay) != 0) return false;
        if (Float.compare(that.thermNight, thermNight) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (thermAway != +0.0f ? Float.floatToIntBits(thermAway) : 0);
        result = 31 * result + (thermDay != +0.0f ? Float.floatToIntBits(thermDay) : 0);
        result = 31 * result + (thermNight != +0.0f ? Float.floatToIntBits(thermNight) : 0);
        return result;
    }
}
