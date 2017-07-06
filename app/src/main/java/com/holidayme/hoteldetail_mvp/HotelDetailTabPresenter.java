package com.holidayme.hoteldetail_mvp;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.holidayme.activities.R;
import com.holidayme.activities.util.Utilities;
import com.holidayme.managers.APIManager;
import com.holidayme.response.GetDestinationForHotelResponse;
import com.holidayme.response.HotelDetailInfoResponse;
import com.holidayme.response.HotelRatesResponse;
import com.holidayme.response.TripAdviserDataResponse;

/**
 * Created by supriya.sakore on 02-11-2016.
 */

class HotelDetailTabPresenter implements IHotelDetailPresenter.IHotelDetailsTabPresenter {

    private IHotelDetailView.IHotelDetailTabView mView;


    HotelDetailTabPresenter(IHotelDetailView.IHotelDetailTabView hotelDetailMainPageView) {
        mView = hotelDetailMainPageView;
    }


    @Override
    public void getCityId(String url, Context context,FragmentManager fragmentManager) {
        mView.showDialog();
        new APIManager<GetDestinationForHotelResponse>().getCityId(url, getCityIdSuccessListener(fragmentManager,context), createMyErrorListener(fragmentManager,context), GetDestinationForHotelResponse.class, context);


    }

    @Override
    public Response.Listener<GetDestinationForHotelResponse> getCityIdSuccessListener(final FragmentManager fragmentManager,final Context context) {
        return new Response.Listener<GetDestinationForHotelResponse>() {
            @Override
            public void onResponse(GetDestinationForHotelResponse getDestinationForHotelResponse) {

                mView.dismissDialog();

                if (getDestinationForHotelResponse.getError() == null)
                    mView.setGetDestinationForHotelResponse(getDestinationForHotelResponse);
                else
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),true,fragmentManager);

            }


        };
    }

    @Override
    public void getHotelRoomRate(String url, String request, Context context,FragmentManager fragmentManager) {
        mView.showDialog();
        new APIManager<HotelRatesResponse>().postHotelRates(url, request, getHotelRoomRateSuccessListener(fragmentManager,context), createMyErrorListener(fragmentManager,context), HotelRatesResponse.class, context);


    }

    @Override
    public Response.Listener<HotelRatesResponse> getHotelRoomRateSuccessListener(final FragmentManager fragmentManager,final Context context) {
        return new Response.Listener<HotelRatesResponse>() {
            @Override
            public void onResponse(HotelRatesResponse hotelRatesResponse) {

                mView.dismissDialog();

                if (hotelRatesResponse.getError() == null)
                    mView.setHotelRateResponse(hotelRatesResponse);

                else
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),true,fragmentManager);

            }
        };
    }


    @Override
    public void getHotelDetails(String url, String request, Context context,FragmentManager fragmentManager) {
        mView.showDialog();
        new APIManager<HotelDetailInfoResponse>().postHotelDetails(url, request, getHotelDetailsSuccessListener(fragmentManager,context), createMyErrorListener(fragmentManager,context), HotelDetailInfoResponse.class, context);

    }

    @Override
    public Response.Listener<HotelDetailInfoResponse> getHotelDetailsSuccessListener(final FragmentManager fragmentManager,final Context context) {
        return new Response.Listener<HotelDetailInfoResponse>() {
            @Override
            public void onResponse(HotelDetailInfoResponse hotelDetailInfoResponse) {

                mView.dismissDialog();

                if (hotelDetailInfoResponse.getError() == null)
                    mView.setHotelDetailResponse(hotelDetailInfoResponse);

                else
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),true,fragmentManager);

            }
        };

    }


    @Override
    public void getTripAdviserDetails(String url, Context context, FragmentManager fragmentManager) {
        mView.showDialog();
        new APIManager<TripAdviserDataResponse>().getTripAdviserDetails(url, getTripAdvisorDetailsSuccessListener(fragmentManager,context), createMyErrorListener(fragmentManager,context), TripAdviserDataResponse.class, context);


    }

    @Override
    public Response.Listener<TripAdviserDataResponse> getTripAdvisorDetailsSuccessListener(final FragmentManager fragmentManager, final Context context) {
        return new Response.Listener<TripAdviserDataResponse>() {
            @Override
            public void onResponse(TripAdviserDataResponse tripAdviserDataResponse) {
                mView.dismissDialog();

                if (tripAdviserDataResponse.getError() == null)
                    mView.setTripAdvisorDetailResponse(tripAdviserDataResponse);

                else
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),true,fragmentManager);

            }
        };
    }


    @Override
    public Response.ErrorListener createMyErrorListener(final FragmentManager fragmentManager,final Context context) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mView.dismissDialog();
                Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),false,fragmentManager);
            }
        };
    }


}
