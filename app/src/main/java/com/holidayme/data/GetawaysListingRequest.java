package com.holidayme.data;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 10-03-2017.
 */

public class GetawaysListingRequest {
    private Long CityId;
    private String CurrencyCode,IpAddress;
    private int SortBy,OrderBy;
    private ArrayList<GetawaysFilterRequestDto>Filter;
    public Long getCityId() {
        return CityId;
    }

    public void setCityId(Long cityId) {
        CityId = cityId;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public String getIpAddress() {
        return IpAddress;
    }

    public void setIpAddress(String ipAddress) {
        IpAddress = ipAddress;
    }

    public int getSortBy() {
        return SortBy;
    }

    public void setSortBy(int sortBy) {
        SortBy = sortBy;
    }

    public int getOrderBy() {
        return OrderBy;
    }

    public void setOrderBy(int orderBy) {
        OrderBy = orderBy;
    }

    public ArrayList<GetawaysFilterRequestDto> getFilter() {
        return Filter;
    }

    public void setFilter(ArrayList<GetawaysFilterRequestDto> filter) {
        Filter = filter;
    }
}
