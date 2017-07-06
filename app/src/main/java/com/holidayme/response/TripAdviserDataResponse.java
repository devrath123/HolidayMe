package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.holidayme.data.TripAdvisorDetailInfoDto;

/**
 * Created by supriya.sakore on 08-06-2016.
 */
public class TripAdviserDataResponse implements Parcelable {
    private TripAdvisorDetailInfoDto TripAdvisorDetail;
    private String Error;
    private Long  TotalExecutionTime;

    public TripAdvisorDetailInfoDto getTripAdvisorDetail() {
        return TripAdvisorDetail;
    }

    public void setTripAdvisorDetail(TripAdvisorDetailInfoDto tripAdvisorDetail) {
        TripAdvisorDetail = tripAdvisorDetail;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    public Long getTotalExecutionTime() {
        return TotalExecutionTime;
    }

    public void setTotalExecutionTime(Long totalExecutionTime) {
        TotalExecutionTime = totalExecutionTime;
    }

    protected TripAdviserDataResponse(Parcel in) {
        TripAdvisorDetail = (TripAdvisorDetailInfoDto) in.readValue(TripAdvisorDetailInfoDto.class.getClassLoader());
        Error = in.readString();
        TotalExecutionTime = in.readByte() == 0x00 ? null : in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(TripAdvisorDetail);
        dest.writeString(Error);
        if (TotalExecutionTime == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(TotalExecutionTime);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TripAdviserDataResponse> CREATOR = new Parcelable.Creator<TripAdviserDataResponse>() {
        @Override
        public TripAdviserDataResponse createFromParcel(Parcel in) {
            return new TripAdviserDataResponse(in);
        }

        @Override
        public TripAdviserDataResponse[] newArray(int size) {
            return new TripAdviserDataResponse[size];
        }
    };
}