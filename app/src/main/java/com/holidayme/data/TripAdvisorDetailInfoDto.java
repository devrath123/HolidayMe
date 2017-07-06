package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 08-06-2016.
 */
public class TripAdvisorDetailInfoDto implements Parcelable {
    private Long Id;
    private String WriteReview;
    private ReviewRatingCountDto ReviewRatingCount;
    private ArrayList<ReviewsDto> Reviews;
    private String WebUrl;
    private ArrayList<SubRatingsDto> Subratings;
    private int NumOfReviews;
    private ArrayList<TripTypesDto> TripTypes;
    private String RatingImageUrl;
    private double Rating;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getWriteReview() {
        return WriteReview;
    }

    public void setWriteReview(String writeReview) {
        WriteReview = writeReview;
    }

    public ArrayList<ReviewsDto> getReviews() {
        return Reviews;
    }

    public void setReviews(ArrayList<ReviewsDto> reviews) {
        Reviews = reviews;
    }

    public ReviewRatingCountDto getReviewRatingCount() {
        return ReviewRatingCount;
    }

    public void setReviewRatingCount(ReviewRatingCountDto reviewRatingCount) {
        ReviewRatingCount = reviewRatingCount;
    }

    public String getWebUrl() {
        return WebUrl;
    }

    public void setWebUrl(String webUrl) {
        WebUrl = webUrl;
    }

    public ArrayList<SubRatingsDto> getSubratings() {
        return Subratings;
    }

    public void setSubratings(ArrayList<SubRatingsDto> subratings) {
        Subratings = subratings;
    }

    public int getNumOfReviews() {
        return NumOfReviews;
    }

    public void setNumOfReviews(int numOfReviews) {
        NumOfReviews = numOfReviews;
    }

    public ArrayList<TripTypesDto> getTripTypes() {
        return TripTypes;
    }

    public void setTripTypes(ArrayList<TripTypesDto> tripTypes) {
        TripTypes = tripTypes;
    }

    public String getRatingImageUrl() {
        return RatingImageUrl;
    }

    public void setRatingImageUrl(String ratingImageUrl) {
        RatingImageUrl = ratingImageUrl;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }

    protected TripAdvisorDetailInfoDto(Parcel in) {
        Id = in.readByte() == 0x00 ? null : in.readLong();
        WriteReview = in.readString();
        ReviewRatingCount = (ReviewRatingCountDto) in.readValue(ReviewRatingCountDto.class.getClassLoader());
        if (in.readByte() == 0x01) {
            Reviews = new ArrayList<ReviewsDto>();
            in.readList(Reviews, ReviewsDto.class.getClassLoader());
        } else {
            Reviews = null;
        }
        WebUrl = in.readString();
        if (in.readByte() == 0x01) {
            Subratings = new ArrayList<SubRatingsDto>();
            in.readList(Subratings, SubRatingsDto.class.getClassLoader());
        } else {
            Subratings = null;
        }
        NumOfReviews = in.readInt();
        if (in.readByte() == 0x01) {
            TripTypes = new ArrayList<TripTypesDto>();
            in.readList(TripTypes, TripTypesDto.class.getClassLoader());
        } else {
            TripTypes = null;
        }
        RatingImageUrl = in.readString();
        Rating = in.readDouble();
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
        dest.writeString(WriteReview);
        dest.writeValue(ReviewRatingCount);
        if (Reviews == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Reviews);
        }
        dest.writeString(WebUrl);
        if (Subratings == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Subratings);
        }
        dest.writeInt(NumOfReviews);
        if (TripTypes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(TripTypes);
        }
        dest.writeString(RatingImageUrl);
        dest.writeDouble(Rating);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TripAdvisorDetailInfoDto> CREATOR = new Parcelable.Creator<TripAdvisorDetailInfoDto>() {
        @Override
        public TripAdvisorDetailInfoDto createFromParcel(Parcel in) {
            return new TripAdvisorDetailInfoDto(in);
        }

        @Override
        public TripAdvisorDetailInfoDto[] newArray(int size) {
            return new TripAdvisorDetailInfoDto[size];
        }
    };
}