package com.holidayme.data;

/**
 * Created by devrath.rathee on 21-03-2017.
 */

public class AllocationDTO {
   String AllocationDate,Title,UserAgent,ErrorMessage;
    int AvailabilityStatus,Allocation,Id,ErrorCode;
    double Price;
    boolean IsError;

    public String getAllocationDate() {
        return AllocationDate;
    }

    public void setAllocationDate(String allocationDate) {
        AllocationDate = allocationDate;
    }

    public int getAvailabilityStatus() {
        return AvailabilityStatus;
    }

    public void setAvailabilityStatus(int availabilityStatus) {
        AvailabilityStatus = availabilityStatus;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUserAgent() {
        return UserAgent;
    }

    public void setUserAgent(String userAgent) {
        UserAgent = userAgent;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public int getAllocation() {
        return Allocation;
    }

    public void setAllocation(int allocation) {
        Allocation = allocation;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public boolean isError() {
        return IsError;
    }

    public void setError(boolean error) {
        IsError = error;
    }
}
