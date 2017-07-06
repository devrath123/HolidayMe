package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shaikh.salim on 8/2/2016.
 */

public class CurrentLocationDetail implements Parcelable {
    String country_code;
    String country_name;

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    protected CurrentLocationDetail(Parcel in) {
        country_code = in.readString();
        country_name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(country_code);
        dest.writeString(country_name);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CurrentLocationDetail> CREATOR = new Parcelable.Creator<CurrentLocationDetail>() {
        @Override
        public CurrentLocationDetail createFromParcel(Parcel in) {
            return new CurrentLocationDetail(in);
        }

        @Override
        public CurrentLocationDetail[] newArray(int size) {
            return new CurrentLocationDetail[size];
        }
    };
}
