package com.thermlabs.djoro.app.model.json;

import com.google.api.client.util.Key;
import com.thermlabs.djoro.app.model.site.TempControlMode;
import com.thermlabs.djoro.app.model.site.TypicalWeekSchedule;

import java.util.ArrayList;
import java.util.List;

public class JSONWeekSchedule {

    @Key
    private String startDate = "2010-01-01T00:00:00.000Z";

    @Key
    private String endDate = "2050-01-01T00:00:00.000Z";

    @Key
    private String type;

    @Key
    private List<DaySchedule> week;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DaySchedule> getWeek() {
        return week;
    }

    public void setWeek(List<DaySchedule> week) {
        this.week = week;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JSONWeekSchedule that = (JSONWeekSchedule) o;

        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null)
            return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (week != null ? !week.equals(that.week) : that.week != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = startDate != null ? startDate.hashCode() : 0;
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (week != null ? week.hashCode() : 0);
        return result;
    }

    private static DaySchedule getWorkingDaySchedule(TypicalWeekSchedule schedule) {
        // Working days
        DaySchedule workingDaySchedule = new DaySchedule();

        workingDaySchedule.setOffDay(false);

        List<HourSchedule> hourSchedules = new ArrayList<HourSchedule>();

        HourSchedule workingDayWakeUp = new HourSchedule();
        workingDayWakeUp.setTime(schedule.getWorkingDayWakeUpTime());
        workingDayWakeUp.setTemperature(TempControlMode.tempDay);
        hourSchedules.add(workingDayWakeUp);

        HourSchedule workingDayGoToWork = new HourSchedule();
        workingDayGoToWork.setTime(schedule.getWorkingDayGotToWorkTime());
        workingDayGoToWork.setTemperature(TempControlMode.tempAway);
        hourSchedules.add(workingDayGoToWork);

        HourSchedule workingDayBackHome = new HourSchedule();
        workingDayBackHome.setTime(schedule.getWorkingDayBackHomeTime());
        workingDayBackHome.setTemperature(TempControlMode.tempDay);
        hourSchedules.add(workingDayBackHome);

        HourSchedule workingDayGoToBed = new HourSchedule();
        workingDayGoToBed.setTime(schedule.getWorkingDayGoToBedTime());
        workingDayGoToBed.setTemperature(TempControlMode.tempNight);
        hourSchedules.add(workingDayGoToBed);

        workingDaySchedule.setList(hourSchedules);
        return workingDaySchedule;
    }

    private static DaySchedule getNonWorkingDaySchedule(TypicalWeekSchedule schedule) {
        // Working days
        DaySchedule nonWorkingDaySchedule = new DaySchedule();

        nonWorkingDaySchedule.setOffDay(true);

        List<HourSchedule> hourSchedules = new ArrayList<HourSchedule>();

        HourSchedule nonWorkingDayWakeUp = new HourSchedule();
        nonWorkingDayWakeUp.setTime(schedule.getNonWorkingDayWakeUpTime());
        nonWorkingDayWakeUp.setTemperature(TempControlMode.tempDay);
        hourSchedules.add(nonWorkingDayWakeUp);

        HourSchedule nonWorkingDayGoToBed = new HourSchedule();
        nonWorkingDayGoToBed.setTime(schedule.getWorkingDayGoToBedTime());
        nonWorkingDayGoToBed.setTemperature(TempControlMode.tempNight);
        hourSchedules.add(nonWorkingDayGoToBed);

        nonWorkingDaySchedule.setList(hourSchedules);
        return nonWorkingDaySchedule;
    }

    public static JSONWeekSchedule fromModel(TypicalWeekSchedule schedule) throws CloneNotSupportedException {
        DaySchedule workingDaySchedule = getWorkingDaySchedule(schedule);
        DaySchedule nonWorkingDaySchedule = getNonWorkingDaySchedule(schedule);

        JSONWeekSchedule jsonWeekSchedule = new JSONWeekSchedule();
        jsonWeekSchedule.type = "WEEK";
        List<DaySchedule> week = new ArrayList<DaySchedule>();

        for (int dayIndex = 0; dayIndex < 7; dayIndex++) {
            DaySchedule day;
            if (schedule.isWorkingDay(dayIndex)){
                 day = (DaySchedule)workingDaySchedule.clone();
            } else {
                day = (DaySchedule)nonWorkingDaySchedule.clone();
            }
            //Day index in JSON is between 1 and 7 and in the mobile app it's between 0 and 6
            day.setDay(dayIndex + 1);
            week.add(day);
        }

        jsonWeekSchedule.setWeek(week);

        return jsonWeekSchedule;
    }
}
