package com.holidayme.widgets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.holidayme.activities.util.Utilities;
import com.holidayme.adapter.AddRoomAdapter;
import com.holidayme.common.CheckInDialogView;
import com.holidayme.common.CheckOutDialogView;
import com.holidayme.common.CustomCalendarDialog;
import com.holidayme.common.CustomProgressDialog;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.common.Validation;
import com.holidayme.data.DateData;
import com.holidayme.data.Destination;
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

import java.lang.reflect.Type;
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
public class HotelIndexFragmentDailog extends DialogFragment implements OnItemClick, DateSelectListener, BackPressInterFace, View.OnClickListener, IUserLandingView, IUserLandingView.IHotelSearchView {

    //==============================================================
    private View rootView;
    private  LinearLayout languageLinearLayout;
    private TextView  searchTextView, checkInDayTextView, checkInMonthTextView, checkInDateTextView, checkOutDayTextView, checkOutMonthTextView, checkOutDateTextView, nightCountTextView, roomCountTextView, adultCountTextView;
    private RelativeLayout checkInDateRelativeLayout, checkOutDateRelativeLayout, roomCountRelativeLayout, actionBarRelativeLayout;
    private RecyclerView addRoomRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Date checkInDate, checkOutDate;
    private CustomCalendarDialog customCalendarCheckInDialog, customCalendarCheckOutDialog;
    private SimpleDateFormat simpleDateFormat;
    private Calendar calendar;
    private UserDTO userDTO;
    private int night;
    private ArrayList<HotelAccommodation> hotelAccommodationsArrayList;
    private Dialog spinningDialog;
    private HotelDetailInfoResponse hotelDetailInfoResponse;
    private HotelRatesResponse hotelRatesResponse;
    private TripAdviserDataResponse tripAdvisorDataResponse;
    private String checkInDateStr = "", checkOutDateStr = "", engCheckInDate = "", engCheckOutDate = "",destinationName,hotelName;
    private ArrayList<Double> locationArrayList ;
    private GTMAnalytics gtmAnalytics;
    private ArrayList<Integer> kidsAgeArrayList;
    private ArrayList<DateData> selectedDatesArrayList;
    private HotelSearchPresenter hotelSearchPresenter;
    private FragmentTransaction fragmentTransaction;
    private long cityId,hotelId;
    public static int backCount;
    private  boolean isDeepLink;
    private Destination destination = null;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userDTO= UserDTO.getUserDTO();
        hotelSearchPresenter = new HotelSearchPresenter(this);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        spinningDialog = CustomProgressDialog.showProgressDialog(getActivity());
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

        Bundle bundle = this.getArguments();

