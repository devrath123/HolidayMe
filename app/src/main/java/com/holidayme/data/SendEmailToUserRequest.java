package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 15-11-2016.
 */

public class SendEmailToUserRequest implements Parcelable {

    private long BookingEntityId,BookingId;
    private int EntityTypes;
    private String UserId,LanguageCode;
    private String UserMobileNumber;

    public long getBookingEntityId() {
        return BookingEntityId;
    }

    public void setBookingEntityId(long bookingEntityId) {
        BookingEntityId = bookingEntityId;
    }

    public long getBookingId() {
        return BookingId;
    }

    public void setBookingId(long bookingId) {
        BookingId = bookingId;
    }

    public int getEntityTypes() {
        return EntityTypes;
    }

    public void setEntityTypes(int entityTypes) {
        EntityTypes = entityTypes;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public String getUserMobileNumber() {
        return UserMobileNumber;
    }

    public void setUserMobileNumber(String userMobileNumber) {
        UserMobileNumber = userMobileNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.BookingEntityId);
        dest.writeLong(this.BookingId);
        dest.writeInt(this.EntityTypes);
        dest.writeString(this.UserId);
        dest.writeString(this.LanguageCode);
        dest.writeString(this.UserMobileNumber);
    }

    public SendEmailToUserRequest() {
    }

    protected SendEmailToUserRequest(Parcel in) {
        this.BookingEntityId = in.readLong();
        this.BookingId = in.readLong();
        this.EntityTypes = in.readInt();
        this.UserId = in.readString();
        this.LanguageCode = in.readString();
        this.UserMobileNumber = in.readString();
    }

    public static final Creator<SendEmailToUserRequest> CREATOR = new Creator<SendEmailToUserRequest>() {
        @Override
        public SendEmailToUserRequest createFromParcel(Parcel source) {
            return new SendEmailToUserRequest(source);
        }

        @Override
        public SendEmailToUserRequest[] newArray(int size) {
            return new SendEmailToUserRequest[size];
        }
    };


}
