package com.holidayme.request;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by santosh.patar on 29-07-2015.
 */
public class PropertySearchRequestDto implements Parcelable {

    long AffiliatedId = 1;
    String CheckInDate;
    String CheckOutDate;
    int DestinationId;
    private String CurrencyCode;
    private String LanguageCode;
    private String UserTrackingId="";
    private String CountryCode=null;



    private ArrayList<com.holidayme.request.PropertySearchRequestAccommodations> PropertySearchRequestAccommodations;

    public long getAffiliatedId() {
        return AffiliatedId;
    }

    public void setAffiliatedId(long affiliatedId) {
        AffiliatedId = affiliatedId;
    }

    public String getCheckInDate() {
        return CheckInDate;
    }

    public void setCheckInDate(String checkInDate) {
        CheckInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return CheckOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        CheckOutDate = checkOutDate;
    }

    public int getDestinationId() {
        return DestinationId;
    }

    public void setDestinationId(int destinationId) {
        DestinationId = destinationId;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public String getUserTrackingId() {
        return UserTrackingId;
    }

    public void setUserTrackingId(String userTrackingId) {
        UserTrackingId = userTrackingId;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public ArrayList<com.holidayme.request.PropertySearchRequestAccommodations> getPropertySearchRequestAccommodations() {
        return PropertySearchRequestAccommodations;
    }

    public void setPropertySearchRequestAccommodations(ArrayList<com.holidayme.request.PropertySearchRequestAccommodations> propertySearchRequestAccommodations) {
        PropertySearchRequestAccommodations = propertySearchRequestAccommodations;
    }

    public PropertySearchRequestDto() {
    }

    protected PropertySearchRequestDto(Parcel in) {
        AffiliatedId = in.readLong();
        CheckInDate = in.readString();
        CheckOutDate = in.readString();
        DestinationId = in.readInt();
        CurrencyCode = in.readString();
        LanguageCode = in.readString();
        UserTrackingId = in.readString();
        CountryCode = in.readString();
        if (in.readByte() == 0x01) {
            PropertySearchRequestAccommodations = new ArrayList<com.holidayme.request.PropertySearchRequestAccommodations>();
            in.readList(PropertySearchRequestAccommodations, com.holidayme.request.PropertySearchRequestAccommodations.class.getClassLoader());
        } else {
            PropertySearchRequestAccommodations = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(AffiliatedId);
        dest.writeString(CheckInDate);
        dest.writeString(CheckOutDate);
        dest.writeInt(DestinationId);
        dest.writeString(CurrencyCode);
        dest.writeString(LanguageCode);
        dest.writeString(UserTrackingId);
        dest.writeString(CountryCode);
        if (PropertySearchRequestAccommodations == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(PropertySearchRequestAccommodations);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PropertySearchRequestDto> CREATOR = new Parcelable.Creator<PropertySearchRequestDto>() {
        @Override
        public PropertySearchRequestDto createFromParcel(Parcel in) {
            return new PropertySearchRequestDto(in);
        }

        @Override
        public PropertySearchRequestDto[] newArray(int size) {
            return new PropertySearchRequestDto[size];
        }
    };
}