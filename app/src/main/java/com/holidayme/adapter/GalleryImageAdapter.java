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
import com.holidayme.data.DImgDto;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class GalleryImageAdapter extends PagerAdapter {

    private Activity context;
    private DisplayImageOptions displayImageOptions;
    private ImageLoadingListener animateFirstListener;
    private ImageLoader imageLoader;
    private ArrayList<DImgDto> dImgDto;
    private ImageGalleryOnItemClick imageGalleryOnItemClick;

    public GalleryImageAdapter(ImageGalleryOnItemClick imageGalleryOnItemClick, Activity context, ArrayList<DImgDto> dImgDto) {

        this.context = context;
        this.imageGalleryOnItemClick = imageGalleryOnItemClick;
        this.dImgDto = dImgDto;
        animateFirstListener = new AnimateFirstDisplayListener();
        displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.icn_default_image_loading)
                .showImageForEmptyUri(R.drawable.icn_default_image_loading).showImageOnFail(R.drawable.icn_default_image_loading).cacheInMemory(true)
                .considerExifParams(true)
                .build();

        imageLoader = ImageLoader.getInstance();

    }

    @Override
    public int getCount() {
        return dImgDto.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final ImageView imageView = new ImageView(context);
        try {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageLoader.displayImage(dImgDto.get(position % dImgDto.size()).getDUrl(), imageView, displayImageOptions, animateFirstListener);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageGalleryOnItemClick != null)
                        imageGalleryOnItemClick.onItemSelected();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
