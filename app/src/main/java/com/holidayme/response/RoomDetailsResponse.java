package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 24-06-2016.
 */
public class RoomDetailsResponse implements Parcelable {
    private ArrayList<String> RoomImages;
    private ArrayList<String> RoomAmenities;
    private String RoomLongDescription;
    private String AppToken;
    private String TransactionId;
    private int AffiliateId;
    private String LanguageCode;
    private String CurrencyCode;
    private String Error;
    private String UserTrackingId;
    private String SessionId;
    private String TotalExecutionTime;

    public ArrayList<String> getRoomImages() {
        return RoomImages;
    }

    public void setRoomImages(ArrayList<String> roomImages) {
        RoomImages = roomImages;
    }

    public ArrayList<String> getRoomAmenities() {
        return RoomAmenities;
    }

    public void setRoomAmenities(ArrayList<String> roomAmenities) {
        RoomAmenities = roomAmenities;
    }

    public String getRoomLongDescription() {
        return RoomLongDescription;
    }

    public void setRoomLongDescription(String roomLongDescription) {
        RoomLongDescription = roomLongDescription;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
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

    public String getTotalExecutionTime() {
        return TotalExecutionTime;
    }

    public void setTotalExecutionTime(String totalExecutionTime) {
        TotalExecutionTime = totalExecutionTime;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    protected RoomDetailsResponse(Parcel in) {
        if (in.readByte() == 0x01) {
            RoomImages = new ArrayList<String>();
            in.readList(RoomImages, String.class.getClassLoader());
        } else {
            RoomImages = null;
        }
        if (in.readByte() == 0x01) {
            RoomAmenities = new ArrayList<String>();
            in.readList(RoomAmenities, String.class.getClassLoader());
        } else {
            RoomAmenities = null;
        }
        RoomLongDescription = in.readString();
        AppToken = in.readString();
        TransactionId = in.readString();
        AffiliateId = in.readInt();
        LanguageCode = in.readString();
        CurrencyCode = in.readString();
        Error = in.readString();
        UserTrackingId = in.readString();
        SessionId = in.readString();
        TotalExecutionTime = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (RoomImages == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(RoomImages);
        }
        if (RoomAmenities == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(RoomAmenities);
        }
        dest.writeString(RoomLongDescription);
        dest.writeString(AppToken);
        dest.writeString(TransactionId);
        dest.writeInt(AffiliateId);
        dest.writeString(LanguageCode);
        dest.writeString(CurrencyCode);
        dest.writeString(Error);
        dest.writeString(UserTrackingId);
        dest.writeString(SessionId);
        dest.writeString(TotalExecutionTime);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RoomDetailsResponse> CREATOR = new Parcelable.Creator<RoomDetailsResponse>() {
        @Override
        public RoomDetailsResponse createFromParcel(Parcel in) {
            return new RoomDetailsResponse(in);
        }

        @Override
        public RoomDetailsResponse[] newArray(int size) {
            return new RoomDetailsResponse[size];
        }
    };
}