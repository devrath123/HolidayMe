package com.holidayme.hoteldetail_mvp;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.holidayme.AppInterface.IRoomItemClickListener;
import com.holidayme.AppInterface.ImageGalleryOnItemClick;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.ImageZoomInActivity;
import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.Utilities;
import com.holidayme.adapter.GalleryImageAdapter;
import com.holidayme.adapter.HotelRoomListAdapter;
import com.holidayme.common.CustomProgressDialog;
import com.holidayme.common.Log;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.data.UserDTO;
import com.holidayme.data.ZoomInImagesDTO;
import com.holidayme.fragments.BaseFragment;

import com.holidayme.fragments.BookingDetailsFragment;
import com.holidayme.fragments.HotelBookingConfirmationFragment;
import com.holidayme.gtm.GTMAnalytics;
import com.holidayme.request.GenerateTemporaryHotelBookingRequest;
import com.holidayme.request.HotelListingRequest;
import com.holidayme.request.HotelRoomDetailsRequest;
import com.holidayme.response.BookingDetailsResponse;
import com.holidayme.response.CurrentLocationDetail;
import com.holidayme.response.GenerateTemporaryHotelBookingResponse;
import com.holidayme.response.HotelBookConfirmationResponse;
import com.holidayme.response.HotelDetailInfoResponse;
import com.holidayme.response.HotelRatesResponse;
import com.holidayme.response.RoomDetailsResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by santosh.patar on 31-08-2015.
 */
public class HotelRoomFragment extends BaseFragment implements ImageGalleryOnItemClick, IHotelDetailView.IHotelDetailRoomView {

    private View rootView, roomListImageHeaderView;
    private ImageView leftArrowImageView, rightArrowImageView;
    private ViewPager galleryViewPager;
    private int selectedImagePosition;
    private static ArrayList<ZoomInImagesDTO> zoomInImagesDTOArrayList;
    private CurrentLocationDetail currentLocationDetail = null;
    private static HotelDetailInfoResponse hotelDetailInfoResponse;
    private static HotelRatesResponse hotelRatesResponse;
    private ListView roomListListView;
    private Dialog spinningDialog;
    private HotelRoomPresenter hotelDetailRoomPresenter;
    private FragmentTransaction fragmentTransaction;
    RelativeLayout galleryRelativeLayout;

    static HotelRoomFragment newInstance(HotelDetailInfoResponse hotelDetailInfoResponse, HotelRatesResponse hotelRatesResponse) {
        HotelRoomFragment.hotelDetailInfoResponse = hotelDetailInfoResponse;
        HotelRoomFragment.hotelRatesResponse = hotelRatesResponse;
        HotelRoomFragment hotelRoomFragment = new HotelRoomFragment();

        zoomInImagesDTOArrayList = new ArrayList<>();
        addImagesToZoomInDTO();

        return hotelRoomFragment;
    }

// add  hotel images  to zoomInDto for image zooming functionality

    private static void addImagesToZoomInDTO() {
        if (!zoomInImagesDTOArrayList.isEmpty())
            zoomInImagesDTOArrayList.clear();
        if(hotelDetailInfoResponse.getMoreInfo()!=null&&hotelDetailInfoResponse.getMoreInfo().getDImg()!=null)
        for (int i = 0; i < hotelDetailInfoResponse.getMoreInfo().getDImg().size(); i++) {
            ZoomInImagesDTO zoomInImagesDTO = new ZoomInImagesDTO();
            zoomInImagesDTO.setCaption(hotelDetailInfoResponse.getMoreInfo().getDImg().get(i).getCapt());
            zoomInImagesDTO.setImageId(hotelDetailInfoResponse.getMoreInfo().getDImg().get(i).getId());
            zoomInImagesDTO.setUrl(hotelDetailInfoResponse.getMoreInfo().getDImg().get(i).getDUrl());
            zoomInImagesDTOArrayList.add(zoomInImagesDTO);
        }
    }


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

        LocalBroadcastManager bookingConfirmationBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter bookingConfirmationIntentFilter = new IntentFilter(Constant.MY_ACTION_CONFIRMATION);

        bookingConfirmationBroadcastManager.registerReceiver(conformationBroadcastReceiver, bookingConfirmationIntentFilter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.hotel_room_info_fragment, container, false);

        hotelDetailRoomPresenter = new HotelRoomPresenter(this);

        roomListImageHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.hotel_room_info_header, null);

        spinningDialog = CustomProgressDialog.showProgressDialog(getActivity());

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();

        selectedImagePosition = Integer.parseInt(Utilities.gettSharedPreference(getActivity(), "pos"));
        galleryViewPager.setCurrentItem(selectedImagePosition);

        if (hotelDetailInfoResponse.getMoreInfo().getDImg()!= null && hotelDetailInfoResponse.getMoreInfo().getDImg().size() != 0)
            new Utilities().setArrowPosition(leftArrowImageView,rightArrowImageView,hotelDetailInfoResponse.getMoreInfo().getDImg().size(),selectedImagePosition,getResources());

    }

    /**
     * Booking confirmation call
     */
    private final BroadcastReceiver conformationBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getExtras();

            if (bundle != null)
                hotelDetailRoomPresenter.getBookedHotelDetail(Constant.HOTEL_BOOKING_CONFORMATION_ENDPOINT + Constant.GETHOTELBOOKDETAILMETHOD + "?bookingId=" + bundle.getString("bookingid") + "&bookingPropertyId=" + bundle.getString("propertyBookingId") + "&currencyCode=" + UserDTO.getUserDTO().getCurrency(), getActivity(), getFragmentManager());

        }
    };

    @Override
    public void setGetBookedHotelDetailResponse(HotelBookConfirmationResponse getBookedHotelDetailResponse) {

        switchToBookingConfirmationPage(getBookedHotelDetailResponse);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(conformationBroadcastReceiver);                           //always unregister the receiver when it’s no longer needed to save resources

    }


    @Override
    public void initUIElements() {

        GTMAnalytics gtmAnalytics = AppController.getInstance().getGTMAnalytics(getActivity());
        gtmAnalytics.setScreenName("HotelRoomDetail Screen - " + hotelDetailInfoResponse.getTtl());
        leftArrowImageView = (ImageView) roomListImageHeaderView.findViewById(R.id.leftArrowImageView);
        rightArrowImageView = (ImageView) roomListImageHeaderView.findViewById(R.id.rightArrowImageView);
        roomListListView = (ListView) rootView.findViewById(R.id.roomListListView);
        galleryRelativeLayout=(RelativeLayout) rootView.findViewById(R.id.galleryRelativeLayout);
        roomListListView.setOnScrollListener(onScrollListener);
        roomListListView.addHeaderView(roomListImageHeaderView);

        setRoomDetailInfoAdapter();

        try {
            addImageToHotelDetailView();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onItemSelected() {
        Intent intent = new Intent(getActivity(), ImageZoomInActivity.class);
        intent.putExtra("ImagesDTO", zoomInImagesDTOArrayList);
        intent.putExtra("ImagePos", selectedImagePosition);
        startActivity(intent);
    }


// get temporary booking Id call

    private void getTempBookingId(String RoomRateCode, boolean IsPayAtHotel) {
        GenerateTemporaryHotelBookingRequest generateTemporaryHotelBookingRequest = new GenerateTemporaryHotelBookingRequest();
        generateTemporaryHotelBookingRequest.setCheckInDate(HotelListingRequest.getHotelListRequest().getCheckInDate());
        generateTemporaryHotelBookingRequest.setCheckOutDate(HotelListingRequest.getHotelListRequest().getCheckOutDate());
        generateTemporaryHotelBookingRequest.setLanguageCode(UserDTO.getUserDTO().getLanguage());
        generateTemporaryHotelBookingRequest.setCityId(HotelListingRequest.getHotelListRequest().getCityId());
        generateTemporaryHotelBookingRequest.setHotelId(hotelDetailInfoResponse.getId());
        generateTemporaryHotelBookingRequest.setRoomRateCode(RoomRateCode);
        generateTemporaryHotelBookingRequest.setPayAtHotel(IsPayAtHotel);
        generateTemporaryHotelBookingRequest.setCurrencyCode(UserDTO.getUserDTO().getCurrency());
        generateTemporaryHotelBookingRequest.setOccupancy(HotelListingRequest.getHotelListRequest().getOccupancy());


        if (NetworkUtilities.isInternet(getActivity()))
            hotelDetailRoomPresenter.generateTemporaryBookingId(Constant.API_URL + Constant.GENERATETEMPID, new GsonBuilder().serializeNulls().create().toJson(generateTemporaryHotelBookingRequest), getActivity(), getFragmentManager());
        else
            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), false, getFragmentManager());

    }

    @Override
    public void setGetTemporaryBookingIdResponse(GenerateTemporaryHotelBookingResponse getTemporaryBookingIdResponse) {
        getBookingDetail(getTemporaryBookingIdResponse.getTempBookingId());
    }


    // get Booking detail call

    private void getBookingDetail(String temp_booking_Id) {

        if (NetworkUtilities.isInternet(getActivity()))
            hotelDetailRoomPresenter.getBookingDetail(Constant.HOTEL_BOOKING_CONFORMATION_ENDPOINT + Constant.GETBOOKINGDETAIL + "?tempBookingId=" + temp_booking_Id + "&currencyCode=" + UserDTO.getUserDTO().getCurrency() + "&ip=" + NetworkUtilities.getIp(), getActivity(), getFragmentManager());
        else
            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), false, getFragmentManager());

    }


    @Override
    public void setGetBookingDetailResponse(BookingDetailsResponse getBookingDetailResponse) {

        if (NetworkUtilities.isInternet(getActivity()))
            getCurrentLocationDetailByIp(getBookingDetailResponse);
        else
            switchToBookingPage(getBookingDetailResponse);

    }

