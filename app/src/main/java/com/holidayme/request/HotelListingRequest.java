package com.holidayme.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.holidayme.data.FiltersRequestDto;
import com.holidayme.data.OccupancyDto;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 09-06-2016.
 */
public class HotelListingRequest implements Parcelable {
    private long CityId;
    private long HotelId;
    private String CheckInDate;
    private String CheckOutDate;
    private int PageNumber;
    private int PageSize;
    private int SortBy;
    private String SortParameter;
    private FiltersRequestDto Filters;
    private ArrayList<OccupancyDto> Occupancy;
    private String LanguageCode;
    private String CurrencyCode;
    private  int SortByArea;

    public int getSortByArea() {
        return SortByArea;
    }

    public void setSortByArea(int sortByArea) {
        SortByArea = sortByArea;
    }

    private HotelListingRequest() {

    }

    private volatile static HotelListingRequest instance;


    public static synchronized HotelListingRequest getHotelListRequest() {
        if (instance == null) {
            synchronized (ListRequestBase.class) {
                if (instance == null) {
                    instance = new HotelListingRequest();
                }
            }
        }

        return instance;
    }

    public static void setInstance(HotelListingRequest instance) {
        HotelListingRequest.instance = instance;
    }

    public long getCityId() {
        return CityId;
    }

    public void setCityId(long cityId) {
        CityId = cityId;
    }

    public long getHotelId() {
        return HotelId;
    }

    public void setHotelId(long hotelId) {
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

    public int getPageNumber() {
        return PageNumber;
    }

    public void setPageNumber(int pageNumber) {
        PageNumber = pageNumber;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public int getSortBy() {
        return SortBy;
    }

    public void setSortBy(int sortBy) {
        SortBy = sortBy;
    }

    public String getSortParameter() {
        return SortParameter;
    }

    public void setSortParameter(String sortParameter) {
        SortParameter = sortParameter;
    }

    public FiltersRequestDto getFilters() {
        return Filters;
    }

    public void setFilters(FiltersRequestDto filters) {
        Filters = filters;
    }

    public ArrayList<OccupancyDto> getOccupancy() {
        return Occupancy;
    }

    public void setOccupancy(ArrayList<OccupancyDto> occupancy) {
        Occupancy = occupancy;
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

    protected HotelListingRequest(Parcel in) {

        SortByArea=in.readInt();
        CityId = in.readLong();
        HotelId = in.readLong();
        CheckInDate = in.readString();
        CheckOutDate = in.readString();
        PageNumber = in.readInt();
        PageSize = in.readInt();
        SortBy = in.readInt();
        SortParameter = in.readString();
        Filters = (FiltersRequestDto) in.readValue(FiltersRequestDto.class.getClassLoader());
        if (in.readByte() == 0x01) {
            Occupancy = new ArrayList<OccupancyDto>();
            in.readList(Occupancy, OccupancyDto.class.getClassLoader());
        } else {
            Occupancy = null;
        }
        LanguageCode = in.readString();
        CurrencyCode = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(SortByArea);
        dest.writeLong(CityId);
        dest.writeLong(HotelId);
        dest.writeString(CheckInDate);
        dest.writeString(CheckOutDate);
        dest.writeInt(PageNumber);
        dest.writeInt(PageSize);
        dest.writeInt(SortBy);
        dest.writeString(SortParameter);
        dest.writeValue(Filters);
        if (Occupancy == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Occupancy);
        }
        dest.writeString(LanguageCode);
        dest.writeString(CurrencyCode);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HotelListingRequest> CREATOR = new Parcelable.Creator<HotelListingRequest>() {
        @Override
        public HotelListingRequest createFromParcel(Parcel in) {
            return new HotelListingRequest(in);
        }

        @Override
        public HotelListingRequest[] newArray(int size) {
            return new HotelListingRequest[size];
        }
    };
}