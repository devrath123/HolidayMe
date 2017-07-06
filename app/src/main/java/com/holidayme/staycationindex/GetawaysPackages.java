package com.holidayme.staycationindex;

/**
 * Created by arshad.shaikh on 3/22/2017.
 */

public class GetawaysPackages {

    private long CityId,CountryId,ErrorCode,Id;
    private boolean IsError;
    private String City,Country,ThumbNailUrl,ImageUrl,Title,UserAgent,ErrorMessage,PackageCount;

    public long getCityId() {
        return CityId;
    }

    public void setCityId(long cityId) {
        CityId = cityId;
    }

    public long getCountryId() {
        return CountryId;
    }

    public void setCountryId(long countryId) {
        CountryId = countryId;
    }

    public long getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(long errorCode) {
        ErrorCode = errorCode;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public boolean isError() {
        return IsError;
    }

    public void setError(boolean error) {
        IsError = error;
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

    public String getThumbNailUrl() {
        return ThumbNailUrl;
    }

    public void setThumbNailUrl(String thumbNailUrl) {
        ThumbNailUrl = thumbNailUrl;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getTitle() {
        return Title;
    }

    public String getPackageCount() {
        return PackageCount;
    }

    public void setPackageCount(String packageCount) {
        PackageCount = packageCount;
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

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }
}
