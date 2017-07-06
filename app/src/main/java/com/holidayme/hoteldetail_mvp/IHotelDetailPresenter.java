package com.holidayme.hoteldetail_mvp;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.android.volley.Response;
import com.holidayme.response.BookingDetailsResponse;
import com.holidayme.response.GenerateTemporaryHotelBookingResponse;
import com.holidayme.response.GetDestinationForHotelResponse;
import com.holidayme.response.HotelBookConfirmationResponse;
import com.holidayme.response.HotelDetailInfoResponse;
import com.holidayme.response.HotelRatesResponse;
import com.holidayme.response.RoomDetailsResponse;
import com.holidayme.response.TripAdviserDataResponse;

/**
 * Created by supriya.sakore on 02-11-2016.
 */

public interface IHotelDetailPresenter {

      interface IHotelDetailsTabPresenter{
          void getCityId(String url, Context context,FragmentManager fragmentManager);
          Response.Listener<GetDestinationForHotelResponse> getCityIdSuccessListener(FragmentManager fragmentManager,Context context);


          void getHotelRoomRate(String url, String request, Context context,FragmentManager fragmentManager);
          Response.Listener<HotelRatesResponse> getHotelRoomRateSuccessListener(FragmentManager fragmentManager,Context context);


          void getHotelDetails(String url, String request, Context context,FragmentManager fragmentManager);
          Response.Listener<HotelDetailInfoResponse> getHotelDetailsSuccessListener(FragmentManager fragmentManager,Context context);


          void getTripAdviserDetails(String url, Context context, FragmentManager fragmentManager);
          Response.Listener<TripAdviserDataResponse> getTripAdvisorDetailsSuccessListener(FragmentManager fragmentManager, Context context);

          Response.ErrorListener createMyErrorListener(FragmentManager fragmentManager,Context context);
      }



    interface IHotelDetailRoomPresenter {

        void generateTemporaryBookingId(String url, String request, Context context, FragmentManager fragmentManager);
        Response.Listener<GenerateTemporaryHotelBookingResponse> generateTemporaryBookingIdSuccessListener(FragmentManager fragmentManager, Context context);

        void getBookingDetail(String url, Context context, FragmentManager fragmentManager);
        Response.Listener<BookingDetailsResponse> getBookingDetailSuccessListener(FragmentManager fragmentManager, Context context);

        void getBookedHotelDetail(String url, Context context, FragmentManager fragmentManager);
        Response.Listener<HotelBookConfirmationResponse> getBookedHotelDetailSuccessListener(FragmentManager fragmentManager, Context context);

        void getRoomDetailInfo(String url,String request, Context context, FragmentManager fragmentManager,int position);
        Response.Listener<RoomDetailsResponse> getRoomDetailInfoSuccessListener(FragmentManager fragmentManager, Context context,int position);

        Response.ErrorListener commonErrorListener(FragmentManager fragmentManager, Context context);
    }


    interface IRoomDetailPresenter{

        void generateTemporaryBookingId(String url, String request, Context context, FragmentManager fragmentManager);
        Response.Listener<GenerateTemporaryHotelBookingResponse> generateTemporaryBookingIdSuccessListener(FragmentManager fragmentManager, Context context);

        void getBookingDetail(String url, Context context, FragmentManager fragmentManager);
        Response.Listener<BookingDetailsResponse> getBookingDetailSuccessListener(FragmentManager fragmentManager, Context context);

        void getBookedHotelDetail(String url, Context context, FragmentManager fragmentManager);
        Response.Listener<HotelBookConfirmationResponse> getBookedHotelDetailSuccessListener(FragmentManager fragmentManager, Context context);

        Response.ErrorListener commonErrorListener(FragmentManager fragmentManager, Context context);
    }
}
