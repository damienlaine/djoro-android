package com.thermlabs.djoro.app.model.site;

import android.os.Build;

import com.thermlabs.djoro.app.BuildConfig;
import com.thermlabs.djoro.app.model.json.DaySchedule;
import com.thermlabs.djoro.app.model.json.HourSchedule;
import com.thermlabs.djoro.app.model.json.JSONWeekSchedule;
import com.thermlabs.djoro.tools.DjoroDate;
import com.thermlabs.djoro.tools.DjoroTime;

import java.util.Arrays;
import java.util.List;

public class TypicalWeekSchedule {
    private boolean[] workingDays;
    private DjoroTime workingDayWakeUpTime;
    private DjoroTime nonWorkingDayWakeUpTime;
    private DjoroTime workingDayGoToBedTime;
    private DjoroTime nonWorkingDayGoToBedTime;
    private DjoroTime workingDayGotToWorkTime;
    private DjoroTime workingDayBackHomeTime;

    public TypicalWeekSchedule(){
    }

    public TypicalWeekSchedule(JSONWeekSchedule week) {
        int i = 0;
        workingDays = new boolean[7];
        for (DaySchedule day: week.getWeek()){
            List<HourSchedule> hourSchedules = day.getList();
            if (day.isOffDay()) {
                if (BuildConfig.DEBUG && hourSchedules.size() != 2) {
                    throw new AssertionError("Bad hour schedule size in JSON result: " + hourSchedules.size());
                }
                nonWorkingDayWakeUpTime = hourSchedules.get(0).getTime();
                nonWorkingDayGoToBedTime = hourSchedules.get(1).getTime();
            } else {
                if (BuildConfig.DEBUG && hourSchedules.size() != 4) {
                    throw new AssertionError("Bad hour schedule size in JSON result: " + hourSchedules.size());
                }
                workingDayWakeUpTime = hourSchedules.get(0).getTime();
                workingDayGotToWorkTime = hourSchedules.get(1).getTime();
                workingDayBackHomeTime = hourSchedules.get(2).getTime();
                workingDayGoToBedTime = hourSchedules.get(3).getTime();
            }
            workingDays[i++] = !day.isOffDay();
        }
    }

    public boolean[] getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(boolean[] workingDays) {
        this.workingDays = workingDays;
    }

    public boolean[] getNonWorkingDays(){
        boolean[] nonWorkingDays = new boolean[DjoroDate.DAY_ORDER.length];
        for (int day : DjoroDate.DAY_ORDER) {
            nonWorkingDays[day] = !workingDays[day];
        }
        return nonWorkingDays;
    }

    public boolean isWorkingDay(int dayIndex) {
        return workingDays[dayIndex];
    }

    public DjoroTime getWorkingDayWakeUpTime() {
        return workingDayWakeUpTime;
    }

    public void setWorkingDayWakeUpTime(DjoroTime workingDayWakeUpTime) {
        this.workingDayWakeUpTime = workingDayWakeUpTime;
    }

    public DjoroTime getNonWorkingDayWakeUpTime() {
        return nonWorkingDayWakeUpTime;
    }

    public void setNonWorkingDayWakeUpTime(DjoroTime nonWorkingDayWakeUpTime) {
        this.nonWorkingDayWakeUpTime = nonWorkingDayWakeUpTime;
    }

    public DjoroTime getWorkingDayGoToBedTime() {
        return workingDayGoToBedTime;
    }

    public void setWorkingDayGoToBedTime(DjoroTime workingDayGoToBedTime) {
        this.workingDayGoToBedTime = workingDayGoToBedTime;
    }

    public DjoroTime getNonWorkingDayGoToBedTime() {
        return nonWorkingDayGoToBedTime;
    }

    public void setNonWorkingDayGoToBedTime(DjoroTime nonWorkingDayGoToBedTime) {
        this.nonWorkingDayGoToBedTime = nonWorkingDayGoToBedTime;
    }

    public DjoroTime getWorkingDayGotToWorkTime() {
        return workingDayGotToWorkTime;
    }

    public void setWorkingDayGotToWorkTime(DjoroTime workingDayGotToWorkTime) {
        this.workingDayGotToWorkTime = workingDayGotToWorkTime;
    }

    public DjoroTime getWorkingDayBackHomeTime() {
        return workingDayBackHomeTime;
    }

    public void setWorkingDayBackHomeTime(DjoroTime workingDayBackHomeTime) {
        this.workingDayBackHomeTime = workingDayBackHomeTime;
    }

    @Override
    public String toString() {
        return "TypicalWeekSchedule{" +
                "workingDays=" + Arrays.toString(workingDays) +
                ", workingDayWakeUpTime=" + workingDayWakeUpTime +
                ", nonWorkingDayWakeUpTime=" + nonWorkingDayWakeUpTime +
                ", workingDayGoToBedTime=" + workingDayGoToBedTime +
                ", nonWorkingDayGoToBedTime=" + nonWorkingDayGoToBedTime +
                ", workingDayGotToWorkTime=" + workingDayGotToWorkTime +
                ", workingDayBackHomeTime=" + workingDayBackHomeTime +
                '}';
    }
}
