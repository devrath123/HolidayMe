package com.holidayme.data;

/**
 * Created by devrath.rathee on 04-07-2017.
 */

public class FlightsBean {

    String depatureCitySmall,departureCity,departureTime,departureDate,arrivalCitySmall,arrivalCity,arrivalTime,arrivalDate;

    public FlightsBean(String depatureCitySmall, String departureCity, String departureTime, String departureDate, String arrivalCitySmall, String arrivalCity, String arrivalTime, String arrivalDate) {
        this.depatureCitySmall = depatureCitySmall;
        this.departureCity = departureCity;
        this.departureTime = departureTime;
        this.departureDate = departureDate;
        this.arrivalCitySmall = arrivalCitySmall;
        this.arrivalCity = arrivalCity;
        this.arrivalTime = arrivalTime;
        this.arrivalDate = arrivalDate;
    }

    public String getDepatureCitySmall() {
        return depatureCitySmall;
    }

    public void setDepatureCitySmall(String depatureCitySmall) {
        this.depatureCitySmall = depatureCitySmall;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalCitySmall() {
        return arrivalCitySmall;
    }

    public void setArrivalCitySmall(String arrivalCitySmall) {
        this.arrivalCitySmall = arrivalCitySmall;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}
