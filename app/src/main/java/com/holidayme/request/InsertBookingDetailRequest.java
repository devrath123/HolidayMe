package com.holidayme.request;

import java.util.ArrayList;

/**
 * Created by shaikh.salim on 7/11/2016.
 */

public class InsertBookingDetailRequest {
    private String TempBookingId;

    public String getTempBookingId() {
        return this.TempBookingId;
    }

    public void setTempBookingId(String TempBookingId) {
        this.TempBookingId = TempBookingId;
    }

    private int BookingID;

    public int getBookingID() {
        return this.BookingID;
    }

    public void setBookingID(int BookingID) {
        this.BookingID = BookingID;
    }

    private int BookingPropertyID;

    public int getBookingPropertyID() {
        return this.BookingPropertyID;
    }

    public void setBookingPropertyID(int BookingPropertyID) {
        this.BookingPropertyID = BookingPropertyID;
    }

    private String BillingCurrencyType;

    public String getBillingCurrencyType() {
        return this.BillingCurrencyType;
    }

    public void setBillingCurrencyType(String BillingCurrencyType) {
        this.BillingCurrencyType = BillingCurrencyType;
    }

    private double BillingPrice;

    public double getBillingPrice() {
        return this.BillingPrice;
    }

    public void setBillingPrice(double BillingPrice) {
        this.BillingPrice = BillingPrice;
    }

    private String CardHolderName;

    public String getCardHolderName() {
        return this.CardHolderName;
    }

    public void setCardHolderName(String CardHolderName) {
        this.CardHolderName = CardHolderName;
    }

    private String CardCvvNumber;

    public String getCardCvvNumber() {
        return this.CardCvvNumber;
    }

    public void setCardCvvNumber(String CardCvvNumber) {
        this.CardCvvNumber = CardCvvNumber;
    }

    private String GuestSalutation;

    public String getGuestSalutation() {
        return this.GuestSalutation;
    }

    public void setGuestSalutation(String GuestSalutation) {
        this.GuestSalutation = GuestSalutation;
    }

    private String GuestFirstName;

    public String getGuestFirstName() {
        return this.GuestFirstName;
    }

    public void setGuestFirstName(String GuestFirstName) {
        this.GuestFirstName = GuestFirstName;
    }

    private String GuestMiddleName;

    public String getGuestMiddleName() {
        return this.GuestMiddleName;
    }

    public void setGuestMiddleName(String GuestMiddleName) {
        this.GuestMiddleName = GuestMiddleName;
    }

    private String GuestCountryISDCode;

    public String getGuestCountryISDCode() {
        return this.GuestCountryISDCode;
    }

    public void setGuestCountryISDCode(String GuestCountryISDCode) {
        this.GuestCountryISDCode = GuestCountryISDCode;
    }

    private String GuestLastName;

    public String getGuestLastName() {
        return this.GuestLastName;
    }

    public void setGuestLastName(String GuestLastName) {
        this.GuestLastName = GuestLastName;
    }

    private String GuestAddress;

    public String getGuestAddress() {
        return this.GuestAddress;
    }

    public void setGuestAddress(String GuestAddress) {
        this.GuestAddress = GuestAddress;
    }

    private String GuestMobileNumber;

    public String getGuestMobileNumber() {
        return this.GuestMobileNumber;
    }

    public void setGuestMobileNumber(String GuestMobileNumber) {
        this.GuestMobileNumber = GuestMobileNumber;
    }

    private String GuestEmail;

    public String getGuestEmail() {
        return this.GuestEmail;
    }

    public void setGuestEmail(String GuestEmail) {
        this.GuestEmail = GuestEmail;
    }

    private String CountryOfResidence;

    public String getCountryOfResidence() {
        return this.CountryOfResidence;
    }

    public void setCountryOfResidence(String CountryOfResidence) {
        this.CountryOfResidence = CountryOfResidence;
    }

    private String PassportCountry;

    public String getPassportCountry() {
        return this.PassportCountry;
    }

    public void setPassportCountry(String PassportCountry) {
        this.PassportCountry = PassportCountry;
    }

    private ArrayList<RoomDetail> RoomDetails;

    public ArrayList<RoomDetail> getRoomDetails() {
        return this.RoomDetails;
    }

    public void setRoomDetails(ArrayList<RoomDetail> RoomDetails) {
        this.RoomDetails = RoomDetails;
    }

    private int OwnerType;

    public int getOwnerType() {
        return this.OwnerType;
    }

    public void setOwnerType(int OwnerType) {
        this.OwnerType = OwnerType;
    }

    private String CustomerIPAddress;

    public String getCustomerIPAddress() {
        return this.CustomerIPAddress;
    }

    public void setCustomerIPAddress(String CustomerIPAddress) {
        this.CustomerIPAddress = CustomerIPAddress;
    }

    private String CustomerUserAgent;

    public String getCustomerUserAgent() {
        return this.CustomerUserAgent;
    }

    public void setCustomerUserAgent(String CustomerUserAgent) {
        this.CustomerUserAgent = CustomerUserAgent;
    }

