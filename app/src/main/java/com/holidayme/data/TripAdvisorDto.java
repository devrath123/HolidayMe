package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 07-06-2016.
 */
public class TripAdvisorDto implements Parcelable,Cloneable {
    private double Rating;
    private String RatImgUrl;
    private int RevCnt;
    private String RevCntUrl;

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }

    public String getRatImgUrl() {
        return RatImgUrl;
    }

    public void setRatImgUrl(String ratImgUrl) {
        RatImgUrl = ratImgUrl;
    }

    public int getRevCnt() {
        return RevCnt;
    }

    public void setRevCnt(int revCnt) {
        RevCnt = revCnt;
    }

    public String getRevCntUrl() {
        return RevCntUrl;
    }

    public void setRevCntUrl(String revCntUrl) {
        RevCntUrl = revCntUrl;
    }

    protected TripAdvisorDto(Parcel in) {
        Rating = in.readDouble();
        RatImgUrl = in.readString();
        RevCnt = in.readInt();
        RevCntUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(Rating);
        dest.writeString(RatImgUrl);
        dest.writeInt(RevCnt);
        dest.writeString(RevCntUrl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TripAdvisorDto> CREATOR = new Parcelable.Creator<TripAdvisorDto>() {
        @Override
        public TripAdvisorDto createFromParcel(Parcel in) {
            return new TripAdvisorDto(in);
        }

        @Override
        public TripAdvisorDto[] newArray(int size) {
            return new TripAdvisorDto[size];
        }
    };

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}