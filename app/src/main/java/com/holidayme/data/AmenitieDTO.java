package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by supriya.sakore on 07-06-2016.
 */
public class AmenitieDTO implements Parcelable {
    private Long Id;
    private String Ttl;
    private String Url;
    private int Priority;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTtl() {
        return Ttl;
    }

    public void setTtl(String ttl) {
        Ttl = ttl;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public int getPriority() {
        return Priority;
    }

    public void setPriority(int priority) {
        Priority = priority;
    }

    protected AmenitieDTO(Parcel in) {
        Id = in.readByte() == 0x00 ? null : in.readLong();
        Ttl = in.readString();
        Url = in.readString();
        Priority = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (Id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(Id);
        }
        dest.writeString(Ttl);
        dest.writeString(Url);
        dest.writeInt(Priority);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<AmenitieDTO> CREATOR = new Parcelable.Creator<AmenitieDTO>() {
        @Override
        public AmenitieDTO createFromParcel(Parcel in) {
            return new AmenitieDTO(in);
        }

        @Override
        public AmenitieDTO[] newArray(int size) {
            return new AmenitieDTO[size];
        }
    };
}