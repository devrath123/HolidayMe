package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 08-06-2016.
 */
public class DImgDto implements Parcelable {


    private Long Id;
    private String  DUrl;
    private String TUrl;
    private String Capt;
    private String Cat;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getDUrl() {
        return DUrl;
    }

    public void setDUrl(String DUrl) {
        this.DUrl = DUrl;
    }

    public String getTUrl() {
        return TUrl;
    }

    public void setTUrl(String TUrl) {
        this.TUrl = TUrl;
    }

    public String getCat() {
        return Cat;
    }

    public void setCat(String cat) {
        Cat = cat;
    }

    public String getCapt() {
        return Capt;
    }

    public void setCapt(String capt) {
        Capt = capt;
    }

    protected DImgDto(Parcel in) {
        Id = in.readByte() == 0x00 ? null : in.readLong();
        DUrl = in.readString();
        TUrl = in.readString();
        Capt = in.readString();
        Cat = in.readString();
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
        dest.writeString(DUrl);
        dest.writeString(Capt);
        dest.writeString(TUrl);
        dest.writeString(Cat);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DImgDto> CREATOR = new Parcelable.Creator<DImgDto>() {
        @Override
        public DImgDto createFromParcel(Parcel in) {
            return new DImgDto(in);
        }

        @Override
        public DImgDto[] newArray(int size) {
            return new DImgDto[size];
        }
    };
}