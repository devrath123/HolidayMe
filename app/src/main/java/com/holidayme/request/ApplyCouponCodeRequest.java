package com.holidayme.request;

/**
 * Created by supriya.sakore on 07-07-2016.
 */

public class ApplyCouponCodeRequest {
    private String TempBookingId;
    private String PromoCode;
    private String CurrencyCode;
    private int EntityType;
    private boolean IsExtranetActivity;
    private int PromotionFor;
    private boolean IsMobile;
    private String Email;
    private String RequestId;
    private boolean IsSubscribUser;
    private String LanguageCode;
    private String TrackingCookie;
    private String UserAgent;
    private String UserIpAddress;
    private int AffiliateId;


    public String getTempBookingId() {
        return TempBookingId;
    }

    public void setTempBookingId(String tempBookingId) {
        TempBookingId = tempBookingId;
    }

    public String getPromoCode() {
        return PromoCode;
    }

    public void setPromoCode(String promoCode) {
        PromoCode = promoCode;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public int getEntityType() {
        return EntityType;
    }

    public void setEntityType(int entityType) {
        EntityType = entityType;
    }

    public boolean isExtranetActivity() {
        return IsExtranetActivity;
    }

    public void setExtranetActivity(boolean extranetActivity) {
        IsExtranetActivity = extranetActivity;
    }

    public int getPromotionFor() {
        return PromotionFor;
    }

    public void setPromotionFor(int promotionFor) {
        PromotionFor = promotionFor;
    }

    public boolean isMobile() {
        return IsMobile;
    }

    public void setMobile(boolean mobile) {
        IsMobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public boolean isSubscribUser() {
        return IsSubscribUser;
    }

    public void setSubscribUser(boolean subscribUser) {
        IsSubscribUser = subscribUser;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public String getTrackingCookie() {
        return TrackingCookie;
    }

    public void setTrackingCookie(String trackingCookie) {
        TrackingCookie = trackingCookie;
    }

    public String getUserAgent() {
        return UserAgent;
    }

    public void setUserAgent(String userAgent) {
        UserAgent = userAgent;
    }

    public String getUserIpAddress() {
        return UserIpAddress;
    }

    public void setUserIpAddress(String userIpAddress) {
        UserIpAddress = userIpAddress;
    }

    public int getAffiliateId() {
        return AffiliateId;
    }

    public void setAffiliateId(int affiliateId) {
        AffiliateId = affiliateId;
    }
}
