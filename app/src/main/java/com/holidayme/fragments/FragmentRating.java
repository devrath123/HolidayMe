package com.holidayme.fragments;

/**
 * Created by supriya.sakore on 06-08-2015.
 */

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
import com.holidayme.adapter.FilterStarRatingAdapter;
import com.holidayme.data.StarDto;
import com.holidayme.response.HotelListingInfoResponse;
import java.util.ArrayList;



public class FragmentRating extends BaseFragment {

    private View rootView;
    private ArrayList<StarDto> starRatingArrayList;

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

                starRatingArrayList = mHotelListResponse.getFilters().getStar();
            }
        }
    }

    @Override
    public void initUIElements() {

        RecyclerView ratingRecyclerView = (RecyclerView)rootView.findViewById(R.id.filterCatogoryRecyclerView);
        TextView headerTextView=(TextView)rootView.findViewById(R.id.headerTextView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ratingRecyclerView.setHasFixedSize(true);
        ratingRecyclerView.setLayoutManager(linearLayoutManager);

        // use a linear layout manager
        ratingRecyclerView.setLayoutManager(linearLayoutManager);

        // create an Object for Adapter
        FilterStarRatingAdapter filterStarRatingAdapter = new FilterStarRatingAdapter(starRatingArrayList,getActivity());
        ratingRecyclerView.setAdapter(filterStarRatingAdapter);

        headerTextView.setText(getActivity().getString(R.string.Select_your_preferred_Rating));
    }
}


