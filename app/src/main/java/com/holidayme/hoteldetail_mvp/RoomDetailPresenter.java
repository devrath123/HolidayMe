package com.holidayme.hoteldetail_mvp;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.holidayme.activities.R;
import com.holidayme.activities.util.Utilities;
import com.holidayme.managers.APIManager;
import com.holidayme.response.BookingDetailsResponse;
import com.holidayme.response.GenerateTemporaryHotelBookingResponse;
import com.holidayme.response.HotelBookConfirmationResponse;

/**
 * Created by supriya.sakore on 11-11-2016.
 */

public class RoomDetailPresenter implements IHotelDetailPresenter.IRoomDetailPresenter{
    private IHotelDetailView.IRoomDetailTabView  mView;


    RoomDetailPresenter(IHotelDetailView.IRoomDetailTabView roomDetailTabView) {
        mView = roomDetailTabView;
    }


    @Override
    public void generateTemporaryBookingId(String url, String request, Context context, FragmentManager fragmentManager) {
        mView.showDialog();
        new APIManager<GenerateTemporaryHotelBookingResponse>().postGenerateTemporaryHotelBooking(url,request,generateTemporaryBookingIdSuccessListener(fragmentManager,context), commonErrorListener(fragmentManager,context), GenerateTemporaryHotelBookingResponse.class, context);

    }

    @Override
    public Response.Listener<GenerateTemporaryHotelBookingResponse> generateTemporaryBookingIdSuccessListener(final FragmentManager fragmentManager, final Context context) {


        return new Response.Listener<GenerateTemporaryHotelBookingResponse>() {
            @Override
            public void onResponse(GenerateTemporaryHotelBookingResponse getGenerateTemporaryHotelBookingResponse) {

                mView.dismissDialog();

                if (getGenerateTemporaryHotelBookingResponse.getError() == null)
                    mView.setGetTemporaryBookingIdResponse(getGenerateTemporaryHotelBookingResponse);
                else
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),false,fragmentManager);

            }


        };
    }

    @Override
    public void getBookingDetail(String url, Context context, FragmentManager fragmentManager) {
        mView.showDialog();
        new APIManager<BookingDetailsResponse>().getBookingDetail(url,getBookingDetailSuccessListener(fragmentManager,context), commonErrorListener(fragmentManager,context), BookingDetailsResponse.class, context);


    }

    @Override
    public Response.Listener<BookingDetailsResponse> getBookingDetailSuccessListener(final FragmentManager fragmentManager,final Context context) {
        return new Response.Listener<BookingDetailsResponse>() {
            @Override
            public void onResponse(BookingDetailsResponse getBookingDetailsResponse) {

                mView.dismissDialog();

                if (getBookingDetailsResponse.getError() == null)
                    mView.setGetBookingDetailResponse(getBookingDetailsResponse);
                else
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),false,fragmentManager);

            }


        };
    }

    @Override
    public void getBookedHotelDetail(String url, Context context, FragmentManager fragmentManager) {
        mView.showDialog();
        new APIManager<HotelBookConfirmationResponse>().getBookedHotelDetail(url, getBookedHotelDetailSuccessListener(fragmentManager,context), commonErrorListener(fragmentManager,context), HotelBookConfirmationResponse.class, context);

    }

    @Override
    public Response.Listener<HotelBookConfirmationResponse> getBookedHotelDetailSuccessListener(final FragmentManager fragmentManager,final Context context) {
        return new Response.Listener<HotelBookConfirmationResponse>() {
            @Override
            public void onResponse(HotelBookConfirmationResponse getHotelBookComfermationResponse) {

                mView.dismissDialog();

                if (getHotelBookComfermationResponse.getError() == null)
                    mView.setGetBookedHotelDetailResponse(getHotelBookComfermationResponse);
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
