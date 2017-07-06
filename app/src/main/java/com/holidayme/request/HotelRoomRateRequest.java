package com.holidayme.request;

import com.holidayme.data.OccupancyDto;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 08-06-2016.
 */
public class HotelRoomRateRequest {





    private String RoomCode;
    private Long CityId;
    private Long HotelId;
    private String CheckInDate;
    private String CheckOutDate;
    
    private ArrayList<OccupancyDto> Occupancy;
    private String TransactionId;
    private String LanguageCode;
    private String CurrencyCode;

    public String getRoomCode() {return RoomCode;}

    public void setRoomCode(String roomCode) {RoomCode = roomCode;}

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

    public ArrayList<OccupancyDto> getOccupancy() {
        return Occupancy;
    }

    public void setOccupancy(ArrayList<OccupancyDto> occupancy) {
        Occupancy = occupancy;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
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
}
