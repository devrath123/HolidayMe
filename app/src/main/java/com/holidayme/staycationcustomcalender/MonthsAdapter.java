package com.holidayme.staycationcustomcalender;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.data.UserDTO;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by arshad.shaikh on 2/24/2017.
 */

public class MonthsAdapter extends RecyclerView.Adapter<MonthsAdapter.ViewHolder> {

    ArrayList<MonthsData> monthsDataArrayList = new ArrayList<>();
    Context context;
    OnItemClickListener onItemClickListener;


    public MonthsAdapter(Context context, ArrayList<MonthsData> monthsDataArrayList) {

        /*if(UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE))
            Collections.reverse(monthsDataArrayList);*/

        this.monthsDataArrayList = monthsDataArrayList;
        this.context = context;

    }

    @Override
    public MonthsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.months_rowlayout, parent, false);

        return new MonthsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MonthsAdapter.ViewHolder holder, int position) {


        if (monthsDataArrayList.get(position).isSelected()) {

            holder.monthRelativeLayout.setBackground(context.getResources().getDrawable(R.drawable.rectangle));
            //holder.monthsRowRelativeLayout.setBackgroundColor(context.getResources().getColor(R.color.dark_blue));
            holder.monthNameTextView.setTextColor(Color.parseColor("#ffffff"));
            holder.yearTextView.setTextColor(Color.parseColor("#ffffff"));

        } else {

            holder.monthRelativeLayout.setBackground(null);
           // holder.monthsRowRelativeLayout.setBackgroundColor(Color.parseColor("#efefef"));
            holder.monthNameTextView.setTextColor(Color.parseColor("#000000"));
            holder.yearTextView.setTextColor(Color.parseColor("#000000"));
        }

        String[] monthYear = monthsDataArrayList.get(position).getDate().split(" ");
        holder.monthNameTextView.setText(monthYear[0]);
        holder.yearTextView.setText(monthYear[1]);

    }

    @Override
    public int getItemCount() {
        return monthsDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView monthNameTextView, yearTextView;
        FrameLayout monthsRowRelativeLayout;
        RelativeLayout monthRelativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            monthNameTextView = (TextView) itemView.findViewById(R.id.monthNameTextView);
            yearTextView = (TextView) itemView.findViewById(R.id.yearTextView);
            monthsRowRelativeLayout = (FrameLayout) itemView.findViewById(R.id.monthsRowRelativeLayout);
            monthRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.monthRelativeLayout);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, getAdapterPosition());
            }

        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }

}
