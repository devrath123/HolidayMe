package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 19-08-2015.
 */
public class HotelBookingDTO implements Parcelable {
    private long BookingPropertyId;
    private String ItineraryId;
    private long HolzooPropertyId;
    private String SupplierPropertyId;
    private String PropertyName;
    private String PropertyAddress;
    private String PropertyCity;
    private String PropertyCountry;
    private String PropertyPostalCode;
    private String IsRefundable;
    private int SupplierId;
    private String SupplierType;
    private String CheckInDate;
    private String CheckOutDate;
    private String RateKey;
    private String RateCode;
    private String RoomTypeCode;
    private String SupplierRoomTypeCode;
    private double ConversionRatio;
    private double AppliedDiscount;
    private int HotelBookingStatus;
    private String CancelationPolicy;
    private int RoomTypeRateKey;
    private String LanguageCode;
    private String UserTrackingId;
    private String CountryCode;
    private String ValueAdds;
    private int SupplierSubType;
    private String GeneralHotelPolicies;
    private String CheckInInstuctions;
    private int SupplierRatePlanType;
    private String SupplierDestinationId;
    private int SupplierBookingStatus;
    private String TariffNotes;
    private String SupplierPaymentGuaranty;
    private double SupplierMinimumSellingPrice;
    private String SupplierCurrencyType;
    private String CardType;
    private String CardNumber;
    private String CardExpiryDate;
    private int TransactionStatusId;
    private String TransactionId;
    private String TransactionRefNo;
    private String PgProviderName;
    private double CommissionableAmount;
    private double MarkupAmount;
    private double AppliedMarkup;
    private double ROE;
    private int ROETopUpPercentile;
    private boolean IsMspApplied;
    private double PgTransactionResponse;
    private double SellingPrice;
    private String HotelContactNo;
    private String WebsiteUrl;
    private String TempBookingId;
    private String PromoCode;
    private double PromoCodePercent;
    private double PromoCodeAmount;
    private double BuyingPrice;
    private String PurchaseToken;
    private String ServiceProductUniqueId;
    private String SupplierAgencyReffNumber;
    private boolean IsCod;

    private boolean IsPayAtHotel;

    private ArrayList<HotelRoomBooking> HotelRoomBookings;

    public long getBookingPropertyId() {
        return BookingPropertyId;
    }

    public void setBookingPropertyId(long bookingPropertyId) {
        BookingPropertyId = bookingPropertyId;
    }

    public String getItineraryId() {
        return ItineraryId;
    }

    public void setItineraryId(String itineraryId) {
        ItineraryId = itineraryId;
    }

    public long getHolzooPropertyId() {
        return HolzooPropertyId;
    }

    public void setHolzooPropertyId(long holzooPropertyId) {
        HolzooPropertyId = holzooPropertyId;
    }

    public String getSupplierPropertyId() {
        return SupplierPropertyId;
    }

    public void setSupplierPropertyId(String supplierPropertyId) {
        SupplierPropertyId = supplierPropertyId;
    }

    public String getPropertyName() {
        return PropertyName;
    }

    public void setPropertyName(String propertyName) {
        PropertyName = propertyName;
    }

    public String getPropertyAddress() {
        return PropertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        PropertyAddress = propertyAddress;
    }

    public String getPropertyCity() {
        return PropertyCity;
    }

    public void setPropertyCity(String propertyCity) {
        PropertyCity = propertyCity;
    }

    public String getPropertyCountry() {
        return PropertyCountry;
    }

    public void setPropertyCountry(String propertyCountry) {
        PropertyCountry = propertyCountry;
    }

    public String getPropertyPostalCode() {
        return PropertyPostalCode;
    }

    public void setPropertyPostalCode(String propertyPostalCode) {
        PropertyPostalCode = propertyPostalCode;
    }

    public String getIsRefundable() {
        return IsRefundable;
    }

    public void setIsRefundable(String isRefundable) {
        IsRefundable = isRefundable;
    }

    public int getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(int supplierId) {
        SupplierId = supplierId;
    }

    public String getSupplierType() {
        return SupplierType;
    }

