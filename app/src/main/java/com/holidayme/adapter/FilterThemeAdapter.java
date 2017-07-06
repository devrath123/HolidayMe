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

public class FilterThemeAdapter extends RecyclerView.Adapter< FilterThemeAdapter.ViewHolder> {

    private ArrayList<CatDto> themeArrayList;
    public  FilterThemeAdapter(ArrayList<CatDto>themeArrayList) {

        this.themeArrayList = themeArrayList;
    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_list_filter_row, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.themeTitleTextView.setText(themeArrayList.get(position).getTtl());
        viewHolder.themeCountTextView.setText("("+ themeArrayList.get(position).getCnt()+")");

        viewHolder.themeCheckBox.setChecked(themeArrayList.get(position).isCheck());
        viewHolder.themeCheckBox.setChecked(themeArrayList.get(position).isCheck());
        viewHolder.themeCheckBox.setTag(position);
        viewHolder.themeRelativeLayout.setTag(position);

        viewHolder.themeRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.filterFlag=true;
                boolean themeCheck = false;
                int pos = (Integer) v.getTag();

                if (themeArrayList.get(pos).isCheck()) {
                    themeArrayList.get(pos).setCheck(false);
                } else {
                    themeArrayList.get(pos).setCheck(true);
                }

                for (int i = 0; i < themeArrayList.size(); i++) {
                    if (themeArrayList.get(i).isCheck()) {
                        themeCheck = true;
                        break;
                    }
                }

                Message message = new Message();

                Bundle bundle = new Bundle();
                bundle.putInt(Constant.FILTER_TYPE, 4);
                bundle.putBoolean("ISCHECK", themeCheck);
                bundle.putParcelableArrayList("FilterThem", themeArrayList);

                message.setData(bundle);
                FilterFragment.filterUpdate.sendMessage(message);
                viewHolder.themeCheckBox.setChecked(themeArrayList.get(pos).isCheck());

            }
        });
    }

    // Return the size arrayList
    @Override
    public int getItemCount() {
        return themeArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView themeTitleTextView,themeCountTextView;
        private CheckBox themeCheckBox;
        private RelativeLayout themeRelativeLayout;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            themeTitleTextView=(TextView) itemLayoutView.findViewById(R.id.hotelTitleTextView);
            themeCheckBox=(CheckBox) itemLayoutView.findViewById(R.id.filterCheckBox);
            themeCountTextView=(TextView) itemLayoutView.findViewById(R.id.filterCountTextView);
            themeRelativeLayout=(RelativeLayout)itemLayoutView.findViewById(R.id.locationRelativeLayout);
        }
    }
}
