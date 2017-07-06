package com.holidayme.hotellisting_mvp;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.android.volley.Response;
import com.holidayme.response.HotelDetailInfoResponse;
import com.holidayme.response.HotelListingInfoResponse;
import com.holidayme.response.HotelRatesResponse;
import com.holidayme.response.TripAdviserDataResponse;

import java.util.Map;

/**
 * Created by arshad.shaikh on 11/14/2016.
 */

public interface IHotelLandingPresenter {

    interface  IHotelListingPresenter{


        void getHotelListInfo(String url, String request, Context context, FragmentManager fragmentManager);
        Response.Listener<HotelListingInfoResponse> getHotelListInfoSuccessListener();
        Response.ErrorListener getHotelListInfoErrorListener();

        void postSearchProperties(String url, String request, Context context);
        Response.Listener<HotelListingInfoResponse>getHotelSearchPropertiesListener();
        Response.ErrorListener postSearchProperTiesError();



        void pushCleverTapEvent(String event, Map<String, Object> action);


        void getHotelRoomRate(String url, String request, Context context);
        Response.Listener<HotelRatesResponse> getHotelRoomRateSuccessListener(Context context);

        void getHotelDetails(String url, String request, Context context);
        Response.Listener<HotelDetailInfoResponse> getHotelDetailsSuccessListener(Context context);

        void getTripAdvisorDetails(String url, Context context);
        Response.Listener<TripAdviserDataResponse> getTripAdvisorDetailsSuccessListener(Context context);

        Response.ErrorListener getHotelListAdapterError(Context context);



    }

}
