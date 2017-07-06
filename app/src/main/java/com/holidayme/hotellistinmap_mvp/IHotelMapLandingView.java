package com.holidayme.hotellistinmap_mvp;

import com.holidayme.response.HotelDetailInfoResponse;
import com.holidayme.response.HotelRatesResponse;
import com.holidayme.response.TripAdviserDataResponse;

/**
 * Created by arshad.shaikh on 11/15/2016.
 */

public interface IHotelMapLandingView {

    void dismissDialog();
    void setHotelRateResponse(HotelRatesResponse hotelRateResponse);
    void setHotelDetailResponse(HotelDetailInfoResponse hotelDetailResponse);
    void setTripAdviserDetailResponse(TripAdviserDataResponse tripAdvisorResponse);


}
