package com.holidayme.hoteldetail_mvp;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;
import com.holidayme.AppInterface.BackPressInterFace;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.HomeActivity;
import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.Utilities;
import com.holidayme.common.AnimateFirstDisplayListener;
import com.holidayme.common.CustomProgressDialog;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.data.Destination;
import com.holidayme.data.HotelAccommodation;
import com.holidayme.data.OccupancyDto;
import com.holidayme.data.UserDTO;
import com.holidayme.fragments.BaseFragment;
import com.holidayme.fragments.HotelListingFragment;
import com.holidayme.gtm.GTMAnalytics;
import com.holidayme.managers.SharedPreferenceManager;
import com.holidayme.request.HotelDetailRequest;
import com.holidayme.request.HotelListingRequest;
import com.holidayme.request.HotelRoomRateRequest;
import com.holidayme.response.GetDestinationForHotelResponse;
import com.holidayme.response.HotelDetailInfoResponse;
import com.holidayme.response.HotelRatesResponse;
import com.holidayme.response.TripAdviserDataResponse;
import com.holidayme.widgets.HotelIndexFragmentDailog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class HotelDetailsTabFragment extends BaseFragment implements IHotelDetailView.IHotelDetailTabView, BackPressInterFace {


    private View rootView;
    private HotelDetailInfoResponse hotelDetailInfoResponse;
    private TripAdviserDataResponse tripAdviserDataResponse;
    private HotelRatesResponse hotelRatesResponse;
    private TextView hotelTitleHeaderTextView,reviewTextView;
    private ImageView tripAdviserRatingHeaderImageView;
    private DisplayImageOptions displayImageOptions;
    private ImageLoadingListener imageLoadingListener;
    private ImageLoader imageLoader;
    private ViewPager hotelDetailTabViewPager;
    private GTMAnalytics gtmAnalytics;
    private Destination destination = null;
    private Dialog spinningDialog;
    private GetDestinationForHotelResponse getDestinationForHotelResponse;
    private AppEventsLogger appEventsLogger;
    private final String EVENT_CATEGORY = "Hotel Details";
    private HotelDetailTabPresenter hotelDetailTabPresenter;
    private boolean isDeepLink;


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        Bundle bundle = this.getArguments();

        displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.icn_trip_adviser)
                .showImageForEmptyUri(R.drawable.icn_trip_adviser).showImageOnFail(R.drawable.icn_trip_adviser).cacheInMemory(true)
                .considerExifParams(true)
                .build();

        imageLoader = ImageLoader.getInstance();

        if (bundle != null) {


            hotelDetailInfoResponse = bundle.getParcelable("HotelDetails");
            tripAdviserDataResponse = bundle.getParcelable("TripAdvisorDeatil");
            hotelRatesResponse = bundle.getParcelable("HotelRoomRate");

        }
        ((HomeActivity) getActivity()).setBackPressListener(HotelDetailsTabFragment.this);
    }


    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spinningDialog = CustomProgressDialog.showProgressDialog(getActivity());
        hotelDetailTabPresenter = new HotelDetailTabPresenter(this);
        HotelListingFragment.isONAttach = false;
        HotelListingFragment.scrollToTop = false;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.hotel_detail_tab_fragment, container, false);
        setHasOptionsMenu(true);

        imageLoadingListener = new AnimateFirstDisplayListener();


        appEventsLogger = AppEventsLogger.newLogger(getActivity());

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        DrawerLayout mDrawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        if (getActivity().getIntent().hasExtra("deeplink")) {
            destination = getActivity().getIntent().getParcelableExtra("deeplink");
            getActivity().getIntent().removeExtra("deeplink");

            isDeepLink = true;
            makeDefaultRequest(0);

            initUIElements();

            getDestinationIDCall();
        } else {
            isDeepLink = false;
            initUIElements();
            populateHotelDetailData();
        }


        return rootView;
    }


    // Initialize UI element and set contain

    @Override
    public void initUIElements() {

        hotelTitleHeaderTextView = (TextView) rootView.findViewById(R.id.hotelTitleHeaderTextView);
        ImageView modifySearchImageView = (ImageView) rootView.findViewById(R.id.filterFloatingActionButton);
        reviewTextView=(TextView)rootView.findViewById(R.id.reviewTextView);

        ImageView mapImageView = (ImageView) rootView.findViewById(R.id.mapImageView);
        tripAdviserRatingHeaderImageView = (ImageView) rootView.findViewById(R.id.tripAdviserRatingHeaderImageView);

        // header info
        int adultCount = 0, childCount = 0;

        for (int i = 0; i < HotelListingRequest.getHotelListRequest().getOccupancy().size(); i++) {
            adultCount = adultCount + HotelListingRequest.getHotelListRequest().getOccupancy().get(i).getNoOfAdults();
            childCount = childCount + HotelListingRequest.getHotelListRequest().getOccupancy().get(i).getChildAges().size();

        }


        if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)){

            ((TextView) rootView.findViewById(R.id.occupancyHeaderTextView)).setText("\u200F"+new Utilities().occupancyInfo(getActivity(), HotelListingRequest.getHotelListRequest().getCheckInDate(), HotelListingRequest.getHotelListRequest().getCheckOutDate(), adultCount, childCount, HotelListingRequest.getHotelListRequest().getOccupancy().size()));


        }
        else {

            ((TextView) rootView.findViewById(R.id.occupancyHeaderTextView)).setText(  new Utilities().occupancyInfo(getActivity(), HotelListingRequest.getHotelListRequest().getCheckInDate(), HotelListingRequest.getHotelListRequest().getCheckOutDate(), adultCount, childCount, HotelListingRequest.getHotelListRequest().getOccupancy().size()));
        }
        (rootView.findViewById(R.id.backPressImageView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).setBackPressListener(null);
                if(HotelIndexFragmentDailog.backCount>0) {

                    for (int i = 0; i < HotelIndexFragmentDailog.backCount+1; i++) {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                    HotelIndexFragmentDailog.backCount = 1;
                }else {
                    getActivity().onBackPressed();
                    Constant.animationFlag=false;
                }


            }
        });

        rootView.findViewById(R.id.callImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Utilities().contactUsDialog(getActivity());
            }
        });


        modifySearchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                Bundle bundle = new Bundle();
                HotelIndexFragmentDailog dialogFragment = new HotelIndexFragmentDailog();
                bundle.putLong("cityId", hotelDetailInfoResponse.getBasicInfo().getCtyId());
                bundle.putLong("hotelids", hotelDetailInfoResponse.getId());
                if(isDeepLink)
                bundle.putString("destinationName", hotelDetailInfoResponse.getBasicInfo().getCty());
                else
                bundle.putString("destinationName", hotelDetailInfoResponse.getBasicInfo().getCty());
                bundle.putString("hotelName",hotelDetailInfoResponse.getTtl());
                bundle.putBoolean("isDeepLink",isDeepLink);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(fm, "Sample Fragment");


            }
        });
        mapImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                gtmAnalytics.sendEvent("HotelDetail Screen - " + hotelDetailInfoResponse.getTtl(), "Map-Details", "show hotel on map");


                Utilities.pushFacebookEvent(appEventsLogger, EVENT_CATEGORY, "Map-Details", "show hotel on map");


                if (NetworkUtilities.isInternet(getActivity()))
                    switchToHotelDetailMap();


                else
                    Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), true, getFragmentManager());


            }

        });

        gtmAnalytics = AppController.getInstance().getGTMAnalytics(getActivity());

    }

    @Override
    public void backPressCalled() {
        if(getActivity()!=null) {
            ((HomeActivity) getActivity()).setBackPressListener(null);
            if(HotelIndexFragmentDailog.backCount>0) {
                for (int i = 0; i < HotelIndexFragmentDailog.backCount+1; i++) {

                    getActivity().getSupportFragmentManager().popBackStack();
                }

            }else{
                getActivity().onBackPressed();
                Constant.animationFlag=false;
            }
            HotelIndexFragmentDailog.backCount = 0;


        }


    }

