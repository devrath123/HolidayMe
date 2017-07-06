package com.holidayme.staycationListing_mvp;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.holidayme.activities.R;
import com.holidayme.activities.util.Utilities;
import com.holidayme.common.Log;
import com.holidayme.data.PackageDetailResponse;
import com.holidayme.data.PackagesListDto;
import com.holidayme.hoteldetail_mvp.IHotelDetailView;
import com.holidayme.managers.APIManager;
import com.holidayme.response.HotelBookConfirmationResponse;
import com.holidayme.response.HotelRatesResponse;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by supriya.sakore on 10-03-2017.
 */

public class StaycationListingPresenter implements IStaycationListingPresenter {
    private IStyacationListView  mView;

    StaycationListingPresenter(IStyacationListView  styacationListView) {
        mView = styacationListView;
    }

    @Override
    public void PostStaycationList(String url, String request, Context context, FragmentManager fragmentManager,boolean isFirstTime) {
        mView.showDialog();
        new APIManager<PackageDetailResponse>().getawaysListing(url,request,postPackageDetailSuccessListener(fragmentManager,context,isFirstTime),commonErrorListener(fragmentManager,context),PackageDetailResponse.class, context);

    }

    @Override
    public Response.Listener<PackageDetailResponse> postPackageDetailSuccessListener(final FragmentManager fragmentManager, final  Context context, final boolean isFirstTime) {
        return new Response.Listener<PackageDetailResponse>() {
            @Override
            public void onResponse(PackageDetailResponse packageDetailResponse) {

                mView.dismissDialog();

                if (packageDetailResponse!=null&&packageDetailResponse.getErrorMessage() == null)
                    mView.setGetawaysListResponse(packageDetailResponse,isFirstTime);
                else
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),true,fragmentManager);

            }
        };
    }


    @Override
    public Response.ErrorListener commonErrorListener(final FragmentManager fragmentManager, final Context context) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mView.dismissDialog();
                Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),true,fragmentManager);
            }
        };
    }
}
