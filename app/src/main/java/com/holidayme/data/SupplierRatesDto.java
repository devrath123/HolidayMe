package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 06-07-2016.
 */

public class SupplierRatesDto implements Parcelable {
    private Long SupplierId;
    private int SupplierPriority;
    private String SupplierPropertyCode;
    private ArrayList<RoomRatesDto> RoomRates;

    public Long getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(Long supplierId) {
        SupplierId = supplierId;
    }

    public int getSupplierPriority() {
        return SupplierPriority;
    }

    public void setSupplierPriority(int supplierPriority) {
        SupplierPriority = supplierPriority;
    }

    public String getSupplierPropertyCode() {
        return SupplierPropertyCode;
    }

    public void setSupplierPropertyCode(String supplierPropertyCode) {
        SupplierPropertyCode = supplierPropertyCode;
    }

    public ArrayList<RoomRatesDto> getRoomRates() {
        return RoomRates;
    }

    public void setRoomRates(ArrayList<RoomRatesDto> roomRates) {
        RoomRates = roomRates;
    }

    protected SupplierRatesDto(Parcel in) {
        SupplierId = in.readByte() == 0x00 ? null : in.readLong();
        SupplierPriority = in.readInt();
        SupplierPropertyCode = in.readString();
        if (in.readByte() == 0x01) {
            RoomRates = new ArrayList<RoomRatesDto>();
            in.readList(RoomRates, RoomRatesDto.class.getClassLoader());
        } else {
            RoomRates = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (SupplierId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(SupplierId);
        }
        dest.writeInt(SupplierPriority);
        dest.writeString(SupplierPropertyCode);
        if (RoomRates == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(RoomRates);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SupplierRatesDto> CREATOR = new Parcelable.Creator<SupplierRatesDto>() {
        @Override
        public SupplierRatesDto createFromParcel(Parcel in) {
            return new SupplierRatesDto(in);
        }

        @Override
        public SupplierRatesDto[] newArray(int size) {
            return new SupplierRatesDto[size];
        }
    };
}