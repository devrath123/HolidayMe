package com.holidayme.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.holidayme.AppInterface.BackPressInterFace;
import com.holidayme.AppInterface.DateSelectListener;
import com.holidayme.AppInterface.OnItemClick;
import com.holidayme.Constants.Constant;
import com.holidayme.NavigationDrawerMVP.NavigationDrawerFragment;
import com.holidayme.activities.HomeActivity;
import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.EndlessRecyclerOnScrollListener;
import com.holidayme.activities.util.RegistrationIntentService;
import com.holidayme.activities.util.Utilities;
import com.holidayme.adapter.AddRoomAdapter;
import com.holidayme.common.CheckInDialogView;
import com.holidayme.common.CheckOutDialogView;
import com.holidayme.common.CustomCalendarDialog;
import com.holidayme.common.CustomProgressDialog;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.common.OrderByTypes;
import com.holidayme.common.Validation;
import com.holidayme.data.DateData;
import com.holidayme.data.Destination;
import com.holidayme.data.FiltersRequestDto;
import com.holidayme.data.HotelAccommodation;
import com.holidayme.data.OccupancyDto;
import com.holidayme.data.UserDTO;
import com.holidayme.gtm.GTMAnalytics;
import com.holidayme.hoteldetail_mvp.HotelDetailsTabFragment;
import com.holidayme.hotelsearch.HotelSearchPresenter;
import com.holidayme.hotelsearch.IUserLandingView;
import com.holidayme.managers.DatabaseManager;
import com.holidayme.managers.SharedPreferenceManager;
import com.holidayme.request.HotelDetailRequest;
import com.holidayme.request.HotelListingRequest;
import com.holidayme.request.HotelRoomRateRequest;
import com.holidayme.response.GetDestinationForHotelResponse;
import com.holidayme.response.HotelDetailInfoResponse;
import com.holidayme.response.HotelListingInfoResponse;
import com.holidayme.response.HotelRatesResponse;
import com.holidayme.response.TripAdviserDataResponse;
import com.holidayme.widgets.AppRater;
import com.holidayme.widgets.CustomeDailogWithTwoButtons;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by santosh.patar on 22-05-2015.
 */
@SuppressLint("SetTextI18n")
public class HotelIndexFragment extends BaseFragment implements OnItemClick, DateSelectListener, BackPressInterFace, View.OnClickListener, IUserLandingView, IUserLandingView.IHotelSearchView {

    //==============================================================
    private Context context;
    private View rootView;
    private LinearLayout languageLinearLayout;
    private TextView checkBuildTextView, searchTextView, locationSearchTextView, checkInDayTextView, checkInMonthTextView, checkInDateTextView, checkOutDayTextView, checkOutMonthTextView,
            checkOutDateTextView, nightCountTextView, roomCountTextView, adultCountTextView,gatewaysTextView;
    private RelativeLayout checkInDateRelativeLayout, checkOutDateRelativeLayout, roomCountRelativeLayout, actionBarRelativeLayout, fullScreenBannerRelativeLayout;
    private RecyclerView addRoomRecyclerView;
    private ImageView bannerImageView, fullScreenBannerImageView, crossBannerImageView;
    private LinearLayoutManager linearLayoutManager;
    private Date checkInDate, checkOutDate;
    private CustomCalendarDialog customCalendarCheckInDialog, customCalendarCheckOutDialog;
    private SimpleDateFormat simpleDateFormat;
    private Calendar calendar;
    private UserDTO userDTO;
    private int night, count;
    private ArrayList<HotelAccommodation> hotelAccommodationsArrayList;
    private Destination destination = null;
    private Dialog spinningDialog;
    private HotelDetailInfoResponse hotelDetailInfoResponse;
    private HotelRatesResponse hotelRatesResponse;
    private TripAdviserDataResponse tripAdvisorDataResponse;
    private GetDestinationForHotelResponse mGetDestinationForHotelResponse;
    private String checkInDateStr = "", checkOutDateStr = "", fullscreen_banner_url, normal_banner_url, engCheckInDate = "", engCheckOutDate = "", EVENT_CATEGORY = "Hotel Index", language, request;
    private ArrayList<Double> locationArrayList;
    private GTMAnalytics gtmAnalytics;
    private boolean bannerFlag, fragmentAnimation;
    private ArrayList<Integer> kidsAgeArrayList;
    private ArrayList<DateData> selectedDatesArrayList;
    private HotelSearchPresenter hotelSearchPresenter;
    private IntentFilter refreshFragmentFilter;
    private LocalBroadcastManager refreshBroadcastManager;
    private FragmentTransaction fragmentTransaction;
    private static boolean isUpdate,isSoldOut;
    private long cityId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();


        userDTO = UserDTO.getUserDTO();
        refreshBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        refreshFragmentFilter = new IntentFilter(Constant.REFRESH_FRAGMENT);
        hotelSearchPresenter = new HotelSearchPresenter(this);


        spinningDialog = CustomProgressDialog.showProgressDialog(context);
       // ((HomeActivity)getActivity()).bottomNavigationVisibility(View.VISIBLE);


