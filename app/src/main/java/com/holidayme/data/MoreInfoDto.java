package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 07-06-2016.
 */
public class MoreInfoDto implements Parcelable {
    private String LDesc;
    private ArrayList<DImgDto> DImg;
    private String HotelPolicy;
    private double StartFromPrice;
    private String CheckInInstructions;

    public String getLDesc() {
        return LDesc;
    }

    public void setLDesc(String LDesc) {
        this.LDesc = LDesc;
    }

    public ArrayList<DImgDto> getDImg() {
        return DImg;
    }

    public void setDImg(ArrayList<DImgDto> DImg) {
        this.DImg = DImg;
    }

    public String getHotelPolicy() {
        return HotelPolicy;
    }

    public void setHotelPolicy(String hotelPolicy) {
        HotelPolicy = hotelPolicy;
    }


    public double getStartFromPrice() {
        return StartFromPrice;
    }

    public void setStartFromPrice(double startFromPrice) {
        StartFromPrice = startFromPrice;
    }

    public String getCheckInInstructions() {
        return CheckInInstructions;
    }

    public void setCheckInInstructions(String checkInInstructions) {
        CheckInInstructions = checkInInstructions;
    }

    protected MoreInfoDto(Parcel in) {
        LDesc = in.readString();
        if (in.readByte() == 0x01) {
            DImg = new ArrayList<DImgDto>();
            in.readList(DImg, DImgDto.class.getClassLoader());
        } else {
            DImg = null;
        }
        HotelPolicy = in.readString();

        StartFromPrice = in.readDouble();
        CheckInInstructions = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(LDesc);
        if (DImg == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(DImg);
        }
        dest.writeString(HotelPolicy);
        dest.writeDouble(StartFromPrice);
        dest.writeString(CheckInInstructions);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MoreInfoDto> CREATOR = new Parcelable.Creator<MoreInfoDto>() {
        @Override
        public MoreInfoDto createFromParcel(Parcel in) {
            return new MoreInfoDto(in);
        }

        @Override
        public MoreInfoDto[] newArray(int size) {
            return new MoreInfoDto[size];
        }
    };
}