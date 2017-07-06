package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 08-06-2016.
 */
public class ReviewRatingCountDto implements Parcelable {
    private int Excellent;
    private int VeryGood;
    private int Average;
    private int Poor;
    private int Terrible;

    public int getExcellent() {
        return Excellent;
    }

    public void setExcellent(int excellent) {
        Excellent = excellent;
    }

    public int getVeryGood() {
        return VeryGood;
    }

    public void setVeryGood(int veryGood) {
        VeryGood = veryGood;
    }

    public int getAverage() {
        return Average;
    }

    public void setAverage(int average) {
        Average = average;
    }

    public int getPoor() {
        return Poor;
    }

    public void setPoor(int poor) {
        Poor = poor;
    }

    public int getTerrible() {
        return Terrible;
    }

    public void setTerrible(int terrible) {
        Terrible = terrible;
    }

    protected ReviewRatingCountDto(Parcel in) {
        Excellent = in.readInt();
        VeryGood = in.readInt();
        Average = in.readInt();
        Poor = in.readInt();
        Terrible = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Excellent);
        dest.writeInt(VeryGood);
        dest.writeInt(Average);
        dest.writeInt(Poor);
        dest.writeInt(Terrible);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ReviewRatingCountDto> CREATOR = new Parcelable.Creator<ReviewRatingCountDto>() {
        @Override
        public ReviewRatingCountDto createFromParcel(Parcel in) {
            return new ReviewRatingCountDto(in);
        }

        @Override
        public ReviewRatingCountDto[] newArray(int size) {
            return new ReviewRatingCountDto[size];
        }
    };
}