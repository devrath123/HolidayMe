package com.holidayme.request;

/**
 * Created by shaikh.salim on 6/8/2016.
 */
public class AutoCompleteRequest {

    int CityCount;
    String Key;
    String HotelNameSearchKey;
    String LanguageCode;
    long CityId;
    int AreaCount;

    public int getAreaCount() {
        return AreaCount;
    }

    public void setAreaCount(int areaCount) {
        AreaCount = areaCount;
    }

    public String getHotelNameSearchKey() {
        return HotelNameSearchKey;
    }

    public void setHotelNameSearchKey(String hotelNameSearchKey) {
        HotelNameSearchKey = hotelNameSearchKey;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public int getCityCount() {
        return CityCount;
    }

    public void setCityCount(int cityCount) {
        CityCount = cityCount;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public long getCityId() {
        return CityId;
    }

    public void setCityId(long cityId) {
        CityId = cityId;
    }
}
