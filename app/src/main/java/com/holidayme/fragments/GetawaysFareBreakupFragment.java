package com.holidayme.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.holidayme.activities.R;
import com.holidayme.data.UserDTO;

import java.util.Locale;

/**
 * Created by arshad.shaikh on 4/7/2017.
 */

public class GetawaysFareBreakupFragment extends  BaseFragment {


    private Context context;
    private View rootView;
    private ImageView backImageView;
    private  TextView titleTextView,totalPriceTextView,numberOfAllocationTextView,perPackageCostTextView;
    private double amountPayable;
    private int allocationCount;
    private UserDTO userDTO;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Bundle bundle = this.getArguments();
        if (bundle != null)
            getBundleParameters(bundle);

    }

    private void getBundleParameters(Bundle bundle) {


        allocationCount=bundle.getInt("selectedAllocation");
        amountPayable =  bundle.getDouble("amounts");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context= getActivity();
        userDTO = UserDTO.getUserDTO();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.getaways_farebreakup, container, false);
        return rootView;
    }

    @Override
    public void initUIElements() {

        backImageView = (ImageView) rootView.findViewById(R.id.headerBackPressImageView);
        titleTextView = (TextView) rootView.findViewById(R.id.headerTitleTextView);
        totalPriceTextView= (TextView) rootView.findViewById(R.id.totalPriceTextView);
        numberOfAllocationTextView= (TextView) rootView.findViewById(R.id.numberOfAllocationTextView);
        perPackageCostTextView= (TextView) rootView.findViewById(R.id.perPackageCostTextView);

        titleTextView.setText(getString(R.string.rate_breakup));

        uiFunctionality();
    }

    private void uiFunctionality() {



        totalPriceTextView.setText(userDTO.getCurrency()+" "+String.format(Locale.US, "%.2f", amountPayable));
        numberOfAllocationTextView.setText(String.valueOf(allocationCount));

        double perPackageCost=amountPayable/allocationCount;

        perPackageCostTextView.setText(userDTO.getCurrency()+" "+String.format(Locale.US, "%.2f", perPackageCost));


        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }
}
