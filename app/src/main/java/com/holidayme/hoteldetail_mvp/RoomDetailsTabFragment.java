package com.holidayme.hoteldetail_mvp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.util.Utilities;
import com.holidayme.common.CustomProgressDialog;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.data.UserDTO;
import com.holidayme.fragments.BaseFragment;

import com.holidayme.fragments.BookingDetailsFragment;
import com.holidayme.fragments.HotelBookingConfirmationFragment;
import com.holidayme.request.GenerateTemporaryHotelBookingRequest;
import com.holidayme.request.HotelListingRequest;
import com.holidayme.response.BookingDetailsResponse;
import com.holidayme.response.CurrentLocationDetail;
import com.holidayme.response.GenerateTemporaryHotelBookingResponse;
import com.holidayme.response.HotelBookConfirmationResponse;
import com.holidayme.response.RoomDetailsResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import java.util.Locale;


public class RoomDetailsTabFragment extends BaseFragment implements IHotelDetailView.IRoomDetailTabView {
    private View rootView;
    private ViewPager roomDetailViewPager;
    private RoomDetailsResponse roomDetailsResponse;
    private String roomIncludes, roomTitle, RoomRateCode, priceLabel;
    private boolean isPayAtHotel;
    private Long hotelId, price;
    private CurrentLocationDetail currentLocationDetail;
    private RoomDetailPresenter roomDetailPresenter;
    private FragmentTransaction fragmentTransaction;
    private TabLayout roomDetailTabLayout;

    private Dialog spinningDialog;

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

        LocalBroadcastManager bookingConfirmationBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter bookingConfirmationIntentFilter = new IntentFilter(Constant.MY_ACTION_CONFIRMATION);
        bookingConfirmationBroadcastManager.registerReceiver(conformationBroadcastReceiver, bookingConfirmationIntentFilter);
        Bundle bundle = this.getArguments();


        if (bundle != null) {
            roomDetailsResponse = bundle.getParcelable("HotelRoomDetailResponse");
            roomIncludes = bundle.getString("includes");
            roomTitle = bundle.getString("roomTitle");
            hotelId = bundle.getLong("HotelId");
            RoomRateCode = bundle.getString("RoomRateCode");
            isPayAtHotel = bundle.getBoolean("IspayatHotel");
            priceLabel = bundle.getString("priceLable");
            price = bundle.getLong("Price");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.room_info_main_page, container, false);
        setHasOptionsMenu(true);

        roomDetailPresenter = new RoomDetailPresenter(this);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


        return rootView;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initUIElements() {
        roomDetailTabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        spinningDialog = CustomProgressDialog.showProgressDialog(getActivity());

        ((TextView) rootView.findViewById(R.id.priceTextView)).setText(UserDTO.getUserDTO().getCurrency() + " " +price);
        ((TextView) rootView.findViewById(R.id.priceLableTextView)).setText(priceLabel);
        rootView.findViewById(R.id.txt_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTempBookingId(RoomRateCode, isPayAtHotel);
            }
        });
        ((TextView) rootView.findViewById(R.id.toolbarTitleTextView)).setText(roomTitle);
        rootView.findViewById(R.id.toolbarBackImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        if (roomDetailsResponse.getRoomAmenities() != null) {
            roomDetailTabLayout.addTab(roomDetailTabLayout.newTab().setText(getActivity().getResources().getString(R.string.room_info)));
            roomDetailTabLayout.addTab(roomDetailTabLayout.newTab().setText(getActivity().getResources().getString(R.string.Amenities)));
        } else {
            roomDetailTabLayout.addTab(roomDetailTabLayout.newTab().setText(getActivity().getResources().getString(R.string.room_info)));
        }

        roomDetailTabLayoutImplementation();


    }


    private void roomDetailTabLayoutImplementation(){
        //for view between tab
        roomDetailViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        roomDetailTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        roomDetailViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(roomDetailTabLayout));

        for (int i = 0; i < roomDetailTabLayout.getTabCount(); i++) {
            if (roomDetailTabLayout.getTabCount() > 1) {
                TabLayout.Tab tab = roomDetailTabLayout.getTabAt(i);
                RelativeLayout tabRelativeLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.tab_layout, roomDetailTabLayout, false);
                TextView tabTextView = (TextView) tabRelativeLayout.findViewById(R.id.tab_title);
                tabTextView.setText(tab.getText());
                tab.setCustomView(tabRelativeLayout);
            } else {
                TabLayout.Tab tab = roomDetailTabLayout.getTabAt(i);
                RelativeLayout tabRelativeLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.tab_layout, roomDetailTabLayout, false);
                TextView tabTextView = (TextView) tabRelativeLayout.findViewById(R.id.tab_title);
                tabRelativeLayout.findViewById(R.id.view_between_tab).setVisibility(View.INVISIBLE);
                tabTextView.setText(tab.getText());
                tab.setCustomView(tabRelativeLayout);
            }


        }


        roomDetailViewPager.setAdapter(new RoomDetailTabAdapter(getChildFragmentManager(), roomDetailTabLayout.getTabCount()));
        roomDetailTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                roomDetailViewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
