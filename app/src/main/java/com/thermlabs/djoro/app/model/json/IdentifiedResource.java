package com.thermlabs.djoro.app.model.json;


import com.google.api.client.util.Key;

public class IdentifiedResource<T> {

    @Key
    private T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdentifiedResource that = (IdentifiedResource) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
