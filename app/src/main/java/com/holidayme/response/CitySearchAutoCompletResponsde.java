package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.holidayme.data.CityDetail;
import com.holidayme.data.Destination;

import java.util.ArrayList;

/**
 * Created by salim on 08-06-2016.
 */
public class CitySearchAutoCompletResponsde extends ErrorResponseBase implements Parcelable {

    ArrayList<CityDetail> AutoComplete;

    public ArrayList<CityDetail> getAutoComplete() {
        return AutoComplete;
    }

    public void setAutoComplete(ArrayList<CityDetail> autoComplete) {
        AutoComplete = autoComplete;
    }

    public CitySearchAutoCompletResponsde() {

    }
    protected CitySearchAutoCompletResponsde(Parcel in) {
        AutoComplete = new ArrayList<CityDetail>();
        if (in.readByte() == 0x01) {
            in.readList(AutoComplete, CityDetail.class.getClassLoader());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (AutoComplete == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(AutoComplete);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CitySearchAutoCompletResponsde> CREATOR = new Parcelable.Creator<CitySearchAutoCompletResponsde>() {
        @Override
        public CitySearchAutoCompletResponsde createFromParcel(Parcel in) {
            return new CitySearchAutoCompletResponsde(in);
        }

        @Override
        public CitySearchAutoCompletResponsde[] newArray(int size) {
            return new CitySearchAutoCompletResponsde[size];
        }
    };
}