// set fragment to tab layout

    private class RoomDetailTabAdapter extends FragmentPagerAdapter {
        int numOfTabs;

        RoomDetailTabAdapter(FragmentManager fm, int numOfTabs) {
            super(fm);
            this.numOfTabs = numOfTabs;

        }

        public Fragment getItem(int position) {
            if (roomDetailsResponse.getRoomAmenities() != null) {

                switch (position) {

                    case 0:

                        Utilities.setSharedPreference(getActivity(), "pos", "0");

                        return RoomInfoFragment.newInstance(roomDetailsResponse, roomIncludes);

                    case 1:

                        return RoomInfoAmenityFragment.newInstance(roomDetailsResponse);

                    default:
                        return null;
                }
            } else {
                switch (position) {

                    case 0:

                        Utilities.setSharedPreference(getActivity(), "pos", "0");

                        return RoomInfoFragment.newInstance(roomDetailsResponse, roomIncludes);


                    default:
                        return null;
                }
            }
        }

        @Override
        public int getCount() {
            return numOfTabs;
        }
    }


    private final BroadcastReceiver conformationBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getExtras();
            //Booking conformation call
            if (bundle != null)
                roomDetailPresenter.getBookedHotelDetail(Constant.HOTEL_BOOKING_CONFORMATION_ENDPOINT + Constant.GETHOTELBOOKDETAILMETHOD + "?bookingId=" + bundle.getString("bookingid") + "&bookingPropertyId=" + bundle.getString("propertyBookingId") + "&currencyCode=" + UserDTO.getUserDTO().getCurrency(), getActivity(), getFragmentManager());

        }
    };

    @Override
    public void setGetBookedHotelDetailResponse(HotelBookConfirmationResponse getBookedHotelDetailResponse) {
        switchToBookingConfirmationPage(getBookedHotelDetailResponse);
    }


