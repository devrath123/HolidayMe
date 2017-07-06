package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 19-08-2015.
 */
public class RoomRateDailyBreakup implements Parcelable {



    private String Date;
    private String Day;
    private int DayIndex;
    private double BaseRate;
    private double NetRate;
    private double TotalTaxes;
    private double MarkupAmount;
    private float MarkupPercentile;
    private double SupplierTax;
    private double SupplierRate;

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

    public double getBaseRate() {
        return BaseRate;
    }

    public void setBaseRate(double baseRate) {
        BaseRate = baseRate;
    }

    public double getNetRate() {
        return NetRate;
    }

    public void setNetRate(double netRate) {
        NetRate = netRate;
    }

    public double getTotalTaxes() {
        return TotalTaxes;
    }

    public void setTotalTaxes(double totalTaxes) {
        TotalTaxes = totalTaxes;
    }

    public double getMarkupAmount() {
        return MarkupAmount;
    }

    public void setMarkupAmount(double markupAmount) {
        MarkupAmount = markupAmount;
    }

    public float getMarkupPercentile() {
        return MarkupPercentile;
    }

    public void setMarkupPercentile(float markupPercentile) {
        MarkupPercentile = markupPercentile;
    }

    public double getSupplierTax() {
        return SupplierTax;
    }

    public void setSupplierTax(double supplierTax) {
        SupplierTax = supplierTax;
    }

    public double getSupplierRate() {
        return SupplierRate;
    }

    public void setSupplierRate(double supplierRate) {
        SupplierRate = supplierRate;
    }

    public RoomRateDailyBreakup() {
        super();
    }

    protected RoomRateDailyBreakup(Parcel in) {
        Date = in.readString();
        Day = in.readString();
        DayIndex = in.readInt();
        BaseRate = in.readDouble();
        NetRate = in.readDouble();
        TotalTaxes = in.readDouble();
        MarkupAmount = in.readDouble();
        MarkupPercentile = in.readFloat();
        SupplierTax = in.readDouble();
        SupplierRate = in.readDouble();
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
        dest.writeDouble(BaseRate);
        dest.writeDouble(NetRate);
        dest.writeDouble(TotalTaxes);
        dest.writeDouble(MarkupAmount);
        dest.writeFloat(MarkupPercentile);
        dest.writeDouble(SupplierTax);
        dest.writeDouble(SupplierRate);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RoomRateDailyBreakup> CREATOR = new Parcelable.Creator<RoomRateDailyBreakup>() {
        @Override
        public RoomRateDailyBreakup createFromParcel(Parcel in) {
            return new RoomRateDailyBreakup(in);
        }

        @Override
        public RoomRateDailyBreakup[] newArray(int size) {
            return new RoomRateDailyBreakup[size];
        }
    };
}