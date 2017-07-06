package com.holidayme.activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.holidayme.Constants.Constant;
import com.holidayme.activities.util.Utilities;
import com.holidayme.adapter.BottomNavigationRecyclerViewAdapter;
import com.holidayme.adapter.ZoomInGalleryImageAdapter;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.data.UserDTO;
import com.holidayme.data.ZoomInImagesDTO;

import java.util.ArrayList;

/**
 * Created by shaikh.salim on 3/23/2016.
 */
public class ImageZoomInActivity extends BaseActivity {

    private ImageView leftArrowImageView, rightArrowImageView, backPressImageView;
    private ViewPager galleryViewPager;
    private int selectedImagePosition;
    private ArrayList<ZoomInImagesDTO> zoomInImagesDTOs;
    private RecyclerView bottomNavigationRecyclerView;
    private  LinearLayoutManager layoutManager;
    private BottomNavigationRecyclerViewAdapter bottomNavigationRecyclerViewAdapter;
    private Context context;
    private  int bottomScrollPosition=0;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_zoom_in_activity);
        context=ImageZoomInActivity.this;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getSerializable("ImagesDTO") != null) {
                zoomInImagesDTOs = (ArrayList<ZoomInImagesDTO>) extras.getSerializable("ImagesDTO");
                selectedImagePosition = extras.getInt("ImagePos", 0);
            }
        }

        if (Constant.ENGLISH_LANGUAGE_CODE.equals(UserDTO.getUserDTO().getLanguage())) {
            NetworkUtilities.setLocale(Constant.ENGLISH_LANGUAGE_CODE, this);
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        } else if (Constant.ARABIC_LANGUAGE_CODE.equals(UserDTO.getUserDTO().getLanguage())) {
            NetworkUtilities.setLocale(Constant.ARABIC_LANGUAGE_CODE, this);
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        initializeUIElement();

        try {
            addImagesToView();
        } catch (Exception e) {
            e.printStackTrace();
        }

        UIFunctionality();
    }


    private void initializeUIElement() {
        leftArrowImageView = (ImageView) findViewById(R.id.leftArrowImageView);
        rightArrowImageView = (ImageView) findViewById(R.id.rightArrowImageView);
        galleryViewPager = (ViewPager) findViewById(R.id.galleryViewPager);
        backPressImageView = (ImageView) findViewById(R.id.img_exit);

        bottomNavigationRecyclerView= (RecyclerView) findViewById(R.id.bottomNavigationRecyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        bottomNavigationRecyclerView.setLayoutManager(layoutManager);



    }


    /**
     * Adds the images to view.
     */
    private void addImagesToView() {

        galleryViewPager.setAdapter(new ZoomInGalleryImageAdapter(ImageZoomInActivity.this, zoomInImagesDTOs));

        if (zoomInImagesDTOs.size() > 0)
            galleryViewPager.setCurrentItem(selectedImagePosition, false);

        if (zoomInImagesDTOs.size() == 1) {
            rightArrowImageView.setVisibility(View.GONE);
            leftArrowImageView.setVisibility(View.GONE);
        } else {
            rightArrowImageView.setVisibility(View.VISIBLE);
            leftArrowImageView.setVisibility(View.GONE);
            if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE))
                rightArrowImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.icn_arrow_left, null));
            else
                rightArrowImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.icn_arrow_right, null));

        }

        if (zoomInImagesDTOs!= null &&zoomInImagesDTOs.size() != 0)
            new Utilities().setArrowPosition(leftArrowImageView, rightArrowImageView, zoomInImagesDTOs.size(), selectedImagePosition, getResources());



    }

    private void UIFunctionality(){



        if(zoomInImagesDTOs!=null) {


            zoomInImagesDTOs.get(selectedImagePosition).setIsActive(true);
            bottomNavigationRecyclerViewAdapter = new BottomNavigationRecyclerViewAdapter(context, zoomInImagesDTOs);
            bottomNavigationRecyclerView.setAdapter(bottomNavigationRecyclerViewAdapter);


            bottomNavigationRecyclerView.getLayoutManager().scrollToPosition(selectedImagePosition);
            bottomNavigationRecyclerViewAdapter.notifyDataSetChanged();

            bottomNavigationRecyclerViewAdapter.setOnItemClickListener(new BottomNavigationRecyclerViewAdapter.RowItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    selectedImagePosition = position;

                    resetBottomNavigation();
                    zoomInImagesDTOs.get(selectedImagePosition).setIsActive(true);


                    bottomNavigationRecyclerViewAdapter.notifyDataSetChanged();
                    bottomScrollPosition = position;
                    galleryViewPager.setCurrentItem(bottomScrollPosition, false);

                }
            });

            backPressImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

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

                    if (selectedImagePosition < zoomInImagesDTOs.size() - 1)
                        ++selectedImagePosition;

                    else if (selectedImagePosition == zoomInImagesDTOs.size() - 1)
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

                    if (zoomInImagesDTOs != null && zoomInImagesDTOs.size() != 0) {

                        selectedImagePosition = position;

                        if (selectedImagePosition < zoomInImagesDTOs.size()) {
                            resetBottomNavigation();
                            zoomInImagesDTOs.get(selectedImagePosition).setIsActive(true);
                            bottomNavigationRecyclerView.getLayoutManager().scrollToPosition(position);
                            bottomNavigationRecyclerViewAdapter.notifyDataSetChanged();


                        }


                        new Utilities().setArrowPosition(leftArrowImageView, rightArrowImageView, zoomInImagesDTOs.size(), selectedImagePosition, getResources());
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }
    }

    private void resetBottomNavigation() {

        for(int i=0;i<zoomInImagesDTOs.size();i++){

            zoomInImagesDTOs.get(i).setIsActive(false);

        }

    }

    @Override
    public void onBackPressed() {
        Utilities.setSharedPreference(ImageZoomInActivity.this, "pos", Integer.toString(selectedImagePosition));
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onBackPressed();
    }


}
