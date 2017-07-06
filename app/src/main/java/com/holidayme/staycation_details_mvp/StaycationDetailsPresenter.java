package com.holidayme.staycation_details_mvp;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.holidayme.activities.R;
import com.holidayme.activities.util.Utilities;
import com.holidayme.data.AmenitiesDTO;
import com.holidayme.data.GetawayDetailBean;
import com.holidayme.data.NearandAroundBean;
import com.holidayme.managers.APIManager;
import com.holidayme.response.TripAdviserDataResponse;

/**
 * Created by devrath.rathee on 18-04-2017.
 */

public class StaycationDetailsPresenter implements IStaycationDetailsPresenter {

    private Context mContext;
    private IStaycationDetailsView mView;

    public StaycationDetailsPresenter(Context context,IStaycationDetailsView iStaycationDetailsView)
    {
        this.mContext = context;
        this.mView = iStaycationDetailsView;
    }

    // Package Details
    @Override
    public void getPackageDetails(String url, String request, Context context) {
        mView.showProgressDialog();
        new APIManager<GetawayDetailBean>().packageDetails(url,request, packageDetailSuccessListener(), commonErrorListener(), GetawayDetailBean.class, context);
    }

    @Override
    public Response.Listener<GetawayDetailBean> packageDetailSuccessListener() {
        return new Response.Listener<GetawayDetailBean>() {
            @Override
            public void onResponse(GetawayDetailBean getawayDetailBean) {
                mView.hideProgressDialog();
                mView.setPackageDetails(getawayDetailBean);
            }
        };
    }


    // Amenities Details
    @Override
    public void getAmenitiesDetails(String url, String request, Context context) {
        new APIManager<AmenitiesDTO>().amenitiesDetails(url,request, amenitiesSuccessListener(), commonErrorListener(), AmenitiesDTO.class, context);

    }

    @Override
    public Response.Listener<AmenitiesDTO> amenitiesSuccessListener() {
        return new Response.Listener<AmenitiesDTO>() {
            @Override
            public void onResponse(AmenitiesDTO amenitiesDTO) {
                mView.setAmenitiesDetails(amenitiesDTO);
            }
        };
    }


    // Near and Around Details
    @Override
    public void getNearAndAroundDetails(String url,Context context) {
        new APIManager<NearandAroundBean>().getFourSquare(url,
                nearAndAroundSuccessListener(), commonErrorListener(), NearandAroundBean.class, context);
    }

    @Override
    public Response.Listener<NearandAroundBean> nearAndAroundSuccessListener() {
        return new Response.Listener<NearandAroundBean>() {
            @Override
            public void onResponse(NearandAroundBean nearandAroundBean) {
                mView.setNearAndAround(nearandAroundBean);
            }
        };
    }


    // Trip Advisor Details
    @Override
    public void getTripAdvisorDetails(String url, Context context) {
        new APIManager<TripAdviserDataResponse>().getTripAdviserDetails(url, tripAdvisorSuccessListener(), commonErrorListener(), TripAdviserDataResponse.class, context);

    }

    @Override
    public Response.Listener<TripAdviserDataResponse> tripAdvisorSuccessListener() {
        return new Response.Listener<TripAdviserDataResponse>() {
            @Override
            public void onResponse(TripAdviserDataResponse tripAdviserDataResponse) {
                mView.setTripAdvisorDetails(tripAdviserDataResponse);
            }
        };
    }

    @Override
    public Response.ErrorListener commonErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mView.hideProgressDialog();
               // Utilities.commonErrorMessage(mContext, mContext.getString(R.string.app_name),mContext.getString(R.string.common_error_msg),false,null);

            }
        };
    }
}
