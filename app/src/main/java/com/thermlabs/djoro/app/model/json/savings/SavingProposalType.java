package com.thermlabs.djoro.app.model.json.savings;

import com.google.api.client.util.Value;

public enum SavingProposalType {

    @Value
    CAMPAIGN,

    @Value
    SCHEDULE,

    @Value
    CALENDAR,

    @Value
    COMFORT,

    @Value
    HOLIDAYS,

    @Value
    WEEKEND,

    @Value
    TEMPERATURE
}
