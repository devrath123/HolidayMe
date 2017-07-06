package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 06-07-2016.
 */

public class RoomsDto implements Parcelable {
    private int RoomNumber;
    private int SpecifiedAdults;
    private int SpecifiedChilds;
    private String RateKey;
    private String RateCode;
    private String SellingCurrency;
    private double NetRate;
    private double BaseRate;
    private double Surcharge;
    private double TotalNetRate;
    private double TaxesAndExtras;
    private double TotalTaxes;
    private double BuyingPrice;
    private double SellingPrice;
    private int  IsRefundable;
    private ArrayList<RateBreakupDto> RateBreakup;
    private boolean FreeCancellation;
    private boolean IsCodApplicable;

    public int getRoomNumber() {
        return RoomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        RoomNumber = roomNumber;
    }

    public int getSpecifiedAdults() {
        return SpecifiedAdults;
    }

    public void setSpecifiedAdults(int specifiedAdults) {
        SpecifiedAdults = specifiedAdults;
    }

    public int getSpecifiedChilds() {
        return SpecifiedChilds;
    }

    public void setSpecifiedChilds(int specifiedChilds) {
        SpecifiedChilds = specifiedChilds;
    }

    public String getRateKey() {
        return RateKey;
    }

    public void setRateKey(String rateKey) {
        RateKey = rateKey;
    }

    public String getRateCode() {
        return RateCode;
    }

    public void setRateCode(String rateCode) {
        RateCode = rateCode;
    }

    public String getSellingCurrency() {
        return SellingCurrency;
    }

    public void setSellingCurrency(String sellingCurrency) {
        SellingCurrency = sellingCurrency;
    }

    public double getNetRate() {
        return NetRate;
    }

    public void setNetRate(double netRate) {
        NetRate = netRate;
    }

    public double getBaseRate() {
        return BaseRate;
    }

    public void setBaseRate(double baseRate) {
        BaseRate = baseRate;
    }

    public double getSurcharge() {
        return Surcharge;
    }

    public void setSurcharge(double surcharge) {
        Surcharge = surcharge;
    }

    public double getTotalNetRate() {
        return TotalNetRate;
    }

    public void setTotalNetRate(double totalNetRate) {
        TotalNetRate = totalNetRate;
    }

    public double getTaxesAndExtras() {
        return TaxesAndExtras;
    }

    public void setTaxesAndExtras(double taxesAndExtras) {
        TaxesAndExtras = taxesAndExtras;
    }

    public double getTotalTaxes() {
        return TotalTaxes;
    }

    public void setTotalTaxes(double totalTaxes) {
        TotalTaxes = totalTaxes;
    }

    public double getBuyingPrice() {
        return BuyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        BuyingPrice = buyingPrice;
    }

    public double getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public ArrayList<RateBreakupDto> getRateBreakup() {
        return RateBreakup;
    }

    public void setRateBreakup(ArrayList<RateBreakupDto> rateBreakup) {
        RateBreakup = rateBreakup;
    }

    public int getIsRefundable() {
        return IsRefundable;
    }

    public void setIsRefundable(int isRefundable) {
        IsRefundable = isRefundable;
    }

    public boolean isFreeCancellation() {
        return FreeCancellation;
    }

    public void setFreeCancellation(boolean freeCancellation) {
        FreeCancellation = freeCancellation;
    }

    public boolean isCodApplicable() {
        return IsCodApplicable;
    }

    public void setCodApplicable(boolean codApplicable) {
        IsCodApplicable = codApplicable;
    }

    protected RoomsDto(Parcel in) {
        RoomNumber = in.readInt();
        SpecifiedAdults = in.readInt();
        SpecifiedChilds = in.readInt();
        RateKey = in.readString();
        RateCode = in.readString();
        SellingCurrency = in.readString();
        NetRate = in.readDouble();
        BaseRate = in.readDouble();
        Surcharge = in.readDouble();
        TotalNetRate = in.readDouble();
        TaxesAndExtras = in.readDouble();
        TotalTaxes = in.readDouble();
        BuyingPrice = in.readDouble();
        SellingPrice = in.readDouble();
        IsRefundable = in.readInt();
        if (in.readByte() == 0x01) {
            RateBreakup = new ArrayList<RateBreakupDto>();
            in.readList(RateBreakup, RateBreakupDto.class.getClassLoader());
        } else {
            RateBreakup = null;
        }
        FreeCancellation = in.readByte() != 0x00;
        IsCodApplicable = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(RoomNumber);
        dest.writeInt(SpecifiedAdults);
        dest.writeInt(SpecifiedChilds);
        dest.writeString(RateKey);
        dest.writeString(RateCode);
        dest.writeString(SellingCurrency);
        dest.writeDouble(NetRate);
        dest.writeDouble(BaseRate);
        dest.writeDouble(Surcharge);
        dest.writeDouble(TotalNetRate);
        dest.writeDouble(TaxesAndExtras);
        dest.writeDouble(TotalTaxes);
        dest.writeDouble(BuyingPrice);
        dest.writeDouble(SellingPrice);
        dest.writeInt(IsRefundable);
        if (RateBreakup == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(RateBreakup);
        }
        dest.writeByte((byte) (FreeCancellation ? 0x01 : 0x00));
        dest.writeByte((byte) (IsCodApplicable ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RoomsDto> CREATOR = new Parcelable.Creator<RoomsDto>() {
        @Override
        public RoomsDto createFromParcel(Parcel in) {
            return new RoomsDto(in);
        }

        @Override
        public RoomsDto[] newArray(int size) {
            return new RoomsDto[size];
        }
    };
}