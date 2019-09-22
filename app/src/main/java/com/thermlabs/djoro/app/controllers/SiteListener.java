package com.thermlabs.djoro.app.controllers;

public interface SiteListener<RESULT> {

    public void onSuccess(RESULT result);

    public void onFailure(Exception exception);
}
