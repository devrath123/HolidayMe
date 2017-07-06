package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 06-07-2016.
 */

public class OccupanciesDto implements Parcelable {
    private int AdultsCount;
    private String ChildrenAges;
    private int ChildrensCount;
    private int RoomsNumber;

    public int getAdultsCount() {
        return AdultsCount;
    }

    public void setAdultsCount(int adultsCount) {
        AdultsCount = adultsCount;
    }

    public String getChildrenAges() {
        return ChildrenAges;
    }

    public void setChildrenAges(String childrenAges) {
        ChildrenAges = childrenAges;
    }

    public int getChildrensCount() {
        return ChildrensCount;
    }

    public void setChildrensCount(int childrensCount) {
        ChildrensCount = childrensCount;
    }

    public int getRoomsNumber() {
        return RoomsNumber;
    }

    public void setRoomsNumber(int roomsNumber) {
        RoomsNumber = roomsNumber;
    }

    protected OccupanciesDto(Parcel in) {
        AdultsCount = in.readInt();
        ChildrenAges = in.readString();
        ChildrensCount = in.readInt();
        RoomsNumber = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(AdultsCount);
        dest.writeString(ChildrenAges);
        dest.writeInt(ChildrensCount);
        dest.writeInt(RoomsNumber);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OccupanciesDto> CREATOR = new Parcelable.Creator<OccupanciesDto>() {
        @Override
        public OccupanciesDto createFromParcel(Parcel in) {
            return new OccupanciesDto(in);
        }

        @Override
        public OccupanciesDto[] newArray(int size) {
            return new OccupanciesDto[size];
        }
    };
}