package com.holidayme.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.appevents.AppEventsLogger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.holidayme.Constants.Constant;

import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.DelayAutoCompleteTextView;
import com.holidayme.activities.util.EndlessRecyclerOnScrollListener;
import com.holidayme.activities.util.HolidayMeFont;
import com.holidayme.activities.util.SoftKeyboardStateWatcher;
import com.holidayme.activities.util.Utilities;
import com.holidayme.adapter.HotelListingAdapter;
import com.holidayme.adapter.HotelTextSearchAutoCompletAdapter;
import com.holidayme.common.CustomProgressDialog;
import com.holidayme.common.Log;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.common.OrderByTypes;
import com.holidayme.common.SortByColumn;
import com.holidayme.data.Destination;
import com.holidayme.data.FiltersRequestDto;
import com.holidayme.data.HotelAccommodation;
import com.holidayme.data.HotelDetail;
import com.holidayme.data.HotelsDto;
import com.holidayme.data.OccupancyDto;
import com.holidayme.data.UserDTO;
import com.holidayme.gtm.GTMAnalytics;
import com.holidayme.hoteldetail_mvp.HotelDetailsTabFragment;
import com.holidayme.hotellisting_mvp.HotelListingPresenter;
import com.holidayme.hotellisting_mvp.IHotelListingClick;
import com.holidayme.hotellisting_mvp.IHotelListingView;
import com.holidayme.managers.DatabaseManager;
import com.holidayme.managers.SharedPreferenceManager;
import com.holidayme.request.HotelDetailRequest;
import com.holidayme.request.HotelListingRequest;
import com.holidayme.request.HotelRoomRateRequest;
import com.holidayme.response.HotelDetailInfoResponse;
import com.holidayme.response.HotelListingInfoResponse;
import com.holidayme.response.HotelRatesResponse;
import com.holidayme.response.TripAdviserDataResponse;
import com.holidayme.widgets.CustomDialog;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import static android.support.v7.widget.RecyclerView.OnClickListener;


/**
 * Created by santosh.patar on 19-08-2015.
 */
public class HotelListingFragment extends BaseFragment implements OnClickListener, IHotelListingView, IHotelListingClick {

    private View rootView;
    private Context context;
    private LinearLayout sortPanelLinearLayout, topSearchLinearLayout;
    private  RelativeLayout bottomFilterLinearLayout;
    private RecyclerView hotelListRecyclerView;
    private RelativeLayout popularitySortRelativeLayout, priceSortRelativeLayout, ratingSortRelativeLayout, tripAdvisorSortRelativeLayout, sortingRelativeLayout, filterRelativeLayout,
            mapRelativeLayout;
    private ImageView popularitySortIconImageView, priceSortIconImageView, ratingSortIconImageView, tripAdvisorSortIconImageView, popularityTickImageView, priceTickImageView,
            ratingTickImageView, tripAdviserTickImageView, sortImageView, filterImageView, hotelSearchClearImageView, hotelSearchIconImageView, callImageView;
    private TextView popularitySortTextView, priceSortTextView, ratingSortTextView, tripAdviserSortTextView,  sortingTextView;
    private SoftKeyboardStateWatcher softKeyboardStateWatcher;
    private LinearLayoutManager linearLayoutManager;
    private HotelListingInfoResponse mHotelListResponse, mHotelFilterListResponse;
    private ArrayList<HotelsDto> hotelsArrayList;
    private int originalListPageNumber = -1, hotelRequestCount, lastFilterApply = -1, night, advanceDays, stateValue;
    private HotelListingAdapter hotelListingAdapter;
    private String request, sortBy = "by popularity", destinationName = "", hotelId = "",occupancyChangedTag="", checkInAr, checkOutAr, checkinDate = "", checkoutDate = "", checkInDateAr = "", checkOutDateAr = "", EVENT_CATEGORY = "Listing page";
    private boolean isFromFilter, isShown;
    private static int LoadMore = 1, particularHotelSearch = 2, sorting = 3, Filter = 4;
    private Dialog spinningDialog;
    private DelayAutoCompleteTextView autoCompleteTextHotelSearch;
    public static boolean isONAttach, scrollToTop;
    private GTMAnalytics gtmAnalytics;
    private HashMap<String, String> checkInDatesHashMap;
    private Destination destination = null;
    private AppEventsLogger appEventsLogger;
    private Date dateTo, dateFrom;
    private HotelListingPresenter hotelListingPresenter;
    private HotelRatesResponse mHotelRateResponse;
    private HotelDetailInfoResponse mHotelDetailResponse;
    private TripAdviserDataResponse mTripAdviosrDataResponse;
    private Long hotelID,cityId;
    private ArrayList<Integer> kidsAgeArrayList;
    public static boolean isListUpdate,  isVisible;
    private ImageView filterFloatingActionButton;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.hotel_listing_fragment, container, false);
        if (context == null)
            context = getActivity();


        appEventsLogger = AppEventsLogger.newLogger(context);

        if (spinningDialog == null)
            spinningDialog = CustomProgressDialog.showProgressDialog(context);

        if (hotelListingPresenter == null) {
            hotelListingPresenter = new HotelListingPresenter(this);
        }

        deepLinkingCall();
        setDestinationText();
        initializeWidgets();
        setLocation();
        setHeaderData();
        backPressed();
        setFonts();
        searchParticularHotel();
        maintainSort();
        loadMorePages();
        if(Constant.dateChangeFlag)
        setUpdatedListing();

        gtmAnalytics = AppController.getInstance().getGTMAnalytics(getActivity());
        gtmAnalytics.setScreenName("HotelListing Screen - " + destinationName);

        return rootView;
    }



    private void deepLinkingCall() {

        if (getActivity().getIntent().hasExtra("deeplink")) {
            destination = getActivity().getIntent().getParcelableExtra("deeplink");
            getActivity().getIntent().removeExtra("deeplink");
            hotelListingCall(destination);
        }

    }

    // sets actionbar and destination text.
    private void setDestinationText() {

        if (UserDTO.getUserDTO().getCityName() != null && !(UserDTO.getUserDTO().getCityName().equals(""))) {
            destinationName = UserDTO.getUserDTO().getCityName();
        } else if (UserDTO.getUserDTO().getDestinationName() != null && !(UserDTO.getUserDTO().getDestinationName().equals(""))) {
            destinationName = UserDTO.getUserDTO().getDestinationName();
        } else {
            destinationName = getActivity().getResources().getString(R.string.hotel);
        }

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(destinationName);
        setHasOptionsMenu(true);

        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        LinearLayout languageLinearLayout = (LinearLayout) drawerLayout.findViewById(R.id.languageLinearLayout);
        View view = drawerLayout.findViewById(R.id.topBarView);
        if (languageLinearLayout != null) {
            languageLinearLayout.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }

    }
    @Override
    public void onPause() {
        //getActivity().unregisterReceiver(filterBroadCastReceiver);

        super.onPause();

        dismissDialog();

    }

    private void initializeWidgets() {

        popularitySortIconImageView = (ImageView) rootView.findViewById(R.id.popularityIconImageView);
        priceSortIconImageView = (ImageView) rootView.findViewById(R.id.priceSortIconImageView);
        ratingSortIconImageView = (ImageView) rootView.findViewById(R.id.ratingSortIconImageView);
        tripAdvisorSortIconImageView = (ImageView) rootView.findViewById(R.id.tripAdvisorSortIconImageView);

    //    sortImageView = (ImageView) rootView.findViewById(R.id.sortImageView);
   //     filterImageView = (ImageView) rootView.findViewById(R.id.filterImageView);

        popularitySortTextView = (TextView) rootView.findViewById(R.id.popularitySortTextView);
        priceSortTextView = (TextView) rootView.findViewById(R.id.priceSortTextView);
        ratingSortTextView = (TextView) rootView.findViewById(R.id.ratingSortTextView);
        tripAdviserSortTextView = (TextView) rootView.findViewById(R.id.tripAdviserSortTextView);

        sortingTextView= (TextView) rootView.findViewById(R.id.sortingTextView);

       //filterTextView = (TextView) rootView.findViewById(R.id.filterTextView);
     //   textSortTextView = (TextView) rootView.findViewById(R.id.textSortTextView);
     //   textSortByTextView = (TextView) rootView.findViewById(R.id.textSortByTextView);

        popularityTickImageView = (ImageView) rootView.findViewById(R.id.popularityTickImageView);
        priceTickImageView = (ImageView) rootView.findViewById(R.id.priceTickImageView);
        tripAdviserTickImageView = (ImageView) rootView.findViewById(R.id.tripAdviserTickImageView);
        ratingTickImageView = (ImageView) rootView.findViewById(R.id.ratingTickImageView);
        callImageView = (ImageView) rootView.findViewById(R.id.callImageView);

        mapRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.mapRelativeLayout);


        popularitySortRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.popularitySortRelativeLayout);
        softKeyboardStateWatcher = new SoftKeyboardStateWatcher(rootView);
        ratingSortRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.ratingSortRelativeLayout);
        tripAdvisorSortRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.tripAdvisorSortRelativeLayout);
        sortPanelLinearLayout = (LinearLayout) rootView.findViewById(R.id.sortPanelLinearLayout);
        sortingRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.sortingRelativeLayout);
        autoCompleteTextHotelSearch = (DelayAutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextHotelSearch);
        topSearchLinearLayout = (LinearLayout) rootView.findViewById(R.id.top_layout);
        hotelSearchClearImageView = (ImageView) rootView.findViewById(R.id.hotelSearchClearImageView);
        priceSortRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.priceSortRelativeLayout);
       bottomFilterLinearLayout= (RelativeLayout) rootView.findViewById(R.id.bottomFilterLinearLayout);

        filterFloatingActionButton= (ImageView) rootView.findViewById(R.id.filterFloatingActionButton);


        sortingRelativeLayout.setOnClickListener(this);
        priceSortRelativeLayout.setOnClickListener(this);
        popularitySortRelativeLayout.setOnClickListener(this);
        ratingSortRelativeLayout.setOnClickListener(this);
        tripAdvisorSortRelativeLayout.setOnClickListener(this);
        //sortPanelLinearLayout.setOnClickListener(this);
        mapRelativeLayout.setOnClickListener(this);
       // filterRelativeLayout.setOnClickListener(this);
        hotelSearchClearImageView.setOnClickListener(this);
       callImageView.setOnClickListener(this);

        filterFloatingActionButton.setOnClickListener(this);

        // auto complete
        autoCompleteTextHotelSearch.setThreshold(1);
        autoCompleteTextHotelSearch.setAutoCompleteDelay(10);
        autoCompleteTextHotelSearch.setLoadingIndicator((ProgressBar) rootView.findViewById(R.id.pb_loading_indicator));



        if (autoCompleteTextHotelSearch != null && autoCompleteTextHotelSearch.getText().toString() != null) {
            if (autoCompleteTextHotelSearch.getText().toString().trim().equals("") || autoCompleteTextHotelSearch.getText().toString().trim().length() == 0) {
                hotelSearchClearImageView.setVisibility(View.GONE);
            } else {
                hotelSearchClearImageView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setLocation() {

        if (destinationName.contains(",")) {
            String locationText = destinationName.split(",")[0];
            ((TextView) rootView.findViewById(R.id.toolbarTitleTextView)).setText(locationText);
        } else {
            ((TextView) rootView.findViewById(R.id.toolbarTitleTextView)).setText(destinationName);
        }
    }

    private void searchParticularHotel() {

        // text change  event
        autoCompleteTextHotelSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                sortPanelLinearLayout.setVisibility(View.INVISIBLE);
                callImageView.setVisibility(View.VISIBLE);
                filterFloatingActionButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 0) {
                    hotelSearchClearImageView.setVisibility(View.GONE);
                    hotelId = "";

                    // call previous data with last filter apply
                    if (isONAttach) {

                        // previous data is present on mHotelListResponse
                        hotelsArrayList = mHotelListResponse.getHotels();

                        setHotelListAdapter(mHotelListResponse, hotelsArrayList);

                    } else {


                        isONAttach = true;
                    }


                } else if (s.length() > 0) {
                    hotelSearchClearImageView.setVisibility(View.VISIBLE);
                    isONAttach = true;
                }
            }
        });

        hotelSearchIconImageView = (ImageView) rootView.findViewById(R.id.hotelSearchIconImageView);
        hotelSearchIconImageView.setOnClickListener(hotelTextSearchClick);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        hotelListRecyclerView = (RecyclerView) rootView.findViewById(R.id.hotelListRecyclerView);
        topSearchLinearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    topSearchLinearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    topSearchLinearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                if (!isShown) {
                    hotelListRecyclerView.setPadding(0, topSearchLinearLayout.getHeight(), 0, 0);
                    sortPanelLinearLayout.setPadding(0, topSearchLinearLayout.getHeight(), 0, 0);
                    if (scrollToTop) {
                        hotelListRecyclerView.smoothScrollToPosition(0);
                    }
                    isShown = true;
                }
            }
        });

        bottomFilterLinearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    bottomFilterLinearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    bottomFilterLinearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                if (!isShown) {
                    hotelListRecyclerView.setPadding(0, 0, 0, bottomFilterLinearLayout.getHeight());
                    if (scrollToTop) {
                        hotelListRecyclerView.smoothScrollToPosition(0);
                    }
                    isShown = true;
                }
            }
        });


        hotelListRecyclerView.setLayoutManager(linearLayoutManager);



        if (isFromFilter) {
            isFromFilter = false;
        } else {
            if (mHotelListResponse != null) {
                if (mHotelListResponse.getHotels() != null && mHotelListResponse.getHotels().size() >= 1) {
                    hotelListRecyclerView.setVisibility(View.VISIBLE);

                    setHotelListAdapter(mHotelListResponse, hotelsArrayList);
                    autoCompleteTextHotelSearch.setAdapter(new HotelTextSearchAutoCompletAdapter(getActivity(), mHotelListResponse.getHotels().get(0).getBasicInfo().getCtyId(), getActivity().getSupportFragmentManager())); // 'this' is Activity instance

                } else {

                    hotelListRecyclerView.setVisibility(View.INVISIBLE);
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.no_hotel_available), true, getFragmentManager());
                }
            }
        }

        if (lastFilterApply != -1) {
          //  filterImageView.setImageResource(R.drawable.filter_filled);
            Log.e("","");

        }
