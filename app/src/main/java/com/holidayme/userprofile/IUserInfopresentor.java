package com.holidayme.userprofile;

import android.content.Context;

import com.android.volley.Response;
import com.holidayme.response.GetDestinationForHotelResponse;
import com.holidayme.response.GetUserProfileInformationResponse;

/**
 * Created by arshad.shaikh on 11/4/2016.
 */

public interface IUserInfopresentor {


    void getUserInfo(String url, String request, Context context);
    Response.Listener<GetUserProfileInformationResponse> getUserinfodata();
    Response.ErrorListener getUserInfoError();

}
