package com.holidayme.staycationindex;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.android.volley.Response;

import org.json.JSONArray;

/**
 * Created by arshad.shaikh on 3/22/2017.
 */

public interface IStayCationLandingPresenter {


    public  void getPackagesCount(Context context ,String url);
    Response.Listener<JSONArray> getGetawaysPackages( Context context);
    Response.ErrorListener commonErrorListener(Context context);


}
