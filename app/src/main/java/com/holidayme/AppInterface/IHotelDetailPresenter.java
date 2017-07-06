package com.holidayme.AppInterface;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.android.volley.Response;
import com.holidayme.response.GetDestinationForHotelResponse;
import com.holidayme.response.HotelDetailInfoResponse;
import com.holidayme.response.HotelListingInfoResponse;
import com.holidayme.response.HotelRatesResponse;
import com.holidayme.response.TripAdviserDataResponse;

/**
 * Created by supriya.sakore on 02-11-2016.
 */

public interface IHotelDetailPresenter {

           void getCityId(String url, Context context,FragmentManager fragmentManager);
           Response.Listener<GetDestinationForHotelResponse> getCityIdSuccessListener(FragmentManager fragmentManager,Context context);


           void getHotelRoomRate(String url, String request, Context context,FragmentManager fragmentManager);
           Response.Listener<HotelRatesResponse> getHotelRoomRateSuccessListener(FragmentManager fragmentManager,Context context);


           void getHotelDetails(String url, String request, Context context,FragmentManager fragmentManager);
           Response.Listener<HotelDetailInfoResponse> getHotelDetailsSuccessListener(FragmentManager fragmentManager,Context context);


           void getTripAdvisorDetails(String url, Context context,FragmentManager fragmentManager);
           Response.Listener<TripAdviserDataResponse> getTripAdvisorDetailsSuccessListener(FragmentManager fragmentManager, Context context);

           Response.ErrorListener createMyErrorListener(FragmentManager fragmentManager,Context context);


}
