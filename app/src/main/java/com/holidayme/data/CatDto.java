package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 09-06-2016.
 */
public class CatDto implements Parcelable,Cloneable {
    private Long Id;
    private String Ttl;
    private int Cnt;
    boolean isCheck = false;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTtl() {
        return Ttl;
    }

    public void setTtl(String ttl) {
        Ttl = ttl;
    }

    public int getCnt() {
        return Cnt;
    }

    public void setCnt(int cnt) {
        Cnt = cnt;
    }

    protected CatDto(Parcel in) {
        Id = in.readByte() == 0x00 ? null : in.readLong();
        Ttl = in.readString();
        Cnt = in.readInt();
        isCheck = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (Id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(Id);
        }
        dest.writeString(Ttl);
        dest.writeInt(Cnt);
        dest.writeByte((byte) (isCheck ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CatDto> CREATOR = new Parcelable.Creator<CatDto>() {
        @Override
        public CatDto createFromParcel(Parcel in) {
            return new CatDto(in);
        }

        @Override
        public CatDto[] newArray(int size) {
            return new CatDto[size];
        }
    };

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}