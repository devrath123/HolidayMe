package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.holidayme.data.GetUserProfileDto;

/**
 * Created by supriya.sakore on 20-10-2015.
 */
public class GetUserProfileInformationResponse implements Parcelable {

    private GetUserProfileDto GetUserProfile;
    private  String ResponseTime;
    private ErrorResponseBase Error;

    public GetUserProfileDto getGetUserProfile() {
        return GetUserProfile;
    }

    public void setGetUserProfile(GetUserProfileDto getUserProfile) {
        GetUserProfile = getUserProfile;
    }

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



    protected GetUserProfileInformationResponse(Parcel in) {
        GetUserProfile = (GetUserProfileDto) in.readValue(GetUserProfileDto.class.getClassLoader());
        ResponseTime = in.readString();
        Error = (ErrorResponseBase) in.readValue(ErrorResponseBase.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(GetUserProfile);
        dest.writeString(ResponseTime);
        dest.writeValue(Error);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GetUserProfileInformationResponse> CREATOR = new Parcelable.Creator<GetUserProfileInformationResponse>() {
        @Override
        public GetUserProfileInformationResponse createFromParcel(Parcel in) {
            return new GetUserProfileInformationResponse(in);
        }

        @Override
        public GetUserProfileInformationResponse[] newArray(int size) {
            return new GetUserProfileInformationResponse[size];
        }
    };
}