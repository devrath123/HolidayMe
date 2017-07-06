package com.holidayme.booking_mvp;


import android.content.Context;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.holidayme.activities.R;
import com.holidayme.activities.util.Utilities;
import com.holidayme.managers.APIManager;
import com.holidayme.response.HotelBookConfirmationResponse;

/**
 * Created by devrath.rathee on 14-11-2016.
 */

public class IBookingPaymentGatewayPresenter implements IBookingPresenter.IBookingPaymentGatewayPresenter {

    IBookingView.IBookingPaymentGatewayView mView;

    public IBookingPaymentGatewayPresenter(IBookingView.IBookingPaymentGatewayView view )
    {
       mView = view;
    }


    @Override
    public void getBookedHotelDetails(String url,Context context) {
        new APIManager<HotelBookConfirmationResponse>().getBookedHotelDetail(url, getBookedHotelDetailSuccessListener(context), commonErrorListener(context), HotelBookConfirmationResponse.class, context);
    }

    @Override
    public Response.Listener<HotelBookConfirmationResponse> getBookedHotelDetailSuccessListener(final Context context) {
        return new Response.Listener<HotelBookConfirmationResponse>() {
            @Override
            public void onResponse(HotelBookConfirmationResponse hotelBookConfirmationResponse) {
                mView.dismissDialog();
                mView.setHotelBookingConfirmationResponse(hotelBookConfirmationResponse);
            }
        };
    }

    @Override
    public Response.ErrorListener commonErrorListener(final Context context) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mView.dismissDialog();
                Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),false,null);
            }
        };
    }
}