        setBannerImages();
        deepLinkingFlow();

    }

    private void setBannerImages() {

        language = UserDTO.getUserDTO().getLanguage();
        if (UserDTO.getUserDTO() != null && language != null && language.equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
            fullscreen_banner_url = "https://hmeimages.blob.core.windows.net/images/app/Ar-300.png";
            normal_banner_url = "https://hme-images.azureedge.net/images/app/Andoird%20banner.png";
        } else {
            fullscreen_banner_url = "https://hmeimages.blob.core.windows.net/images/app/en-300.png";
            normal_banner_url = "https://hme-images.azureedge.net/images/app/Banner-320.png";
        }
    }

    //check whether flow is coming from deep linking
    private void deepLinkingFlow() {

        if (getActivity().getIntent().hasExtra("deeplink")) {
            destination = getActivity().getIntent().getParcelableExtra("deeplink");
            hotelSearchPresenter.checkDeepLinking(destination, getActivity().getSupportFragmentManager().beginTransaction());
        }

    }

    private DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .considerExifParams(true)
            .build();

    @Override
    public void onResume() {
        super.onResume();

        refreshBroadcastManager.registerReceiver(refreshFragmentReceiver, refreshFragmentFilter);
       ((HomeActivity)getActivity()).bottomNavigationVisibility(View.VISIBLE);
        com.nostra13.universalimageloader.core.ImageLoader defaultImageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        defaultImageLoader.setDefaultLoadingListener(new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                bannerImageView.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                bannerImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                bannerImageView.setVisibility(View.GONE);
            }
        });

        defaultImageLoader.displayImage(normal_banner_url, bannerImageView, displayImageOptions);

        if (!bannerFlag) {
            com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
            imageLoader.setDefaultLoadingListener(new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    fullScreenBannerRelativeLayout.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    fullScreenBannerRelativeLayout.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    fullScreenBannerRelativeLayout.setVisibility(View.GONE);
                }
            });
            imageLoader.displayImage(fullscreen_banner_url, fullScreenBannerImageView, displayImageOptions);
        }
    }

    // broadcast receiver refreshes fragment.
    private final BroadcastReceiver refreshFragmentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String hotelTitle = getString(R.string.hotel);
            String getawaysTitle=getString(R.string.getaways_title);

            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
            ((TextView) (HomeActivity.toolbar.findViewById(R.id.toolbar_title))).setText(hotelTitle);

            ((TextView) (HomeActivity.gatewaysTextView.findViewById(R.id.gatewaysTextView))).setText(getawaysTitle);
            ((TextView) (HomeActivity.hotelsTextView.findViewById(R.id.hotelsTextView))).setText(hotelTitle);

            try {
                clearAutoComplete();
                language = UserDTO.getUserDTO().getLanguage();
                final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.detach(HotelIndexFragment.this).attach(HotelIndexFragment.this).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void clearAutoComplete() {

        locationSearchTextView.setText("");
        destination = null;
        UserDTO.getUserDTO().setHotel(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((HomeActivity)getActivity()).bottomNavigationVisibility(View.GONE);
        refreshBroadcastManager.unregisterReceiver(refreshFragmentReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.index_page, container, false);
        ((HomeActivity)getActivity()).bottomNavigationVisibility(View.VISIBLE);
        setHasOptionsMenu(true);
        AppRater.app_launched(context, true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.notifySelecterChange();

        hotelSearchPresenter = new HotelSearchPresenter(this);

        initUI();
        setRoomInfoData();
        showActionBar();
        setDrawer();

        if (HotelListingFragment.isListUpdate) {

            setNewOccupancyData();
            HotelListingFragment.isListUpdate = false;

        }


        gtmAnalytics = AppController.getInstance().getGTMAnalytics(getActivity());
        gtmAnalytics.setScreenName("Hotel Index Screen");

        return rootView;
    }

    private void initUI() {

        searchTextView = (TextView) rootView.findViewById(R.id.searchTextView);
        locationSearchTextView = (TextView) rootView.findViewById(R.id.locationSearchTextView);
        checkInDateTextView = (TextView) rootView.findViewById(R.id.checkInDateTextView);
        checkInMonthTextView = (TextView) rootView.findViewById(R.id.checkInMonthTextView);
        checkInDayTextView = (TextView) rootView.findViewById(R.id.checkInDayTextView);
        checkOutDateTextView = (TextView) rootView.findViewById(R.id.checkOutDateTextView);
        checkOutMonthTextView = (TextView) rootView.findViewById(R.id.checkOutMonthTextView);
        checkOutDayTextView = (TextView) rootView.findViewById(R.id.checkOutDayTextView);
        roomCountTextView = (TextView) rootView.findViewById(R.id.roomCountTextView);
        nightCountTextView = (TextView) rootView.findViewById(R.id.nightCountTextView);
        adultCountTextView = (TextView) rootView.findViewById(R.id.adultCountTextView);
        checkBuildTextView = (TextView) rootView.findViewById(R.id.checkBuildTextView);
        addRoomRecyclerView = (RecyclerView) rootView.findViewById(R.id.addRoomRecyclerView);
        bannerImageView = (ImageView) rootView.findViewById(R.id.bannerImageView);
        crossBannerImageView = (ImageView) rootView.findViewById(R.id.crossBannerImageView);
        fullScreenBannerImageView = (ImageView) rootView.findViewById(R.id.fullScreenBannerImageView);
        fullScreenBannerRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.fullScreenBannerRelativeLayout);
        checkInDateRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.checkInDateRelativeLayout);
        checkOutDateRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.checkOutDateRelativeLayout);
        roomCountRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.roomCountRelativeLayout);
        actionBarRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.actionBarRelativeLayout);


        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        addRoomRecyclerView.setHasFixedSize(true);
        addRoomRecyclerView.setLayoutManager(linearLayoutManager);
        checkInDateRelativeLayout.setBackground(null);
        checkOutDateRelativeLayout.setBackground(null);

        locationSearchTextView.setOnClickListener(this);
        searchTextView.setOnClickListener(this);
        bannerImageView.setOnClickListener(this);
        checkInDateRelativeLayout.setOnClickListener(this);
        checkOutDateRelativeLayout.setOnClickListener(this);
        roomCountRelativeLayout.setOnClickListener(this);
        checkInDateTextView.setOnClickListener(this);
        crossBannerImageView.setOnClickListener(this);
        fullScreenBannerRelativeLayout.setOnClickListener(this);
        checkBuildTextView.setOnClickListener(this);

        if (NetworkUtilities.isInternet(getActivity()))
            new VersionChecker().execute("https://play.google.com/store/apps/details?id=com.holidayme.activities&hl=en");


    }


    private void setRoomInfoData() {

        if (destination != null) {
            locationSearchTextView.setText(destination.getDestinationName());
        } else {
            destination = DatabaseManager.getLatestData(UserDTO.getUserDTO().getLanguage());
            if (destination != null) {
                locationSearchTextView.setText(destination.getDestinationName());
            }
        }

        if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)){

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 150, 0, 0);
            actionBarRelativeLayout.setLayoutParams(params);

        }

        simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        calendar = Calendar.getInstance(Locale.getDefault());

        if (destination != null) {
            try {
                if (destination.getCheckInDate() != null && destination.getCheckOutDate() != null) {
                    if (!Utilities.isPassedDate(destination.getCheckInDate())) {
                        if (checkInDateStr.equalsIgnoreCase("") && checkOutDateStr.equalsIgnoreCase("")) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy-EEE", Locale.US);
                            checkInDate = simpleDateFormat.parse(destination.getCheckInDate());
                            checkOutDate = simpleDateFormat.parse(destination.getCheckOutDate());
                            checkInDateStr = dateFormat.format(checkInDate);
                            checkOutDateStr = dateFormat.format(checkOutDate);
                            night = NetworkUtilities.daysBetween(checkInDate, checkOutDate);
                        }
                    }
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }

        if (checkInDateStr.equalsIgnoreCase("") && checkOutDateStr.equalsIgnoreCase("")) {
            checkInDateStr = Validation.getCurrentDate();
            checkOutDateStr = Validation.getNextCurrentDate();
            try {
                String dateStart = simpleDateFormat.format(calendar.getTime());
                checkInDate = simpleDateFormat.parse(dateStart);
                calendar.add(Calendar.DATE, 1);
                String endDate = simpleDateFormat.format(calendar.getTime());
                checkOutDate = simpleDateFormat.parse(endDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        HashMap<String, String> checkInDateHashMap = Utilities.splitDate(checkInDateStr);
        checkInDateTextView.setText(checkInDateHashMap.get(Constant.Date.DATE.name()));
        HashMap<String, String> checkOutDateHashMap = Utilities.splitDate(checkOutDateStr);
        checkOutDateTextView.setText(checkOutDateHashMap.get(Constant.Date.DATE.name()));
        setMonth(checkInDateHashMap.get(Constant.Date.YEAR.name()), checkOutDateHashMap.get(Constant.Date.YEAR.name()));

        if (AppController.getInstance().getCleverTapInstance() != null) {
            HashMap<String, Object> cleverTapHashMap = new HashMap<>();
            cleverTapHashMap.put("LOB", "Hotels");
            cleverTapHashMap.put("Currency", UserDTO.getUserDTO().getCurrency());
            AppController.getInstance().getCleverTapInstance().data.pushGcmRegistrationId(RegistrationIntentService.token, true);
            if (language.equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
                hotelSearchPresenter.pushCleverTapEvent("Hotels Index Page", cleverTapHashMap);
            } else {
                hotelSearchPresenter.pushCleverTapEvent("Hotels Index Page", cleverTapHashMap);
            }
        }

        selectedDatesArrayList = getDaysBetweenDates(checkInDate, checkOutDate);
        setRoomCounts();
        setNightCounts();
    }

    private void setRoomCounts() {

        if (hotelAccommodationsArrayList == null) {
            hotelAccommodationsArrayList = new ArrayList<>();

            hotelAccommodationsArrayList.add(new HotelAccommodation(2, null, 0, 1, 1));
            if (UserDTO.getUserDTO().getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE))
                roomCountTextView.setText(1 + " " + context.getResources().getString(R.string.room));
            else {
                roomCountTextView.setText(context.getResources().getString(R.string.one_room));
            }

            adultCountTextView.setText(2 + " " + context.getResources().getString(R.string.Adults));
        } else {
            int adultCount = 0, kidCount = 0;
            for (int i = 0; i < hotelAccommodationsArrayList.size(); i++) {
                adultCount = adultCount + hotelAccommodationsArrayList.get(i).getAdultsCount();
                kidCount = kidCount + hotelAccommodationsArrayList.get(i).getKids();
            }
            setKidsAndAdultsText(adultCount, kidCount);
        }

    }

    private void setNightCounts() {

        night = NetworkUtilities.daysBetween(checkInDate, checkOutDate);
        if (userDTO.getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE)) {
            if (night == 1) {
                nightCountTextView.setText(night + " " + getActivity().getResources().getString(R.string.night));
            } else {
                nightCountTextView.setText(night + " " + getActivity().getResources().getString(R.string.nights));
            }
        } else if (night == 1) {
            nightCountTextView.setText(getActivity().getResources().getString(R.string.one_night));
        } else if (night == 2) {
            nightCountTextView.setText(getActivity().getResources().getString(R.string.two_night));
        } else if (night > 2 && night < 10) {
            nightCountTextView.setText(night + " " + getActivity().getResources().getString(R.string.three_ten_night));
        } else {
            nightCountTextView.setText(night + " " + getActivity().getResources().getString(R.string.eleven_and_above_night));
        }
    }
    /// hotel details
    //////////// details API Call

    private void newFragmentCall() {

        dismissDialog();

        Fragment fragment = new HotelDetailsTabFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelable("HotelDetails", hotelDetailInfoResponse);
        bundle.putParcelable("TripAdvisorDeatil", tripAdvisorDataResponse);
        bundle.putParcelable("HotelRoomRate", hotelRatesResponse);
        fragment.setArguments(bundle);

        setFragmentTransaction(fragment, "NOAnimation");
    }

    // call to hotelListing api.
    private void getCityHotels(String tag) {

        HotelListingRequest hotelListingRequest = HotelListingRequest.getHotelListRequest();
        hotelListingRequest.setCurrencyCode(userDTO.getCurrency());
        hotelListingRequest.setLanguageCode(userDTO.getLanguage());
        hotelListingRequest.setCheckInDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(checkInDate));
        hotelListingRequest.setCheckOutDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(checkOutDate));
        hotelListingRequest.setPageNumber(1);
        hotelListingRequest.setPageSize(10);


        if (tag.equals("noRooms")) {

            hotelListingRequest.setCityId(cityId);  // for sold out hotels.. set popularity and descending order.
            hotelListingRequest.setSortBy(OrderByTypes.Descending.getOrderVal());
            hotelListingRequest.setSortParameter("popularity");

        } else {

            hotelListingRequest.setCityId(Long.parseLong(destination.getKey()));   // regular flow.. getting cityId and areaId from destination.

            UserDTO.getUserDTO().setCityName(destination.getDestinationName());
            // here check whether category is city search otherwise areaWise search
            if (destination.getCategory().equals("City")) {

                hotelListingRequest.setSortBy(OrderByTypes.Descending.getOrderVal());
                hotelListingRequest.setSortParameter("popularity");


            } else {

                hotelListingRequest.setSortBy(OrderByTypes.Descending.getOrderVal());
                hotelListingRequest.setSortParameter("area");
                hotelListingRequest.setSortByArea(0);
            }


        }


        ArrayList<OccupancyDto> occupancyDtoArrayList = new ArrayList<>();
        for (int i = 0; i < hotelAccommodationsArrayList.size(); i++) {
            kidsAgeArrayList = new ArrayList<>();
            if (hotelAccommodationsArrayList.get(i).getKids() > 0) {
                if (hotelAccommodationsArrayList.get(i).getKids() == 1) {
                    kidsAgeArrayList.add(hotelAccommodationsArrayList.get(i).getKid1Age());
                } else if (hotelAccommodationsArrayList.get(i).getKids() == 2) {
                    kidsAgeArrayList.add(hotelAccommodationsArrayList.get(i).getKid1Age());
                    kidsAgeArrayList.add(hotelAccommodationsArrayList.get(i).getKid2Age());
                }
            }

            occupancyDtoArrayList.add(new OccupancyDto(hotelAccommodationsArrayList.get(i).getAdultsCount(), kidsAgeArrayList));
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

        if (NetworkUtilities.isInternet(getActivity())) {

            showDialog();

            hotelSearchPresenter.getHotelListInfo(Constant.API_URL + Constant.HOTELLISTING, request, context);


        } else {

            Utilities.commonErrorMessage(context, context.getString(R.string.Network_not_avilable), context.getString(R.string.please_check_your_internet_connection), false, getFragmentManager());
        }

    }

    @Override
    public void setCheckInDate(String checkInDate) {

        if (!TextUtils.equals(checkInDate, "")) {
            try {

                Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "checkin calendar", "checkin date");

                customCalendarCheckInDialog.dismiss();
                this.checkInDate = simpleDateFormat.parse(checkInDate);
                int day = Integer.parseInt((String) android.text.format.DateFormat.format("dd", this.checkInDate.getTime()));
                int month = Integer.parseInt((String) android.text.format.DateFormat.format("MM", this.checkInDate.getTime()));
                int year = Integer.parseInt((String) android.text.format.DateFormat.format("yyyy", this.checkInDate.getTime()));
                calendar.set(year, month - 1, day);
                calendar.add(Calendar.DATE, 1);
                String endDate = simpleDateFormat.format(calendar.getTime());
                checkOutDate = simpleDateFormat.parse(endDate);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy-EEE", Locale.US);
                this.checkInDateStr = dateFormat.format(this.checkInDate);
                HashMap<String, String> CheckInDateHashMap = Utilities.splitDate(this.checkInDateStr);
                checkInDateTextView.setText(CheckInDateHashMap.get(Constant.Date.DATE.name()));
                selectedDatesArrayList = getDaysBetweenDates(this.checkInDate, checkOutDate);

                this.checkOutDateStr = dateFormat.format(checkOutDate);
                HashMap<String, String> checkOutDateHashMap = Utilities.splitDate(this.checkOutDateStr);
                checkOutDateTextView.setText(checkOutDateHashMap.get(Constant.Date.DATE.name()));
                setMonth(CheckInDateHashMap.get(Constant.Date.YEAR.name()), checkOutDateHashMap.get(Constant.Date.YEAR.name()));
                night = NetworkUtilities.daysBetween(this.checkInDate, checkOutDate);
                if (userDTO.getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE)) {
                    if (night == 1) {
                        nightCountTextView.setText(night + " " + getActivity().getResources().getString(R.string.night));
                    } else {
                        nightCountTextView.setText(night + " " + getActivity().getResources().getString(R.string.nights));
                    }
                } else if (night == 1) {
                    nightCountTextView.setText(getActivity().getResources().getString(R.string.one_night));
                } else if (night == 2) {
                    nightCountTextView.setText(getActivity().getResources().getString(R.string.two_night));
                } else if (night > 2 && night < 10) {
                    nightCountTextView.setText(night + " " + getActivity().getResources().getString(R.string.three_ten_night));
                } else {
                    nightCountTextView.setText(night + " " + getActivity().getResources().getString(R.string.eleven_and_above_night));
                }
                showCheckout();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setCheckOutDate(String checkoutdate) {
        if (!TextUtils.equals(checkoutdate, "")) {
            try {

                Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "checkout calendar", "checkout date");

                checkInDateRelativeLayout.setBackground(null);
                checkOutDateRelativeLayout.setBackground(null);
                customCalendarCheckOutDialog.dismiss();
                checkOutDate = simpleDateFormat.parse(checkoutdate);
                if (checkInDate.compareTo(checkOutDate) == 0) {
                    calendar.add(Calendar.DATE, 1);
                    String endDate = simpleDateFormat.format(calendar.getTime());
                    checkOutDate = simpleDateFormat.parse(endDate);
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy-EEE", Locale.US);
                this.checkOutDateStr = simpleDateFormat.format(checkOutDate);
                HashMap<String, String> checkOutDateHashMap = Utilities.splitDate(this.checkOutDateStr);
                checkOutDateTextView.setText(checkOutDateHashMap.get(Constant.Date.DATE.name()));
                setMonth(null, checkOutDateHashMap.get(Constant.Date.YEAR.name()));
                selectedDatesArrayList = getDaysBetweenDates(checkInDate, checkOutDate);
                night = NetworkUtilities.daysBetween(checkInDate, checkOutDate);
                if (userDTO.getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE)) {
                    if (night == 1) {
                        nightCountTextView.setText(night + " " + getActivity().getResources().getString(R.string.night));
                    } else {
                        nightCountTextView.setText(night + " " + getActivity().getResources().getString(R.string.nights));
                    }
                } else if (night == 1) {
                    nightCountTextView.setText(getActivity().getResources().getString(R.string.one_night));
                } else if (night == 2) {
                    nightCountTextView.setText(getActivity().getResources().getString(R.string.two_night));
                } else if (night > 2 && night < 10) {
                    nightCountTextView.setText(night + " " + getActivity().getResources().getString(R.string.three_ten_night));
                } else {
                    nightCountTextView.setText(night + " " + getActivity().getResources().getString(R.string.eleven_and_above_night));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showCheckIn() {

        checkInDateRelativeLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.date_back, null));
        checkOutDateRelativeLayout.setBackground(null);

        CheckInDialogView checkInDialogView = new CheckInDialogView(context, language, selectedDatesArrayList, HotelIndexFragment.this, Locale.getDefault().getLanguage(),true);
        customCalendarCheckInDialog = new CustomCalendarDialog(context)
                .setLayout(checkInDialogView.Create_CheckInView())
                .setGravity(CustomCalendarDialog.GRAVITY_BOTTOM)
                .setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                .setLocationByAttachedView(checkInDateRelativeLayout)
                .setTouchOutsideDismiss(true)
                .setMarginLeftAndRight(24, 24);
        customCalendarCheckInDialog.show();
        customCalendarCheckInDialog.setOnEasyDialogDismissed(new CustomCalendarDialog.OnEasyDialogDismissed() {
            @Override
            public void onDismissed() {
                checkInDateRelativeLayout.setBackground(null);
            }
        });
    }

    private void showCheckout() {

        checkOutDateRelativeLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.date_back, null));
        checkInDateRelativeLayout.setBackground(null);

        CheckOutDialogView checkOutDialogView = new CheckOutDialogView(context, language, selectedDatesArrayList, HotelIndexFragment.this, Locale.getDefault().getLanguage());
        customCalendarCheckOutDialog = new CustomCalendarDialog(context)
                .setLayout(checkOutDialogView.Create_CheckOutView())
                .setGravity(CustomCalendarDialog.GRAVITY_BOTTOM)
                .setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                .setLocationByAttachedView(checkOutDateRelativeLayout)
                .setTouchOutsideDismiss(true)
                .setMarginLeftAndRight(24, 24);
        customCalendarCheckOutDialog.show();
        customCalendarCheckOutDialog.setOnEasyDialogDismissed(new CustomCalendarDialog.OnEasyDialogDismissed() {
            @Override
            public void onDismissed() {
                checkOutDateRelativeLayout.setBackground(null);
            }
        });
    }

    // calculates days between start date and end date.
    private ArrayList<DateData> getDaysBetweenDates(Date startDate, Date endDate) {

        String language = UserDTO.getUserDTO().getLanguage();
        ArrayList<DateData> dataArrayList = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        while (calendar.getTime().before(endDate)) {
            Date result = calendar.getTime();
            try {
                int day = Integer.parseInt((String) android.text.format.DateFormat.format("dd", result));
                int month = Integer.parseInt((String) android.text.format.DateFormat.format("MM", result));
                int year = Integer.parseInt((String) android.text.format.DateFormat.format("yyyy", result));
                dataArrayList.add(new DateData(day, month, year, "middle"));
                calendar.add(Calendar.DATE, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int day = Integer.parseInt((String) android.text.format.DateFormat.format("dd", endDate));
        int month = Integer.parseInt((String) android.text.format.DateFormat.format("MM", endDate));
        int year = Integer.parseInt((String) android.text.format.DateFormat.format("yyyy", endDate));
        if (language.equalsIgnoreCase(Constant.ENGLISH_LANGUAGE_CODE)) {
            dataArrayList.add(new DateData(day, month, year, "right"));
        } else {
            dataArrayList.add(new DateData(day, month, year, "left"));
        }

        DateData dateData = dataArrayList.get(0);
        if (language.equalsIgnoreCase(Constant.ENGLISH_LANGUAGE_CODE)) {
            dateData.setBackground("left");

        } else {
            dateData.setBackground("right");
        }
        dataArrayList.set(0, dateData);
        return dataArrayList;
    }

    @Override
    public void scrollTo(int position) {

    }

    @Override
    public void doneClick(ArrayList<HotelAccommodation> accommodationArrayList) {

        Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "Done-room", "select rooms and pax");

        ((HomeActivity)getActivity()).bottomNavigationVisibility(View.VISIBLE);

        if (!this.hotelAccommodationsArrayList.isEmpty())
            this.hotelAccommodationsArrayList.clear();

        this.hotelAccommodationsArrayList.addAll(accommodationArrayList);

        int adultCount = 0, kidCount = 0;

        for (int i = 0; i < accommodationArrayList.size(); i++) {
            adultCount = adultCount + accommodationArrayList.get(i).getAdultsCount();
            kidCount = kidCount + accommodationArrayList.get(i).getKids();
        }
        setKidsAndAdultsText(adultCount, kidCount);
        actionBarRelativeLayout.setVisibility(View.VISIBLE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((DrawerLayout) getActivity().findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        addRoomRecyclerView.setVisibility(View.GONE);
        bannerImageView.setVisibility(View.VISIBLE);
        ((HomeActivity) getActivity()).setBackPressListener(null);
    }

    private void setKidsAndAdultsText(int adultCount, int kidCount) {
        if (userDTO.getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE)) {
            if (hotelAccommodationsArrayList.size() == 1) {
                roomCountTextView.setText(hotelAccommodationsArrayList.size() + " " + context.getResources().getString(R.string.room));
            } else {
                roomCountTextView.setText(hotelAccommodationsArrayList.size() + " " + context.getResources().getString(R.string.Room));
            }
        } else if (hotelAccommodationsArrayList.size() == 1) {
            roomCountTextView.setText(context.getResources().getString(R.string.one_room));
        } else if (hotelAccommodationsArrayList.size() == 2) {
            roomCountTextView.setText(context.getResources().getString(R.string.two_room));
        } else {
            roomCountTextView.setText(hotelAccommodationsArrayList.size() + " " + context.getResources().getString(R.string.three_ten_room));
        }
        if (adultCount == 1 && kidCount == 0) {
            adultCountTextView.setText(adultCount + " " + context.getResources().getString(R.string.Adult));
        } else if (adultCount > 1 && kidCount == 0) {
            adultCountTextView.setText(adultCount + " " + context.getResources().getString(R.string.Adults));
        } else if (adultCount == 1 && kidCount == 1) {
            adultCountTextView.setText(adultCount + " " + context.getResources().getString(R.string.Adult) + " , " + kidCount + " " + context.getResources().getString(R.string.Kid));
        } else if (adultCount > 1 && kidCount > 1) {
            adultCountTextView.setText(adultCount + " " + context.getResources().getString(R.string.Adults) + " , " + kidCount + " " + context.getResources().getString(R.string.Kids));
        } else if (adultCount > 1 && kidCount == 1) {
            adultCountTextView.setText(adultCount + " " + context.getResources().getString(R.string.Adults) + " , " + kidCount + " " + context.getResources().getString(R.string.Kid));
        } else if (adultCount == 1 && kidCount > 1) {
            adultCountTextView.setText(adultCount + " " + context.getResources().getString(R.string.Adult) + " , " + kidCount + " " + context.getResources().getString(R.string.Kids));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        locationArrayList = new ArrayList<>();
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            destination = data.getParcelableExtra("data");
            if (destination.isHotel()) {
                UserDTO.getUserDTO().setHotel(true);
            } else {
                UserDTO.getUserDTO().setHotel(false);
            }
            if (!locationArrayList.isEmpty())
                locationArrayList.clear();
            if (destination.getLatitude() != 0 && destination.getLogitude() != 0) {
                locationArrayList.add(destination.getLatitude());
                locationArrayList.add(destination.getLogitude());
            }
        }
    }

    @Override
    public void initUIElements() {
    }

    @Override
    public void backPressCalled() {

        int adultCount = 0, kidCount = 0;
        for (int i = 0; i < hotelAccommodationsArrayList.size(); i++) {
            adultCount = adultCount + hotelAccommodationsArrayList.get(i).getAdultsCount();
            kidCount = kidCount + hotelAccommodationsArrayList.get(i).getKids();
        }
        setKidsAndAdultsText(adultCount, kidCount);
        actionBarRelativeLayout.setVisibility(View.VISIBLE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        addRoomRecyclerView.setVisibility(View.GONE);
        ((HomeActivity) getActivity()).setBackPressListener(null);
        ((HomeActivity)getActivity()).bottomNavigationVisibility(View.VISIBLE);

    }

    private void setMonth(String inYear, String outYear) {

        SimpleDateFormat simpleDateFormat;

        if (inYear != null) {
            String checkInDate = "";
            simpleDateFormat = new SimpleDateFormat("dd-MMMM-yyyy-EEE", new Locale(userDTO.getLanguage()));
            checkInDate = simpleDateFormat.format(this.checkInDate);

            checkInMonthTextView.setText(Utilities.splitDate(checkInDate).get(Constant.Date.MONTH.name()) + " " + inYear);
            checkInDayTextView.setText(Utilities.splitDate(checkInDate).get(Constant.Date.DAY.name()));
        }
        if (outYear != null) {
            String checkOutDate = "";
            simpleDateFormat = new SimpleDateFormat("dd-MMMM-yyyy-EEE", new Locale(userDTO.getLanguage()));
            checkOutDate = simpleDateFormat.format(this.checkOutDate);
            checkOutMonthTextView.setText(Utilities.splitDate(checkOutDate).get(Constant.Date.MONTH.name()) + " " + outYear);
            checkOutDayTextView.setText(Utilities.splitDate(checkOutDate).get(Constant.Date.DAY.name()));
        }
    }

    private void copyTextToClipBoard() {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(final Void... params) {
                String line = "";
                try {
                    URL url = new URL(Constant.COUPON_CODE_URL);

                    // read text returned by server
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));

                    while ((line = bufferedReader.readLine()) != null) {
                        return line;
                    }
                    bufferedReader.close();
                } catch (MalformedURLException e) {
                    System.out.println("Malformed URL: " + e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("I/O Error: " + e.getMessage());
                    e.printStackTrace();
                }
                return line;
            }

            @Override
            protected void onPostExecute(String couponcode) {
                if (!couponcode.equalsIgnoreCase("")) {
                    ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("couponcode", couponcode);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getActivity(), getString(R.string.coupon_code), Toast.LENGTH_SHORT).show();
                }
                bannerFlag = true;
                fullScreenBannerRelativeLayout.setVisibility(View.GONE);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fullScreenBannerRelativeLayout:

                bannerFlag = true;
                fullScreenBannerRelativeLayout.setVisibility(View.GONE);

                break;

            case R.id.crossBannerImageView:

                bannerFlag = true;
                fullScreenBannerRelativeLayout.setVisibility(View.GONE);

                break;

            case R.id.checkBuildTextView:

                //checkBuild();
                break;

            case R.id.searchTextView:

                navigateToHotelSearch();
                break;

            case R.id.locationSearchTextView:

                navigateToLocationSearch();
                break;

            case R.id.bannerImageView:

                copyTextToClipBoard();
                break;

            case R.id.checkInDateRelativeLayout:


                gtmAnalytics.sendEvent("Hotel Index Screen", "Check-in", "checkin calendar open");
                Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "Check-in", "checkin calendar open");

                showCheckIn();
                break;

            case R.id.checkOutDateRelativeLayout:


                gtmAnalytics.sendEvent("Hotel Index Screen", "check-out", "check-out calendar open");

                Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "check-out", "check-out calendar open");

                showCheckout();
                break;

            case R.id.roomCountRelativeLayout:

                gtmAnalytics.sendEvent("Hotel Index Screen", "Room/Pax selection", "select rooms and pax");
                Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "Room/Pax selection", "select rooms and pax");
                ((HomeActivity) getActivity()).setBackPressListener(HotelIndexFragment.this);

                ((HomeActivity)getActivity()).bottomNavigationVisibility(View.GONE);

                addRoomRecyclerView.setVisibility(View.VISIBLE);
                bannerImageView.setVisibility(View.GONE);
                actionBarRelativeLayout.setVisibility(View.INVISIBLE);

                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                ((DrawerLayout) getActivity().findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                addRoomRecyclerView.setVisibility(View.VISIBLE);
                AddRoomAdapter addRoomAdapter = new AddRoomAdapter(hotelAccommodationsArrayList, context, HotelIndexFragment.this, gtmAnalytics, getFragmentManager());
                addRoomRecyclerView.setAdapter(addRoomAdapter);

                break;


            default:
                break;
        }
    }


    // this method is used to check the build version.
    private void checkBuild() {
        count++;
        if (count == 5) {
            if (Constant.build_version.equals("qa")) {
                Toast.makeText(context, "This is a Test Build", Toast.LENGTH_LONG).show();
            } else if (Constant.build_version.equals("prod")) {
                Toast.makeText(context, "This is a Live Build", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void navigateToLocationSearch() {

        fragmentAnimation = true;

        AutoCompleteFragment autoCompleteFragment = new AutoCompleteFragment();
        autoCompleteFragment.setTargetFragment(HotelIndexFragment.this, 1);
        setFragmentTransaction(autoCompleteFragment, "Animation");

    }

    private void navigateToHotelSearch() {

        EndlessRecyclerOnScrollListener.current_page = 1;
        SharedPreferenceManager.getInstance(context).saveIntegerPreference(Constant.MIN_SELECTED, 0);
        SharedPreferenceManager.getInstance(context).saveIntegerPreference(Constant.MAX_SELECTED, 0);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        engCheckInDate = simpleDateFormat.format(checkInDate);
        engCheckOutDate = simpleDateFormat.format(checkOutDate);

        if (destination != null) {
            int days;
            try {
                days = NetworkUtilities.daysBetween(checkOutDate, checkInDate);
                if (days <= 25) {
                    gtmAnalytics.sendEvent("Hotel Index Screen", "Search", "Search for hotels");
                    DatabaseManager.deleteData(destination.getDestinationName(), UserDTO.getUserDTO().getLanguage());
                    UserDTO.getUserDTO().setDestinationName(destination.getDestinationName());

                    if (DatabaseManager.getRowCount(UserDTO.getUserDTO().getLanguage()) == Constant.MAX_RECENT_SEARCH_LIMIT) {
                        DatabaseManager.deleteData(DatabaseManager.getOldData(UserDTO.getUserDTO().getLanguage()), UserDTO.getUserDTO().getLanguage());
                    }
                    String kids = "0", adults;
                    String[] adultCountArray = adultCountTextView.getText().toString().trim().split(",");
                    if (adultCountArray.length > 1) {
                        adults = adultCountArray[0];
                        kids = adultCountArray[1];
                    } else {
                        adults = adultCountArray[0];
                    }

                    DatabaseManager.insertData(destination.getDestinationName(), engCheckInDate, engCheckOutDate, new Gson().toJson(hotelAccommodationsArrayList), adults, kids, destination.getCategory(), UserDTO.getUserDTO().getLanguage(), destination.getKey());

                    if (NetworkUtilities.isInternet(context)) {

                        UserDTO.getUserDTO().setDestinationName(destination.getDestinationName());

                        //FaceBook Event Tracking
                        Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), Constant.HOTEL_SEARCH, Constant.CHECK_IN_DATE, simpleDateFormat.format(checkInDate));
                        Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), Constant.HOTEL_SEARCH, Constant.CHECK_OUT_DATE, simpleDateFormat.format(checkOutDate));
                        Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), Constant.HOTEL_SEARCH, Constant.HOTEL_ID, destination.getKey());
                        Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), Constant.HOTEL_SEARCH, Constant.HOTEL_NAME, locationSearchTextView.getText().toString());
                        Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), Constant.HOTEL_SEARCH, Constant.ROOMSCOUNT, Integer.toString(hotelAccommodationsArrayList.size()));
                        Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), Constant.HOTEL_SEARCH, Constant.NIGHTSCOUNT, Integer.toString(night));

                        if (destination.getCategory().equalsIgnoreCase("Hotel")) {

                            UserDTO.getUserDTO().setHotel(true);
                            getHotel();

                        } else {

                            UserDTO.getUserDTO().setHotel(false);
                            getCityHotels("cities");
                        }
                    } else {


                        Utilities.commonErrorMessage(context, context.getString(R.string.Network_not_avilable), context.getString(R.string.please_check_your_internet_connection), false, getFragmentManager());
                    }

                } else {

                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.date_difference), false, getFragmentManager());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.Invalid_destination_Please_select_valid_destination), false, getFragmentManager());
        }
    }

    private void getHotel() {

        if (NetworkUtilities.isInternet(getActivity())) {

            showDialog();
            hotelSearchPresenter.getCityId(Constant.API_URL + Constant.GETDESTINATIONIDMETHOD + "?HotelId=" + destination.getKey() + "&languageCode=" + userDTO.getLanguage(), getActivity());

        } else {

            Utilities.commonErrorMessage(context, context.getString(R.string.Network_not_avilable), context.getString(R.string.please_check_your_internet_connection), false, getFragmentManager());
        }
    }

    @Override
    public void dismissDialog() {

        if (spinningDialog != null && spinningDialog.isShowing()) {
            spinningDialog.dismiss();
        }
    }


    @Override
    public void setGetDestinationForHotelResponse(GetDestinationForHotelResponse getDestinationForHotelResponse) {

        this.mGetDestinationForHotelResponse = getDestinationForHotelResponse;
        cityId = getDestinationForHotelResponse.getCityId();

        UserDTO.getUserDTO().setCityName(getDestinationForHotelResponse.getCity());


        getHotelRoomRateCall(mGetDestinationForHotelResponse.getCityId());


    }

    // call to hotelRoomRate api.
    private void getHotelRoomRateCall(long cityId) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD", Locale.ENGLISH);
        engCheckInDate = dateFormat.format(checkInDate);
        engCheckOutDate = dateFormat.format(checkOutDate);

        ArrayList<OccupancyDto> occupancyDtoArrayList = new ArrayList<>();
        HotelRoomRateRequest hotelRoomRateRequest = new HotelRoomRateRequest();
        hotelRoomRateRequest.setCheckInDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(checkInDate));
        hotelRoomRateRequest.setCheckOutDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(checkOutDate));
        hotelRoomRateRequest.setLanguageCode(UserDTO.getUserDTO().getLanguage());
        hotelRoomRateRequest.setCityId(cityId);
        hotelRoomRateRequest.setHotelId(Long.parseLong(destination.getKey()));
        hotelRoomRateRequest.setCurrencyCode(UserDTO.getUserDTO().getCurrency());

        for (int i = 0; i < hotelAccommodationsArrayList.size(); i++) {

            if (kidsAgeArrayList == null)
                kidsAgeArrayList = new ArrayList<>();
            kidsAgeArrayList.clear();

            if (hotelAccommodationsArrayList.get(i).getKids() > 0) {
                if (hotelAccommodationsArrayList.get(i).getKids() == 1) {
                    kidsAgeArrayList.add(hotelAccommodationsArrayList.get(i).getKid1Age());
                } else if (hotelAccommodationsArrayList.get(i).getKids() == 2) {
                    kidsAgeArrayList.add(hotelAccommodationsArrayList.get(i).getKid1Age());
                    kidsAgeArrayList.add(hotelAccommodationsArrayList.get(i).getKid2Age());
                }
            }

            occupancyDtoArrayList.add(new OccupancyDto(hotelAccommodationsArrayList.get(i).getAdultsCount(), kidsAgeArrayList));
            hotelRoomRateRequest.setOccupancy(occupancyDtoArrayList);
        }

        HotelListingRequest hotelListingRequest = HotelListingRequest.getHotelListRequest();
        hotelListingRequest.setCheckInDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(checkInDate));
        hotelListingRequest.setCheckOutDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(checkOutDate));
        hotelListingRequest.setLanguageCode(UserDTO.getUserDTO().getLanguage());
        hotelListingRequest.setCityId(cityId);
        hotelListingRequest.setCurrencyCode(UserDTO.getUserDTO().getCurrency());
        hotelListingRequest.setOccupancy(hotelRoomRateRequest.getOccupancy());

        String request = new Gson().toJson(hotelRoomRateRequest);

        if (NetworkUtilities.isInternet(getActivity())) {
            hotelSearchPresenter.getHotelRoomRate(Constant.API_URL + Constant.HOTELSRATES, request, getActivity());

        } else
            Utilities.commonErrorMessage(context, context.getString(R.string.Network_not_avilable), context.getString(R.string.please_check_your_internet_connection), false, getFragmentManager());
    }

    // setting hotel rate response.
    @Override
    public void setHotelRateResponse(HotelRatesResponse hotelRateResponse) {

        this.hotelRatesResponse = hotelRateResponse;

        if (hotelRatesResponse.getRoomTypes() != null && hotelRatesResponse.getRoomTypes().size() != 0) {

            hotelDetailCall(mGetDestinationForHotelResponse.getCityId());
        } else {

            dismissDialog();

            final CustomeDailogWithTwoButtons customeDailogWithTwoButtons = new CustomeDailogWithTwoButtons(getActivity(), getString(R.string.app_name), getString(R.string.location_no_rooms_available), getString(R.string.No), getString(R.string.yes));
            customeDailogWithTwoButtons.setDialogExitListener(new CustomeDailogWithTwoButtons.DialogExitListener() {
                @Override
                public void dialogExitWithDone() {
                    customeDailogWithTwoButtons.dismiss();
                    getCityHotels("noRooms");
                    isSoldOut=true;

                }

                @Override
                public void dialogExitWithDismissOrCancel() {
                    customeDailogWithTwoButtons.dismiss();
                }
            });
            if (!customeDailogWithTwoButtons.isShowing())
                customeDailogWithTwoButtons.show();

        }
    }

    //    call to hotel detail. This methods navigates control to hotelDetailsTab fragment.
    private void hotelDetailCall(long cityId) {

        if (NetworkUtilities.isInternet(getActivity())) {
            String request = new Gson().toJson(new HotelDetailRequest(cityId, Long.parseLong(destination.getKey()), UserDTO.getUserDTO().getLanguage()));
            hotelSearchPresenter.getHotelDetails(Constant.API_URL + Constant.HOTELDETAIL, request, getActivity());

        } else {
            Utilities.commonErrorMessage(context, context.getString(R.string.Network_not_avilable), context.getString(R.string.please_check_your_internet_connection), false, getFragmentManager());
        }
    }

    @Override
    public void setHotelDetailResponse(HotelDetailInfoResponse hotelDetailResponse) {

        this.hotelDetailInfoResponse = hotelDetailResponse;
        if (hotelDetailInfoResponse.getBasicInfo().getTripAdvisor().getRating() != 0.0 && hotelDetailInfoResponse.getBasicInfo().getTripAdvisor().getRating() != 0) {
            tripAdvisorDetailCall(hotelDetailInfoResponse.getId());
        } else
            newFragmentCall();
    }

    @Override
    public void setTripAdvisorDetailResponse(TripAdviserDataResponse tripAdvisorResponse) {
        this.tripAdvisorDataResponse = tripAdvisorResponse;
        newFragmentCall();
    }

    // call to TripAdvisorDetailCall API.
    private void tripAdvisorDetailCall(Long hotelId) {

        if (NetworkUtilities.isInternet(getActivity())) {
            showDialog();
            hotelSearchPresenter.getTripAdvisorDetails(Constant.API_URL + Constant.TRIPADVISORDATA + "?HotelId=" + hotelId + "&languageCode=" + UserDTO.getUserDTO().getLanguage(), getActivity());
        } else
            Utilities.commonErrorMessage(context, context.getString(R.string.Network_not_avilable), context.getString(R.string.please_check_your_internet_connection), false, getFragmentManager());
    }

    @Override
    public void setHotelListData(HotelListingInfoResponse hotelListingInfoResponse) {

        dismissDialog();

        if ((hotelListingInfoResponse.getHotels() != null) && hotelListingInfoResponse.getHotels().size() > 0) {

            UserDTO.getUserDTO().setDestinationName(destination.getDestinationName());
            if(isSoldOut){
                UserDTO.getUserDTO().setCityName(hotelListingInfoResponse.getHotels().get(0).getBasicInfo().getCty());
            }
            else{

                UserDTO.getUserDTO().setCityName(destination.getDestinationName());

            }

            if (getActivity() != null && !getActivity().isFinishing()) {

                Fragment fragment = new HotelListingFragment();
                HotelListingFragment.scrollToTop = true;
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.HOTEL_LISTING_RESPONSE, hotelListingInfoResponse);
                fragment.setArguments(bundle);
                ((HomeActivity)getActivity()).bottomNavigationVisibility(View.GONE);

                setFragmentTransaction(fragment, "NoAnimation");

            }
        }
        isSoldOut=false;

    }


    @Override
    public void showDialog() {

        if (spinningDialog != null && !spinningDialog.isShowing()) {

            spinningDialog.show();
        }


    }

    private void showActionBar() {

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((TextView) (((HomeActivity) getActivity()).toolbar.findViewById(R.id.toolbar_title))).setText(getActivity().getResources().getString(R.string.Hotel));
    }

    private void setDrawer() {

        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        languageLinearLayout = (LinearLayout) drawerLayout.findViewById(R.id.languageLinearLayout);
        View view = drawerLayout.findViewById(R.id.topBarView);
        if (languageLinearLayout != null) {
            view.setVisibility(View.VISIBLE);
            languageLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    private void setNewOccupancyData() {

        Destination dest = DatabaseManager.getLatestData(language);

        if (dest != null) {

            locationSearchTextView.setText(dest.getDestinationName());

            try {
                if (dest.getCheckInDate() != null && dest.getCheckOutDate() != null) {
                    if (!Utilities.isPassedDate(dest.getCheckInDate())) {

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy-EEE", Locale.US);
                        checkInDate = simpleDateFormat.parse(dest.getCheckInDate());
                        checkOutDate = simpleDateFormat.parse(dest.getCheckOutDate());
                        checkInDateStr = dateFormat.format(checkInDate);
                        checkOutDateStr = dateFormat.format(checkOutDate);
                        night = NetworkUtilities.daysBetween(checkInDate, checkOutDate);

                    }
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }


            HashMap<String, String> checkInDateHashMap = Utilities.splitDate(checkInDateStr);
            checkInDateTextView.setText(checkInDateHashMap.get(Constant.Date.DATE.name()));
            HashMap<String, String> checkOutDateHashMap = Utilities.splitDate(checkOutDateStr);
            checkOutDateTextView.setText(checkOutDateHashMap.get(Constant.Date.DATE.name()));
            setMonth(checkInDateHashMap.get(Constant.Date.YEAR.name()), checkOutDateHashMap.get(Constant.Date.YEAR.name()));

            selectedDatesArrayList = getDaysBetweenDates(checkInDate, checkOutDate);

            ArrayList<HotelAccommodation> accommodationArrayList = new ArrayList<>();

            Type listType = new TypeToken<ArrayList<HotelAccommodation>>() {
            }.getType();
            accommodationArrayList = new GsonBuilder().create().fromJson(dest.getmRoomInfo(), listType);


            AddRoomAdapter addRoomAdapter = new AddRoomAdapter(accommodationArrayList, context, HotelIndexFragment.this, gtmAnalytics, getFragmentManager());
            addRoomRecyclerView.setAdapter(addRoomAdapter);

            this.hotelAccommodationsArrayList = accommodationArrayList;
            setRecentRoomInfo(accommodationArrayList);
            setNightCounts();

            Constant.dateChangeFlag = false;
            this.destination = dest;
        }
    }

    private void setRecentRoomInfo(ArrayList<HotelAccommodation> accommodationArrayList) {

        int adultCount = 0, kidCount = 0;
        for (int i = 0; i < accommodationArrayList.size(); i++) {
            adultCount = adultCount + accommodationArrayList.get(i).getAdultsCount();
            kidCount = kidCount + accommodationArrayList.get(i).getKids();
        }
        setRecentKidsAndAdultsTexts(adultCount, kidCount, accommodationArrayList);
    }

    private void setRecentKidsAndAdultsTexts(int adultCount, int kidCount, ArrayList<HotelAccommodation> accommodationArrayList) {

        if (userDTO.getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE)) {
            if (accommodationArrayList.size() == 1) {
                roomCountTextView.setText(accommodationArrayList.size() + " " + context.getResources().getString(R.string.room));
            } else {
                roomCountTextView.setText(accommodationArrayList.size() + " " + context.getResources().getString(R.string.Room));
            }
        } else if (accommodationArrayList.size() == 1) {
            roomCountTextView.setText(context.getResources().getString(R.string.one_room));
        } else if (accommodationArrayList.size() == 2) {
            roomCountTextView.setText(context.getResources().getString(R.string.two_room));
        } else {
            roomCountTextView.setText(accommodationArrayList.size() + " " + context.getResources().getString(R.string.three_ten_room));
        }
        if (adultCount == 1 && kidCount == 0) {
            adultCountTextView.setText(adultCount + " " + context.getResources().getString(R.string.Adult));
        } else if (adultCount > 1 && kidCount == 0) {
            adultCountTextView.setText(adultCount + " " + context.getResources().getString(R.string.Adults));
        } else if (adultCount == 1 && kidCount == 1) {
            adultCountTextView.setText(adultCount + " " + context.getResources().getString(R.string.Adult) + " , " + kidCount + " " + context.getResources().getString(R.string.Kid));
        } else if (adultCount > 1 && kidCount > 1) {
            adultCountTextView.setText(adultCount + " " + context.getResources().getString(R.string.Adults) + " , " + kidCount + " " + context.getResources().getString(R.string.Kids));
        } else if (adultCount > 1 && kidCount == 1) {
            adultCountTextView.setText(adultCount + " " + context.getResources().getString(R.string.Adults) + " , " + kidCount + " " + context.getResources().getString(R.string.Kid));
        } else if (adultCount == 1 && kidCount > 1) {
            adultCountTextView.setText(adultCount + " " + context.getResources().getString(R.string.Adult) + " , " + kidCount + " " + context.getResources().getString(R.string.Kids));
        }


    }


    private void setFragmentTransaction(Fragment fragment, String animation) {

        try {
            fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

            if (animation.equals("Animation")) {

                fragmentTransaction.setCustomAnimations(0, 0, 0, R.anim.slide_out_down);
                fragmentTransaction.replace(R.id.container_body, fragment, AutoCompleteFragment.class.getName());
                fragmentTransaction.addToBackStack(AutoCompleteFragment.class.getName());
            }
            else {

                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.addToBackStack(null);

            }
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private class VersionChecker extends AsyncTask<String, String, String> {

        String newVersion;

        @Override
        protected String doInBackground(String... params) {

            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=com.holidayme.activities&hl=en")
                        .timeout(500)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newVersion;

        }

        @Override
        protected void onPostExecute(String s) {
            try {
                if (getActivity() != null && getActivity().getPackageManager() != null) {
                    String currentVersion = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
                    if (!isUpdate)
                        if (s != null && !currentVersion.equals(s)) {
                            final CustomeDailogWithTwoButtons customeDailog = new CustomeDailogWithTwoButtons(getActivity(), getString(R.string.app_name), getString(R.string.upgrade_app_two_button_msg), getString(R.string.LATER), getString(R.string.update_two_btn));
                            customeDailog.setDialogExitListener(new CustomeDailogWithTwoButtons.DialogExitListener() {
                                @Override
                                public void dialogExitWithDone() {
                                    Utilities.navigateToPlayStore(getActivity());
                                    customeDailog.dismiss();
                                }

                                @Override
                                public void dialogExitWithDismissOrCancel() {
                                    customeDailog.dismiss();
                                }


                            });
                            customeDailog.show();
                            isUpdate = true;
                        }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        }
    }


}