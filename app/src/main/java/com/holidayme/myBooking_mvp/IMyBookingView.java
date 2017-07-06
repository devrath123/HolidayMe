package com.holidayme.myBooking_mvp;


import com.holidayme.response.BookHistoryResponse;
import com.holidayme.response.HotelBookConfirmationResponse;

/**
 * Created by supriya.sakore on 15-11-2016.
 */

public interface IMyBookingView {
    interface IMyBooking {
        void dismissDialog();

        void showDialog();

        void setBookedHotelDetailResponse(HotelBookConfirmationResponse hotelBookConfirmationResponse);

        void setBookingHistoryResponse(BookHistoryResponse bookingHistoryResponse, int type);



    }

   interface IMyBookingViewClickListener {

        void viewVoucherClickListener(int position);

        void sendVoucherClickListener(int position);

        void requestCancellationClickListener(int position);
    }

}
