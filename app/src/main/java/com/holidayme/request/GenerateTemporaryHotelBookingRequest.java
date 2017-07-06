package com.holidayme.request;

import com.holidayme.data.OccupancyDto;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 06-07-2016.
 */

public class GenerateTemporaryHotelBookingRequest {
    private Long HotelId;
    private String RoomRateCode;
    private boolean IsCodApplicable;
    private String CodCityName;
    private Long CityId;
    private boolean IsPayAtHotel;
    private String CheckInDate;
    private String CheckOutDate;
    private String LanguageCode;
    private String CurrencyCode;
    private ArrayList<OccupancyDto> Occupancy;

    public Long getHotelId() {
        return HotelId;
    }

    public void setHotelId(Long hotelId) {
        HotelId = hotelId;
    }

    public String getRoomRateCode() {
        return RoomRateCode;
    }

    public void setRoomRateCode(String roomRateCode) {
        RoomRateCode = roomRateCode;
    }

    public boolean isCodApplicable() {
        return IsCodApplicable;
    }

    public void setCodApplicable(boolean codApplicable) {
        IsCodApplicable = codApplicable;
    }

    public String getCodCityName() {
        return CodCityName;
    }

    public void setCodCityName(String codCityName) {
        CodCityName = codCityName;
    }

    public Long getCityId() {
        return CityId;
    }

    public void setCityId(Long cityId) {
        CityId = cityId;
    }

    public boolean isPayAtHotel() {
        return IsPayAtHotel;
    }

    public void setPayAtHotel(boolean payAtHotel) {
        IsPayAtHotel = payAtHotel;
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

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public ArrayList<OccupancyDto> getOccupancy() {
        return Occupancy;
    }

    public void setOccupancy(ArrayList<OccupancyDto> occupancy) {
        Occupancy = occupancy;
    }
}
