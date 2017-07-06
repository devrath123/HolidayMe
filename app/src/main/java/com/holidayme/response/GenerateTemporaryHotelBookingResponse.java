package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 06-07-2016.
 */

public class GenerateTemporaryHotelBookingResponse implements Parcelable {
    private String TempBookingId;
    private String AppToken;
    private String TransactionId;
    private int AffiliateId;
    private String LanguageCode;
    private String CurrencyCode;
    private String Error;
    private String UserTrackingId;
    private String SessionId;
    private Long TotalExecutionTime;

    public String getTempBookingId() {
        return TempBookingId;
    }

    public void setTempBookingId(String tempBookingId) {
        TempBookingId = tempBookingId;
    }

    public String getAppToken() {
        return AppToken;
    }

    public void setAppToken(String appToken) {
        AppToken = appToken;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public int getAffiliateId() {
        return AffiliateId;
    }

    public void setAffiliateId(int affiliateId) {
        AffiliateId = affiliateId;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    public String getUserTrackingId() {
        return UserTrackingId;
    }

    public void setUserTrackingId(String userTrackingId) {
        UserTrackingId = userTrackingId;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public Long getTotalExecutionTime() {
        return TotalExecutionTime;
    }

    public void setTotalExecutionTime(Long totalExecutionTime) {
        TotalExecutionTime = totalExecutionTime;
    }

    protected GenerateTemporaryHotelBookingResponse(Parcel in) {
        TempBookingId = in.readString();
        AppToken = in.readString();
        TransactionId = in.readString();
        AffiliateId = in.readInt();
        LanguageCode = in.readString();
        CurrencyCode = in.readString();
        Error = in.readString();
        UserTrackingId = in.readString();
        SessionId = in.readString();
        TotalExecutionTime = in.readByte() == 0x00 ? null : in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TempBookingId);
        dest.writeString(AppToken);
        dest.writeString(TransactionId);
        dest.writeInt(AffiliateId);
        dest.writeString(LanguageCode);
        dest.writeString(CurrencyCode);
        dest.writeString(Error);
        dest.writeString(UserTrackingId);
        dest.writeString(SessionId);
        if (TotalExecutionTime == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(TotalExecutionTime);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GenerateTemporaryHotelBookingResponse> CREATOR = new Parcelable.Creator<GenerateTemporaryHotelBookingResponse>() {
        @Override
        public GenerateTemporaryHotelBookingResponse createFromParcel(Parcel in) {
            return new GenerateTemporaryHotelBookingResponse(in);
        }

        @Override
        public GenerateTemporaryHotelBookingResponse[] newArray(int size) {
            return new GenerateTemporaryHotelBookingResponse[size];
        }
    };
}