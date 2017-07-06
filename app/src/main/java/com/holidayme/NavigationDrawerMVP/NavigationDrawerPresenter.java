package com.holidayme.NavigationDrawerMVP;

import android.content.Context;

import com.holidayme.Constants.Constant;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.data.UserDTO;
import com.holidayme.managers.SharedPreferenceManager;

/**
 * Created by supriya.sakore on 30-09-2016.
 */

public class NavigationDrawerPresenter implements INavigationDrawerPresenter{
    INavigationDrawerView mView;
    Context mContext;

    public  NavigationDrawerPresenter(INavigationDrawerView view, Context context){
        mView=view;
        mContext=context;
    }

    @Override
    public void setCurrency(String currency) {
        UserDTO.getUserDTO().setCurrency(currency);
        SharedPreferenceManager.getInstance(mContext).savePreference(Constant.CURRENCY, currency);

    }

    @Override
    public void setLanguage(String language) {

        if (language.equals(Constant.ARABIC_LANGUAGE_CODE)) {
            UserDTO.getUserDTO().setLanguage(Constant.ARABIC_LANGUAGE_CODE);
            SharedPreferenceManager.getInstance(mContext).savePreference(Constant.LANGUAGE, Constant.ARABIC_LANGUAGE_CODE);
            NetworkUtilities.setLocale(Constant.ARABIC_LANGUAGE_CODE, (mContext));
        } else {
            UserDTO.getUserDTO().setLanguage(Constant.ENGLISH_LANGUAGE_CODE);
            SharedPreferenceManager.getInstance(mContext).savePreference(Constant.LANGUAGE, Constant.ENGLISH_LANGUAGE_CODE);
            NetworkUtilities.setLocale(Constant.ENGLISH_LANGUAGE_CODE, (mContext));
        }
    }


    @Override
    public void setUserInfo() {

        SharedPreferenceManager.getInstance(mContext).savePreference("userName", "");
        SharedPreferenceManager.getInstance(mContext).savePreference("emailid", "");
        SharedPreferenceManager.getInstance(mContext).savePreference("UserToken", "");
        UserDTO.getUserDTO().setUserName("");
        UserDTO.getUserDTO().setEmailID("");
        UserDTO.getUserDTO().setToken("");
    }



}