//        else
//            filterImageView.setImageResource(R.drawable.filter_symbol);

        autoCompleteTextHotelSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hotelSearch();
                    return true;
                }
                return false;
            }
        });

        // auto complete TextView
        autoCompleteTextHotelSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                HotelDetail hotelDetail = (HotelDetail) adapterView.getItemAtPosition(position);
                gtmAnalytics.sendEvent("HotelListing Screen - " + destinationName, "Search by hotel name - " + hotelDetail.getHotelName(), "Proceed to hotel details");
                Utilities.pushFacebookEvent(appEventsLogger, EVENT_CATEGORY, "Search by hotel name", "enter hotel name");

                autoCompleteTextHotelSearch.setText(hotelDetail.getHotelName());
                hotelId = Long.toString(hotelDetail.getHotelId());
                if (!(hotelDetail.getHotelName().equals("")) && hotelDetail.getHotelName().length() > 0) {
                    autoCompleteTextHotelSearch.setSelection(hotelDetail.getHotelName().trim().length());
                }

                hotelSearch();
            }
        });

    }

    private void loadMorePages() {



        hotelListRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {

                Constant.animationFlag=true;

                if (mHotelListResponse.isLoadMore()) {
                    loadMoreData(currentPage);
                }
            }

            @Override
            public void onHide() {

                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });

    }

    private void setUpdatedListing() {


           isListUpdate=true;

            Destination dest= DatabaseManager.getLatestData(UserDTO.getUserDTO().getLanguage());
          /*  dest.setCategory("City");
            dest.setKey(Long.toString(cityId));
            dest.setDestinationName(destinationName);*/
            this.destination=dest;

            if(dest!=null){

                ArrayList<HotelAccommodation>accommodationArrayList= new ArrayList<>();
                String category=dest.getCategory();


                Type listType = new TypeToken<ArrayList<HotelAccommodation>>(){}.getType();
                accommodationArrayList = new GsonBuilder().create().fromJson(dest.getmRoomInfo(), listType);

                Date checkInDate=null,checkOutDate=null;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
                long cityID= Long.parseLong(dest.getKey());

                try {
                    checkInDate = dateFormat.parse(dest.getCheckInDate());
                    checkOutDate = dateFormat.parse(dest.getCheckOutDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                occupancyChangedTag="occupancyChanged";

                updateCityHotels(accommodationArrayList,checkInDate,checkOutDate,cityID,category);


                Constant.dateChangeFlag=false;


            }

    }


    private void updateCityHotels(ArrayList<HotelAccommodation> accommodationArrayList, Date checkInDate, Date checkOutDate, long cityID, String category) {




        HotelListingRequest hotelListingRequest = HotelListingRequest.getHotelListRequest();
     /*   hotelListingRequest.setCurrencyCode(UserDTO.getUserDTO().getCurrency());
        hotelListingRequest.setLanguageCode(UserDTO.getUserDTO().getLanguage());*/
        hotelListingRequest.setCheckInDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(checkInDate));
        hotelListingRequest.setCheckOutDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(checkOutDate));
        hotelListingRequest.setPageNumber(1);
       /*
        hotelListingRequest.setPageSize(10);


        hotelListingRequest.setCityId(cityID);


        hotelListingRequest.setSortBy(OrderByTypes.Descending.getOrderVal());

        if(category.equalsIgnoreCase("Area")){

            hotelListingRequest.setSortParameter("area");
            hotelListingRequest.setSortByArea(0);

        }else{

            hotelListingRequest.setSortParameter("popularity");
        }*/

        ArrayList<OccupancyDto> occupancyDtoArrayList = new ArrayList<>();
        for (int i = 0; i < accommodationArrayList.size(); i++) {
            kidsAgeArrayList = new ArrayList<>();
            if (accommodationArrayList.get(i).getKids() > 0) {
                if (accommodationArrayList.get(i).getKids() == 1) {
                    kidsAgeArrayList.add(accommodationArrayList.get(i).getKid1Age());
                } else if (accommodationArrayList.get(i).getKids() == 2) {
                    kidsAgeArrayList.add(accommodationArrayList.get(i).getKid1Age());
                    kidsAgeArrayList.add(accommodationArrayList.get(i).getKid2Age());
                }
            }

            occupancyDtoArrayList.add(new OccupancyDto(accommodationArrayList.get(i).getAdultsCount(), kidsAgeArrayList));
            hotelListingRequest.setOccupancy(occupancyDtoArrayList);
        }
       /* FiltersRequestDto filtersRequestDto = new FiltersRequestDto();
        filtersRequestDto.setAmenityIds(null);
        filtersRequestDto.setAreaIds(null);
        filtersRequestDto.setHotelId(null);
        filtersRequestDto.setCategoryIds(null);
        filtersRequestDto.setChainIds(null);
        filtersRequestDto.setMaxPrice(0.00);
        filtersRequestDto.setMinPrice(0.00);
        filtersRequestDto.setTripAdvisorRatings(null);
        filtersRequestDto.setStarRatings(null);
        hotelListingRequest.setFilters(filtersRequestDto);*/

        request = new Gson().toJson(hotelListingRequest);

        if (NetworkUtilities.isInternet(getActivity())) {

            showDialog();

            hotelListingPresenter.getHotelListInfo(Constant.API_URL + Constant.HOTELLISTING, request, context,getFragmentManager());

        } else {

            Utilities.commonErrorMessage(context, context.getString(R.string.Network_not_avilable), context.getString(R.string.please_check_your_internet_connection), false, getFragmentManager());
        }

    }



    private void backPressed() {

        rootView.findViewById(R.id.toolbarBackImageView).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (softKeyboardStateWatcher.isSoftKeyboardOpened()) {
                    NetworkUtilities.hideSoftKeyboard(autoCompleteTextHotelSearch, context);

                } else {

                    getActivity().onBackPressed();
                }

            }
        });
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter filterIntentFilter = new IntentFilter(Constant.MY_ACTION);
        localBroadcastManager.registerReceiver(filterBroadCastReceiver, filterIntentFilter);


        // initially filter is not apply
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            if (bundle.getParcelable("HotelListingResponse") != null) {

                mHotelListResponse = bundle.getParcelable("HotelListingResponse");
            } else {
                getActivity().getSupportFragmentManager().popBackStack();
            }

            if (bundle.getParcelable("HotelListingResponse") != null) {
                mHotelFilterListResponse = bundle.getParcelable("HotelListingResponse");
            }

            destinationName=bundle.getString("destinationName");
            cityId=bundle.getLong("cityId");

            hotelsArrayList = new ArrayList<>();
            if (mHotelListResponse != null) {
                hotelsArrayList = mHotelListResponse.getHotels();
            }
        }

        isONAttach = true;


    }


    private void setHeaderData() {

        int adultCount = 0, childCount = 0;

        for (int i = 0; i < HotelListingRequest.getHotelListRequest().getOccupancy().size(); i++) {

            adultCount = adultCount + HotelListingRequest.getHotelListRequest().getOccupancy().get(i).getNoOfAdults();
            childCount = childCount + HotelListingRequest.getHotelListRequest().getOccupancy().get(i).getChildAges().size();

        }
        Calendar calendarTo = new GregorianCalendar();
        Calendar calendarFrom = new GregorianCalendar();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy", Locale.US);
        try {
            dateTo = sdf.parse(sdf.format(new SimpleDateFormat("yyyy-MM-dd").parse(HotelListingRequest.getHotelListRequest().getCheckInDate())));
            checkinDate = sdf.format(dateTo);
            calendarTo.setTime(dateTo);
            dateFrom = sdf.parse(sdf.format(new SimpleDateFormat("yyyy-MM-dd").parse(HotelListingRequest.getHotelListRequest().getCheckOutDate())));
            checkoutDate = sdf.format(dateFrom);
            calendarFrom.setTime(dateFrom);

            night = NetworkUtilities.daysBetween(calendarFrom.getTime(), calendarTo.getTime());
            advanceDays = NetworkUtilities.daysBetween(calendarTo.getTime(), Calendar.getInstance().getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //  header info end



        if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)){

            ((TextView) rootView.findViewById(R.id.roomInfoTextView)).setText("\u200F"+new Utilities().occupancyInfo(getActivity(), HotelListingRequest.getHotelListRequest().getCheckInDate(), HotelListingRequest.getHotelListRequest().getCheckOutDate(), adultCount, childCount, HotelListingRequest.getHotelListRequest().getOccupancy().size()));

        }
        else {

            ((TextView) rootView.findViewById(R.id.roomInfoTextView)).setText(  new Utilities().occupancyInfo(getActivity(), HotelListingRequest.getHotelListRequest().getCheckInDate(), HotelListingRequest.getHotelListRequest().getCheckOutDate(), adultCount, childCount, HotelListingRequest.getHotelListRequest().getOccupancy().size()));
        }



        // cleverTap
        if (AppController.getInstance().getCleverTapInstance() != null) {

            HashMap<String, Object> cleverTapMap = new HashMap<>();
            cleverTapMap.put("LOB", "Hotels");
            cleverTapMap.put("Destination", destinationName);
            cleverTapMap.put("Advance Purchase", advanceDays);
            cleverTapMap.put("Travel Nights", night);
            cleverTapMap.put("Number of rooms", HotelListingRequest.getHotelListRequest().getOccupancy().size());
            cleverTapMap.put("Number of passengers", adultCount + "|" + childCount);
            cleverTapMap.put("Currency", UserDTO.getUserDTO().getCurrency());
            cleverTapMap.put("From Date", dateFrom);
            cleverTapMap.put("To Date", dateTo);
            if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {

                hotelListingPresenter.pushCleverTapEvent("AR Funnel City Hotel Listing Viewed", cleverTapMap);

            } else {
                hotelListingPresenter.pushCleverTapEvent("Funnel City Hotel Listing Viewed", cleverTapMap);
            }

        }
    }

    private void setFonts() {

        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
            HolidayMeFont.overrideFonts(context, autoCompleteTextHotelSearch, Constant.NotoKufiArabic_Regular);
        } else {
            HolidayMeFont.overrideFonts(context, autoCompleteTextHotelSearch, Constant.HelveticaNeueLight);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isShown = false;
    }

    //sorting
    private void maintainSort() {

        HotelListingRequest hotelListingRequest = HotelListingRequest.getHotelListRequest();
//        if (hotelListingRequest.getSortBy() == OrderByTypes.Descending.getOrderVal()) {
//            sortImageView.setImageResource(R.drawable.dwn_arrow_white);
//        } else {
//            sortImageView.setImageResource(R.drawable.up_arrow);
//        }
        if ("by price".equals(sortBy)) {
        //    sortImageView.setVisibility(View.VISIBLE);
            sortingTextView.setText(getString(R.string.price));

           // priceSortRelativeLayout.setBackgroundResource(R.drawable.igm_bg_selected);
            priceSortIconImageView.setImageResource(R.drawable.price_range_w);
            priceTickImageView.setVisibility(View.VISIBLE);
            priceSortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            priceSortRelativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.light_purple));

        } else if ("by trip advisor".equals(sortBy)) {
     //       sortImageView.setVisibility(View.VISIBLE);
            tripAdviserSortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            tripAdvisorSortIconImageView.setImageResource(R.drawable.trip_advisor_w);
        //    tripAdvisorSortRelativeLayout.setBackgroundResource(R.drawable.igm_bg_selected);
            tripAdvisorSortRelativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.light_purple));
            tripAdviserTickImageView.setVisibility(View.VISIBLE);
           sortingTextView.setText(getString(R.string.By_Trip_Advisor_Rating));

        } else if ("By Rating".equals(sortBy)) {
       //     sortImageView.setVisibility(View.VISIBLE);
            ratingSortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            ratingSortIconImageView.setImageResource(R.drawable.star_rating_w);
            ratingSortRelativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.light_purple));


            ratingTickImageView.setVisibility(View.VISIBLE);
            sortingTextView.setText(getString(R.string.By_Star_Rating));
        } else if ("by popularity".equals(sortBy)) {

       //     sortImageView.setVisibility(View.INVISIBLE);
            popularitySortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            popularitySortIconImageView.setImageResource(R.drawable.popularity_w);
            popularityTickImageView.setVisibility(View.VISIBLE);
            popularitySortRelativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.light_purple));
            sortingTextView.setText(getString(R.string.By_Popularity));


        }
    }

    /**
     * Click Listener Activate on search icon click
     */
    private OnClickListener hotelTextSearchClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            hotelSearch();
        }
    };

    /**
     * Hotel Search
     */


    private void hotelSearch() {

        if (autoCompleteTextHotelSearch != null) {
            NetworkUtilities.hideSoftKeyboard(autoCompleteTextHotelSearch, getActivity());
        }
        HotelListingRequest hotelListingRequest = HotelListingRequest.getHotelListRequest();
        hotelListingRequest.setPageNumber(1);

        /**Added for Resetting the Filter and sort after hotel search**/
        resetFilterAndSorting();

        if (hotelId.equals("")) {

            hotelsArrayList = mHotelListResponse.getHotels();

            setHotelListAdapter(mHotelListResponse, hotelsArrayList);
            hotelListingRequest.getFilters().setHotelName(autoCompleteTextHotelSearch.getText().toString());
            hotelListingRequest.getFilters().setHotelId((long) 0);

            request = new GsonBuilder().serializeNulls().create().toJson(hotelListingRequest);

            if (NetworkUtilities.isInternet(getActivity())) {

                showDialog();
                stateValue = particularHotelSearch;
                hotelListingPresenter.postSearchProperties(Constant.API_URL + Constant.HOTEL_LISTING, request, context);

            } else
                noInternetConnectionDialog();

        } else {

            hotelListingRequest.getFilters().setHotelId(Long.parseLong(hotelId));
            request = new Gson().toJson(hotelListingRequest);
            if (NetworkUtilities.isInternet(getActivity())) {

                showDialog();
                stateValue = particularHotelSearch;
                hotelListingPresenter.postSearchProperties(Constant.API_URL + Constant.HOTEL_LISTING, request, context);

            } else {
                noInternetConnectionDialog();
            }
        }

    }

    private void resetFilterAndSorting() {

        HotelListingRequest hotelListingRequest = HotelListingRequest.getHotelListRequest();
        FiltersRequestDto filtersRequestDto = new FiltersRequestDto();
        filtersRequestDto.setAmenityIds(null);
        filtersRequestDto.setAreaIds(null);
        filtersRequestDto.setHotelId(null);
        filtersRequestDto.setCategoryIds(null);
        filtersRequestDto.setChainIds(null);
        filtersRequestDto.setMaxPrice(0.00);
        filtersRequestDto.setMinPrice(0.00);
        filtersRequestDto.setTripAdvisorRatings(null);
        filtersRequestDto.setStarRatings(null);
        hotelListingRequest.setFilters(filtersRequestDto);

     //   filterTextView.setTextColor(ContextCompat.getColor(context, R.color.white_text_color));
      //  filterImageView.setImageResource(R.drawable.filter_symbol);
        lastFilterApply = -1;
        maintainSort();
    }

    // adding 10 object creating dynamically to arrayList and updating recyclerView when ever we reached last item
    private void loadMoreData(int current_page) {

        HotelListingRequest hotelListingRequest = HotelListingRequest.getHotelListRequest();
        hotelListingRequest.setPageSize(10);
        if (hotelListingRequest.getFilters().getHotelId() == null && hotelListingRequest.getFilters().getHotelName() == null)
            originalListPageNumber = current_page;
        hotelListingRequest.setPageNumber(current_page);

        request = new GsonBuilder().serializeNulls().create().toJson(hotelListingRequest);

        Log.e("loadmore","request"+request);

        if (NetworkUtilities.isInternet(getActivity())) {

            stateValue = LoadMore;
                 showDialog();
            hotelListingPresenter.postSearchProperties(Constant.API_URL + Constant.HOTEL_LISTING, request, context);

        } else {

            Utilities.commonErrorMessage(context, context.getString(R.string.Network_not_avilable), context.getString(R.string.please_check_your_internet_connection), true, getFragmentManager());
        }
    }

    // API call for sorting, searching, filters.
    private void oderBy(String orderColumn, int orderBytype) {
        EndlessRecyclerOnScrollListener.current_page = 1;

        HotelListingRequest hotelListingRequest = HotelListingRequest.getHotelListRequest();
        hotelListingRequest.getFilters().setHotelName(autoCompleteTextHotelSearch.getText().toString());
        hotelListingRequest.setSortBy(orderBytype);
        hotelListingRequest.setSortParameter(orderColumn);
        hotelListingRequest.setPageNumber(1);

        request = new GsonBuilder().serializeNulls().create().toJson(hotelListingRequest);

        if (NetworkUtilities.isInternet(getActivity())) {

            stateValue = sorting;
            showDialog();

            hotelListingPresenter.postSearchProperties(Constant.API_URL + Constant.HOTEL_LISTING, request, context);

        } else {
            noInternetConnectionDialog();
        }


    }

    /**
     * update Filter Selection Data to New Result
     */

    private final BroadcastReceiver filterBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            isFromFilter = true;
            int lastFilterApply = intent.getIntExtra("LastFilter", -1);

            Bundle bundle = intent.getExtras();

            if (bundle != null) {
                mHotelFilterListResponse = bundle.getParcelable("HotelListingResponse");
            }


            Constant.animationFlag=false;
            FilterBy(lastFilterApply);
            searchParticularHotel();
        }
    };


    // last filter apply.
    private void FilterBy(int lastFilterApply) {

        this.lastFilterApply = lastFilterApply;
        HotelListingRequest hotelListingRequest = HotelListingRequest.getHotelListRequest();

        if (lastFilterApply != -1) {

            // top area
            ArrayList<Long> areaIdsArrayList = new ArrayList<>();
            for (int i = 0; i < mHotelFilterListResponse.getFilters().getArea().size(); i++) {
                if (mHotelFilterListResponse.getFilters().getArea().get(i).isCheck()) {
                    areaIdsArrayList.add(mHotelFilterListResponse.getFilters().getArea().get(i).getId());
                }
            }
            hotelListingRequest.getFilters().setAreaIds(areaIdsArrayList);

            // star rating filter apply.
            ArrayList<Double> starRatingsArrayList = new ArrayList<>();

            for (int i = 0; i < mHotelFilterListResponse.getFilters().getStar().size(); i++) {
                if (mHotelFilterListResponse.getFilters().getStar().get(i).isCheck()) {
                    starRatingsArrayList.add((double) ((int) mHotelFilterListResponse.getFilters().getStar().get(i).getStar()));
                }
            }
            hotelListingRequest.getFilters().setStarRatings(starRatingsArrayList);

            // star Amenity filter apply.
            ArrayList<Long> amenityIdsArrayList = new ArrayList<>();

            for (int i = 0; i < mHotelFilterListResponse.getFilters().getAme().size(); i++) {
                if (mHotelFilterListResponse.getFilters().getAme().get(i).isCheck()) {
                    amenityIdsArrayList.add(mHotelFilterListResponse.getFilters().getAme().get(i).getId());
                }
            }
            hotelListingRequest.getFilters().setAmenityIds(amenityIdsArrayList);

            // hotelChains filter apply.

            ArrayList<Long> chainIdArrayList = new ArrayList<>();

            for (int i = 0; i < mHotelFilterListResponse.getFilters().getChain().size(); i++) {
                if (mHotelFilterListResponse.getFilters().getChain().get(i).isCheck()) {
                    chainIdArrayList.add(mHotelFilterListResponse.getFilters().getChain().get(i).getId());
                }
            }


            if(areaIdsArrayList!=null && destination.getCategory().equals("Area") ){

               hotelListingRequest.setSortParameter("area");

            }
            else{

                hotelListingRequest.setSortParameter("popularity");
            }



            hotelListingRequest.getFilters().setChainIds(chainIdArrayList);

            // TripAdvisor filter apply.
            ArrayList<Double> tripAdvisorRatingsArrayList = new ArrayList<>();

            for (int i = 0; i < mHotelFilterListResponse.getFilters().getTrip().size(); i++) {
                if (mHotelFilterListResponse.getFilters().getTrip().get(i).isCheck()) {
                    tripAdvisorRatingsArrayList.add((double) ((int) mHotelFilterListResponse.getFilters().getTrip().get(i).getRat()));
                }
            }
            hotelListingRequest.getFilters().setTripAdvisorRatings(tripAdvisorRatingsArrayList);

            // Theme filter apply.
            ArrayList<Long> categoryIdsArrayList = new ArrayList<>();

            for (int i = 0; i < mHotelFilterListResponse.getFilters().getCat().size(); i++) {
                if (mHotelFilterListResponse.getFilters().getCat().get(i).isCheck()) {
                    categoryIdsArrayList.add(mHotelFilterListResponse.getFilters().getCat().get(i).getId());
                }
            }
            hotelListingRequest.getFilters().setCategoryIds(categoryIdsArrayList);

            // price filter apply.
            if (mHotelFilterListResponse.isPriceFilterApply()) {
                hotelListingRequest.getFilters().setMinPrice(mHotelFilterListResponse.getSelectedMinimumRate());
                hotelListingRequest.getFilters().setMaxPrice(mHotelFilterListResponse.getSelectedMaximumRate());
            }




        }
        else {

            resetFilterAndSorting();
            Constant.clearFilter=false;


        }

        if (lastFilterApply == -1) {
            hotelListingRequest.setPageNumber(1);
        } else {
            hotelListingRequest.setPageNumber(1);
        }
        hotelListingRequest.getFilters().setHotelId((long) 0);
        hotelListingRequest.getFilters().setHotelName("");
        request = new GsonBuilder().serializeNulls().create().toJson(hotelListingRequest);



        if(Constant.filterChange){

            if (NetworkUtilities.isInternet(getActivity())) {

                stateValue = Filter;
                showDialog();

                hotelListingPresenter.postSearchProperties(Constant.API_URL + Constant.HOTEL_LISTING, request, context);


            } else {
                noInternetConnectionDialog();
            }
        }




    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.unregisterReceiver(filterBroadCastReceiver);
    }


    private void hideViews() {

        topSearchLinearLayout.animate().translationY(-topSearchLinearLayout.getHeight()).setInterpolator(new AccelerateInterpolator(2));
     //   bottomFilterLinearLayout.animate().translationY(bottomFilterLinearLayout.getHeight()).setInterpolator(new AccelerateInterpolator(2));

        if (isShown) {
            //   hotelListRecyclerView.setPadding(0, 0, 0, 0);

            hotelListRecyclerView.setPadding(0,0,0,0);

            sortPanelLinearLayout.setPadding(0,0,0,0);
            isShown = false;
        }
        NetworkUtilities.hideSoftKeyboard(autoCompleteTextHotelSearch, getContext());
    }

    private void showViews() {

        topSearchLinearLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).setDuration(1000);

    //    bottomFilterLinearLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).setDuration(1000);
        if (!isShown) {
            hotelListRecyclerView.setPadding(0, topSearchLinearLayout.getHeight(), 0, bottomFilterLinearLayout.getHeight());
            isShown = true;
        }
    }

    private void noInternetConnectionDialog() {

        Utilities.commonErrorMessage(context, context.getString(R.string.Network_not_avilable), context.getString(R.string.please_check_your_internet_connection), true, getFragmentManager());
    }

    @Override
    public void initUIElements() {
        if(UserDTO.getUserDTO().getCountryCode()==null)
            new Utilities().getCurrentLocationDetailByIp();
    }

    @Override
    public void onResume() {
        super.onResume();

        Destination dest= DatabaseManager.getLatestData(UserDTO.getUserDTO().getLanguage());

        if(dest!=null){

            this.destination=dest;
        }


     //   sortImageView.setImageResource(R.drawable.dwn_arrow_white);
    }



    //deepLinking call
    private CustomDialog customDialog = null;

    private void hotelListingCall(Destination destination) {

        SharedPreferenceManager.getInstance(context).saveIntegerPreference(Constant.MIN_SELECTED, 0);
        SharedPreferenceManager.getInstance(context).saveIntegerPreference(Constant.MAX_SELECTED, 0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        Date checkInDate = null, checkOutDate = null;
        ArrayList<HotelAccommodation> hotelAccommodationArrayList = new ArrayList<>();
        HotelAccommodation hotelAccommodation = new HotelAccommodation();
        hotelAccommodation.setAdultsCount(2);
        hotelAccommodation.setKids(0);
        hotelAccommodation.setKid1Age(1);
        hotelAccommodation.setKid2Age(1);
        hotelAccommodationArrayList.add(hotelAccommodation);

        try {
            String startDate = simpleDateFormat.format(calendar.getTime());
            checkInDate = simpleDateFormat.parse(startDate);
            calendar.add(Calendar.DATE, 1);
            String endDate = simpleDateFormat.format(calendar.getTime());
            checkOutDate = simpleDateFormat.parse(endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HotelListingRequest hotelListingRequest = HotelListingRequest.getHotelListRequest();
        hotelListingRequest.setCurrencyCode(UserDTO.getUserDTO().getCurrency());
        hotelListingRequest.setLanguageCode(UserDTO.getUserDTO().getLanguage());
        hotelListingRequest.setCheckInDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(checkInDate));
        hotelListingRequest.setCheckOutDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(checkOutDate));
        hotelListingRequest.setPageNumber(1);
        hotelListingRequest.setPageSize(30);
        hotelListingRequest.setCityId(Long.parseLong(destination.getKey()));
        hotelListingRequest.setSortBy(OrderByTypes.Descending.getOrderVal());
        hotelListingRequest.setSortParameter("popularity");
        ArrayList<OccupancyDto> occupancyDtoArrayList = new ArrayList<>();
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

            OccupancyDto occupancyDto = new OccupancyDto(hotelAccommodationArrayList.get(i).getAdultsCount(), KidsAge);
            occupancyDto.setNoOfAdults(hotelAccommodationArrayList.get(i).getAdultsCount());
            occupancyDto.setChildAges(KidsAge);
            occupancyDtoArrayList.add(occupancyDto);
            hotelListingRequest.setOccupancy(occupancyDtoArrayList);
        }
        FiltersRequestDto filtersRequestDto = new FiltersRequestDto();
        filtersRequestDto.setAmenityIds(null);
        filtersRequestDto.setAreaIds(null);
        filtersRequestDto.setHotelId(null);
        filtersRequestDto.setCategoryIds(null);
        filtersRequestDto.setChainIds(null);
        filtersRequestDto.setMaxPrice(0.00);
        filtersRequestDto.setMinPrice(0.00);
        filtersRequestDto.setTripAdvisorRatings(null);
        filtersRequestDto.setStarRatings(null);
        hotelListingRequest.setFilters(filtersRequestDto);

        request = new Gson().toJson(hotelListingRequest);

        Log.d("Hotel Listing Request", request);

        if (NetworkUtilities.isInternet(getActivity())) {

            showDialog();
            hotelListingPresenter.getHotelListInfo(Constant.API_URL + Constant.HOTELLISTING, request, context, getFragmentManager());

        } else {

            noInternetConnectionDialog();
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.mapRelativeLayout:

                navigateToMap();
                break;



            case R.id.sortingRelativeLayout:

                openSortMenu();

                break;

            case R.id.popularitySortRelativeLayout:

                sortByPopularity();
                break;

            case R.id.priceSortRelativeLayout:

                sortByPrice();
                break;

            case R.id.ratingSortRelativeLayout:

                sortByRating();
                break;

            case R.id.tripAdvisorSortRelativeLayout:

                sortByTripAdvisor();
                break;

            case R.id.sortPanelLinearLayout:

                sortPanelLinearLayout.setVisibility(View.INVISIBLE);
                callImageView.setVisibility(View.VISIBLE);
                filterFloatingActionButton.setVisibility(View.VISIBLE);
                break;

            case R.id.hotelSearchClearImageView:

                clearHotelSearchTextView();
                break;
            case R.id.callImageView:
                new Utilities().contactUsDialog(getActivity());
                break;

            case R.id.filterFloatingActionButton:

                navigateToFilters();

                break;
        }
    }


    // clears hotelSearchTextView
    private void clearHotelSearchTextView() {

        autoCompleteTextHotelSearch.setText("");

        lastFilterApply = -1;
        HotelListingRequest hotelListingRequest = HotelListingRequest.getHotelListRequest();
        hotelListingRequest.getFilters().setHotelName("");
        hotelListingRequest.getFilters().setHotelId((long) 0);
        if (originalListPageNumber != -1) {
            hotelListingRequest.setPageNumber(originalListPageNumber);
        } else {
            hotelListingRequest.setPageNumber(1);
        }
        oderBy(hotelListingRequest.getSortParameter(), hotelListingRequest.getSortBy());
    }

    private void openSortMenu() {


        gtmAnalytics.sendEvent("HotelListing Screen - " + destinationName, "Sort", "open sort menu");
        if (mHotelListResponse.getHotels() != null && mHotelListResponse.getHotels().size() >= 0) {

            if (sortPanelLinearLayout.getVisibility() == View.VISIBLE) {
                sortPanelLinearLayout.setVisibility(View.INVISIBLE);
                callImageView.setVisibility(View.VISIBLE);
                filterFloatingActionButton.setVisibility(View.VISIBLE);
                sortPanelLinearLayout.setPadding(0,0,0,0);

            } else {
                sortPanelLinearLayout.setVisibility(View.VISIBLE);
                sortPanelLinearLayout.setPadding(0,0,0,0);
                callImageView.setVisibility(View.INVISIBLE);
                filterFloatingActionButton.setVisibility(View.INVISIBLE);
            //    sortPanelLinearLayout.setPadding(0,0,bottomFilterLinearLayout.getHeight(),bottomFilterLinearLayout.getHeight());
                sortPanelLinearLayout.setClickable(true);



            }





        }
    }

    private void navigateToFilters() {

        // filed Filter
        gtmAnalytics.sendEvent("HotelListing Screen - " + destinationName, "Filter", "open filter");

        Bundle bundle = new Bundle();

        Utilities.pushFacebookEvent(appEventsLogger, EVENT_CATEGORY, "Filter", "open filter");
        // for show all Filter Every time

        if (mHotelFilterListResponse.getHotels() != null && mHotelFilterListResponse.getHotels().size() > 1) {

            Fragment fragment = new FilterFragment();
            fragment.setTargetFragment(HotelListingFragment.this, 0);
//            sortImageView.setImageResource(R.drawable.dwn_arrow);

            bundle.putParcelable("HotelListingResponse", mHotelFilterListResponse);
            if (lastFilterApply == -1) {
                bundle.putBoolean("resetfilters", true);
            } else {
                bundle.putBoolean("resetfilters", false);
            }
            bundle.putInt("lastFilterApply", lastFilterApply);
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.addToBackStack("Filter");
            fragmentTransaction.commitAllowingStateLoss();

        } else {

            Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.no_filter_available), false, getFragmentManager());
        }

    }

    private void navigateToMap() {

        gtmAnalytics.sendEvent("HotelListing Screen - " + destinationName, "Map", "Show map");

        Bundle bundle = new Bundle();

        Utilities.pushFacebookEvent(appEventsLogger, EVENT_CATEGORY, "Map", "Show map");

        Fragment fragment = new MapViewFragment();
        bundle.putParcelable("GetHotelMapResponse", mHotelListResponse);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    // shows hotelListing by tripAdviser.
    private void sortByTripAdvisor() {

        sortBy = "by trip advisor";
        gtmAnalytics.sendEvent("HotelListing Screen - " + destinationName, "Sort-TA", "Sort by TA rating");

        Utilities.pushFacebookEvent(appEventsLogger, EVENT_CATEGORY, "Sort-TA", "Sort by TA rating");

        int orderType;

        popularitySortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.dark_gray_text_color));
        priceSortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.dark_gray_text_color));
        ratingSortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.dark_gray_text_color));
        tripAdviserSortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));



        popularitySortIconImageView.setImageResource(R.drawable.popularity);
        priceSortIconImageView.setImageResource(R.drawable.price_range);
        ratingSortIconImageView.setImageResource(R.drawable.star_rating);
        tripAdvisorSortIconImageView.setImageResource(R.drawable.trip_advisor_w);

        popularitySortRelativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.staycation_theme));
        priceSortRelativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.staycation_theme));
        ratingSortRelativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.staycation_theme));
        tripAdvisorSortRelativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.blue));



        popularityTickImageView.setVisibility(View.INVISIBLE);
        priceTickImageView.setVisibility(View.INVISIBLE);
        ratingTickImageView.setVisibility(View.INVISIBLE);
        tripAdviserTickImageView.setVisibility(View.VISIBLE);

       sortingTextView.setText(getString(R.string.By_Trip_Advisor_Rating));
     //   sortImageView.setVisibility(View.VISIBLE);

        if (tripAdvisorSortRelativeLayout.isClickable()) {

            HotelListingRequest hotelListingRequest = HotelListingRequest.getHotelListRequest();

            if (hotelListingRequest.getSortBy() == 1) {
                orderType = OrderByTypes.Descending.getOrderVal();
               // sortImageView.setImageResource(R.drawable.dwn_arrow_white);
            } else {
                orderType = OrderByTypes.Descending.getOrderVal();
             //   sortImageView.setImageResource(R.drawable.dwn_arrow_white);
            }
        } else {
            orderType = OrderByTypes.Descending.getOrderVal();
          //  sortImageView.setImageResource(R.drawable.dwn_arrow_white);
        }

        oderBy(SortByColumn.TripAdvisorRating.name(), orderType);

        sortPanelLinearLayout.setVisibility(View.INVISIBLE);
        callImageView.setVisibility(View.VISIBLE);
        filterFloatingActionButton.setVisibility(View.VISIBLE);

    }

    // shows hotelListing byRating.
    private void sortByRating() {

        int orderType;
        sortBy = "By Rating";
        gtmAnalytics.sendEvent("HotelListing Screen - " + destinationName, "Sort-Star rating", "Sort by star rating");

        Utilities.pushFacebookEvent(appEventsLogger, EVENT_CATEGORY, "Sort-Star rating", "Sort by star rating");

        popularitySortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.dark_gray_text_color));
        priceSortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.dark_gray_text_color));
        ratingSortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        tripAdviserSortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.dark_gray_text_color));

        popularitySortIconImageView.setImageResource(R.drawable.popularity);
        priceSortIconImageView.setImageResource(R.drawable.price_range);
        ratingSortIconImageView.setImageResource(R.drawable.star_rating_w);
        tripAdvisorSortIconImageView.setImageResource(R.drawable.trip_advisor);

