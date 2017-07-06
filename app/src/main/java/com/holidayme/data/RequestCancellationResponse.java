package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 15-11-2016.
 */

public class RequestCancellationResponse implements Parcelable {
    private long IncidentId;
    private ErrorDTO Error;



    public long getIncidentId() {
        return IncidentId;
    }

    public void setIncidentId(long incidentId) {
        IncidentId = incidentId;
    }

    public ErrorDTO getError() {
        return Error;
    }

    public void setError(ErrorDTO error) {
        Error = error;
    }

    public RequestCancellationResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.IncidentId);
        dest.writeParcelable(this.Error, flags);
    }

    protected RequestCancellationResponse(Parcel in) {
        this.IncidentId = in.readLong();
        this.Error = in.readParcelable(ErrorDTO.class.getClassLoader());
    }

    public static final Creator<RequestCancellationResponse> CREATOR = new Creator<RequestCancellationResponse>() {
        @Override
        public RequestCancellationResponse createFromParcel(Parcel source) {
            return new RequestCancellationResponse(source);
        }

        @Override
        public RequestCancellationResponse[] newArray(int size) {
            return new RequestCancellationResponse[size];
        }
    };
}
