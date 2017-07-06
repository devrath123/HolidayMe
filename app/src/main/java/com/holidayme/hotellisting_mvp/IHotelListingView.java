package com.holidayme.hotellisting_mvp;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.android.volley.Response;
import com.holidayme.response.HotelDetailInfoResponse;
import com.holidayme.response.HotelListingInfoResponse;
import com.holidayme.response.HotelRatesResponse;
import com.holidayme.response.TripAdviserDataResponse;

/**
 * Created by arshad.shaikh on 11/14/2016.
 */

public interface IHotelListingView {


    void setSearchProperTies(HotelListingInfoResponse hotelListingInfoResponse);
    void setSearchPropertiesError();

    void setDeepLinkingSuccessListener(HotelListingInfoResponse deepLinkingSuccessListener);
    void deepLinkingErrorListener();

    void setHotelRateResponse(HotelRatesResponse hotelRateResponse);
    void setHotelDetailResponse(HotelDetailInfoResponse hotelDetailResponse);
    void setTripAdvisorDetailResponse(TripAdviserDataResponse tripAdvisorResponse);



    void dismissDialog();
    void showDialog();




}
