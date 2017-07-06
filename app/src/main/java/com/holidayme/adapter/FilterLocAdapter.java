package com.holidayme.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.holidayme.activities.R;
import com.holidayme.common.Log;
import com.holidayme.data.CatDto;
import com.holidayme.fragments.FilterFragment;
import com.holidayme.response.HotelListingInfoResponse;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 25-08-2015.
 */
public class FilterLocAdapter extends RecyclerView.Adapter<FilterLocAdapter.ViewHolder> {
    private ArrayList<CatDto>locationsArrayList;

    public FilterLocAdapter(ArrayList<CatDto> topAreaArrayList) {

        this.locationsArrayList = topAreaArrayList;


    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_list_filter_row, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        viewHolder.locationTitleTextView.setText(locationsArrayList.get(position).getTtl());
        viewHolder.filterCountTextView.setText("("+locationsArrayList.get(position).getCnt()+")");

        viewHolder.filterCheckBox.setChecked(locationsArrayList.get(position).isCheck());
        viewHolder.filterCheckBox.setTag(position);

        viewHolder.location_layout.setTag(position);

        viewHolder.location_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean locationCheck = false;
                int pos = (Integer) v.getTag();

                Log.d("check pos tag:", pos + "");

                if (locationsArrayList.get(pos).isCheck()) {
                    locationsArrayList.get(pos).setCheck(false);
                } else {
                    locationsArrayList.get(pos).setCheck(true);
                }


                for (int i = 0; i < locationsArrayList.size(); i++) {
                    if (locationsArrayList.get(i).isCheck()) {
                        locationCheck = true;
                        break;
                    }
                }

                Message message = new Message();

                Bundle bundle = new Bundle();
                bundle.putInt("FILTERTYPE", 1);
                bundle.putBoolean("ISCHECK", locationCheck);
                bundle.putParcelableArrayList(
                        "FilterLocation",
                        locationsArrayList);
                message.setData(bundle);
                FilterFragment.filterUpdate.sendMessage(message);
                viewHolder.filterCheckBox.setChecked(locationsArrayList.get(pos).isCheck());
            }
        });
    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return locationsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView locationTitleTextView,filterCountTextView;
        private CheckBox filterCheckBox;
        private RelativeLayout location_layout;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            locationTitleTextView=(TextView) itemLayoutView.findViewById(R.id.hotelTitleTextView);
            filterCheckBox=(CheckBox) itemLayoutView.findViewById(R.id.filterCheckBox);
            filterCountTextView=(TextView) itemLayoutView.findViewById(R.id.filterCountTextView);
            location_layout=(RelativeLayout)itemLayoutView.findViewById(R.id.locationRelativeLayout);


        }

    }


}
