package com.holidayme.userprofile;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.holidayme.common.Log;
import com.holidayme.managers.APIManager;
import com.holidayme.response.GetUserProfileInformationResponse;

/**
 * Created by arshad.shaikh on 11/4/2016.
 */

public class UserPresentor implements IUserInfopresentor {


    @Override
    public void getUserInfo(String url, String request, Context context) {

        new APIManager<GetUserProfileInformationResponse>().getUserAccountInfo(url,getUserinfodata(),getUserInfoError(),GetUserProfileInformationResponse.class,context);

    }

    @Override
    public Response.Listener<GetUserProfileInformationResponse> getUserinfodata() {
        return new Response.Listener<GetUserProfileInformationResponse>() {
            @Override
            public void onResponse(GetUserProfileInformationResponse getUserProfileInformationResponse) {

                Log.e("profile",""+getUserProfileInformationResponse);
            }
        };
    }

    @Override
    public Response.ErrorListener getUserInfoError() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        };
    }
}