    public void setSupplierType(String supplierType) {
        SupplierType = supplierType;
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

    public String getRateKey() {
        return RateKey;
    }

    public void setRateKey(String rateKey) {
        RateKey = rateKey;
    }

    public String getRateCode() {
        return RateCode;
    }

    public void setRateCode(String rateCode) {
        RateCode = rateCode;
    }

    public String getRoomTypeCode() {
        return RoomTypeCode;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        RoomTypeCode = roomTypeCode;
    }

    public String getSupplierRoomTypeCode() {
        return SupplierRoomTypeCode;
    }

    public void setSupplierRoomTypeCode(String supplierRoomTypeCode) {
        SupplierRoomTypeCode = supplierRoomTypeCode;
    }

    public double getConversionRatio() {
        return ConversionRatio;
    }

    public void setConversionRatio(double conversionRatio) {
        ConversionRatio = conversionRatio;
    }

    public double getAppliedDiscount() {
        return AppliedDiscount;
    }

    public void setAppliedDiscount(double appliedDiscount) {
        AppliedDiscount = appliedDiscount;
    }

    public int getHotelBookingStatus() {
        return HotelBookingStatus;
    }

    public void setHotelBookingStatus(int hotelBookingStatus) {
        HotelBookingStatus = hotelBookingStatus;
    }

    public int getRoomTypeRateKey() {
        return RoomTypeRateKey;
    }

    public void setRoomTypeRateKey(int roomTypeRateKey) {
        RoomTypeRateKey = roomTypeRateKey;
    }

    public String getCancelationPolicy() {
        return CancelationPolicy;
    }

    public void setCancelationPolicy(String cancelationPolicy) {
        CancelationPolicy = cancelationPolicy;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public String getUserTrackingId() {
        return UserTrackingId;
    }

    public void setUserTrackingId(String userTrackingId) {
        UserTrackingId = userTrackingId;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getValueAdds() {
        return ValueAdds;
    }

    public void setValueAdds(String valueAdds) {
        ValueAdds = valueAdds;
    }

    public int getSupplierSubType() {
        return SupplierSubType;
    }

    public void setSupplierSubType(int supplierSubType) {
        SupplierSubType = supplierSubType;
    }

    public String getGeneralHotelPolicies() {
        return GeneralHotelPolicies;
    }

    public void setGeneralHotelPolicies(String generalHotelPolicies) {
        GeneralHotelPolicies = generalHotelPolicies;
    }

    public String getCheckInInstuctions() {
        return CheckInInstuctions;
    }

    public void setCheckInInstuctions(String checkInInstuctions) {
        CheckInInstuctions = checkInInstuctions;
    }

    public int getSupplierRatePlanType() {
        return SupplierRatePlanType;
    }

    public void setSupplierRatePlanType(int supplierRatePlanType) {
        SupplierRatePlanType = supplierRatePlanType;
    }

    public String getSupplierDestinationId() {
        return SupplierDestinationId;
    }

    public void setSupplierDestinationId(String supplierDestinationId) {
        SupplierDestinationId = supplierDestinationId;
    }

    public int getSupplierBookingStatus() {
        return SupplierBookingStatus;
    }

    public void setSupplierBookingStatus(int supplierBookingStatus) {
        SupplierBookingStatus = supplierBookingStatus;
    }

    public String getTariffNotes() {
        return TariffNotes;
    }

    public void setTariffNotes(String tariffNotes) {
        TariffNotes = tariffNotes;
    }

    public String getSupplierPaymentGuaranty() {
        return SupplierPaymentGuaranty;
    }

    public void setSupplierPaymentGuaranty(String supplierPaymentGuaranty) {
        SupplierPaymentGuaranty = supplierPaymentGuaranty;
    }

    public double getSupplierMinimumSellingPrice() {
        return SupplierMinimumSellingPrice;
    }

    public void setSupplierMinimumSellingPrice(double supplierMinimumSellingPrice) {
        SupplierMinimumSellingPrice = supplierMinimumSellingPrice;
    }

    public String getSupplierCurrencyType() {
        return SupplierCurrencyType;
    }

    public void setSupplierCurrencyType(String supplierCurrencyType) {
        SupplierCurrencyType = supplierCurrencyType;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getCardExpiryDate() {
        return CardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        CardExpiryDate = cardExpiryDate;
    }

    public int getTransactionStatusId() {
        return TransactionStatusId;
    }

    public void setTransactionStatusId(int transactionStatusId) {
        TransactionStatusId = transactionStatusId;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public String getTransactionRefNo() {
        return TransactionRefNo;
    }

    public void setTransactionRefNo(String transactionRefNo) {
        TransactionRefNo = transactionRefNo;
    }

    public String getPgProviderName() {
        return PgProviderName;
    }

    public void setPgProviderName(String pgProviderName) {
        PgProviderName = pgProviderName;
    }

    public double getCommissionableAmount() {
        return CommissionableAmount;
    }

    public void setCommissionableAmount(double commissionableAmount) {
        CommissionableAmount = commissionableAmount;
    }

    public double getMarkupAmount() {
        return MarkupAmount;
    }

    public void setMarkupAmount(double markupAmount) {
        MarkupAmount = markupAmount;
    }

    public double getAppliedMarkup() {
        return AppliedMarkup;
    }

    public void setAppliedMarkup(double appliedMarkup) {
        AppliedMarkup = appliedMarkup;
    }

    public double getROE() {
        return ROE;
    }

    public void setROE(double ROE) {
        this.ROE = ROE;
    }

    public int getROETopUpPercentile() {
        return ROETopUpPercentile;
    }

    public void setROETopUpPercentile(int ROETopUpPercentile) {
        this.ROETopUpPercentile = ROETopUpPercentile;
    }

    public boolean isMspApplied() {
        return IsMspApplied;
    }

    public void setIsMspApplied(boolean isMspApplied) {
        IsMspApplied = isMspApplied;
    }

    public double getPgTransactionResponse() {
        return PgTransactionResponse;
    }

    public void setPgTransactionResponse(double pgTransactionResponse) {
        PgTransactionResponse = pgTransactionResponse;
    }

    public double getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public String getHotelContactNo() {
        return HotelContactNo;
    }

    public void setHotelContactNo(String hotelContactNo) {
        HotelContactNo = hotelContactNo;
    }

    public String getWebsiteUrl() {
        return WebsiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        WebsiteUrl = websiteUrl;
    }

    public String getTempBookingId() {
        return TempBookingId;
    }

    public void setTempBookingId(String tempBookingId) {
        TempBookingId = tempBookingId;
    }

    public String getPromoCode() {
        return PromoCode;
    }

    public void setPromoCode(String promoCode) {
        PromoCode = promoCode;
    }

    public double getPromoCodePercent() {
        return PromoCodePercent;
    }

    public void setPromoCodePercent(double promoCodePercent) {
        PromoCodePercent = promoCodePercent;
    }

    public double getPromoCodeAmount() {
        return PromoCodeAmount;
    }

    public void setPromoCodeAmount(double promoCodeAmount) {
        PromoCodeAmount = promoCodeAmount;
    }

    public double getBuyingPrice() {
        return BuyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        BuyingPrice = buyingPrice;
    }

    public String getPurchaseToken() {
        return PurchaseToken;
    }

    public void setPurchaseToken(String purchaseToken) {
        PurchaseToken = purchaseToken;
    }

    public String getServiceProductUniqueId() {
        return ServiceProductUniqueId;
    }

    public void setServiceProductUniqueId(String serviceProductUniqueId) {
        ServiceProductUniqueId = serviceProductUniqueId;
    }

    public String getSupplierAgencyReffNumber() {
        return SupplierAgencyReffNumber;
    }

    public void setSupplierAgencyReffNumber(String supplierAgencyReffNumber) {
        SupplierAgencyReffNumber = supplierAgencyReffNumber;
    }

    public boolean isCod() {
        return IsCod;
    }

    public void setIsCod(boolean isCod) {
        IsCod = isCod;
    }

    public ArrayList<HotelRoomBooking> getHotelRoomBookings() {
        return HotelRoomBookings;
    }

    public void setHotelRoomBookings(ArrayList<HotelRoomBooking> hotelRoomBookings) {
        HotelRoomBookings = hotelRoomBookings;
    }

    public boolean isPayAtHotel() {
        return IsPayAtHotel;
    }

    public void setIsPayAtHotel(boolean isPayAtHotel) {
        IsPayAtHotel = isPayAtHotel;
    }



    protected HotelBookingDTO(Parcel in) {
        BookingPropertyId = in.readLong();
        ItineraryId = in.readString();
        HolzooPropertyId = in.readLong();
        SupplierPropertyId = in.readString();
        PropertyName = in.readString();
        PropertyAddress = in.readString();
        PropertyCity = in.readString();
        PropertyCountry = in.readString();
        PropertyPostalCode = in.readString();
        IsRefundable = in.readString();
        SupplierId = in.readInt();
        SupplierType = in.readString();
        CheckInDate = in.readString();
        CheckOutDate = in.readString();
        RateKey = in.readString();
        RateCode = in.readString();
        RoomTypeCode = in.readString();
        SupplierRoomTypeCode = in.readString();
        ConversionRatio = in.readDouble();
        AppliedDiscount = in.readDouble();
        HotelBookingStatus = in.readInt();
        CancelationPolicy = in.readString();
        RoomTypeRateKey = in.readInt();
        LanguageCode = in.readString();
        UserTrackingId = in.readString();
        CountryCode = in.readString();
        ValueAdds = in.readString();
        SupplierSubType = in.readInt();
        GeneralHotelPolicies = in.readString();
        CheckInInstuctions = in.readString();
        SupplierRatePlanType = in.readInt();
        SupplierDestinationId = in.readString();
        SupplierBookingStatus = in.readInt();
        TariffNotes = in.readString();
        SupplierPaymentGuaranty = in.readString();
        SupplierMinimumSellingPrice = in.readDouble();
        SupplierCurrencyType = in.readString();
        CardType = in.readString();
        CardNumber = in.readString();
        CardExpiryDate = in.readString();
        TransactionStatusId = in.readInt();
        TransactionId = in.readString();
        TransactionRefNo = in.readString();
        PgProviderName = in.readString();
        CommissionableAmount = in.readDouble();
        MarkupAmount = in.readDouble();
        AppliedMarkup = in.readDouble();
        ROE = in.readDouble();
        ROETopUpPercentile = in.readInt();
        IsMspApplied = in.readByte() != 0x00;
        PgTransactionResponse = in.readDouble();
        SellingPrice = in.readDouble();
        HotelContactNo = in.readString();
        WebsiteUrl = in.readString();
        TempBookingId = in.readString();
        PromoCode = in.readString();
        PromoCodePercent = in.readDouble();
        PromoCodeAmount = in.readDouble();
        BuyingPrice = in.readDouble();
        PurchaseToken = in.readString();
        ServiceProductUniqueId = in.readString();
        SupplierAgencyReffNumber = in.readString();
        IsCod = in.readByte() != 0x00;
        IsPayAtHotel = in.readByte() != 0x00;
        if (in.readByte() == 0x01) {
            HotelRoomBookings = new ArrayList<HotelRoomBooking>();
            in.readList(HotelRoomBookings, HotelRoomBooking.class.getClassLoader());
        } else {
            HotelRoomBookings = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(BookingPropertyId);
        dest.writeString(ItineraryId);
        dest.writeLong(HolzooPropertyId);
        dest.writeString(SupplierPropertyId);
        dest.writeString(PropertyName);
        dest.writeString(PropertyAddress);
        dest.writeString(PropertyCity);
        dest.writeString(PropertyCountry);
        dest.writeString(PropertyPostalCode);
        dest.writeString(IsRefundable);
        dest.writeInt(SupplierId);
        dest.writeString(SupplierType);
        dest.writeString(CheckInDate);
        dest.writeString(CheckOutDate);
        dest.writeString(RateKey);
        dest.writeString(RateCode);
        dest.writeString(RoomTypeCode);
        dest.writeString(SupplierRoomTypeCode);
        dest.writeDouble(ConversionRatio);
        dest.writeDouble(AppliedDiscount);
        dest.writeInt(HotelBookingStatus);
        dest.writeString(CancelationPolicy);
        dest.writeInt(RoomTypeRateKey);
        dest.writeString(LanguageCode);
        dest.writeString(UserTrackingId);
        dest.writeString(CountryCode);
        dest.writeString(ValueAdds);
        dest.writeInt(SupplierSubType);
        dest.writeString(GeneralHotelPolicies);
        dest.writeString(CheckInInstuctions);
        dest.writeInt(SupplierRatePlanType);
        dest.writeString(SupplierDestinationId);
        dest.writeInt(SupplierBookingStatus);
        dest.writeString(TariffNotes);
        dest.writeString(SupplierPaymentGuaranty);
        dest.writeDouble(SupplierMinimumSellingPrice);
        dest.writeString(SupplierCurrencyType);
        dest.writeString(CardType);
        dest.writeString(CardNumber);
        dest.writeString(CardExpiryDate);
        dest.writeInt(TransactionStatusId);
        dest.writeString(TransactionId);
        dest.writeString(TransactionRefNo);
        dest.writeString(PgProviderName);
        dest.writeDouble(CommissionableAmount);
        dest.writeDouble(MarkupAmount);
        dest.writeDouble(AppliedMarkup);
        dest.writeDouble(ROE);
        dest.writeInt(ROETopUpPercentile);
        dest.writeByte((byte) (IsMspApplied ? 0x01 : 0x00));
        dest.writeDouble(PgTransactionResponse);
        dest.writeDouble(SellingPrice);
        dest.writeString(HotelContactNo);
        dest.writeString(WebsiteUrl);
        dest.writeString(TempBookingId);
        dest.writeString(PromoCode);
        dest.writeDouble(PromoCodePercent);
        dest.writeDouble(PromoCodeAmount);
        dest.writeDouble(BuyingPrice);
        dest.writeString(PurchaseToken);
        dest.writeString(ServiceProductUniqueId);
        dest.writeString(SupplierAgencyReffNumber);
        dest.writeByte((byte) (IsCod ? 0x01 : 0x00));
        dest.writeByte((byte) (IsPayAtHotel ? 0x01 : 0x00));
        if (HotelRoomBookings == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(HotelRoomBookings);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HotelBookingDTO> CREATOR = new Parcelable.Creator<HotelBookingDTO>() {
        @Override
        public HotelBookingDTO createFromParcel(Parcel in) {
            return new HotelBookingDTO(in);
        }

        @Override
        public HotelBookingDTO[] newArray(int size) {
            return new HotelBookingDTO[size];
        }
    };
}