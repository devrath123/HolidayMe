package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 07-07-2016.
 */

public class ApplyCouponCodeResponse extends ResponseBase implements Parcelable {

    private double BillingPrice;
    private double BillingPriceApplyingPromoCode;
    private double BillingPriceApplyingPromoCodeInRequestedCurrency;
    private double BillingPriceInRequestedCurrency;
    private double PromoCodeAmount;
    private double PromoCodeAmountInRequestedCurrency;
    private double PromoCodePercent;
    private double HolidayHotelPromoCodeAmount;
    private double HolidayTransferPromoCodeAmount;
    private String HolidaySightseeingPromoCodeAmount;
    private String ApplyCouponCodeMessage;
    private boolean IsPromocodeApplied;
    private String ExtranetActivityPromoCodeAmount;

    public double getBillingPrice() {
        return BillingPrice;
    }

    public void setBillingPrice(double billingPrice) {
        BillingPrice = billingPrice;
    }

    public double getBillingPriceApplyingPromoCode() {
        return BillingPriceApplyingPromoCode;
    }

    public void setBillingPriceApplyingPromoCode(double billingPriceApplyingPromoCode) {
        BillingPriceApplyingPromoCode = billingPriceApplyingPromoCode;
    }

    public double getBillingPriceApplyingPromoCodeInRequestedCurrency() {
        return BillingPriceApplyingPromoCodeInRequestedCurrency;
    }

    public void setBillingPriceApplyingPromoCodeInRequestedCurrency(double billingPriceApplyingPromoCodeInRequestedCurrency) {
        BillingPriceApplyingPromoCodeInRequestedCurrency = billingPriceApplyingPromoCodeInRequestedCurrency;
    }

    public double getBillingPriceInRequestedCurrency() {
        return BillingPriceInRequestedCurrency;
    }

    public void setBillingPriceInRequestedCurrency(double billingPriceInRequestedCurrency) {
        BillingPriceInRequestedCurrency = billingPriceInRequestedCurrency;
    }

    public double getPromoCodeAmount() {
        return PromoCodeAmount;
    }

    public void setPromoCodeAmount(double promoCodeAmount) {
        PromoCodeAmount = promoCodeAmount;
    }

    public double getPromoCodeAmountInRequestedCurrency() {
        return PromoCodeAmountInRequestedCurrency;
    }

    public void setPromoCodeAmountInRequestedCurrency(double promoCodeAmountInRequestedCurrency) {
        PromoCodeAmountInRequestedCurrency = promoCodeAmountInRequestedCurrency;
    }

    public double getPromoCodePercent() {
        return PromoCodePercent;
    }

    public void setPromoCodePercent(double promoCodePercent) {
        PromoCodePercent = promoCodePercent;
    }

    public double getHolidayHotelPromoCodeAmount() {
        return HolidayHotelPromoCodeAmount;
    }

    public void setHolidayHotelPromoCodeAmount(double holidayHotelPromoCodeAmount) {
        HolidayHotelPromoCodeAmount = holidayHotelPromoCodeAmount;
    }

    public double getHolidayTransferPromoCodeAmount() {
        return HolidayTransferPromoCodeAmount;
    }

    public void setHolidayTransferPromoCodeAmount(double holidayTransferPromoCodeAmount) {
        HolidayTransferPromoCodeAmount = holidayTransferPromoCodeAmount;
    }

    public String getHolidaySightseeingPromoCodeAmount() {
        return HolidaySightseeingPromoCodeAmount;
    }

    public void setHolidaySightseeingPromoCodeAmount(String holidaySightseeingPromoCodeAmount) {
        HolidaySightseeingPromoCodeAmount = holidaySightseeingPromoCodeAmount;
    }

    public String getApplyCouponCodeMessage() {
        return ApplyCouponCodeMessage;
    }

    public void setApplyCouponCodeMessage(String applyCouponCodeMessage) {
        ApplyCouponCodeMessage = applyCouponCodeMessage;
    }

    public boolean isPromocodeApplied() {
        return IsPromocodeApplied;
    }

    public void setPromocodeApplied(boolean promocodeApplied) {
        IsPromocodeApplied = promocodeApplied;
    }

    public String getExtranetActivityPromoCodeAmount() {
        return ExtranetActivityPromoCodeAmount;
    }

    public void setExtranetActivityPromoCodeAmount(String extranetActivityPromoCodeAmount) {
        ExtranetActivityPromoCodeAmount = extranetActivityPromoCodeAmount;
    }

    protected ApplyCouponCodeResponse(Parcel in) {
        BillingPrice = in.readDouble();
        BillingPriceApplyingPromoCode = in.readDouble();
        BillingPriceApplyingPromoCodeInRequestedCurrency = in.readDouble();
        BillingPriceInRequestedCurrency = in.readDouble();
        PromoCodeAmount = in.readDouble();
        PromoCodeAmountInRequestedCurrency = in.readDouble();
        PromoCodePercent = in.readDouble();
        HolidayHotelPromoCodeAmount = in.readDouble();
        HolidayTransferPromoCodeAmount = in.readDouble();
        HolidaySightseeingPromoCodeAmount = in.readString();
        ApplyCouponCodeMessage = in.readString();
        IsPromocodeApplied = in.readByte() != 0x00;
        ExtranetActivityPromoCodeAmount = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(BillingPrice);
        dest.writeDouble(BillingPriceApplyingPromoCode);
        dest.writeDouble(BillingPriceApplyingPromoCodeInRequestedCurrency);
        dest.writeDouble(BillingPriceInRequestedCurrency);
        dest.writeDouble(PromoCodeAmount);
        dest.writeDouble(PromoCodeAmountInRequestedCurrency);
        dest.writeDouble(PromoCodePercent);
        dest.writeDouble(HolidayHotelPromoCodeAmount);
        dest.writeDouble(HolidayTransferPromoCodeAmount);
        dest.writeString(HolidaySightseeingPromoCodeAmount);
        dest.writeString(ApplyCouponCodeMessage);
        dest.writeByte((byte) (IsPromocodeApplied ? 0x01 : 0x00));
        dest.writeString(ExtranetActivityPromoCodeAmount);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ApplyCouponCodeResponse> CREATOR = new Parcelable.Creator<ApplyCouponCodeResponse>() {
        @Override
        public ApplyCouponCodeResponse createFromParcel(Parcel in) {
            return new ApplyCouponCodeResponse(in);
        }

        @Override
        public ApplyCouponCodeResponse[] newArray(int size) {
            return new ApplyCouponCodeResponse[size];
        }
    };
}