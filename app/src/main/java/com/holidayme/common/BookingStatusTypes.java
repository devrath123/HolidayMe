package com.holidayme.common;

/**
 * Created by santosh.patar on 29-09-2015.
 */
public enum BookingStatusTypes {

    None (0), Successful(1), Confirmed(2),Fail(3), InProgress(4),Pending(5),Deleted(6),Unconfirmed(7),Error(8),AmendSuccess(9),Rejected(10),PendingCancellation(11)
    ,PendingRequest(12),Amended(13),Extra(14);

    private int BookingStatusVal;

    BookingStatusTypes(int numVal) {
        this.BookingStatusVal = numVal;
    }

    public int getBookingStatusVal() {
        return BookingStatusVal;
    }


}
