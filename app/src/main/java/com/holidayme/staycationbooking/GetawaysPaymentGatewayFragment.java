package com.holidayme.staycationbooking;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.holidayme.activities.R;

/**
 * Created by arshad.shaikh on 3/21/2017.
 */

public class GetawaysPaymentGatewayFragment extends Fragment {

    private Context context;
    private  View view;
    private WebView getawayBookingWebView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.getaways_paymentgateway_page, container, false);

        initUI();

        setData();

        return view;
    }

    private void setData() {



    }

    private void initUI() {

        getawayBookingWebView= (WebView) view.findViewById(R.id.getawayBookingWebView);

    }
}

