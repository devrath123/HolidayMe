package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.holidayme.data.BookingDTO;
import com.holidayme.data.HotelBookingDTO;

/**
 * Created by supriya.sakore on 19-08-2015.
 */
public class HotelBookConfirmationResponse extends ResponseBase implements Parcelable {
    private boolean ShowAdditionalTermsAndConditions;
    private boolean IsCod;
    private int CodRequestStatus;

    private BookingDTO<HotelBookingDTO> BookingDetails;

    public boolean isShowAdditionalTermsAndConditions() {
        return ShowAdditionalTermsAndConditions;
    }

    public void setShowAdditionalTermsAndConditions(boolean showAdditionalTermsAndConditions) {
        ShowAdditionalTermsAndConditions = showAdditionalTermsAndConditions;
    }

    public boolean isCod() {
        return IsCod;
    }

    public void setIsCod(boolean isCod) {
        IsCod = isCod;
    }

    public int getCodRequestStatus() {
        return CodRequestStatus;
    }

    public void setCodRequestStatus(int codRequestStatus) {
        CodRequestStatus = codRequestStatus;
    }



    public BookingDTO<HotelBookingDTO> getBookingDetails() {
        return BookingDetails;
    }

    public void setBookingDetails(BookingDTO<HotelBookingDTO> bookingDetails) {
        BookingDetails = bookingDetails;
    }

    public HotelBookConfirmationResponse() {
        super();
    }


    protected HotelBookConfirmationResponse(Parcel in) {
        ShowAdditionalTermsAndConditions = in.readByte() != 0x00;
        IsCod = in.readByte() != 0x00;
        CodRequestStatus = in.readInt();
        BookingDetails = (BookingDTO) in.readValue(BookingDTO.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (ShowAdditionalTermsAndConditions ? 0x01 : 0x00));
        dest.writeByte((byte) (IsCod ? 0x01 : 0x00));
        dest.writeInt(CodRequestStatus);
        dest.writeValue(BookingDetails);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HotelBookConfirmationResponse> CREATOR = new Parcelable.Creator<HotelBookConfirmationResponse>() {
        @Override
        public HotelBookConfirmationResponse createFromParcel(Parcel in) {
            return new HotelBookConfirmationResponse(in);
        }

        @Override
        public HotelBookConfirmationResponse[] newArray(int size) {
            return new HotelBookConfirmationResponse[size];
        }
    };
}