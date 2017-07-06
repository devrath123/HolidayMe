package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 08-06-2016.
 */
public class TripTypesDto implements Parcelable {
    private String localized_name;
    private String name;
    private int value;

    public String getLocalized_name() {
        return localized_name;
    }

    public void setLocalized_name(String localized_name) {
        this.localized_name = localized_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    protected TripTypesDto(Parcel in) {
        localized_name = in.readString();
        name = in.readString();
        value = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(localized_name);
        dest.writeString(name);
        dest.writeInt(value);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TripTypesDto> CREATOR = new Parcelable.Creator<TripTypesDto>() {
        @Override
        public TripTypesDto createFromParcel(Parcel in) {
            return new TripTypesDto(in);
        }

        @Override
        public TripTypesDto[] newArray(int size) {
            return new TripTypesDto[size];
        }
    };
}