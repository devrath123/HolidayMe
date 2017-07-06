package com.holidayme.booking_mvp;

import com.holidayme.response.ApplyCouponCodeResponse;
import com.holidayme.response.HotelBookConfirmationResponse;

/**
 * Created by devrath.rathee on 14-11-2016.
 */

public interface IBookingView {

    interface IBookingPaymentGatewayView {
        void showDialog();
        void dismissDialog();
        void setHotelBookingConfirmationResponse(HotelBookConfirmationResponse hotelBookingConfirmationResponse);

    }

    interface IBookingDetailsView {
        void showDialog();

        void dismissDialog();

        void setArea(String areaKey, String areaValue);

        void setApplyCoupon(ApplyCouponCodeResponse applyCouponCodeResponse);

        void setInsertBookingDetailsResponse(StringBuilder stringBuilder);

        void setBookedHotelDetailsResponse(HotelBookConfirmationResponse hotelBookConfirmationResponse);
    }
}
