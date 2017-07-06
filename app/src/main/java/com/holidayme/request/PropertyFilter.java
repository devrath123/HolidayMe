package com.holidayme.request;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by santosh.patar on 29-07-2015.
 */
public class PropertyFilter implements Parcelable {









    private String PropertyTitle;
    private boolean IsPayAtHotel;
    private double PricePerNightMin;
    private double PricePerNightMax;
    private ArrayList<Long> CategoryIds;
    private ArrayList<Double> StarRatings;
    private ArrayList<Long> AmenityIds;
    private ArrayList<Long> ChainId;
    private ArrayList<Long> AreaIds;
    // private int CurrentFilterApplied; //Property Filter Type

    private String CurrentFilterApplied; //Property Filter Type

    private int SortByColumn;
    private int OrderByTypes;
    private long PropertyId;
    private ArrayList<Double> TripAdvisorRatings;
    private ArrayList<Double> Location;



    private boolean WifiAvailable;


    public String getPropertyTitle() {
        return PropertyTitle;
    }

    public void setPropertyTitle(String propertyTitle) {
        PropertyTitle = propertyTitle;
    }

    public boolean isPayAtHotel() {
        return IsPayAtHotel;
    }

    public void setIsPayAtHotel(boolean isPayAtHotel) {
        IsPayAtHotel = isPayAtHotel;
    }

    public double getPricePerNightMin() {
        return PricePerNightMin;
    }

    public void setPricePerNightMin(double pricePerNightMin) {
        PricePerNightMin = pricePerNightMin;
    }

    public double getPricePerNightMax() {
        return PricePerNightMax;
    }

    public void setPricePerNightMax(double pricePerNightMax) {
        PricePerNightMax = pricePerNightMax;
    }

    public ArrayList<Long> getCategoryIds() {
        return CategoryIds;
    }

    public void setCategoryIds(ArrayList<Long> categoryIds) {
        CategoryIds = categoryIds;
    }

    public ArrayList<Long> getAmenityIds() {
        return AmenityIds;
    }

    public void setAmenityIds(ArrayList<Long> amenityIds) {
        AmenityIds = amenityIds;
    }

    public ArrayList<Double> getStarRatings() {
        return StarRatings;
    }

    public void setStarRatings(ArrayList<Double> starRatings) {
        StarRatings = starRatings;
    }

    public ArrayList<Long> getChainId() {
        return ChainId;
    }

    public void setChainId(ArrayList<Long> chainId) {
        ChainId = chainId;
    }

    public ArrayList<Long> getAreaIds() {
        return AreaIds;
    }

    public void setAreaIds(ArrayList<Long> areaIds) {
        AreaIds = areaIds;
    }



   /* public int getCurrentFilterApplied() {
        return CurrentFilterApplied;
    }

    public void setCurrentFilterApplied(int currentFilterApplied) {
        CurrentFilterApplied = currentFilterApplied;
    }*/

    public String getCurrentFilterApplied() {
        return CurrentFilterApplied;
    }

    public void setCurrentFilterApplied(String currentFilterApplied) {
        CurrentFilterApplied = currentFilterApplied;
    }

    public int getSortByColumn() {
        return SortByColumn;
    }

    public void setSortByColumn(int sortByColumn) {
        SortByColumn = sortByColumn;
    }

    public int getOrderByTypes() {
        return OrderByTypes;
    }

    public void setOrderByTypes(int orderByTypes) {
        OrderByTypes = orderByTypes;
    }

    public ArrayList<Double> getTripAdvisorRatings() {
        return TripAdvisorRatings;
    }

    public void setTripAdvisorRatings(ArrayList<Double> tripAdvisorRatings) {
        TripAdvisorRatings = tripAdvisorRatings;
    }

    public long getPropertyId() {
        return PropertyId;
    }

    public void setPropertyId(long propertyId) {
        PropertyId = propertyId;
    }

    public boolean isWifiAvailable() {
        return WifiAvailable;
    }

    public void setWifiAvailable(boolean wifiAvailable) {
        WifiAvailable = wifiAvailable;
    }

    public ArrayList<Double> getLocation() {
        return Location;
    }

    public void setLocation(ArrayList<Double> location) {
        Location = location;
    }

    public PropertyFilter() {
    }

    protected PropertyFilter(Parcel in) {
        PropertyTitle = in.readString();
        IsPayAtHotel = in.readByte() != 0x00;
        PricePerNightMin = in.readDouble();
        PricePerNightMax = in.readDouble();
        if (in.readByte() == 0x01) {
            CategoryIds = new ArrayList<Long>();
            in.readList(CategoryIds, Long.class.getClassLoader());
        } else {
            CategoryIds = null;
        }
        if (in.readByte() == 0x01) {
            StarRatings = new ArrayList<Double>();
            in.readList(StarRatings, Double.class.getClassLoader());
        } else {
            StarRatings = null;
        }
        if (in.readByte() == 0x01) {
            AmenityIds = new ArrayList<Long>();
            in.readList(AmenityIds, Long.class.getClassLoader());
        } else {
            AmenityIds = null;
        }
        if (in.readByte() == 0x01) {
            ChainId = new ArrayList<Long>();
            in.readList(ChainId, Long.class.getClassLoader());
        } else {
            ChainId = null;
        }
        if (in.readByte() == 0x01) {
            AreaIds = new ArrayList<Long>();
            in.readList(AreaIds, Long.class.getClassLoader());
        } else {
            AreaIds = null;
        }
        CurrentFilterApplied = in.readString();
        SortByColumn = in.readInt();
        OrderByTypes = in.readInt();
        PropertyId = in.readLong();
        if (in.readByte() == 0x01) {
            TripAdvisorRatings = new ArrayList<Double>();
            in.readList(TripAdvisorRatings, Double.class.getClassLoader());
        } else {
            TripAdvisorRatings = null;
        }
        if (in.readByte() == 0x01) {
            Location = new ArrayList<Double>();
            in.readList(Location, Double.class.getClassLoader());
        } else {
            Location = null;
        }
        WifiAvailable = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PropertyTitle);
        dest.writeByte((byte) (IsPayAtHotel ? 0x01 : 0x00));
        dest.writeDouble(PricePerNightMin);
        dest.writeDouble(PricePerNightMax);
        if (CategoryIds == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(CategoryIds);
        }
        if (StarRatings == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(StarRatings);
        }
        if (AmenityIds == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(AmenityIds);
        }
        if (ChainId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ChainId);
        }
        if (AreaIds == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(AreaIds);
        }
        dest.writeString(CurrentFilterApplied);
        dest.writeInt(SortByColumn);
        dest.writeInt(OrderByTypes);
        dest.writeLong(PropertyId);
        if (TripAdvisorRatings == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(TripAdvisorRatings);
        }
        if (Location == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Location);
        }
        dest.writeByte((byte) (WifiAvailable ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PropertyFilter> CREATOR = new Parcelable.Creator<PropertyFilter>() {
        @Override
        public PropertyFilter createFromParcel(Parcel in) {
            return new PropertyFilter(in);
        }

        @Override
        public PropertyFilter[] newArray(int size) {
            return new PropertyFilter[size];
        }
    };
}