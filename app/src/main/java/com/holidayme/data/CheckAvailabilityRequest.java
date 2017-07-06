package com.holidayme.data;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 23-03-2017.
 */

public class CheckAvailabilityRequest {
   private long RoomId,PackageId,SubPackageId;
   private String CurrencyCode,IpAddress;
   private int Allocation;
    private ArrayList<String>AllocationDates;

    public long getRoomId() {
        return RoomId;
    }

    public void setRoomId(long roomId) {
        RoomId = roomId;
    }

    public long getPackageId() {
        return PackageId;
    }

    public void setPackageId(long packageId) {
        PackageId = packageId;
    }

    public long getSubPackageId() {
        return SubPackageId;
    }

    public void setSubPackageId(long subPackageId) {
        SubPackageId = subPackageId;
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

    public int getAllocation() {
        return Allocation;
    }

    public void setAllocation(int allocation) {
        Allocation = allocation;
    }

    public ArrayList<String> getAllocationDates() {
        return AllocationDates;
    }

    public void setAllocationDates(ArrayList<String> allocationDates) {
        AllocationDates = allocationDates;
    }
}
