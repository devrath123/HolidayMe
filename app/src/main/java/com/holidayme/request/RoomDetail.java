package com.holidayme.request;

import java.util.ArrayList;

/**
 * Created by shaikh.salim on 7/11/2016.
 */

public class RoomDetail {

    private int RoomSrNo;
    private int TravellerSalutationId;
    private String TravellerSalutation;
    private String TravellerFirstName;
    private String TravellerMiddleName;
    private String TravellerLastName;
    private String TravellerCountry;
    private String BedTypeId;
    private String BedTypeDescription;
    private boolean IsExtraBedRequired;
    private boolean IsTwinRequired;
    private int NumberOfBeds;
    private boolean SmokingPreference;
    private ArrayList<Long> SpecialRequests;

    public int getRoomSrNo() {
        return this.RoomSrNo;
    }

    public void setRoomSrNo(int RoomSrNo) {
        this.RoomSrNo = RoomSrNo;
    }


    public int getTravellerSalutationId() {
        return this.TravellerSalutationId;
    }

    public void setTravellerSalutationId(int TravellerSalutationId) {
        this.TravellerSalutationId = TravellerSalutationId;
    }


    public String getTravellerSalutation() {
        return this.TravellerSalutation;
    }

    public void setTravellerSalutation(String TravellerSalutation) {
        this.TravellerSalutation = TravellerSalutation;
    }


    public String getTravellerFirstName() {
        return this.TravellerFirstName;
    }

    public void setTravellerFirstName(String TravellerFirstName) {
        this.TravellerFirstName = TravellerFirstName;
    }


    public String getTravellerMiddleName() {
        return this.TravellerMiddleName;
    }

    public void setTravellerMiddleName(String TravellerMiddleName) {
        this.TravellerMiddleName = TravellerMiddleName;
    }


    public String getTravellerLastName() {
        return this.TravellerLastName;
    }

    public void setTravellerLastName(String TravellerLastName) {
        this.TravellerLastName = TravellerLastName;
    }


    public String getTravellerCountry() {
        return this.TravellerCountry;
    }

    public void setTravellerCountry(String TravellerCountry) {
        this.TravellerCountry = TravellerCountry;
    }


    public String getBedTypeId() {
        return this.BedTypeId;
    }

    public void setBedTypeId(String BedTypeId) {
        this.BedTypeId = BedTypeId;
    }


    public String getBedTypeDescription() {
        return this.BedTypeDescription;
    }

    public void setBedTypeDescription(String BedTypeDescription) {
        this.BedTypeDescription = BedTypeDescription;
    }


    public boolean getIsExtraBedRequired() {
        return this.IsExtraBedRequired;
    }

    public void setIsExtraBedRequired(boolean IsExtraBedRequired) {
        this.IsExtraBedRequired = IsExtraBedRequired;
    }


    public boolean getIsTwinRequired() {
        return this.IsTwinRequired;
    }

    public void setIsTwinRequired(boolean IsTwinRequired) {
        this.IsTwinRequired = IsTwinRequired;
    }


    public int getNumberOfBeds() {
        return this.NumberOfBeds;
    }

    public void setNumberOfBeds(int NumberOfBeds) {
        this.NumberOfBeds = NumberOfBeds;
    }


    public boolean getSmokingPreference() {
        return this.SmokingPreference;
    }

    public void setSmokingPreference(boolean SmokingPreference) {
        this.SmokingPreference = SmokingPreference;
    }


    public ArrayList<Long> getSpecialRequests() {
        return this.SpecialRequests;
    }

    public void setSpecialRequests(ArrayList<Long> SpecialRequests) {
        this.SpecialRequests = SpecialRequests;
    }
}
