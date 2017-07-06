package com.holidayme.common;

/**
 * Created by santosh.patar on 29-09-2015.
 */
public enum TransactionStatusTypes {
    None (0), Successful(1), Fail(2),Void(3), InProgress(4);

    private int TransactionVal;




    TransactionStatusTypes(int numVal) {
        this.TransactionVal = numVal;
    }

    public int getTransactionVal() {
        return TransactionVal;
    }
}
