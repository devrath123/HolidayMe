package com.holidayme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.holidayme.activities.R;
import com.holidayme.common.AnimateFirstDisplayListener;
import com.holidayme.data.SubRatingsDto;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by santosh.patar on 04-09-2015.
 */
public class SummeryAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private ImageLoadingListener animateFirstListener;
    private ImageLoader imageLoader;
    private ArrayList<SubRatingsDto> subRatingsDtoArrayList;
    private int subRatingSize;

    public SummeryAdapter(ArrayList<SubRatingsDto> subRatingsDtoArrayList, Context context) {
        this.subRatingsDtoArrayList = subRatingsDtoArrayList;

        animateFirstListener = new AnimateFirstDisplayListener();
        displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .considerExifParams(true)
                .build();

        imageLoader = ImageLoader.getInstance();

        if (subRatingsDtoArrayList != null) {
            subRatingSize = subRatingsDtoArrayList.size();
        }

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return subRatingSize;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        final ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.rating_summery_row, null);
            viewHolder.tripAdviserReviewImageView = (ImageView) view.findViewById(R.id.tripAdviserReviewImageView);
            viewHolder.topReviewTextView = (TextView) view.findViewById(R.id.topReviewTextView);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();

        try {
            imageLoader.displayImage(subRatingsDtoArrayList.get(position).getRating_image_url(), viewHolder.tripAdviserReviewImageView, displayImageOptions, animateFirstListener);
            viewHolder.topReviewTextView.setText(subRatingsDtoArrayList.get(position).getLocalized_name());
            viewHolder.subRatingsDto = subRatingsDtoArrayList.get(position);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    public class ViewHolder {

        private ImageView tripAdviserReviewImageView;
        private TextView topReviewTextView;
        SubRatingsDto subRatingsDto;

    }
}
