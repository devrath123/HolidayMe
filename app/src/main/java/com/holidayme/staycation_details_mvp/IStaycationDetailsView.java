package com.holidayme.staycation_details_mvp;

import com.holidayme.data.AmenitiesDTO;
import com.holidayme.data.GetawayDetailBean;
import com.holidayme.data.NearandAroundBean;
import com.holidayme.response.TripAdviserDataResponse;

/**
 * Created by devrath.rathee on 18-04-2017.
 */

public interface IStaycationDetailsView {

    void showProgressDialog();
    void hideProgressDialog();

    void setPackageDetails(GetawayDetailBean getawayDetailBean);
    void setAmenitiesDetails(AmenitiesDTO amenitiesDTO);
    void setNearAndAround(NearandAroundBean nearandAroundBean);
    void setTripAdvisorDetails(TripAdviserDataResponse tripAdviserDataResponse);
}
