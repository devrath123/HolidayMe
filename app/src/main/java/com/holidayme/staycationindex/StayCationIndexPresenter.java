package com.holidayme.staycationindex;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.holidayme.activities.R;
import com.holidayme.activities.util.Utilities;
import com.holidayme.common.Log;
import com.holidayme.managers.APIManager;
import com.holidayme.staycationListing_mvp.IStyacationListView;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by arshad.shaikh on 3/22/2017.
 */

public class StayCationIndexPresenter implements  IStayCationLandingPresenter {

    private IStayCationIndexView mView;
    public StayCationIndexPresenter(StayCationIndexFragment stayCationIndexFragment) {

        mView=stayCationIndexFragment;
    }

    @Override
    public void getPackagesCount(Context context ,String url) {

        new APIManager<JsonArray>().getGetawaysPackages(url,getGetawaysPackages(context),commonErrorListener(context),context);

    }

    @Override
    public Response.Listener<JSONArray> getGetawaysPackages(final Context context) {
        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                mView.dismissDialog();
                if(jsonArray!=null){

                    ArrayList<GetawaysPackages> packagesArrayList = new ArrayList<>();

                    Type listType = new TypeToken<ArrayList<GetawaysPackages>>() {
                    }.getType();
                    packagesArrayList = new GsonBuilder().create().fromJson(String.valueOf(jsonArray), listType);

                    Log.d("response",packagesArrayList.toString());
                    mView.setGetawaysPackageCount(packagesArrayList);

                }else{

                   // Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.common_error_msg), false, null);

                    mView.retryGetawaysPackageCount();
                }



            }
        };
    }



    @Override
    public Response.ErrorListener commonErrorListener(final Context context) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                mView.dismissDialog();
            //    Utilities.commonErrorMessage(context, context.getString(R.string.app_name),context.getString(R.string.common_error_msg),false,null);

                mView.retryGetawaysPackageCount();

            }
        };
    }


}
