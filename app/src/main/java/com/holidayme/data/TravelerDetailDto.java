package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 21-04-2017.
 */

public class TravelerDetailDto implements Parcelable {
    private String Salutation,TravelerFirstName,TravelerMiddleName,TravelerLastName,TravelerEmailId,TravelerPhoneNumber;
     private boolean IsLeadTraveler;

    public String getSalutation() {
        return Salutation;
    }

    public void setSalutation(String salutation) {
        Salutation = salutation;
    }

    public String getTravelerFirstName() {
        return TravelerFirstName;
    }

    public void setTravelerFirstName(String travelerFirstName) {
        TravelerFirstName = travelerFirstName;
    }

    public String getTravelerMiddleName() {
        return TravelerMiddleName;
    }

    public void setTravelerMiddleName(String travelerMiddleName) {
        TravelerMiddleName = travelerMiddleName;
    }

    public String getTravelerLastName() {
        return TravelerLastName;
    }

    public void setTravelerLastName(String travelerLastName) {
        TravelerLastName = travelerLastName;
    }

    public String getTravelerEmailId() {
        return TravelerEmailId;
    }

    public void setTravelerEmailId(String travelerEmailId) {
        TravelerEmailId = travelerEmailId;
    }

    public String getTravelerPhoneNumber() {
        return TravelerPhoneNumber;
    }

    public void setTravelerPhoneNumber(String travelerPhoneNumber) {
        TravelerPhoneNumber = travelerPhoneNumber;
    }

    public boolean isLeadTraveler() {
        return IsLeadTraveler;
    }

    public void setLeadTraveler(boolean leadTraveler) {
        IsLeadTraveler = leadTraveler;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Salutation);
        dest.writeString(this.TravelerFirstName);
        dest.writeString(this.TravelerMiddleName);
        dest.writeString(this.TravelerLastName);
        dest.writeString(this.TravelerEmailId);
        dest.writeString(this.TravelerPhoneNumber);
        dest.writeByte(this.IsLeadTraveler ? (byte) 1 : (byte) 0);
    }

    public TravelerDetailDto() {
    }

    protected TravelerDetailDto(Parcel in) {
        this.Salutation = in.readString();
        this.TravelerFirstName = in.readString();
        this.TravelerMiddleName = in.readString();
        this.TravelerLastName = in.readString();
        this.TravelerEmailId = in.readString();
        this.TravelerPhoneNumber = in.readString();
        this.IsLeadTraveler = in.readByte() != 0;
    }

    public static final Parcelable.Creator<TravelerDetailDto> CREATOR = new Parcelable.Creator<TravelerDetailDto>() {
        @Override
        public TravelerDetailDto createFromParcel(Parcel source) {
            return new TravelerDetailDto(source);
        }

        @Override
        public TravelerDetailDto[] newArray(int size) {
            return new TravelerDetailDto[size];
        }
    };
}
