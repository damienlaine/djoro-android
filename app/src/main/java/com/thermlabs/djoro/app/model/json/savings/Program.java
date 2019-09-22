package com.thermlabs.djoro.app.model.json.savings;

import com.google.api.client.util.Key;
import com.thermlabs.djoro.app.model.site.TempControlMode;
import com.thermlabs.djoro.tools.DateToolbox;

import java.util.Date;

public class Program {

    @Key
    String startDate;

    @Key
    String endDate;

    @Key
    TempControlMode temperature;

    @Key
    String type = "PERMANENT";

    public Program() {
    }

    public Program(Date startDate, Date endDate, TempControlMode temperature) {
        this.startDate = DateToolbox.format(startDate);
        this.endDate = DateToolbox.format(endDate);
        this.temperature = temperature;
    }

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

    public TempControlMode getTemperature() {
        return temperature;
    }

    public void setTemperature(TempControlMode temperature) {
        this.temperature = temperature;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Program program = (Program) o;

        if (endDate != null ? !endDate.equals(program.endDate) : program.endDate != null)
            return false;
        if (temperature != program.temperature) return false;
        if (startDate != null ? !startDate.equals(program.startDate) : program.startDate != null)
            return false;
        if (type != null ? !type.equals(program.type) : program.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = startDate != null ? startDate.hashCode() : 0;
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (temperature != null ? temperature.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Program{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", temperature=" + temperature +
                ", type='" + type + '\'' +
                '}';
    }
}
