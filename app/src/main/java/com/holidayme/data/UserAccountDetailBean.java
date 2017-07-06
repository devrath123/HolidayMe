package com.holidayme.data;

/**
 * Created by devrath.rathee on 07-10-2016.
 */


public class UserAccountDetailBean {

    int AccountId;
    String Salutation, FirstName, MiddleName, LastName,DisplayName, Phone, Email, UserSecret, TransactionId, UserTrackingId,SessionId, Error;
    long TotalExecutionTime;


    public String getSalutation() {
        return Salutation;
    }

    public void setSalutation(String salutation) {
        Salutation = salutation;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserSecret() {
        return UserSecret;
    }

    public void setUserSecret(String userSecret) {
        UserSecret = userSecret;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
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

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }

    public long getTotalExecutionTime() {
        return TotalExecutionTime;
    }

    public void setTotalExecutionTime(long totalExecutionTime) {
        TotalExecutionTime = totalExecutionTime;
    }
}
