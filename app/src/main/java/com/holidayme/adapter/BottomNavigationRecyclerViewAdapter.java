package com.holidayme.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.holidayme.activities.R;
import com.holidayme.data.ZoomInImagesDTO;

import java.util.ArrayList;

/**
 * Created by arshad.shaikh on 3/6/2017.
 */

public class BottomNavigationRecyclerViewAdapter extends RecyclerView.Adapter<BottomNavigationRecyclerViewAdapter.ViewHolder> {

    private  Context context;
    private  ArrayList<ZoomInImagesDTO>imageStylesArrayList;
    RowItemClickListener rowItemClickListener;


    public BottomNavigationRecyclerViewAdapter(Context context, ArrayList<ZoomInImagesDTO> imageStylesArrayList) {

        this.context=context;
        this.imageStylesArrayList=imageStylesArrayList;

    }

    @Override
    public BottomNavigationRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bottom_style_row_layout, parent, false);

        return new BottomNavigationRecyclerViewAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BottomNavigationRecyclerViewAdapter.ViewHolder holder, int position) {


        if(imageStylesArrayList.get(position).isActive()){

            holder.bottomRowRelativeLayout.setBackgroundColor(Color.parseColor("#00FFFFFF"));

        }
        else{

            holder.bottomRowRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.button_selector));

        }

        Glide.with(context)
                .load(imageStylesArrayList.get(position).getUrl())
                .into(holder.bottomNavigationImageView);

    }

    @Override
    public int getItemCount() {
        return imageStylesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView bottomNavigationImageView;
        RelativeLayout bottomRowRelativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            bottomRowRelativeLayout= (RelativeLayout) itemView.findViewById(R.id.bottomRowRelativeLayout);
            bottomNavigationImageView= (ImageView) itemView.findViewById(R.id.bottomNavigationImageView);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (rowItemClickListener != null) {
                rowItemClickListener.onItemClick(v,getAdapterPosition());
            }
        }
    }

    public interface RowItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(
            final RowItemClickListener rowItemClickListener) {
        this.rowItemClickListener = rowItemClickListener;
    }


}
