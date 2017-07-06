
package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shaikh.salim on 6/9/2016.
 */
public class AutoCompleteData implements Parcelable {
    String CityId;
    String DisplayName;
    String Title;
    long HotelId;
    boolean isHotel;
    boolean isCity;
    boolean isHotelHeader;
    boolean isCityHeader;
    boolean isArea;

    public boolean isArea() {
        return isArea;
    }

    public void setArea(boolean area) {
        isArea = area;
    }

    public boolean isAreaHeader() {
        return isAreaHeader;
    }

    public void setAreaHeader(boolean areaHeader) {
        isAreaHeader = areaHeader;
    }

    boolean isAreaHeader;

    public AutoCompleteData(String CityId, String DisplayName, String Title, long HotelId, boolean isHotel, boolean isCity,boolean isArea) {
        this.CityId = CityId;
        this.DisplayName = DisplayName;
        this.Title = Title;
        this.HotelId = HotelId;
        this.isHotel = isHotel;
        this.isCity = isCity;
        this.isArea=isArea;
    }

    protected AutoCompleteData(Parcel in) {


        CityId = in.readString();
        DisplayName = in.readString();
        Title = in.readString();
        HotelId = in.readLong();
        isHotel = in.readByte() != 0x00;
        isCity = in.readByte() != 0x00;
        isArea= in.readByte() != 0x00;
        isHotelHeader = in.readByte() != 0x00;
        isCityHeader = in.readByte() != 0x00;
        isAreaHeader=in.readByte() != 0x00;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CityId);
        dest.writeString(DisplayName);
        dest.writeString(Title);
        dest.writeLong(HotelId);
        dest.writeByte((byte) (isHotel ? 0x01 : 0x00));
        dest.writeByte((byte) (isCity ? 0x01 : 0x00));
        dest.writeByte((byte) (isArea ? 0x01 : 0x00));
        dest.writeByte((byte) (isHotelHeader ? 0x01 : 0x00));
        dest.writeByte((byte) (isCityHeader ? 0x01 : 0x00));
        dest.writeByte((byte) (isAreaHeader ? 0x01 : 0x00));


    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<AutoCompleteData> CREATOR = new Parcelable.Creator<AutoCompleteData>() {
        @Override
        public AutoCompleteData createFromParcel(Parcel in) {
            return new AutoCompleteData(in);
        }

        @Override
        public AutoCompleteData[] newArray(int size) {
            return new AutoCompleteData[size];
        }
    };

    public boolean isHotelHeader() {
        return isHotelHeader;
    }

    public void setHotelHeader(boolean hotelHeader) {
        isHotelHeader = hotelHeader;
    }

    public boolean isCityHeader() {
        return isCityHeader;
    }

    public void setCityHeader(boolean cityHeader) {
        isCityHeader = cityHeader;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public long getHotelId() {
        return HotelId;
    }

    public void setHotelId(long hotelId) {
        HotelId = hotelId;
    }

    public boolean isHotel() {
        return isHotel;
    }

    public void setHotel(boolean hotel) {
        isHotel = hotel;
    }

    public boolean isCity() {
        return isCity;
    }

    public void setCity(boolean city) {
        isCity = city;
    }
}
