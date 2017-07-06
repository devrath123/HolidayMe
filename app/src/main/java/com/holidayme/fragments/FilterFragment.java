package com.holidayme.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.appevents.AppEventsLogger;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.EndlessRecyclerOnScrollListener;
import com.holidayme.activities.util.Utilities;
import com.holidayme.common.Log;
import com.holidayme.common.PropertyFilterType;
import com.holidayme.data.AmeDto;
import com.holidayme.data.CatDto;
import com.holidayme.data.StarDto;
import com.holidayme.data.TripDto;
import com.holidayme.data.UserDTO;
import com.holidayme.gtm.GTMAnalytics;
import com.holidayme.managers.SharedPreferenceManager;
import com.holidayme.request.HotelListingRequest;
import com.holidayme.response.HotelListingInfoResponse;
import com.holidayme.widgets.CustomRangeBar;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 12-08-2015.
 */

public class FilterFragment extends BaseFragment implements CustomRangeBar.SeekBarChangeListener,View.OnClickListener {


    public static Handler filterUpdate;

    private RelativeLayout locationFilterRelativeLayout, priceFilterRelativeLayout, amenityFilterRelativeLayout, themeFilterRelativeLayout, hotelChainFilterRelativeLayout, ratingFilterRelativeLayout, tripFilterRelativeLayout,applyRelativeLayout, clearRelativeLayout;
    private  ImageView locationIconImageView, priceIconImageView, amenityIconImageView, hotelChainIconImageView, ratingIconImageView, tripAdviserIconImageView, themeIconImageView, lockTickImageView, priceTickImageView, amenitiesTickImageView, hotelChainTickImageView, ratingTickImageView, tripAdviserTickImageView, themeTickImageView;
    private TextView locationTextView, priceTextView, amenitiesTextView, hotelChainTextView, ratingTextView, tripAdviserTextView, themeTextView, applyTextView;
    private View rootView;
    private int typeClick,lastFilterApply = -1,oldfilter ;
    private HotelListingInfoResponse hotelListingInfoResponse, oldHotelListResponse;
    private boolean resetFilters;
    private String destinationName = "";
    private GTMAnalytics gtmAnalytics;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.filter, container, false);

        HotelListingFragment.isONAttach = false;
        HotelListingFragment.scrollToTop = false;
        EndlessRecyclerOnScrollListener.current_page=1;
        if (UserDTO.getUserDTO().getDestinationName() != null && !(UserDTO.getUserDTO().getDestinationName().equals(""))) {
            destinationName = UserDTO.getUserDTO().getDestinationName();
        } else if (UserDTO.getUserDTO().getCityName() != null && !(UserDTO.getUserDTO().getCityName().equals(""))) {
            destinationName = UserDTO.getUserDTO().getCityName();
        } else {
            destinationName = getActivity().getResources().getString(R.string.hotel);
        }

        initUI();
        backPressed();
        filterUpdate();
        setHeaderTitle();
        setHeaderInfo();
        setFiltersTickVisibilities();

        return rootView;
    }

    private void initUI() {

        gtmAnalytics = AppController.getInstance().getGTMAnalytics(getActivity());
        gtmAnalytics.setScreenName("Filter Screen");

        // initializing widgets.
        locationFilterRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.locationFilterRelativeLayout);
        locationTextView = (TextView) rootView.findViewById(R.id.locationTextView);
        locationIconImageView = (ImageView) rootView.findViewById(R.id.locationIconImageView);
        lockTickImageView = (ImageView) rootView.findViewById(R.id.lockTickImageView);
        priceTextView = (TextView) rootView.findViewById(R.id.priceTextView);
        priceIconImageView = (ImageView) rootView.findViewById(R.id.priceIconImageView);
        priceTickImageView = (ImageView) rootView.findViewById(R.id.priceTickImageView);
        ratingTextView = (TextView) rootView.findViewById(R.id.ratingTextView);
        ratingIconImageView = (ImageView) rootView.findViewById(R.id.ratingIconImageView);
        ratingTickImageView = (ImageView) rootView.findViewById(R.id.ratingTickImageView);
        amenitiesTextView = (TextView) rootView.findViewById(R.id.amenitiesTextView);
        amenityIconImageView = (ImageView) rootView.findViewById(R.id.amenityIconImageView);
        amenitiesTickImageView = (ImageView) rootView.findViewById(R.id.amenitiesTickImageView);
        hotelChainFilterRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.hotelChainFilterRelativeLayout);
        hotelChainTextView = (TextView) rootView.findViewById(R.id.hotelChainTextView);
        hotelChainIconImageView = (ImageView) rootView.findViewById(R.id.hotelChainIconImageView);
        hotelChainTickImageView = (ImageView) rootView.findViewById(R.id.hotelChainTickImageView);
        clearRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.clearRelativeLayout);
        applyRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.applyRelativeLayout);
        applyTextView = (TextView) rootView.findViewById(R.id.applyTextView);
        tripFilterRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.tripFilterRelativeLayout);
        priceFilterRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.priceFilterRelativeLayout);
        ratingFilterRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.ratingFilterRelativeLayout);
        tripAdviserTextView = (TextView) rootView.findViewById(R.id.tripAdviserTextView);
        tripAdviserIconImageView = (ImageView) rootView.findViewById(R.id.tripAdviserIconImageView);
        tripAdviserTickImageView = (ImageView) rootView.findViewById(R.id.tripAdviserTickImageView);
        themeFilterRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.themeFilterRelativeLayout);
        themeTextView = (TextView) rootView.findViewById(R.id.themeTextView);
        themeIconImageView = (ImageView) rootView.findViewById(R.id.themeIconImageView);
        themeTickImageView = (ImageView) rootView.findViewById(R.id.themeTickImageView);
        amenityFilterRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.amenityFilterRelativeLayout);

        // setting listeners to widgets.
        themeFilterRelativeLayout.setOnClickListener(this);
        amenityFilterRelativeLayout.setOnClickListener(this);
        hotelChainFilterRelativeLayout.setOnClickListener(this);
        ratingFilterRelativeLayout.setOnClickListener(this);
        tripFilterRelativeLayout.setOnClickListener(this);
        applyRelativeLayout.setOnClickListener(replyClick);
        clearRelativeLayout.setOnClickListener(ClearClick);
        locationFilterRelativeLayout.setOnClickListener(this);
        priceFilterRelativeLayout.setOnClickListener(this);

        Constant.clearFilter=false;

    }

    private void backPressed() {
        rootView.findViewById(R.id.toolbarBackImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRelativeLayout.performClick();
                getActivity().onBackPressed();
            }
        });
    }

    private void filterUpdate() {

        filterUpdate = new Handler() {

            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                final Bundle bundle = msg.getData();

                int filterType = bundle.getInt("FILTERTYPE");
                boolean isCheck = bundle.getBoolean("ISCHECK");


                switch (filterType) {

                    case 0:
                        hotelListingInfoResponse.setSelectedMinimumRate(bundle.getInt("SelectedMinValue"));
                        hotelListingInfoResponse.setSelectedMaximumRate(bundle.getInt("SelectedMaxValue"));
                        hotelListingInfoResponse.setPriceFilterApply(isCheck);
                        if (isCheck) {
                            priceTickImageView.setVisibility(View.VISIBLE);
                            lastFilterApply = PropertyFilterType.MinMaxPrice.getNumVal();
                        } else {
                            priceTickImageView.setVisibility(View.INVISIBLE);
                        }
                        filter();
                        break;

                    case 1:
                        ArrayList<CatDto> locationArrayList = bundle.getParcelableArrayList("FilterLocation");
                        hotelListingInfoResponse.getFilters().setArea(locationArrayList);
                        if (isCheck) {
                            lockTickImageView.setVisibility(View.VISIBLE);
                            lastFilterApply = PropertyFilterType.TopArea.getNumVal();
                        } else {
                            lockTickImageView.setVisibility(View.INVISIBLE);
                        }
                        filter();
                        break;
                    case 2:
                        ArrayList<StarDto> ratingArrayList = bundle.getParcelableArrayList("starRating");
                        hotelListingInfoResponse.getFilters().setStar(ratingArrayList);

                        if (isCheck) {
                            lastFilterApply = PropertyFilterType.StarRating.getNumVal();
                            ratingTickImageView.setVisibility(View.VISIBLE);
                        } else {
                            ratingTickImageView.setVisibility(View.INVISIBLE);
                        }
                        filter();
                        break;
                    case 3:
                        ArrayList<TripDto> tripAdviserArrayList = bundle.getParcelableArrayList("TripAdvisorRating");
                        hotelListingInfoResponse.getFilters().setTrip(tripAdviserArrayList);
                        if (isCheck) {
                            lastFilterApply = PropertyFilterType.TripAdvisorRating.getNumVal();
                            tripAdviserTickImageView.setVisibility(View.VISIBLE);
                        } else {
                            tripAdviserTickImageView.setVisibility(View.INVISIBLE);
                        }
                        filter();
                        break;
                    case 4:

                        ArrayList<CatDto> themeArrayList = bundle.getParcelableArrayList("FilterThem");
                        hotelListingInfoResponse.getFilters().setCat(themeArrayList);

                        if (isCheck) {
                            lastFilterApply = PropertyFilterType.Themes.getNumVal();

                            themeTickImageView.setVisibility(View.VISIBLE);
                        } else {
                            themeTickImageView.setVisibility(View.INVISIBLE);
                        }
                        filter();
                        break;
                    case 5:
                        ArrayList<AmeDto> amenitiesArrayList = bundle.getParcelableArrayList("Filter_Amenities");
                        hotelListingInfoResponse.getFilters().setAme(amenitiesArrayList);

                        if (isCheck) {
                            lastFilterApply = PropertyFilterType.Amenities.getNumVal();
                            amenitiesTickImageView.setVisibility(View.VISIBLE);
                        } else {
                            amenitiesTickImageView.setVisibility(View.INVISIBLE);
                        }
                        filter();
                        break;

                    case 6:
                        ArrayList<CatDto> hotelChainArrayList = bundle.getParcelableArrayList("hotelChainList");
                        hotelListingInfoResponse.getFilters().setChain(hotelChainArrayList);

                        if (isCheck) {
                            lastFilterApply = PropertyFilterType.HotelChain.getNumVal();
                            hotelChainTickImageView.setVisibility(View.VISIBLE);
                        } else {
                            hotelChainTickImageView.setVisibility(View.INVISIBLE);
                        }
                        filter();
                        break;

                }
            }

        };
    }

    private void setHeaderTitle() {
        if(destinationName.contains(",")) {
            String[] locationArray = destinationName.split(",");
            String location = locationArray[0];
            ((TextView) rootView.findViewById(R.id.toolbarTitleTextView)).setText(getActivity().getResources().getString(R.string.Filter));

        }else
            ((TextView) rootView.findViewById(R.id.toolbarTitleTextView)).setText(destinationName);

    }

    private void setHeaderInfo() {

        // setting header info
        int adultCount = 0,childCount = 0;
        int roomCount = HotelListingRequest.getHotelListRequest().getOccupancy().size();

        for (int i = 0; i < roomCount; i++) {
            adultCount = adultCount + HotelListingRequest.getHotelListRequest().getOccupancy().get(i).getNoOfAdults();
            childCount = childCount + HotelListingRequest.getHotelListRequest().getOccupancy().get(i).getChildAges().size();

        }

        //  header info end
        if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)){

            ((TextView) rootView.findViewById(R.id.roomInfoTextView)).setText("\u200F"+new Utilities().occupancyInfo(getActivity(), HotelListingRequest.getHotelListRequest().getCheckInDate(), HotelListingRequest.getHotelListRequest().getCheckOutDate(), adultCount, childCount, HotelListingRequest.getHotelListRequest().getOccupancy().size()));

        }
        else {

            ((TextView) rootView.findViewById(R.id.roomInfoTextView)).setText(  new Utilities().occupancyInfo(getActivity(), HotelListingRequest.getHotelListRequest().getCheckInDate(), HotelListingRequest.getHotelListRequest().getCheckOutDate(), adultCount, childCount, HotelListingRequest.getHotelListRequest().getOccupancy().size()));
        }

    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        Bundle bundle = this.getArguments();

        if (bundle != null) {

            oldHotelListResponse = bundle.getParcelable("HotelListingResponse");
            try {
                hotelListingInfoResponse = (HotelListingInfoResponse) oldHotelListResponse.clone();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            lastFilterApply = bundle.getInt("lastFilterApply");
            resetFilters = bundle.getBoolean("resetfilters");
            oldfilter=lastFilterApply;
        }
    }



    private View.OnClickListener replyClick = new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            Log.e("",""+oldHotelListResponse);

            isFilterApply();

            Intent lastFilter = new Intent(Constant.MY_ACTION);
            lastFilter.putExtra("LastFilter", lastFilterApply);





            if(lastFilterApply==10 && Constant.clearFilter){

                Constant.filterChange=true;
            }

            else if(oldfilter!=lastFilterApply){

                Constant.filterChange=true;

            }
            else if(lastFilterApply==-1 ){

                Constant.filterChange=false;
            }

            else if(Constant.filterFlag){

                Constant.filterChange=true;
            }


            else{

                Constant.filterChange=false;
            }


       //     oldHotelListResponse = hotelListingInfoResponse;




            Bundle bundle = new Bundle();
            bundle.putParcelable(Constant.HOTEL_LISTING_RESPONSE, hotelListingInfoResponse);

            lastFilter.putExtras(bundle);
            LocalBroadcastManager myLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
            myLocalBroadcastManager.sendBroadcast(lastFilter);
            getActivity().getSupportFragmentManager().popBackStack();

            Constant.filterFlag=false;


        }
    };


    private View.OnClickListener ClearClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {



            SharedPreferenceManager.getInstance(getActivity()).saveIntegerPreference(Constant.MIN_SELECTED,0);
            SharedPreferenceManager.getInstance(getActivity()).saveIntegerPreference(Constant.MAX_SELECTED,0);

            // for price
            priceTickImageView.setVisibility(View.INVISIBLE);
            applyTextView.setText(R.string.Done);

            hotelListingInfoResponse.setSelectedMinimumRate((int) hotelListingInfoResponse.getMinPrc());
            hotelListingInfoResponse.setSelectedMaximumRate((int) hotelListingInfoResponse.getMaxPrc());
            hotelListingInfoResponse.setPriceFilterApply(false);

            FragmentPrice refreshFragmentPrice = (FragmentPrice) getFragmentManager().findFragmentByTag("MY_FRAGMENT");
            if (refreshFragmentPrice != null && refreshFragmentPrice.isVisible()) {
                refreshFragmentPrice.refresh();
            }


            for (int i = 0; i < hotelListingInfoResponse.getFilters().getArea().size(); i++) {
                if (hotelListingInfoResponse.getFilters().getArea().get(i).isCheck()) {
                    hotelListingInfoResponse.getFilters().getArea().get(i).setCheck(false);
                }
            }
            for (int i = 0; i < hotelListingInfoResponse.getFilters().getAme().size(); i++) {
                if (hotelListingInfoResponse.getFilters().getAme().get(i).isCheck()) {
                    hotelListingInfoResponse.getFilters().getAme().get(i).setCheck(false);
                }
            }
            for (int i = 0; i < hotelListingInfoResponse.getFilters().getStar().size(); i++) {
                if (hotelListingInfoResponse.getFilters().getStar().get(i).isCheck()) {
                    hotelListingInfoResponse.getFilters().getStar().get(i).setCheck(false);
                }
            }
            for (int i = 0; i < hotelListingInfoResponse.getFilters().getCat().size(); i++) {
                if (hotelListingInfoResponse.getFilters().getCat().get(i).isCheck()) {
                    hotelListingInfoResponse.getFilters().getCat().get(i).setCheck(false);
                }
            }
            for (int i = 0; i < hotelListingInfoResponse.getFilters().getChain().size(); i++) {
                if (hotelListingInfoResponse.getFilters().getChain().get(i).isCheck()) {
                    hotelListingInfoResponse.getFilters().getChain().get(i).setCheck(false);
                }
            }
            for (int i = 0; i < hotelListingInfoResponse.getFilters().getTrip().size(); i++) {
                if (hotelListingInfoResponse.getFilters().getTrip().get(i).isCheck()) {
                    hotelListingInfoResponse.getFilters().getTrip().get(i).setCheck(false);
                }
            }
            if (typeClick == 0) {
                priceFilterRelativeLayout.performClick();
            } else if (typeClick == 1) {
                locationFilterRelativeLayout.performClick();
            } else if (typeClick == 2) {
                ratingFilterRelativeLayout.performClick();
            } else if (typeClick == 3) {
                tripFilterRelativeLayout.performClick();
            } else if (typeClick == 4) {
                themeFilterRelativeLayout.performClick();
            } else if (typeClick == 5) {
                amenityFilterRelativeLayout.performClick();
            } else if (typeClick == 6) {
                hotelChainFilterRelativeLayout.performClick();
            }

            priceTickImageView.setVisibility(View.INVISIBLE);
            lockTickImageView.setVisibility(View.INVISIBLE);
            ratingTickImageView.setVisibility(View.INVISIBLE);
            amenitiesTickImageView.setVisibility(View.INVISIBLE);
            hotelChainTickImageView.setVisibility(View.INVISIBLE);
            themeTickImageView.setVisibility(View.INVISIBLE);
            tripAdviserTickImageView.setVisibility(View.INVISIBLE);

            Constant.clearFilter=true;
            lastFilterApply=10;


        }
    };

    public void filter() {
        if (isFilterApply()) {
            applyTextView.setText(R.string.Apply);
        } else {
            applyTextView.setText(R.string.Done);
        }
    }

    //This method sets background colors for filters.
    private void setBackground(int type) {

        switch (type){

            case 0:

                priceFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_purple));
                priceTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
                priceIconImageView.setImageResource(R.drawable.price_range_w);

                locationFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                locationTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                locationIconImageView.setImageResource(R.drawable.location);

                ratingFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                ratingTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                ratingIconImageView.setImageResource(R.drawable.star_rating);

                tripFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                tripAdviserTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                tripAdviserIconImageView.setImageResource(R.drawable.trip_advisor);

                themeFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                themeTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                themeIconImageView.setImageResource(R.drawable.hotel_theme);

                amenityFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                amenitiesTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                amenityIconImageView.setImageResource(R.drawable.amenities);

                hotelChainFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                hotelChainTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                hotelChainIconImageView.setImageResource(R.drawable.hotel_chain);

                break;

            case 1:

                locationFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_purple));
                locationTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
                locationIconImageView.setImageResource(R.drawable.location_w);

                priceFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                priceTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                priceIconImageView.setImageResource(R.drawable.price_range);

                ratingFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                ratingTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                ratingIconImageView.setImageResource(R.drawable.star_rating);

                tripFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                tripAdviserTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                tripAdviserIconImageView.setImageResource(R.drawable.trip_advisor);

                themeFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                themeTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                themeIconImageView.setImageResource(R.drawable.hotel_theme);

                amenityFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                amenitiesTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                amenityIconImageView.setImageResource(R.drawable.amenities);

                hotelChainFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                hotelChainTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                hotelChainIconImageView.setImageResource(R.drawable.hotel_chain);
                break;

            case 2:

                ratingFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_purple));
                ratingTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
                ratingIconImageView.setImageResource(R.drawable.star_rating_w);


                priceFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                priceTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                priceIconImageView.setImageResource(R.drawable.price_range);

                locationFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                locationTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                locationIconImageView.setImageResource(R.drawable.location);

                tripFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                tripAdviserTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                tripAdviserIconImageView.setImageResource(R.drawable.trip_advisor);

                themeFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                themeTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                themeIconImageView.setImageResource(R.drawable.hotel_theme);

                amenityFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                amenitiesTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                amenityIconImageView.setImageResource(R.drawable.amenities);

                hotelChainFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                hotelChainTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                hotelChainIconImageView.setImageResource(R.drawable.hotel_chain);

                break;

            case 3:

                tripFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_purple));
                tripAdviserTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
                tripAdviserIconImageView.setImageResource(R.drawable.trip_advisor_w);

                priceFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                priceTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                priceIconImageView.setImageResource(R.drawable.price_range);

                locationFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                locationTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                locationIconImageView.setImageResource(R.drawable.location);


                themeFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                themeTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                themeIconImageView.setImageResource(R.drawable.hotel_theme);

                ratingFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                ratingTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                ratingIconImageView.setImageResource(R.drawable.star_rating);

                amenityFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                amenitiesTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                amenityIconImageView.setImageResource(R.drawable.amenities);


                hotelChainFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                hotelChainTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                hotelChainIconImageView.setImageResource(R.drawable.hotel_chain);

                break;

            case 4:

                themeFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_purple));
                themeTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
                themeIconImageView.setImageResource(R.drawable.hotel_theme_w);

                ratingFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                ratingTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                ratingIconImageView.setImageResource(R.drawable.star_rating);


                amenityFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                amenitiesTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                amenityIconImageView.setImageResource(R.drawable.amenities);

                hotelChainFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                hotelChainTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                hotelChainIconImageView.setImageResource(R.drawable.hotel_chain);

                priceFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                priceTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                priceIconImageView.setImageResource(R.drawable.price_range);

                locationFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                locationTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                locationIconImageView.setImageResource(R.drawable.location);

                tripFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                tripAdviserTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                tripAdviserIconImageView.setImageResource(R.drawable.trip_advisor);

                break;

            case 5:

                amenityFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_purple));
                amenitiesTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
                amenityIconImageView.setImageResource(R.drawable.amenities_w);

                hotelChainFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                hotelChainTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                hotelChainIconImageView.setImageResource(R.drawable.hotel_chain);

                priceFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                priceTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                priceIconImageView.setImageResource(R.drawable.price_range);

                locationFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                locationTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                locationIconImageView.setImageResource(R.drawable.location);

                tripFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                tripAdviserTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                tripAdviserIconImageView.setImageResource(R.drawable.trip_advisor);

                ratingFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                ratingTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                ratingIconImageView.setImageResource(R.drawable.star_rating);

                themeFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                themeTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                themeIconImageView.setImageResource(R.drawable.hotel_theme);

                break;

            case 6:

                hotelChainFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_purple));
                hotelChainTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
                hotelChainIconImageView.setImageResource(R.drawable.hotel_chain_w);

                priceFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                priceTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                priceIconImageView.setImageResource(R.drawable.price_range);

                locationFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                locationTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                locationIconImageView.setImageResource(R.drawable.location);

                tripFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                tripAdviserTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                tripAdviserIconImageView.setImageResource(R.drawable.trip_advisor);

                ratingFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                ratingTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                ratingIconImageView.setImageResource(R.drawable.star_rating);

                themeFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                themeTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                themeIconImageView.setImageResource(R.drawable.hotel_theme);

                amenityFilterRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.light_grey));
                amenitiesTextView.setTextColor(ContextCompat.getColor(getActivity(),R.color.dark_gray_text_color));
                amenityIconImageView.setImageResource(R.drawable.amenities);

                break;
        }

    }

    private void setFiltersTickVisibilities() {

        if (status(hotelListingInfoResponse, 0)) {
            priceTickImageView.setVisibility(View.VISIBLE);
        } else {
            priceTickImageView.setVisibility(View.INVISIBLE);
        }

        if (status(hotelListingInfoResponse, 1)) {
            lockTickImageView.setVisibility(View.VISIBLE);
        } else {
            lockTickImageView.setVisibility(View.INVISIBLE);
        }

        if (status(hotelListingInfoResponse, 2)) {
            ratingTickImageView.setVisibility(View.VISIBLE);
        } else {
            ratingTickImageView.setVisibility(View.INVISIBLE);
        }

        if (status(hotelListingInfoResponse, 3)) {
            tripAdviserTickImageView.setVisibility(View.VISIBLE);
        } else {
            tripAdviserTickImageView.setVisibility(View.INVISIBLE);
        }

        if (status(hotelListingInfoResponse, 4)) {
            themeTickImageView.setVisibility(View.VISIBLE);
        } else {
            themeTickImageView.setVisibility(View.INVISIBLE);
        }

        if (status(hotelListingInfoResponse, 5)) {
            amenitiesTickImageView.setVisibility(View.VISIBLE);
        } else {
            amenitiesTickImageView.setVisibility(View.INVISIBLE);
        }

        if (status(hotelListingInfoResponse, 6)) {
            hotelChainTickImageView.setVisibility(View.VISIBLE);
        } else {
            hotelChainTickImageView.setVisibility(View.INVISIBLE);
        }

        if (resetFilters) {
            clearRelativeLayout.performClick();
        }
        filter();
        priceFilterRelativeLayout.performClick();


    }

    private boolean status(HotelListingInfoResponse mHotelListResponse, int type) {

        boolean isCheck = false;

        switch (type){
            case 0:

                if (mHotelListResponse.isPriceFilterApply()) {
                    isCheck = true;
                }

                return isCheck;

            case 1:

                if(mHotelListResponse.getFilters()!=null&&mHotelListResponse.getFilters().getArea()!=null){
                    for (int i = 0; i < mHotelListResponse.getFilters().getArea().size(); i++) {

                        if (mHotelListResponse.getFilters().getArea().get(i).isCheck()) {
                            isCheck = true;
                            break;
                        }
                    }
                }

                return isCheck;

            case 2:

                for (int i = 0; i < mHotelListResponse.getFilters().getStar().size(); i++) {

                    if (mHotelListResponse.getFilters().getStar().get(i).isCheck()) {
                        isCheck = true;
                        break;
                    }
                }
                return isCheck;

            case 3:

                for (int i = 0; i < mHotelListResponse.getFilters().getTrip().size(); i++) {

                    if (mHotelListResponse.getFilters().getTrip().get(i).isCheck()) {
                        isCheck = true;
                        break;
                    }
                }
                return isCheck;

            case 4:

                for (int i = 0; i < mHotelListResponse.getFilters().getCat().size(); i++) {

                    if (mHotelListResponse.getFilters().getCat().get(i).isCheck()) {
                        isCheck = true;
                        break;
                    }
                }
                return isCheck;

            case 5:

                for (int i = 0; i < mHotelListResponse.getFilters().getAme().size(); i++) {

                    if (mHotelListResponse.getFilters().getAme().get(i).isCheck()) {
                        isCheck = true;
                        break;
                    }
                }
                return isCheck;

            case 6:

                for (int i = 0; i < mHotelListResponse.getFilters().getChain().size(); i++) {

                    if (mHotelListResponse.getFilters().getChain().get(i).isCheck()) {
                        isCheck = true;
                        break;
                    }
                }
                return isCheck;
            default:

                return isCheck;
        }

    }

    private boolean isFilterApply() {

        if (priceTickImageView.getVisibility() == View.VISIBLE) {
            return true;
        } else if (lockTickImageView.getVisibility() == View.VISIBLE) {
            return true;
        } else if (amenitiesTickImageView.getVisibility() == View.VISIBLE) {
            return true;
        } else if (hotelChainTickImageView.getVisibility() == View.VISIBLE) {
            return true;
        } else if (ratingTickImageView.getVisibility() == View.VISIBLE) {
            return true;
        } else if (tripAdviserTickImageView.getVisibility() == View.VISIBLE) {
            return true;
        } else if (themeTickImageView.getVisibility() == View.VISIBLE) {
            return true;
        } else {
            lastFilterApply = PropertyFilterType.NoFilter.getNumVal();
            return false;
        }
    }

    @Override
    public void SeekBarValueChanged(int minValue, int maxValue, boolean isCheck, int minSelectedPos, int maxSelectedPos) {
        hotelListingInfoResponse.setSelectedMinimumRate(minValue);
        hotelListingInfoResponse.setSelectedMaximumRate(maxValue);
        hotelListingInfoResponse.setPriceFilterApply(isCheck);
        if (isCheck) {
            SharedPreferenceManager.getInstance(getActivity()).saveIntegerPreference(Constant.MIN_SELECTED,minSelectedPos);
            SharedPreferenceManager.getInstance(getActivity()).saveIntegerPreference(Constant.MAX_SELECTED,maxSelectedPos);
            priceTickImageView.setVisibility(View.VISIBLE);
            lastFilterApply = PropertyFilterType.MinMaxPrice.getNumVal();
        } else {
            priceTickImageView.setVisibility(View.INVISIBLE);
        }
        filter();
    }


    @Override
    public void initUIElements() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.priceFilterRelativeLayout:

                navigateToPriceFilters();
                break;

            case R.id.locationFilterRelativeLayout:

                navigateToLocationFilter();
                break;

            case R.id.ratingFilterRelativeLayout:

                navigateToRatingFilter();
                break;

            case R.id.tripFilterRelativeLayout:

                navigateToTripFilter();
                break;

            case R.id.themeFilterRelativeLayout:

                navigateToThemeFilter();
                break;

            case R.id.amenityFilterRelativeLayout:

                navigateToAmenitiesFilter();
                break;

            case R.id.hotelChainFilterRelativeLayout:

                navigateToHotelChainFilter();
                break;
            default:
                break;
        }

    }

    // call to hotelChainFilterFragment.
    private void navigateToHotelChainFilter() {


        Fragment fragment = new FragmentHotelChain();
        setFragmentTransactionAndPushEvents(fragment,"Filter Screen","Hotel Chain","Hotel Chain filter options");
        setBackground(6);
        typeClick = 6;
    }

    private void setFragmentTransactionAndPushEvents(Fragment fragment, String eventName, String eventKey, String eventValue) {

        String eventCategory = "Filter";

        gtmAnalytics.sendEvent(eventName,eventKey,eventValue);
        Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()),eventCategory,eventKey,eventValue);

        Bundle bundle= new Bundle();
        bundle.putParcelable(Constant.HOTEL_LISTING_RESPONSE, hotelListingInfoResponse);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction= getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place,fragment);
        fragmentTransaction.commit();

    }

    // call to AmenitiesFilterFragment.
    private void navigateToAmenitiesFilter() {

        Fragment fragment = new FragmentAmenities();
        setFragmentTransactionAndPushEvents(fragment,"Filter Screen","Amenities","Amenities filter options");

        setBackground(5);
        typeClick = 5;
    }

    // calls to themeFilter fragment.
    private void navigateToThemeFilter() {


        Fragment fragment = new FragmentTheme();
        setFragmentTransactionAndPushEvents(fragment,"Filter Screen","Theme","Hotel theme filter options");

        setBackground(4);
        typeClick = 4;
    }

    private void navigateToTripFilter() {

        Fragment fragment = new FragmentTripAdvisor();
        setFragmentTransactionAndPushEvents(fragment,"Filter Screen","TA","TA rating filter options");

        setBackground(3);
        typeClick = 3;
    }

    // call to RatingFilter.
    private void navigateToRatingFilter() {

        Fragment fragment = new FragmentRating();
        setFragmentTransactionAndPushEvents(fragment,"Filter Screen","Start rating","Start rating filter options");

        setBackground(2);
        typeClick = 2;
    }

    // call to locationFilter.
    private void navigateToLocationFilter() {

        Fragment fragment = new FragmentLocation();
        setFragmentTransactionAndPushEvents(fragment,"Filter Screen","Location","Location filter options");

        setBackground(1);
        typeClick = 1;
    }

    // call to price filter.
    private void navigateToPriceFilters() {

        FragmentPrice fragment = new FragmentPrice();
        fragment.setFilterFragment(FilterFragment.this);
        setFragmentTransactionAndPushEvents(fragment,"Filter Screen","Price","Price filter options");
        setBackground(0);
        typeClick = 0;
    }
}


