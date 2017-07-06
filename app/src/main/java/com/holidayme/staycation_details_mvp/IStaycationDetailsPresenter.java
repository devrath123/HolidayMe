package com.holidayme.staycation_details_mvp;

import android.content.Context;

import com.android.volley.Response;
import com.holidayme.data.AmenitiesDTO;
import com.holidayme.data.GetawayDetailBean;
import com.holidayme.data.NearandAroundBean;
import com.holidayme.response.TripAdviserDataResponse;

/**
 * Created by devrath.rathee on 18-04-2017.
 */

public interface IStaycationDetailsPresenter {

    void getPackageDetails(String url, String request, Context context);
    Response.Listener<GetawayDetailBean> packageDetailSuccessListener();

    void getAmenitiesDetails(String url,String request,Context context);
    Response.Listener<AmenitiesDTO> amenitiesSuccessListener();

    void getNearAndAroundDetails(String url,Context context);
    Response.Listener<NearandAroundBean> nearAndAroundSuccessListener();

    void getTripAdvisorDetails(String url,Context context);
    Response.Listener<TripAdviserDataResponse> tripAdvisorSuccessListener();

    Response.ErrorListener commonErrorListener();
}
