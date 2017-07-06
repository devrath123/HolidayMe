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

public class SpinnerCustomAdapter extends ArrayAdapter<String> {
    Activity mContext;
    private String[] salutation;
    LayoutInflater inflater;

    public SpinnerCustomAdapter(Activity ctx, int txtViewResourceId, String[] objects) {
        super(ctx, txtViewResourceId, objects);
        this.mContext=ctx;
        salutation=objects;
        inflater =mContext.getLayoutInflater();

    }
    public View getView(int pos, View convertedView, ViewGroup parent)  {

        convertedView = inflater.inflate(R.layout.spinner_drop_down_row, parent, false);
        TextView main_text = (TextView) convertedView.findViewById(R.id.txt_spinner);
        main_text.setPadding(0,0,0,0);
        main_text.setText(salutation[pos]);
        return convertedView;
    }


    public View getDropDownView(int pos, View convertedView, ViewGroup parent) {

        convertedView = inflater.inflate(R.layout.spinner_drop_down_row, parent, false);
        TextView main_text = (TextView) convertedView.findViewById(R.id.txt_spinner);
        main_text.setPadding(15,0,15,0);
        main_text.setText(salutation[pos]);
        return convertedView;
    }




}