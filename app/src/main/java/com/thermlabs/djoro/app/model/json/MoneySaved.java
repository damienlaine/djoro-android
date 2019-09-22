package com.thermlabs.djoro.app.model.json;

import com.google.api.client.util.Key;

public class MoneySaved {

    @Key
    private String last_calc_date;

    @Key
    private float amount;

    @Key
    private String from;

    public String getLast_calc_date() {
        return last_calc_date;
    }

    public void setLast_calc_date(String last_calc_date) {
        this.last_calc_date = last_calc_date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoneySaved that = (MoneySaved) o;

        if (Float.compare(that.amount, amount) != 0) return false;
        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        if (last_calc_date != null ? !last_calc_date.equals(that.last_calc_date) : that.last_calc_date != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = last_calc_date != null ? last_calc_date.hashCode() : 0;
        result = 31 * result + (amount != +0.0f ? Float.floatToIntBits(amount) : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        return result;
    }
}
