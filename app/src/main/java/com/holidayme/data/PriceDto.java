package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 08-06-2016.
 */
public class PriceDto implements Parcelable {

    private double NetPrice;
    private double Tax;
    private double Total;
    private boolean TaxIncld;

    public double getNetPrice() {
        return NetPrice;
    }

    public void setNetPrice(double netPrice) {
        NetPrice = netPrice;
    }

    public double getTax() {
        return Tax;
    }

    public void setTax(double tax) {
        Tax = tax;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public boolean isTaxIncld() {
        return TaxIncld;
    }

    public void setTaxIncld(boolean taxIncld) {
        TaxIncld = taxIncld;
    }

    protected PriceDto(Parcel in) {
        NetPrice = in.readDouble();
        Tax = in.readDouble();
        Total = in.readDouble();
        TaxIncld = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(NetPrice);
        dest.writeDouble(Tax);
        dest.writeDouble(Total);
        dest.writeByte((byte) (TaxIncld ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PriceDto> CREATOR = new Parcelable.Creator<PriceDto>() {
        @Override
        public PriceDto createFromParcel(Parcel in) {
            return new PriceDto(in);
        }

        @Override
        public PriceDto[] newArray(int size) {
            return new PriceDto[size];
        }
    };
}