package com.thermlabs.djoro.app.fragment;


import android.os.Bundle;


import com.github.machinarius.preferencefragment.PreferenceFragment;
import com.thermlabs.djoro.app.R;

public class SettingsFragment extends PreferenceFragment {

    public static int KEY_PREF_SERVER_URL = R.string.pref_key_server_url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }


}
