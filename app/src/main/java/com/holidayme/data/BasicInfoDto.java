package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 07-06-2016.
 */
public class BasicInfoDto implements Parcelable {
    private Long CtyId;
    private String Cty;
    private Long AraId;
    private String Ara;
    private Long CntyId;
    private String Cnty;
    private double Lat;
    private double Long;
    private String CitySeoName;
    private String AreaSeoName;
    private String CountrySeoName;
    private ArrayList<String> LImg;
    private double Star;
    private String SDesc;
    private int Pop;
    private String Adrs;
    private boolean PAH;
    private String Promo;
    private TripAdvisorDto TripAdvisor;
    private ArrayList<AmenitieDTO> Amn;




    public java.lang.Long getCtyId() {
        return CtyId;
    }

    public void setCtyId(java.lang.Long ctyId) {
        CtyId = ctyId;
    }

    public String getCty() {
        return Cty;
    }

    public void setCty(String cty) {
        Cty = cty;
    }

    public java.lang.Long getAraId() {
        return AraId;
    }

    public void setAraId(java.lang.Long araId) {
        AraId = araId;
    }

    public String getAra() {
        return Ara;
    }

    public void setAra(String ara) {
        Ara = ara;
    }

    public java.lang.Long getCntyId() {
        return CntyId;
    }

    public void setCntyId(java.lang.Long cntyId) {
        CntyId = cntyId;
    }

    public String getCnty() {
        return Cnty;
    }

    public void setCnty(String cnty) {
        Cnty = cnty;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLong() {
        return Long;
    }

    public void setLong(double aLong) {
        Long = aLong;
    }

    public String getCitySeoName() {
        return CitySeoName;
    }

    public void setCitySeoName(String citySeoName) {
        CitySeoName = citySeoName;
    }

    public String getAreaSeoName() {
        return AreaSeoName;
    }

    public void setAreaSeoName(String areaSeoName) {
        AreaSeoName = areaSeoName;
    }

    public String getCountrySeoName() {
        return CountrySeoName;
    }

    public void setCountrySeoName(String countrySeoName) {
        CountrySeoName = countrySeoName;
    }

    public ArrayList<String> getLImg() {
        return LImg;
    }

    public void setLImg(ArrayList<String> LImg) {
        this.LImg = LImg;
    }

    public double getStar() {
        return Star;
    }

    public void setStar(double star) {
        Star = star;
    }

    public String getSDesc() {
        return SDesc;
    }

    public void setSDesc(String SDesc) {
        this.SDesc = SDesc;
    }

    public int getPop() {
        return Pop;
    }

    public void setPop(int pop) {
        Pop = pop;
    }

    public String getAdrs() {
        return Adrs;
    }

    public void setAdrs(String adrs) {
        Adrs = adrs;
    }

    public boolean isPAH() {
        return PAH;
    }

    public void setPAH(boolean PAH) {
        this.PAH = PAH;
    }

    public String getPromo() {
        return Promo;
    }

    public void setPromo(String promo) {
        Promo = promo;
    }

    public TripAdvisorDto getTripAdvisor() {
        return TripAdvisor;
    }

    public void setTripAdvisor(TripAdvisorDto tripAdvisor) {
        TripAdvisor = tripAdvisor;
    }

    public ArrayList<AmenitieDTO> getAmn() {
        return Amn;
    }

    public void setAmn(ArrayList<AmenitieDTO> amn) {
        Amn = amn;
    }

    protected BasicInfoDto(Parcel in) {
        CtyId = in.readByte() == 0x00 ? null : in.readLong();
        Cty = in.readString();
        AraId = in.readByte() == 0x00 ? null : in.readLong();
        Ara = in.readString();
        CntyId = in.readByte() == 0x00 ? null : in.readLong();
        Cnty = in.readString();
        Lat = in.readDouble();
        Long = in.readDouble();
        CitySeoName = in.readString();
        AreaSeoName = in.readString();
        CountrySeoName = in.readString();
        if (in.readByte() == 0x01) {
            LImg = new ArrayList<String>();
            in.readList(LImg, String.class.getClassLoader());
        } else {
            LImg = null;
        }
        Star = in.readDouble();
        SDesc = in.readString();
        Pop = in.readInt();
        Adrs = in.readString();
        PAH = in.readByte() != 0x00;
        Promo = in.readString();
        TripAdvisor = (TripAdvisorDto) in.readValue(TripAdvisorDto.class.getClassLoader());
        if (in.readByte() == 0x01) {
            Amn = new ArrayList<AmenitieDTO>();
            in.readList(Amn, AmenitieDTO.class.getClassLoader());
        } else {
            Amn = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (CtyId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(CtyId);
        }
        dest.writeString(Cty);
        if (AraId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(AraId);
        }
        dest.writeString(Ara);
        if (CntyId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(CntyId);
        }
        dest.writeString(Cnty);
        dest.writeDouble(Lat);
        dest.writeDouble(Long);
        dest.writeString(CitySeoName);
        dest.writeString(AreaSeoName);
        dest.writeString(CountrySeoName);
        if (LImg == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(LImg);
        }
        dest.writeDouble(Star);
        dest.writeString(SDesc);
        dest.writeInt(Pop);
        dest.writeString(Adrs);
        dest.writeByte((byte) (PAH ? 0x01 : 0x00));
        dest.writeString(Promo);
        dest.writeValue(TripAdvisor);
        if (Amn == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Amn);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BasicInfoDto> CREATOR = new Parcelable.Creator<BasicInfoDto>() {
        @Override
        public BasicInfoDto createFromParcel(Parcel in) {
            return new BasicInfoDto(in);
        }

        @Override
        public BasicInfoDto[] newArray(int size) {
            return new BasicInfoDto[size];
        }
    };
}