package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by santosh.patar on 07-08-2015.
 */
public class ErrorResponseBase implements Parcelable {

    private int ErrorCode;
    private int ErrorCatagory;
    private String ErrorMessage;
    private String Error;
    private int TotalExecutionTime;

    public int getErrorCatagory() {
        return ErrorCatagory;
    }

    public void setErrorCatagory(int errorCatagory) {
        ErrorCatagory = errorCatagory;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public ErrorResponseBase() {
        super();
    }


    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    public int getTotalExecutionTime() {
        return TotalExecutionTime;
    }

    public void setTotalExecutionTime(int totalExecutionTime) {
        TotalExecutionTime = totalExecutionTime;
    }

    protected ErrorResponseBase(Parcel in) {
        ErrorCode = in.readInt();
        ErrorCatagory = in.readInt();
        ErrorMessage = in.readString();
        Error = in.readString();
        TotalExecutionTime = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ErrorCode);
        dest.writeInt(ErrorCatagory);
        dest.writeString(ErrorMessage);
        dest.writeString(Error);
        dest.writeInt(TotalExecutionTime);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ErrorResponseBase> CREATOR = new Parcelable.Creator<ErrorResponseBase>() {
        @Override
        public ErrorResponseBase createFromParcel(Parcel in) {
            return new ErrorResponseBase(in);
        }

        @Override
        public ErrorResponseBase[] newArray(int size) {
            return new ErrorResponseBase[size];
        }
    };
}