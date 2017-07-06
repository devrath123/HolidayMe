package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by santosh.patar on 12-08-2015.
 */
public class Destination implements Parcelable {

    private String Key;
    private String Value;
    private String Category;
    private long RegionType;
    private long Count;
    private String DestinationName;
    private String checkInDate;
    private String checkOutDate;
    private String mRoomInfo;
    private double Latitude = 0;
    private double Logitude = 0;

    private boolean isCity; // this is  for Autocomplet Header item only

    private boolean isHotel; // this is for AutoComplet Header only, not use this to check, type of destination, user Catogory

    public Destination(String key, String value, String category, long regionType, long count, String destinationName, String checkInDate, String checkOutDate, String mRoomInfo, double latitude, double logitude, boolean isCity, boolean isHotel) {
        Key = key;
        Value = value;
        Category = category;
        RegionType = regionType;
        Count = count;
        DestinationName = destinationName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.mRoomInfo = mRoomInfo;
        Latitude = latitude;
        Logitude = logitude;
        this.isCity = isCity;
        this.isHotel = isHotel;
    }
    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public long getRegionType() {
        return RegionType;
    }

    public void setRegionType(long regionType) {
        RegionType = regionType;
    }

    public long getCount() {
        return Count;
    }

    public void setCount(long count) {
        Count = count;
    }

    public String getDestinationName() {
        return DestinationName;
    }

    public void setDestinationName(String destinationName) {
        DestinationName = destinationName;
    }

    public boolean isCity() {
        return isCity;
    }

    public void setIsCity(boolean isCity) {
        this.isCity = isCity;
    }

    public boolean isHotel() {
        return isHotel;
    }

    public void setIsHotel(boolean isHotel) {
        this.isHotel = isHotel;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }



    public String getmRoomInfo() {
        return mRoomInfo;
    }

    public void setmRoomInfo(String mRoomInfo) {
        this.mRoomInfo = mRoomInfo;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLogitude() {
        return Logitude;
    }

    public void setLogitude(double logitude) {
        Logitude = logitude;
    }


    public Destination(){}
    protected Destination(Parcel in) {
        Key = in.readString();
        Value = in.readString();
        Category = in.readString();
        RegionType = in.readLong();
        Count = in.readLong();
        DestinationName = in.readString();
        checkInDate = in.readString();
        checkOutDate = in.readString();
        mRoomInfo = in.readString();
        Latitude = in.readDouble();
        Logitude = in.readDouble();
        isCity = in.readByte() != 0x00;
        isHotel = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Key);
        dest.writeString(Value);
        dest.writeString(Category);
        dest.writeLong(RegionType);
        dest.writeLong(Count);
        dest.writeString(DestinationName);
        dest.writeString(checkInDate);
        dest.writeString(checkOutDate);
        dest.writeString(mRoomInfo);
        dest.writeDouble(Latitude);
        dest.writeDouble(Logitude);
        dest.writeByte((byte) (isCity ? 0x01 : 0x00));
        dest.writeByte((byte) (isHotel ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Destination> CREATOR = new Parcelable.Creator<Destination>() {
        @Override
        public Destination createFromParcel(Parcel in) {
            return new Destination(in);
        }

        @Override
        public Destination[] newArray(int size) {
            return new Destination[size];
        }
    };
}