    private int TransactionStatusId;

    public int getTransactionStatusId() {
        return this.TransactionStatusId;
    }

    public void setTransactionStatusId(int TransactionStatusId) {
        this.TransactionStatusId = TransactionStatusId;
    }

    private String CountryCode;

    public String getCountryCode() {
        return this.CountryCode;
    }

    public void setCountryCode(String CountryCode) {
        this.CountryCode = CountryCode;
    }

    private String Nationality;

    public String getNationality() {
        return this.Nationality;
    }

    public void setNationality(String Nationality) {
        this.Nationality = Nationality;
    }

    private String AddressLine1;

    public String getAddressLine1() {
        return this.AddressLine1;
    }

    public void setAddressLine1(String AddressLine1) {
        this.AddressLine1 = AddressLine1;
    }

    private String City;

    public String getCity() {
        return this.City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    private String State;

    public String getState() {
        return this.State;
    }

    public void setState(String State) {
        this.State = State;
    }

    private String Country;

    public String getCountry() {
        return this.Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    private String CardNumber;

    public String getCardNumber() {
        return this.CardNumber;
    }

    public void setCardNumber(String CardNumber) {
        this.CardNumber = CardNumber;
    }

    private String PostalCode;

    public String getPostalCode() {
        return this.PostalCode;
    }

    public void setPostalCode(String PostalCode) {
        this.PostalCode = PostalCode;
    }

    private String ExpiryTime;

    public String getExpiryTime() {
        return this.ExpiryTime;
    }

    public void setExpiryTime(String ExpiryTime) {
        this.ExpiryTime = ExpiryTime;
    }

    private int CardType;

    public int getCardType() {
        return this.CardType;
    }

    public void setCardType(int CardType) {
        this.CardType = CardType;
    }

    private boolean IsIrsAgent;

    public boolean getIsIrsAgent() {
        return this.IsIrsAgent;
    }

    public void setIsIrsAgent(boolean IsIrsAgent) {
        this.IsIrsAgent = IsIrsAgent;
    }

    private String PromoCode;

    public String getPromoCode() {
        return this.PromoCode;
    }

    public void setPromoCode(String PromoCode) {
        this.PromoCode = PromoCode;
    }

    private String PaymentMode;

    public String getPaymentMode() {
        return this.PaymentMode;
    }

    public void setPaymentMode(String PaymentMode) {
        this.PaymentMode = PaymentMode;
    }

    private int CodCityId;

    public int getCodCityId() {
        return this.CodCityId;
    }

    public void setCodCityId(int CodCityId) {
        this.CodCityId = CodCityId;
    }

    private int CodAreaId;

    public int getCodAreaId() {
        return this.CodAreaId;
    }

    public void setCodAreaId(int CodAreaId) {
        this.CodAreaId = CodAreaId;
    }

    private String CodCountryCode;

    public String getCodCountryCode() {
        return this.CodCountryCode;
    }

    public void setCodCountryCode(String CodCountryCode) {
        this.CodCountryCode = CodCountryCode;
    }

    private String BookingUrl;

    public String getBookingUrl() {
        return this.BookingUrl;
    }

    public void setBookingUrl(String BookingUrl) {
        this.BookingUrl = BookingUrl;
    }

    private int PromotionFor;

    public int getPromotionFor() {
        return this.PromotionFor;
    }

    public void setPromotionFor(int PromotionFor) {
        this.PromotionFor = PromotionFor;
    }

    private boolean IsMobile;

    public boolean getIsMobile() {
        return this.IsMobile;
    }

    public void setIsMobile(boolean IsMobile) {
        this.IsMobile = IsMobile;
    }

    private String RequestId;

    public String getRequestId() {
        return this.RequestId;
    }

    public void setRequestId(String RequestId) {
        this.RequestId = RequestId;
    }

    private boolean IsSubscribUser;

    public boolean getIsSubscribUser() {
        return this.IsSubscribUser;
    }

    public void setIsSubscribUser(boolean IsSubscribUser) {
        this.IsSubscribUser = IsSubscribUser;
    }

    private String LanguageCode;

    public String getLanguageCode() {
        return this.LanguageCode;
    }

    public void setLanguageCode(String LanguageCode) {
        this.LanguageCode = LanguageCode;
    }

    private String TrackingCookie;

    public String getTrackingCookie() {
        return this.TrackingCookie;
    }

    public void setTrackingCookie(String TrackingCookie) {
        this.TrackingCookie = TrackingCookie;
    }

    private String UserAgent;

    public String getUserAgent() {
        return this.UserAgent;
    }

    public void setUserAgent(String UserAgent) {
        this.UserAgent = UserAgent;
    }

    private String UserIpAddress;

    public String getUserIpAddress() {
        return this.UserIpAddress;
    }

    public void setUserIpAddress(String UserIpAddress) {
        this.UserIpAddress = UserIpAddress;
    }

    private int AffiliateId;

    public int getAffiliateId() {
        return this.AffiliateId;
    }

    public void setAffiliateId(int AffiliateId) {
        this.AffiliateId = AffiliateId;
    }
}
