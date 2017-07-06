package com.holidayme.data;

import java.util.ArrayList;

/**
 * Created by devrath.rathee on 02-03-2017.
 */

public class NearandAroundBean {


    ArrayList<FourSquare> FourSquarePoi;

    public ArrayList<FourSquare> getFourSquarePoi() {
        return FourSquarePoi;
    }

    public void setFourSquarePoi(ArrayList<FourSquare> fourSquarePoi) {
        FourSquarePoi = fourSquarePoi;
    }

    public class FourSquare
    {
      String Name,CatName,Dist;
      int Cat;
      double Lat,Longi;

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getCatName() {
            return CatName;
        }

        public void setCatName(String catName) {
            CatName = catName;
        }

        public String getDist() {
            return Dist;
        }

        public void setDist(String dist) {
            Dist = dist;
        }

        public int getCat() {
            return Cat;
        }

        public void setCat(int cat) {
            Cat = cat;
        }

        public double getLat() {
            return Lat;
        }

        public void setLat(double lat) {
            Lat = lat;
        }

        public double getLongi() {
            return Longi;
        }

        public void setLongi(double longi) {
            Longi = longi;
        }
    }


}
