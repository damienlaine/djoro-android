package com.thermlabs.djoro.app.model.json;

import com.google.api.client.util.Key;

public abstract class BaseResult<T> {
    @Key
    private int status;

    @Key
    private T payload;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseResult that = (BaseResult) o;

        if (status != that.status) return false;
        if (payload != null ? !payload.equals(that.payload) : that.payload != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = status;
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        return result;
    }
}
