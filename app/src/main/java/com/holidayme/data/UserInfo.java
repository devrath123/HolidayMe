package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shaikh.salim on 7/22/2016.
 */

public class UserInfo implements Parcelable {
    String Email;
    String FirstName;
    String LastName;
    String IsdCode;
    String CountryOfResidence;


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getIsdCode() {
        return IsdCode;
    }

    public void setIsdCode(String isdCode) {
        IsdCode = isdCode;
    }

    public String getCountryOfResidence() {
        return CountryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        CountryOfResidence = countryOfResidence;
    }

    protected UserInfo(Parcel in) {
        Email = in.readString();
        FirstName = in.readString();
        LastName = in.readString();
        IsdCode = in.readString();
        CountryOfResidence = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Email);
        dest.writeString(FirstName);
        dest.writeString(LastName);
        dest.writeString(IsdCode);
        dest.writeString(CountryOfResidence);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
