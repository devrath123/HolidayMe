package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 08-06-2016.
 */
public class RoomTypesDto implements Parcelable {
    private String Id;
    private String Ttl;
    private String CancellationPolicy;
    private int RefundableType;
    private PriceDto Price;
    private String Rooms;
    private int MaxPax;
    private ArrayList<String> Includes;
    private boolean IsPayAtHotel;
    private boolean IsCodApplicable;
    private String TarrifNote;
    private String CancellationToDate;
    private String CancellationFromDate;
    private double CancellationAmount;
    private String CancellationCurrencyCode;
    private String PromoTxt;
    private String GroupName;
    private String RoomRateCode;
    private String TaxLabel;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTtl() {
        return Ttl;
    }

    public void setTtl(String ttl) {
        Ttl = ttl;
    }

    public String getCancellationPolicy() {
        return CancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        CancellationPolicy = cancellationPolicy;
    }

    public int getRefundableType() {
        return RefundableType;
    }

    public void setRefundableType(int refundableType) {
        RefundableType = refundableType;
    }

    public PriceDto getPrice() {
        return Price;
    }

    public void setPrice(PriceDto price) {
        Price = price;
    }

    public String getRooms() {
        return Rooms;
    }

    public void setRooms(String rooms) {
        Rooms = rooms;
    }

    public int getMaxPax() {
        return MaxPax;
    }

    public void setMaxPax(int maxPax) {
        MaxPax = maxPax;
    }

    public ArrayList<String> getIncludes() {
        return Includes;
    }

    public void setIncludes(ArrayList<String> includes) {
        Includes = includes;
    }

    public boolean isPayAtHotel() {
        return IsPayAtHotel;
    }

    public void setPayAtHotel(boolean payAtHotel) {
        IsPayAtHotel = payAtHotel;
    }

    public boolean isCodApplicable() {
        return IsCodApplicable;
    }

    public void setCodApplicable(boolean codApplicable) {
        IsCodApplicable = codApplicable;
    }

    public String getTarrifNote() {
        return TarrifNote;
    }

    public void setTarrifNote(String tarrifNote) {
        TarrifNote = tarrifNote;
    }

    public String getCancellationToDate() {
        return CancellationToDate;
    }

    public void setCancellationToDate(String cancellationToDate) {
        CancellationToDate = cancellationToDate;
    }

    public String getCancellationFromDate() {
        return CancellationFromDate;
    }

    public void setCancellationFromDate(String cancellationFromDate) {
        CancellationFromDate = cancellationFromDate;
    }

    public double getCancellationAmount() {
        return CancellationAmount;
    }

    public void setCancellationAmount(double cancellationAmount) {
        CancellationAmount = cancellationAmount;
    }

    public String getCancellationCurrencyCode() {
        return CancellationCurrencyCode;
    }

    public void setCancellationCurrencyCode(String cancellationCurrencyCode) {
        CancellationCurrencyCode = cancellationCurrencyCode;
    }



    public String getPromoTxt() {
        return PromoTxt;
    }

    public void setPromoTxt(String promoTxt) {
        PromoTxt = promoTxt;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getRoomRateCode() {
        return RoomRateCode;
    }

    public void setRoomRateCode(String roomRateCode) {
        RoomRateCode = roomRateCode;
    }

    public String getTaxLabel() {
        return TaxLabel;
    }

    public void setTaxLabel(String taxLabel) {
        TaxLabel = taxLabel;
    }

    protected RoomTypesDto(Parcel in) {
        Id = in.readString();
        Ttl = in.readString();
        CancellationPolicy = in.readString();
        RefundableType = in.readInt();
        Price = (PriceDto) in.readValue(PriceDto.class.getClassLoader());
        Rooms = in.readString();
        MaxPax = in.readInt();
        if (in.readByte() == 0x01) {
            Includes = new ArrayList<String>();
            in.readList(Includes, String.class.getClassLoader());
        } else {
            Includes = null;
        }
        IsPayAtHotel = in.readByte() != 0x00;
        IsCodApplicable = in.readByte() != 0x00;
        TarrifNote = in.readString();
        CancellationToDate = in.readString();
        CancellationFromDate = in.readString();
        CancellationAmount = in.readDouble();
        CancellationCurrencyCode = in.readString();
        PromoTxt = in.readString();
        GroupName = in.readString();
        RoomRateCode = in.readString();
        TaxLabel = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(Ttl);
        dest.writeString(CancellationPolicy);
        dest.writeInt(RefundableType);
        dest.writeValue(Price);
        dest.writeString(Rooms);
        dest.writeInt(MaxPax);
        if (Includes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Includes);
        }
        dest.writeByte((byte) (IsPayAtHotel ? 0x01 : 0x00));
        dest.writeByte((byte) (IsCodApplicable ? 0x01 : 0x00));
        dest.writeString(TarrifNote);
        dest.writeString(CancellationToDate);
        dest.writeString(CancellationFromDate);
        dest.writeDouble(CancellationAmount);
        dest.writeString(CancellationCurrencyCode);
        dest.writeString(PromoTxt);
        dest.writeString(GroupName);
        dest.writeString(RoomRateCode);
        dest.writeString(TaxLabel);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RoomTypesDto> CREATOR = new Parcelable.Creator<RoomTypesDto>() {
        @Override
        public RoomTypesDto createFromParcel(Parcel in) {
            return new RoomTypesDto(in);
        }

        @Override
        public RoomTypesDto[] newArray(int size) {
            return new RoomTypesDto[size];
        }
    };
}