// set Room, detail, Review and amenity fragment to hotel detail adapter


    private class HotelDetailAdapter extends FragmentPagerAdapter {
        int noOfTab;


        HotelDetailAdapter(FragmentManager fragmentManager, int numOfTabs) {
            super(fragmentManager);

            this.noOfTab = numOfTabs;

        }


        public Fragment getItem(int position) {

            if (hotelDetailInfoResponse.getBasicInfo().getTripAdvisor() != null && hotelDetailInfoResponse.getBasicInfo().getTripAdvisor().getRevCnt() != 0) {
                switch (position) {

                    case 0:

                        SharedPreferenceManager.getInstance(getActivity()).savePreference("pos", "0");

                        gtmAnalytics.sendEvent("HotelDetail Screen - " + hotelDetailInfoResponse.getTtl(), "Show more rooms", "show entire room list");

                        Utilities.pushFacebookEvent(appEventsLogger, EVENT_CATEGORY, "Show more rooms", "show entire room list");

                        return HotelRoomFragment.newInstance(hotelDetailInfoResponse, hotelRatesResponse);


                    case 1:

                        gtmAnalytics.sendEvent("HotelDetail Screen - " + hotelDetailInfoResponse.getTtl(), "Hotel details", "show hotel description");

                        Utilities.pushFacebookEvent(appEventsLogger, EVENT_CATEGORY, "Hotel details", "show hotel description");

                        return HotelDetailsInfoFragment.newInstance(hotelDetailInfoResponse);

                    case 2:


                        gtmAnalytics.sendEvent("HotelDetail Screen - " + hotelDetailInfoResponse.getTtl(), "Hotel Reviews", "show hotel reviews");

                        Utilities.pushFacebookEvent(appEventsLogger, EVENT_CATEGORY, "Hotel Reviews", "show hotel reviews");

                        return HotelDetailsReviewFragment.newInstance(hotelDetailInfoResponse, tripAdviserDataResponse);

                    case 3:

                        gtmAnalytics.sendEvent("HotelDetail Screen - " + hotelDetailInfoResponse.getTtl(), "Amenities", "show list of amenities");

                        Utilities.pushFacebookEvent(appEventsLogger, EVENT_CATEGORY, "Amenities", "show list of amenities");

                        return HotelDetailsAmenitiesFragment.newInstance(hotelDetailInfoResponse);


                    default:
                        return null;
                }
            } else {
                switch (position) {

                    case 0:


                        gtmAnalytics.sendEvent("HotelDetail Screen - " + hotelDetailInfoResponse.getTtl(), "Show more rooms", "show entire room list");

                        Utilities.pushFacebookEvent(appEventsLogger, EVENT_CATEGORY, "Show more rooms", "show entire room list");

                        return HotelRoomFragment.newInstance(hotelDetailInfoResponse, hotelRatesResponse);

                    case 1:

                        gtmAnalytics.sendEvent("HotelDetail Screen - " + hotelDetailInfoResponse.getTtl(), "Hotel details", "show hotel description");

                        Utilities.pushFacebookEvent(appEventsLogger, EVENT_CATEGORY, "Hotel details", "show hotel description");

                        return HotelDetailsInfoFragment.newInstance(hotelDetailInfoResponse);

                    case 2:


                        gtmAnalytics.sendEvent("HotelDetail Screen - " + hotelDetailInfoResponse.getTtl(), "Amenities", "show list of amenities");

                        Utilities.pushFacebookEvent(appEventsLogger, EVENT_CATEGORY, "Amenities", "show list of amenities");

                        return HotelDetailsAmenitiesFragment.newInstance(hotelDetailInfoResponse);


                    default:
                        return null;
                }
            }
        }


        @Override
        public int getCount() {

            return noOfTab;
        }
    }


    // get City Id for Hotel Detail Call start

    private void getDestinationIDCall() {
        if (NetworkUtilities.isInternet(getActivity()))
            hotelDetailTabPresenter.getCityId(Constant.API_URL + Constant.GETDESTINATIONIDMETHOD + "?HotelId=" + destination.getKey() + "&languageCode=" + UserDTO.getUserDTO().getLanguage(), getActivity(), getFragmentManager());
        else {
            dismissDialog();
            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), true, getFragmentManager());
        }
    }

    @Override
    public void setGetDestinationForHotelResponse(GetDestinationForHotelResponse getDestinationForHotelResponse) {

        this.getDestinationForHotelResponse = getDestinationForHotelResponse;
        getHotelRoomRateCall(this.getDestinationForHotelResponse.getCityId());
    }

    //  get Hotel Room Rate Call start


    private void getHotelRoomRateCall(long cityId) {
        if (NetworkUtilities.isInternet(getActivity()))
            hotelDetailTabPresenter.getHotelRoomRate(Constant.API_URL + Constant.HOTELSRATES, makeDefaultRequest(cityId), getActivity(), getFragmentManager());
        else {
            dismissDialog();

            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), true, getFragmentManager());

        }
    }


    @Override
    public void setHotelRateResponse(HotelRatesResponse hotelRateResponse) {

        this.hotelRatesResponse = hotelRateResponse;
        if (this.hotelRatesResponse.getRoomTypes() != null && this.hotelRatesResponse.getRoomTypes().size() != 0)
            hotelDetailCall(getDestinationForHotelResponse.getCityId());
        else {
            dismissDialog();

            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.app_name), getActivity().getString(R.string.No_room_available), true, getFragmentManager());

        }

    }


