package com.holidayme.data;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 22-03-2017.
 */

public class GetawaysFilterRequestDto {
    private int FilterBy;
    private ArrayList<Long>FilterId;
    private double MinDistance,MaxDistance;


    public GetawaysFilterRequestDto(int filterBy, ArrayList<Long> filterId, double minDistance, double maxDistance) {
        FilterBy = filterBy;
        FilterId = filterId;
        MinDistance = minDistance;
        MaxDistance = maxDistance;
    }

    public int getFilterBy() {
        return FilterBy;
    }

    public void setFilterBy(int filterBy) {
        FilterBy = filterBy;
    }

    public ArrayList<Long> getFilterId() {
        return FilterId;
    }

    public void setFilterId(ArrayList<Long> filterId) {
        FilterId = filterId;
    }

    public double getMinDistance() {
        return MinDistance;
    }

    public void setMinDistance(double minDistance) {
        MinDistance = minDistance;
    }

    public double getMaxDistance() {
        return MaxDistance;
    }

    public void setMaxDistance(double maxDistance) {
        MaxDistance = maxDistance;
    }
}