        if (bundle != null) {

            cityId=bundle.getLong("cityId");
            hotelId=bundle.getLong("hotelids");
            destinationName= bundle.getString("destinationName");
            isDeepLink=bundle.getBoolean("isDeepLink");
            hotelName=bundle.getString("hotelName");


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.index_page_dailog, container, false);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.add_room_bg);

        setHasOptionsMenu(true);


        hotelSearchPresenter = new HotelSearchPresenter(this);

        initUI();

        setRoomInfoData();
      //  showActionBar();
        setDrawer();
        setRecentOccupancyData();

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        gtmAnalytics = AppController.getInstance().getGTMAnalytics(getActivity());
      //  gtmAnalytics.setScreenName("Hotel Index Screen");

        return rootView;
    }

    private void setRecentOccupancyData() {



        Destination destinationArrayList= DatabaseManager.getLatestData(UserDTO.getUserDTO().getLanguage());

        if(destinationArrayList!=null){

            try {
                if (destinationArrayList.getCheckInDate() != null && destinationArrayList.getCheckOutDate() != null) {
                    if (!Utilities.isPassedDate(destinationArrayList.getCheckInDate())) {

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy-EEE", Locale.US);
                        checkInDate = simpleDateFormat.parse(destinationArrayList.getCheckInDate());
                        checkOutDate = simpleDateFormat.parse(destinationArrayList.getCheckOutDate());
                        checkInDateStr = dateFormat.format(checkInDate);
                        checkOutDateStr = dateFormat.format(checkOutDate);
                        night = NetworkUtilities.daysBetween(checkInDate, checkOutDate);

                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


            HashMap<String, String> checkInDateHashMap = Utilities.splitDate(checkInDateStr);
            checkInDateTextView.setText(checkInDateHashMap.get(Constant.Date.DATE.name()));
            HashMap<String, String> checkOutDateHashMap = Utilities.splitDate(checkOutDateStr);
            checkOutDateTextView.setText(checkOutDateHashMap.get(Constant.Date.DATE.name()));
            setMonth(checkInDateHashMap.get(Constant.Date.YEAR.name()), checkOutDateHashMap.get(Constant.Date.YEAR.name()));

            selectedDatesArrayList = getDaysBetweenDates(checkInDate, checkOutDate);

            ArrayList<HotelAccommodation>accommodationArrayList= new ArrayList<>();
            Type listType = new TypeToken<ArrayList<HotelAccommodation>>(){}.getType();
            accommodationArrayList = new GsonBuilder().create().fromJson(destinationArrayList.getmRoomInfo(), listType);

            AddRoomAdapter addRoomAdapter= new AddRoomAdapter(accommodationArrayList,getActivity(),HotelIndexFragmentDailog.this,gtmAnalytics,getFragmentManager());
            addRoomRecyclerView.setAdapter(addRoomAdapter);

            this.hotelAccommodationsArrayList=accommodationArrayList;
            setRecentRoomInfo(accommodationArrayList);
            setNightCounts();
        }
    }

    private void setRecentRoomInfo(ArrayList<HotelAccommodation> accommodationArrayList) {



        int adultCount = 0,kidCount = 0;
        for (int i = 0; i < accommodationArrayList.size(); i++) {
            adultCount = adultCount + accommodationArrayList.get(i).getAdultsCount();
            kidCount = kidCount + accommodationArrayList.get(i).getKids();
        }

        setRecentKidsAndAdultsTexts(adultCount,kidCount,accommodationArrayList);

    }

    private void setRecentKidsAndAdultsTexts(int adultCount, int kidCount, ArrayList<HotelAccommodation> accommodationArrayList) {

        if (userDTO.getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE)) {
            if (accommodationArrayList.size() == 1) {
                roomCountTextView.setText(accommodationArrayList.size() + " " + getActivity().getResources().getString(R.string.room));
            } else {
                roomCountTextView.setText(accommodationArrayList.size() + " " + getActivity().getResources().getString(R.string.Room));
            }
        } else if (accommodationArrayList.size() == 1) {
            roomCountTextView.setText(getActivity().getResources().getString(R.string.one_room));
        } else if (accommodationArrayList.size() == 2) {
            roomCountTextView.setText(getActivity().getResources().getString(R.string.two_room));
        } else {
            roomCountTextView.setText(accommodationArrayList.size() + " " + getActivity().getResources().getString(R.string.three_ten_room));
        }
        if (adultCount == 1 && kidCount == 0) {
            adultCountTextView.setText(adultCount + " " + getActivity().getResources().getString(R.string.Adult));
        } else if (adultCount > 1 && kidCount == 0) {
            adultCountTextView.setText(adultCount + " " + getActivity().getResources().getString(R.string.Adults));
        } else if (adultCount == 1 && kidCount == 1) {
            adultCountTextView.setText(adultCount + " " + getActivity().getResources().getString(R.string.Adult) + " , " + kidCount+ " " + getActivity().getResources().getString(R.string.Kid));
        } else if (adultCount > 1 && kidCount > 1) {
            adultCountTextView.setText(adultCount + " " + getActivity().getResources().getString(R.string.Adults) + " , " + kidCount + " " + getActivity().getResources().getString(R.string.Kids));
        } else if (adultCount > 1 && kidCount == 1) {
            adultCountTextView.setText(adultCount + " " + getActivity().getResources().getString(R.string.Adults) + " , " + kidCount + " " + getActivity().getResources().getString(R.string.Kid));
        } else if (adultCount == 1 && kidCount > 1) {
            adultCountTextView.setText(adultCount + " " + getActivity().getResources().getString(R.string.Adult) + " , " + kidCount + " " + getActivity().getResources().getString(R.string.Kids));
        }

    }

    private void initUI() {

        searchTextView = (TextView) rootView.findViewById(R.id.searchTextView);
        checkInDateTextView = (TextView) rootView.findViewById(R.id.checkInDateTextView);
        checkInMonthTextView = (TextView) rootView.findViewById(R.id.checkInMonthTextView);
        checkInDayTextView = (TextView) rootView.findViewById(R.id.checkInDayTextView);
        checkOutDateTextView = (TextView) rootView.findViewById(R.id.checkOutDateTextView);
        checkOutMonthTextView = (TextView) rootView.findViewById(R.id.checkOutMonthTextView);
        checkOutDayTextView = (TextView) rootView.findViewById(R.id.checkOutDayTextView);
        roomCountTextView = (TextView) rootView.findViewById(R.id.roomCountTextView);
        nightCountTextView = (TextView) rootView.findViewById(R.id.nightCountTextView);
        adultCountTextView = (TextView) rootView.findViewById(R.id.adultCountTextView);
        addRoomRecyclerView = (RecyclerView) rootView.findViewById(R.id.addRoomRecyclerView);
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

        searchTextView.setOnClickListener(this);
        checkInDateRelativeLayout.setOnClickListener(this);
        checkOutDateRelativeLayout.setOnClickListener(this);
        roomCountRelativeLayout.setOnClickListener(this);
        checkInDateTextView.setOnClickListener(this);

    }


    private void setRoomInfoData() {



        destination = DatabaseManager.getLatestData(UserDTO.getUserDTO().getLanguage());

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


        selectedDatesArrayList = getDaysBetweenDates(checkInDate, checkOutDate);
        setRoomCounts();
        setNightCounts();
    }

    private void setRoomCounts() {

        if (hotelAccommodationsArrayList == null) {
            hotelAccommodationsArrayList = new ArrayList<>();

            hotelAccommodationsArrayList.add(new HotelAccommodation(2,null,0,1,1));
            if (UserDTO.getUserDTO().getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE))
                roomCountTextView.setText(1 + " " + getActivity().getResources().getString(R.string.room));
            else {
                roomCountTextView.setText(getActivity().getResources().getString(R.string.one_room));
            }

            adultCountTextView.setText(2 + " " + getActivity().getResources().getString(R.string.Adult));
        } else {
            int adultCount = 0,kidCount = 0;
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
        //backCount=1;
        backCount++;
        if(getDialog().isShowing()){
            getDialog().dismiss();
        }

        Fragment fragment = new HotelDetailsTabFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelable("HotelDetails", hotelDetailInfoResponse);
        bundle.putParcelable("TripAdvisorDeatil", tripAdvisorDataResponse);
        bundle.putParcelable("HotelRoomRate", hotelRatesResponse);

        fragment.setArguments(bundle);
        setFragmentTransaction(fragment);
    }



    @Override
    public void setCheckInDate(String checkInDate ) {

        if (!TextUtils.equals(checkInDate, "")) {
            try {

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

        CheckInDialogView checkInDialogView = new CheckInDialogView(getActivity(), UserDTO.getUserDTO().getLanguage(), selectedDatesArrayList, HotelIndexFragmentDailog.this, Locale.getDefault().getLanguage(),false);
        customCalendarCheckInDialog = new CustomCalendarDialog(getActivity())
                .setLayout(checkInDialogView.Create_CheckInView())
                .setGravity(CustomCalendarDialog.GRAVITY_BOTTOM)
                .setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white))
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

        CheckOutDialogView checkOutDialogView = new CheckOutDialogView(getActivity(), UserDTO.getUserDTO().getLanguage(), selectedDatesArrayList, HotelIndexFragmentDailog.this, Locale.getDefault().getLanguage());
        customCalendarCheckOutDialog = new CustomCalendarDialog(getActivity())
                .setLayout(checkOutDialogView.Create_CheckOutView())
                .setGravity(CustomCalendarDialog.GRAVITY_BOTTOM)
                .setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white))
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

        String language= UserDTO.getUserDTO().getLanguage();
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((DrawerLayout) getActivity().findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        addRoomRecyclerView.setVisibility(View.GONE);
        ((HomeActivity) getActivity()).setBackPressListener(null);
    }

    private void setKidsAndAdultsText(int adultCount, int kidCount) {
        if (userDTO.getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE)) {
            if (hotelAccommodationsArrayList.size() == 1) {
                roomCountTextView.setText(hotelAccommodationsArrayList.size() + " " + getActivity().getResources().getString(R.string.room));
            } else {
                roomCountTextView.setText(hotelAccommodationsArrayList.size() + " " + getActivity().getResources().getString(R.string.Room));
            }
        } else if (hotelAccommodationsArrayList.size() == 1) {
            roomCountTextView.setText(getActivity().getResources().getString(R.string.one_room));
        } else if (hotelAccommodationsArrayList.size() == 2) {
            roomCountTextView.setText(getActivity().getResources().getString(R.string.two_room));
        } else {
            roomCountTextView.setText(hotelAccommodationsArrayList.size() + " " + getActivity().getResources().getString(R.string.three_ten_room));
        }
        if (adultCount == 1 && kidCount == 0) {
            adultCountTextView.setText(adultCount + " " + getActivity().getResources().getString(R.string.Adult));
        } else if (adultCount > 1 && kidCount == 0) {
            adultCountTextView.setText(adultCount + " " + getActivity().getResources().getString(R.string.Adults));
        } else if (adultCount == 1 && kidCount == 1) {
            adultCountTextView.setText(adultCount + " " + getActivity().getResources().getString(R.string.Adult) + " , " + kidCount+ " " + getActivity().getResources().getString(R.string.Kid));
        } else if (adultCount > 1 && kidCount > 1) {
            adultCountTextView.setText(adultCount + " " + getActivity().getResources().getString(R.string.Adults) + " , " + kidCount + " " + getActivity().getResources().getString(R.string.Kids));
        } else if (adultCount > 1 && kidCount == 1) {
            adultCountTextView.setText(adultCount + " " + getActivity().getResources().getString(R.string.Adults) + " , " + kidCount + " " + getActivity().getResources().getString(R.string.Kid));
        } else if (adultCount == 1 && kidCount > 1) {
            adultCountTextView.setText(adultCount + " " + getActivity().getResources().getString(R.string.Adult) + " , " + kidCount + " " + getActivity().getResources().getString(R.string.Kids));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        locationArrayList= new ArrayList<>();
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
    public void backPressCalled() {

        int adultCount=0,kidCount=0;
        for (int i = 0; i < hotelAccommodationsArrayList.size(); i++) {
            adultCount = adultCount + hotelAccommodationsArrayList.get(i).getAdultsCount();
            kidCount = kidCount + hotelAccommodationsArrayList.get(i).getKids();
        }
        setKidsAndAdultsText(adultCount, kidCount);
        actionBarRelativeLayout.setVisibility(View.VISIBLE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        addRoomRecyclerView.setVisibility(View.GONE);
        ((HomeActivity) getActivity()).setBackPressListener(null);
    }

    private void setMonth(String inYear, String outYear) {

        SimpleDateFormat simpleDateFormat;

        if (inYear != null) {
            String checkInDate = "";
            simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy-EEE", new Locale(userDTO.getLanguage()));
            checkInDate = simpleDateFormat.format(this.checkInDate);

            checkInMonthTextView.setText(Utilities.splitDate(checkInDate).get(Constant.Date.MONTH.name()) + " " + inYear);
            checkInDayTextView.setText(Utilities.splitDate(checkInDate).get(Constant.Date.DAY.name()));
        }
        if (outYear != null) {
            String checkOutDate = "";
            simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy-EEE", new Locale(userDTO.getLanguage()));
            checkOutDate = simpleDateFormat.format(this.checkOutDate);
            checkOutMonthTextView.setText(Utilities.splitDate(checkOutDate).get(Constant.Date.MONTH.name()) + " " + outYear);
            checkOutDayTextView.setText(Utilities.splitDate(checkOutDate).get(Constant.Date.DAY.name()));
        }
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.searchTextView:

                navigateToHotelSearch();
                break;

            case R.id.checkInDateRelativeLayout:

                showCheckIn();
                break;

            case R.id.checkOutDateRelativeLayout:

                showCheckout();
                break;

            case R.id.roomCountRelativeLayout:

                ((HomeActivity) getActivity()).setBackPressListener(HotelIndexFragmentDailog.this);

                addRoomRecyclerView.setVisibility(View.VISIBLE);
                actionBarRelativeLayout.setVisibility(View.INVISIBLE);

                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                ((DrawerLayout) getActivity().findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                addRoomRecyclerView.setVisibility(View.VISIBLE);
                AddRoomAdapter addRoomAdapter = new AddRoomAdapter(hotelAccommodationsArrayList, getActivity(), HotelIndexFragmentDailog.this, gtmAnalytics, getFragmentManager());
                addRoomRecyclerView.setAdapter(addRoomAdapter);

                break;
            default:
                break;
        }
    }

    private void navigateToHotelSearch() {

        EndlessRecyclerOnScrollListener.current_page = 1;
        SharedPreferenceManager.getInstance(getActivity()).saveIntegerPreference(Constant.MIN_SELECTED, 0);
        SharedPreferenceManager.getInstance(getActivity()).saveIntegerPreference(Constant.MAX_SELECTED, 0);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        engCheckInDate = simpleDateFormat.format(checkInDate);
        engCheckOutDate = simpleDateFormat.format(checkOutDate);




            int days;
            try {
                days = NetworkUtilities.daysBetween(checkOutDate, checkInDate);
                if (days <= 25) {

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

                   if(isDeepLink){

                       String rowId= DatabaseManager.getLatestRowId(UserDTO.getUserDTO().getLanguage());

                       if(rowId!=null){

                           DatabaseManager.updateData(destinationName, engCheckInDate, engCheckOutDate, new Gson().toJson(hotelAccommodationsArrayList), adults, kids, "City", UserDTO.getUserDTO().getLanguage(), Long.toString(cityId),rowId);

                       }
                       else{

                           DatabaseManager.insertData(destinationName, engCheckInDate, engCheckOutDate, new Gson().toJson(hotelAccommodationsArrayList), adults, kids, "City", UserDTO.getUserDTO().getLanguage(), Long.toString(cityId));

                       }

                   }
                   else{

                       destination=DatabaseManager.getLatestData(UserDTO.getUserDTO().getLanguage());




                       String category=destination.getCategory();

                       if(category.equalsIgnoreCase("Hotel")){

                           String rowId= DatabaseManager.getLatestRowId(UserDTO.getUserDTO().getLanguage());
                           if(rowId!=null){

                               DatabaseManager.updateData(hotelName, engCheckInDate, engCheckOutDate, new Gson().toJson(hotelAccommodationsArrayList), adults, kids, "Hotel", UserDTO.getUserDTO().getLanguage(), Long.toString(hotelId),rowId);

                           }
                           else{

                               DatabaseManager.insertData(hotelName, engCheckInDate, engCheckOutDate, new Gson().toJson(hotelAccommodationsArrayList), adults, kids, "Hotel", UserDTO.getUserDTO().getLanguage(), Long.toString(hotelId));

                           }

                       }

                       else if(category.equalsIgnoreCase("City")){

                           String latestRowId="";
                           latestRowId= DatabaseManager.getLatestRowId(UserDTO.getUserDTO().getLanguage());

                           if(!latestRowId.equals("") ){

                               // update database with latest rowId.
                               DatabaseManager.updateData(destinationName, engCheckInDate, engCheckOutDate, new Gson().toJson(hotelAccommodationsArrayList), adults, kids, "City", UserDTO.getUserDTO().getLanguage(), String.valueOf(HotelListingRequest.getHotelListRequest().getCityId()),latestRowId);


                           }

                           else {

                               // insert as new row.
                               DatabaseManager.insertData(destinationName, engCheckInDate, engCheckOutDate, new Gson().toJson(hotelAccommodationsArrayList), adults, kids, "City", UserDTO.getUserDTO().getLanguage(), String.valueOf(HotelListingRequest.getHotelListRequest().getCityId()));

                           }

                       }
                       else{

                           String latestRowId="";
                            latestRowId= DatabaseManager.getLatestRowId(UserDTO.getUserDTO().getLanguage());

                           if(!latestRowId.equals("") ){

                               // update database with latest rowId.
                               DatabaseManager.updateData(UserDTO.getUserDTO().getCityName(), engCheckInDate, engCheckOutDate, new Gson().toJson(hotelAccommodationsArrayList), adults, kids, "Area", UserDTO.getUserDTO().getLanguage(), String.valueOf(HotelListingRequest.getHotelListRequest().getCityId()),latestRowId);


                           }

                           else {

                               // insert as new row.
                               DatabaseManager.insertData(UserDTO.getUserDTO().getCityName(), engCheckInDate, engCheckOutDate, new Gson().toJson(hotelAccommodationsArrayList), adults, kids, "Area", UserDTO.getUserDTO().getLanguage(),String.valueOf(HotelListingRequest.getHotelListRequest().getCityId()));

                           }



                       }

                   }



                    if (NetworkUtilities.isInternet(getActivity())) {

                        UserDTO.getUserDTO().setDestinationName(destinationName);

                            UserDTO.getUserDTO().setHotel(true);
                            getHotel();
                            Constant.dateChangeFlag=true;


                    } else {


                        Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), false, getFragmentManager());
                    }

                } else {

                    Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.app_name), getActivity().getString(R.string.date_difference), false, getFragmentManager());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


    }

    private void getHotel() {

        if (NetworkUtilities.isInternet(getActivity())) {

            showDialog();
            getHotelRoomRateCall(cityId);

        } else {

            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), false, getFragmentManager());
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
        hotelRoomRateRequest.setHotelId(hotelId);
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
        //hotelListingRequest.setCityId(cityId);
        hotelListingRequest.setCurrencyCode(UserDTO.getUserDTO().getCurrency());
        hotelListingRequest.setOccupancy(hotelRoomRateRequest.getOccupancy());

        String request = new Gson().toJson(hotelRoomRateRequest);


        if (NetworkUtilities.isInternet(getActivity())) {
            hotelSearchPresenter.getHotelRoomRate(Constant.API_URL + Constant.HOTELSRATES, request, getActivity());

        } else
            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), false, getFragmentManager());
    }

    // setting hotel rate response.
    @Override
    public void setHotelRateResponse(HotelRatesResponse hotelRateResponse) {

        this.hotelRatesResponse = hotelRateResponse;

        if (hotelRatesResponse.getRoomTypes() != null && hotelRatesResponse.getRoomTypes().size() != 0) {

            hotelDetailCall(cityId);
        }
        else {

            dismissDialog();

            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.app_name),"Please change Dates and Occupancy", false, getFragmentManager());

        }
    }

