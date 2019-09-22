package com.thermlabs.djoro.app.request;

import com.google.api.client.util.Key;
import com.thermlabs.djoro.app.model.TempMode;

public class NewSavingResultPayload extends BaseModeResultPayload {
    @Key
    private String savingUid;

    public String getSavingUid() {
        return savingUid;
    }

    public void setSavingUid(String savingUid) {
        this.savingUid = savingUid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        NewSavingResultPayload that = (NewSavingResultPayload) o;

        if (savingUid != null ? !savingUid.equals(that.savingUid) : that.savingUid != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (savingUid != null ? savingUid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewSavingResultPayload{" +
                "savingUid='" + savingUid + '\'' +
                '}';
    }
}