// Room detail call

    private void roomInfoCall(String room_id,int position) {
        HotelRoomDetailsRequest hotelDetailRequest = new HotelRoomDetailsRequest();
        hotelDetailRequest.setCheckInDate(HotelListingRequest.getHotelListRequest().getCheckInDate());
        hotelDetailRequest.setCheckOutDate(HotelListingRequest.getHotelListRequest().getCheckOutDate());
        hotelDetailRequest.setCityId(HotelListingRequest.getHotelListRequest().getCityId());
        hotelDetailRequest.setHotelId(hotelDetailInfoResponse.getId());
        hotelDetailRequest.setLanguageCode(UserDTO.getUserDTO().getLanguage());
        hotelDetailRequest.setCurrencyCode(UserDTO.getUserDTO().getCurrency());
        hotelDetailRequest.setRoomCode(room_id);
        hotelDetailRequest.setOccupancy(HotelListingRequest.getHotelListRequest().getOccupancy());
        Gson gson = new GsonBuilder().serializeNulls().create();

        if (NetworkUtilities.isInternet(getActivity()))
            hotelDetailRoomPresenter.getRoomDetailInfo(Constant.API_URL + Constant.ROOMDETAILS, gson.toJson(hotelDetailRequest), getActivity(), getFragmentManager(),position);
        else
            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), false, getFragmentManager());


    }

    @Override
    public void setRoomDetailInfo(RoomDetailsResponse roomDetailInfo,int position) {
        switchToRoomDetailInfoPage(roomDetailInfo,position);
    }

     // go to room info page while clicking on room section
    @Override
    public void switchToRoomDetailInfoPage(RoomDetailsResponse roomDetailInfo,int position) {
        Fragment roomDetailsTabFragment = new RoomDetailsTabFragment();
        Bundle roomDetailsTabBundle = new Bundle();
        roomDetailsTabBundle.putParcelable("HotelRoomDetailResponse", roomDetailInfo);
        roomDetailsTabBundle.putLong("Price", Math.round(hotelRatesResponse.getRoomTypes().get(position).getPrice().getTotal()));
        roomDetailsTabBundle.putString("priceLable", hotelRatesResponse.getPriceLabel());
        roomDetailsTabBundle.putBoolean("IspayatHotel", hotelRatesResponse.getRoomTypes().get(position).isPayAtHotel());
        roomDetailsTabBundle.putString("RoomRateCode", hotelRatesResponse.getRoomTypes().get(position).getRoomRateCode());
        roomDetailsTabBundle.putLong("HotelId", hotelDetailInfoResponse.getId());
        roomDetailsTabBundle.putString("roomTitle", hotelRatesResponse.getRoomTypes().get(position).getTtl());
        if (hotelRatesResponse.getRoomTypes().get(position).getIncludes().toString().replace("[", "").replace("]", "") != null)
            roomDetailsTabBundle.putString("includes", String.valueOf(hotelRatesResponse.getRoomTypes().get(position).getIncludes().toString().replace("[", "").replace("]", "")));

        roomDetailsTabFragment.setArguments(roomDetailsTabBundle);

        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_body, roomDetailsTabFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();

    }


    @Override
    public void setGalleryAdapter() {
        if(hotelDetailInfoResponse.getMoreInfo()!=null&&hotelDetailInfoResponse.getMoreInfo().getDImg()!=null) {
            GalleryImageAdapter galleryImageAdapter = new GalleryImageAdapter(HotelRoomFragment.this, getActivity(), hotelDetailInfoResponse.getMoreInfo().getDImg());
            galleryViewPager.setAdapter(galleryImageAdapter);
            galleryViewPager.setVisibility(View.VISIBLE);
            rightArrowImageView.setVisibility(View.VISIBLE);
            leftArrowImageView.setVisibility(View.VISIBLE);
        }else{
            galleryViewPager.setVisibility(View.GONE);
            rightArrowImageView.setVisibility(View.GONE);
            leftArrowImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setRoomDetailInfoAdapter() {


        IRoomItemClickListener iRoomItemClickListener = new IRoomItemClickListener() {
            @Override
            public void bookingUrl(int position) {
                getTempBookingId(hotelRatesResponse.getRoomTypes().get(position).getRoomRateCode(), hotelRatesResponse.getRoomTypes().get(position).isPayAtHotel());

            }

            @Override
            public void viewRoomDetail(int position) {

                roomInfoCall(hotelRatesResponse.getRoomTypes().get(position).getId(),position);

            }


        };

        HotelRoomListAdapter hotelRoomListAdapter = new HotelRoomListAdapter(hotelRatesResponse, getActivity(), iRoomItemClickListener, getActivity().getSupportFragmentManager());
        roomListListView.setAdapter(hotelRoomListAdapter);
    }

//  parallax implementation
    private AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            View firstChild = roomListListView.getChildAt(0);
            int topY = 0;
            if (firstChild != null)
                topY = firstChild.getTop();

            roomListImageHeaderView.setY(topY * 0.5f);

        }
    };

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
                } catch (MalformedURLException e) {
                    Log.e("error on fetching detail from ip", "Malformed URL: " + e.getMessage());
                } catch (IOException e) {
                    Log.e("error on fetching detail from ip", "I/O Error: " + e.getMessage());
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
        if (spinningDialog != null && spinningDialog.isShowing())
            spinningDialog.dismiss();


    }

    @Override
    public void showDialog() {

      if(spinningDialog != null&& !spinningDialog.isShowing()) {
          spinningDialog.show();
      }
    }


    @Override
    public void switchToBookingPage(BookingDetailsResponse bookingDetailsResponse) {
        dismissDialog();
        Fragment bookingFragment_new = new BookingDetailsFragment();
        Bundle bookingFragmentBundle = new Bundle();
        bookingFragmentBundle.putParcelable("BookingDetailResponse", bookingDetailsResponse);
        bookingFragmentBundle.putLong("hotelid", hotelDetailInfoResponse.getId());
        if (currentLocationDetail != null)
            bookingFragmentBundle.putParcelable("location", currentLocationDetail);

        bookingFragment_new.setArguments(bookingFragmentBundle);
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_body, bookingFragment_new);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();

    }

    @Override
    public void switchToBookingConfirmationPage(HotelBookConfirmationResponse hotelBookConfirmationResponse) {

        try {
            Fragment bookingConfirmationFragment = new HotelBookingConfirmationFragment();
            Bundle bookingConfermationFragmentBundle = new Bundle();
            bookingConfermationFragmentBundle.putParcelable("hotelBookConfirmationResponse", hotelBookConfirmationResponse);
            bookingConfermationFragmentBundle.putParcelable("RoomRate", hotelBookConfirmationResponse);
            bookingConfirmationFragment.setArguments(bookingConfermationFragmentBundle);
            fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container_body, bookingConfirmationFragment);
            fragmentTransaction.commit();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    // add image to Image gallery
    @Override
    public void addImageToHotelDetailView() {

        galleryViewPager = (ViewPager) roomListImageHeaderView.findViewById(R.id.galleryViewPager);
        setGalleryAdapter();

        selectedImagePosition = Integer.parseInt(Utilities.gettSharedPreference(getActivity(), "pos"));
        if (hotelDetailInfoResponse.getMoreInfo().getDImg().size() > 0)
            galleryViewPager.setCurrentItem(selectedImagePosition, false);


        if (hotelDetailInfoResponse.getMoreInfo().getDImg().size() == 1) {
            rightArrowImageView.setVisibility(View.GONE);
            leftArrowImageView.setVisibility(View.GONE);

        } else {
            if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE))
                rightArrowImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.icn_arrow_left, null));
            else
                rightArrowImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.icn_arrow_right, null));

        }

        UIFunctionality();


    }


    private void UIFunctionality(){

        leftArrowImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (selectedImagePosition > 0)
                    --selectedImagePosition;

                galleryViewPager.setCurrentItem(selectedImagePosition, false);
            }
        });

        rightArrowImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (selectedImagePosition < hotelDetailInfoResponse.getMoreInfo().getDImg().size() - 1)
                    ++selectedImagePosition;

                else if (selectedImagePosition == hotelDetailInfoResponse.getMoreInfo().getDImg().size() - 1)
                    selectedImagePosition = 0;


                galleryViewPager.setCurrentItem(selectedImagePosition, false);

            }
        });

        galleryViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedImagePosition = position;

                if (hotelDetailInfoResponse.getMoreInfo().getDImg()!= null && hotelDetailInfoResponse.getMoreInfo().getDImg().size() != 0)
                new Utilities().setArrowPosition(leftArrowImageView,rightArrowImageView,hotelDetailInfoResponse.getMoreInfo().getDImg().size(),selectedImagePosition,getResources());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
