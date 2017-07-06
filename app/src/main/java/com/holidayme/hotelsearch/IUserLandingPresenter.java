package com.holidayme.hotelsearch;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.android.volley.Response;
import com.holidayme.data.Destination;
import com.holidayme.response.GetDestinationForHotelResponse;
import com.holidayme.response.HotelDetailInfoResponse;
import com.holidayme.response.HotelListingInfoResponse;
import com.holidayme.response.HotelRatesResponse;
import com.holidayme.response.TripAdviserDataResponse;

import java.util.Map;

/**
 * Created by devrath.rathee on 16-09-2016.
 */

public interface IUserLandingPresenter {


    interface IHotelSearchPresenter {

        void checkDeepLinking(Destination destination, FragmentTransaction fragmentTransaction);
        void pushCleverTapEvent(String event, Map<String, Object> action);

        void getCityId(String url, Context context);
        Response.Listener<GetDestinationForHotelResponse> getCityIdSuccessListener(Context context);


        void getHotelRoomRate(String url, String request, Context context);
        Response.Listener<HotelRatesResponse> getHotelRoomRateSuccessListener(Context context);


        void getHotelDetails(String url, String request, Context context);
        Response.Listener<HotelDetailInfoResponse> getHotelDetailsSuccessListener(Context context);

        void getTripAdvisorDetails(String url, Context context);
        Response.Listener<TripAdviserDataResponse> getTripAdvisorDetailsSuccessListener(Context context);

        void getHotelListInfo(String url, String request, Context context);
        Response.Listener<HotelListingInfoResponse> getHotelListInfoSuccessListener(Context context);

        Response.ErrorListener getHotelIndexError(Context context);



    }

    interface IAutoCompletePresenter {
        void getNearByCity(String url, Context context);
    }
}
