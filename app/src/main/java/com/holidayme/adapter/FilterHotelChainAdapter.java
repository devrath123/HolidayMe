package com.holidayme.adapter;

/**
 * Created by supriya.sakore on 31-08-2015.
 */

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.data.CatDto;
import com.holidayme.fragments.FilterFragment;

import java.util.ArrayList;


public class FilterHotelChainAdapter extends RecyclerView.Adapter<FilterHotelChainAdapter.ViewHolder> {

    private ArrayList<CatDto> filterHotelChainArrayList;

    public FilterHotelChainAdapter(ArrayList<CatDto> filterHotelChainArrayList) {

        this.filterHotelChainArrayList = filterHotelChainArrayList;

    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_list_filter_row, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        viewHolder.hotelTitleTextView.setText(filterHotelChainArrayList.get(position).getTtl());
        viewHolder.filterCountTextView.setText("(" + filterHotelChainArrayList.get(position).getCnt() + ")");


        viewHolder.filterCheckBox.setChecked(filterHotelChainArrayList.get(position).isCheck());
        viewHolder.filterCheckBox.setTag(position);
        viewHolder.filterHotelChainRelativeLayout.setTag(position);

        viewHolder.filterHotelChainRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.filterFlag=true;

                int position = (Integer) v.getTag();


                if (filterHotelChainArrayList.get(position).isCheck()) {
                    filterHotelChainArrayList.get(position).setCheck(false);
                } else {
                    filterHotelChainArrayList.get(position).setCheck(true);
                }

                notifyDataSetChanged();

                viewHolder.filterCheckBox.setChecked(filterHotelChainArrayList.get(position).isCheck());

            }
        });

        NotifyFilter();

    }

    // Return the size arrayList
    @Override
    public int getItemCount() {
        return filterHotelChainArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        private TextView hotelTitleTextView, filterCountTextView;
        private CheckBox filterCheckBox;
        private RelativeLayout filterHotelChainRelativeLayout;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            hotelTitleTextView = (TextView) itemLayoutView.findViewById(R.id.hotelTitleTextView);
            filterCheckBox = (CheckBox) itemLayoutView.findViewById(R.id.filterCheckBox);
            filterCountTextView = (TextView) itemLayoutView.findViewById(R.id.filterCountTextView);
            filterHotelChainRelativeLayout = (RelativeLayout) itemLayoutView.findViewById(R.id.locationRelativeLayout);
        }
    }

    private void NotifyFilter() {


        boolean chainCheck = false;
        for (int i = 0; i < filterHotelChainArrayList.size(); i++) {
            if (filterHotelChainArrayList.get(i).isCheck()) {

                chainCheck = true;
                break;
            }

        }

        Message message = new Message();

        Bundle bundle = new Bundle();
        bundle.putInt("FILTERTYPE", 6);
        bundle.putBoolean("ISCHECK", chainCheck);
        bundle.putParcelableArrayList("hotelChainList", filterHotelChainArrayList);
        message.setData(bundle);
        FilterFragment.filterUpdate.sendMessage(message);

    }

}
