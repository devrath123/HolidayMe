package com.holidayme.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.holidayme.activities.R;

/**
 * Created by arshad.shaikh on 4/7/2017.
 */

public class HotelPoliciesFragment extends  BaseFragment {


    private Context context;
    private View rootView;
    private ImageView backImageView;
    private TextView  hotelPoliciesTextView;
    private  TextView titleTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.getaways_farebreakup, container, false);
        return rootView;
    }

    @Override
    public void initUIElements() {

        backImageView = (ImageView) rootView.findViewById(R.id.headerBackPressImageView);
        hotelPoliciesTextView= (TextView) rootView.findViewById(R.id.hotelPoliciesTextView);
        titleTextView = (TextView) rootView.findViewById(R.id.headerTitleTextView);

        titleTextView.setText(getResources().getString(R.string.HOTEL_POLICY_OF));

        uiFunctionality();
    }

    private void uiFunctionality() {

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }
}
