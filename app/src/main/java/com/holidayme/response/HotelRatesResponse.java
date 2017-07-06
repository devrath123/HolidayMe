package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.holidayme.data.RoomTypesDto;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 24-06-2016.
 */
public class HotelRatesResponse implements Parcelable {
    private ArrayList<RoomTypesDto> RoomTypes;
    private Long StartFromPrice;
    private String CheckInInstructions;
    private String AppToken;
    private String TransactionId;
    private int AffiliateId;
    private String LanguageCode;
    private String CurrencyCode;
    private String Error;
    private String UserTrackingId;
    private String SessionId;
    private String PriceLabel;
    private long TotalExecutionTime;

    public ArrayList<RoomTypesDto> getRoomTypes() {
        return RoomTypes;
    }

    public void setRoomTypes(ArrayList<RoomTypesDto> roomTypes) {
        RoomTypes = roomTypes;
    }

    public Long getStartFromPrice() {
        return StartFromPrice;
    }

    public void setStartFromPrice(Long startFromPrice) {
        StartFromPrice = startFromPrice;
    }

    public String getCheckInInstructions() {
        return CheckInInstructions;
    }

    public void setCheckInInstructions(String checkInInstructions) {
        CheckInInstructions = checkInInstructions;
    }

    public String getAppToken() {
        return AppToken;
    }

    public void setAppToken(String appToken) {
        AppToken = appToken;
    }

    public int getAffiliateId() {
        return AffiliateId;
    }

    public void setAffiliateId(int affiliateId) {
        AffiliateId = affiliateId;
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

    public String getPriceLabel() {
        return PriceLabel;
    }

    public void setPriceLabel(String priceLabel) {
        PriceLabel = priceLabel;
    }

    protected HotelRatesResponse(Parcel in) {
        if (in.readByte() == 0x01) {
            RoomTypes = new ArrayList<RoomTypesDto>();
            in.readList(RoomTypes, RoomTypesDto.class.getClassLoader());
        } else {
            RoomTypes = null;
        }
        StartFromPrice = in.readByte() == 0x00 ? null : in.readLong();
        CheckInInstructions = in.readString();
        AppToken = in.readString();
        TransactionId = in.readString();
        AffiliateId = in.readInt();
        LanguageCode = in.readString();
        CurrencyCode = in.readString();
        Error = in.readString();
        UserTrackingId = in.readString();
        SessionId = in.readString();
        TotalExecutionTime = in.readLong();
        PriceLabel=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (RoomTypes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(RoomTypes);
        }
        if (StartFromPrice == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(StartFromPrice);
        }
        dest.writeString(CheckInInstructions);
        dest.writeString(AppToken);
        dest.writeString(TransactionId);
        dest.writeInt(AffiliateId);
        dest.writeString(LanguageCode);
        dest.writeString(CurrencyCode);
        dest.writeString(Error);
        dest.writeString(UserTrackingId);
        dest.writeString(SessionId);
        dest.writeLong(TotalExecutionTime);
        dest.writeString(PriceLabel);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HotelRatesResponse> CREATOR = new Parcelable.Creator<HotelRatesResponse>() {
        @Override
        public HotelRatesResponse createFromParcel(Parcel in) {
            return new HotelRatesResponse(in);
        }

        @Override
        public HotelRatesResponse[] newArray(int size) {
            return new HotelRatesResponse[size];
        }
    };
}