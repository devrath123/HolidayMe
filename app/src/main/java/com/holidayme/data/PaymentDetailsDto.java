package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 11-04-2017.
 */

public class PaymentDetailsDto implements Parcelable {
   private String CardNumber,CardExpiryDate,CardCvvNumber,CardHolderName;
    private int PaymentMode;

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getCardExpiryDate() {
        return CardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        CardExpiryDate = cardExpiryDate;
    }

    public String getCardCvvNumber() {
        return CardCvvNumber;
    }

    public void setCardCvvNumber(String cardCvvNumber) {
        CardCvvNumber = cardCvvNumber;
    }

    public int getPaymentMode() {
        return PaymentMode;
    }

    public void setPaymentMode(int paymentMode) {
        PaymentMode = paymentMode;
    }

    public String getCardHolderName() {
        return CardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        CardHolderName = cardHolderName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.CardNumber);
        dest.writeString(this.CardExpiryDate);
        dest.writeString(this.CardCvvNumber);
        dest.writeInt(this.PaymentMode);
    }

    public PaymentDetailsDto() {
    }

    protected PaymentDetailsDto(Parcel in) {
        this.CardNumber = in.readString();
        this.CardExpiryDate = in.readString();
        this.CardCvvNumber = in.readString();
        this.PaymentMode = in.readInt();
    }

    public static final Parcelable.Creator<PaymentDetailsDto> CREATOR = new Parcelable.Creator<PaymentDetailsDto>() {
        @Override
        public PaymentDetailsDto createFromParcel(Parcel source) {
            return new PaymentDetailsDto(source);
        }

        @Override
        public PaymentDetailsDto[] newArray(int size) {
            return new PaymentDetailsDto[size];
        }
    };
}
