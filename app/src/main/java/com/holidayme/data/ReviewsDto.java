package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 08-06-2016.
 */
public class ReviewsDto implements Parcelable {
    private Long id;
    private String lang;
    private Long location_id;
    private String published_date;
    private double rating;
    private int helpful_votes;
    private String rating_image_url;
    private String url;
    private String trip_type;
    private String travel_date;
    private String text;
    private String title;

    public ReviewsDto()
    {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Long getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Long location_id) {
        this.location_id = location_id;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getHelpful_votes() {
        return helpful_votes;
    }

    public void setHelpful_votes(int helpful_votes) {
        this.helpful_votes = helpful_votes;
    }

    public String getRating_image_url() {
        return rating_image_url;
    }

    public void setRating_image_url(String rating_image_url) {
        this.rating_image_url = rating_image_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTrip_type() {
        return trip_type;
    }

    public void setTrip_type(String trip_type) {
        this.trip_type = trip_type;
    }

    public String getTravel_date() {
        return travel_date;
    }

    public void setTravel_date(String travel_date) {
        this.travel_date = travel_date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    protected ReviewsDto(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readLong();
        lang = in.readString();
        location_id = in.readByte() == 0x00 ? null : in.readLong();
        published_date = in.readString();
        rating = in.readDouble();
        helpful_votes = in.readInt();
        rating_image_url = in.readString();
        url = in.readString();
        trip_type = in.readString();
        travel_date = in.readString();
        text = in.readString();
        title = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(id);
        }
        dest.writeString(lang);
        if (location_id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(location_id);
        }
        dest.writeString(published_date);
        dest.writeDouble(rating);
        dest.writeInt(helpful_votes);
        dest.writeString(rating_image_url);
        dest.writeString(url);
        dest.writeString(trip_type);
        dest.writeString(travel_date);
        dest.writeString(text);
        dest.writeString(title);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ReviewsDto> CREATOR = new Parcelable.Creator<ReviewsDto>() {
        @Override
        public ReviewsDto createFromParcel(Parcel in) {
            return new ReviewsDto(in);
        }

        @Override
        public ReviewsDto[] newArray(int size) {
            return new ReviewsDto[size];
        }
    };
}