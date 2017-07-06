package com.holidayme.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.MyInfoWindowAdapter;
import com.holidayme.activities.util.Utilities;
import com.holidayme.common.CustomProgressDialog;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.data.HotelsDto;
import com.holidayme.data.UserDTO;
import com.holidayme.gtm.GTMAnalytics;
import com.holidayme.hoteldetail_mvp.HotelDetailsTabFragment;
import com.holidayme.hotellistinmap_mvp.HotelListingMapPresenter;
import com.holidayme.hotellistinmap_mvp.IHotelMapLandingView;
import com.holidayme.request.HotelDetailRequest;
import com.holidayme.request.HotelListingRequest;
import com.holidayme.request.HotelRoomRateRequest;
import com.holidayme.response.HotelDetailInfoResponse;
import com.holidayme.response.HotelListingInfoResponse;
import com.holidayme.response.HotelRatesResponse;
import com.holidayme.response.TripAdviserDataResponse;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by santosh.patar on 27-08-2015.
 */
public class MapViewFragment extends BaseFragment implements OnMapReadyCallback, IHotelMapLandingView {

    private View rootView;
    private HotelListingInfoResponse hotelListingInfoResponse;
    private TripAdviserDataResponse tripAdviserDataResponse;
    private HotelRatesResponse hotelRatesResponse;
    private GoogleMap googleMap = null;
    private Context context;
    private Marker marker;
    private LatLngBounds.Builder builder;
    private String EVENT_CATEGORY = "Map";
    private Dialog spinningDialog;
    private HotelDetailInfoResponse mHotelDetailResponse;
    private GTMAnalytics gtmAnalytics;
    private Long HotelID;
    private AppEventsLogger appEventsLogger;
    private HotelListingMapPresenter hotelListingMapPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        gtmAnalytics = AppController.getInstance().getGTMAnalytics(getActivity());
        gtmAnalytics.setScreenName("Map");
        appEventsLogger = AppEventsLogger.newLogger(getActivity());
        try {

            rootView = inflater.inflate(R.layout.mapview_fragment, container, false);
            HotelListingFragment.isONAttach = false;
            HotelListingFragment.scrollToTop = false;
            hotelListingMapPresenter = new HotelListingMapPresenter(this);
            context = getActivity();
            spinningDialog = CustomProgressDialog.showProgressDialog(context);
            setHasOptionsMenu(false);
            initializeWidgets();

            return rootView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Bundle bundle = this.getArguments();
        if (bundle != null) {

            hotelListingInfoResponse = bundle.getParcelable("GetHotelMapResponse");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (hotelListingInfoResponse.getHotels() != null) {
            showMarker(hotelListingInfoResponse.getHotels());
            this.googleMap.setInfoWindowAdapter(new MyInfoWindowAdapter(getActivity(), hotelListingInfoResponse.getPriceLabel(), hotelListingInfoResponse.getHotels(), gtmAnalytics));

            this.googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {

                    int snippetPosition = Integer.parseInt(marker.getSnippet());

                    gtmAnalytics.sendEvent("Map", "Hotel detail - " + hotelListingInfoResponse.getHotels().get(snippetPosition).getTtl(), "load hotel detail");
                    Utilities.pushFacebookEvent(appEventsLogger, EVENT_CATEGORY, "Hotel info condensed view", "Hotel detail");

                    HotelID = hotelListingInfoResponse.getHotels().get(snippetPosition).getId();
                    hotelRoomRateCall(HotelID);
                }
            });

            Utilities.pushFacebookEvent(appEventsLogger, EVENT_CATEGORY, "hotel pin", "Hotel info condensed view");

        } else {

            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.app_name), getActivity().getString(R.string.common_error_msg), false, getFragmentManager());

        }
    }

    private void initializeWidgets() {


        if (googleMap == null) {

            SupportMapFragment supportMapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment));
            supportMapFragment.getMapAsync(this);

        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
        }


        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {

                LatLngBounds bounds = builder.build();
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 0);
                googleMap.moveCamera(cameraUpdate);
                googleMap.animateCamera(cameraUpdate);

            }
        });
    }


    private void showMarker(ArrayList<HotelsDto> hotelsArrayList) {

        googleMap.clear();

        builder = new LatLngBounds.Builder();
        List<Marker> markerArrayList = new ArrayList<>();
        for (int i = 0; i < hotelsArrayList.size(); i++) {

            LatLng latLng = new LatLng(hotelsArrayList.get(i).getBasicInfo().getLat(), hotelsArrayList.get(i).getBasicInfo().getLong());
            BitmapDescriptor bitmapMarker;

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            googleMap.animateCamera(CameraUpdateFactory.zoomIn());
            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
            if (hotelsArrayList.size() == 1) {
                bitmapMarker = (BitmapDescriptorFactory.fromResource(R.drawable.map_pin));
            } else {
                bitmapMarker = (BitmapDescriptorFactory.fromResource(R.drawable.pin));
            }

            String title;

            try {

                title = hotelsArrayList.get(i).getTtl();
                marker = googleMap.addMarker(new MarkerOptions().position(latLng).icon(bitmapMarker).title(title).snippet(String.valueOf(i)));

            } catch (Exception e) {
                e.printStackTrace();
            }

            markerArrayList.add(marker);
            builder.include(marker.getPosition());
        }

    }

    // navigates to hotelDetailsTab fragment.
    private void newFragmentCall() {

        Fragment fragment = new HotelDetailsTabFragment();

        Bundle bundle = new Bundle();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        bundle.putParcelable("HotelDetails", mHotelDetailResponse);
        bundle.putParcelable("TripAdvisorDeatil", tripAdviserDataResponse);
        bundle.putParcelable("HotelRoomRate", hotelRatesResponse);

        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack("MapViewFragment");
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void hotelDetailCall(Long hotelId) {

        HotelDetailRequest hotelDetailRequest = new HotelDetailRequest();
        hotelDetailRequest.setHotelId(hotelId);
        hotelDetailRequest.setCityId(HotelListingRequest.getHotelListRequest().getCityId());
        hotelDetailRequest.setLanguageCode(UserDTO.getUserDTO().getLanguage());

        String request = new Gson().toJson(hotelDetailRequest);

        if (NetworkUtilities.isInternet(context)) {

            showDialog();
            hotelListingMapPresenter.getHotelDetails(Constant.API_URL + Constant.HOTELDETAIL, request, context);

        } else {

            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), false, getFragmentManager());
        }
    }

    private void hotelRoomRateCall(long hotelId) {

        HotelRoomRateRequest hotelDetailRequest = new HotelRoomRateRequest();
        hotelDetailRequest.setCheckInDate(HotelListingRequest.getHotelListRequest().getCheckInDate());
        hotelDetailRequest.setCheckOutDate(HotelListingRequest.getHotelListRequest().getCheckOutDate());
        hotelDetailRequest.setLanguageCode(UserDTO.getUserDTO().getLanguage());
        hotelDetailRequest.setCityId(HotelListingRequest.getHotelListRequest().getCityId());
        hotelDetailRequest.setHotelId(hotelId);
        hotelDetailRequest.setCurrencyCode(UserDTO.getUserDTO().getCurrency());
        hotelDetailRequest.setOccupancy(HotelListingRequest.getHotelListRequest().getOccupancy());

        String request = new GsonBuilder().serializeNulls().create().toJson(hotelDetailRequest);

        if (NetworkUtilities.isInternet(context)) {

            showDialog();

            hotelListingMapPresenter.getHotelRoomRate(Constant.API_URL + Constant.HOTELSRATES, request, context);

        } else {

            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), false, getFragmentManager());
        }
    }


    private void tripAdviserDetailCall(Long hotelID) {
        if (NetworkUtilities.isInternet(context)) {

            showDialog();
            hotelListingMapPresenter.getTripAdvisorDetails(Constant.API_URL + Constant.TRIPADVISORDATA + "?HotelId=" + hotelID + "&languageCode=" + UserDTO.getUserDTO().getLanguage(), context);

        } else {

            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), false, getFragmentManager());

        }
    }


    @Override
    public void initUIElements() {

        rootView.findViewById(R.id.toolbarBackImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gtmAnalytics.sendEvent("Map", "back to List View", "Back to listing");
                Bundle bundle = new Bundle();
                bundle.putString("back to List View", "Back to listing");
                appEventsLogger.logEvent(EVENT_CATEGORY, bundle);
                getActivity().onBackPressed();
            }
        });

        String destinationName;

        if (UserDTO.getUserDTO().getDestinationName() != null && !(UserDTO.getUserDTO().getDestinationName().equals(""))) {
            destinationName = UserDTO.getUserDTO().getDestinationName();
        } else if (UserDTO.getUserDTO().getCityName() != null && !(UserDTO.getUserDTO().getCityName().equals(""))) {
            destinationName = UserDTO.getUserDTO().getCityName();
        } else {
            destinationName = getActivity().getResources().getString(R.string.hotel);
        }

        if (destinationName.contains(",")) {
            String locationText = destinationName.split(",")[0];
            ((TextView) rootView.findViewById(R.id.toolbarTitleTextView)).setText(locationText);
        } else {
            ((TextView) rootView.findViewById(R.id.toolbarTitleTextView)).setText(destinationName);
        }
    }


    @Override
    public void setHotelRateResponse(HotelRatesResponse hotelRateResponse) {

        if (hotelRateResponse.getError() == null) {

            hotelRatesResponse = hotelRateResponse;
            if (hotelRateResponse.getRoomTypes() != null && hotelRateResponse.getRoomTypes().size() != 0) {
                hotelDetailCall(HotelID);
            } else {

                Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.app_name), getActivity().getString(R.string.No_room_available), false, null);
            }

        } else {

            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.app_name), getActivity().getString(R.string.common_error_msg), false, null);
        }
    }

    @Override
    public void setHotelDetailResponse(HotelDetailInfoResponse hotelDetailResponse) {

        if (hotelDetailResponse.getError() == null) {
            mHotelDetailResponse = hotelDetailResponse;
            if (hotelDetailResponse.getBasicInfo().getTripAdvisor().getRating() != 0.0 && hotelDetailResponse.getBasicInfo().getTripAdvisor().getRating() != 0) {
                tripAdviserDetailCall(hotelDetailResponse.getId());
            } else {

                newFragmentCall();
            }

        } else {

            dismissDialog();
            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.app_name), getActivity().getString(R.string.common_error_msg), false, null);
        }
    }

    @Override
    public void setTripAdviserDetailResponse(TripAdviserDataResponse tripAdvisorResponse) {

        if (tripAdvisorResponse.getError() == null) {
            tripAdviserDataResponse = tripAdvisorResponse;

            newFragmentCall();
        } else {

            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.app_name), getActivity().getString(R.string.common_error_msg), false, getFragmentManager());
        }
    }

    public void showDialog() {

        if (spinningDialog != null && !spinningDialog.isShowing()) {
            spinningDialog.show();
        }
    }

    @Override
    public void dismissDialog() {
        if (spinningDialog != null && spinningDialog.isShowing()) {

            spinningDialog.dismiss();
        }
    }

}
