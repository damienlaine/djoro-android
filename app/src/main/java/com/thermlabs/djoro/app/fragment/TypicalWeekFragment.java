package com.thermlabs.djoro.app.fragment;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.thermlabs.djoro.app.DjoroApplication;
import com.thermlabs.djoro.app.R;
import com.thermlabs.djoro.app.controllers.SiteListener;
import com.thermlabs.djoro.app.model.json.JSONWeekSchedule;
import com.thermlabs.djoro.app.model.json.WeekScheduleResult;
import com.thermlabs.djoro.app.model.site.SiteState;
import com.thermlabs.djoro.app.model.site.TypicalWeekSchedule;
import com.thermlabs.djoro.app.utils.components.DayButtons;
import com.thermlabs.djoro.tools.DjoroTime;

public class TypicalWeekFragment extends Fragment {

    private DayButtons workingDays;
    private DayButtons nonWorkingDays;

    private TextView workingDayWakeUpTime;
    private TextView workingDayGoToBedTime;
    private TextView workingDayGoToWorkTime;
    private TextView workingDayBackHomeTime;
    private TextView nonWorkingDayWakeUpTime;
    private TextView nonWorkingDayGoToBedTime;

    private View validateLayout;

    private TypicalWeekListener listener = new TypicalWeekListener();

