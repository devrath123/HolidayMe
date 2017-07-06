package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 13-04-2017.
 */

public class GetawayBookingConfirmationResponse implements Parcelable {
    private long BookingId,BookingDetailId,PackageId,SubPackageId,HotelId,CityId;
    private String PackageTitle,SubPackageTitle,HotelTitle,Salutation,TravellerFirstName,TravellerMiddleName,TravellerLastName,TravellerEmailId,TravellerPhoneNumber,
            ImportantInformation,Inclusions,PackageCategories,CancellationPolicy,BookingPolicy,CheckInDate,CheckOutDate,BookingDate,CardFirstSixDigits,CardLastFourDigits,
            BuyingCurrency,SellingCurrency,Country,City,ProductAddress,ReceiptNumber,InvoiceNumber,ListingThumbnailUrl,RevenueInUsd;
    private int NoOfPackages,Nights,BookingStatus,SupplierStatusId,TransactionStatus;
    private double BuyingPrice,SellingPrice,PerPackagePriceInUserCurrency,TotalPriceInUserCurrency;
    private boolean IsFraud;
    private ArrayList<TravelerDetailDto>TravelerDetail;

    public long getBookingId() {
        return BookingId;
    }

    public void setBookingId(long bookingId) {
        BookingId = bookingId;
    }

    public long getBookingDetailId() {
        return BookingDetailId;
    }

    public void setBookingDetailId(long bookingDetailId) {
        BookingDetailId = bookingDetailId;
    }

    public long getPackageId() {
        return PackageId;
    }

    public void setPackageId(long packageId) {
        PackageId = packageId;
    }

    public long getSubPackageId() {
        return SubPackageId;
    }

    public void setSubPackageId(long subPackageId) {
        SubPackageId = subPackageId;
    }

    public long getCityId() {
        return CityId;
    }

    public void setCityId(long cityId) {
        CityId = cityId;
    }

    public long getHotelId() {
        return HotelId;
    }

    public void setHotelId(long hotelId) {
        HotelId = hotelId;
    }

    public String getPackageTitle() {
        return PackageTitle;
    }

    public void setPackageTitle(String packageTitle) {
        PackageTitle = packageTitle;
    }

    public String getSubPackageTitle() {
        return SubPackageTitle;
    }

    public void setSubPackageTitle(String subPackageTitle) {
        SubPackageTitle = subPackageTitle;
    }

    public String getHotelTitle() {
        return HotelTitle;
    }

    public void setHotelTitle(String hotelTitle) {
        HotelTitle = hotelTitle;
    }

    public String getSalutation() {
        return Salutation;
    }

