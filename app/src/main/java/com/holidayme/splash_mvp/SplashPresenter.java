package com.holidayme.splash_mvp;

import android.content.Context;
import android.text.TextUtils;

import com.holidayme.Constants.Constant;
import com.holidayme.common.HmacSha1Signature;
import com.holidayme.data.UserDTO;
import com.holidayme.managers.SharedPreferenceManager;

/**
 * Created by devrath.rathee on 02-11-2016.
 */

public class SplashPresenter implements ISplashPresenter {

    @Override
    public void setTrackingAndSessionId(Context context) {

        if (UserDTO.getUserDTO().getUserTrackingId()!=null||UserDTO.getUserDTO().getUserTrackingId()!=""){
            String nOnce = HmacSha1Signature.nONCEGenerator();
            UserDTO.getUserDTO().setUserTrackingId(nOnce);
            SharedPreferenceManager.getInstance(context).savePreference("UserTrackingId",nOnce);
        }

        String nOnce = HmacSha1Signature.nONCEGenerator();
        UserDTO.getUserDTO().setSessionId(nOnce);
        SharedPreferenceManager.getInstance(context).savePreference("SessionId",nOnce);
    }

    @Override
    public void setDeepLinkDefault(Context context) {

        UserDTO.getUserDTO().setLanguage(Constant.ENGLISH_LANGUAGE_CODE);
        UserDTO.getUserDTO().setCurrency(Constant.CURRENCY_USD);

        SharedPreferenceManager.getInstance(context).savePreference(Constant.CURRENCY, Constant.CURRENCY_USD);
        SharedPreferenceManager.getInstance(context).savePreference(Constant.LANGUAGE, Constant.ENGLISH_LANGUAGE_CODE);
        SharedPreferenceManager.getInstance(context).saveBooleanPreference("isFirstTime", false);

    }

    @Override
    public void setUserDTO(Context context) {

        UserDTO.getUserDTO().setUserName(SharedPreferenceManager.getInstance(context).getPreference("userName", ""));
        UserDTO.getUserDTO().setEmailID(SharedPreferenceManager.getInstance(context).getPreference("emailid", ""));
        UserDTO.getUserDTO().setCurrency(SharedPreferenceManager.getInstance(context).getPreference(Constant.CURRENCY,Constant.CURRENCY_USD));
        UserDTO.getUserDTO().setLanguage(SharedPreferenceManager.getInstance(context).getPreference(Constant.LANGUAGE, Constant.ENGLISH_LANGUAGE_CODE));
        UserDTO.getUserDTO().setToken(SharedPreferenceManager.getInstance(context).getPreference("UserToken", null));
        UserDTO.getUserDTO().setUserSecret(SharedPreferenceManager.getInstance(context).getPreference("UserSecret", null));
        UserDTO.getUserDTO().setUserIP(SharedPreferenceManager.getInstance(context).getPreference("USERIP", null));

    }
}
