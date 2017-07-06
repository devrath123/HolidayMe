package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by santosh.patar on 21-09-2015.
 */
public class ErrorDTO implements Parcelable {

    private int ErrorCode;
    private int ErrorCatagory;
    private String ErrorMessage;


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

    public ErrorDTO() {
        super();
    }


    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }



    protected ErrorDTO(Parcel in) {
        ErrorCode = in.readInt();
        ErrorCatagory = in.readInt();
        ErrorMessage = in.readString();
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
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ErrorDTO> CREATOR = new Parcelable.Creator<ErrorDTO>() {
        @Override
        public ErrorDTO createFromParcel(Parcel in) {
            return new ErrorDTO(in);
        }

        @Override
        public ErrorDTO[] newArray(int size) {
            return new ErrorDTO[size];
        }
    };
}
