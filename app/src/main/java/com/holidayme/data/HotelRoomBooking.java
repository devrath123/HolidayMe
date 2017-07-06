package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 19-08-2015.
 */
public class HotelRoomBooking implements Parcelable {
    private long BookingPropertyRoomId;
    private long BookingPropertyId;
    private int RoomSrNo;
    private String RoomName;
    private boolean IsExtraBedRequired;
    private boolean IsTwinRequired;
    private int NumberOfBeds;
    private int AdultsCount;
    private String ChildrenAges;
    private int ChildrensCount;
    private String TravellerSalutation;
    private String TravellerFirstName;
    private String TravellerMiddleName;
    private String TravellerLastName;
    private String TravellerCountry;
    private String TravellerFullName;
    private String BedTypeId;
    private String BedTypeDescription;
    private boolean SmokingPreference;
    private String Allocation;
    private double BaseRate;
    private double NetRate;
    private double ExtraGuestCharge;
    private double TaxesAndFees;
    private double ChargeableRate;
    private String SupplierConfirmationNo;
    private int CancellationStatusId;
    private String CancellationNo;
    private int TravellerSalutationId;
    private double HotelOccupancyTax;
    private double SalesTax;
    private double SupplierNetRate;
    private double SellingRate;
    private double SupplierTax;
    private double SupplierRate;
    private ArrayList<RoomRateDailyBreakup> RoomRateDailyBreakups;


    private ArrayList<SupplementDetailsDto> SupplimentDetails;

    public long getBookingPropertyRoomId() {
        return BookingPropertyRoomId;
    }

    public void setBookingPropertyRoomId(long bookingPropertyRoomId) {
        BookingPropertyRoomId = bookingPropertyRoomId;
    }

    public long getBookingPropertyId() {
        return BookingPropertyId;
    }

    public void setBookingPropertyId(long bookingPropertyId) {
        BookingPropertyId = bookingPropertyId;
    }

    public int getRoomSrNo() {
        return RoomSrNo;
    }

