package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 09-06-2016.
 */
public class StarDto implements Parcelable,Cloneable {
    private double Star;
    private int Cnt;
    boolean isCheck = false;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public double getStar() {
        return Star;
    }

    public void setStar(double star) {
        Star = star;
    }

    public int getCnt() {
        return Cnt;
    }

    public void setCnt(int cnt) {
        Cnt = cnt;
    }

    public StarDto() {
    }

    protected StarDto(Parcel in) {
        Star = in.readDouble();
        Cnt = in.readInt();
        isCheck = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(Star);
        dest.writeInt(Cnt);
        dest.writeByte((byte) (isCheck ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StarDto> CREATOR = new Parcelable.Creator<StarDto>() {
        @Override
        public StarDto createFromParcel(Parcel in) {
            return new StarDto(in);
        }

        @Override
        public StarDto[] newArray(int size) {
            return new StarDto[size];
        }
    };

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}