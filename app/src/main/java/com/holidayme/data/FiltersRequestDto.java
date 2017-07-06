package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 09-06-2016.
 */
public class FiltersRequestDto implements Parcelable {
    private ArrayList<Double> StarRatings,TripAdvisorRatings;
    private ArrayList<Long> CategoryIds;
    private ArrayList<Long> AmenityIds;
    private ArrayList<Long> ChainIds;
    private ArrayList<Long> AreaIds;
    private boolean IsWifi;
    private boolean IsPayAtHotel;
    private String HotelName;
    private Long HotelId;
    private double MinPrice;
    private double MaxPrice;

    public ArrayList<Double> getStarRatings() {
        return StarRatings;
    }

    public void setStarRatings(ArrayList<Double> starRatings) {
        StarRatings = starRatings;
    }

    public ArrayList<Long> getCategoryIds() {
        return CategoryIds;
    }

    public void setCategoryIds(ArrayList<Long> categoryIds) {
        CategoryIds = categoryIds;
    }

    public ArrayList<Double> getTripAdvisorRatings() {
        return TripAdvisorRatings;
    }

    public void setTripAdvisorRatings(ArrayList<Double> tripAdvisorRatings) {
        TripAdvisorRatings = tripAdvisorRatings;
    }

    public ArrayList<Long> getAmenityIds() {
        return AmenityIds;
    }

    public void setAmenityIds(ArrayList<Long> amenityIds) {
        AmenityIds = amenityIds;
    }

    public ArrayList<Long> getChainIds() {
        return ChainIds;
    }

    public void setChainIds(ArrayList<Long> chainIds) {
        ChainIds = chainIds;
    }

    public ArrayList<Long> getAreaIds() {
        return AreaIds;
    }

    public void setAreaIds(ArrayList<Long> areaIds) {
        AreaIds = areaIds;
    }

    public boolean isPayAtHotel() {
        return IsPayAtHotel;
    }

    public void setIsPayAtHotel(boolean isPayAtHotel) {
        IsPayAtHotel = isPayAtHotel;
    }

    public boolean isWifi() {
        return IsWifi;
    }

    public void setIsWifi(boolean isWifi) {
        IsWifi = isWifi;
    }

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String hotelName) {
        HotelName = hotelName;
    }

    public Long getHotelId() {
        return HotelId;
    }

    public void setHotelId(Long hotelId) {
        HotelId = hotelId;
    }

    public double getMinPrice() {
        return MinPrice;
    }

    public void setMinPrice(double minPrice) {
        MinPrice = minPrice;
    }

    public double getMaxPrice() {
        return MaxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        MaxPrice = maxPrice;
    }

    public FiltersRequestDto() {

    }

    protected FiltersRequestDto(Parcel in) {
        if (in.readByte() == 0x01) {
            StarRatings = new ArrayList<Double>();
            in.readList(StarRatings, Double.class.getClassLoader());
        } else {
            StarRatings = null;
        }
        if (in.readByte() == 0x01) {
            TripAdvisorRatings = new ArrayList<Double>();
            in.readList(TripAdvisorRatings, Double.class.getClassLoader());
        } else {
            TripAdvisorRatings = null;
        }
        if (in.readByte() == 0x01) {
            CategoryIds = new ArrayList<Long>();
            in.readList(CategoryIds, Long.class.getClassLoader());
        } else {
            CategoryIds = null;
        }
        if (in.readByte() == 0x01) {
            AmenityIds = new ArrayList<Long>();
            in.readList(AmenityIds, Long.class.getClassLoader());
        } else {
            AmenityIds = null;
        }
        if (in.readByte() == 0x01) {
            ChainIds = new ArrayList<Long>();
            in.readList(ChainIds, Long.class.getClassLoader());
        } else {
            ChainIds = null;
        }
        if (in.readByte() == 0x01) {
            AreaIds = new ArrayList<Long>();
            in.readList(AreaIds, Long.class.getClassLoader());
        } else {
            AreaIds = null;
        }
        IsWifi = in.readByte() != 0x00;
        IsPayAtHotel = in.readByte() != 0x00;
        HotelName = in.readString();
        HotelId = in.readByte() == 0x00 ? null : in.readLong();
        MinPrice = in.readDouble();
        MaxPrice = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (StarRatings == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(StarRatings);
        }
        if (TripAdvisorRatings == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(TripAdvisorRatings);
        }
        if (CategoryIds == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(CategoryIds);
        }
        if (AmenityIds == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(AmenityIds);
        }
        if (ChainIds == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ChainIds);
        }
        if (AreaIds == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(AreaIds);
        }
        dest.writeByte((byte) (IsWifi ? 0x01 : 0x00));
        dest.writeByte((byte) (IsPayAtHotel ? 0x01 : 0x00));
        dest.writeString(HotelName);
        if (HotelId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(HotelId);
        }
        dest.writeDouble(MinPrice);
        dest.writeDouble(MaxPrice);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FiltersRequestDto> CREATOR = new Parcelable.Creator<FiltersRequestDto>() {
        @Override
        public FiltersRequestDto createFromParcel(Parcel in) {
            return new FiltersRequestDto(in);
        }

        @Override
        public FiltersRequestDto[] newArray(int size) {
            return new FiltersRequestDto[size];
        }
    };
}