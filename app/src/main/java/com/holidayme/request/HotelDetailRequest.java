package com.holidayme.request;

/**
 * Created by supriya.sakore on 27-06-2016.
 */
public class HotelDetailRequest {
    private Long CityId;
    private Long HotelId;
    private String LanguageCode;

    public HotelDetailRequest(long cityId, long l, String language) {

        CityId=cityId;
        LanguageCode=language;
        HotelId=l;
    }

    public HotelDetailRequest() {


    }

    public Long getCityId() {
        return CityId;
    }

    public void setCityId(Long cityId) {
        CityId = cityId;
    }

    public Long getHotelId() {
        return HotelId;
    }

    public void setHotelId(Long hotelId) {
        HotelId = hotelId;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }
}
