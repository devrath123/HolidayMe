package com.holidayme.staycationListing_mvp;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.android.volley.Response;
import com.holidayme.data.PackageDetailResponse;
import com.holidayme.response.GetDestinationForHotelResponse;
import com.holidayme.response.HotelRatesResponse;

import org.json.JSONArray;

/**
 * Created by supriya.sakore on 08-03-2017.
 */

public interface IStaycationListingPresenter {
    void PostStaycationList(String url, String request, Context context, FragmentManager fragmentManager,boolean isFirstTime);
    Response.Listener<PackageDetailResponse> postPackageDetailSuccessListener(FragmentManager fragmentManager, Context context,boolean isFirstTime);

    Response.ErrorListener commonErrorListener(FragmentManager fragmentManager,Context context);


}
