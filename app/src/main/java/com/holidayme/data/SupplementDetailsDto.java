package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by santosh.patar on 21-09-2015.
 */
public class SupplementDetailsDto implements Parcelable {



    private  int SupplementId;
    private  double SupplementTotalPrice;
    private  int SupplementType;

    /*public List<SupplementAgeGroupDto> SupplementAgeGroups;
    public class SupplementAgeGroupDto
    {
        public int SupplementAgeFrom { get; set; }
        public int SupplementAgeTo { get; set; }
        public double SupplementPrice { get; set; }
        public int SupplementQuantity { get; set; }
    }*/


    public int getSupplementId() {
        return SupplementId;
    }

    public void setSupplementId(int supplementId) {
        SupplementId = supplementId;
    }

    public double getSupplementTotalPrice() {
        return SupplementTotalPrice;
    }

    public void setSupplementTotalPrice(double supplementTotalPrice) {
        SupplementTotalPrice = supplementTotalPrice;
    }

    public int getSupplementType() {
        return SupplementType;
    }

    public void setSupplementType(int supplementType) {
        SupplementType = supplementType;
    }



    protected SupplementDetailsDto(Parcel in) {
        SupplementId = in.readInt();
        SupplementTotalPrice = in.readDouble();
        SupplementType = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(SupplementId);
        dest.writeDouble(SupplementTotalPrice);
        dest.writeInt(SupplementType);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SupplementDetailsDto> CREATOR = new Parcelable.Creator<SupplementDetailsDto>() {
        @Override
        public SupplementDetailsDto createFromParcel(Parcel in) {
            return new SupplementDetailsDto(in);
        }

        @Override
        public SupplementDetailsDto[] newArray(int size) {
            return new SupplementDetailsDto[size];
        }
    };
}