package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shaikh.salim on 7/11/2016.
 */

public class PaymentGatewayPostRequest implements Parcelable {
    private String PostUrl;

    private PostData PostData;

    private String ScriptToRender;

    public String getPostUrl() {
        return PostUrl;
    }

    public com.holidayme.response.PostData getPostData() {
        return PostData;
    }

    public void setPostData(com.holidayme.response.PostData postData) {
        PostData = postData;
    }

    public String getScriptToRender() {
        return ScriptToRender;
    }

    public void setScriptToRender(String scriptToRender) {
        ScriptToRender = scriptToRender;
    }

    public PaymentGatewayPostRequest() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.PostUrl);
        dest.writeParcelable((Parcelable) this.PostData, flags);
        dest.writeString(this.ScriptToRender);
    }

    protected PaymentGatewayPostRequest(Parcel in) {
        this.PostUrl = in.readString();
        this.PostData = in.readParcelable(com.holidayme.response.PostData.class.getClassLoader());
        this.ScriptToRender = in.readString();
    }

    public static final Creator<PaymentGatewayPostRequest> CREATOR = new Creator<PaymentGatewayPostRequest>() {
        @Override
        public PaymentGatewayPostRequest createFromParcel(Parcel source) {
            return new PaymentGatewayPostRequest(source);
        }

        @Override
        public PaymentGatewayPostRequest[] newArray(int size) {
            return new PaymentGatewayPostRequest[size];
        }
    };
}
