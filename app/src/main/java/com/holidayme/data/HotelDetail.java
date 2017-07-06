package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shaikh.salim on 6/9/2016.
 */
public class HotelDetail implements Parcelable {
    long HotelId;
    String HotelName;

    public long getHotelId() {
        return HotelId;
    }

    public void setHotelId(long hotelId) {
        HotelId = hotelId;
    }

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String hotelName) {
        HotelName = hotelName;
    }

    public HotelDetail() {
    }

    protected HotelDetail(Parcel in) {
        HotelId = in.readLong();
        HotelName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(HotelId);
        dest.writeString(HotelName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HotelDetail> CREATOR = new Parcelable.Creator<HotelDetail>() {
        @Override
        public HotelDetail createFromParcel(Parcel in) {
            return new HotelDetail(in);
        }

        @Override
        public HotelDetail[] newArray(int size) {
            return new HotelDetail[size];
        }
    };
}