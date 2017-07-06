package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 06-07-2016.
 */

public class BookingDetailsDto implements Parcelable {
    private int AffiliateId;
    private String BookingIpAddress;
    private String CheckInDate;
    private String CheckOutDate;
    private String CountryCode;
    private String CurrencyCode;
    private boolean IsPayAtHotel;
    private boolean IsSameCurrency;
    private ArrayList<OccupanciesDto> Occupancies;
    private String PaymentRedirectUrl;
    private ArrayList<SpecialRequestsDto> SpecialRequests;
    private String TempBookingId;
    private String Address1;
    private String Address2;
    private String Address3;
    private String City;
    private long DestinationId;
    private String LanguageCode;
    private RatesDto Rates;
    private boolean ShowAdditionalTermsAndConditions;
    private String BillingCurrencyCode;
    private double SellingPriceInBillingCurrencyCode;
    private String UserInfo;
    private CodAvailableDto CodAvailable;
    private String SellingCurrency;
    private double SellingPrice;
    private double StarRating;
    private double Taxes;
    private String Title;
    private String TitleLowerCase;
    private String HotelImage;

    public int getAffiliateId() {
        return AffiliateId;
    }

    public void setAffiliateId(int affiliateId) {
        AffiliateId = affiliateId;
    }

    public String getBookingIpAddress() {
        return BookingIpAddress;
    }