// Hotel detail call start

    private void hotelDetailCall(Long cityId) {
        HotelDetailRequest hotelDetailRequest = new HotelDetailRequest();
        hotelDetailRequest.setHotelId(Long.parseLong(destination.getKey()));
        hotelDetailRequest.setCityId(cityId);
        hotelDetailRequest.setLanguageCode(UserDTO.getUserDTO().getLanguage());

        if (NetworkUtilities.isInternet(getActivity())) {

            hotelDetailTabPresenter.getHotelDetails(Constant.API_URL + Constant.HOTELDETAIL, new Gson().toJson(hotelDetailRequest), getActivity(), getFragmentManager());
        } else {
            dismissDialog();

            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), true, getFragmentManager());


        }
    }

    @Override
    public void setHotelDetailResponse(HotelDetailInfoResponse hotelDetailResponse) {

        hotelDetailInfoResponse = hotelDetailResponse;
        if (this.hotelDetailInfoResponse.getBasicInfo().getTripAdvisor().getRating() != 0.0 && this.hotelDetailInfoResponse.getBasicInfo().getTripAdvisor().getRating() != 0) {
            tripAdviserDetailCall(this.hotelDetailInfoResponse.getId());
        } else {
            dismissDialog();
            populateHotelDetailData();
        }
    }


