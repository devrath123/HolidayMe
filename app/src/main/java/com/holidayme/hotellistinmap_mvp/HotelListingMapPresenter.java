package com.holidayme.hotellistinmap_mvp;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.holidayme.activities.R;
import com.holidayme.activities.util.Utilities;
import com.holidayme.fragments.MapViewFragment;
import com.holidayme.managers.APIManager;
import com.holidayme.response.HotelDetailInfoResponse;
import com.holidayme.response.HotelRatesResponse;
import com.holidayme.response.TripAdviserDataResponse;

/**
 * Created by arshad.shaikh on 11/15/2016.
 */

public class HotelListingMapPresenter implements IHotelMapLandingPresenter {

    private IHotelMapLandingView mView;


    public HotelListingMapPresenter(MapViewFragment mapViewFragment) {

        this.mView=mapViewFragment;
    }

    @Override
    public void getHotelRoomRate(String url, String request, Context context) {

        new APIManager<HotelRatesResponse>().postHotelDetails(url, request, getHotelRoomRateSuccessListener(context), getCommonErrorListener(context), HotelRatesResponse.class, context);

    }

    @Override
    public Response.Listener<HotelRatesResponse> getHotelRoomRateSuccessListener(final Context context) {
        return new Response.Listener<HotelRatesResponse>() {
            @Override
            public void onResponse(HotelRatesResponse hotelRatesResponse) {

                mView.dismissDialog();
                if(hotelRatesResponse.getError()==null)
                    mView.setHotelRateResponse(hotelRatesResponse);
                else
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.common_error_msg),false,null);


            }
        };
    }



    @Override
    public void getHotelDetails(String url, String request, Context context) {

        new APIManager<HotelDetailInfoResponse>().postHotelDetails(url, request, getHotelDetailsSuccessListener(context), getCommonErrorListener(context), HotelDetailInfoResponse.class, context);

    }

    @Override
    public Response.Listener<HotelDetailInfoResponse> getHotelDetailsSuccessListener(final Context context) {
        return new Response.Listener<HotelDetailInfoResponse>() {
            @Override
            public void onResponse(HotelDetailInfoResponse hotelDetailInfoResponse) {

                mView.dismissDialog();
                if(hotelDetailInfoResponse.getError()==null)
                    mView.setHotelDetailResponse(hotelDetailInfoResponse);
                else
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.common_error_msg),false,null);


            }
        };
    }



    @Override
    public void getTripAdvisorDetails(String url, Context context) {

        new APIManager<TripAdviserDataResponse>().getTripAdviserDetails(url, getTripAdvisorDetailsSuccessListener(context), getCommonErrorListener(context), TripAdviserDataResponse.class, context);

    }

    @Override
    public Response.Listener<TripAdviserDataResponse> getTripAdvisorDetailsSuccessListener(final Context context) {
        return new Response.Listener<TripAdviserDataResponse>() {
            @Override
            public void onResponse(TripAdviserDataResponse tripAdviserDataResponse) {

                mView.dismissDialog();
                if(tripAdviserDataResponse.getError()==null)
                mView.setTripAdviserDetailResponse(tripAdviserDataResponse);
                else
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.common_error_msg),false,null);

            }
        };
    }

    @Override
    public Response.ErrorListener getCommonErrorListener(final Context context) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                mView.dismissDialog();
                Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),false,null);
            }
        };
    }


}
