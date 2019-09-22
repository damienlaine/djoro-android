package com.thermlabs.djoro.app.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle();
    }

    protected void setTitle(){
        getActivity().setTitle(getTitleResourceId());
    }

    public abstract int getTitleResourceId();
}
