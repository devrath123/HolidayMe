package com.holidayme.hotellisting_mvp;

import android.view.View;

import java.util.HashMap;

/**
 * Created by arshad.shaikh on 11/21/2016.
 */

public interface IHotelListingClick {

    void onHotelLisItemClick(long hotelId, HashMap<String, Object> cleverTapHashMap);
}
