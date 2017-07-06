package com.holidayme.selectlanguage_mvp;

import android.content.Context;

import com.holidayme.Constants.Constant;
import com.holidayme.activities.util.Utilities;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.data.UserDTO;
import com.holidayme.managers.SharedPreferenceManager;

/**
 * Created by devrath.rathee on 02-11-2016.
 */

public class SelectLanguagePresenter implements ISelectLanguagePresenter {

    ISelectLanguageView mSelectLanguageView;

    public SelectLanguagePresenter(ISelectLanguageView selectLanguageView)
    {
        mSelectLanguageView = selectLanguageView;
    }

    @Override
    public void setCurrency(String currency) {
        UserDTO.getUserDTO().setCurrency(currency);
        SharedPreferenceManager.getInstance((Context) mSelectLanguageView).savePreference(Constant.CURRENCY, currency);
    }

    @Override
    public void setLanguage(String language) {
        if (language.equals(Constant.ARABIC_LANGUAGE_CODE)) {
            UserDTO.getUserDTO().setLanguage(Constant.ARABIC_LANGUAGE_CODE);
            SharedPreferenceManager.getInstance((Context) mSelectLanguageView).savePreference(Constant.LANGUAGE, Constant.ARABIC_LANGUAGE_CODE);
            NetworkUtilities.setLocale(Constant.ARABIC_LANGUAGE_CODE, (Context) mSelectLanguageView);
        } else {
            UserDTO.getUserDTO().setLanguage(Constant.ENGLISH_LANGUAGE_CODE);
            SharedPreferenceManager.getInstance((Context) mSelectLanguageView).savePreference(Constant.LANGUAGE, Constant.ENGLISH_LANGUAGE_CODE);
            NetworkUtilities.setLocale(Constant.ENGLISH_LANGUAGE_CODE, (Context) mSelectLanguageView);
        }
    }

    @Override
    public void setAdapter(String currency) {
        mSelectLanguageView.setAdapterData(Utilities.getCurrencyList(currency));
    }

    @Override
    public void setFirstTimePreference(String key, boolean value) {
        SharedPreferenceManager.getInstance((Context) mSelectLanguageView).saveBooleanPreference(key, value);
        mSelectLanguageView.traverseToLogin();
    }
}
