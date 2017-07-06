package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.holidayme.response.HotelListingInfoResponse;

/**
 * Created by supriya.sakore on 09-06-2016.
 */
public class HotelsDto implements Parcelable,Cloneable {

    private long Id;
    private String Ttl;
    private double Price;
    private double Discount;
    private double SlashedPrice;
    private BasicInfoListingDto BasicInfo;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTtl() {
        return Ttl;
    }

    public void setTtl(String ttl) {
        Ttl = ttl;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getSlashedPrice() {
        return SlashedPrice;
    }

    public void setSlashedPrice(double slashedPrice) {
        SlashedPrice = slashedPrice;
    }

    public BasicInfoListingDto getBasicInfo() {
        return BasicInfo;
    }

    public void setBasicInfo(BasicInfoListingDto basicInfo) {
        BasicInfo = basicInfo;
    }

    public HotelsDto() {

    }
    protected HotelsDto(Parcel in) {
        Id = in.readLong();
        Ttl = in.readString();
        Price = in.readDouble();
        Discount = in.readDouble();
        SlashedPrice = in.readDouble();
        BasicInfo = (BasicInfoListingDto) in.readValue(BasicInfoListingDto.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(Id);
        dest.writeString(Ttl);
        dest.writeDouble(Price);
        dest.writeDouble(Discount);
        dest.writeDouble(SlashedPrice);
        dest.writeValue(BasicInfo);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HotelsDto> CREATOR = new Parcelable.Creator<HotelsDto>() {
        @Override
        public HotelsDto createFromParcel(Parcel in) {
            return new HotelsDto(in);
        }

        @Override
        public HotelsDto[] newArray(int size) {
            return new HotelsDto[size];
        }
    };

    @Override
    public Object clone() throws CloneNotSupportedException {
        HotelsDto cloned = (HotelsDto) super.clone();
        cloned.setBasicInfo((BasicInfoListingDto) cloned.getBasicInfo().clone());
        return cloned;
    }
}