package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 06-07-2016.
 */

public class RoomRatesDto implements Parcelable {
    private String SupplierRoomTypeId;
    private String SupplierRoomTypeName;
    private int SupplierSubType;
    private String BuyingCurrency;
    private String SellingCurrency;
    private double TotalNetRate;
    private double TotalBuyingPrice;
    private double TotalSellingPrice;
    private double TotalTaxesPrice;
    private ArrayList<RoomsDto> Rooms;
    private int RoomTypeRateKey;
    private String MealPlans;
    private String ValueAdds;
    private boolean IsPayAtHotel;
    private String Type;
    private String Code;
    private String CancelationPolicy;
    private ArrayList<String> Amenities;
    private  String TariffNotes;

    public String getTariffNotes() {
        return TariffNotes;
    }

    public void setTariffNotes(String tariffNotes) {
        TariffNotes = tariffNotes;
    }

    public String getSupplierRoomTypeId() {
        return SupplierRoomTypeId;
    }

    public void setSupplierRoomTypeId(String supplierRoomTypeId) {
        SupplierRoomTypeId = supplierRoomTypeId;
    }

    public String getSupplierRoomTypeName() {
        return SupplierRoomTypeName;
    }

    public void setSupplierRoomTypeName(String supplierRoomTypeName) {
        SupplierRoomTypeName = supplierRoomTypeName;
    }

    public int getSupplierSubType() {
        return SupplierSubType;
    }

    public void setSupplierSubType(int supplierSubType) {
        SupplierSubType = supplierSubType;
    }

    public String getBuyingCurrency() {
        return BuyingCurrency;
    }

    public void setBuyingCurrency(String buyingCurrency) {
        BuyingCurrency = buyingCurrency;
    }

    public double getTotalNetRate() {
        return TotalNetRate;
    }

    public void setTotalNetRate(double totalNetRate) {
        TotalNetRate = totalNetRate;
    }

    public String getSellingCurrency() {
        return SellingCurrency;
    }

    public void setSellingCurrency(String sellingCurrency) {
        SellingCurrency = sellingCurrency;
    }

    public double getTotalBuyingPrice() {
        return TotalBuyingPrice;
    }

    public void setTotalBuyingPrice(double totalBuyingPrice) {
        TotalBuyingPrice = totalBuyingPrice;
    }

    public double getTotalSellingPrice() {
        return TotalSellingPrice;
    }

    public void setTotalSellingPrice(double totalSellingPrice) {
        TotalSellingPrice = totalSellingPrice;
    }

    public double getTotalTaxesPrice() {
        return TotalTaxesPrice;
    }

    public void setTotalTaxesPrice(double totalTaxesPrice) {
        TotalTaxesPrice = totalTaxesPrice;
    }

    public ArrayList<RoomsDto> getRooms() {
        return Rooms;
    }

    public void setRooms(ArrayList<RoomsDto> rooms) {
        Rooms = rooms;
    }

    public int getRoomTypeRateKey() {
        return RoomTypeRateKey;
    }

    public void setRoomTypeRateKey(int roomTypeRateKey) {
        RoomTypeRateKey = roomTypeRateKey;
    }

    public String getMealPlans() {
        return MealPlans;
    }

    public void setMealPlans(String mealPlans) {
        MealPlans = mealPlans;
    }

    public String getValueAdds() {
        return ValueAdds;
    }

    public void setValueAdds(String valueAdds) {
        ValueAdds = valueAdds;
    }

    public boolean isPayAtHotel() {
        return IsPayAtHotel;
    }

    public void setPayAtHotel(boolean payAtHotel) {
        IsPayAtHotel = payAtHotel;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getCancelationPolicy() {
        return CancelationPolicy;
    }

    public void setCancelationPolicy(String cancelationPolicy) {
        CancelationPolicy = cancelationPolicy;
    }

    public ArrayList<String> getAmenities() {
        return Amenities;
    }

    public void setAmenities(ArrayList<String> amenities) {
        Amenities = amenities;
    }

    protected RoomRatesDto(Parcel in) {
        TariffNotes=in.readString();
        SupplierRoomTypeId = in.readString();
        SupplierRoomTypeName = in.readString();
        SupplierSubType = in.readInt();
        BuyingCurrency = in.readString();
        SellingCurrency = in.readString();
        TotalNetRate = in.readDouble();
        TotalBuyingPrice = in.readDouble();
        TotalSellingPrice = in.readDouble();
        TotalTaxesPrice = in.readDouble();
        if (in.readByte() == 0x01) {
            Rooms = new ArrayList<RoomsDto>();
            in.readList(Rooms, RoomsDto.class.getClassLoader());
        } else {
            Rooms = null;
        }
        RoomTypeRateKey = in.readInt();
        MealPlans = in.readString();
        ValueAdds = in.readString();
        IsPayAtHotel = in.readByte() != 0x00;
        Type = in.readString();
        Code = in.readString();
        CancelationPolicy = in.readString();
        if (in.readByte() == 0x01) {
            Amenities = new ArrayList<String>();
            in.readList(Amenities, String.class.getClassLoader());
        } else {
            Amenities = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TariffNotes);
        dest.writeString(SupplierRoomTypeId);
        dest.writeString(SupplierRoomTypeName);
        dest.writeInt(SupplierSubType);
        dest.writeString(BuyingCurrency);
        dest.writeString(SellingCurrency);
        dest.writeDouble(TotalNetRate);
        dest.writeDouble(TotalBuyingPrice);
        dest.writeDouble(TotalSellingPrice);
        dest.writeDouble(TotalTaxesPrice);
        if (Rooms == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Rooms);
        }
        dest.writeInt(RoomTypeRateKey);
        dest.writeString(MealPlans);
        dest.writeString(ValueAdds);
        dest.writeByte((byte) (IsPayAtHotel ? 0x01 : 0x00));
        dest.writeString(Type);
        dest.writeString(Code);
        dest.writeString(CancelationPolicy);
        if (Amenities == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Amenities);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RoomRatesDto> CREATOR = new Parcelable.Creator<RoomRatesDto>() {
        @Override
        public RoomRatesDto createFromParcel(Parcel in) {
            return new RoomRatesDto(in);
        }

        @Override
        public RoomRatesDto[] newArray(int size) {
            return new RoomRatesDto[size];
        }
    };
}