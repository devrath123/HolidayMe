package com.holidayme.response;

import com.holidayme.data.Hotels;

import java.util.ArrayList;

/**
 * Created by santosh.patar on 21-08-2015.
 */
public class SearchHotelByTextResponse  {
    private ErrorResponseBase Error;

    private ArrayList<Hotels> HotelList;

    public ArrayList<Hotels> getHotelList() {
        return HotelList;
    }

    public void setHotelList(ArrayList<Hotels> hotelList) {
        HotelList = hotelList;
    }

    public ErrorResponseBase getError() {
        return Error;
    }

    public void setError(ErrorResponseBase error) {
        Error = error;
    }
}
