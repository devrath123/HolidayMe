package com.holidayme.staycationindex;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.holidayme.activities.R;

import java.util.ArrayList;

/**
 * Created by arshad.shaikh on 3/1/2017.
 */

class GatewayCategoryAdapter extends RecyclerView.Adapter<GatewayCategoryAdapter.ViewHolder> {

    private  Context context;
    private  ArrayList<SampleData>sampleDataArrayList= new ArrayList<>();
    private OnItemClickListener itemClickListener;



    GatewayCategoryAdapter(Context context, ArrayList<SampleData> sampleDataArrayList) {

        this.context=context;
        this.sampleDataArrayList=sampleDataArrayList;
    }

    @Override
    public GatewayCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.gateway_category_row_layout,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GatewayCategoryAdapter.ViewHolder holder, int position) {

  //      holder.gateWayCategoryNameTextView.setText(sampleDataArrayList.get(position).getName());

        Glide.with(context)
                .load(sampleDataArrayList.get(position).getImage())
                .into(holder.gatewayCategoryImageView);

    }

    @Override
    public int getItemCount() {
        return sampleDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView gateWayCategoryCountTextView,gateWayCategoryNameTextView;
        ImageView gatewayCategoryImageView;

        public ViewHolder(View itemView) {
            super(itemView);

         //   gateWayCategoryNameTextView= (TextView) itemView.findViewById(R.id.gateWayCategoryNameTextView);
           // gateWayCategoryCountTextView= (TextView) itemView.findViewById(R.id.gateWayCategoryCountTextView);

            gatewayCategoryImageView= (ImageView) itemView.findViewById(R.id.gatewayCategoryImageView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (itemClickListener != null) {
                itemClickListener.onItemClick(view,getAdapterPosition());
            }

        }
    }

    interface OnItemClickListener {
        public void onItemClick(View view,int position);
    }

    void setOnItemClickListener(
            final OnItemClickListener mItemClickListener) {
            this.itemClickListener = mItemClickListener;
    }
}
