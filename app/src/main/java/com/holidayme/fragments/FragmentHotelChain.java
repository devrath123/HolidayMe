package com.holidayme.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.adapter.FilterHotelChainAdapter;
import com.holidayme.data.CatDto;
import com.holidayme.response.HotelListingInfoResponse;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 24-08-2015.
 */

public class FragmentHotelChain extends BaseFragment {

    private View rootView;

    private ArrayList<CatDto> hotelChainArrayList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.filter_category_fragment, container, false);
        return rootView;
    }


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        Bundle bundle = this.getArguments();
        HotelListingInfoResponse mHotelListResponse;

        if (bundle != null) {

            mHotelListResponse = bundle.getParcelable(Constant.HOTEL_LISTING_RESPONSE);
            hotelChainArrayList = mHotelListResponse.getFilters().getChain();
        }
    }


    @Override
    public void initUIElements() {
        RecyclerView hotelChainRecyclerView = (RecyclerView) rootView.findViewById(R.id.filterCatogoryRecyclerView);
        TextView headerTextView = (TextView) rootView.findViewById(R.id.headerTextView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        hotelChainRecyclerView.setHasFixedSize(true);
        hotelChainRecyclerView.setLayoutManager(linearLayoutManager);

        // use a linear layout manager
        hotelChainRecyclerView.setLayoutManager(linearLayoutManager);

        // create an Object for Adapter
        FilterHotelChainAdapter filterHotelChainAdapter = new FilterHotelChainAdapter(hotelChainArrayList);

        // set the adapter object to the Recyclerview
        hotelChainRecyclerView.setAdapter(filterHotelChainAdapter);

        headerTextView.setText(getActivity().getString(R.string.Select_your_preferred_Hotels));

    }

}
