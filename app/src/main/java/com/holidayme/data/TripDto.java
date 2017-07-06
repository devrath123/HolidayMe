package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 09-06-2016.
 */
public class TripDto implements Parcelable,Cloneable {
    private double Rat;
    private int Cnt;
    boolean isCheck = false;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }


    public double getRat() {
        return Rat;
    }

    public void setRat(double rat) {
        Rat = rat;
    }

    public int getCnt() {
        return Cnt;
    }

    public void setCnt(int cnt) {
        Cnt = cnt;
    }

    protected TripDto(Parcel in) {
        Rat = in.readDouble();
        Cnt = in.readInt();
        isCheck = in.readByte() != 0x00;
    }
    public TripDto() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(Rat);
        dest.writeInt(Cnt);
        dest.writeByte((byte) (isCheck ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TripDto> CREATOR = new Parcelable.Creator<TripDto>() {
        @Override
        public TripDto createFromParcel(Parcel in) {
            return new TripDto(in);
        }

        @Override
        public TripDto[] newArray(int size) {
            return new TripDto[size];
        }
    };

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}