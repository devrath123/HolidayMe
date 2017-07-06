package com.holidayme.data;

import java.util.ArrayList;

/**
 * Created by santosh.patar on 17-08-2015.
 */
public class HotelAccommodation {


     private int AdultsCount;

     private ArrayList<Integer> ChildrenAges;

     private int  kids;

     private int kid1Age;

    private int kid2Age;

    public HotelAccommodation(int adultsCount, ArrayList<Integer> childrenAges, int kids, int kid1Age, int kid2Age) {
        AdultsCount = adultsCount;
        ChildrenAges = childrenAges;
        this.kids = kids;
        this.kid1Age = kid1Age;
        this.kid2Age = kid2Age;
    }

    public HotelAccommodation() {

    }

    public int getAdultsCount() {
        return AdultsCount;
    }

    public void setAdultsCount(int adultsCount) {
        AdultsCount = adultsCount;
    }

    public ArrayList<Integer> getChildrenAges() {
        return ChildrenAges;
    }

    public void setChildrenAges(ArrayList<Integer> childrenAges) {
        ChildrenAges = childrenAges;
    }

    public int getKid1Age() {
        return kid1Age;
    }

    public void setKid1Age(int kid1Age) {
        this.kid1Age = kid1Age;
    }

    public int getKid2Age() {
        return kid2Age;
    }

    public void setKid2Age(int kid2Age) {
        this.kid2Age = kid2Age;
    }

    public int getKids() {
        return kids;
    }

    public void setKids(int kids) {
        this.kids = kids;
    }

}
