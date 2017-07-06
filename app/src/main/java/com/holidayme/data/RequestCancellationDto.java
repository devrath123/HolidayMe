package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 15-11-2016.
 */

public class RequestCancellationDto implements Parcelable {
    private String EmailAddress;
    private int EntityType;
    private long HolzooBookingId;

    public RequestCancellationDto(String emailAddress, int entityType, long holzooBookingId) {
        EmailAddress = emailAddress;
        EntityType = entityType;
        HolzooBookingId = holzooBookingId;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public int getEntityType() {
        return EntityType;
    }

    public void setEntityType(int entityType) {
        EntityType = entityType;
    }

    public long getHolzooBookingId() {
        return HolzooBookingId;
    }

    public void setHolzooBookingId(long holzooBookingId) {
        HolzooBookingId = holzooBookingId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.EmailAddress);
        dest.writeInt(this.EntityType);
        dest.writeLong(this.HolzooBookingId);
    }



    protected RequestCancellationDto(Parcel in) {
        this.EmailAddress = in.readString();
        this.EntityType = in.readInt();
        this.HolzooBookingId = in.readLong();
    }

    public static final Parcelable.Creator<RequestCancellationDto> CREATOR = new Parcelable.Creator<RequestCancellationDto>() {
        @Override
        public RequestCancellationDto createFromParcel(Parcel source) {
            return new RequestCancellationDto(source);
        }

        @Override
        public RequestCancellationDto[] newArray(int size) {
            return new RequestCancellationDto[size];
        }
    };
}