    public void setSalutation(String salutation) {
        Salutation = salutation;
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

    public String getTravellerEmailId() {
        return TravellerEmailId;
    }

    public void setTravellerEmailId(String travellerEmailId) {
        TravellerEmailId = travellerEmailId;
    }

    public String getTravellerPhoneNumber() {
        return TravellerPhoneNumber;
    }

    public void setTravellerPhoneNumber(String travellerPhoneNumber) {
        TravellerPhoneNumber = travellerPhoneNumber;
    }

    public String getImportantInformation() {
        return ImportantInformation;
    }

    public void setImportantInformation(String importantInformation) {
        ImportantInformation = importantInformation;
    }

    public String getInclusions() {
        return Inclusions;
    }

    public void setInclusions(String inclusions) {
        Inclusions = inclusions;
    }

    public String getCancellationPolicy() {
        return CancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        CancellationPolicy = cancellationPolicy;
    }

    public String getPackageCategories() {
        return PackageCategories;
    }

    public void setPackageCategories(String packageCategories) {
        PackageCategories = packageCategories;
    }

    public String getBookingPolicy() {
        return BookingPolicy;
    }

    public void setBookingPolicy(String bookingPolicy) {
        BookingPolicy = bookingPolicy;
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

    public String getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(String bookingDate) {
        BookingDate = bookingDate;
    }

    public String getCardFirstSixDigits() {
        return CardFirstSixDigits;
    }

    public void setCardFirstSixDigits(String cardFirstSixDigits) {
        CardFirstSixDigits = cardFirstSixDigits;
    }

    public String getCardLastFourDigits() {
        return CardLastFourDigits;
    }

    public void setCardLastFourDigits(String cardLastFourDigits) {
        CardLastFourDigits = cardLastFourDigits;
    }

    public String getBuyingCurrency() {
        return BuyingCurrency;
    }

    public void setBuyingCurrency(String buyingCurrency) {
        BuyingCurrency = buyingCurrency;
    }

    public String getSellingCurrency() {
        return SellingCurrency;
    }

    public void setSellingCurrency(String sellingCurrency) {
        SellingCurrency = sellingCurrency;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getProductAddress() {
        return ProductAddress;
    }

    public void setProductAddress(String productAddress) {
        ProductAddress = productAddress;
    }

    public String getReceiptNumber() {
        return ReceiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        ReceiptNumber = receiptNumber;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public String getListingThumbnailUrl() {
        return ListingThumbnailUrl;
    }

    public void setListingThumbnailUrl(String listingThumbnailUrl) {
        ListingThumbnailUrl = listingThumbnailUrl;
    }

    public int getNoOfPackages() {
        return NoOfPackages;
    }

    public void setNoOfPackages(int noOfPackages) {
        NoOfPackages = noOfPackages;
    }

    public int getNights() {
        return Nights;
    }

    public void setNights(int nights) {
        Nights = nights;
    }

    public int getBookingStatus() {
        return BookingStatus;
    }

    public void setBookingStatus(int bookingStatus) {
        BookingStatus = bookingStatus;
    }

    public int getSupplierStatusId() {
        return SupplierStatusId;
    }

    public void setSupplierStatusId(int supplierStatusId) {
        SupplierStatusId = supplierStatusId;
    }

    public int getTransactionStatus() {
        return TransactionStatus;
    }

    public void setTransactionStatus(int transactionStatus) {
        TransactionStatus = transactionStatus;
    }

    public double getBuyingPrice() {
        return BuyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        BuyingPrice = buyingPrice;
    }

    public double getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public double getPerPackagePriceInUserCurrency() {
        return PerPackagePriceInUserCurrency;
    }

    public void setPerPackagePriceInUserCurrency(double perPackagePriceInUserCurrency) {
        PerPackagePriceInUserCurrency = perPackagePriceInUserCurrency;
    }

    public double getTotalPriceInUserCurrency() {
        return TotalPriceInUserCurrency;
    }

    public void setTotalPriceInUserCurrency(double totalPriceInUserCurrency) {
        TotalPriceInUserCurrency = totalPriceInUserCurrency;
    }

    public ArrayList<TravelerDetailDto> getTravelerDetail() {
        return TravelerDetail;
    }

    public void setTravelerDetail(ArrayList<TravelerDetailDto> travelerDetail) {
        TravelerDetail = travelerDetail;
    }

    public String getRevenueInUsd() {
        return RevenueInUsd;
    }

    public void setRevenueInUsd(String revenueInUsd) {
        RevenueInUsd = revenueInUsd;
    }

    public boolean isFraud() {
        return IsFraud;
    }

    public void setFraud(boolean fraud) {
        IsFraud = fraud;
    }


    public GetawayBookingConfirmationResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.BookingId);
        dest.writeLong(this.BookingDetailId);
        dest.writeLong(this.PackageId);
        dest.writeLong(this.SubPackageId);
        dest.writeLong(this.HotelId);
        dest.writeLong(this.CityId);
        dest.writeString(this.PackageTitle);
        dest.writeString(this.SubPackageTitle);
        dest.writeString(this.HotelTitle);
        dest.writeString(this.Salutation);
        dest.writeString(this.TravellerFirstName);
        dest.writeString(this.TravellerMiddleName);
        dest.writeString(this.TravellerLastName);
        dest.writeString(this.TravellerEmailId);
        dest.writeString(this.TravellerPhoneNumber);
        dest.writeString(this.ImportantInformation);
        dest.writeString(this.Inclusions);
        dest.writeString(this.PackageCategories);
        dest.writeString(this.CancellationPolicy);
        dest.writeString(this.BookingPolicy);
        dest.writeString(this.CheckInDate);
        dest.writeString(this.CheckOutDate);
        dest.writeString(this.BookingDate);
        dest.writeString(this.CardFirstSixDigits);
        dest.writeString(this.CardLastFourDigits);
        dest.writeString(this.BuyingCurrency);
        dest.writeString(this.SellingCurrency);
        dest.writeString(this.Country);
        dest.writeString(this.City);
        dest.writeString(this.ProductAddress);
        dest.writeString(this.ReceiptNumber);
        dest.writeString(this.InvoiceNumber);
        dest.writeString(this.ListingThumbnailUrl);
        dest.writeString(this.RevenueInUsd);
        dest.writeInt(this.NoOfPackages);
        dest.writeInt(this.Nights);
        dest.writeInt(this.BookingStatus);
        dest.writeInt(this.SupplierStatusId);
        dest.writeInt(this.TransactionStatus);
        dest.writeDouble(this.BuyingPrice);
        dest.writeDouble(this.SellingPrice);
        dest.writeDouble(this.PerPackagePriceInUserCurrency);
        dest.writeDouble(this.TotalPriceInUserCurrency);
        dest.writeByte(this.IsFraud ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.TravelerDetail);
    }

    protected GetawayBookingConfirmationResponse(Parcel in) {
        this.BookingId = in.readLong();
        this.BookingDetailId = in.readLong();
        this.PackageId = in.readLong();
        this.SubPackageId = in.readLong();
        this.HotelId = in.readLong();
        this.CityId = in.readLong();
        this.PackageTitle = in.readString();
        this.SubPackageTitle = in.readString();
        this.HotelTitle = in.readString();
        this.Salutation = in.readString();
        this.TravellerFirstName = in.readString();
        this.TravellerMiddleName = in.readString();
        this.TravellerLastName = in.readString();
        this.TravellerEmailId = in.readString();
        this.TravellerPhoneNumber = in.readString();
        this.ImportantInformation = in.readString();
        this.Inclusions = in.readString();
        this.PackageCategories = in.readString();
        this.CancellationPolicy = in.readString();
        this.BookingPolicy = in.readString();
        this.CheckInDate = in.readString();
        this.CheckOutDate = in.readString();
        this.BookingDate = in.readString();
        this.CardFirstSixDigits = in.readString();
        this.CardLastFourDigits = in.readString();
        this.BuyingCurrency = in.readString();
        this.SellingCurrency = in.readString();
        this.Country = in.readString();
        this.City = in.readString();
        this.ProductAddress = in.readString();
        this.ReceiptNumber = in.readString();
        this.InvoiceNumber = in.readString();
        this.ListingThumbnailUrl = in.readString();
        this.RevenueInUsd = in.readString();
        this.NoOfPackages = in.readInt();
        this.Nights = in.readInt();
        this.BookingStatus = in.readInt();
        this.SupplierStatusId = in.readInt();
        this.TransactionStatus = in.readInt();
        this.BuyingPrice = in.readDouble();
        this.SellingPrice = in.readDouble();
        this.PerPackagePriceInUserCurrency = in.readDouble();
        this.TotalPriceInUserCurrency = in.readDouble();
        this.IsFraud = in.readByte() != 0;
        this.TravelerDetail = in.createTypedArrayList(TravelerDetailDto.CREATOR);
    }

    public static final Creator<GetawayBookingConfirmationResponse> CREATOR = new Creator<GetawayBookingConfirmationResponse>() {
        @Override
        public GetawayBookingConfirmationResponse createFromParcel(Parcel source) {
            return new GetawayBookingConfirmationResponse(source);
        }

        @Override
        public GetawayBookingConfirmationResponse[] newArray(int size) {
            return new GetawayBookingConfirmationResponse[size];
        }
    };
}
