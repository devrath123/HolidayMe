package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 26-10-2015.
 */
public class EditProfileResponse implements Parcelable {
    private  String ResponseTime;
    private ErrorResponseBase Error;

    public String getResponseTime() {
        return ResponseTime;
    }

    public void setResponseTime(String responseTime) {
        ResponseTime = responseTime;
    }

    public ErrorResponseBase getError() {
        return Error;
    }

    public void setError(ErrorResponseBase error) {
        Error = error;
    }


    protected EditProfileResponse(Parcel in) {
        ResponseTime = in.readString();
        Error = (ErrorResponseBase) in.readValue(ErrorResponseBase.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ResponseTime);
        dest.writeValue(Error);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<EditProfileResponse> CREATOR = new Parcelable.Creator<EditProfileResponse>() {
        @Override
        public EditProfileResponse createFromParcel(Parcel in) {
            return new EditProfileResponse(in);
        }

        @Override
        public EditProfileResponse[] newArray(int size) {
            return new EditProfileResponse[size];
        }
    };
}