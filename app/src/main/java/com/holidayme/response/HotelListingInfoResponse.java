package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.holidayme.data.FiltersDto;
import com.holidayme.data.HotelsDto;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 09-06-2016.
 */
public class HotelListingInfoResponse implements Parcelable, Cloneable {
    private ArrayList<HotelsDto> Hotels;
    private int TtlCnt;
    private boolean LoadMore;
    private double MinPrc;
    private double MaxPrc;
    private boolean IsPAH;
    private boolean IsWifi;
    private boolean IsDeals;
    private double Lat;
    private double Long;
    private FiltersDto Filters;
    private String AppToken;
    private String TransactionId;
    private int AffiliateId;
    private String LanguageCode;
    private String CurrencyCode;
    private String Error;
    private String UserTrackingId;
    private String SessionId;
    private Long TotalExecutionTime;
    private int selectedMinimumRate = 0;
    private int selectedMaximumRate = 0;
    boolean isPriceFilterApply = false;
    private String TaxLabel;
    private String PriceLabel;


    public ArrayList<HotelsDto> getHotels() {
        return Hotels;
    }

    public void setHotels(ArrayList<HotelsDto> hotels) {
        Hotels = hotels;
    }

    public int getTtlCnt() {
        return TtlCnt;
    }

    public void setTtlCnt(int ttlCnt) {
        TtlCnt = ttlCnt;
    }

    public boolean isLoadMore() {
        return LoadMore;
    }

    public void setLoadMore(boolean loadMore) {
        LoadMore = loadMore;
    }

    public double getMinPrc() {
        return MinPrc;
    }

    public void setMinPrc(double minPrc) {
        MinPrc = minPrc;
    }

    public double getMaxPrc() {
        return MaxPrc;
    }

    public void setMaxPrc(double maxPrc) {
        MaxPrc = maxPrc;
    }

    public boolean isPAH() {
        return IsPAH;
    }

    public void setIsPAH(boolean isPAH) {
        IsPAH = isPAH;
    }

    public boolean isWifi() {
        return IsWifi;
    }

    public void setIsWifi(boolean isWifi) {
        IsWifi = isWifi;
    }

    public boolean isDeals() {
        return IsDeals;
    }

