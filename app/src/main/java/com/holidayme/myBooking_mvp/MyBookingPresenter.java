package com.holidayme.myBooking_mvp;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.holidayme.activities.R;
import com.holidayme.activities.util.Utilities;
import com.holidayme.data.RequestCancellationResponse;
import com.holidayme.data.SendEmailToUserResponse;
import com.holidayme.data.UserDTO;
import com.holidayme.managers.APIManager;
import com.holidayme.response.BookHistoryResponse;
import com.holidayme.response.HotelBookConfirmationResponse;

/**
 * Created by supriya.sakore on 15-11-2016.
 */

public class MyBookingPresenter implements com.holidayme.myBooking_mvp.IMyBookingPresenter {
    private IMyBookingView.IMyBooking mView;

    public MyBookingPresenter(IMyBookingView.IMyBooking myBookingView){
        mView=myBookingView;
    }


    @Override
    public void getBookingHistory(String url,String request,int type, Context context, FragmentManager fragmentManager) {
        mView.showDialog();
        new APIManager<BookHistoryResponse>().postBookingHistory(url,request,getBookingHistorySuccessListener(type,fragmentManager,context), commonErrorListener(fragmentManager,context), BookHistoryResponse.class, context);

    }

    @Override
    public Response.Listener<BookHistoryResponse> getBookingHistorySuccessListener(final int type,final FragmentManager fragmentManager, final Context context) {
        return new Response.Listener<BookHistoryResponse>() {
            @Override
            public void onResponse(BookHistoryResponse bookHistoryResponse) {

                mView.dismissDialog();

                if (bookHistoryResponse.getError() == null)
                    mView.setBookingHistoryResponse(bookHistoryResponse,type);
                else
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),true,fragmentManager);

            }


        };
    }

    @Override
    public void getBookedHotelDetail(String url, Context context, FragmentManager fragmentManager) {
        mView.showDialog();
        new APIManager<HotelBookConfirmationResponse>().getBookedHotelDetail(url, getBookedHotelDetailSuccessListener(fragmentManager,context), commonErrorListener(fragmentManager,context), HotelBookConfirmationResponse.class, context);
    }

    @Override
    public Response.Listener<HotelBookConfirmationResponse> getBookedHotelDetailSuccessListener(final FragmentManager fragmentManager, final Context context) {
        return new Response.Listener<HotelBookConfirmationResponse>() {
            @Override
            public void onResponse(HotelBookConfirmationResponse getHotelBookComfermationResponse) {

                mView.dismissDialog();

                if (getHotelBookComfermationResponse.getError() == null)
                    mView.setBookedHotelDetailResponse(getHotelBookComfermationResponse);
                else
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),false,fragmentManager);

            }
        };
    }

    @Override
    public void bookingRequestCancellation(String url, String request, Context context, FragmentManager fragmentManager) {
        mView.showDialog();
        new APIManager<RequestCancellationResponse>().postRequestCancellation(url,request, bookingRequestCancellationSuccessListener(fragmentManager,context), commonErrorListener(fragmentManager,context), RequestCancellationResponse.class, context);

    }

    @Override
    public Response.Listener<RequestCancellationResponse> bookingRequestCancellationSuccessListener(final FragmentManager fragmentManager, final Context context) {
        return new Response.Listener<RequestCancellationResponse>() {
            @Override
            public void onResponse(RequestCancellationResponse requestCancellationResponse) {

                mView.dismissDialog();

                if (requestCancellationResponse.getError()==null)
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name),String.format(context.getString(R.string.request_cancellation_info), requestCancellationResponse.getIncidentId()+""), false, fragmentManager);
                else
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),false,fragmentManager);

            }
        };
    }

    @Override
    public void sendEmailToUser(String url, String request, Context context, FragmentManager fragmentManager) {
        mView.showDialog();
        new APIManager<SendEmailToUserResponse>().postSendEmailToUser(url,request, sendEmailToUserSuccessListener(fragmentManager,context), commonErrorListener(fragmentManager,context), SendEmailToUserResponse.class, context);

    }

    @Override
    public Response.Listener<SendEmailToUserResponse> sendEmailToUserSuccessListener(final FragmentManager fragmentManager, final Context context) {
        return new Response.Listener<SendEmailToUserResponse>() {
            @Override
            public void onResponse(SendEmailToUserResponse sendEmailToUserResponse) {

                mView.dismissDialog();

                if (sendEmailToUserResponse.getError()== null)
                    if(sendEmailToUserResponse.isSendSuccessful())
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.send_email_to_user_massage)+" "+UserDTO.getUserDTO().getEmailID(),false,fragmentManager);
                    else
                        Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),false,fragmentManager);

                else
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),false,fragmentManager);

            }
        };
    }

    @Override
    public Response.ErrorListener commonErrorListener(final FragmentManager fragmentManager, final Context context) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mView.dismissDialog();
                Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),false,fragmentManager);
            }
        };
    }
}
