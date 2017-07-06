package com.holidayme.adapter;

/**
 * Created by supriya.sakore on 28-08-2015.
 */

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.common.AnimateFirstDisplayListener;
import com.holidayme.data.AmeDto;
import com.holidayme.fragments.FilterFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 25-08-2015.
 */
public class FilterAmenityAdapter extends RecyclerView.Adapter<FilterAmenityAdapter.ViewHolder> {
    private ArrayList<AmeDto> amenityArrayList;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private com.nostra13.universalimageloader.core.ImageLoader universalImageLoader;


    public FilterAmenityAdapter(ArrayList<AmeDto>amenityArrayList) {

        this.amenityArrayList = amenityArrayList;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icn_default_icon)
                .showImageForEmptyUri(R.drawable.icn_default_icon)
                .showImageOnFail(R.drawable.icn_default_icon)
                .cacheInMemory(true)
                .considerExifParams(true)
                .build();

        universalImageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();

    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_list_amenity_row, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        viewHolder.filterAmenityTitleTextView.setText(amenityArrayList.get(position).getTtl());


        if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();
        viewHolder.amenityCountTextView.setText("("+amenityArrayList.get(position).getCnt()+")");

        universalImageLoader.displayImage(amenityArrayList.get(position).getUrl(), viewHolder.amenityThumbNailImageView, options, animateFirstListener);

        viewHolder.filterAmenityCheckBox.setChecked(amenityArrayList.get(position).isCheck());
        viewHolder.filterAmenityCheckBox.setTag(position);
        viewHolder.filterAmenityRelativeLayout.setTag(position);

        viewHolder.filterAmenityRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.filterFlag=true;
                boolean amenityCheck = false;
                int position = (Integer) v.getTag();


                if (amenityArrayList.get(position).isCheck()) {
                    amenityArrayList.get(position).setCheck(false);
                } else {
                    amenityArrayList.get(position).setCheck(true);
                }


                for (int i = 0; i < amenityArrayList.size(); i++) {
                    if (amenityArrayList.get(i).isCheck()) {
                        amenityCheck = true;

                        break;


                    }

                }

                Message message = new Message();

                Bundle bundle = new Bundle();
                bundle.putInt(Constant.FILTER_TYPE, 5);
                bundle.putBoolean("ISCHECK", amenityCheck);
                bundle.putParcelableArrayList("Filter_Amenities", amenityArrayList);
                message.setData(bundle);
                FilterFragment.filterUpdate.sendMessage(message);
                viewHolder.filterAmenityCheckBox.setChecked(amenityArrayList.get(position).isCheck());

            }
        });

    }

    // Return the size arrayList
    @Override
    public int getItemCount() {
        return amenityArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView filterAmenityTitleTextView,amenityCountTextView;
        private CheckBox filterAmenityCheckBox;
        private ImageView amenityThumbNailImageView;
        private RelativeLayout filterAmenityRelativeLayout;


        public ViewHolder(View itemLayoutView) {

            super(itemLayoutView);

            filterAmenityTitleTextView=(TextView) itemLayoutView.findViewById(R.id.filterAmenityTitleTextView);
            filterAmenityCheckBox=(CheckBox) itemLayoutView.findViewById(R.id.filterAmenityCheckBox);
            amenityThumbNailImageView = (ImageView) itemLayoutView.findViewById(R.id.amenityThumbNailImageView);
            amenityCountTextView=(TextView)itemLayoutView.findViewById(R.id.amenityCountTextView);
            filterAmenityRelativeLayout=(RelativeLayout)itemLayoutView.findViewById(R.id.lfilterAmenityRelativeLayout);

        }

    }

}
