package com.thermlabs.djoro.app.model.json.savings;

import com.google.api.client.util.Value;

public enum SavingProposalStatus {

    @Value
    PROPOSED,

    @Value
    APPLIED,

    @Value
    DISMISSED,

    @Value
    EXPIRED
}
