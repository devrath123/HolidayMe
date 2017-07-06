package com.holidayme.hotellisting_mvp;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.RegistrationIntentService;
import com.holidayme.activities.util.Utilities;
import com.holidayme.fragments.HotelListingFragment;
import com.holidayme.managers.APIManager;
import com.holidayme.response.HotelDetailInfoResponse;
import com.holidayme.response.HotelListingInfoResponse;
import com.holidayme.response.HotelRatesResponse;
import com.holidayme.response.TripAdviserDataResponse;

import java.util.Map;

/**
 * Created by arshad.shaikh on 11/14/2016.
 */

public class HotelListingPresenter implements  IHotelLandingPresenter.IHotelListingPresenter{

    private IHotelListingView mView;




    public HotelListingPresenter(HotelListingFragment hotelListingFragment) {

        this.mView=hotelListingFragment;
    }


    @Override
    public void pushCleverTapEvent(String event, Map<String, Object> action) {

        AppController.getInstance().getCleverTapInstance().data.pushGcmRegistrationId(RegistrationIntentService.token, true);
        AppController.getInstance().getCleverTapInstance().event.push(event, action);

    }

    @Override
    public void getHotelRoomRate(String url, String request, Context context) {

        new APIManager<HotelRatesResponse>().postHotelDetails(url, request, getHotelRoomRateSuccessListener(context), getHotelListAdapterError(context), HotelRatesResponse.class, context);



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
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),false,null);


            }
        };
    }



    @Override
    public void getHotelDetails(String url, String request, Context context) {

        new APIManager<HotelDetailInfoResponse>().postHotelDetails(url, request, getHotelDetailsSuccessListener(context), getHotelListAdapterError(context), HotelDetailInfoResponse.class, context);


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
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),false,null);
            }
        };
    }



    @Override
    public void getTripAdvisorDetails(String url, Context context) {
        new APIManager<TripAdviserDataResponse>().getTripAdviserDetails(url, getTripAdvisorDetailsSuccessListener(context), getHotelListAdapterError(context), TripAdviserDataResponse.class, context);

    }

    @Override
    public Response.Listener<TripAdviserDataResponse> getTripAdvisorDetailsSuccessListener(final Context context) {
        return new Response.Listener<TripAdviserDataResponse>() {
            @Override
            public void onResponse(TripAdviserDataResponse tripAdviserDataResponse) {
                mView.dismissDialog();
                if(tripAdviserDataResponse.getError()==null)
                mView.setTripAdvisorDetailResponse(tripAdviserDataResponse);
                else
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),false,null);

            }
        };
    }

    @Override
    public Response.ErrorListener getHotelListAdapterError(final Context context) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                mView.dismissDialog();
                Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),false,null);
            }
        };
    }


    @Override
    public void getHotelListInfo(String url, String request, Context context, FragmentManager fragmentManager) {

        new APIManager<HotelListingInfoResponse>().getHotelListInfo(url,request, getHotelListInfoSuccessListener(), getHotelListInfoErrorListener(), HotelListingInfoResponse.class, context);

    }

    @Override
    public Response.Listener<HotelListingInfoResponse> getHotelListInfoSuccessListener() {
        return new Response.Listener<HotelListingInfoResponse>() {
            @Override
            public void onResponse(HotelListingInfoResponse hotelListingInfoResponse) {
                mView.dismissDialog();
                mView.setDeepLinkingSuccessListener(hotelListingInfoResponse);
            }
        };
    }

    @Override
    public Response.ErrorListener getHotelListInfoErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                mView.dismissDialog();
                mView.deepLinkingErrorListener();
            }
        };
    }

    @Override
    public void postSearchProperties(String url, String request, Context context) {



        new APIManager<HotelListingInfoResponse>().postSearchProperties(url,request,getHotelSearchPropertiesListener(),postSearchProperTiesError(),HotelListingInfoResponse.class,context);

    }

    @Override
    public Response.Listener<HotelListingInfoResponse> getHotelSearchPropertiesListener() {
        return new Response.Listener<HotelListingInfoResponse>() {
            @Override
            public void onResponse(HotelListingInfoResponse hotelListingInfoResponse) {
                mView.dismissDialog();
                mView.setSearchProperTies(hotelListingInfoResponse);
            }
        };
    }

    @Override
    public Response.ErrorListener postSearchProperTiesError() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mView.dismissDialog();
                mView.setSearchPropertiesError();

            }
        };
    }


}
