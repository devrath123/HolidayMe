package com.holidayme.hotelsearch;

import com.holidayme.data.Destination;
import com.holidayme.response.GetDestinationForHotelResponse;
import com.holidayme.response.HotelDetailInfoResponse;
import com.holidayme.response.HotelListingInfoResponse;
import com.holidayme.response.HotelRatesResponse;
import com.holidayme.response.TripAdviserDataResponse;

import java.util.List;

/**
 * Created by devrath.rathee on 19-09-2016.
 */

public interface IUserLandingView  {

    interface IHotelSearchView {

        void dismissDialog();
        void setGetDestinationForHotelResponse(GetDestinationForHotelResponse getDestinationForHotelResponse);
        void setHotelRateResponse(HotelRatesResponse hotelRateResponse);
        void setHotelDetailResponse(HotelDetailInfoResponse hotelDetailResponse);
        void setTripAdvisorDetailResponse(TripAdviserDataResponse tripAdvisorResponse);
        void setHotelListData(HotelListingInfoResponse hotelListingInfoResponse);
        void showDialog();
    }

    interface IAutoCompleteView {
        void hideShowProgressBar(int visibility);
        void errorDialog(String title, String message);
        void setRecentSearchAdapter(List<Destination> destinationList);

    }
}
