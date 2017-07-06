package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 08-06-2016.
 */
public class SubRatingsDto implements Parcelable {
    private String localized_name;
    private String rating_image_url;
    private String name;
    private double value;

    public String getLocalized_name() {
        return localized_name;
    }

    public void setLocalized_name(String localized_name) {
        this.localized_name = localized_name;
    }

    public String getRating_image_url() {
        return rating_image_url;
    }

    public void setRating_image_url(String rating_image_url) {
        this.rating_image_url = rating_image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    protected SubRatingsDto(Parcel in) {
        localized_name = in.readString();
        rating_image_url = in.readString();
        name = in.readString();
        value = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(localized_name);
        dest.writeString(rating_image_url);
        dest.writeString(name);
        dest.writeDouble(value);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SubRatingsDto> CREATOR = new Parcelable.Creator<SubRatingsDto>() {
        @Override
        public SubRatingsDto createFromParcel(Parcel in) {
            return new SubRatingsDto(in);
        }

        @Override
        public SubRatingsDto[] newArray(int size) {
            return new SubRatingsDto[size];
        }
    };
}