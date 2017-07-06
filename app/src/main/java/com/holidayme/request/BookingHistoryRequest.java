package com.holidayme.request;

/**
 * Created by supriya.sakore on 23-10-2015.
 */
public class BookingHistoryRequest {
   private long HolzooBookingId;
    private int PageNumber;
    private int PageSize;
    private int BookingStatusType;
    private String Email ;
    private int AccountId;

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }

    public long getHolzooBookingId() {
        return HolzooBookingId;
    }

    public void setHolzooBookingId(long holzooBookingId) {
        HolzooBookingId = holzooBookingId;
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

    public int getBookingStatusType() {
        return BookingStatusType;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setBookingStatusType(int bookingStatusType) {
        BookingStatusType = bookingStatusType;
    }
}
