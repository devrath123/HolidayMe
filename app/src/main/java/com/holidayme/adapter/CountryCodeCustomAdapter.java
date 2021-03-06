package com.holidayme.adapter;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.data.UserDTO;

/**
 * Created by supriya.sakore on 29-07-2016.
 */

public class CountryCodeCustomAdapter extends ArrayAdapter<String> {
    Activity mContext;
    private String[] Country = new String[0];
    private String[] Code = new String[0];
    LayoutInflater inflater;

    public CountryCodeCustomAdapter(Activity ctx, int txtViewResourceId, String[] country, String[] code) {
        super(ctx,txtViewResourceId,country);
        this.mContext=ctx;
        Country=country;
        Code=code;
        inflater = mContext.getLayoutInflater();
    }

    public View getView(int pos, View convertedView, ViewGroup parent)  {

        convertedView = inflater.inflate(R.layout.spinner_drop_down_row, parent, false);
        TextView main_text = (TextView) convertedView.findViewById(R.id.txt_spinner);
        main_text.setPadding(0,0,0,0);
        main_text.setText(Code[pos]);
        return convertedView;
    }


    public View getDropDownView(int pos, View convertedView, ViewGroup parent) {

        convertedView = inflater.inflate(R.layout.spinner_drop_down_row, parent, false);
        TextView main_text = (TextView) convertedView.findViewById(R.id.txt_spinner);
        main_text.setPadding(25,0,15,0);
        if(UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE))
        main_text.setGravity(Gravity.END);
        else
            main_text.setGravity(Gravity.START);

        main_text.setText(Country[pos]);
        return convertedView;
    }




}