package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 14-08-2015.
 */
public class RoomRateDTO implements Parcelable {
    private String SupplierRoomTypeId;
    private String SupplierRoomTypeName;
    private  PropertySupplierSubTypes SupplierSubType;
    private double TotalNetRate;
    private double TotalTaxesPrice;
    private int RoomTypeRateKey;
    private String MealPlans;
    private String ValueAdds;
    private String TariffNotes;
    private String CancellationPolicy;
    private int IsRefundable;
    private int MaxAdults;
    private int MaxChildren;
    private String Code;
    private String Type;
    private String CheckInInstructions;
    private String Description;
    private boolean IsPayAtHotel;
    private String DisplayAmountLabel=null;
    private String TaxLabel=null;
    private double DisplayAmount=0.0;



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

    public PropertySupplierSubTypes getSupplierSubType() {
        return SupplierSubType;
    }

    public void setSupplierSubType(PropertySupplierSubTypes supplierSubType) {
        SupplierSubType = supplierSubType;
    }

    public double getTotalTaxesPrice() {
        return TotalTaxesPrice;
    }

    public void setTotalTaxesPrice(double totalTaxesPrice) {
        TotalTaxesPrice = totalTaxesPrice;
    }

    public double getTotalNetRate() {
        return TotalNetRate;
    }

    public void setTotalNetRate(double totalNetRate) {
        TotalNetRate = totalNetRate;
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

    public String getTariffNotes() {
        return TariffNotes;
    }

    public void setTariffNotes(String tariffNotes) {
        TariffNotes = tariffNotes;
    }

    public String getCancellationPolicy() {
        return CancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        CancellationPolicy = cancellationPolicy;
    }

    public int getIsRefundable() {
        return IsRefundable;
    }

    public void setIsRefundable(int isRefundable) {
        IsRefundable = isRefundable;
    }

    public int getMaxAdults() {
        return MaxAdults;
    }

    public void setMaxAdults(int maxAdults) {
        MaxAdults = maxAdults;
    }

    public int getMaxChildren() {
        return MaxChildren;
    }

    public void setMaxChildren(int maxChildren) {
        MaxChildren = maxChildren;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCheckInInstructions() {
        return CheckInInstructions;
    }

    public void setCheckInInstructions(String checkInInstructions) {
        CheckInInstructions = checkInInstructions;
    }

    public boolean isPayAtHotel() {
        return IsPayAtHotel;
    }

    public void setIsPayAtHotel(boolean isPayAtHotel) {
        IsPayAtHotel = isPayAtHotel;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }





    public double getDisplayAmount() {
        return DisplayAmount;
    }

    public void setDisplayAmount(double displayAmount) {
        DisplayAmount = displayAmount;
    }

    public String getDisplayAmountLabel() {

        return DisplayAmountLabel;
    }

    public void setDisplayAmountLabel(String displayAmountLabel) {
        DisplayAmountLabel = displayAmountLabel;
    }

    public String getTaxLabel() {
        return TaxLabel;
    }

    public void setTaxLabel(String taxLabel) {
        TaxLabel = taxLabel;
    }

    protected RoomRateDTO(Parcel in) {
        SupplierRoomTypeId = in.readString();
        SupplierRoomTypeName = in.readString();
        SupplierSubType = (PropertySupplierSubTypes) in.readValue(PropertySupplierSubTypes.class.getClassLoader());
        TotalNetRate = in.readDouble();
        TotalTaxesPrice = in.readDouble();
        RoomTypeRateKey = in.readInt();
        MealPlans = in.readString();
        ValueAdds = in.readString();
        TariffNotes = in.readString();
        CancellationPolicy = in.readString();
        IsRefundable = in.readInt();
        MaxAdults = in.readInt();
        MaxChildren = in.readInt();
        Code = in.readString();
        Type = in.readString();
        CheckInInstructions = in.readString();
        IsPayAtHotel = in.readByte() != 0x00;
        Description=in.readString();
        DisplayAmount=in.readDouble();
        DisplayAmountLabel=in.readString();
        TaxLabel=in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(SupplierRoomTypeId);
        dest.writeString(SupplierRoomTypeName);
        dest.writeValue(SupplierSubType);
        dest.writeDouble(TotalNetRate);
        dest.writeDouble(TotalTaxesPrice);
        dest.writeInt(RoomTypeRateKey);
        dest.writeString(MealPlans);
        dest.writeString(ValueAdds);
        dest.writeString(TariffNotes);
        dest.writeString(CancellationPolicy);
        dest.writeInt(IsRefundable);
        dest.writeInt(MaxAdults);
        dest.writeInt(MaxChildren);
        dest.writeString(Code);
        dest.writeString(Type);
        dest.writeString(CheckInInstructions);
        dest.writeByte((byte) (IsPayAtHotel ? 0x01 : 0x00));
        dest.writeString(Description);
        dest.writeDouble(DisplayAmount);
        dest.writeString(DisplayAmountLabel);
        dest.writeString(TaxLabel);

    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RoomRateDTO> CREATOR = new Parcelable.Creator<RoomRateDTO>() {
        @Override
        public RoomRateDTO createFromParcel(Parcel in) {
            return new RoomRateDTO(in);
        }

        @Override
        public RoomRateDTO[] newArray(int size) {
            return new RoomRateDTO[size];
        }
    };
}