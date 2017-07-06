package com.holidayme.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.holidayme.activities.R;
import com.holidayme.common.AnimateFirstDisplayListener;
import com.holidayme.data.ZoomInImagesDTO;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by shaikh.salim on 4/6/2016.
 */
public class ZoomInGalleryImageAdapter extends PagerAdapter {
    private Activity context;
    private DisplayImageOptions displayImageOptions;
    private ImageLoadingListener animateFirstListener;
    private ImageLoader imageLoader;
    private ArrayList<ZoomInImagesDTO> zoomInImagesDTOArrayList;

    public ZoomInGalleryImageAdapter(Activity context, ArrayList<ZoomInImagesDTO> zoomInImagesDTOArrayList) {

        this.context = context;
        this.zoomInImagesDTOArrayList = zoomInImagesDTOArrayList;

        animateFirstListener = new AnimateFirstDisplayListener();
        displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.icn_default_image_loading)
                .showImageForEmptyUri(R.drawable.icn_default_image_loading).showImageOnFail(R.drawable.icn_default_image_loading).cacheInMemory(true)
                .considerExifParams(true)
                .build();

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config.build());

    }

    @Override
    public int getCount() {
        return zoomInImagesDTOArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        ImageView galleryImageView = new ImageView(context);
        try {
            galleryImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageLoader.displayImage(zoomInImagesDTOArrayList.get(position % zoomInImagesDTOArrayList.size()).getUrl(), galleryImageView, displayImageOptions, animateFirstListener);
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
