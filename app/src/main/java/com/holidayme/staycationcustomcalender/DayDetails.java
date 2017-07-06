package com.holidayme.staycationcustomcalender;

/**
 * Created by arshad.shaikh on 2/24/2017.
 */

public class DayDetails {

    private  String date,dayOfWeek,allocationDate,month,year;
    double price,slashPrice;
    int nightsCount,availabilityStatus,allocations;
    private boolean isSelect;

    public DayDetails(String date, String dayOfWeek,String month,String year,String allocationDate,double price, double slashPrice,
                      int nightsCount,int availabilityStatus,int allocations ,boolean isSelect) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.month = month;
        this.year = year;
        this.allocationDate = allocationDate;
        this.price = price;
        this.slashPrice = slashPrice;
        this.nightsCount = nightsCount;
        this.availabilityStatus = availabilityStatus;
        this.allocations = allocations;
        this.isSelect = isSelect;
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

    public int getAllocations() {
        return allocations;
    }

    public void setAllocations(int allocations) {
        this.allocations = allocations;
    }

    public int getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(int availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getAllocationDate() {
        return allocationDate;
    }

    public void setAllocationDate(String allocationDate) {
        this.allocationDate = allocationDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSlashPrice() {
        return slashPrice;
    }

    public void setSlashPrice(double slashPrice) {
        this.slashPrice = slashPrice;
    }

    public int getNightsCount() {
        return nightsCount;
    }

    public void setNightsCount(int nightsCount) {
        this.nightsCount = nightsCount;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