// get trip Adviser Detail Call start


    private void tripAdviserDetailCall(Long hotelId) {
        if (NetworkUtilities.isInternet(getActivity())) {

            hotelDetailTabPresenter.getTripAdviserDetails(Constant.API_URL + Constant.TRIPADVISORDATA + "?HotelId=" + hotelId + "&languageCode=" + UserDTO.getUserDTO().getLanguage(), getActivity(), getFragmentManager());
        } else {
            dismissDialog();

            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), true, getFragmentManager());

        }

    }


    @Override

    public void setTripAdvisorDetailResponse(TripAdviserDataResponse tripAdvisorResponse) {
        this.tripAdviserDataResponse = tripAdvisorResponse;
        populateHotelDetailData();

    }


//   To display hotel Detail after data available


    @Override
    public void populateHotelDetailData() {

        dismissDialog();

        if (hotelDetailInfoResponse != null) {
            if (hotelDetailInfoResponse.getTtl() != null) {
                hotelTitleHeaderTextView.setText(hotelDetailInfoResponse.getTtl());
                gtmAnalytics.setScreenName("HotelDetail Screen - " + hotelDetailInfoResponse.getTtl());
                if (destination != null)

                    destination.setDestinationName(hotelDetailInfoResponse.getTtl());
            } else {
                hotelTitleHeaderTextView.setText(R.string.hotel);

            }
        }

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);

        if (hotelDetailInfoResponse.getBasicInfo().getTripAdvisor().getRevCnt() == 0) {
            tabLayout.addTab(tabLayout.newTab().setText(getActivity().getResources().getString(R.string.Rooms)));
            tabLayout.addTab(tabLayout.newTab().setText(getActivity().getResources().getString(R.string.details)));
            tabLayout.addTab(tabLayout.newTab().setText(getActivity().getResources().getString(R.string.Amenities)));
        } else {
            tabLayout.addTab(tabLayout.newTab().setText(getActivity().getResources().getString(R.string.Rooms)));
            tabLayout.addTab(tabLayout.newTab().setText(getActivity().getResources().getString(R.string.details)));
            tabLayout.addTab(tabLayout.newTab().setText(getActivity().getResources().getString(R.string.Reviews)));
            tabLayout.addTab(tabLayout.newTab().setText(getActivity().getResources().getString(R.string.Amenities)));
        }

        hotelDetailTabViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        hotelDetailTabViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        //for view between tab
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.tab_layout, tabLayout, false);
            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
            assert tab != null;
            tabTextView.setText(tab.getText());
            tab.setCustomView(relativeLayout);

        }

        try {

            Utilities.setStarRating((float) hotelDetailInfoResponse.getBasicInfo().getStar(), getActivity(), rootView);
            if (hotelDetailInfoResponse.getBasicInfo().getTripAdvisor().getRating() != 0 && hotelDetailInfoResponse.getBasicInfo().getTripAdvisor().getRating() != 0.0) {
                tripAdviserRatingHeaderImageView.setVisibility(View.VISIBLE);
                reviewTextView.setText(tripAdviserDataResponse.getTripAdvisorDetail().getNumOfReviews()+" "+getResources().getString(R.string.reviews));
                 Utilities.setTripAdviserRating(tripAdviserRatingHeaderImageView,(int)hotelDetailInfoResponse.getBasicInfo().getTripAdvisor().getRating());
              //  imageLoader.displayImage(hotelDetailInfoResponse.getBasicInfo().getTripAdvisor().getRatImgUrl(), tripAdviserRatingHeaderImageView, displayImageOptions, imageLoadingListener);
            } else {
                tripAdviserRatingHeaderImageView.setVisibility(View.INVISIBLE);
                reviewTextView.setVisibility(View.INVISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        HotelDetailAdapter hotelDetailAdapter = new HotelDetailAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        hotelDetailTabViewPager.setAdapter(hotelDetailAdapter);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                hotelDetailTabViewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    // goto map view from hotel detail
    @Override
    public void switchToHotelDetailMap() {

        Bundle hotelDetailMapBundle = new Bundle();
        Fragment hotelDetailMapFragment = new HotelDetailMapFragment();
        hotelDetailMapBundle.putDouble("Latitude", hotelDetailInfoResponse.getBasicInfo().getLat());
        hotelDetailMapBundle.putDouble("Logitude", hotelDetailInfoResponse.getBasicInfo().getLong());
        hotelDetailMapBundle.putString("Title", hotelDetailInfoResponse.getTtl());
        hotelDetailMapBundle.putString("Address", hotelDetailInfoResponse.getBasicInfo().getAdrs());
        hotelDetailMapFragment.setArguments(hotelDetailMapBundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_body, hotelDetailMapFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

// make default hotel listing request for hotel detail call and get all related parameter


    private String makeDefaultRequest(long cityId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        ArrayList<HotelAccommodation> hotelAccommodationArrayList = new ArrayList<>();
        hotelAccommodationArrayList.add(new HotelAccommodation(2, null, 0, 1, 1));
        String checkInDate = null, checkOutDate = null;

        try {
            checkInDate = dateFormat.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
            checkOutDate = dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<OccupancyDto> occupancyDtoArrayList = new ArrayList<>();
        HotelRoomRateRequest hotelRoomRateRequest = new HotelRoomRateRequest();
        hotelRoomRateRequest.setCheckInDate(checkInDate);
        hotelRoomRateRequest.setCheckOutDate(checkOutDate);
        hotelRoomRateRequest.setLanguageCode(UserDTO.getUserDTO().getLanguage());
        hotelRoomRateRequest.setCityId(cityId);
        hotelRoomRateRequest.setHotelId(Long.parseLong(destination.getKey()));
        hotelRoomRateRequest.setCurrencyCode(UserDTO.getUserDTO().getCurrency());

        for (int i = 0; i < hotelAccommodationArrayList.size(); i++) {
            ArrayList<Integer> KidsAge = new ArrayList<>();

            if (hotelAccommodationArrayList.get(i).getKids() > 0) {
                if (hotelAccommodationArrayList.get(i).getKids() == 1) {
                    KidsAge.add(hotelAccommodationArrayList.get(i).getKid1Age());
                } else if (hotelAccommodationArrayList.get(i).getKids() == 2) {
                    KidsAge.add(hotelAccommodationArrayList.get(i).getKid1Age());
                    KidsAge.add(hotelAccommodationArrayList.get(i).getKid2Age());
                }
            }

            occupancyDtoArrayList.add(new OccupancyDto(hotelAccommodationArrayList.get(i).getAdultsCount(), KidsAge));
            hotelRoomRateRequest.setOccupancy(occupancyDtoArrayList);
        }

        HotelListingRequest hotelListingRequest = HotelListingRequest.getHotelListRequest();
        hotelListingRequest.setCheckInDate(checkInDate);
        hotelListingRequest.setCheckOutDate(checkOutDate);
        hotelListingRequest.setLanguageCode(UserDTO.getUserDTO().getLanguage());
        hotelListingRequest.setCityId(cityId);
        hotelListingRequest.setCurrencyCode(UserDTO.getUserDTO().getCurrency());
        hotelListingRequest.setOccupancy(hotelRoomRateRequest.getOccupancy());
        return new Gson().toJson(hotelRoomRateRequest);
    }


    @Override
    public void dismissDialog() {
        if (spinningDialog != null && spinningDialog.isShowing()) {
            spinningDialog.dismiss();
        }

    }

    @Override
    public void showDialog() {

        if (spinningDialog != null && !spinningDialog.isShowing()) {
            spinningDialog.show();
        }
    }


}
