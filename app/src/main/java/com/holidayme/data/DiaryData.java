package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shaikh.salim on 6/30/2016.
 */
public class DiaryData implements Parcelable {
    String title;
    String description;
    String imageUrl;

    protected DiaryData(Parcel in) {
        title = in.readString();
        description = in.readString();
        imageUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(imageUrl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DiaryData> CREATOR = new Parcelable.Creator<DiaryData>() {
        @Override
        public DiaryData createFromParcel(Parcel in) {
            return new DiaryData(in);
        }

        @Override
        public DiaryData[] newArray(int size) {
            return new DiaryData[size];
        }
    };

    public DiaryData(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
