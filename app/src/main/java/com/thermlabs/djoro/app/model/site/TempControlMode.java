package com.thermlabs.djoro.app.model.site;

import com.google.api.client.util.Value;

public enum TempControlMode {

    @Value
    tempNight,

    @Value
    tempAway,

    @Value
    boostTempDay,

    @Value
    boostTempNight,

    @Value
    tempDay
}
