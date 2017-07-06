package com.holidayme.adapter;

/**
 * Created by supriya.sakore on 31-08-2015.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.util.Utilities;
import com.holidayme.data.StarDto;
import com.holidayme.fragments.FilterFragment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class FilterStarRatingAdapter extends RecyclerView.Adapter<FilterStarRatingAdapter.ViewHolder> {

    private ArrayList<StarDto> startRatingArrayList, newStarRatingArrayList;
    private Context context;
    private View itemLayoutView;

    public FilterStarRatingAdapter(ArrayList<StarDto> starRatingArrayList, Context context) {

        this.startRatingArrayList = starRatingArrayList;
        Collections.sort(startRatingArrayList, new StarRating());
        this.context = context;
        newStarRatingArrayList = new ArrayList<>();


        for (int i = 0; i < startRatingArrayList.size(); i++) {
            if (startRatingArrayList.size() - 1 != i) {
                if ((int) startRatingArrayList.get(i).getStar() == (int) startRatingArrayList.get(i + 1).getStar()) {
                    StarDto starDto = new StarDto();
                    if (startRatingArrayList.get(i).getStar() > startRatingArrayList.get(i + 1).getStar()) {
                        starDto.setStar(startRatingArrayList.get(i + 1).getStar());
                    } else {
                        starDto.setStar(startRatingArrayList.get(i).getStar());
                    }
                    starDto.setCnt(startRatingArrayList.get(i).getCnt() + startRatingArrayList.get(i + 1).getCnt());
                    newStarRatingArrayList.add(starDto);
                    i = i + 1;
                } else {
                    newStarRatingArrayList.add(startRatingArrayList.get(i));
                }
            } else {
                newStarRatingArrayList.add(startRatingArrayList.get(i));
            }
        }
        if (!newStarRatingArrayList.isEmpty())
            this.startRatingArrayList = newStarRatingArrayList;
    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_starrating_row, null);

        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }
    private class StarRating implements Comparator<StarDto> {

        @Override
        public int compare(StarDto startRatingOne, StarDto starRatingSecond) {
            if (startRatingOne.getStar() < starRatingSecond.getStar()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        Utilities.setStarRating((int) startRatingArrayList.get(position).getStar(),context,itemLayoutView);
        viewHolder.starRatingCountTextView.setText("(" + startRatingArrayList.get(position).getCnt() + ")");
        viewHolder.startRatingCheckBox.setChecked(startRatingArrayList.get(position).isCheck());
        viewHolder.startRatingCheckBox.setTag(position);
        viewHolder.starRatingRelativeLayout.setTag(position);

        viewHolder.starRatingRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean starRatingCheck = false;
                Constant.filterFlag=true;

                int position = (Integer) v.getTag();

                if (startRatingArrayList.get(position).isCheck()) {
                    startRatingArrayList.get(position).setCheck(false);
                } else {
                    startRatingArrayList.get(position).setCheck(true);

                }


                for (int i = 0; i < startRatingArrayList.size(); i++) {
                    if (startRatingArrayList.get(i).isCheck()) {
                        starRatingCheck = true;

                        break;
                    }
                }

                Message message = new Message();

                Bundle bundle = new Bundle();
                bundle.putInt("FILTERTYPE", 2);
                bundle.putBoolean("ISCHECK", starRatingCheck);

                bundle.putParcelableArrayList("starRating", startRatingArrayList);

                message.setData(bundle);
                FilterFragment.filterUpdate.sendMessage(message);

                viewHolder.startRatingCheckBox.setChecked(startRatingArrayList.get(position).isCheck());
            }
        });

    }

    // Return the size arrayList
    @Override
    public int getItemCount() {
        return startRatingArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox startRatingCheckBox;
        private TextView starRatingCountTextView;
        private LinearLayout starRatingRelativeLayout;

        public ViewHolder(View itemLayoutView) {

            super(itemLayoutView);

            startRatingCheckBox = (CheckBox) itemLayoutView.findViewById(R.id.startRatingCheckBox);
            starRatingCountTextView = (TextView) itemLayoutView.findViewById(R.id.starRatingCountTextView);
            starRatingRelativeLayout = (LinearLayout) itemLayoutView.findViewById(R.id.starRatingRelativeLayout);

        }
    }

}
