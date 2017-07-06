package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 06-07-2016.
 */

public class SpecialRequestsDto implements Parcelable {
    private Long Id;
    private String Description;
    boolean isCheck = false;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    protected SpecialRequestsDto(Parcel in) {
        Id = in.readByte() == 0x00 ? null : in.readLong();
        Description = in.readString();
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
        dest.writeString(Description);
        dest.writeByte((byte) (isCheck ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SpecialRequestsDto> CREATOR = new Parcelable.Creator<SpecialRequestsDto>() {
        @Override
        public SpecialRequestsDto createFromParcel(Parcel in) {
            return new SpecialRequestsDto(in);
        }

        @Override
        public SpecialRequestsDto[] newArray(int size) {
            return new SpecialRequestsDto[size];
        }
    };
}
