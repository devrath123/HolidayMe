package com.holidayme.hoteldetail_mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.holidayme.activities.R;
import com.holidayme.activities.util.Utilities;
import com.holidayme.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santosh.patar on 09-10-2015.
 */
public class HotelDetailMapFragment extends BaseFragment implements OnMapReadyCallback,IHotelDetailView.IHotelDetailMapView {

    private View rootView;
    private GoogleMap googleMap;
    private Marker mapMarker;
    private double latitude, longitude;
    private String hotelTitle, hotelAddress;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.hotel_details_map_fragment, container, false);
        setHasOptionsMenu(true);
        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.blank_menu, menu);

    }


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            latitude = bundle.getDouble("Latitude");
            longitude = bundle.getDouble("Logitude");
            if (bundle.getString("Title") != null) {
                hotelTitle = bundle.getString("Title");
            }

            if (bundle.getString("Address") != null) {
                hotelAddress = bundle.getString("Address");
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        showMarkerOnMap();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
          googleMap.clear();
    }


    @Override
    public void initUIElements() {
        TextView headerTitleTextView = (TextView) rootView.findViewById(R.id.headerTitleTextView);
        ImageView headerBackPressImageView = (ImageView) rootView.findViewById(R.id.headerBackPressImageView);
        headerTitleTextView.setText(hotelTitle);
        headerBackPressImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();
            }
        });

        if (googleMap == null) {
            SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment));
            mapFragment.getMapAsync(this);
        } else
            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.app_name), getActivity().getString(R.string.Sorry_unable_to_create_maps), false, getFragmentManager());

    }

    @Override
    public void showMarkerOnMap() {
        googleMap.clear();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        List<Marker> markerArrayList = new ArrayList<>();
        LatLng latLng = new LatLng(latitude, longitude);
        BitmapDescriptor bitmapMarker;
        bitmapMarker = (BitmapDescriptorFactory.fromResource(R.drawable.map_pin));
        try {
            mapMarker = googleMap.addMarker(new MarkerOptions().position(latLng).icon(bitmapMarker).title(hotelTitle).snippet(hotelAddress));

        } catch (Exception e) {
            e.printStackTrace();
        }
        markerArrayList.add(mapMarker);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
        googleMap.moveCamera(cameraUpdate);
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);
        builder.include(mapMarker.getPosition());
    }
}
