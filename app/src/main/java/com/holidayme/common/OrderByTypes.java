package com.holidayme.common;

/**
 * Created by santosh.patar on 25-08-2015.
 */
public enum OrderByTypes {

    Ascending(1), Descending(0);
    private int orderVal;

    OrderByTypes(int orderVal) {
        this.orderVal = orderVal;
    }

    public int getOrderVal() {
        return orderVal;
    }
}
