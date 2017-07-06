package com.holidayme.hoteldetail_mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.holidayme.AppInterface.ImageGalleryOnItemClick;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.ImageZoomInActivity;
import com.holidayme.activities.R;
import com.holidayme.activities.util.Utilities;
import com.holidayme.adapter.RoomInfoImageAdapter;
import com.holidayme.data.UserDTO;
import com.holidayme.data.ZoomInImagesDTO;
import com.holidayme.fragments.BaseFragment;
import com.holidayme.response.RoomDetailsResponse;

import java.util.ArrayList;

/**
 * Created by santosh.patar on 31-08-2015.
 */

public class RoomInfoAmenityFragment extends BaseFragment implements ImageGalleryOnItemClick, IHotelDetailView.IRoomDetailView {

    private View rootView;
    private ImageView leftArrowImageView,rightArrowImageView;
    private ViewPager galleryViewPager;
    private int selectedImagePosition;
    private static ArrayList<ZoomInImagesDTO> zoomInImagesDTOArrayList;
    private static RoomDetailsResponse roomDetailsResponse;
    private RelativeLayout galleryRelativeLayout;
    private TextView amenityTextView;

    static RoomInfoAmenityFragment newInstance(RoomDetailsResponse roomDetailsResponse) {
        zoomInImagesDTOArrayList = new ArrayList<>();
        RoomInfoAmenityFragment.roomDetailsResponse = roomDetailsResponse;
        RoomInfoAmenityFragment roomInfoAmenityFragment = new RoomInfoAmenityFragment();
        addImagesToZoomInDTO();

        return roomInfoAmenityFragment;
    }

    private static void addImagesToZoomInDTO() {
        if (!zoomInImagesDTOArrayList.isEmpty())
            zoomInImagesDTOArrayList.clear();
        if (roomDetailsResponse.getRoomImages() != null && !roomDetailsResponse.getRoomImages().isEmpty())
            for (int i = 0; i < roomDetailsResponse.getRoomImages().size(); i++) {
                ZoomInImagesDTO zoomInImagesDTO = new ZoomInImagesDTO();
                zoomInImagesDTO.setUrl(roomDetailsResponse.getRoomImages().get(i));
                zoomInImagesDTOArrayList.add(zoomInImagesDTO);
            }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.room_info_fragment, container, false);

        initUIElements();

        try {
            addImagesToView();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }


    @Override
    public void onResume() {
        selectedImagePosition = Integer.parseInt(Utilities.gettSharedPreference(getActivity(), "pos"));
        galleryViewPager.setCurrentItem(selectedImagePosition);

        if (roomDetailsResponse.getRoomImages() != null && roomDetailsResponse.getRoomImages().size() != 0)
        new Utilities().setArrowPosition(leftArrowImageView,rightArrowImageView, roomDetailsResponse.getRoomImages().size(),selectedImagePosition,getResources());

        super.onResume();
    }

    /**
     * Adds the images to view.
     */
    @Override
    public void addImagesToView() {

        if (roomDetailsResponse.getRoomImages() == null || roomDetailsResponse.getRoomImages().isEmpty())
            galleryRelativeLayout.setVisibility(View.GONE);
        else
            galleryRelativeLayout.setVisibility(View.VISIBLE);


        setGalleryAdapter();

        selectedImagePosition = Integer.parseInt(Utilities.gettSharedPreference(getActivity(), "pos"));
        if (roomDetailsResponse.getRoomImages().size() > 0)
            galleryViewPager.setCurrentItem(selectedImagePosition, false);

        if (roomDetailsResponse.getRoomImages().size() == 1) {
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

    @Override
    public void UIFunctionality() {

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

                if (selectedImagePosition < roomDetailsResponse.getRoomImages().size() - 1)
                    ++selectedImagePosition;

                else if (selectedImagePosition == roomDetailsResponse.getRoomImages().size() - 1)
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

                if (roomDetailsResponse.getRoomImages() != null && roomDetailsResponse.getRoomImages().size() != 0)
                    new Utilities().setArrowPosition(leftArrowImageView,rightArrowImageView, roomDetailsResponse.getRoomImages().size(),selectedImagePosition,getResources());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void setGalleryAdapter() {

        galleryViewPager.setAdapter(new RoomInfoImageAdapter(RoomInfoAmenityFragment.this, getActivity(), roomDetailsResponse));

    }

    @Override
    public void setTextToTextView() {
        if (roomDetailsResponse.getRoomAmenities() != null && !roomDetailsResponse.getRoomAmenities().isEmpty()) {
            String amenity = String.valueOf(roomDetailsResponse.getRoomAmenities()).replace("[", "").replace("]", "");
            if (amenity.length() > 0) {
                amenity.trim();
                if (amenity.charAt(amenity.length() - 1) == ',')
                    amenity = amenity.substring(0, amenity.length() - 1);
                amenity = "&#8226; " + amenity;
                amenity = amenity.replace(",", "<br>&#8226; ");
                amenity = amenity.replace("&#8226; &#8226; ", "&#8226; ");
                amenityTextView.setText(Html.fromHtml("<b>" + getActivity().getString(R.string.aminities) + "</b>" + "<br>" + amenity + "<br><br>" + getActivity().getString(R.string.aminity_note)));
            }

        }
    }


    @Override
    public void initUIElements() {
        amenityTextView = (TextView) rootView.findViewById(R.id.descriptionTextView);
        leftArrowImageView = (ImageView) rootView.findViewById(R.id.leftArrowImageView);
        rightArrowImageView = (ImageView) rootView.findViewById(R.id.rightArrowImageView);
        galleryViewPager = (ViewPager) rootView.findViewById(R.id.galleryViewPager);
        galleryRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.galleryRelativeLayout);

         setTextToTextView();


    }

    @Override
    public void onItemSelected() {
        Intent intent = new Intent(getActivity(), ImageZoomInActivity.class);
        intent.putExtra("ImagesDTO", zoomInImagesDTOArrayList);
        intent.putExtra("ImagePos", selectedImagePosition);
        startActivity(intent);
    }


}
