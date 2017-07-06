package com.holidayme.staycation_calendar_mvp;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.holidayme.activities.R;
import com.holidayme.activities.util.Utilities;
import com.holidayme.managers.APIManager;

import org.json.JSONArray;

/**
 * Created by devrath.rathee on 18-04-2017.
 */

public class StaycationCalendarPresenter implements IStaycationCalendarPresenter
{
    Context mContext;
    IStaycationCalendarView mView;

    public StaycationCalendarPresenter(Context context,IStaycationCalendarView staycationCalendarView)
    {
        this.mContext = context;
        this.mView = staycationCalendarView;
    }

    @Override
    public void getAllocations(String url, String request, Context context) {
        mView.showProgressDialog();
        new APIManager<JSONArray>().getAllocations(url, request,
                allocationsSuccessListener(), commonErrorListener(), JSONArray.class, context);

    }

    @Override
    public Response.Listener<JSONArray> allocationsSuccessListener() {
        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                mView.hideProgressDialog();
                mView.setAllocationDetails(jsonArray);
            }
        };
    }

    @Override
    public Response.ErrorListener commonErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mView.hideProgressDialog();
                mView.getMonths();
               // Utilities.commonErrorMessage(mContext, mContext.getString(R.string.app_name),mContext.getString(R.string.common_error_msg),false,null);

            }
        };
    }
}
