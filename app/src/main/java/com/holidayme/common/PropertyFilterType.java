package com.holidayme.common;

/**
 * Created by santosh.patar on 25-08-2015.
 */
public enum PropertyFilterType {
    TopArea(0), StarRating(1),Themes(2),Amenities(3),HotelChain(4),TripAdvisorRating(5),WifiAvailable(6),MinMaxPrice(7),IsPayAtHotel(8),NoFilter(-1);

    private int numVal;

    PropertyFilterType(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }





}
