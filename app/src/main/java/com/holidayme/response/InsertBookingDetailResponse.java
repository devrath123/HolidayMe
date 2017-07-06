package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shaikh.salim on 7/11/2016.
 */

public class InsertBookingDetailResponse extends ResponseBase implements Parcelable {
    private String HolzooBookingId;

    private PaymentGatewayPostRequest PaymentGatewayPostRequest;
    private String IsPayAtHotel;

    private String BookingPropertyId;

    public String getHolzooBookingId() {
        return HolzooBookingId;
    }

    public void setHolzooBookingId(String HolzooBookingId) {
        this.HolzooBookingId = HolzooBookingId;
    }

    public PaymentGatewayPostRequest getPaymentGatewayPostRequest() {
        return PaymentGatewayPostRequest;
    }

    public void setPaymentGatewayPostRequest(PaymentGatewayPostRequest PaymentGatewayPostRequest) {
        this.PaymentGatewayPostRequest = PaymentGatewayPostRequest;
    }

    public String getIsPayAtHotel() {
        return IsPayAtHotel;
    }

    public void setIsPayAtHotel(String IsPayAtHotel) {
        this.IsPayAtHotel = IsPayAtHotel;
    }

    public String getBookingPropertyId() {
        return BookingPropertyId;
    }

    public void setBookingPropertyId(String BookingPropertyId) {
        this.BookingPropertyId = BookingPropertyId;
    }


    protected InsertBookingDetailResponse(Parcel in) {
        HolzooBookingId = in.readString();
        PaymentGatewayPostRequest = (PaymentGatewayPostRequest) in.readValue(PaymentGatewayPostRequest.class.getClassLoader());
        IsPayAtHotel = in.readString();
        BookingPropertyId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(HolzooBookingId);
        dest.writeValue(PaymentGatewayPostRequest);
        dest.writeString(IsPayAtHotel);
        dest.writeString(BookingPropertyId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<InsertBookingDetailResponse> CREATOR = new Parcelable.Creator<InsertBookingDetailResponse>() {
        @Override
        public InsertBookingDetailResponse createFromParcel(Parcel in) {
            return new InsertBookingDetailResponse(in);
        }

        @Override
        public InsertBookingDetailResponse[] newArray(int size) {
            return new InsertBookingDetailResponse[size];
        }
    };
}
