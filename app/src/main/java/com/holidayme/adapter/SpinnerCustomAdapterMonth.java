package com.holidayme.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.holidayme.activities.R;

/**
 * Created by supriya.sakore on 29-07-2016.
 */

public class SpinnerCustomAdapterMonth extends ArrayAdapter<String> {
    Activity mContext;
    private String[] Month = new String[0];
    private String[] MonthVal = new String[0];
    LayoutInflater inflater;

    public SpinnerCustomAdapterMonth(Activity ctx, int txtViewResourceId, String[] month, String[]monthval) {
        super(ctx, txtViewResourceId, month);
        this.mContext=ctx;
        Month=month;
        MonthVal=monthval;
        inflater =mContext.getLayoutInflater();

    }

    public View getView(int pos, View convertedView, ViewGroup parent)  {

        convertedView = inflater.inflate(R.layout.spinner_drop_down_row, parent, false);
        TextView main_text = (TextView) convertedView.findViewById(R.id.txt_spinner);
        main_text.setPadding(0,0,0,0);
        main_text.setText(MonthVal[pos]);
        return convertedView;
    }


    public View getDropDownView(int pos, View convertedView, ViewGroup parent) {

        convertedView = inflater.inflate(R.layout.spinner_drop_down_row, parent, false);
        TextView main_text = (TextView) convertedView.findViewById(R.id.txt_spinner);
        main_text.setPadding(25,0,25,0);
        main_text.setText(Month[pos]);
        return convertedView;
    }




}