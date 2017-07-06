package com.holidayme.request;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by santosh.patar on 29-07-2015.
 */
public class PropertySearchRequestAccommodations implements Parcelable {

    private int AdultsCount;
    private int ChildCount;
    private ArrayList<Integer> ChildrenAges;


    public int getAdultsCount() {
        return AdultsCount;
    }

    public int getChildCount() {
        return ChildCount;
    }

    public void setChildCount(int childCount) {
        ChildCount = childCount;
    }

    public void setAdultsCount(int adultsCount) {
        AdultsCount = adultsCount;
    }

    public ArrayList<Integer> getChildrenAges() {
        return ChildrenAges;
    }

    public void setChildrenAges(ArrayList<Integer> childrenAges) {
        ChildrenAges = childrenAges;
    }

    public PropertySearchRequestAccommodations(){}
    protected PropertySearchRequestAccommodations(Parcel in) {
        AdultsCount = in.readInt();
        ChildCount = in.readInt();
        if (in.readByte() == 0x01) {
            ChildrenAges = new ArrayList<Integer>();
            in.readList(ChildrenAges, Integer.class.getClassLoader());
        } else {
            ChildrenAges = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(AdultsCount);
        dest.writeInt(ChildCount);
        if (ChildrenAges == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ChildrenAges);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PropertySearchRequestAccommodations> CREATOR = new Parcelable.Creator<PropertySearchRequestAccommodations>() {
        @Override
        public PropertySearchRequestAccommodations createFromParcel(Parcel in) {
            return new PropertySearchRequestAccommodations(in);
        }

        @Override
        public PropertySearchRequestAccommodations[] newArray(int size) {
            return new PropertySearchRequestAccommodations[size];
        }
    };
}