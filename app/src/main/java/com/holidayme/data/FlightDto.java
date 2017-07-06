package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 13-01-2016.
 */
public class FlightDto implements Parcelable {
    private long HolzooBookingId;
    private String TempBookingId;
    private long EntityBookingId;
    private String BookingStatus;
    private String BookingType;
    private String TotalCharges;
    private String CheckInDate;
    private String CheckOutDate;
    private String City;
    private String Country;
    private String PostalCode;
    private String TransferType;
    private String AirportName;
    private String TransferArea;
    private double DisplayAmount;
    private double BillingAmount;
    private String DisplayCurrencyType;
    private String BillingCurrencyType;
    private boolean IsUpcoming;
    private String Duration;
    private String ListingThumbnailUrl;
    private String Title;
    private String PropertyAddress;
    private int AdultsCount;
    private int ChildrensCount;
    private String ConfirmationNumber;
    private String BookingDate;
    private String DepartureTime;
    private String DiscountedAmount;
    private String SellingCurrencyType;

    public long getHolzooBookingId() {
        return HolzooBookingId;
    }

    public void setHolzooBookingId(long holzooBookingId) {
        HolzooBookingId = holzooBookingId;
    }

    public String getTempBookingId() {
        return TempBookingId;
    }

    public void setTempBookingId(String tempBookingId) {
        TempBookingId = tempBookingId;
    }

    public long getEntityBookingId() {
        return EntityBookingId;
    }

    public void setEntityBookingId(long entityBookingId) {
        EntityBookingId = entityBookingId;
    }

    public String getBookingStatus() {
        return BookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        BookingStatus = bookingStatus;
    }

    public String getBookingType() {
        return BookingType;
    }

    public void setBookingType(String bookingType) {
        BookingType = bookingType;
    }

    public String getTotalCharges() {
        return TotalCharges;
    }

    public void setTotalCharges(String totalCharges) {
        TotalCharges = totalCharges;
    }

    public String getCheckInDate() {
        return CheckInDate;
    }

    public void setCheckInDate(String checkInDate) {
        CheckInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return CheckOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        CheckOutDate = checkOutDate;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getTransferType() {
        return TransferType;
    }

    public void setTransferType(String transferType) {
        TransferType = transferType;
    }

    public String getAirportName() {
        return AirportName;
    }

    public void setAirportName(String airportName) {
        AirportName = airportName;
    }

    public String getTransferArea() {
        return TransferArea;
    }

    public void setTransferArea(String transferArea) {
        TransferArea = transferArea;
    }

    public String getDisplayCurrencyType() {
        return DisplayCurrencyType;
    }

    public void setDisplayCurrencyType(String displayCurrencyType) {
        DisplayCurrencyType = displayCurrencyType;
    }

    public double getBillingAmount() {
        return BillingAmount;
    }

    public void setBillingAmount(double billingAmount) {
        BillingAmount = billingAmount;
    }

    public double getDisplayAmount() {
        return DisplayAmount;
    }

    public void setDisplayAmount(double displayAmount) {
        DisplayAmount = displayAmount;
    }

    public String getBillingCurrencyType() {
        return BillingCurrencyType;
    }

    public void setBillingCurrencyType(String billingCurrencyType) {
        BillingCurrencyType = billingCurrencyType;
    }

    public boolean isUpcoming() {
        return IsUpcoming;
    }

    public void setIsUpcoming(boolean isUpcoming) {
        IsUpcoming = isUpcoming;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getListingThumbnailUrl() {
        return ListingThumbnailUrl;
    }

    public void setListingThumbnailUrl(String listingThumbnailUrl) {
        ListingThumbnailUrl = listingThumbnailUrl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPropertyAddress() {
        return PropertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        PropertyAddress = propertyAddress;
    }

    public int getAdultsCount() {
        return AdultsCount;
    }

    public void setAdultsCount(int adultsCount) {
        AdultsCount = adultsCount;
    }

    public int getChildrensCount() {
        return ChildrensCount;
    }

    public void setChildrensCount(int childrensCount) {
        ChildrensCount = childrensCount;
    }

    public String getConfirmationNumber() {
        return ConfirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        ConfirmationNumber = confirmationNumber;
    }

    public String getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(String bookingDate) {
        BookingDate = bookingDate;
    }

    public String getDepartureTime() {
        return DepartureTime;
    }

    public void setDepartureTime(String departureTime) {
        DepartureTime = departureTime;
    }

    public String getDiscountedAmount() {
        return DiscountedAmount;
    }

    public void setDiscountedAmount(String discountedAmount) {
        DiscountedAmount = discountedAmount;
    }

    public String getSellingCurrencyType() {
        return SellingCurrencyType;
    }

    public void setSellingCurrencyType(String sellingCurrencyType) {
        SellingCurrencyType = sellingCurrencyType;
    }


    protected FlightDto(Parcel in) {
        HolzooBookingId = in.readLong();
        TempBookingId = in.readString();
        EntityBookingId = in.readLong();
        BookingStatus = in.readString();
        BookingType = in.readString();
        TotalCharges = in.readString();
        CheckInDate = in.readString();
        CheckOutDate = in.readString();
        City = in.readString();
        Country = in.readString();
        PostalCode = in.readString();
        TransferType = in.readString();
        AirportName = in.readString();
        TransferArea = in.readString();
        DisplayAmount = in.readDouble();
        BillingAmount = in.readDouble();
        DisplayCurrencyType = in.readString();
        BillingCurrencyType = in.readString();
        IsUpcoming = in.readByte() != 0x00;
        Duration = in.readString();
        ListingThumbnailUrl = in.readString();
        Title = in.readString();
        PropertyAddress = in.readString();
        AdultsCount = in.readInt();
        ChildrensCount = in.readInt();
        ConfirmationNumber = in.readString();
        BookingDate = in.readString();
        DepartureTime = in.readString();
        DiscountedAmount = in.readString();
        SellingCurrencyType = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(HolzooBookingId);
        dest.writeString(TempBookingId);
        dest.writeLong(EntityBookingId);
        dest.writeString(BookingStatus);
        dest.writeString(BookingType);
        dest.writeString(TotalCharges);
        dest.writeString(CheckInDate);
        dest.writeString(CheckOutDate);
        dest.writeString(City);
        dest.writeString(Country);
        dest.writeString(PostalCode);
        dest.writeString(TransferType);
        dest.writeString(AirportName);
        dest.writeString(TransferArea);
        dest.writeDouble(DisplayAmount);
        dest.writeDouble(BillingAmount);
        dest.writeString(DisplayCurrencyType);
        dest.writeString(BillingCurrencyType);
        dest.writeByte((byte) (IsUpcoming ? 0x01 : 0x00));
        dest.writeString(Duration);
        dest.writeString(ListingThumbnailUrl);
        dest.writeString(Title);
        dest.writeString(PropertyAddress);
        dest.writeInt(AdultsCount);
        dest.writeInt(ChildrensCount);
        dest.writeString(ConfirmationNumber);
        dest.writeString(BookingDate);
        dest.writeString(DepartureTime);
        dest.writeString(DiscountedAmount);
        dest.writeString(SellingCurrencyType);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FlightDto> CREATOR = new Parcelable.Creator<FlightDto>() {
        @Override
        public FlightDto createFromParcel(Parcel in) {
            return new FlightDto(in);
        }

        @Override
        public FlightDto[] newArray(int size) {
            return new FlightDto[size];
        }
    };
}