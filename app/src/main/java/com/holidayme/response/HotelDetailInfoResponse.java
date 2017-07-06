package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.holidayme.data.BasicInfoDto;
import com.holidayme.data.MoreInfoDto;

/**
 * Created by supriya.sakore on 07-06-2016.
 */
public class HotelDetailInfoResponse implements Parcelable {
    private Long Id;
    private String Ttl;
    private double StartFromPrice;
    private String HotelSeoName;
    private int TotalHotels;
    private BasicInfoDto BasicInfo;
    private MoreInfoDto MoreInfo;
    private String AppToken;
    private String TransactionId;
    private int AffiliateId;
    private String LanguageCode;
    private String CurrencyCode;
    private String Error;
    private String UserTrackingId;
    private String SessionId;
    private Long TotalExecutionTime;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTtl() {
        return Ttl;
    }

    public void setTtl(String ttl) {
        Ttl = ttl;
    }

    public double getStartFromPrice() {
        return StartFromPrice;
    }

    public void setStartFromPrice(double startFromPrice) {
        StartFromPrice = startFromPrice;
    }

    public String getHotelSeoName() {
        return HotelSeoName;
    }

    public void setHotelSeoName(String hotelSeoName) {
        HotelSeoName = hotelSeoName;
    }

    public BasicInfoDto getBasicInfo() {
        return BasicInfo;
    }

    public void setBasicInfo(BasicInfoDto basicInfo) {
        BasicInfo = basicInfo;
    }

    public int getTotalHotels() {
        return TotalHotels;
    }

    public void setTotalHotels(int totalHotels) {
        TotalHotels = totalHotels;
    }

    public MoreInfoDto getMoreInfo() {
        return MoreInfo;
    }

    public void setMoreInfo(MoreInfoDto moreInfo) {
        MoreInfo = moreInfo;
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

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public int getAffiliateId() {
        return AffiliateId;
    }

    public void setAffiliateId(int affiliateId) {
        AffiliateId = affiliateId;
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

    protected HotelDetailInfoResponse(Parcel in) {
        Id = in.readByte() == 0x00 ? null : in.readLong();
        Ttl = in.readString();
        StartFromPrice = in.readDouble();
        HotelSeoName = in.readString();
        TotalHotels = in.readInt();
        BasicInfo = (BasicInfoDto) in.readValue(BasicInfoDto.class.getClassLoader());
        MoreInfo = (MoreInfoDto) in.readValue(MoreInfoDto.class.getClassLoader());
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
        if (Id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(Id);
        }
        dest.writeString(Ttl);
        dest.writeDouble(StartFromPrice);
        dest.writeString(HotelSeoName);
        dest.writeInt(TotalHotels);
        dest.writeValue(BasicInfo);
        dest.writeValue(MoreInfo);
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
    public static final Parcelable.Creator<HotelDetailInfoResponse> CREATOR = new Parcelable.Creator<HotelDetailInfoResponse>() {
        @Override
        public HotelDetailInfoResponse createFromParcel(Parcel in) {
            return new HotelDetailInfoResponse(in);
        }

        @Override
        public HotelDetailInfoResponse[] newArray(int size) {
            return new HotelDetailInfoResponse[size];
        }
    };
}