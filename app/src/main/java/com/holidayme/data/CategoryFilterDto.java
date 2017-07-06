package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 21-03-2017.
 */

public class CategoryFilterDto implements Parcelable {
    private long Id;
    private String Title;
    private boolean IsCheck;

    public CategoryFilterDto(long id, String title) {
        Id = id;
        Title = title;

    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public boolean isCheck() {
        return IsCheck;
    }

    public void setCheck(boolean check) {
        IsCheck = check;
    }

    public CategoryFilterDto() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.Id);
        dest.writeString(this.Title);
        dest.writeByte(this.IsCheck ? (byte) 1 : (byte) 0);
    }

    protected CategoryFilterDto(Parcel in) {
        this.Id = in.readLong();
        this.Title = in.readString();
        this.IsCheck = in.readByte() != 0;
    }

    public static final Creator<CategoryFilterDto> CREATOR = new Creator<CategoryFilterDto>() {
        @Override
        public CategoryFilterDto createFromParcel(Parcel source) {
            return new CategoryFilterDto(source);
        }

        @Override
        public CategoryFilterDto[] newArray(int size) {
            return new CategoryFilterDto[size];
        }
    };
}
