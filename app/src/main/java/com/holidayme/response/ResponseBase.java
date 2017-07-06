package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.holidayme.data.ErrorDTO;

/**
 * Created by santosh.patar on 21-09-2015.
 */
public class ResponseBase implements Parcelable {
    private ErrorDTO Error;
    private String  ResponseTime;

    public ErrorDTO getError() {
        return Error;
    }

    public void setError(ErrorDTO error) {
        Error = error;
    }

    public String getResponseTime() {
        return ResponseTime;
    }

    public void setResponseTime(String responseTime) {
        ResponseTime = responseTime;
    }

    public ResponseBase() {
        super();
    }



    protected ResponseBase(Parcel in) {
        Error = (ErrorDTO) in.readValue(ErrorDTO.class.getClassLoader());
        ResponseTime = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(Error);
        dest.writeString(ResponseTime);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ResponseBase> CREATOR = new Parcelable.Creator<ResponseBase>() {
        @Override
        public ResponseBase createFromParcel(Parcel in) {
            return new ResponseBase(in);
        }

        @Override
        public ResponseBase[] newArray(int size) {
            return new ResponseBase[size];
        }
    };
}