//        popularitySortRelativeLayout.setBackgroundResource(R.drawable.igm_bg);
//        priceSortRelativeLayout.setBackgroundResource(R.drawable.igm_bg);
//        ratingSortRelativeLayout.setBackgroundResource(R.drawable.igm_bg_selected);
//        tripAdvisorSortRelativeLayout.setBackgroundResource(R.drawable.igm_bg);

        popularitySortRelativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.staycation_theme));
        priceSortRelativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.staycation_theme));
        ratingSortRelativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.blue));
        tripAdvisorSortRelativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.staycation_theme));

        popularityTickImageView.setVisibility(View.INVISIBLE);
        priceTickImageView.setVisibility(View.INVISIBLE);
        ratingTickImageView.setVisibility(View.VISIBLE);
        tripAdviserTickImageView.setVisibility(View.INVISIBLE);

        sortingTextView.setText(getString(R.string.By_Star_Rating));

 //       sortImageView.setVisibility(View.VISIBLE);
        if (ratingSortRelativeLayout.isClickable()) {
            HotelListingRequest hotelListingRequest = HotelListingRequest.getHotelListRequest();
            if (hotelListingRequest.getSortBy() == 1) {
                orderType = OrderByTypes.Descending.getOrderVal();
        //        sortImageView.setImageResource(R.drawable.dwn_arrow_white);
            } else {
                orderType = OrderByTypes.Ascending.getOrderVal();
             //   sortImageView.setImageResource(R.drawable.up_arrow);
            }
        } else {
            orderType = OrderByTypes.Ascending.getOrderVal();
         //   sortImageView.setImageResource(R.drawable.up_arrow);
        }

        oderBy(SortByColumn.StarRating.name(), orderType);
        sortPanelLinearLayout.setVisibility(View.INVISIBLE);
        callImageView.setVisibility(View.VISIBLE);
        filterFloatingActionButton.setVisibility(View.VISIBLE);

    }

    // shows hotelListing by price.
    private void sortByPrice() {

        gtmAnalytics.sendEvent("HotelListing Screen - " + destinationName, "Sort-Price", "Sort by price");

        Utilities.pushFacebookEvent(appEventsLogger, EVENT_CATEGORY, "Sort-Price", "Sort by price");

        sortBy = "by price";
        int orderType;
        popularitySortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.dark_gray_text_color));
        priceSortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        ratingSortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.dark_gray_text_color));
        tripAdviserSortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.dark_gray_text_color));

        popularitySortIconImageView.setImageResource(R.drawable.popularity);
        priceSortIconImageView.setImageResource(R.drawable.price_range_w);
        ratingSortIconImageView.setImageResource(R.drawable.star_rating);
        tripAdvisorSortIconImageView.setImageResource(R.drawable.trip_advisor);

        popularitySortRelativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.staycation_theme));
        priceSortRelativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.blue));
        ratingSortRelativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.staycation_theme));
        tripAdvisorSortRelativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.staycation_theme));

