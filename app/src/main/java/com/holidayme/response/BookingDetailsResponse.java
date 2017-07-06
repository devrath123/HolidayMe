package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.holidayme.data.BookingDetailsDto;
import com.holidayme.data.CodAvailableDto;
import com.holidayme.data.UserInfo;

/**
 * Created by supriya.sakore on 06-07-2016.
 */

public class BookingDetailsResponse implements Parcelable {
    private BookingDetailsDto BookingDetails;
    private boolean ShowAdditionalTermsAndConditions;
    private String BillingCurrencyCode;
    private double SellingPriceInBillingCurrencyCode;
    private CodAvailableDto CodAvailable;
    private double TotalPriceInUSD;
    private Long ResponseTime;
    private UserInfo UserInfo;
    private String Error;

    public UserInfo getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        UserInfo = userInfo;
    }

    public BookingDetailsDto getBookingDetails() {
        return BookingDetails;
    }

    public void setBookingDetails(BookingDetailsDto bookingDetails) {
        BookingDetails = bookingDetails;
    }

    public boolean isShowAdditionalTermsAndConditions() {
        return ShowAdditionalTermsAndConditions;
    }

    public void setShowAdditionalTermsAndConditions(boolean showAdditionalTermsAndConditions) {
        ShowAdditionalTermsAndConditions = showAdditionalTermsAndConditions;
    }

    public String getBillingCurrencyCode() {
        return BillingCurrencyCode;
    }

    public void setBillingCurrencyCode(String billingCurrencyCode) {
        BillingCurrencyCode = billingCurrencyCode;
    }

    public double getSellingPriceInBillingCurrencyCode() {
        return SellingPriceInBillingCurrencyCode;
    }

    public void setSellingPriceInBillingCurrencyCode(double sellingPriceInBillingCurrencyCode) {
        SellingPriceInBillingCurrencyCode = sellingPriceInBillingCurrencyCode;
    }

    public CodAvailableDto getCodAvailable() {
        return CodAvailable;
    }

    public void setCodAvailable(CodAvailableDto codAvailable) {
        CodAvailable = codAvailable;
    }

    public double getTotalPriceInUSD() {
        return TotalPriceInUSD;
    }

    public void setTotalPriceInUSD(double totalPriceInUSD) {
        TotalPriceInUSD = totalPriceInUSD;
    }

    public Long getResponseTime() {
        return ResponseTime;
    }

    public void setResponseTime(Long responseTime) {
        ResponseTime = responseTime;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    protected BookingDetailsResponse(Parcel in) {
        BookingDetails = (BookingDetailsDto) in.readValue(BookingDetailsDto.class.getClassLoader());
        ShowAdditionalTermsAndConditions = in.readByte() != 0x00;
        BillingCurrencyCode = in.readString();
        SellingPriceInBillingCurrencyCode = in.readDouble();
        CodAvailable = (CodAvailableDto) in.readValue(CodAvailableDto.class.getClassLoader());
        TotalPriceInUSD = in.readDouble();
        ResponseTime = in.readByte() == 0x00 ? null : in.readLong();
        UserInfo = (UserInfo) in.readValue(UserInfo.class.getClassLoader());
        Error = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(BookingDetails);
        dest.writeByte((byte) (ShowAdditionalTermsAndConditions ? 0x01 : 0x00));
        dest.writeString(BillingCurrencyCode);
        dest.writeDouble(SellingPriceInBillingCurrencyCode);
        dest.writeValue(CodAvailable);
        dest.writeDouble(TotalPriceInUSD);
        if (ResponseTime == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(ResponseTime);
        }
        dest.writeValue(UserInfo);
        dest.writeString(Error);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BookingDetailsResponse> CREATOR = new Parcelable.Creator<BookingDetailsResponse>() {
        @Override
        public BookingDetailsResponse createFromParcel(Parcel in) {
            return new BookingDetailsResponse(in);
        }

        @Override
        public BookingDetailsResponse[] newArray(int size) {
            return new BookingDetailsResponse[size];
        }
    };
}