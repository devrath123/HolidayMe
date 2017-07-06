package com.holidayme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.holidayme.activities.R;
import com.holidayme.data.SpecialRequestsDto;

import java.util.ArrayList;

/**
 * Created by santosh.patar on 04-09-2015.
 */
public class SpecialRequestListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SpecialRequestsDto> specialRequestsDtoArrayList;
    private OnItemRemove itemRemove;
    private int specialRequestArrayListSize;

    public SpecialRequestListAdapter(ArrayList<SpecialRequestsDto> specialRequestsDtoArrayList, Context mContext, OnItemRemove itemRemove) {
        this.context = mContext;
        this.specialRequestsDtoArrayList = specialRequestsDtoArrayList;
        specialRequestArrayListSize = specialRequestsDtoArrayList.size();
        this.itemRemove = itemRemove;
    }

    @Override
    public int getCount() {
        return specialRequestArrayListSize;
    }

    @Override
    public Object getItem(int arg0) {
        return specialRequestsDtoArrayList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup arg2) {

        final ViewHolder holder;

        if (convertview == null) {

            holder = new ViewHolder();
            convertview = LayoutInflater.from(context).inflate(R.layout.special_request_row, null);
            holder.selectedSpecialRequestTextView = (TextView) convertview.findViewById(R.id.txt_selected_sp);
            holder.closeImageView = (ImageView) convertview.findViewById(R.id.img_close);
            convertview.setTag(holder);
        } else {
            holder = (ViewHolder) convertview.getTag();
        }

        try {
            holder.selectedSpecialRequestTextView.setText(specialRequestsDtoArrayList.get(position).getDescription());
            holder.closeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemRemove.itemPosition(position);
                }
            });

            holder.specialRequestsDto = specialRequestsDtoArrayList.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertview;
    }

    public class ViewHolder {

         TextView selectedSpecialRequestTextView;
         SpecialRequestsDto specialRequestsDto;
         ImageView closeImageView;

    }

    public interface OnItemRemove {
        void itemPosition(int index);
    }
}