//    call to hotel detail. This methods navigates control to hotelDetailsTab fragment.
    private void hotelDetailCall(long cityId) {

        if (NetworkUtilities.isInternet(getActivity())) {
            String request = new Gson().toJson(new HotelDetailRequest(cityId, hotelId, UserDTO.getUserDTO().getLanguage()));
            hotelSearchPresenter.getHotelDetails(Constant.API_URL + Constant.HOTELDETAIL, request, getActivity());

        } else {
            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), false, getFragmentManager());
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
        }
        else
            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), false, getFragmentManager());
    }

    @Override
    public void setHotelListData(HotelListingInfoResponse hotelListingInfoResponse) {

        dismissDialog();

    }

    @Override
    public void showDialog() {

        if(spinningDialog!= null && !spinningDialog.isShowing()){

            spinningDialog.show();
        }
    }



    private void setDrawer() {

        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        languageLinearLayout  = (LinearLayout) drawerLayout.findViewById(R.id.languageLinearLayout);
        View view = drawerLayout.findViewById(R.id.topBarView);
        if (languageLinearLayout != null) {
            view.setVisibility(View.VISIBLE);
            languageLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    private void setFragmentTransaction(Fragment fragment){

        try {
            fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.addToBackStack("tabs");
            fragmentTransaction.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}