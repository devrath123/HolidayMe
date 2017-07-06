package com.holidayme.hotellistinmap_mvp;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.android.volley.Response;
import com.holidayme.response.HotelDetailInfoResponse;
import com.holidayme.response.HotelRatesResponse;
import com.holidayme.response.TripAdviserDataResponse;

/**
 * Created by arshad.shaikh on 11/15/2016.
 */

public interface IHotelMapLandingPresenter {

    void getHotelRoomRate(String url, String request, Context context);
    Response.Listener<HotelRatesResponse> getHotelRoomRateSuccessListener(Context context);

    void getHotelDetails(String url, String request, Context context);
    Response.Listener<HotelDetailInfoResponse> getHotelDetailsSuccessListener(Context context);

    void getTripAdvisorDetails(String url, Context context);
    Response.Listener<TripAdviserDataResponse> getTripAdvisorDetailsSuccessListener(Context context);

    Response.ErrorListener getCommonErrorListener(Context context);
}
