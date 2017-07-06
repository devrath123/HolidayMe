package com.holidayme.myBooking_mvp;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import com.android.volley.Response;
import com.holidayme.data.RequestCancellationResponse;
import com.holidayme.data.SendEmailToUserResponse;
import com.holidayme.response.BookHistoryResponse;
import com.holidayme.response.HotelBookConfirmationResponse;


/**
 * Created by supriya.sakore on 15-11-2016.
 */

public interface IMyBookingPresenter {

    void getBookingHistory(String url, String request, int type,Context context, FragmentManager fragmentManager);
    Response.Listener<BookHistoryResponse> getBookingHistorySuccessListener(int type,FragmentManager fragmentManager, Context context);

    void getBookedHotelDetail(String url, Context context, FragmentManager fragmentManager);
    Response.Listener<HotelBookConfirmationResponse> getBookedHotelDetailSuccessListener(FragmentManager fragmentManager, Context context);

    void bookingRequestCancellation(String url,String request, Context context, FragmentManager fragmentManager);
    Response.Listener<RequestCancellationResponse> bookingRequestCancellationSuccessListener(FragmentManager fragmentManager, Context context);

    void sendEmailToUser(String url,String request, Context context, FragmentManager fragmentManager);
    Response.Listener<SendEmailToUserResponse>sendEmailToUserSuccessListener(FragmentManager fragmentManager, Context context);

    Response.ErrorListener commonErrorListener(FragmentManager fragmentManager, Context context);
}
