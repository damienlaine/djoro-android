package com.thermlabs.djoro.app;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.thermlabs.djoro.app.dialog.SetAwayDurationDialogFragment;
import com.thermlabs.djoro.app.dialog.SetTemperatureDialogFragment;
import com.thermlabs.djoro.app.fragment.HomeCardsFragment;
import com.thermlabs.djoro.app.fragment.SavingCardsFragment;
import com.thermlabs.djoro.app.fragment.SetAwayPeriodFragment;
import com.thermlabs.djoro.app.fragment.TypicalWeekFragment;
import com.thermlabs.djoro.app.fragment.SettingsFragment;
import com.thermlabs.djoro.app.model.TempMode;
import com.thermlabs.djoro.app.model.site.TempControlState;
import com.thermlabs.djoro.app.utils.SimpleSectionedListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity
        implements SetAwayDurationDialogFragment.SetAwayDialogListener,
        SetTemperatureDialogFragment.TemperatureDialogListener

{

    private ListView mDrawerList;
    private DrawerLayout mDrawer;
    private CustomActionBarDrawerToggle mDrawerToggle;
    private int mCurrentTitle=R.string.app_name;
    private int mSelectedFragment;
    private Fragment mBaseFragment;
    SimpleSectionedListAdapter mSectionedAdapter;

    public static final int CASE_STD = 0;
    public static final int CASE_HOME = 0;
    public static final int CASE_SAVE_MORE = 1;
    public static final int CASE_TYPICAL_WEEK_WIZARD = 2;
    public static final int CASE_SET_AWAY_PERIOD = 3;
    public static final int CASE_SETTINGS = 4;


    //Used in savedInstanceState
    private static String BUNDLE_SELECTEDFRAGMENT = "BDL_SELFRG";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_SELECTEDFRAGMENT, mSelectedFragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        _initMenu();
        mDrawerToggle = new CustomActionBarDrawerToggle(this, mDrawer);
        mDrawer.setDrawerListener(mDrawerToggle);

        mBaseFragment = new HomeCardsFragment();
        openFragment(mBaseFragment);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        //boolean drawerOpen = mDrawer.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
		 * The action bar home/up should open or close the drawer.
		 * ActionBarDrawerToggle will take care of this.
		 */
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {

            case R.id.menu_settings:
                openFragment(new SettingsFragment());
                return true;
            //About

            case R.id.menu_about:
                //Utils.showAbout(this);
                return true;

            default:
                break;
        }


        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }

    public Fragment selectFragment(int position) {
        Fragment baseFragment = null;

        switch (position) {

            case CASE_HOME:
                baseFragment = new HomeCardsFragment();
                break;
            case CASE_SAVE_MORE:
                baseFragment = new SavingCardsFragment();
                break;
            case CASE_SET_AWAY_PERIOD:
                baseFragment = new SetAwayPeriodFragment();
                break;
            case CASE_SETTINGS:
                baseFragment = new SettingsFragment();
                break;
            case CASE_TYPICAL_WEEK_WIZARD:
                baseFragment = new TypicalWeekFragment();
        }

        return baseFragment;
    }

    public void openFragment(Fragment newFragment) {
        if (newFragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, newFragment);
            if (!(newFragment instanceof HomeCardsFragment)) {
                fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.commit();
        }
    }





    private void _initMenu() {
        mDrawerList = (ListView) findViewById(R.id.drawer);

        String[] options = {
                getString(R.string.title_fragment_home),
                getString(R.string.title_fragment_save_more),
                getString(R.string.title_fragment_typical_week)
        };

        if (mDrawerList != null) {
            ArrayAdapter<String> mAdapter =new ArrayAdapter<String>(this,
                    R.layout.demo_activity_menuitem, options);

            List<SimpleSectionedListAdapter.Section> sections =
                    new ArrayList<SimpleSectionedListAdapter.Section>();

            sections.add(new SimpleSectionedListAdapter.Section(CASE_STD,"Card base features"));


            SimpleSectionedListAdapter.Section[] dummy = new SimpleSectionedListAdapter.Section[sections.size()];
            mSectionedAdapter = new SimpleSectionedListAdapter(this,R.layout.demo_activity_menusection,mAdapter);
            mSectionedAdapter.setSections(sections.toArray(dummy));

            mDrawerList.setAdapter(mSectionedAdapter);
            mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        }

    }

    private class CustomActionBarDrawerToggle extends ActionBarDrawerToggle {

        public CustomActionBarDrawerToggle(Activity mActivity, DrawerLayout mDrawerLayout) {
            super(
                    mActivity,
                    mDrawerLayout,
                    R.drawable.ic_navigation_drawer,
                    R.string.app_name,
                    mCurrentTitle);
        }

        @Override
        public void onDrawerClosed(View view) {
            getActionBar().setTitle(getString(mCurrentTitle));
            invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            getActionBar().setTitle(getString(R.string.app_name));
            invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            // Highlight the selected item, update the title, and close the drawer
            // update selected item and title, then close the drawer
            position = mSectionedAdapter.sectionedPositionToPosition(position);

            mDrawerList.setItemChecked(position, true);
            mBaseFragment = selectFragment(position);
            mSelectedFragment = position;

            mDrawer.closeDrawer(mDrawerList);
            if (mBaseFragment != null)
                openFragment(mBaseFragment);
        }
    }

    @Override
    public void onDialogPositiveClick() {

    }

    @Override
    public void onDialogNegativeClick() {


    }

    @Override
    public void onTempDialogPositiveClick(TempMode newMode, float newMeasuredTemperature) {
        TempControlState tempState = ((DjoroApplication) getApplication()).getTempController().getCurrentState();
        //Switch mode
        tempState.setCurrentMode(newMode);
        Log.d("SetTemperature", "NEW TEMP MODE: " + newMode.toString());
        ((HomeCardsFragment)mBaseFragment).switchToMode(newMode);
        //Update measured temperature
        tempState.setCurrentMeasuredTemp(newMeasuredTemperature);
        ((HomeCardsFragment)mBaseFragment).updateCurrentMeasuredTemp(newMeasuredTemperature);
    }

    @Override
    public void onTempDialogNegativeClick() {

    }
}
