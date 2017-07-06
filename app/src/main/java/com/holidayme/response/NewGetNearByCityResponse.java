package com.holidayme.response;

import com.holidayme.data.CityDetail;
import com.holidayme.data.NearByCityDto;

/**
 * Created by santosh.patar on 26-08-2015.
 */
public class NewGetNearByCityResponse {

    private CityDetail City;

    private String Error;

    private long TotalExecutionTime;

    public CityDetail getCity() {
        return City;
    }

    public void setCity(CityDetail city) {
        City = city;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    public long getTotalExecutionTime() {
        return TotalExecutionTime;
    }

    public void setTotalExecutionTime(long totalExecutionTime) {
        TotalExecutionTime = totalExecutionTime;
    }
}