// generate temporary booking Id

    private void getTempBookingId(String RoomRateCode, boolean IsPayAtHotel) {

        GenerateTemporaryHotelBookingRequest generateTemporaryHotelBookingRequest = new GenerateTemporaryHotelBookingRequest();
        generateTemporaryHotelBookingRequest.setCheckInDate(HotelListingRequest.getHotelListRequest().getCheckInDate());
        generateTemporaryHotelBookingRequest.setCheckOutDate(HotelListingRequest.getHotelListRequest().getCheckOutDate());
        generateTemporaryHotelBookingRequest.setLanguageCode(UserDTO.getUserDTO().getLanguage());
        generateTemporaryHotelBookingRequest.setCityId(HotelListingRequest.getHotelListRequest().getCityId());
        generateTemporaryHotelBookingRequest.setHotelId(hotelId);
        generateTemporaryHotelBookingRequest.setRoomRateCode(RoomRateCode);
        generateTemporaryHotelBookingRequest.setPayAtHotel(IsPayAtHotel);
        generateTemporaryHotelBookingRequest.setCurrencyCode(UserDTO.getUserDTO().getCurrency());
        generateTemporaryHotelBookingRequest.setOccupancy(HotelListingRequest.getHotelListRequest().getOccupancy());

        if (NetworkUtilities.isInternet(getActivity())) {
            roomDetailPresenter.generateTemporaryBookingId(Constant.API_URL + Constant.GENERATETEMPID, new GsonBuilder().serializeNulls().create().toJson(generateTemporaryHotelBookingRequest), getActivity(), getFragmentManager());
        } else
            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), false, getFragmentManager());

    }

    @Override
    public void setGetTemporaryBookingIdResponse(GenerateTemporaryHotelBookingResponse getTemporaryBookingIdResponse) {
        getBookingDetail(getTemporaryBookingIdResponse.getTempBookingId());
    }


    // get Booking detail call

    private void getBookingDetail(String temp_booking_Id) {
        if (NetworkUtilities.isInternet(getActivity()))
            roomDetailPresenter.getBookingDetail(Constant.HOTEL_BOOKING_CONFORMATION_ENDPOINT + Constant.GETBOOKINGDETAIL + "?tempBookingId=" + temp_booking_Id + "&currencyCode=" + UserDTO.getUserDTO().getCurrency() + "&ip=" + NetworkUtilities.getIp(), getActivity(), getFragmentManager());
        else
            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), false, getFragmentManager());

    }

    @Override
    public void setGetBookingDetailResponse(BookingDetailsResponse getBookingDetailResponse) {
        if (NetworkUtilities.isInternet(getActivity())) {
            getCurrentLocationDetailByIp(getBookingDetailResponse);
        } else {
           switchToBookingPage(getBookingDetailResponse);
        }

    }




    // get current location of user by IP
    private void getCurrentLocationDetailByIp(final BookingDetailsResponse bookingDetailsResponse) {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                showDialog();
            }

            @Override
            protected String doInBackground(final Void... params) {
                String location = "";
                try {
                    URL url = new URL(Constant.GET_MY_DETAIL_FROM_IP);
                    // read text returned by server
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                    while ((location = bufferedReader.readLine()) != null) {
                        return location;
                    }
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return location;
            }

            @Override
            protected void onPostExecute(String location) {
                dismissDialog();
                if (!TextUtils.equals(location, "")) {
                    currentLocationDetail = new Gson().fromJson(location, CurrentLocationDetail.class);
                }
                switchToBookingPage(bookingDetailsResponse);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    @Override
    public void dismissDialog() {
        if(spinningDialog!=null&&spinningDialog.isShowing())
            spinningDialog.dismiss();
    }

    @Override
    public void showDialog() {
        if(spinningDialog!=null&&!spinningDialog.isShowing())
          spinningDialog.show();
    }



    @Override
    public void switchToBookingPage(BookingDetailsResponse bookingDetailsResponse) {

        try {
            Fragment bookingFragment_new = new BookingDetailsFragment();
            Bundle bookingFragmentBundle = new Bundle();
            bookingFragmentBundle.putParcelable("BookingDetailResponse", bookingDetailsResponse);
            bookingFragmentBundle.putLong("hotelid", hotelId);
            if (currentLocationDetail != null)
                bookingFragmentBundle.putParcelable("location", currentLocationDetail);

            bookingFragment_new.setArguments(bookingFragmentBundle);
            fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container_body, bookingFragment_new);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void switchToBookingConfirmationPage(HotelBookConfirmationResponse hotelBookConfirmationResponse) {

        try {
            Fragment bookingConfirmationFragment = new HotelBookingConfirmationFragment();
            Bundle bookingConfirmationFragmentBundle = new Bundle();
            bookingConfirmationFragmentBundle.putParcelable("hotelBookConfirmationResponse", hotelBookConfirmationResponse);
            bookingConfirmationFragmentBundle.putParcelable("RoomRate", hotelBookConfirmationResponse);
            bookingConfirmationFragment.setArguments(bookingConfirmationFragmentBundle);
            fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container_body, bookingConfirmationFragment);
            fragmentTransaction.commit();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}