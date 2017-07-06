package com.holidayme.staycationListing_mvp;

import com.holidayme.data.PackageDetailResponse;
import com.holidayme.data.PackagesListDto;
import com.holidayme.response.GetDestinationForHotelResponse;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 10-03-2017.
 */

public interface IStyacationListView {
    void dismissDialog();
    void showDialog();
    void setGetawaysListResponse(PackageDetailResponse packagesListDtos,boolean isFirstTime);

}