    public void setBookingIpAddress(String bookingIpAddress) {
        BookingIpAddress = bookingIpAddress;
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

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public boolean isPayAtHotel() {
        return IsPayAtHotel;
    }

    public void setPayAtHotel(boolean payAtHotel) {
        IsPayAtHotel = payAtHotel;
    }

    public boolean isSameCurrency() {
        return IsSameCurrency;
    }

    public void setSameCurrency(boolean sameCurrency) {
        IsSameCurrency = sameCurrency;
    }

    public ArrayList<OccupanciesDto> getOccupancies() {
        return Occupancies;
    }

    public void setOccupancies(ArrayList<OccupanciesDto> occupancies) {
        Occupancies = occupancies;
    }

    public String getPaymentRedirectUrl() {
        return PaymentRedirectUrl;
    }

    public void setPaymentRedirectUrl(String paymentRedirectUrl) {
        PaymentRedirectUrl = paymentRedirectUrl;
    }

    public ArrayList<SpecialRequestsDto> getSpecialRequests() {
        return SpecialRequests;
    }

    public void setSpecialRequests(ArrayList<SpecialRequestsDto> specialRequests) {
        SpecialRequests = specialRequests;
    }

    public String getTempBookingId() {
        return TempBookingId;
    }

    public void setTempBookingId(String tempBookingId) {
        TempBookingId = tempBookingId;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }

    public String getAddress3() {
        return Address3;
    }

    public void setAddress3(String address3) {
        Address3 = address3;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public long getDestinationId() {
        return DestinationId;
    }

    public void setDestinationId(long destinationId) {
        DestinationId = destinationId;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public RatesDto getRates() {
        return Rates;
    }

    public void setRates(RatesDto rates) {
        Rates = rates;
    }

    public boolean isShowAdditionalTermsAndConditions() {
        return ShowAdditionalTermsAndConditions;
    }

    public void setShowAdditionalTermsAndConditions(boolean showAdditionalTermsAndConditions) {
        ShowAdditionalTermsAndConditions = showAdditionalTermsAndConditions;
    }

    public String getBillingCurrencyCode() {
        return BillingCurrencyCode;
    }

    public void setBillingCurrencyCode(String billingCurrencyCode) {
        BillingCurrencyCode = billingCurrencyCode;
    }

    public double getSellingPriceInBillingCurrencyCode() {
        return SellingPriceInBillingCurrencyCode;
    }

    public void setSellingPriceInBillingCurrencyCode(double sellingPriceInBillingCurrencyCode) {
        SellingPriceInBillingCurrencyCode = sellingPriceInBillingCurrencyCode;
    }

    public String getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(String userInfo) {
        UserInfo = userInfo;
    }

    public CodAvailableDto getCodAvailable() {
        return CodAvailable;
    }

    public void setCodAvailable(CodAvailableDto codAvailable) {
        CodAvailable = codAvailable;
    }

    public String getSellingCurrency() {
        return SellingCurrency;
    }

    public void setSellingCurrency(String sellingCurrency) {
        SellingCurrency = sellingCurrency;
    }

    public double getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public double getTaxes() {
        return Taxes;
    }

    public void setTaxes(double taxes) {
        Taxes = taxes;
    }

    public double getStarRating() {
        return StarRating;
    }

    public void setStarRating(double starRating) {
        StarRating = starRating;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTitleLowerCase() {
        return TitleLowerCase;
    }

    public void setTitleLowerCase(String titleLowerCase) {
        TitleLowerCase = titleLowerCase;
    }

    public String getHotelImage() {
        return HotelImage;
    }

    public void setHotelImage(String hotelImage) {
        HotelImage = hotelImage;
    }

    protected BookingDetailsDto(Parcel in) {
        AffiliateId = in.readInt();
        BookingIpAddress = in.readString();
        CheckInDate = in.readString();
        CheckOutDate = in.readString();
        CountryCode = in.readString();
        CurrencyCode = in.readString();
        IsPayAtHotel = in.readByte() != 0x00;
        IsSameCurrency = in.readByte() != 0x00;
        if (in.readByte() == 0x01) {
            Occupancies = new ArrayList<OccupanciesDto>();
            in.readList(Occupancies, OccupanciesDto.class.getClassLoader());
        } else {
            Occupancies = null;
        }
        PaymentRedirectUrl = in.readString();
        if (in.readByte() == 0x01) {
            SpecialRequests = new ArrayList<SpecialRequestsDto>();
            in.readList(SpecialRequests, SpecialRequestsDto.class.getClassLoader());
        } else {
            SpecialRequests = null;
        }
        TempBookingId = in.readString();
        Address1 = in.readString();
        Address2 = in.readString();
        Address3 = in.readString();
        City = in.readString();
        DestinationId = in.readLong();
        LanguageCode = in.readString();
        Rates = (RatesDto) in.readValue(RatesDto.class.getClassLoader());
        ShowAdditionalTermsAndConditions = in.readByte() != 0x00;
        BillingCurrencyCode = in.readString();
        SellingPriceInBillingCurrencyCode = in.readDouble();
        UserInfo = in.readString();
        CodAvailable = (CodAvailableDto) in.readValue(CodAvailableDto.class.getClassLoader());
        SellingCurrency = in.readString();
        SellingPrice = in.readDouble();
        StarRating = in.readDouble();
        Taxes = in.readDouble();
        Title = in.readString();
        TitleLowerCase = in.readString();
        HotelImage = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(AffiliateId);
        dest.writeString(BookingIpAddress);
        dest.writeString(CheckInDate);
        dest.writeString(CheckOutDate);
        dest.writeString(CountryCode);
        dest.writeString(CurrencyCode);
        dest.writeByte((byte) (IsPayAtHotel ? 0x01 : 0x00));
        dest.writeByte((byte) (IsSameCurrency ? 0x01 : 0x00));
        if (Occupancies == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Occupancies);
        }
        dest.writeString(PaymentRedirectUrl);
        if (SpecialRequests == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(SpecialRequests);
        }
        dest.writeString(TempBookingId);
        dest.writeString(Address1);
        dest.writeString(Address2);
        dest.writeString(Address3);
        dest.writeString(City);
        dest.writeLong(DestinationId);
        dest.writeString(LanguageCode);
        dest.writeValue(Rates);
        dest.writeByte((byte) (ShowAdditionalTermsAndConditions ? 0x01 : 0x00));
        dest.writeString(BillingCurrencyCode);
        dest.writeDouble(SellingPriceInBillingCurrencyCode);
        dest.writeString(UserInfo);
        dest.writeValue(CodAvailable);
        dest.writeString(SellingCurrency);
        dest.writeDouble(SellingPrice);
        dest.writeDouble(StarRating);
        dest.writeDouble(Taxes);
        dest.writeString(Title);
        dest.writeString(TitleLowerCase);
        dest.writeString(HotelImage);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BookingDetailsDto> CREATOR = new Parcelable.Creator<BookingDetailsDto>() {
        @Override
        public BookingDetailsDto createFromParcel(Parcel in) {
            return new BookingDetailsDto(in);
        }

        @Override
        public BookingDetailsDto[] newArray(int size) {
            return new BookingDetailsDto[size];
        }
    };
}