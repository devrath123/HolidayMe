package com.holidayme.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.util.Utilities;
import com.holidayme.common.AnimateFirstDisplayListener;
import com.holidayme.data.ReviewsDto;
import com.holidayme.data.UserDTO;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by santosh.patar on 04-09-2015.
 */
public class TopReviewAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private DisplayImageOptions displayImageOptions;
    private ImageLoadingListener animateFirstListener;
    private ImageLoader imageLoader;
    private ArrayList<ReviewsDto> reviewsDtoArrayList;
    private int reviewSize;

    public TopReviewAdapter(ArrayList<ReviewsDto> reviewsDtoArrayList, Context context) {
        this.reviewsDtoArrayList = reviewsDtoArrayList;

        if (reviewsDtoArrayList != null) {
            reviewSize = reviewsDtoArrayList.size();
        }

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        animateFirstListener = new AnimateFirstDisplayListener();
        displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.icn_trip_adviser)
                .showImageForEmptyUri(R.drawable.icn_trip_adviser).showImageOnFail(R.drawable.icn_trip_adviser).cacheInMemory(true)
                .considerExifParams(true)
                .build();

        imageLoader = ImageLoader.getInstance();


    }

    @Override
    public int getCount() {
        return reviewSize;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertview, ViewGroup arg2) {

        final ViewHolder holder;

        if (convertview == null) {

            holder = new ViewHolder();
            convertview = layoutInflater.inflate(R.layout.item_list_top_review, null);
            if(UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE))
             convertview.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

            holder.tripAdviserReviewImageView = (ImageView) convertview.findViewById(R.id.TripAdviserReviewImageView);
            holder.reviewDetailTextView = (TextView) convertview.findViewById(R.id.reviewDetailTextView);
            holder.publishDateTextView = (TextView) convertview.findViewById(R.id.publishDateTextView);
            holder.reviewTitleTextView = (TextView) convertview.findViewById(R.id.reviewTitleTextView);
            convertview.setTag(holder);

        } else {
            holder = (ViewHolder) convertview.getTag();
        }

        try {
            Utilities.setTripAdviserRating(holder.tripAdviserReviewImageView,(int)reviewsDtoArrayList.get(position).getRating());
           // imageLoader.displayImage(reviewsDtoArrayList.get(position).getRating_image_url(), holder.tripAdviserReviewImageView, displayImageOptions, animateFirstListener);
            holder.reviewDetailTextView.setText(Html.fromHtml(reviewsDtoArrayList.get(position).getText()) + "");

            holder.reviewTitleTextView.setText(reviewsDtoArrayList.get(position).getTitle() + "");
            holder.reviewTitleTextView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

            holder.reviewsDto = reviewsDtoArrayList.get(position);

            if(reviewsDtoArrayList.get(position).getPublished_date() != null && reviewsDtoArrayList.get(position).getPublished_date().length() > 0 ) {
                String[] dateTimeArray = reviewsDtoArrayList.get(position).getPublished_date().split("T");
                String[] dateArray = dateTimeArray[0].split("-");

                String[] timeArray = dateTimeArray[1].split("-");
                String[] time = timeArray[0].split(":");

                String reviewTime;

                if(Integer.parseInt(time[0]) > 12)
                {
                    int pmTime = 24 - Integer.parseInt(time[0]);
                    reviewTime = pmTime + ":" + time[1] + ":" + time[2] + " PM";
                }
                else
                   reviewTime = timeArray[0] + " AM";


                String date = dateArray[2] + "/" + dateArray[1] + "/" + dateArray[0] + " " + reviewTime;
                holder.publishDateTextView.setText(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return convertview;
    }

    public class ViewHolder {
        private ImageView tripAdviserReviewImageView;
        private TextView reviewDetailTextView, publishDateTextView, reviewTitleTextView;
        ReviewsDto reviewsDto;

    }
}
