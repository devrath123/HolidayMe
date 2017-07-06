package com.holidayme.login_mvp;

import android.content.Context;

import com.android.volley.Response;
import com.holidayme.data.UserAccountDetailBean;

/**
 * Created by devrath.rathee on 03-11-2016.
 */

public interface ILoginPresenter {

    void getUserDetails(Context context,String token);
    Response.Listener<UserAccountDetailBean> userDetailsSuccessListener();
    Response.ErrorListener userDetailsErrorListener();
}
