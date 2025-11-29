package com.syedhisham41.cron_validator.DTO;

public class CronFields {

    private String seconds; 
    
    private String minutes; 
    
    private String hours; 
    
    private String daysOfMonth; 
    
    private String nearestWeekdays; 
    
    private String months; 
    
    private String daysOfWeek; 
    
    private String lastDayOfWeek; 
    
    private String NthDayOfWeek; 
    
    private String years; 
    
    public CronFields() { }

    public CronFields(String seconds, String minutes, String hours, String daysOfMonth, String nearestWeekdays,
            String months, String daysOfWeek, String lastDayOfWeek, String nthDayOfWeek, String years) {
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;
        this.daysOfMonth = daysOfMonth;
        this.nearestWeekdays = nearestWeekdays;
        this.months = months;
        this.daysOfWeek = daysOfWeek;
        this.lastDayOfWeek = lastDayOfWeek;
        NthDayOfWeek = nthDayOfWeek;
        this.years = years;
    }

    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getDaysOfMonth() {
        return daysOfMonth;
    }

    public void setDaysOfMonth(String daysOfMonth) {
        this.daysOfMonth = daysOfMonth;
    }

    public String getNearestWeekdays() {
        return nearestWeekdays;
    }

    public void setNearestWeekdays(String nearestWeekdays) {
        this.nearestWeekdays = nearestWeekdays;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public String getLastDayOfWeek() {
        return lastDayOfWeek;
    }

    public void setLastDayOfWeek(String lastDayOfWeek) {
        this.lastDayOfWeek = lastDayOfWeek;
    }

    public String getNthDayOfWeek() {
        return NthDayOfWeek;
    }

    public void setNthDayOfWeek(String nthDayOfWeek) {
        NthDayOfWeek = nthDayOfWeek;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

}
