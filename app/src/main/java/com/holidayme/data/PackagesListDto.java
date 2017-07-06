package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 09-03-2017.
 */

public class PackagesListDto implements Parcelable {

    private String City,Country,type;
    private Double Distance;
    private ArrayList<PackagesDto>Packages;
    private boolean isCity,IsExlusiveDeal,IsSellingFast;

    private long PackageId,AreaId,CountryId,StateId,HotelId,CityId;
    private String Title,HotelName,HotelInclusion,HotelPolicy,BuyingCurrency,Description,Address1,Address2,HotelExclusion,ListingImage,
            TermsAndCondition,CityEn,CityName,CountryName,TripAdvisorRating;
    private int Popularity,listSize;
    private double StartFromPrice,SlashRate,ConversionRate,StartRating,DistancePackage;

    public PackagesListDto(Long packageId,Long hotelId,Long cityId,String title,String image,String city,String country,boolean IsExlusiveDeal,boolean IsSellingFast,double SlashRate,double StartFromPrice,double DistancePackage,String BuyingCurrency,String type) {
        this.PackageId=packageId;
        this.CityId=cityId;
        this.Title=title;
        this.ListingImage=image;
        this.type=type;
        this.CityName=city;
        this.CountryName=country;
        this.IsExlusiveDeal=IsExlusiveDeal;
        this.IsSellingFast=IsSellingFast;
        this.SlashRate=SlashRate;
        this.StartFromPrice=StartFromPrice;
        this.DistancePackage=DistancePackage;
        this.BuyingCurrency=BuyingCurrency;
        this.HotelId=hotelId;
    }

    public PackagesListDto(String city, String country, double distance,String type,int size) {
        this.City=city;
        this.Country=country;
        this.Distance=distance;
        this.type=type;
        this.listSize=size;
    }

    public long getPackageId() {
        return PackageId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPackageId(long packageId) {
        PackageId = packageId;

    }

    public long getAreaId() {
        return AreaId;
    }

    public void setAreaId(long areaId) {
        AreaId = areaId;
    }

    public long getCountryId() {
        return CountryId;
    }

    public void setCountryId(long countryId) {
        CountryId = countryId;
    }

    public long getStateId() {
        return StateId;
    }

    public void setStateId(long stateId) {
        StateId = stateId;
    }

    public long getHotelId() {
        return HotelId;
    }

    public void setHotelId(long hotelId) {
        HotelId = hotelId;
    }

    public long getCityId() {
        return CityId;
    }

    public void setCityId(long cityId) {
        CityId = cityId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String hotelName) {
        HotelName = hotelName;
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

    public String getBuyingCurrency() {
        return BuyingCurrency;
    }

    public void setBuyingCurrency(String buyingCurrency) {
        BuyingCurrency = buyingCurrency;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    public String getHotelExclusion() {
        return HotelExclusion;
    }

    public void setHotelExclusion(String hotelExclusion) {
        HotelExclusion = hotelExclusion;
    }

    public boolean getIsSellingFast() {
        return IsSellingFast;
    }

    public void setIsSellingFast(boolean isSellingFast) {
        IsSellingFast = isSellingFast;
    }

    public String getListingImage() {
        return ListingImage;
    }

    public void setListingImage(String listingImage) {
        ListingImage = listingImage;
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

    public int getPopularity() {
        return Popularity;
    }

    public void setPopularity(int popularity) {
        Popularity = popularity;
    }

    public double getStartFromPrice() {
        return StartFromPrice;
    }

    public void setStartFromPrice(double startFromPrice) {
        StartFromPrice = startFromPrice;
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

    public double getStartRating() {
        return StartRating;
    }

    public void setStartRating(double startRating) {
        StartRating = startRating;
    }

    public double getDistancePackage() {
        return DistancePackage;
    }

    public void setDistancePackage(double distancePackage) {
        DistancePackage = distancePackage;
    }

    public static Creator<PackagesListDto> getCREATOR() {
        return CREATOR;
    }



    public boolean isCity() {
        return isCity;
    }

    public void setCity(boolean city) {
        isCity = city;
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

    public boolean isExlusiveDeal() {
        return IsExlusiveDeal;
    }

    public void setExlusiveDeal(boolean exlusiveDeal) {
        IsExlusiveDeal = exlusiveDeal;
    }

    public Double getDistance() {
        return Distance;
    }

    public void setDistance(double distance) {
        Distance = distance;
    }

    public boolean isSellingFast() {
        return IsSellingFast;
    }

    public void setSellingFast(boolean sellingFast) {
        IsSellingFast = sellingFast;
    }

    public ArrayList<PackagesDto> getPackages() {
        return Packages;
    }

    public void setPackages(ArrayList<PackagesDto> packages) {
        Packages = packages;
    }

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }

    public PackagesListDto() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.City);
        dest.writeString(this.Country);
        dest.writeString(this.type);
        dest.writeDouble(this.Distance);
        dest.writeTypedList(this.Packages);
        dest.writeByte(this.isCity ? (byte) 1 : (byte) 0);
        dest.writeByte(this.IsExlusiveDeal ? (byte) 1 : (byte) 0);
        dest.writeByte(this.IsSellingFast ? (byte) 1 : (byte) 0);
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
        dest.writeInt(this.listSize);
        dest.writeDouble(this.StartFromPrice);
        dest.writeDouble(this.SlashRate);
        dest.writeDouble(this.ConversionRate);
        dest.writeDouble(this.StartRating);
        dest.writeDouble(this.DistancePackage);
    }

    protected PackagesListDto(Parcel in) {
        this.City = in.readString();
        this.Country = in.readString();
        this.type = in.readString();
        this.Distance = in.readDouble();
        this.Packages = in.createTypedArrayList(PackagesDto.CREATOR);
        this.isCity = in.readByte() != 0;
        this.IsExlusiveDeal = in.readByte() != 0;
        this.IsSellingFast = in.readByte() != 0;
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
        this.listSize = in.readInt();
        this.StartFromPrice = in.readDouble();
        this.SlashRate = in.readDouble();
        this.ConversionRate = in.readDouble();
        this.StartRating = in.readDouble();
        this.DistancePackage = in.readDouble();
    }

    public static final Creator<PackagesListDto> CREATOR = new Creator<PackagesListDto>() {
        @Override
        public PackagesListDto createFromParcel(Parcel source) {
            return new PackagesListDto(source);
        }

        @Override
        public PackagesListDto[] newArray(int size) {
            return new PackagesListDto[size];
        }
    };
}
