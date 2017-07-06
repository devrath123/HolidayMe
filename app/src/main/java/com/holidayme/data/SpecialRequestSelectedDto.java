package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 12-07-2016.
 */

public class SpecialRequestSelectedDto implements Parcelable {
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SpecialRequestSelectedDto> CREATOR = new Parcelable.Creator<SpecialRequestSelectedDto>() {
        @Override
        public SpecialRequestSelectedDto createFromParcel(Parcel in) {
            return new SpecialRequestSelectedDto();
        }

        @Override
        public SpecialRequestSelectedDto[] newArray(int size) {
            return new SpecialRequestSelectedDto[size];
        }
    };
}
