package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 15-11-2016.
 */

public class RequestCancellationRequest implements Parcelable {
    private RequestCancellationDto CrmIncident;
    private String Email;

    public RequestCancellationDto getCrmIncident() {
        return CrmIncident;
    }

    public void setCrmIncident(RequestCancellationDto crmIncident) {
        CrmIncident = crmIncident;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.CrmIncident, flags);
        dest.writeString(this.Email);
    }

    public RequestCancellationRequest() {
    }

    protected RequestCancellationRequest(Parcel in) {
        this.CrmIncident = in.readParcelable(RequestCancellationDto.class.getClassLoader());
        this.Email = in.readString();
    }

    public static final Parcelable.Creator<RequestCancellationRequest> CREATOR = new Parcelable.Creator<RequestCancellationRequest>() {
        @Override
        public RequestCancellationRequest createFromParcel(Parcel source) {
            return new RequestCancellationRequest(source);
        }

        @Override
        public RequestCancellationRequest[] newArray(int size) {
            return new RequestCancellationRequest[size];
        }
    };
}
