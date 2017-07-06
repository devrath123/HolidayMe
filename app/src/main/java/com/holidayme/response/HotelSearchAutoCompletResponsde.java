package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.holidayme.data.HotelDetail;

import java.util.ArrayList;

/**
 * Created by shaikh.salim on 6/8/2016.
 */
public class HotelSearchAutoCompletResponsde extends ErrorResponseBase implements Parcelable {
    ArrayList<HotelDetail> HotelAutoCompletes;
    String AppToken;
    String TransactionId;
    int AffiliateId;
    String LanguageCode;
    String CurrencyCode;
    String UserTrackingId;
    String SessionId;


    public ArrayList<HotelDetail> getHotelDetails() {
        return HotelAutoCompletes;
    }

    public void setHotelDetails(ArrayList<HotelDetail> hotelDetails) {
        this.HotelAutoCompletes = hotelDetails;
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

    public HotelSearchAutoCompletResponsde() {

    }

    protected HotelSearchAutoCompletResponsde(Parcel in) {
        HotelAutoCompletes = new ArrayList<HotelDetail>();
        if (in.readByte() == 0x01) {
            in.readList(HotelAutoCompletes, HotelDetail.class.getClassLoader());
        }
        AppToken = in.readString();
        TransactionId = in.readString();
        AffiliateId = in.readInt();
        LanguageCode = in.readString();
        CurrencyCode = in.readString();
        UserTrackingId = in.readString();
        SessionId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (HotelAutoCompletes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(HotelAutoCompletes);
        }
        dest.writeString(AppToken);
        dest.writeString(TransactionId);
        dest.writeInt(AffiliateId);
        dest.writeString(LanguageCode);
        dest.writeString(CurrencyCode);
        dest.writeString(UserTrackingId);
        dest.writeString(SessionId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HotelSearchAutoCompletResponsde> CREATOR = new Parcelable.Creator<HotelSearchAutoCompletResponsde>() {
        @Override
        public HotelSearchAutoCompletResponsde createFromParcel(Parcel in) {
            return new HotelSearchAutoCompletResponsde(in);
        }

        @Override
        public HotelSearchAutoCompletResponsde[] newArray(int size) {
            return new HotelSearchAutoCompletResponsde[size];
        }
    };
}
