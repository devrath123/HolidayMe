package com.holidayme.adapter;

/**
 * Created by supriya.sakore on 14-10-2015.
 */

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.holidayme.AppInterface.ImageGalleryOnItemClick;
import com.holidayme.activities.R;
import com.holidayme.common.AnimateFirstDisplayListener;
import com.holidayme.response.RoomDetailsResponse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class RoomInfoImageAdapter extends PagerAdapter {

    private Activity context;
    private DisplayImageOptions displayImageOptions;
    private ImageLoadingListener animateFirstListener;
    private ImageLoader imageLoader;
    private RoomDetailsResponse roomDetailsResponse;
    private ImageGalleryOnItemClick imageGalleryOnItemClick;

    public RoomInfoImageAdapter(ImageGalleryOnItemClick imageGalleryOnItemClick, Activity context, RoomDetailsResponse roomDetailsResponse) {
        this.context = context;
        this.imageGalleryOnItemClick = imageGalleryOnItemClick;
        this.roomDetailsResponse = roomDetailsResponse;

        animateFirstListener = new AnimateFirstDisplayListener();
        displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.icn_default_image_loading)
                .showImageForEmptyUri(R.drawable.icn_default_image_loading).showImageOnFail(R.drawable.icn_default_image_loading).cacheInMemory(true)
                .considerExifParams(true)
                .build();

        imageLoader = ImageLoader.getInstance();

    }

    @Override
    public int getCount() {
        if(roomDetailsResponse.getRoomImages()!=null)
        return roomDetailsResponse.getRoomImages().size();
        else
            return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final ImageView galleryImageView = new ImageView(context);
        try {
            galleryImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            if(roomDetailsResponse.getRoomImages()==null|| roomDetailsResponse.getRoomImages().isEmpty()){
                galleryImageView.setVisibility(View.GONE);
            }else {
                galleryImageView.setVisibility(View.VISIBLE);
                imageLoader.displayImage(roomDetailsResponse.getRoomImages().get(position % roomDetailsResponse.getRoomImages().size()), galleryImageView, displayImageOptions, animateFirstListener);
            }
            galleryImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageGalleryOnItemClick != null)
                        imageGalleryOnItemClick.onItemSelected();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        container.addView(galleryImageView, 0);
        return galleryImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
