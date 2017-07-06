package com.holidayme.login_mvp;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.util.Util;
import com.holidayme.Constants.Constant;
import com.holidayme.common.Log;
import com.holidayme.data.UserAccountDetailBean;
import com.holidayme.data.UserDTO;
import com.holidayme.managers.APIManager;
import com.holidayme.managers.SharedPreferenceManager;

/**
 * Created by devrath.rathee on 03-11-2016.
 */

public class LoginPresenter implements ILoginPresenter {

    Context mContext;
    String mToken;
    ILoginView mView;

    public LoginPresenter(Context context, ILoginView iLoginView) {
        mContext = context;
        mView = iLoginView;
    }

    @Override
    public void getUserDetails(Context context, String token) {
        mToken = token;
        SharedPreferenceManager.getInstance(context).savePreference("UserToken", token);
        new APIManager<UserAccountDetailBean>().getUserAccountInfo(Constant.GET_USER_DETAILS + token, userDetailsSuccessListener(),
                userDetailsErrorListener(), UserAccountDetailBean.class,
                context);
    }

    @Override
    public Response.Listener<UserAccountDetailBean> userDetailsSuccessListener() {
        return new Response.Listener<UserAccountDetailBean>() {
            @Override
            public void onResponse(UserAccountDetailBean userAccountDetailBean) {

                try {
                    SharedPreferenceManager.getInstance(mContext).savePreference("userName", userAccountDetailBean.getDisplayName());
                    SharedPreferenceManager.getInstance(mContext).savePreference("emailid", userAccountDetailBean.getEmail());
                    SharedPreferenceManager.getInstance(mContext).savePreference("phoneNumber", userAccountDetailBean.getPhone());
                    SharedPreferenceManager.getInstance(mContext).savePreference("UserSecret", userAccountDetailBean.getUserSecret());
                    SharedPreferenceManager.getInstance(mContext).saveIntegerPreference("AccountId",userAccountDetailBean.getAccountId());
                    SharedPreferenceManager.getInstance(mContext).savePreference("FirstName",userAccountDetailBean.getFirstName());
                    SharedPreferenceManager.getInstance(mContext).savePreference("LastName",userAccountDetailBean.getLastName());
                    SharedPreferenceManager.getInstance(mContext).savePreference("DisplayName",userAccountDetailBean.getDisplayName());

                    UserDTO.getUserDTO().setUserName(userAccountDetailBean.getDisplayName());
                    UserDTO.getUserDTO().setToken(mToken);
                    UserDTO.getUserDTO().setEmailID(userAccountDetailBean.getEmail());
                    UserDTO.getUserDTO().setUserSecret(userAccountDetailBean.getUserSecret());
                    UserDTO.getUserDTO().setAccountId(userAccountDetailBean.getAccountId());
                    UserDTO.getUserDTO().setFirstName(userAccountDetailBean.getFirstName());
                    UserDTO.getUserDTO().setLastName(userAccountDetailBean.getLastName());

                    mView.goToHomeScreen();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        };
    }

    @Override
    public Response.ErrorListener userDetailsErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("Error : ", volleyError.getMessage());
            }
        };
    }
}
