package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.holidayme.data.Destination;

import java.util.ArrayList;

/**
 * Created by santosh.patar on 12-08-2015.
 */
public class AutoCompletResponsde extends ErrorResponseBase implements Parcelable {
    String TimeTaken;

    private ArrayList<Destination> Destinations;

    public String getTimeTaken() {
        return TimeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        TimeTaken = timeTaken;
    }

    public ArrayList<Destination> getDestinations() {
        return Destinations;
    }

    public void setDestinations(ArrayList<Destination> destinations) {
        Destinations = destinations;
    }

    public AutoCompletResponsde() {

    }


    protected AutoCompletResponsde(Parcel in) {
        TimeTaken = in.readString();
        if (in.readByte() == 0x01) {
            Destinations = new ArrayList<Destination>();
            in.readList(Destinations, Destination.class.getClassLoader());
        } else {
            Destinations = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TimeTaken);
        if (Destinations == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Destinations);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<AutoCompletResponsde> CREATOR = new Parcelable.Creator<AutoCompletResponsde>() {
        @Override
        public AutoCompletResponsde createFromParcel(Parcel in) {
            return new AutoCompletResponsde(in);
        }

        @Override
        public AutoCompletResponsde[] newArray(int size) {
            return new AutoCompletResponsde[size];
        }
    };
}