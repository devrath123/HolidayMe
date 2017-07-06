package com.holidayme.data;

/**
 * Created by santosh.patar on 26-08-2015.
 */
public class NearByCityDto {

    private int CityId;
    private String CityName;
    private String CountryName;

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }
}

