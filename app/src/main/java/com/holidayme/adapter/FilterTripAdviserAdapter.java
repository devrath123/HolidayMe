package com.holidayme.adapter;

/**
 * Created by supriya.sakore on 01-09-2015.
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
import android.widget.LinearLayout;
import android.widget.TextView;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.util.Utilities;
import com.holidayme.data.TripDto;
import com.holidayme.fragments.FilterFragment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class FilterTripAdviserAdapter extends RecyclerView.Adapter<FilterTripAdviserAdapter.ViewHolder> {

    private ArrayList<TripDto> tripAdviserArrayList;

    public FilterTripAdviserAdapter(ArrayList<TripDto> tripAdviserArrayList) {

        Collections.sort(tripAdviserArrayList, new TripAdvisorRating());
        this.tripAdviserArrayList = tripAdviserArrayList;

       ArrayList<TripDto> newTripAdviserArrayList = new ArrayList<>();


        for (int i = 0; i < tripAdviserArrayList.size(); i++) {
            if (tripAdviserArrayList.get(i).getRat()!=0.0)
            {
                if (tripAdviserArrayList.size() - 1 != i) {
                    if ((int) tripAdviserArrayList.get(i).getRat() == (int) tripAdviserArrayList.get(i + 1).getRat()) {
                        TripDto starDto = new TripDto();
                        if (tripAdviserArrayList.get(i).getRat() > tripAdviserArrayList.get(i + 1).getRat()) {
                            starDto.setRat(tripAdviserArrayList.get(i + 1).getRat());
                        } else {
                            starDto.setRat(tripAdviserArrayList.get(i).getRat());
                        }
                        starDto.setCnt(tripAdviserArrayList.get(i).getCnt() + tripAdviserArrayList.get(i + 1).getCnt());
                        newTripAdviserArrayList.add(starDto);
                        i = i + 1;
                    } else {
                        newTripAdviserArrayList.add(tripAdviserArrayList.get(i));
                    }
                } else {
                    newTripAdviserArrayList.add(tripAdviserArrayList.get(i));
                }
            }
        }
        if (!newTripAdviserArrayList.isEmpty())
            this.tripAdviserArrayList = newTripAdviserArrayList;
    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_tripadd_row, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

  private  class TripAdvisorRating implements Comparator<TripDto> {

        @Override
        public int compare(TripDto e1, TripDto e2) {
            if (e1.getRat() < e2.getRat()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        viewHolder.tripAdviserThumbnailImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Utilities.setTripAdviserRating(viewHolder.tripAdviserThumbnailImageView,(int) tripAdviserArrayList.get(position).getRat());
        viewHolder.tripAdviserCountTextView.setText("(" + tripAdviserArrayList.get(position).getCnt() + ")");
        viewHolder.tripAdviserCheckBox.setChecked(tripAdviserArrayList.get(position).isCheck());
        viewHolder.tripAdviserCheckBox.setTag(position);
        viewHolder.tripAdviserRelativeLayout.setTag(position);

        viewHolder.tripAdviserRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.filterFlag=true;
                boolean tripAdviserCheck = false;
                int position = (Integer) v.getTag();

                if (tripAdviserArrayList.get(position).isCheck()) {
                    tripAdviserArrayList.get(position).setCheck(false);
                } else {
                    tripAdviserArrayList.get(position).setCheck(true);
                }

                for (int i = 0; i < tripAdviserArrayList.size(); i++) {
                    if (tripAdviserArrayList.get(i).isCheck()) {
                        tripAdviserCheck = true;
                        break;
                    }
                }

                Message message = new Message();

                Bundle bundle = new Bundle();
                bundle.putInt(Constant.FILTER_TYPE, 3);
                bundle.putBoolean("ISCHECK", tripAdviserCheck);

                bundle.putParcelableArrayList("TripAdvisorRating", tripAdviserArrayList);
                message.setData(bundle);

                FilterFragment.filterUpdate.sendMessage(message);
                viewHolder.tripAdviserCheckBox.setChecked(tripAdviserArrayList.get(position).isCheck());
            }
        });

    }

    // Return the size arrayList
    @Override
    public int getItemCount() {
        return tripAdviserArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox tripAdviserCheckBox;
        private ImageView tripAdviserThumbnailImageView;
        private TextView tripAdviserCountTextView;
        private LinearLayout tripAdviserRelativeLayout;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tripAdviserCheckBox = (CheckBox) itemLayoutView.findViewById(R.id.tripAdviserCheckBox);
            tripAdviserThumbnailImageView = (ImageView) itemLayoutView.findViewById(R.id.tripAdviserThumbnailImageView);
            tripAdviserCountTextView = (TextView) itemLayoutView.findViewById(R.id.tripAdviserCountTextView);
            tripAdviserRelativeLayout = (LinearLayout) itemLayoutView.findViewById(R.id.tripAdviserRelativeLayout);
        }

    }

}