//        popularitySortRelativeLayout.setBackgroundResource(R.drawable.igm_bg);
//        priceSortRelativeLayout.setBackgroundResource(R.drawable.igm_bg_selected);
//        ratingSortRelativeLayout.setBackgroundResource(R.drawable.igm_bg);
//        tripAdvisorSortRelativeLayout.setBackgroundResource(R.drawable.igm_bg);



        popularityTickImageView.setVisibility(View.INVISIBLE);
        priceTickImageView.setVisibility(View.VISIBLE);
        ratingTickImageView.setVisibility(View.INVISIBLE);
        tripAdviserTickImageView.setVisibility(View.INVISIBLE);

//        sortImageView.setVisibility(View.VISIBLE);
       sortingTextView.setText(getString(R.string.By_Price));

        if (priceSortRelativeLayout.isClickable()) {

            HotelListingRequest hotelListingRequest = HotelListingRequest.getHotelListRequest();

            if (hotelListingRequest.getSortBy() == 1) {
                orderType = OrderByTypes.Descending.getOrderVal();
              //  sortImageView.setImageResource(R.drawable.dwn_arrow_white);
            } else {
                orderType = OrderByTypes.Ascending.getOrderVal();
              //  sortImageView.setImageResource(R.drawable.up_arrow);
            }
        } else {
            orderType = OrderByTypes.Ascending.getOrderVal();
           // sortImageView.setImageResource(R.drawable.up_arrow);
        }

        oderBy(SortByColumn.Price.name(), orderType);

        sortPanelLinearLayout.setVisibility(View.INVISIBLE);
        callImageView.setVisibility(View.VISIBLE);
        filterFloatingActionButton.setVisibility(View.VISIBLE);
    }

    // shows hotelListing by Popularity.
    private void sortByPopularity() {

        gtmAnalytics.sendEvent("HotelListing Screen - " + destinationName, "Sort-Popularity", "Sort by popularity");

        Utilities.pushFacebookEvent(appEventsLogger, EVENT_CATEGORY, "Sort-Popularity", "Sort by popularity");

        sortBy = "by popularity";

        int orderType;
        popularitySortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        priceSortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.dark_gray_text_color));
        ratingSortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.dark_gray_text_color));
        tripAdviserSortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.dark_gray_text_color));

        popularitySortIconImageView.setImageResource(R.drawable.popularity_w);
        priceSortIconImageView.setImageResource(R.drawable.price_range);
        ratingSortIconImageView.setImageResource(R.drawable.star_rating);
        tripAdvisorSortIconImageView.setImageResource(R.drawable.trip_advisor);

