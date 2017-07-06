package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 09-06-2016.
 */
public class OccupancyDto implements Parcelable {
    private int NoOfAdults;
    private ArrayList<Integer> ChildAges;

    public OccupancyDto(int adultsCount, ArrayList<Integer> kidsAge) {

        NoOfAdults=adultsCount;
        ChildAges=kidsAge;
    }

    public int getNoOfAdults() {
        return NoOfAdults;
    }

    public void setNoOfAdults(int noOfAdults) {
        NoOfAdults = noOfAdults;
    }

    public ArrayList<Integer> getChildAges() {
        return ChildAges;
    }

    public void setChildAges(ArrayList<Integer> childAges) {
        ChildAges = childAges;
    }


    public OccupancyDto() {

    }

//    public OccupancyDto(int childAges, ArrayList<Integer> noOfAdults) {
//        ChildAges = childAges;
//        NoOfAdults = noOfAdults;
//    }

    protected OccupancyDto(Parcel in) {
        NoOfAdults = in.readInt();
        if (in.readByte() == 0x01) {
            ChildAges = new ArrayList<Integer>();
            in.readList(ChildAges, Integer.class.getClassLoader());
        } else {
            ChildAges = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(NoOfAdults);
        if (ChildAges == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ChildAges);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OccupancyDto> CREATOR = new Parcelable.Creator<OccupancyDto>() {
        @Override
        public OccupancyDto createFromParcel(Parcel in) {
            return new OccupancyDto(in);
        }

        @Override
        public OccupancyDto[] newArray(int size) {
            return new OccupancyDto[size];
        }
    };
}
