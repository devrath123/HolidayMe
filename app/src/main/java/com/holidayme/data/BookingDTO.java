package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 19-08-2015.
 */
public class BookingDTO<T> implements Parcelable {


    private long HolzooBookingId;
    private int BookingType;
    private String BookedBy;
    private String BookedOn;
    private String LastModifiedBy;
    private String LastModifiedOn;
    private String BuyingCurrencyType;
    private double BuyingPrice;
    private String SellingCurrencyType;
    private double SellingPrice;
    private String BillingCurrencyType;
    private double BillingPrice;
    private String ArrivalDate;
    private String DepartureDate;
    private String CustomerIPAddress;
    private String CustomerUserAgent;
    private String GuestSalutation;
    private String GuestFirstName;
    private String GuestMiddleName;
    private String GuestLastName;
    private String GuestAddress;
    private String GuestMobileNumber;
    private String CountryOfResidence;
    private String PassportCountry;
    private int OwnerType;
    private String Comments;
    private int SupplierBookingStatus;
    private String CODTrackingId;
    private long CODSupplierId;
    private int CODRequestStatus;
    private String LanguageCode;
    private boolean IsCod;
    private double TotalPriceInUSD;

    public T BookingEntity;


    public void setHolzooBookingId(int holzooBookingId) {
        HolzooBookingId = holzooBookingId;
    }

    public int getBookingType() {
        return BookingType;
    }

    public void setBookingType(int bookingType) {
        BookingType = bookingType;
    }

    public String getBookedBy() {
        return BookedBy;
    }

    public void setBookedBy(String bookedBy) {
        BookedBy = bookedBy;
    }

    public String getBookedOn() {
        return BookedOn;
    }

    public void setBookedOn(String bookedOn) {
        BookedOn = bookedOn;
    }

