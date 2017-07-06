package com.holidayme.staycationcustomcalender;

/**
 * Created by arshad.shaikh on 2/24/2017.
 */

public class MonthsData {

    private  String month,year, date;
    private boolean isSelected;
    private int dayIndex;

    public MonthsData(String month, String year, boolean isSelected) {

        this.month=month;
        this.year=year;
        this.isSelected=isSelected;
    }

    public MonthsData(String date,int dayIndex) {

        this.date=date;
        this.dayIndex = dayIndex;
    }

    public int getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
