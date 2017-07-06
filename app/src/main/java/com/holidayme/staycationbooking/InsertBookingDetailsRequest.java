package com.holidayme.staycationbooking;

import com.holidayme.data.PaymentDetailsDto;

/**
 * Created by arshad.shaikh on 3/22/2017.
 */

public class InsertBookingDetailsRequest {

    private  String PackageKey,ReturnUrl,BookingUrl,LanguageCode,CurrencyCode,AppToken,UserAgent;

    private long AccountId;

    private GuestDetailsDTO GuestDetail;
    private PaymentDetailsDto PaymentDetails;

    public GuestDetailsDTO getGuestDetail() {
        return GuestDetail;
    }

    public void setGuestDetail(GuestDetailsDTO guestDetail) {
        GuestDetail = guestDetail;
    }

    public String getPackageKey() {
        return PackageKey;
    }

    public void setPackageKey(String packageKey) {
        PackageKey = packageKey;
    }

    public String getReturnUrl() {
        return ReturnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        ReturnUrl = returnUrl;
    }

    public String getBookingUrl() {
        return BookingUrl;
    }

    public void setBookingUrl(String bookingUrl) {
        BookingUrl = bookingUrl;
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

    public String getAppToken() {
        return AppToken;
    }

    public void setAppToken(String appToken) {
        AppToken = appToken;
    }

    public String getUserAgent() {
        return UserAgent;
    }

    public void setUserAgent(String userAgent) {
        UserAgent = userAgent;
    }

    public long getAccountId() {
        return AccountId;
    }

    public void setAccountId(long accountId) {
        AccountId = accountId;
    }


    public PaymentDetailsDto getPaymentDetails() {
        return PaymentDetails;
    }

    public void setPaymentDetails(PaymentDetailsDto paymentDetails) {
        PaymentDetails = paymentDetails;
    }
}
