package com.thermlabs.djoro.tools;

public class DjoroTime {
    private int hour;
    private int minute;

    public DjoroTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public DjoroTime(String time) {
        String[] parts = time.split(":");
        hour = Integer.parseInt(parts[0]);
        minute = Integer.parseInt(parts[1]);
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String toString(){
        return String.format("%02d:%02d", hour, minute);
    }
}
