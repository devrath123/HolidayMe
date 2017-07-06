package com.holidayme.data;

import java.util.ArrayList;

/**
 * Created by devrath.rathee on 02-03-2017.
 */

public class AmenitiesDTO {

    private ArrayList<Amenity> Amenities;
    private String TraxId,Error;
    private int ExecTime;

    public String getTraxId() {
        return TraxId;
    }

    public void setTraxId(String traxId) {
        TraxId = traxId;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    public int getExecTime() {
        return ExecTime;
    }

    public void setExecTime(int execTime) {
        ExecTime = execTime;
    }

    public ArrayList<Amenity> getAmenities() {
        return Amenities;
    }

    public void setAmenities(ArrayList<Amenity> amenities) {
        Amenities = amenities;
    }

    public static class Amenity {
        int Id, Priority;
        String Ttl, Url, Description;

        public Amenity(int id, int priority, String ttl, String url, String description) {
            Id = id;
            Priority = priority;
            Ttl = ttl;
            Url = url;
            Description = description;
        }

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public int getPriority() {
            return Priority;
        }

        public void setPriority(int priority) {
            Priority = priority;
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

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }
    }
}
