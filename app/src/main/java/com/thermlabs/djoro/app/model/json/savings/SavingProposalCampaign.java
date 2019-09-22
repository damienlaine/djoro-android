package com.thermlabs.djoro.app.model.json.savings;

import com.google.api.client.util.Key;

import java.util.Currency;

public class SavingProposalCampaign extends SavingProposal {

    @Key
    private float amount;

    @Key
    private String createdOn;

    @Key
    private String expiresOn;

    @Key
    private String title;

    @Key
    private String content;

    // TODO internationalisation, this might change if we have customers outside EURO Zone.
    private Currency currency = Currency.getInstance("EUR");

    public SavingProposalCampaign(){}

    public SavingProposalCampaign(int priority, SavingProposalStatus status, SavingProposalType type, float amount, String createdOn, String expiresOn, String title, String content) {
        super(priority, status, type);
        this.amount = amount;
        this.createdOn = createdOn;
        this.expiresOn = expiresOn;
        this.title = title;
        this.content = content;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(String expiresOn) {
        this.expiresOn = expiresOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SavingProposalCampaign that = (SavingProposalCampaign) o;

        if (Float.compare(that.amount, amount) != 0) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null)
            return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null)
            return false;
        if (expiresOn != null ? !expiresOn.equals(that.expiresOn) : that.expiresOn != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (amount != +0.0f ? Float.floatToIntBits(amount) : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (expiresOn != null ? expiresOn.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SavingProposalCampaign{" +
                "amount=" + amount +
                ", createdOn='" + createdOn + '\'' +
                ", expiresOn='" + expiresOn + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", currency=" + currency +
                '}';
    }
}
