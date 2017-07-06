package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 15-11-2016.
 */

public class SendEmailToUserResponse implements Parcelable {
    private boolean IsSendSuccessful;
    private ErrorDTO Error;

    public boolean isSendSuccessful() {
        return IsSendSuccessful;
    }

    public void setSendSuccessful(boolean sendSuccessful) {
        IsSendSuccessful = sendSuccessful;
    }

    public ErrorDTO getError() {
        return Error;
    }

    public void setError(ErrorDTO error) {
        Error = error;
    }

    public SendEmailToUserResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.IsSendSuccessful ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.Error, flags);
    }

    protected SendEmailToUserResponse(Parcel in) {
        this.IsSendSuccessful = in.readByte() != 0;
        this.Error = in.readParcelable(ErrorDTO.class.getClassLoader());
    }

    public static final Creator<SendEmailToUserResponse> CREATOR = new Creator<SendEmailToUserResponse>() {
        @Override
        public SendEmailToUserResponse createFromParcel(Parcel source) {
            return new SendEmailToUserResponse(source);
        }

        @Override
        public SendEmailToUserResponse[] newArray(int size) {
            return new SendEmailToUserResponse[size];
        }
    };
}
