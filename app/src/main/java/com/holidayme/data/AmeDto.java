package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 09-06-2016.
 */
public class AmeDto implements Parcelable,Cloneable {
    private Long Id;
    private String Ttl;
    private String Url;
    private String Cnt;
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

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getTtl() {
        return Ttl;
    }

    public void setTtl(String ttl) {
        Ttl = ttl;
    }

    public String getCnt() {
        return Cnt;
    }

    public void setCnt(String cnt) {
        Cnt = cnt;
    }

    protected AmeDto(Parcel in) {
        Id = in.readLong();
        Ttl = in.readString();
        Url = in.readString();
        Cnt = in.readString();
        isCheck = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(Id);
        dest.writeString(Ttl);
        dest.writeString(Url);
        dest.writeString(Cnt);
        dest.writeByte((byte) (isCheck ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<AmeDto> CREATOR = new Parcelable.Creator<AmeDto>() {
        @Override
        public AmeDto createFromParcel(Parcel in) {
            return new AmeDto(in);
        }

        @Override
        public AmeDto[] newArray(int size) {
            return new AmeDto[size];
        }
    };

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}