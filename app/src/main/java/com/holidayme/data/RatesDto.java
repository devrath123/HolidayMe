package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 06-07-2016.
 */

public class RatesDto implements Parcelable {
    private ArrayList<SupplierRatesDto> SupplierRates;


    public ArrayList<SupplierRatesDto> getSupplierRates() {
        return SupplierRates;
    }

    public void setSupplierRates(ArrayList<SupplierRatesDto> supplierRates) {
        SupplierRates = supplierRates;
    }



    protected RatesDto(Parcel in) {
        if (in.readByte() == 0x01) {
            SupplierRates = new ArrayList<SupplierRatesDto>();
            in.readList(SupplierRates, SupplierRatesDto.class.getClassLoader());
        } else {
            SupplierRates = null;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (SupplierRates == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(SupplierRates);
        }

    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RatesDto> CREATOR = new Parcelable.Creator<RatesDto>() {
        @Override
        public RatesDto createFromParcel(Parcel in) {
            return new RatesDto(in);
        }

        @Override
        public RatesDto[] newArray(int size) {
            return new RatesDto[size];
        }
    };
}