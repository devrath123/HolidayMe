package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by supriya.sakore on 06-07-2016.
 */

public class CodAvailableDto implements Parcelable {
    private String CountryCode;
    private String SupplierCityName;
    private String SupplierCityId;
    private String CityId;
    private String CityName;
    private String SupplierAreas;
    private HashMap<String, String> CodCities;
    private boolean IsCod;

    public HashMap<String, String> getCodCities() {
        return CodCities;
    }

    public void setCodCities(HashMap<String, String> codCities) {
        CodCities = codCities;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getSupplierCityName() {
        return SupplierCityName;
    }

    public void setSupplierCityName(String supplierCityName) {
        SupplierCityName = supplierCityName;
    }

    public String getSupplierCityId() {
        return SupplierCityId;
    }

    public void setSupplierCityId(String supplierCityId) {
        SupplierCityId = supplierCityId;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getSupplierAreas() {
        return SupplierAreas;
    }

    public void setSupplierAreas(String supplierAreas) {
        SupplierAreas = supplierAreas;
    }

    public boolean isCod() {
        return IsCod;
    }

    public void setCod(boolean cod) {
        IsCod = cod;
    }

    protected CodAvailableDto(Parcel in) {
        CountryCode = in.readString();
        SupplierCityName = in.readString();
        SupplierCityId = in.readString();
        CityId = in.readString();
        CityName = in.readString();
        SupplierAreas = in.readString();
        CodCities = (HashMap) in.readValue(HashMap.class.getClassLoader());
        IsCod = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CountryCode);
        dest.writeString(SupplierCityName);
        dest.writeString(SupplierCityId);
        dest.writeString(CityId);
        dest.writeString(CityName);
        dest.writeString(SupplierAreas);
        dest.writeValue(CodCities);
        dest.writeByte((byte) (IsCod ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CodAvailableDto> CREATOR = new Parcelable.Creator<CodAvailableDto>() {
        @Override
        public CodAvailableDto createFromParcel(Parcel in) {
            return new CodAvailableDto(in);
        }

        @Override
        public CodAvailableDto[] newArray(int size) {
            return new CodAvailableDto[size];
        }
    };
}