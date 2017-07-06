package com.holidayme.staycationbooking;

/**
 * Created by arshad.shaikh on 3/22/2017.
 */

public class GuestDetailsDTO {

    private  String GuestAddress,GuestCountryIsdCode,GuestFirstName,GuestLastName,GuestMiddleName,GuestMobileNumber,GuestSalutation,
            GuestEmail,LeaderTravellerSalutation,LeadTravellerFirstName,LeadTravellerMiddleName,LeadTravellerLastName;

    private boolean IsLeadTraveller,IsPrimaryGuest;


    public String getGuestAddress() {
        return GuestAddress;
    }

    public void setGuestAddress(String guestAddress) {
        GuestAddress = guestAddress;
    }

    public String getGuestCountryIsdCode() {
        return GuestCountryIsdCode;
    }

    public void setGuestCountryIsdCode(String guestCountryIsdCode) {
        GuestCountryIsdCode = guestCountryIsdCode;
    }

    public String getGuestFirstName() {
        return GuestFirstName;
    }

    public void setGuestFirstName(String guestFirstName) {
        GuestFirstName = guestFirstName;
    }

    public String getGuestLastName() {
        return GuestLastName;
    }

    public void setGuestLastName(String guestLastName) {
        GuestLastName = guestLastName;
    }

    public String getGuestMiddleName() {
        return GuestMiddleName;
    }

    public void setGuestMiddleName(String guestMiddleName) {
        GuestMiddleName = guestMiddleName;
    }

    public String getGuestMobileNumber() {
        return GuestMobileNumber;
    }

    public void setGuestMobileNumber(String guestMobileNumber) {
        GuestMobileNumber = guestMobileNumber;
    }

    public String getGuestSalutation() {
        return GuestSalutation;
    }

    public void setGuestSalutation(String guestSalutation) {
        GuestSalutation = guestSalutation;
    }

    public String getGuestEmail() {
        return GuestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        GuestEmail = guestEmail;
    }

    public String getLeaderTravellerSalutation() {
        return LeaderTravellerSalutation;
    }

    public void setLeaderTravellerSalutation(String leaderTravellerSalutation) {
        LeaderTravellerSalutation = leaderTravellerSalutation;
    }

    public String getLeadTravellerFirstName() {
        return LeadTravellerFirstName;
    }

    public void setLeadTravellerFirstName(String leadTravellerFirstName) {
        LeadTravellerFirstName = leadTravellerFirstName;
    }

    public String getLeadTravellerMiddleName() {
        return LeadTravellerMiddleName;
    }

    public void setLeadTravellerMiddleName(String leadTravellerMiddleName) {
        LeadTravellerMiddleName = leadTravellerMiddleName;
    }

    public String getLeadTravellerLastName() {
        return LeadTravellerLastName;
    }

    public void setLeadTravellerLastName(String leadTravellerLastName) {
        LeadTravellerLastName = leadTravellerLastName;
    }

    public boolean isLeadTraveller() {
        return IsLeadTraveller;
    }

    public void setLeadTraveller(boolean leadTraveller) {
        IsLeadTraveller = leadTraveller;
    }

    public boolean isPrimaryGuest() {
        return IsPrimaryGuest;
    }

    public void setPrimaryGuest(boolean primaryGuest) {
        IsPrimaryGuest = primaryGuest;
    }
}
