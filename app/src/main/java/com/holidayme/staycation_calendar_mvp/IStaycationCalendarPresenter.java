package com.holidayme.staycation_calendar_mvp;

import android.content.Context;

import com.android.volley.Response;

import org.json.JSONArray;

/**
 * Created by devrath.rathee on 18-04-2017.
 */

public interface IStaycationCalendarPresenter {

    void getAllocations(String url, String request, Context context);
    Response.Listener<JSONArray> allocationsSuccessListener();

    Response.ErrorListener commonErrorListener();
}
