package com.holidayme.staycationindex;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.data.UserDTO;

import java.util.ArrayList;

/**
 * Created by arshad.shaikh on 2/17/2017.
 */

class GatewaysRecyclerViewAdapter extends RecyclerView.Adapter<GatewaysRecyclerViewAdapter.ViewHolder> {


    private Context context;

    private ArrayList<GetawaysPackages> packagesArrayList= new ArrayList<>();
    private OnRowItemClickListener onRowItemClickListener;



    GatewaysRecyclerViewAdapter(Context context , ArrayList<GetawaysPackages> packagesArrayList ) {

            this.packagesArrayList=packagesArrayList;
            this.context=context;



    }

    @Override
    public GatewaysRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gateways_row_layout, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final GatewaysRecyclerViewAdapter.ViewHolder holder, final int position) {

        holder.gateWayCountryNameTextView.setText(packagesArrayList.get(position).getCity());


       if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)){

           holder.gateWaysCountTextView.setText(context.getResources().getString(R.string.getaways_row_title)+" "+packagesArrayList.get(position).getPackageCount());
       }

        else{


           holder.gateWaysCountTextView.setText(packagesArrayList.get(position).getPackageCount()+" "+context.getResources().getString(R.string.getaways_row_title));
       }





        Glide.with(context)
                .load(packagesArrayList.get(position).getImageUrl())
                .error(R.drawable.icn_default_image_loading)
                .placeholder(R.drawable.icn_default_image_loading)
                .centerCrop()
                .into(holder.gatewayImageView);



    }

    @Override
    public int getItemCount() {
        return packagesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView gateWayCountryNameTextView,gateWaysCountTextView;
        ImageView gatewayImageView;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            gatewayImageView= (ImageView) itemView.findViewById(R.id.gatewayImageView);
            gateWayCountryNameTextView= (TextView) itemView.findViewById(R.id.gateWayCountryNameTextView);
            gateWaysCountTextView= (TextView) itemView.findViewById(R.id.gateWaysCountTextView);
            cardView= (CardView) itemView.findViewById(R.id.card_view);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {


            if (onRowItemClickListener != null) {
                onRowItemClickListener.onItemClick(view,getAdapterPosition(),packagesArrayList.get(getAdapterPosition()).getCityId(),packagesArrayList.get(getAdapterPosition()).getPackageCount());
            }
        }
    }

    interface OnRowItemClickListener {
        public void onItemClick(View view, int position, long cityId,String pacakageCount);
    }

    void setOnItemClickListener(
            final OnRowItemClickListener mItemClickListener) {
        this.onRowItemClickListener = mItemClickListener;
    }
}
