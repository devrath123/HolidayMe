package com.holidayme.common;

/**
 * Created by santosh.patar on 25-08-2015.
 */
public enum SortByColumn {
    StartFromPrice(0), TripAdvisorRating(2), StarRating(3),Landmark(4), Popularity(10),Price,popularity;
    //StartFromPrice(0), TripAdvisorRating(2), StarRating(3),Landmark(3), Popularity(10), DurationInHourString(10);

    private int columVal;

    SortByColumn() {
    }

    SortByColumn(int numVal) {
        this.columVal = numVal;
    }
    public int getColumnVal() {
        return columVal;
    }
}
