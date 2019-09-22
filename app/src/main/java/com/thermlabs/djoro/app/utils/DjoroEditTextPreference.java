package com.thermlabs.djoro.app.utils;


import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

public class DjoroEditTextPreference extends EditTextPreference {
    public DjoroEditTextPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DjoroEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        setSummary(text);
    }
}
