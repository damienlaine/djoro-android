package com.thermlabs.djoro.app.model.json.savings;

import com.google.api.client.util.Key;
import com.thermlabs.djoro.app.model.json.IdentifiedResource;

import java.util.Currency;
import java.util.Date;

public class SavingProposal extends IdentifiedResource<Integer> {

    @Key
    private int priority;

    @Key
    private SavingProposalStatus status;

    @Key
    private SavingProposalType type;

    public SavingProposal(){}

    public SavingProposal(int priority, SavingProposalStatus status, SavingProposalType type) {
        this.priority = priority;
        this.status = status;
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public SavingProposalStatus getStatus() {
        return status;
    }

    public void setStatus(SavingProposalStatus status) {
        this.status = status;
    }

    public SavingProposalType getType() {
        return type;
    }

    public void setType(SavingProposalType type) {
        this.type = type;
    }




}
