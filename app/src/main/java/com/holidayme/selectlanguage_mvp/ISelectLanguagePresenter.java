package com.holidayme.selectlanguage_mvp;

/**
 * Created by devrath.rathee on 02-11-2016.
 */

public interface ISelectLanguagePresenter {

    void setCurrency(String currency);
    void setLanguage(String language);
    void setAdapter(String currency);
    void setFirstTimePreference(String key,boolean value);

}
