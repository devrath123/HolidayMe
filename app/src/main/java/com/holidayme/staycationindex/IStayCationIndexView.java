package com.holidayme.staycationindex;

import com.google.gson.JsonArray;
import com.holidayme.data.PackageDetailResponse;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by arshad.shaikh on 3/22/2017.
 */

public interface IStayCationIndexView {

    void showDialog();
    void dismissDialog();
    void setGetawaysPackageCount(ArrayList<GetawaysPackages> jsonArray);
    void retryGetawaysPackageCount();
}
