package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 09-06-2016.
 */
public class GetDestinationForHotelResponse implements Parcelable {
    private long CityId;
    private String City;
    private String AppToken;
    private String TransactionId;
    private int AffiliateId;
    private String LanguageCode;
    private String CurrencyCode;
    private String Error;
    private String UserTrackingId;
    private String SessionId;
    private long TotalExecutionTime;

    public long getCityId() {
        return CityId;
    }

    public void setCityId(long cityId) {
        CityId = cityId;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
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

    public long getTotalExecutionTime() {
        return TotalExecutionTime;
    }

    public void setTotalExecutionTime(long totalExecutionTime) {
        TotalExecutionTime = totalExecutionTime;
    }


    protected GetDestinationForHotelResponse(Parcel in) {
        CityId = in.readLong();
        City = in.readString();
        AppToken = in.readString();
        TransactionId = in.readString();
        AffiliateId = in.readInt();
        LanguageCode = in.readString();
        CurrencyCode = in.readString();
        Error = in.readString();
        UserTrackingId = in.readString();
        SessionId = in.readString();
        TotalExecutionTime = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(CityId);
        dest.writeString(City);
        dest.writeString(AppToken);
        dest.writeString(TransactionId);
        dest.writeInt(AffiliateId);
        dest.writeString(LanguageCode);
        dest.writeString(CurrencyCode);
        dest.writeString(Error);
        dest.writeString(UserTrackingId);
        dest.writeString(SessionId);
        dest.writeLong(TotalExecutionTime);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GetDestinationForHotelResponse> CREATOR = new Parcelable.Creator<GetDestinationForHotelResponse>() {
        @Override
        public GetDestinationForHotelResponse createFromParcel(Parcel in) {
            return new GetDestinationForHotelResponse(in);
        }

        @Override
        public GetDestinationForHotelResponse[] newArray(int size) {
            return new GetDestinationForHotelResponse[size];
        }
    };
}