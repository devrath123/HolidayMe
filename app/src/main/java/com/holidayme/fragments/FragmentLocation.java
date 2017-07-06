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
import com.holidayme.adapter.FilterLocationAdapter;
import com.holidayme.data.CatDto;
import com.holidayme.response.HotelListingInfoResponse;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 24-08-2015.
 */

public class FragmentLocation extends BaseFragment {

    private View rootView;
    private ArrayList<CatDto> topAreaArrayList;

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

         HotelListingInfoResponse mHotelListResponse;

        Bundle bundle = this.getArguments();

        if (bundle != null) {

            mHotelListResponse = bundle.getParcelable(Constant.HOTEL_LISTING_RESPONSE);

            if(mHotelListResponse!=null){

                topAreaArrayList = mHotelListResponse.getFilters().getArea();
            }
        }
    }

    @Override
    public void initUIElements() {

        RecyclerView filterLocationsRecyclerView = (RecyclerView)rootView.findViewById(R.id.filterCatogoryRecyclerView);
        TextView headerTextView=(TextView)rootView.findViewById(R.id.headerTextView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        filterLocationsRecyclerView.setHasFixedSize(true);
        filterLocationsRecyclerView.setLayoutManager(linearLayoutManager);

        FilterLocationAdapter filterLocationAdapter = new FilterLocationAdapter(topAreaArrayList);
        filterLocationsRecyclerView.setAdapter(filterLocationAdapter);

        headerTextView.setText(getActivity().getString(R.string.Select_your_preferred_location));

    }

}
