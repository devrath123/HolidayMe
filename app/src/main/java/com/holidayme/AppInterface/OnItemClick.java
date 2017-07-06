package com.holidayme.AppInterface;

import com.holidayme.data.HotelAccommodation;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 07-03-2016.
 */
public interface OnItemClick {
    void scrollTo(int position);
    void doneClick(ArrayList<HotelAccommodation> mRoominfo);

}
