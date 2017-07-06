package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.holidayme.data.BookingListDto;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 23-10-2015.
 */
public class BookHistoryResponse implements Parcelable {
    private ArrayList<BookingListDto> BookingList;
    private  boolean IsMoreRecordsExist;
    private String ResponseTime;
    private ErrorResponseBase Error;

    public ArrayList<BookingListDto> getBookingList() {
        return BookingList;
    }

    public void setBookingList(ArrayList<BookingListDto> bookingList) {
        BookingList = bookingList;
    }

    public boolean isMoreRecordsExist() {
        return IsMoreRecordsExist;
    }

    public void setIsMoreRecordsExist(boolean isMoreRecordsExist) {
        IsMoreRecordsExist = isMoreRecordsExist;
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



    protected BookHistoryResponse(Parcel in) {
        if (in.readByte() == 0x01) {
            BookingList = new ArrayList<BookingListDto>();
            in.readList(BookingList, BookingListDto.class.getClassLoader());
        } else {
            BookingList = null;
        }
        IsMoreRecordsExist = in.readByte() != 0x00;
        ResponseTime = in.readString();
        Error = (ErrorResponseBase) in.readValue(ErrorResponseBase.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (BookingList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(BookingList);
        }
        dest.writeByte((byte) (IsMoreRecordsExist ? 0x01 : 0x00));
        dest.writeString(ResponseTime);
        dest.writeValue(Error);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BookHistoryResponse> CREATOR = new Parcelable.Creator<BookHistoryResponse>() {
        @Override
        public BookHistoryResponse createFromParcel(Parcel in) {
            return new BookHistoryResponse(in);
        }

        @Override
        public BookHistoryResponse[] newArray(int size) {
            return new BookHistoryResponse[size];
        }
    };
}