package com.holidayme.data;

import java.util.ArrayList;

/**
 * Created by devrath.rathee on 08-03-2017.
 */

public class GetawayDetailBean {

    private int CountryId, Popularity;
    private String Title, HotelName, HotelInclusion, HotelPolicy, BuyingCurrency, Description, Address1, Address2, HotelExclusion,
            IsSellingFast, SeoName, ImportantNotes, ListingImage, IsExlusiveDeal, TermsAndCondition, CityEn, CityName,
            CountryName, CategoriesDetail, Categories, TripAdvisorRating;
    long PackageId,AreaId,StateId, HotelId, CityId;
    private double Lattitude, Longitude, StartFromPrice, CityLatitude, CityLongitude, Distance, SlashRate, ConversionRate;
    private boolean IsSeoVisible;
    private String[] DetailImages;
    private Integer[] Ameneties;
    private float StartRating;

    private ArrayList<SubPackageBean> SubPackagesList;

    public long getPackageId() {
        return PackageId;
    }

    public void setPackageId(int packageId) {
        PackageId = packageId;
    }

    public long getAreaId() {
        return AreaId;
    }

    public void setAreaId(int areaId) {
        AreaId = areaId;
    }

    public int getCountryId() {
        return CountryId;
    }

    public void setCountryId(int countryId) {
        CountryId = countryId;
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

    public void setStateId(int stateId) {
        StateId = stateId;
    }

    public long getHotelId() {
        return HotelId;
    }

    public void setHotelId(int hotelId) {
        HotelId = hotelId;
    }

    public long getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public float getStartRating() {
        return StartRating;
    }

    public void setStartRating(float startRating) {
        StartRating = startRating;
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

    public String getIsSellingFast() {
        return IsSellingFast;
    }

    public void setIsSellingFast(String isSellingFast) {
        IsSellingFast = isSellingFast;
    }

    public String getSeoName() {
        return SeoName;
    }

    public void setSeoName(String seoName) {
        SeoName = seoName;
    }

    public String getImportantNotes() {
        return ImportantNotes;
    }

    public void setImportantNotes(String importantNotes) {
        ImportantNotes = importantNotes;
    }

    public String getListingImage() {
        return ListingImage;
    }

    public void setListingImage(String listingImage) {
        ListingImage = listingImage;
    }

    public String getIsExlusiveDeal() {
        return IsExlusiveDeal;
    }

    public void setIsExlusiveDeal(String isExlusiveDeal) {
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

    public String getCategoriesDetail() {
        return CategoriesDetail;
    }

    public void setCategoriesDetail(String categoriesDetail) {
        CategoriesDetail = categoriesDetail;
    }

    public String getCategories() {
        return Categories;
    }

    public void setCategories(String categories) {
        Categories = categories;
    }

    public String getTripAdvisorRating() {
        return TripAdvisorRating;
    }

    public void setTripAdvisorRating(String tripAdvisorRating) {
        TripAdvisorRating = tripAdvisorRating;
    }

    public double getLattitude() {
        return Lattitude;
    }

    public void setLattitude(double lattitude) {
        Lattitude = lattitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getStartFromPrice() {
        return StartFromPrice;
    }

    public void setStartFromPrice(double startFromPrice) {
        StartFromPrice = startFromPrice;
    }

    public double getCityLatitude() {
        return CityLatitude;
    }

    public void setCityLatitude(double cityLatitude) {
        CityLatitude = cityLatitude;
    }

    public double getCityLongiTude() {
        return CityLongitude;
    }

    public void setCityLongiTude(double cityLongiTude) {
        CityLongitude = cityLongiTude;
    }

    public double getDistance() {
        return Distance;
    }

    public void setDistance(double distance) {
        Distance = distance;
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

    public boolean isSeoVisible() {
        return IsSeoVisible;
    }

    public void setSeoVisible(boolean seoVisible) {
        IsSeoVisible = seoVisible;
    }

    public String[] getDetailImages() {
        return DetailImages;
    }

    public void setDetailImages(String[] detailImages) {
        DetailImages = detailImages;
    }

    public Integer[] getAmeneties() {
        return Ameneties;
    }

    public void setAmeneties(Integer[] ameneties) {
        Ameneties = ameneties;
    }

    public ArrayList<SubPackageBean> getSubPackagesList() {
        return SubPackagesList;
    }

    public void setSubPackagesList(ArrayList<SubPackageBean> subPackagesList) {
        SubPackagesList = subPackagesList;
    }

    public static class SubPackageBean {
        int Nights, CutOffDays;
        long RoomId, Id;
        String WhatYouGet, Description, Title, UserAgent;
        double SellingPrice, MarketPrice, ConversionRate, Discount;

        public int getNights() {
            return Nights;
        }

        public void setNights(int nights) {
            Nights = nights;
        }

        public int getCutOffDays() {
            return CutOffDays;
        }

        public void setCutOffDays(int cutOffDays) {
            CutOffDays = cutOffDays;
        }

        public long getRoomId() {
            return RoomId;
        }

        public void setRoomId(int roomId) {
            RoomId = roomId;
        }

        public long getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getWhatYouGet() {
            return WhatYouGet;
        }

        public void setWhatYouGet(String whatYouGet) {
            WhatYouGet = whatYouGet;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getUserAgent() {
            return UserAgent;
        }

        public void setUserAgent(String userAgent) {
            UserAgent = userAgent;
        }

        public double getSellingPrice() {
            return SellingPrice;
        }

        public void setSellingPrice(double sellingPrice) {
            SellingPrice = sellingPrice;
        }

        public double getMarketPrice() {
            return MarketPrice;
        }

        public void setMarketPrice(double marketPrice) {
            MarketPrice = marketPrice;
        }

        public double getConversionRate() {
            return ConversionRate;
        }

        public void setConversionRate(double conversionRate) {
            ConversionRate = conversionRate;
        }

        public double getDiscount() {
            return Discount;
        }

        public void setDiscount(double discount) {
            Discount = discount;
        }
    }


}
