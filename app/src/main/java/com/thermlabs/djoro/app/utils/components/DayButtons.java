package com.thermlabs.djoro.app.utils.components;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ToggleButton;
import com.thermlabs.djoro.app.R;
import com.thermlabs.djoro.tools.DjoroDate;
import java.text.DateFormatSymbols;

public class DayButtons {

    private final int mColorLit;
    private final int mColorDim;
    private final Typeface mRobotoNormal;
    private final Typeface mRobotoBold;
    private final ViewGroup[] dayButtonParents;
    private final ToggleButton[] dayButtons;

    public DayButtons(Context context, LayoutInflater inflater, LinearLayout buttonsLayout) {

        Resources res = context.getResources();
        mColorLit = res.getColor(R.color.clock_white);
        mColorDim = res.getColor(R.color.clock_gray);
        mRobotoBold = Typeface.create("sans-serif-condensed", Typeface.BOLD);
        mRobotoNormal = Typeface.create("sans-serif-condensed", Typeface.NORMAL);
        dayButtonParents = new ViewGroup[7];
        dayButtons = new ToggleButton[7];

        initButtons(inflater, buttonsLayout);

    }

    private void initButtons(LayoutInflater inflater, LinearLayout buttonsLayout){
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] mShortWeekDayStrings = dfs.getShortWeekdays();
        String[] mLongWeekDayStrings = dfs.getWeekdays();

        // Build button for each day.
        for (int i = 0; i < 7; i++) {
            final ViewGroup viewgroup = (ViewGroup) inflater.inflate(R.layout.day_button, buttonsLayout, false);
            final ToggleButton button = (ToggleButton) viewgroup.getChildAt(0);
            final int dayToShowIndex = DjoroDate.DAY_ORDER[i];
            button.setText(mShortWeekDayStrings[dayToShowIndex]);
            button.setTextOn(mShortWeekDayStrings[dayToShowIndex]);
            button.setTextOff(mShortWeekDayStrings[dayToShowIndex]);
            button.setContentDescription(mLongWeekDayStrings[dayToShowIndex]);
            buttonsLayout.addView(viewgroup);
            dayButtons[i] = button;
            dayButtonParents[i] = viewgroup;
        }

        buttonsLayout.setVisibility(View.VISIBLE);
    }

    public void toggle(int dayIndex){
        dayButtons[dayIndex].toggle();
    }

    public boolean isChecked(int dayIndex) {
        return dayButtons[dayIndex].isChecked();
    }

    public void setOnClickListener(View.OnClickListener listener, int dayIndex){
        dayButtonParents[dayIndex].setOnClickListener(listener);
    }

    public void turnOffDayOfWeek(int dayIndex) {
        dayButtons[dayIndex].setChecked(false);
        dayButtons[dayIndex].setTextColor(mColorDim);
        dayButtons[dayIndex].setTypeface(mRobotoNormal);
    }

    public void turnOnDayOfWeek(int dayIndex) {
        dayButtons[dayIndex].setChecked(true);
        dayButtons[dayIndex].setTextColor(mColorLit);
        dayButtons[dayIndex].setTypeface(mRobotoBold);
    }

}
