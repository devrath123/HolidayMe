package com.holidayme.hoteldetail_mvp;

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

public interface IHotelDetailView {
    interface IHotelDetailTabView {
        void dismissDialog();

        void showDialog();

        void setGetDestinationForHotelResponse(GetDestinationForHotelResponse getDestinationForHotelResponse);

        void setHotelRateResponse(HotelRatesResponse hotelRateResponse);

        void setHotelDetailResponse(HotelDetailInfoResponse hotelDetailResponse);

        void setTripAdvisorDetailResponse(TripAdviserDataResponse tripAdvisorResponse);

        void populateHotelDetailData();

        void switchToHotelDetailMap();

    }

    interface IHotelDetailRoomView {
        void dismissDialog();

        void showDialog();

        void setGetTemporaryBookingIdResponse(GenerateTemporaryHotelBookingResponse getTemporaryBookingIdResponse);

        void setGetBookingDetailResponse(BookingDetailsResponse getBookingDetailResponse);

        void setGetBookedHotelDetailResponse(HotelBookConfirmationResponse getBookedHotelDetailResponse);

        void switchToBookingPage(BookingDetailsResponse bookingDetailsResponse);

        void switchToBookingConfirmationPage(HotelBookConfirmationResponse hotelBookConfirmationResponse);

        void addImageToHotelDetailView();

        void setRoomDetailInfo(RoomDetailsResponse roomDetailInfo,int position);

        void switchToRoomDetailInfoPage(RoomDetailsResponse roomDetailInfo,int position);

        void setGalleryAdapter();

        void setRoomDetailInfoAdapter();

    }

    interface IHotelDetailReviewView {
        void disableSeekBarClick();

        void setSummeryAdapter();

        void setReviewAdapter();

        void setTopReviewAdapter();

        void setAverageRatingToSeekBar();
    }

    interface IHotelDetailMapView {
        void showMarkerOnMap();
    }

    interface IRoomDetailView {
        void addImagesToView();

        void UIFunctionality();

        void setGalleryAdapter();

        void setTextToTextView();
    }

    interface  IRoomDetailTabView{

        void dismissDialog();

        void showDialog();

        void setGetTemporaryBookingIdResponse(GenerateTemporaryHotelBookingResponse getTemporaryBookingIdResponse);

        void setGetBookingDetailResponse(BookingDetailsResponse getBookingDetailResponse);

        void setGetBookedHotelDetailResponse(HotelBookConfirmationResponse getBookedHotelDetailResponse);

        void switchToBookingPage(BookingDetailsResponse bookingDetailsResponse);

        void switchToBookingConfirmationPage(HotelBookConfirmationResponse hotelBookConfirmationResponse);

    }
}
