package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by shaikh.salim on 7/18/2016.
 */

public class GetAreaByCityIdResponse extends ResponseBase implements Parcelable {
    HashMap<String, String> CodCityAreas;

    public HashMap<String, String> getCodCityAreas() {
        return CodCityAreas;
    }

    public void setCodCityAreas(HashMap<String, String> codCityAreas) {
        CodCityAreas = codCityAreas;
    }

    protected GetAreaByCityIdResponse(Parcel in) {
        CodCityAreas = (HashMap) in.readValue(HashMap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(CodCityAreas);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GetAreaByCityIdResponse> CREATOR = new Parcelable.Creator<GetAreaByCityIdResponse>() {
        @Override
        public GetAreaByCityIdResponse createFromParcel(Parcel in) {
            return new GetAreaByCityIdResponse(in);
        }

        @Override
        public GetAreaByCityIdResponse[] newArray(int size) {
            return new GetAreaByCityIdResponse[size];
        }
    };
}
