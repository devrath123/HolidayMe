package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 09-06-2016.
 */
public class FiltersDto implements Parcelable, Cloneable {
    private ArrayList<CatDto> Cat;
    private ArrayList<StarDto> Star;
    private ArrayList<AmeDto> Ame;
    private ArrayList<TripDto> Trip;
    private ArrayList<CatDto> Area;
    private ArrayList<CatDto> Chain;
    private boolean IsWifi;
    private boolean IsDeals;
    private boolean IsPah;

    public ArrayList<CatDto> getCat() {
        if (this.Cat == null)
            Cat = new ArrayList<>();
        return Cat;
    }

    public void setCat(ArrayList<CatDto> cat) {
        Cat = cat;
    }

    public ArrayList<StarDto> getStar() {
        if (this.Star == null)
            Star = new ArrayList<>();
        return Star;
    }

    public void setStar(ArrayList<StarDto> star) {
        Star = star;
    }

    public ArrayList<AmeDto> getAme() {
        if (this.Ame == null)
            Ame = new ArrayList<>();
        return Ame;
    }

    public void setAme(ArrayList<AmeDto> ame) {
        Ame = ame;
    }

    public ArrayList<TripDto> getTrip() {
        if (this.Trip == null)
            Trip = new ArrayList<>();
        return Trip;
    }

    public void setTrip(ArrayList<TripDto> trip) {
        Trip = trip;
    }

    public ArrayList<CatDto> getArea() {
        if (this.Area == null)
            Area = new ArrayList<>();
        return Area;
    }

    public void setArea(ArrayList<CatDto> area) {
        Area = area;
    }

    public ArrayList<CatDto> getChain() {
        if (this.Chain == null)
            Chain = new ArrayList<>();
        return Chain;
    }

    public void setChain(ArrayList<CatDto> chain) {
        Chain = chain;
    }

    public boolean isWifi() {
        return IsWifi;
    }

    public void setIsWifi(boolean isWifi) {
        IsWifi = isWifi;
    }

    public boolean isDeals() {
        return IsDeals;
    }

    public void setIsDeals(boolean isDeals) {
        IsDeals = isDeals;
    }

    public boolean isPah() {
        return IsPah;
    }

    public void setIsPah(boolean isPah) {
        IsPah = isPah;
    }

    protected FiltersDto(Parcel in) {
        if (in.readByte() == 0x01) {
            Cat = new ArrayList<CatDto>();
            in.readList(Cat, CatDto.class.getClassLoader());
        } else {
            Cat = null;
        }
        if (in.readByte() == 0x01) {
            Star = new ArrayList<StarDto>();
            in.readList(Star, StarDto.class.getClassLoader());
        } else {
            Star = null;
        }
        if (in.readByte() == 0x01) {
            Ame = new ArrayList<AmeDto>();
            in.readList(Ame, AmeDto.class.getClassLoader());
        } else {
            Ame = null;
        }
        if (in.readByte() == 0x01) {
            Trip = new ArrayList<TripDto>();
            in.readList(Trip, TripDto.class.getClassLoader());
        } else {
            Trip = null;
        }
        if (in.readByte() == 0x01) {
            Area = new ArrayList<CatDto>();
            in.readList(Area, CatDto.class.getClassLoader());
        } else {
            Area = null;
        }
        if (in.readByte() == 0x01) {
            Chain = new ArrayList<CatDto>();
            in.readList(Chain, CatDto.class.getClassLoader());
        } else {
            Chain = null;
        }
        IsWifi = in.readByte() != 0x00;
        IsDeals = in.readByte() != 0x00;
        IsPah = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (Cat == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Cat);
        }
        if (Star == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Star);
        }
        if (Ame == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Ame);
        }
        if (Trip == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Trip);
        }
        if (Area == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Area);
        }
        if (Chain == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Chain);
        }
        dest.writeByte((byte) (IsWifi ? 0x01 : 0x00));
        dest.writeByte((byte) (IsDeals ? 0x01 : 0x00));
        dest.writeByte((byte) (IsPah ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FiltersDto> CREATOR = new Parcelable.Creator<FiltersDto>() {
        @Override
        public FiltersDto createFromParcel(Parcel in) {
            return new FiltersDto(in);
        }

        @Override
        public FiltersDto[] newArray(int size) {
            return new FiltersDto[size];
        }
    };

    @Override
    public Object clone() throws CloneNotSupportedException {
        FiltersDto cloned = (FiltersDto) super.clone();
        ArrayList<CatDto> catDtos = new ArrayList<CatDto>();
        if (cloned != null) {
            if (cloned.getCat() != null && !cloned.getCat().isEmpty())
                for (int i = 0; i < cloned.getCat().size(); i++) {
                    CatDto catDto = (CatDto) cloned.getCat().get(i).clone();
                    catDtos.add(catDto);
                }
            cloned.setCat(catDtos);
        }


        ArrayList<StarDto> starDtos = new ArrayList<StarDto>();

        if (cloned != null && cloned.getStar() != null && !cloned.getStar().isEmpty()) {
            for (int i = 0; i < cloned.getStar().size(); i++) {
                StarDto starDto = (StarDto) cloned.getStar().get(i).clone();
                starDtos.add(starDto);
            }
            cloned.setStar(starDtos);
        }


        ArrayList<AmeDto> ameDtos = new ArrayList<AmeDto>();
        if (cloned != null && cloned.getAme() != null && !cloned.getAme().isEmpty()) {
            for (int i = 0; i < cloned.getAme().size(); i++) {
                AmeDto ameDto = (AmeDto) cloned.getAme().get(i).clone();
                ameDtos.add(ameDto);
            }
            cloned.setAme(ameDtos);
        }


        ArrayList<TripDto> tripDtos = new ArrayList<TripDto>();
        if (cloned != null && cloned.getTrip() != null && !cloned.getTrip().isEmpty()) {
            for (int i = 0; i < cloned.getTrip().size(); i++) {
                TripDto tripDto = (TripDto) cloned.getTrip().get(i).clone();
                tripDtos.add(tripDto);
            }
            cloned.setTrip(tripDtos);
        }

        ArrayList<CatDto> areaDtos = new ArrayList<CatDto>();
        if (cloned != null && cloned.getArea() != null && !cloned.getArea().isEmpty()) {
            for (int i = 0; i < cloned.getArea().size(); i++) {
                CatDto catDto = (CatDto) cloned.getArea().get(i).clone();
                areaDtos.add(catDto);
            }
            cloned.setArea(areaDtos);
        }

        ArrayList<CatDto> chainDtos = new ArrayList<CatDto>();
        if (cloned != null && cloned.getChain() != null && !cloned.getChain().isEmpty()) {
            for (int i = 0; i < cloned.getChain().size(); i++) {
                CatDto catDto = (CatDto) cloned.getChain().get(i).clone();
                chainDtos.add(catDto);
            }
            cloned.setChain(chainDtos);
        }

        return cloned;
    }
}