    public String getLastModifiedBy() {
        return LastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        LastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedOn() {
        return LastModifiedOn;
    }

    public void setLastModifiedOn(String lastModifiedOn) {
        LastModifiedOn = lastModifiedOn;
    }

    public String getBuyingCurrencyType() {
        return BuyingCurrencyType;
    }

    public void setBuyingCurrencyType(String buyingCurrencyType) {
        BuyingCurrencyType = buyingCurrencyType;
    }

    public double getBuyingPrice() {
        return BuyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        BuyingPrice = buyingPrice;
    }

    public String getSellingCurrencyType() {
        return SellingCurrencyType;
    }

    public void setSellingCurrencyType(String sellingCurrencyType) {
        SellingCurrencyType = sellingCurrencyType;
    }

    public double getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public String getBillingCurrencyType() {
        return BillingCurrencyType;
    }

    public void setBillingCurrencyType(String billingCurrencyType) {
        BillingCurrencyType = billingCurrencyType;
    }

    public String getDepartureDate() {
        return DepartureDate;
    }

    public void setDepartureDate(String departureDate) {
        DepartureDate = departureDate;
    }

    public double getBillingPrice() {
        return BillingPrice;
    }

    public void setBillingPrice(double billingPrice) {
        BillingPrice = billingPrice;
    }

    public double getTotalPriceInUSD() {
        return TotalPriceInUSD;
    }

    public void setTotalPriceInUSD(double totalPriceInUSD) {
        TotalPriceInUSD = totalPriceInUSD;
    }

    public String getArrivalDate() {
        return ArrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        ArrivalDate = arrivalDate;
    }

    public String getCustomerIPAddress() {
        return CustomerIPAddress;
    }

    public void setCustomerIPAddress(String customerIPAddress) {
        CustomerIPAddress = customerIPAddress;
    }

    public String getCustomerUserAgent() {
        return CustomerUserAgent;
    }

    public void setCustomerUserAgent(String customerUserAgent) {
        CustomerUserAgent = customerUserAgent;
    }

    public String getGuestSalutation() {
        return GuestSalutation;
    }

    public void setGuestSalutation(String guestSalutation) {
        GuestSalutation = guestSalutation;
    }

    public String getGuestFirstName() {
        return GuestFirstName;
    }

    public void setGuestFirstName(String guestFirstName) {
        GuestFirstName = guestFirstName;
    }

    public String getGuestMiddleName() {
        return GuestMiddleName;
    }

    public void setGuestMiddleName(String guestMiddleName) {
        GuestMiddleName = guestMiddleName;
    }

    public String getGuestLastName() {
        return GuestLastName;
    }

    public void setGuestLastName(String guestLastName) {
        GuestLastName = guestLastName;
    }

    public String getGuestAddress() {
        return GuestAddress;
    }

    public void setGuestAddress(String guestAddress) {
        GuestAddress = guestAddress;
    }

    public String getGuestMobileNumber() {
        return GuestMobileNumber;
    }

    public void setGuestMobileNumber(String guestMobileNumber) {
        GuestMobileNumber = guestMobileNumber;
    }

    public String getCountryOfResidence() {
        return CountryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        CountryOfResidence = countryOfResidence;
    }

    public String getPassportCountry() {
        return PassportCountry;
    }

    public void setPassportCountry(String passportCountry) {
        PassportCountry = passportCountry;
    }

    public int getOwnerType() {
        return OwnerType;
    }

    public void setOwnerType(int ownerType) {
        OwnerType = ownerType;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public int getSupplierBookingStatus() {
        return SupplierBookingStatus;
    }

    public void setSupplierBookingStatus(int supplierBookingStatus) {
        SupplierBookingStatus = supplierBookingStatus;
    }

    public String getCODTrackingId() {
        return CODTrackingId;
    }

    public void setCODTrackingId(String CODTrackingId) {
        this.CODTrackingId = CODTrackingId;
    }


    public void setCODSupplierId(int CODSupplierId) {
        this.CODSupplierId = CODSupplierId;
    }

    public int getCODRequestStatus() {
        return CODRequestStatus;
    }

    public void setCODRequestStatus(int CODRequestStatus) {
        this.CODRequestStatus = CODRequestStatus;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public boolean isCod() {
        return IsCod;
    }

    public void setIsCod(boolean isCod) {
        IsCod = isCod;
    }

    public T getBookingEntity() {
        return BookingEntity;
    }

    public void setBookingEntity(T bookingEntity) {
        BookingEntity = bookingEntity;
    }

    public long getHolzooBookingId() {
        return HolzooBookingId;
    }

    public void setHolzooBookingId(long holzooBookingId) {
        HolzooBookingId = holzooBookingId;
    }

    public long getCODSupplierId() {
        return CODSupplierId;
    }

    public void setCODSupplierId(long CODSupplierId) {
        this.CODSupplierId = CODSupplierId;
    }


    protected BookingDTO(Parcel in) {
        HolzooBookingId = in.readLong();
        BookingType = in.readInt();
        BookedBy = in.readString();
        BookedOn = in.readString();
        LastModifiedBy = in.readString();
        LastModifiedOn = in.readString();
        BuyingCurrencyType = in.readString();
        BuyingPrice = in.readDouble();
        SellingCurrencyType = in.readString();
        SellingPrice = in.readDouble();
        BillingCurrencyType = in.readString();
        BillingPrice = in.readDouble();
        ArrivalDate = in.readString();
        DepartureDate = in.readString();
        CustomerIPAddress = in.readString();
        CustomerUserAgent = in.readString();
        GuestSalutation = in.readString();
        GuestFirstName = in.readString();
        GuestMiddleName = in.readString();
        GuestLastName = in.readString();
        GuestAddress = in.readString();
        GuestMobileNumber = in.readString();
        CountryOfResidence = in.readString();
        PassportCountry = in.readString();
        OwnerType = in.readInt();
        Comments = in.readString();
        SupplierBookingStatus = in.readInt();
        CODTrackingId = in.readString();
        CODSupplierId = in.readLong();
        CODRequestStatus = in.readInt();
        LanguageCode = in.readString();
        IsCod = in.readByte() != 0x00;
        TotalPriceInUSD = in.readDouble();
        BookingEntity = (T) in.readValue(BookingDTO.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(HolzooBookingId);
        dest.writeInt(BookingType);
        dest.writeString(BookedBy);
        dest.writeString(BookedOn);
        dest.writeString(LastModifiedBy);
        dest.writeString(LastModifiedOn);
        dest.writeString(BuyingCurrencyType);
        dest.writeDouble(BuyingPrice);
        dest.writeString(SellingCurrencyType);
        dest.writeDouble(SellingPrice);
        dest.writeString(BillingCurrencyType);
        dest.writeDouble(BillingPrice);
        dest.writeString(ArrivalDate);
        dest.writeString(DepartureDate);
        dest.writeString(CustomerIPAddress);
        dest.writeString(CustomerUserAgent);
        dest.writeString(GuestSalutation);
        dest.writeString(GuestFirstName);
        dest.writeString(GuestMiddleName);
        dest.writeString(GuestLastName);
        dest.writeString(GuestAddress);
        dest.writeString(GuestMobileNumber);
        dest.writeString(CountryOfResidence);
        dest.writeString(PassportCountry);
        dest.writeInt(OwnerType);
        dest.writeString(Comments);
        dest.writeInt(SupplierBookingStatus);
        dest.writeString(CODTrackingId);
        dest.writeLong(CODSupplierId);
        dest.writeInt(CODRequestStatus);
        dest.writeString(LanguageCode);
        dest.writeByte((byte) (IsCod ? 0x01 : 0x00));
        dest.writeDouble(TotalPriceInUSD);
        dest.writeValue(BookingEntity);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BookingDTO> CREATOR = new Parcelable.Creator<BookingDTO>() {
        @Override
        public BookingDTO createFromParcel(Parcel in) {
            return new BookingDTO(in);
        }

        @Override
        public BookingDTO[] newArray(int size) {
            return new BookingDTO[size];
        }
    };
}