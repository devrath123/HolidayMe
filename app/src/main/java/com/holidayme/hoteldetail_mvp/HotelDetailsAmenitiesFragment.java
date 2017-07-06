package com.holidayme.hoteldetail_mvp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.common.AnimateFirstDisplayListener;
import com.holidayme.fragments.BaseFragment;
import com.holidayme.response.HotelDetailInfoResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by supriya.sakore on 10-11-2016.
 */

public class HotelDetailsAmenitiesFragment extends BaseFragment {
    private View rootView;
    private DisplayImageOptions displayImageOptions;
    private ImageLoadingListener animateFirstListener;
    private ImageLoader imageLoader;
    private static HotelDetailInfoResponse hotelDetailResponse;

    static HotelDetailsAmenitiesFragment newInstance(HotelDetailInfoResponse hotelDetailInfoResponse) {
        HotelDetailsAmenitiesFragment.hotelDetailResponse = hotelDetailInfoResponse;
        return new HotelDetailsAmenitiesFragment();
    }


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        animateFirstListener = new AnimateFirstDisplayListener();
        displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.icn_default_icon)
                .showImageForEmptyUri(R.drawable.icn_default_icon).showImageOnFail(R.drawable.icn_default_icon).cacheInMemory(true)
                .considerExifParams(true)
                .build();

        imageLoader = ImageLoader.getInstance();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.hotel_details_amenities_fragment, container, false);
        setHasOptionsMenu(true);
        return rootView;
    }


    @Override
    public void initUIElements() {
        LinearLayout addAmenitiesContainerLinearLayout = (LinearLayout) rootView.findViewById(R.id.addAmenitiesContainerLinearLayout);

        AppController.getInstance().getGTMAnalytics(getActivity()).setScreenName("HotelDetailAmenities Screen - " + hotelDetailResponse.getTtl());

        TextView errorTextView = (TextView)rootView.findViewById(R.id.errorTextView);

         // set amenity to container
        if( hotelDetailResponse.getBasicInfo().getAmn()!= null)
        {
            errorTextView.setVisibility(View.GONE);
            //LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            for (int i = 0; i < hotelDetailResponse.getBasicInfo().getAmn().size(); i++) {

                final View addView = View.inflate(getActivity(),R.layout.add_anemities_raw, null);
                //final View addView = layoutInflater.inflate(R.layout.add_anemities_raw, null);
                TextView amenityTextView = (TextView) addView.findViewById(R.id.amenityTextView);
                ImageView amenityImageView = (ImageView) addView.findViewById(R.id.amenityImageView);
                imageLoader.displayImage(hotelDetailResponse.getBasicInfo().getAmn().get(i).getUrl(), amenityImageView, displayImageOptions, animateFirstListener);
                amenityTextView.setPadding(10, 0, 0, 0);
                amenityTextView.setText(hotelDetailResponse.getBasicInfo().getAmn().get(i).getTtl());
                addAmenitiesContainerLinearLayout.addView(addView);
            }

        }else{
            errorTextView.setVisibility(View.VISIBLE);
        }


    }






}
