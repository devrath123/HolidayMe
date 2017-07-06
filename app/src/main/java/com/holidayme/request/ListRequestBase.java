package com.holidayme.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.holidayme.common.NetworkUtilities;

/**
 * Created by santosh.patar on 29-07-2015.
 */
public class ListRequestBase implements Parcelable {


    private ListRequestBase(){

    }

    private volatile  static ListRequestBase instance;


    public static synchronized ListRequestBase getListRequestBase(){
        if(instance ==  null){
            synchronized (ListRequestBase.class){
                if(instance == null){
                    instance = new ListRequestBase();
                }
            }
        }

        return instance;
    }



    private int PageSize;
    private int PageNumber;

    private boolean IsFilterCountNeeded;

    String TopAreaCount;
    String TopAmenitiesCount;


    private boolean ShowList;

    private String Ip= NetworkUtilities.getIp();
    private String Nationality= null;
    private boolean IsTopHotels = false;
    //private String RequestId="1d9ba2f5-510c-4f6f-af09-a187f37d6a3a";
    private String RequestId;
    private boolean IsSubscribUser=false;
    private String LanguageCode;
    private String TrackingCookie= null;
    private String Cookie = "";

    private long PropertyCode;

    private String CountryCode = null;
    private String UserIpAddress=NetworkUtilities.getIp();

    private String  UserAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.125 Safari/537.36";

    private boolean IsMobile = true;

    private PropertySearchRequestDto PropertySearchRequestDto = null;
    private PropertyFilter PropertyFilter = null;

    public com.holidayme.request.PropertyFilter getPropertyFilter() {
        return PropertyFilter;
    }

    public void setPropertyFilter(com.holidayme.request.PropertyFilter propertyFilter) {
        PropertyFilter = propertyFilter;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public int getPageNumber() {
        return PageNumber;
    }

    public void setPageNumber(int pageNumber) {
        PageNumber = pageNumber;
    }

    public boolean isFilterCountNeeded() {
        return IsFilterCountNeeded;
    }

    public void setIsFilterCountNeeded(boolean isFilterCountNeeded) {
        IsFilterCountNeeded = isFilterCountNeeded;
    }

    public String getTopAreaCount() {
        return TopAreaCount;
    }

    public void setTopAreaCount(String topAreaCount) {
        TopAreaCount = topAreaCount;
    }

    public String getTopAmenitiesCount() {
        return TopAmenitiesCount;
    }

    public void setTopAmenitiesCount(String topAmenitiesCount) {
        TopAmenitiesCount = topAmenitiesCount;
    }

    public boolean isShowList() {
        return ShowList;
    }

    public void setShowList(boolean showList) {
        ShowList = showList;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public boolean isTopHotels() {
        return IsTopHotels;
    }

    public void setIsTopHotels(boolean isTopHotels) {
        IsTopHotels = isTopHotels;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public boolean isSubscribUser() {
        return IsSubscribUser;
    }

    public void setIsSubscribUser(boolean isSubscribUser) {
        IsSubscribUser = isSubscribUser;
    }

    public String getCookie() {
        return Cookie;
    }

    public void setCookie(String cookie) {
        Cookie = cookie;
    }

    public String getUserIpAddress() {
        return UserIpAddress;
    }

    public void setUserIpAddress(String userIpAddress) {
        UserIpAddress = userIpAddress;
    }

    public String getUserAgent() {
        return UserAgent;
    }

    public void setUserAgent(String userAgent) {
        UserAgent = userAgent;
    }

    public boolean isMobile() {
        return IsMobile;
    }

    public void setIsMobile(boolean isMobile) {
        IsMobile = isMobile;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public String getTrackingCookie() {
        return TrackingCookie;
    }

    public void setTrackingCookie(String trackingCookie) {
        TrackingCookie = trackingCookie;
    }

    public com.holidayme.request.PropertySearchRequestDto getPropertySearchRequestDto() {
        return PropertySearchRequestDto;
    }

    public void setPropertySearchRequestDto(com.holidayme.request.PropertySearchRequestDto propertySearchRequestDto) {
        PropertySearchRequestDto = propertySearchRequestDto;
    }

    public long getPropertyCode() {
        return PropertyCode;
    }

    public void setPropertyCode(long propertyCode) {
        PropertyCode = propertyCode;
    }

    protected ListRequestBase(Parcel in) {
        PageSize = in.readInt();
        PageNumber = in.readInt();
        IsFilterCountNeeded = in.readByte() != 0x00;
        TopAreaCount = in.readString();
        TopAmenitiesCount = in.readString();
        ShowList = in.readByte() != 0x00;
        Ip = in.readString();
        Nationality = in.readString();
        IsTopHotels = in.readByte() != 0x00;
        RequestId = in.readString();
        IsSubscribUser = in.readByte() != 0x00;
        LanguageCode = in.readString();
        TrackingCookie = in.readString();
        Cookie = in.readString();
        PropertyCode = in.readLong();
        CountryCode = in.readString();
        UserIpAddress = in.readString();
        UserAgent = in.readString();
        IsMobile = in.readByte() != 0x00;
        PropertySearchRequestDto = (PropertySearchRequestDto) in.readValue(PropertySearchRequestDto.class.getClassLoader());
        PropertyFilter = (PropertyFilter) in.readValue(PropertyFilter.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(PageSize);
        dest.writeInt(PageNumber);
        dest.writeByte((byte) (IsFilterCountNeeded ? 0x01 : 0x00));
        dest.writeString(TopAreaCount);
        dest.writeString(TopAmenitiesCount);
        dest.writeByte((byte) (ShowList ? 0x01 : 0x00));
        dest.writeString(Ip);
        dest.writeString(Nationality);
        dest.writeByte((byte) (IsTopHotels ? 0x01 : 0x00));
        dest.writeString(RequestId);
        dest.writeByte((byte) (IsSubscribUser ? 0x01 : 0x00));
        dest.writeString(LanguageCode);
        dest.writeString(TrackingCookie);
        dest.writeString(Cookie);
        dest.writeLong(PropertyCode);
        dest.writeString(CountryCode);
        dest.writeString(UserIpAddress);
        dest.writeString(UserAgent);
        dest.writeByte((byte) (IsMobile ? 0x01 : 0x00));
        dest.writeValue(PropertySearchRequestDto);
        dest.writeValue(PropertyFilter);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ListRequestBase> CREATOR = new Parcelable.Creator<ListRequestBase>() {
        @Override
        public ListRequestBase createFromParcel(Parcel in) {
            return new ListRequestBase(in);
        }

        @Override
        public ListRequestBase[] newArray(int size) {
            return new ListRequestBase[size];
        }
    };
}