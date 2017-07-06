package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 06-07-2016.
 */

public class RateBreakupDto implements Parcelable {
    private String Date;
    private String Day;
    private int DayIndex;
    private int BaseRate;
    private double NetRate;
    private int TotalTaxes;
    private int MarkupAmount;
    private int MarkupPercentile;
    private int SupplierTax;
    private int SupplierRate;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public int getDayIndex() {
        return DayIndex;
    }

    public void setDayIndex(int dayIndex) {
        DayIndex = dayIndex;
    }

    public int getBaseRate() {
        return BaseRate;
    }

    public void setBaseRate(int baseRate) {
        BaseRate = baseRate;
    }

    public double getNetRate() {
        return NetRate;
    }

    public void setNetRate(double netRate) {
        NetRate = netRate;
    }

    public int getTotalTaxes() {
        return TotalTaxes;
    }

    public void setTotalTaxes(int totalTaxes) {
        TotalTaxes = totalTaxes;
    }

    public int getMarkupAmount() {
        return MarkupAmount;
    }

    public void setMarkupAmount(int markupAmount) {
        MarkupAmount = markupAmount;
    }

    public int getSupplierTax() {
        return SupplierTax;
    }

    public void setSupplierTax(int supplierTax) {
        SupplierTax = supplierTax;
    }

    public int getMarkupPercentile() {
        return MarkupPercentile;
    }

    public void setMarkupPercentile(int markupPercentile) {
        MarkupPercentile = markupPercentile;
    }

    public int getSupplierRate() {
        return SupplierRate;
    }

    public void setSupplierRate(int supplierRate) {
        SupplierRate = supplierRate;
    }

    protected RateBreakupDto(Parcel in) {
        Date = in.readString();
        Day = in.readString();
        DayIndex = in.readInt();
        BaseRate = in.readInt();
        NetRate = in.readDouble();
        TotalTaxes = in.readInt();
        MarkupAmount = in.readInt();
        MarkupPercentile = in.readInt();
        SupplierTax = in.readInt();
        SupplierRate = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Date);
        dest.writeString(Day);
        dest.writeInt(DayIndex);
        dest.writeInt(BaseRate);
        dest.writeDouble(NetRate);
        dest.writeInt(TotalTaxes);
        dest.writeInt(MarkupAmount);
        dest.writeInt(MarkupPercentile);
        dest.writeInt(SupplierTax);
        dest.writeInt(SupplierRate);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RateBreakupDto> CREATOR = new Parcelable.Creator<RateBreakupDto>() {
        @Override
        public RateBreakupDto createFromParcel(Parcel in) {
            return new RateBreakupDto(in);
        }

        @Override
        public RateBreakupDto[] newArray(int size) {
            return new RateBreakupDto[size];
        }
    };
}