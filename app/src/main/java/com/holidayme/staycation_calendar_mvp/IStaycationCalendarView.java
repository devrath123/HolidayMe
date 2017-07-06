package com.holidayme.staycation_calendar_mvp;

import org.json.JSONArray;

/**
 * Created by devrath.rathee on 18-04-2017.
 */

public interface IStaycationCalendarView {
    void showProgressDialog();
    void hideProgressDialog();
    void getMonths();
    void setAllocationDetails(JSONArray jsonArray);
}
