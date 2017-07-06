package com.holidayme.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.data.TripTypesDto;
import com.holidayme.data.UserDTO;

import java.util.ArrayList;

/**
 * Created by santosh.patar on 04-09-2015.
 */
public class ReviewAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<TripTypesDto> tripTypesDtoArrayList;
    private int tripTypeSize;
    Context context;

    public ReviewAdapter(ArrayList<TripTypesDto> tripTypesDtoArrayList, Context context) {
        this.tripTypesDtoArrayList = tripTypesDtoArrayList;
        if (tripTypesDtoArrayList != null) {
            tripTypeSize = tripTypesDtoArrayList.size();
        }
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public int getCount() {
        return tripTypeSize;
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
    public View getView(final int position, View view, ViewGroup viewGroup) {

        final ViewHolder holder;

        if (view == null) {

            holder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.review_row, null);


            holder.reviewCountTextView = (TextView) view.findViewById(R.id.totalReviewTextView);
            holder.reviewTitleTextView = (TextView) view.findViewById(R.id.reviewTextView);

            // set icon to particular ratings

            for (int i = 0; i < tripTypesDtoArrayList.size(); i++) {
                if (UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
                    if (tripTypesDtoArrayList.get(position).getLocalized_name().equalsIgnoreCase("Family"))
                        holder.reviewTitleTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.family, 0);
                    if (tripTypesDtoArrayList.get(position).getLocalized_name().equalsIgnoreCase("Business"))
                        holder.reviewTitleTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.business, 0);
                    if (tripTypesDtoArrayList.get(position).getLocalized_name().equalsIgnoreCase("couples"))
                        holder.reviewTitleTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.couple, 0);
                    if (tripTypesDtoArrayList.get(position).getLocalized_name().equalsIgnoreCase("Solo travel"))
                        holder.reviewTitleTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.solo, 0);
                    if (tripTypesDtoArrayList.get(position).getLocalized_name().equalsIgnoreCase("Friends getaway"))
                        holder.reviewTitleTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.friends, 0);
                } else {
                    if (tripTypesDtoArrayList.get(position).getLocalized_name().equalsIgnoreCase("Family"))
                        holder.reviewTitleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.family, 0, 0, 0);
                    if (tripTypesDtoArrayList.get(position).getLocalized_name().equalsIgnoreCase("Business"))
                        holder.reviewTitleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.business, 0, 0, 0);
                    if (tripTypesDtoArrayList.get(position).getLocalized_name().equalsIgnoreCase("couples"))
                        holder.reviewTitleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.couple, 0, 0, 0);
                    if (tripTypesDtoArrayList.get(position).getLocalized_name().equalsIgnoreCase("Solo travel"))
                        holder.reviewTitleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.solo, 0, 0, 0);
                    if (tripTypesDtoArrayList.get(position).getLocalized_name().equalsIgnoreCase("Friends getaway"))
                        holder.reviewTitleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.friends, 0, 0, 0);
                }
            }
            view.setTag(holder);

        } else {

            holder = (ViewHolder) view.getTag();

        }


        try {

            holder.reviewCountTextView.setText(tripTypesDtoArrayList.get(position).getValue() + "");
            holder.reviewTitleTextView.setText(tripTypesDtoArrayList.get(position).getLocalized_name());
            holder.tripTypesDto = tripTypesDtoArrayList.get(position);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    public class ViewHolder {


        private TextView reviewCountTextView, reviewTitleTextView;
        TripTypesDto tripTypesDto;

    }
}
