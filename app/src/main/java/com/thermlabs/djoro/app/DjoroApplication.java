package com.thermlabs.djoro.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.octo.android.robospice.JacksonGoogleHttpClientSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.thermlabs.djoro.app.controllers.SiteController;


public class DjoroApplication extends Application {
    private static Application s_instance;

    /**
     * Controller which centralizes information about temperature management.
     */
    private SiteController tempCtrl;

    /**
     * The Manager of communication with server through REST protocol
     */
    private SpiceManager spiceManager;

    public DjoroApplication(){
        s_instance = this;
        spiceManager = new SpiceManager(JacksonGoogleHttpClientSpiceService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        tempCtrl = new SiteController(preferences, spiceManager);

        spiceManager.start(this);
    }



    @Override
    public void onTerminate() {
        super.onTerminate();
        spiceManager.shouldStop();
    }

    public static Context getContext(){
        return s_instance;
    }

    public static String getResourceString(int resId){
        return getContext().getString(resId);
    }

    public SiteController getTempController() {
        return tempCtrl;
    }
}