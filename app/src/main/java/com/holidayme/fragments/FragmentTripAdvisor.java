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
import com.holidayme.adapter.FilterTripAdviserAdapter;
import com.holidayme.data.TripDto;
import com.holidayme.response.HotelListingInfoResponse;
import java.util.ArrayList;



public class FragmentTripAdvisor extends BaseFragment {

    private View rootView;
    private ArrayList<TripDto> tripAdvisorArrayList;

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

            if(mHotelListResponse!=null){
                tripAdvisorArrayList = mHotelListResponse.getFilters().getTrip();
            }
        }
    }


    @Override
    public void initUIElements() {

        RecyclerView tripAdviserRecyclerView = (RecyclerView)rootView.findViewById(R.id.filterCatogoryRecyclerView);
        TextView headerTextView=(TextView)rootView.findViewById(R.id.headerTextView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        tripAdviserRecyclerView.setHasFixedSize(true);
        tripAdviserRecyclerView.setLayoutManager(linearLayoutManager);

        // use a linear layout manager
        tripAdviserRecyclerView.setLayoutManager(linearLayoutManager);

        // create an Object for Adapter
         FilterTripAdviserAdapter filterTripAdvisorAdapter = new FilterTripAdviserAdapter(tripAdvisorArrayList);

        // set the adapter object to the RecyclerView
        tripAdviserRecyclerView.setAdapter(filterTripAdvisorAdapter);

        headerTextView.setText(getActivity().getString(R.string.Select_your_prefered_trip_advisor_rating));

    }
}
