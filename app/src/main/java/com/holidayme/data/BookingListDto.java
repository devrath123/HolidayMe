package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 23-10-2015.
 */
public class BookingListDto implements Parcelable {
    private long HolzooBookingId;
    private String TempBookingId;
    private long EntityBookingId;
    private int BookingStatus;
    private int BookingType;
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
    private FlightDto Flight;
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



    public int getBookingType() {
        return BookingType;
    }

    public void setBookingType(int bookingType) {
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

    public double getDisplayAmount() {
        return DisplayAmount;
    }

    public void setDisplayAmount(double displayAmount) {
        DisplayAmount = displayAmount;
    }

    public double getBillingAmount() {
        return BillingAmount;
    }

    public void setBillingAmount(double billingAmount) {
        BillingAmount = billingAmount;
    }

    public String getDisplayCurrencyType() {
        return DisplayCurrencyType;
    }

    public void setDisplayCurrencyType(String displayCurrencyType) {
        DisplayCurrencyType = displayCurrencyType;
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

    public FlightDto getFlight() {
        return Flight;
    }

    public void setFlight(FlightDto flight) {
        Flight = flight;
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

    public int getBookingStatus() {
        return BookingStatus;
    }

    public void setBookingStatus(int bookingStatus) {
        BookingStatus = bookingStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.HolzooBookingId);
        dest.writeString(this.TempBookingId);
        dest.writeLong(this.EntityBookingId);
        dest.writeInt(this.BookingStatus);
        dest.writeInt(this.BookingType);
        dest.writeString(this.TotalCharges);
        dest.writeString(this.CheckInDate);
        dest.writeString(this.CheckOutDate);
        dest.writeString(this.City);
        dest.writeString(this.Country);
        dest.writeString(this.PostalCode);
        dest.writeString(this.TransferType);
        dest.writeString(this.AirportName);
        dest.writeString(this.TransferArea);
        dest.writeDouble(this.DisplayAmount);
        dest.writeDouble(this.BillingAmount);
        dest.writeString(this.DisplayCurrencyType);
        dest.writeString(this.BillingCurrencyType);
        dest.writeByte(this.IsUpcoming ? (byte) 1 : (byte) 0);
        dest.writeString(this.Duration);
        dest.writeString(this.ListingThumbnailUrl);
        dest.writeString(this.Title);
        dest.writeString(this.PropertyAddress);
        dest.writeInt(this.AdultsCount);
        dest.writeInt(this.ChildrensCount);
        dest.writeString(this.ConfirmationNumber);
        dest.writeString(this.BookingDate);
        dest.writeString(this.DepartureTime);
        dest.writeParcelable(this.Flight, flags);
        dest.writeString(this.DiscountedAmount);
        dest.writeString(this.SellingCurrencyType);
    }

    public BookingListDto() {
    }

    protected BookingListDto(Parcel in) {
        this.HolzooBookingId = in.readLong();
        this.TempBookingId = in.readString();
        this.EntityBookingId = in.readLong();
        this.BookingStatus = in.readInt();
        this.BookingType = in.readInt();
        this.TotalCharges = in.readString();
        this.CheckInDate = in.readString();
        this.CheckOutDate = in.readString();
        this.City = in.readString();
        this.Country = in.readString();
        this.PostalCode = in.readString();
        this.TransferType = in.readString();
        this.AirportName = in.readString();
        this.TransferArea = in.readString();
        this.DisplayAmount = in.readDouble();
        this.BillingAmount = in.readDouble();
        this.DisplayCurrencyType = in.readString();
        this.BillingCurrencyType = in.readString();
        this.IsUpcoming = in.readByte() != 0;
        this.Duration = in.readString();
        this.ListingThumbnailUrl = in.readString();
        this.Title = in.readString();
        this.PropertyAddress = in.readString();
        this.AdultsCount = in.readInt();
        this.ChildrensCount = in.readInt();
        this.ConfirmationNumber = in.readString();
        this.BookingDate = in.readString();
        this.DepartureTime = in.readString();
        this.Flight = in.readParcelable(FlightDto.class.getClassLoader());
        this.DiscountedAmount = in.readString();
        this.SellingCurrencyType = in.readString();
    }

    public static final Creator<BookingListDto> CREATOR = new Creator<BookingListDto>() {
        @Override
        public BookingListDto createFromParcel(Parcel source) {
            return new BookingListDto(source);
        }

        @Override
        public BookingListDto[] newArray(int size) {
            return new BookingListDto[size];
        }
    };
}