//        popularitySortRelativeLayout.setBackgroundResource(R.drawable.igm_bg_selected);
//        priceSortRelativeLayout.setBackgroundResource(R.drawable.igm_bg);
//        ratingSortRelativeLayout.setBackgroundResource(R.drawable.igm_bg);
//        tripAdvisorSortRelativeLayout.setBackgroundResource(R.drawable.igm_bg);


        popularitySortRelativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.blue));
        priceSortRelativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.staycation_theme));
        ratingSortRelativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.staycation_theme));
        tripAdvisorSortRelativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.staycation_theme));

        popularityTickImageView.setVisibility(View.VISIBLE);
        priceTickImageView.setVisibility(View.INVISIBLE);
        ratingTickImageView.setVisibility(View.INVISIBLE);
        tripAdviserTickImageView.setVisibility(View.INVISIBLE);

        sortingTextView.setText(getString(R.string.By_Popularity));

//        sortImageView.setVisibility(View.INVISIBLE);
        if (popularitySortRelativeLayout.isClickable()) {

            HotelListingRequest hotelListingRequest = HotelListingRequest.getHotelListRequest();

            if (hotelListingRequest.getSortBy() == 1) {
                orderType = OrderByTypes.Descending.getOrderVal();
            } else {
                orderType = OrderByTypes.Descending.getOrderVal();
            }
        } else {
            orderType = OrderByTypes.Descending.getOrderVal();
        }

        oderBy(SortByColumn.Popularity.name(), orderType);

        sortPanelLinearLayout.setVisibility(View.INVISIBLE);
        callImageView.setVisibility(View.VISIBLE);
        filterFloatingActionButton.setVisibility(View.VISIBLE);
    }


    @Override
    public void setSearchProperTies(HotelListingInfoResponse hotelListingInfoResponse) {

        Constant.animationFlag=true;


        if (stateValue == LoadMore && hotelRequestCount == Constant.HOTELMAXREQUEST) {

            hotelRequestCount = 0;

            // no record found for LoadMore
        } else {

            hotelRequestCount = 0;

            if (stateValue == LoadMore && hotelListingInfoResponse.getHotels() != null && hotelListingInfoResponse.getHotels().size() > 0) {

                for (int i = 0; i < hotelListingInfoResponse.getHotels().size(); i++) {
                    hotelsArrayList.add(hotelListingInfoResponse.getHotels().get(i));

                }

                hotelListingAdapter.notifyDataSetChanged();
                HotelListingRequest hotelListingRequest = HotelListingRequest.getHotelListRequest();
                if (hotelListingRequest.getFilters().getHotelId() == null && hotelListingRequest.getFilters().getHotelName() == null)
                    mHotelListResponse.setHotels(hotelsArrayList);

                mHotelListResponse.setLoadMore(hotelListingInfoResponse.isLoadMore());


            } else if (stateValue == particularHotelSearch && hotelListingInfoResponse.getHotels() != null && hotelListingInfoResponse.getHotels().size() > 0) {

                hotelsArrayList = hotelListingInfoResponse.getHotels(); // no actual data change
                mHotelListResponse = hotelListingInfoResponse;
                setHotelListAdapter(mHotelListResponse, hotelsArrayList);
                // no  actual response change


            } else if (stateValue == particularHotelSearch && hotelListingInfoResponse.getHotels() == null) {
                hotelListRecyclerView.setVisibility(View.VISIBLE);
                hotelListingAdapter.notifyDataSetChanged();

                CustomDialog customDialog = new CustomDialog(getActivity(), getString(R.string.no_filters_hotels_title), getString(R.string.no_filter_hotels_found), getString(R.string.Reset_filter));
                customDialog.setDialogExitListener(new CustomDialog.DialogExitListener() {
                    @Override
                    public void dialogExitWithDone() {

                    }

                    @Override
                    public void dialogExitWithDismissOrCancel() {
                        autoCompleteTextHotelSearch.setText("");
                        hotelSearchClearImageView.setVisibility(View.GONE);
                        lastFilterApply = -1;
                        FilterBy(lastFilterApply);
                       // filterTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                     //   filterImageView.setImageResource(R.drawable.filter_symbol);
                      //  textSortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                      //  textSortByTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                    }
                });
                customDialog.show();

            } else if (stateValue == sorting && hotelListingInfoResponse.getHotels() != null && hotelListingInfoResponse.getHotels().size() > 0) {



                mHotelListResponse = hotelListingInfoResponse;
                hotelsArrayList = hotelListingInfoResponse.getHotels();

                setHotelListAdapter(mHotelListResponse, hotelsArrayList);


            } else if (stateValue == Filter && hotelListingInfoResponse.getHotels() != null && hotelListingInfoResponse.getHotels().size() > 0) {


                dismissDialog();
                autoCompleteTextHotelSearch.setText("");
                hotelSearchClearImageView.setVisibility(View.GONE);


                if (hotelListRecyclerView.getVisibility() == View.INVISIBLE) {
                    hotelListRecyclerView.setVisibility(View.VISIBLE);
                }

                /**
                 *  set data to listView and  AutoCompleteAdapter
                 */
                hotelsArrayList = hotelListingInfoResponse.getHotels();
                mHotelListResponse = hotelListingInfoResponse;

                setHotelListAdapter(mHotelListResponse, hotelsArrayList);


            } else if (stateValue == Filter && hotelListingInfoResponse.getHotels() == null) {
                hotelListRecyclerView.setVisibility(View.VISIBLE);
                hotelListingAdapter.notifyDataSetChanged();

                CustomDialog customDialog = new CustomDialog(getActivity(), getString(R.string.no_filters_hotels_title), getString(R.string.no_filter_hotels_found), getString(R.string.Reset_filter));
                customDialog.setDialogExitListener(new CustomDialog.DialogExitListener() {
                    @Override
                    public void dialogExitWithDone() {

                    }

                    @Override
                    public void dialogExitWithDismissOrCancel() {
                        autoCompleteTextHotelSearch.setText("");
                        hotelSearchClearImageView.setVisibility(View.GONE);
                        lastFilterApply = -1;
                        FilterBy(lastFilterApply);
                       // filterTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                   //     filterImageView.setImageResource(R.drawable.filter_symbol);
                        //textSortTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                        //textSortByTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                    }
                });
                customDialog.show();

            }
        }

        loadMorePages();
    }

    @Override
    public void setSearchPropertiesError() {

        mHotelFilterListResponse = mHotelListResponse;

        dismissDialog();
        hotelRequestCount = 0;

        Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.server_is_not_responding_please_try_after_some_time), false, getFragmentManager());

    }

    @Override
    public void setDeepLinkingSuccessListener(HotelListingInfoResponse hotelListingInfoResponse) {

        if ((hotelListingInfoResponse.getHotels() != null) && hotelListingInfoResponse.getHotels().size() > 0) {

            hotelRequestCount = 0;
            if(!occupancyChangedTag.equals("occupancyChanged")){

                UserDTO.getUserDTO().setDestinationName(destination.getDestinationName());
            }

            if (getActivity() != null && !getActivity().isFinishing()) {
                mHotelListResponse = hotelListingInfoResponse;
                mHotelFilterListResponse = hotelListingInfoResponse;
                hotelsArrayList = new ArrayList<>();
                if (mHotelListResponse != null) {
                    hotelsArrayList = mHotelListResponse.getHotels();
                }
                if (mHotelListResponse.getHotels() != null && mHotelListResponse.getHotels().size() >= 1) {
                    hotelListRecyclerView.setVisibility(View.VISIBLE);

                    setHotelListAdapter(mHotelListResponse, hotelsArrayList);
                    autoCompleteTextHotelSearch.setAdapter(new HotelTextSearchAutoCompletAdapter(getActivity(), mHotelListResponse.getHotels().get(0).getBasicInfo().getCtyId(), getActivity().getSupportFragmentManager())); // 'this' is Activity instance

                } else {

                    hotelListRecyclerView.setVisibility(View.INVISIBLE);
                    if (customDialog == null)

                        Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.no_hotel_available), true, getFragmentManager());

                }
            }
        } else {
            UserDTO.getUserDTO().setDestinationName(destination.getDestinationName());
            if (getActivity() != null && !getActivity().isFinishing()) {
                mHotelListResponse = hotelListingInfoResponse;
                mHotelFilterListResponse = hotelListingInfoResponse;
                hotelsArrayList = new ArrayList<>();
                if (mHotelListResponse != null) {
                    hotelsArrayList = mHotelListResponse.getHotels();
                }
                if (mHotelListResponse.getHotels() != null && mHotelListResponse.getHotels().size() >= 1) {
                    hotelListRecyclerView.setVisibility(View.VISIBLE);


                    setHotelListAdapter(mHotelListResponse, hotelsArrayList);

                    autoCompleteTextHotelSearch.setAdapter(new HotelTextSearchAutoCompletAdapter(getActivity(), mHotelListResponse.getHotels().get(0).getBasicInfo().getCtyId(), getActivity().getSupportFragmentManager())); // 'this' is Activity instance

                } else {

                    hotelListRecyclerView.setVisibility(View.INVISIBLE);
                    if (customDialog == null)

                        Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.no_hotel_available), true, getFragmentManager());
                }
            }
        }

    }

    @Override
    public void deepLinkingErrorListener() {

    }

    private void setHotelListAdapter(HotelListingInfoResponse mHotelListResponse, ArrayList<HotelsDto> hotelsArrayList) {

        this.mHotelListResponse = mHotelListResponse;
        this.hotelsArrayList = hotelsArrayList;

        hotelListingAdapter = new HotelListingAdapter(this.mHotelListResponse, this.hotelsArrayList, getActivity(), gtmAnalytics,bottomFilterLinearLayout.getHeight());


        // set the adapter object to the RecyclerView
        hotelListRecyclerView.setAdapter(hotelListingAdapter);


        hotelListingAdapter.notifyDataSetChanged();
        hotelListRecyclerView.invalidate();


        hotelListingAdapter.setOnItemClickListener(this);
    }


    @Override
    public void setHotelRateResponse(HotelRatesResponse hotelRateResponse) {


        mHotelRateResponse = hotelRateResponse;
        if (hotelRateResponse.getRoomTypes() != null && hotelRateResponse.getRoomTypes().size() != 0) {

            hotelDetailCall(hotelID);

        } else {
            dismissDialog();
            Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.No_room_available), false, null);
        }


    }

    @Override
    public void setHotelDetailResponse(HotelDetailInfoResponse hotelDetailResponse) {


        mHotelDetailResponse = hotelDetailResponse;

        if (hotelDetailResponse.getBasicInfo().getTripAdvisor().getRating() != 0.0 && hotelDetailResponse.getBasicInfo().getTripAdvisor().getRating() != 0) {
            tripAdviserDetailCall(hotelDetailResponse.getId());
        } else {
            newFragmentCall();
        }


    }

    @Override
    public void setTripAdvisorDetailResponse(TripAdviserDataResponse tripAdvisorResponse) {

        if (tripAdvisorResponse.getError() == null) {
            mTripAdviosrDataResponse = tripAdvisorResponse;
            newFragmentCall();

        } else {
            Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.common_error_msg), false, null);
        }
    }

    private void newFragmentCall() {

        dismissDialog();

        Fragment fragment = new HotelDetailsTabFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("HotelDetails", mHotelDetailResponse);
        if (mTripAdviosrDataResponse != null)
            bundle.putParcelable("TripAdvisorDeatil", mTripAdviosrDataResponse);
        bundle.putParcelable("HotelRoomRate", mHotelRateResponse);
        fragment.setArguments(bundle);
        if(getActivity()!=null) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.addToBackStack("MapViewFragment");
            fragmentTransaction.commitAllowingStateLoss();
        }


    }

    private void tripAdviserDetailCall(Long id) {

        if (NetworkUtilities.isInternet(context)) {
            hotelListingPresenter.getTripAdvisorDetails(Constant.API_URL + Constant.TRIPADVISORDATA + "?HotelId=" + id + "&languageCode=" + UserDTO.getUserDTO().getLanguage(), context);

        } else {
            Utilities.commonErrorMessage(context, context.getString(R.string.Network_not_avilable), context.getString(R.string.please_check_your_internet_connection), false, null);
        }


    }

    private void hotelDetailCall(Long hotelID) {

        HotelDetailRequest hotelDetailRequest = new HotelDetailRequest();
        hotelDetailRequest.setHotelId(hotelID);
        hotelDetailRequest.setCityId(HotelListingRequest.getHotelListRequest().getCityId());
        hotelDetailRequest.setLanguageCode(UserDTO.getUserDTO().getLanguage());
        String request = new Gson().toJson(hotelDetailRequest);

        if (NetworkUtilities.isInternet(context)) {

            showDialog();
            hotelListingPresenter.getHotelDetails(Constant.API_URL + Constant.HOTELDETAIL, request, context);

        } else {

            Utilities.commonErrorMessage(context, context.getString(R.string.Network_not_avilable), context.getString(R.string.please_check_your_internet_connection), false, null);
        }

    }


    @Override
    public void dismissDialog() {

        if ((spinningDialog != null) && spinningDialog.isShowing()) {
            spinningDialog.dismiss();
        }
    }

    @Override
    public void showDialog() {
        if (spinningDialog != null && !spinningDialog.isShowing()) {

            spinningDialog.show();
        }
    }


    private void roomRateCall(long id) {


        hotelID = id;

        HotelRoomRateRequest hotelDetailRequest = new HotelRoomRateRequest();
        hotelDetailRequest.setCheckInDate(HotelListingRequest.getHotelListRequest().getCheckInDate());
        hotelDetailRequest.setCheckOutDate(HotelListingRequest.getHotelListRequest().getCheckOutDate());
        hotelDetailRequest.setLanguageCode(UserDTO.getUserDTO().getLanguage());
        hotelDetailRequest.setCityId(HotelListingRequest.getHotelListRequest().getCityId());
        hotelDetailRequest.setHotelId(id);
        hotelDetailRequest.setCurrencyCode(UserDTO.getUserDTO().getCurrency());
        hotelDetailRequest.setOccupancy(HotelListingRequest.getHotelListRequest().getOccupancy());

        String request = new GsonBuilder().create().toJson(hotelDetailRequest);
        if (context == null) {
            context = AppController.getInstance().getApplicationContext();
        }

        if (NetworkUtilities.isInternet(context)) {

            showDialog();

            hotelListingPresenter.getHotelRoomRate(Constant.API_URL + Constant.HOTELSRATES, request, context);
        } else {
            Utilities.commonErrorMessage(context, context.getString(R.string.Network_not_avilable), context.getString(R.string.please_check_your_internet_connection), false, null);

        }
    }


    @Override
    public void onHotelLisItemClick(long hotelId, HashMap<String, Object> cleverTapHashMap) {

        this.hotelID = hotelId;

        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {

            hotelListingPresenter.pushCleverTapEvent("AR Funnel Hotels Detail", cleverTapHashMap);
        } else {

            hotelListingPresenter.pushCleverTapEvent("Funnel City Hotel Detail Viewed", cleverTapHashMap);

        }
        roomRateCall(hotelID);


    }


}
