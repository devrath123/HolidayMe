package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 17-08-2015.
 */
public class ReviewDTO implements Parcelable {
    private String PublisheDate;
    private String Title;
    private String Description;
    private RatingDTO Rating;

    public String getPublisheDate() {
        return PublisheDate;
    }

    public void setPublisheDate(String publisheDate) {
        PublisheDate = publisheDate;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public RatingDTO getRating() {
        return Rating;
    }

    public void setRating(RatingDTO rating) {
        Rating = rating;
    }



    protected ReviewDTO(Parcel in) {
        PublisheDate = in.readString();
        Title = in.readString();
        Description = in.readString();
        Rating = (RatingDTO) in.readValue(RatingDTO.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PublisheDate);
        dest.writeString(Title);
        dest.writeString(Description);
        dest.writeValue(Rating);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ReviewDTO> CREATOR = new Parcelable.Creator<ReviewDTO>() {
        @Override
        public ReviewDTO createFromParcel(Parcel in) {
            return new ReviewDTO(in);
        }

        @Override
        public ReviewDTO[] newArray(int size) {
            return new ReviewDTO[size];
        }
    };
}