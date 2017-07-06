package com.holidayme.booking_mvp;

import android.content.Context;
import com.android.volley.Response;
import com.holidayme.response.ApplyCouponCodeResponse;
import com.holidayme.response.GetAreaByCityIdResponse;
import com.holidayme.response.HotelBookConfirmationResponse;
import com.holidayme.response.InsertBookingDetailResponse;

/**
 * Created by devrath.rathee on 14-11-2016.
 */

public interface IBookingPresenter {

    interface IBookingPaymentGatewayPresenter
    {
        void getBookedHotelDetails(String url,Context context);
        Response.Listener<HotelBookConfirmationResponse> getBookedHotelDetailSuccessListener(Context context);
        Response.ErrorListener commonErrorListener(Context context);

    }

    interface IBookingDetailsPresenter {
        void getAreaByCityId(String url,Context context);
        Response.Listener<GetAreaByCityIdResponse> getAreaByCityIdSuccessListener(Context context);

        void applyCouponCode(String url,String request,Context context);
        Response.Listener<ApplyCouponCodeResponse> applyCouponCodeSuccessListener(Context context);

        void insertBookingDetails(String url,String request,Context context);
        Response.Listener<InsertBookingDetailResponse> insertBookingDetailsSuccessListener(Context context);

        Response.Listener<HotelBookConfirmationResponse> getBookedHotelDetailSuccessListener(Context context);

        Response.ErrorListener commonErrorListener(Context context);

    }
}
