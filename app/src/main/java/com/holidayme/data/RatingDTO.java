package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by santosh.patar on 01-09-2015.
 */
public class RatingDTO implements Parcelable {
    private double Value;
    private String Url;

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        Value = value;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }



    protected RatingDTO(Parcel in) {
        Value = in.readDouble();
        Url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(Value);
        dest.writeString(Url);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RatingDTO> CREATOR = new Parcelable.Creator<RatingDTO>() {
        @Override
        public RatingDTO createFromParcel(Parcel in) {
            return new RatingDTO(in);
        }

        @Override
        public RatingDTO[] newArray(int size) {
            return new RatingDTO[size];
        }
    };
}