    public void setRoomSrNo(int roomSrNo) {
        RoomSrNo = roomSrNo;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public boolean isTwinRequired() {
        return IsTwinRequired;
    }

    public void setIsTwinRequired(boolean isTwinRequired) {
        IsTwinRequired = isTwinRequired;
    }

    public boolean isExtraBedRequired() {
        return IsExtraBedRequired;
    }

    public void setIsExtraBedRequired(boolean isExtraBedRequired) {
        IsExtraBedRequired = isExtraBedRequired;
    }

    public int getNumberOfBeds() {
        return NumberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        NumberOfBeds = numberOfBeds;
    }

    public int getAdultsCount() {
        return AdultsCount;
    }

    public void setAdultsCount(int adultsCount) {
        AdultsCount = adultsCount;
    }

    public String getChildrenAges() {
        return ChildrenAges;
    }

    public void setChildrenAges(String childrenAges) {
        ChildrenAges = childrenAges;
    }

    public int getChildrensCount() {
        return ChildrensCount;
    }

    public void setChildrensCount(int childrensCount) {
        ChildrensCount = childrensCount;
    }

    public String getTravellerSalutation() {
        return TravellerSalutation;
    }

    public void setTravellerSalutation(String travellerSalutation) {
        TravellerSalutation = travellerSalutation;
    }

    public String getTravellerFirstName() {
        return TravellerFirstName;
    }

    public void setTravellerFirstName(String travellerFirstName) {
        TravellerFirstName = travellerFirstName;
    }

    public String getTravellerMiddleName() {
        return TravellerMiddleName;
    }

    public void setTravellerMiddleName(String travellerMiddleName) {
        TravellerMiddleName = travellerMiddleName;
    }

    public String getTravellerLastName() {
        return TravellerLastName;
    }

    public void setTravellerLastName(String travellerLastName) {
        TravellerLastName = travellerLastName;
    }

    public String getTravellerCountry() {
        return TravellerCountry;
    }

    public void setTravellerCountry(String travellerCountry) {
        TravellerCountry = travellerCountry;
    }

    public String getTravellerFullName() {
        return TravellerFullName;
    }

    public void setTravellerFullName(String travellerFullName) {
        TravellerFullName = travellerFullName;
    }

    public String getBedTypeId() {
        return BedTypeId;
    }

    public void setBedTypeId(String bedTypeId) {
        BedTypeId = bedTypeId;
    }

    public String getBedTypeDescription() {
        return BedTypeDescription;
    }

    public void setBedTypeDescription(String bedTypeDescription) {
        BedTypeDescription = bedTypeDescription;
    }

    public boolean isSmokingPreference() {
        return SmokingPreference;
    }

    public void setSmokingPreference(boolean smokingPreference) {
        SmokingPreference = smokingPreference;
    }

    public String getAllocation() {
        return Allocation;
    }

    public void setAllocation(String allocation) {
        Allocation = allocation;
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

    public double getExtraGuestCharge() {
        return ExtraGuestCharge;
    }

    public void setExtraGuestCharge(double extraGuestCharge) {
        ExtraGuestCharge = extraGuestCharge;
    }

    public double getTaxesAndFees() {
        return TaxesAndFees;
    }

    public void setTaxesAndFees(double taxesAndFees) {
        TaxesAndFees = taxesAndFees;
    }

    public double getChargeableRate() {
        return ChargeableRate;
    }

    public void setChargeableRate(double chargeableRate) {
        ChargeableRate = chargeableRate;
    }

    public String getSupplierConfirmationNo() {
        return SupplierConfirmationNo;
    }

    public void setSupplierConfirmationNo(String supplierConfirmationNo) {
        SupplierConfirmationNo = supplierConfirmationNo;
    }

    public int getCancellationStatusId() {
        return CancellationStatusId;
    }

    public void setCancellationStatusId(int cancellationStatusId) {
        CancellationStatusId = cancellationStatusId;
    }

    public String getCancellationNo() {
        return CancellationNo;
    }

    public void setCancellationNo(String cancellationNo) {
        CancellationNo = cancellationNo;
    }

    public int getTravellerSalutationId() {
        return TravellerSalutationId;
    }

    public void setTravellerSalutationId(int travellerSalutationId) {
        TravellerSalutationId = travellerSalutationId;
    }

    public double getHotelOccupancyTax() {
        return HotelOccupancyTax;
    }

    public void setHotelOccupancyTax(double hotelOccupancyTax) {
        HotelOccupancyTax = hotelOccupancyTax;
    }

    public double getSalesTax() {
        return SalesTax;
    }

    public void setSalesTax(double salesTax) {
        SalesTax = salesTax;
    }

    public double getSupplierNetRate() {
        return SupplierNetRate;
    }

    public void setSupplierNetRate(double supplierNetRate) {
        SupplierNetRate = supplierNetRate;
    }

    public double getSupplierTax() {
        return SupplierTax;
    }

    public void setSupplierTax(double supplierTax) {
        SupplierTax = supplierTax;
    }

    public double getSellingRate() {
        return SellingRate;
    }

    public void setSellingRate(double sellingRate) {
        SellingRate = sellingRate;
    }

    public double getSupplierRate() {
        return SupplierRate;
    }

    public void setSupplierRate(double supplierRate) {
        SupplierRate = supplierRate;
    }

    public ArrayList<RoomRateDailyBreakup> getRoomRateDailyBreakups() {
        return RoomRateDailyBreakups;
    }

    public void setRoomRateDailyBreakups(ArrayList<RoomRateDailyBreakup> roomRateDailyBreakups) {
        RoomRateDailyBreakups = roomRateDailyBreakups;
    }

    public ArrayList<SupplementDetailsDto> getSupplimentDetails() {
        return SupplimentDetails;
    }

    public void setSupplimentDetails(ArrayList<SupplementDetailsDto> supplimentDetails) {
        SupplimentDetails = supplimentDetails;
    }





    protected HotelRoomBooking(Parcel in) {
        BookingPropertyRoomId = in.readLong();
        BookingPropertyId = in.readLong();
        RoomSrNo = in.readInt();
        RoomName = in.readString();
        IsExtraBedRequired = in.readByte() != 0x00;
        IsTwinRequired = in.readByte() != 0x00;
        NumberOfBeds = in.readInt();
        AdultsCount = in.readInt();
        ChildrenAges = in.readString();
        ChildrensCount = in.readInt();
        TravellerSalutation = in.readString();
        TravellerFirstName = in.readString();
        TravellerMiddleName = in.readString();
        TravellerLastName = in.readString();
        TravellerCountry = in.readString();
        TravellerFullName = in.readString();
        BedTypeId = in.readString();
        BedTypeDescription = in.readString();
        SmokingPreference = in.readByte() != 0x00;
        Allocation = in.readString();
        BaseRate = in.readDouble();
        NetRate = in.readDouble();
        ExtraGuestCharge = in.readDouble();
        TaxesAndFees = in.readDouble();
        ChargeableRate = in.readDouble();
        SupplierConfirmationNo = in.readString();
        CancellationStatusId = in.readInt();
        CancellationNo = in.readString();
        TravellerSalutationId = in.readInt();
        HotelOccupancyTax = in.readDouble();
        SalesTax = in.readDouble();
        SupplierNetRate = in.readDouble();
        SellingRate = in.readDouble();
        SupplierTax = in.readDouble();
        SupplierRate = in.readDouble();
        if (in.readByte() == 0x01) {
            RoomRateDailyBreakups = new ArrayList<RoomRateDailyBreakup>();
            in.readList(RoomRateDailyBreakups, RoomRateDailyBreakup.class.getClassLoader());
        } else {
            RoomRateDailyBreakups = null;
        }
        if (in.readByte() == 0x01) {
            SupplimentDetails = new ArrayList<SupplementDetailsDto>();
            in.readList(SupplimentDetails, SupplementDetailsDto.class.getClassLoader());
        } else {
            SupplimentDetails = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(BookingPropertyRoomId);
        dest.writeLong(BookingPropertyId);
        dest.writeInt(RoomSrNo);
        dest.writeString(RoomName);
        dest.writeByte((byte) (IsExtraBedRequired ? 0x01 : 0x00));
        dest.writeByte((byte) (IsTwinRequired ? 0x01 : 0x00));
        dest.writeInt(NumberOfBeds);
        dest.writeInt(AdultsCount);
        dest.writeString(ChildrenAges);
        dest.writeInt(ChildrensCount);
        dest.writeString(TravellerSalutation);
        dest.writeString(TravellerFirstName);
        dest.writeString(TravellerMiddleName);
        dest.writeString(TravellerLastName);
        dest.writeString(TravellerCountry);
        dest.writeString(TravellerFullName);
        dest.writeString(BedTypeId);
        dest.writeString(BedTypeDescription);
        dest.writeByte((byte) (SmokingPreference ? 0x01 : 0x00));
        dest.writeString(Allocation);
        dest.writeDouble(BaseRate);
        dest.writeDouble(NetRate);
        dest.writeDouble(ExtraGuestCharge);
        dest.writeDouble(TaxesAndFees);
        dest.writeDouble(ChargeableRate);
        dest.writeString(SupplierConfirmationNo);
        dest.writeInt(CancellationStatusId);
        dest.writeString(CancellationNo);
        dest.writeInt(TravellerSalutationId);
        dest.writeDouble(HotelOccupancyTax);
        dest.writeDouble(SalesTax);
        dest.writeDouble(SupplierNetRate);
        dest.writeDouble(SellingRate);
        dest.writeDouble(SupplierTax);
        dest.writeDouble(SupplierRate);
        if (RoomRateDailyBreakups == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(RoomRateDailyBreakups);
        }
        if (SupplimentDetails == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(SupplimentDetails);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HotelRoomBooking> CREATOR = new Parcelable.Creator<HotelRoomBooking>() {
        @Override
        public HotelRoomBooking createFromParcel(Parcel in) {
            return new HotelRoomBooking(in);
        }

        @Override
        public HotelRoomBooking[] newArray(int size) {
            return new HotelRoomBooking[size];
        }
    };
}