    public void setIsDeals(boolean isDeals) {
        IsDeals = isDeals;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLong() {
        return Long;
    }

    public void setLong(double aLong) {
        Long = aLong;
    }

    public FiltersDto getFilters() {
        return Filters;
    }

    public void setFilters(FiltersDto filters) {
        Filters = filters;
    }

    public String getAppToken() {
        return AppToken;
    }

    public void setAppToken(String appToken) {
        AppToken = appToken;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public int getAffiliateId() {
        return AffiliateId;
    }

    public void setAffiliateId(int affiliateId) {
        AffiliateId = affiliateId;
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

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    public String getUserTrackingId() {
        return UserTrackingId;
    }

    public void setUserTrackingId(String userTrackingId) {
        UserTrackingId = userTrackingId;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public java.lang.Long getTotalExecutionTime() {
        return TotalExecutionTime;
    }

    public void setTotalExecutionTime(java.lang.Long totalExecutionTime) {
        TotalExecutionTime = totalExecutionTime;
    }

    public int getSelectedMinimumRate() {
        return selectedMinimumRate;
    }

    public void setSelectedMinimumRate(int selectedMinimumRate) {
        this.selectedMinimumRate = selectedMinimumRate;
    }

    public int getSelectedMaximumRate() {
        return selectedMaximumRate;
    }

    public void setSelectedMaximumRate(int selectedMaximumRate) {
        this.selectedMaximumRate = selectedMaximumRate;
    }

    public boolean isPriceFilterApply() {
        return isPriceFilterApply;
    }

    public void setPriceFilterApply(boolean priceFilterApply) {
        isPriceFilterApply = priceFilterApply;
    }

    public void setIsPriceFilterApply(boolean isPriceFilterApply) {
        this.isPriceFilterApply = isPriceFilterApply;
    }

    public String getTaxLabel() {
        return TaxLabel;
    }

    public void setTaxLabel(String taxLabel) {
        TaxLabel = taxLabel;
    }

    public String getPriceLabel() {
        return PriceLabel;
    }

    public void setPriceLabel(String priceLabel) {
        PriceLabel = priceLabel;
    }

    public HotelListingInfoResponse() {

    }

    protected HotelListingInfoResponse(Parcel in) {
        if (in.readByte() == 0x01) {
            Hotels = new ArrayList<HotelsDto>();
            in.readList(Hotels, HotelsDto.class.getClassLoader());
        } else {
            Hotels = null;
        }
        TtlCnt = in.readInt();
        LoadMore = in.readByte() != 0x00;
        MinPrc = in.readDouble();
        MaxPrc = in.readDouble();
        IsPAH = in.readByte() != 0x00;
        IsWifi = in.readByte() != 0x00;
        IsDeals = in.readByte() != 0x00;
        Lat = in.readDouble();
        Long = in.readDouble();
        Filters = (FiltersDto) in.readValue(FiltersDto.class.getClassLoader());
        AppToken = in.readString();
        TransactionId = in.readString();
        AffiliateId = in.readInt();
        LanguageCode = in.readString();
        CurrencyCode = in.readString();
        Error = in.readString();
        UserTrackingId = in.readString();
        SessionId = in.readString();
        TotalExecutionTime = in.readByte() == 0x00 ? null : in.readLong();
        selectedMinimumRate = in.readInt();
        selectedMaximumRate = in.readInt();
        isPriceFilterApply = in.readByte() != 0x00;
        TaxLabel = in.readString();
        PriceLabel = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (Hotels == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Hotels);
        }
        dest.writeInt(TtlCnt);
        dest.writeByte((byte) (LoadMore ? 0x01 : 0x00));
        dest.writeDouble(MinPrc);
        dest.writeDouble(MaxPrc);
        dest.writeByte((byte) (IsPAH ? 0x01 : 0x00));
        dest.writeByte((byte) (IsWifi ? 0x01 : 0x00));
        dest.writeByte((byte) (IsDeals ? 0x01 : 0x00));
        dest.writeDouble(Lat);
        dest.writeDouble(Long);
        dest.writeValue(Filters);
        dest.writeString(AppToken);
        dest.writeString(TransactionId);
        dest.writeInt(AffiliateId);
        dest.writeString(LanguageCode);
        dest.writeString(CurrencyCode);
        dest.writeString(Error);
        dest.writeString(UserTrackingId);
        dest.writeString(SessionId);
        if (TotalExecutionTime == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(TotalExecutionTime);
        }
        dest.writeInt(selectedMinimumRate);
        dest.writeInt(selectedMaximumRate);
        dest.writeByte((byte) (isPriceFilterApply ? 0x01 : 0x00));
        dest.writeString(TaxLabel);
        dest.writeString(PriceLabel);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HotelListingInfoResponse> CREATOR = new Parcelable.Creator<HotelListingInfoResponse>() {
        @Override
        public HotelListingInfoResponse createFromParcel(Parcel in) {
            return new HotelListingInfoResponse(in);
        }

        @Override
        public HotelListingInfoResponse[] newArray(int size) {
            return new HotelListingInfoResponse[size];
        }
    };

    @Override
    public Object clone() throws CloneNotSupportedException {
        HotelListingInfoResponse cloned = (HotelListingInfoResponse) super.clone();
        ArrayList<HotelsDto> hotelsDtos = new ArrayList<HotelsDto>();
        for (int i = 0; i < cloned.getHotels().size(); i++) {
            HotelsDto hotelsDto = (HotelsDto) cloned.getHotels().get(i).clone();
            hotelsDtos.add(hotelsDto);
        }
        cloned.setHotels(hotelsDtos);
        cloned.setFilters((FiltersDto) cloned.getFilters().clone());
        return cloned;
    }
}