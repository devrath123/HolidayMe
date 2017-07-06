package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 09-03-2017.
 */

public class PackagesDto implements Parcelable {
    private long PackageId,AreaId,CountryId,StateId,HotelId,CityId;
    private String Title,HotelName,HotelInclusion,HotelPolicy,BuyingCurrency,Description,Address1,Address2,HotelExclusion,ListingImage,
    TermsAndCondition,CityEn,CityName,CountryName,TripAdvisorRating;
    private int Popularity;
    private boolean IsExlusiveDeal,IsSellingFast;

    private double StartFromPrice,SlashRate,ConversionRate,StartRating,Distance;

    public long getPackageId() {
        return PackageId;
    }

    public void setPackageId(long packageId) {
        PackageId = packageId;
    }

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String hotelName) {
        HotelName = hotelName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public long getAreaId() {
        return AreaId;
    }

    public void setAreaId(long areaId) {
        AreaId = areaId;
    }

    public String getHotelInclusion() {
        return HotelInclusion;
    }

    public void setHotelInclusion(String hotelInclusion) {
        HotelInclusion = hotelInclusion;
    }

    public String getHotelPolicy() {
        return HotelPolicy;
    }

    public void setHotelPolicy(String hotelPolicy) {
        HotelPolicy = hotelPolicy;
    }

    public long getCountryId() {
        return CountryId;
    }

    public void setCountryId(long countryId) {
        CountryId = countryId;
    }

    public String getBuyingCurrency() {
        return BuyingCurrency;
    }

    public void setBuyingCurrency(String buyingCurrency) {
        BuyingCurrency = buyingCurrency;
    }

    public long getHotelId() {
        return HotelId;
    }

    public void setHotelId(long hotelId) {
        HotelId = hotelId;
    }

    public int getPopularity() {
        return Popularity;
    }

    public void setPopularity(int popularity) {
        Popularity = popularity;
    }

    public long getStateId() {
        return StateId;
    }

    public void setStateId(long stateId) {
        StateId = stateId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getStartFromPrice() {
        return StartFromPrice;
    }

    public void setStartFromPrice(double startFromPrice) {
        StartFromPrice = startFromPrice;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public String getHotelExclusion() {
        return HotelExclusion;
    }

    public void setHotelExclusion(String hotelExclusion) {
        HotelExclusion = hotelExclusion;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }

    public boolean getIsSellingFast() {
        return IsSellingFast;
    }

    public void setIsSellingFast(boolean isSellingFast) {
        IsSellingFast = isSellingFast;
    }

    public long getCityId() {
        return CityId;
    }

    public void setCityId(long cityId) {
        CityId = cityId;
    }

    public String getListingImage() {
        return ListingImage;
    }

    public void setListingImage(String listingImage) {
        ListingImage = listingImage;
    }

    public double getStartRating() {
        return StartRating;
    }

    public void setStartRating(double startRating) {
        StartRating = startRating;
    }

    public double getDistance() {
        return Distance;
    }

    public void setDistance(double distance) {
        Distance = distance;
    }

    public boolean getIsExlusiveDeal() {
        return IsExlusiveDeal;
    }

    public void setIsExlusiveDeal(boolean isExlusiveDeal) {
        IsExlusiveDeal = isExlusiveDeal;
    }

    public String getTermsAndCondition() {
        return TermsAndCondition;
    }

    public void setTermsAndCondition(String termsAndCondition) {
        TermsAndCondition = termsAndCondition;
    }

    public String getCityEn() {
        return CityEn;
    }

    public void setCityEn(String cityEn) {
        CityEn = cityEn;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }







    public String getTripAdvisorRating() {
        return TripAdvisorRating;
    }

    public void setTripAdvisorRating(String tripAdvisorRating) {
        TripAdvisorRating = tripAdvisorRating;
    }

    public double getSlashRate() {
        return SlashRate;
    }

    public void setSlashRate(double slashRate) {
        SlashRate = slashRate;
    }

    public double getConversionRate() {
        return ConversionRate;
    }

    public void setConversionRate(double conversionRate) {
        ConversionRate = conversionRate;
    }



    public PackagesDto() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.PackageId);
        dest.writeLong(this.AreaId);
        dest.writeLong(this.CountryId);
        dest.writeLong(this.StateId);
        dest.writeLong(this.HotelId);
        dest.writeLong(this.CityId);
        dest.writeString(this.Title);
        dest.writeString(this.HotelName);
        dest.writeString(this.HotelInclusion);
        dest.writeString(this.HotelPolicy);
        dest.writeString(this.BuyingCurrency);
        dest.writeString(this.Description);
        dest.writeString(this.Address1);
        dest.writeString(this.Address2);
        dest.writeString(this.HotelExclusion);
        dest.writeString(this.ListingImage);
        dest.writeString(this.TermsAndCondition);
        dest.writeString(this.CityEn);
        dest.writeString(this.CityName);
        dest.writeString(this.CountryName);
        dest.writeString(this.TripAdvisorRating);
        dest.writeInt(this.Popularity);
        dest.writeDouble(this.StartFromPrice);
        dest.writeDouble(this.SlashRate);
        dest.writeDouble(this.ConversionRate);
        dest.writeDouble(this.StartRating);
        dest.writeDouble(this.Distance);
    }

    protected PackagesDto(Parcel in) {
        this.PackageId = in.readLong();
        this.AreaId = in.readLong();
        this.CountryId = in.readLong();
        this.StateId = in.readLong();
        this.HotelId = in.readLong();
        this.CityId = in.readLong();
        this.Title = in.readString();
        this.HotelName = in.readString();
        this.HotelInclusion = in.readString();
        this.HotelPolicy = in.readString();
        this.BuyingCurrency = in.readString();
        this.Description = in.readString();
        this.Address1 = in.readString();
        this.Address2 = in.readString();
        this.HotelExclusion = in.readString();
        this.ListingImage = in.readString();
        this.TermsAndCondition = in.readString();
        this.CityEn = in.readString();
        this.CityName = in.readString();
        this.CountryName = in.readString();
        this.TripAdvisorRating = in.readString();
        this.Popularity = in.readInt();
        this.StartFromPrice = in.readDouble();
        this.SlashRate = in.readDouble();
        this.ConversionRate = in.readDouble();
        this.StartRating = in.readDouble();
        this.Distance = in.readDouble();
    }

    public static final Creator<PackagesDto> CREATOR = new Creator<PackagesDto>() {
        @Override
        public PackagesDto createFromParcel(Parcel source) {
            return new PackagesDto(source);
        }

        @Override
        public PackagesDto[] newArray(int size) {
            return new PackagesDto[size];
        }
    };
}