    public TypicalWeekFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_typical_week, container, false);


        initWorkingDaysButtons(rootView, inflater);
        initNonWorkingDaysButtons(rootView, inflater);

        workingDayWakeUpTime = (TextView) rootView.findViewById(R.id.workingDaysWakeUpTime);
        workingDayWakeUpTime.setOnClickListener(new TimeSetListener());

        workingDayGoToBedTime = (TextView) rootView.findViewById(R.id.workingDaysGoToBedTime);
        workingDayGoToBedTime .setOnClickListener(new TimeSetListener());

        workingDayGoToWorkTime = (TextView) rootView.findViewById(R.id.workingDaysGoToWorkTime);
        workingDayGoToWorkTime.setOnClickListener(new TimeSetListener());

        workingDayBackHomeTime = (TextView) rootView.findViewById(R.id.workingDaysBackHomeTime);
        workingDayBackHomeTime.setOnClickListener(new TimeSetListener());

        nonWorkingDayWakeUpTime = (TextView) rootView.findViewById(R.id.nonWorkingDaysWakeUpTime);
        nonWorkingDayWakeUpTime.setOnClickListener(new TimeSetListener());

        nonWorkingDayGoToBedTime = (TextView) rootView.findViewById(R.id.nonWorkingDaysGoToBedTime);
        nonWorkingDayGoToBedTime .setOnClickListener(new TimeSetListener());



        validateLayout = rootView.findViewById(R.id.updateButtons);
        Button okButton = (Button) rootView.findViewById(R.id.typical_week_ok_button);
        Button cancelButton = (Button) rootView.findViewById(R.id.typical_week_cancel_button);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TypicalWeekSchedule week = new TypicalWeekSchedule();
                setTypicalWeekScheduleFromViews(week);
                ((DjoroApplication) getActivity().getApplication()).getTempController().updateTypicalWeekSchedule(week, listener);
                updateViews(week);
                validateLayout.setVisibility(View.INVISIBLE);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TypicalWeekSchedule week = ((DjoroApplication) getActivity().getApplication()).getTempController().getTypicalWeekSchedule();
                updateViews(week);
                validateLayout.setVisibility(View.INVISIBLE);
            }
        });

        refreshTypicalWeekSchedule();

        return rootView;
    }

    private void setTypicalWeekScheduleFromViews(TypicalWeekSchedule week) {

        boolean[] wDays = new boolean[7];
        for (int dayIndex = 0; dayIndex < 7;  dayIndex++) {
            wDays[dayIndex] = workingDays.isChecked(dayIndex);
        }
        week.setWorkingDays(wDays);

        week.setNonWorkingDayGoToBedTime(new DjoroTime(nonWorkingDayGoToBedTime.getText().toString()));
        week.setNonWorkingDayWakeUpTime(new DjoroTime(nonWorkingDayWakeUpTime.getText().toString()));
        week.setWorkingDayGoToBedTime(new DjoroTime(workingDayGoToBedTime.getText().toString()));
        week.setWorkingDayWakeUpTime(new DjoroTime(workingDayWakeUpTime.getText().toString()));
        week.setWorkingDayGotToWorkTime(new DjoroTime(workingDayGoToWorkTime.getText().toString()));
        week.setWorkingDayBackHomeTime(new DjoroTime(workingDayBackHomeTime.getText().toString()));
    }


    private void initNonWorkingDaysButtons(ViewGroup rootView, LayoutInflater inflater) {
        LinearLayout nonWorkingDaysLayout = (LinearLayout) rootView.findViewById(R.id.nonWorkingDays);
        nonWorkingDays = new DayButtons(getActivity(), inflater, nonWorkingDaysLayout);

        for (int i = 0; i < 7; i++) {
            final int buttonIndex;
            buttonIndex = i;

            nonWorkingDays.setOnClickListener(new DaysButtonListener(buttonIndex), buttonIndex);
            workingDays.setOnClickListener(new DaysButtonListener(buttonIndex), buttonIndex);
        }
    }

    private void initWorkingDaysButtons(ViewGroup rootView, LayoutInflater inflater) {
        LinearLayout workingDaysLayout = (LinearLayout) rootView.findViewById(R.id.workingDays);
        workingDays = new DayButtons(getActivity(), inflater, workingDaysLayout);

        for (int i = 0; i < 7; i++) {
            final int buttonIndex = i;

            workingDays.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    workingDays.toggle(buttonIndex);
                    final boolean checked = workingDays.isChecked(buttonIndex);

                    if (checked) {
                        workingDays.turnOnDayOfWeek(buttonIndex);
                    } else {
                        workingDays.turnOffDayOfWeek(buttonIndex);
                    }
                }
            }, buttonIndex);
        }
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.changevalue, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private class TimeSetListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            final TextView tv = (TextView) v;
            String[] parts = tv.getText().toString().split(":");
            int oldHour = Integer.parseInt(parts[0]);
            int oldMinutes = Integer.parseInt(parts[1]);

            TimePickerDialog pickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    tv.setText(new DjoroTime(hourOfDay, minute).toString());
                    tv.setTextColor(getActivity().getResources().getColor(R.color.button_color_red));
                    validateLayout.setVisibility(View.VISIBLE);
                }
            }, oldHour, oldMinutes, true );
            pickerDialog.show();
        }
    }


    private class DaysButtonListener implements View.OnClickListener {

        private int dayIndex;

        public DaysButtonListener(int dayIndex) {
            this.dayIndex = dayIndex;
        }

        @Override
        public void onClick(View v) {
            validateLayout.setVisibility(View.VISIBLE);
            nonWorkingDays.toggle(dayIndex);
            workingDays.toggle(dayIndex);
            final boolean checked = nonWorkingDays.isChecked(dayIndex);

            if (checked) {
                nonWorkingDays.turnOnDayOfWeek(dayIndex);
                workingDays.turnOffDayOfWeek(dayIndex);
            } else {
                nonWorkingDays.turnOffDayOfWeek(dayIndex);
                workingDays.turnOnDayOfWeek(dayIndex);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                refreshTypicalWeekSchedule();
                Toast.makeText(getActivity(), "Refreshed typical week schedule", Toast.LENGTH_SHORT).show();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateTimeView(TextView timeView, DjoroTime updatedTime) {
        timeView.setText(updatedTime.toString());
        timeView.setTextColor(Color.BLACK);
    }

    private void refreshTypicalWeekSchedule(){
        ((DjoroApplication) getActivity().getApplication()).getTempController().refreshTypicalWeekSchedule(listener);

    }


    public final class TypicalWeekListener implements SiteListener<WeekScheduleResult> {

        @Override
        public void onSuccess(WeekScheduleResult week) {
            Toast.makeText( getActivity(), "Typical week schedule: success", Toast.LENGTH_SHORT).show();
            updateViews(new TypicalWeekSchedule(week.getPayload()));
        }

        @Override
        public void onFailure(Exception exception) {
            Toast.makeText(getActivity(), "Typical week schedule: failure", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateViews(TypicalWeekSchedule week){

        for (int dayIndex = 0; dayIndex < 7; dayIndex++){
            if (week.getWorkingDays()[dayIndex]) {
                this.workingDays.turnOnDayOfWeek(dayIndex);
                nonWorkingDays.turnOffDayOfWeek(dayIndex);
            } else {
                this.workingDays.turnOffDayOfWeek(dayIndex);
                nonWorkingDays.turnOnDayOfWeek(dayIndex);
            }
        }

        updateTimeView(workingDayGoToWorkTime,week.getWorkingDayGotToWorkTime());
        updateTimeView(workingDayBackHomeTime,week.getWorkingDayBackHomeTime());
        updateTimeView(workingDayWakeUpTime,week.getWorkingDayWakeUpTime());
        updateTimeView(workingDayGoToBedTime,week.getWorkingDayGoToBedTime());
        updateTimeView(nonWorkingDayWakeUpTime,week.getNonWorkingDayWakeUpTime());
        updateTimeView(nonWorkingDayGoToBedTime,week.getNonWorkingDayGoToBedTime());
    }
}
