package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shaikh.salim on 6/8/2016.
 */
public class CityDetail implements Parcelable {
    int Entity;
    String DisplayName;
    String Title;
    int Id;
    String Code;

    public int getEntity() {
        return Entity;
    }

    public void setEntity(int entity) {
        Entity = entity;
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

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    protected CityDetail(Parcel in) {
        Entity = in.readInt();
        DisplayName = in.readString();
        Title = in.readString();
        Id = in.readInt();
        Code = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Entity);
        dest.writeString(DisplayName);
        dest.writeString(Title);
        dest.writeInt(Id);
        dest.writeString(Code);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CityDetail> CREATOR = new Parcelable.Creator<CityDetail>() {
        @Override
        public CityDetail createFromParcel(Parcel in) {
            return new CityDetail(in);
        }

        @Override
        public CityDetail[] newArray(int size) {
            return new CityDetail[size];
        }
    };
}
