package com.holidayme.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.staycation_details_mvp.StaycationDetailsActivity;
import com.holidayme.data.PackagesListDto;
import com.holidayme.data.UserDTO;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;
import static java.util.Locale.US;


/**
 * Created by arshad on 6/6/16.
 */
public class StaycationListingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {


    private static final int HEADER_TYPE = 1111;
    private static final int ROW_TYPE = 1112;


    private Context context;
    private  ArrayList<PackagesListDto>listDtos;

    public StaycationListingAdapter(Context context, ArrayList<PackagesListDto> listDtos) {
        this.context = context;
        this.listDtos=listDtos;

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {

        switch (listDtos.get(position).getType()){
            case "header":
                return HEADER_TYPE;

            case "row":
                return ROW_TYPE;
        }

        return HEADER_TYPE;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {

            case ROW_TYPE:
                return (new packageListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.staycation_list_row, parent, false)));

            case HEADER_TYPE:
                return (new ViewHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.staycation_header_row, parent, false)));

        }

        return (new ViewHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.staycation_header_row, parent, false)));
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        switch (holder.getItemViewType()){
            case HEADER_TYPE:

                ViewHeader header = (ViewHeader)holder;
                if(position==0||listDtos.get(position).getDistance()==0||listDtos.get(position).getDistance()==0.0)
                    header.headerDistanceTextView.setVisibility(View.INVISIBLE);
                else
                    header.headerDistanceTextView.setVisibility(View.VISIBLE);


              /*  if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
                    header.headerTextView.setGravity(Gravity.RIGHT);
                    header.headerDistanceTextView.setText(Math.round(listDtos.get(position).getDistance())+" "+(context.getString(R.string.kms))+" "+context.getString(R.string.From)+" "+listDtos.get(0).getCity()+" | ");

                }else {*/
                    header.headerTextView.setGravity(Gravity.LEFT);
                    header.headerDistanceTextView.setText(" | "+Math.round(listDtos.get(position).getDistance())+" "+(context.getString(R.string.kms))+" "+context.getString(R.string.From)+" "+listDtos.get(0).getCity());

              //  }





                header.headerTextView.setText(listDtos.get(position).getCity()+" ("+listDtos.get(position).getListSize()+ ") " );
               // header.headerDistanceTextView.setText(" | "+listDtos.get(position).getDistancePackage()+" kms From "+listDtos.get(0).getCity());

                break;

            case ROW_TYPE:

                packageListViewHolder packageListViewHolder = (packageListViewHolder)holder;
                Glide.with(context)
                        .load(listDtos.get(position).getListingImage())
                        .error(R.drawable.icn_default_image_loading)
                        .placeholder(R.drawable.icn_default_image_loading)
                        .centerCrop()
                        .into(packageListViewHolder.getawaysImageView);

                NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                DecimalFormat formatter = new DecimalFormat("#,###,###");

                packageListViewHolder.amountBeforeDiscountTextView.setText(listDtos.get(position).getTitle());
                packageListViewHolder.packageTitleTextView.setText(listDtos.get(position).getTitle() );
                packageListViewHolder.packageAddressTextView.setText(listDtos.get(position).getCityName()+" ,"+listDtos.get(position).getCountryName());
               if(listDtos.get(position).getIsExlusiveDeal())
                packageListViewHolder.exclusiveDealImageView.setVisibility(View.VISIBLE);
                else
                   packageListViewHolder.exclusiveDealImageView.setVisibility(View.GONE);
                if(listDtos.get(position).getIsSellingFast())
                    packageListViewHolder.sellingFastTextView.setVisibility(View.VISIBLE);
                else
                   packageListViewHolder.sellingFastTextView.setVisibility(View.GONE);

                if(listDtos.get(position).getSlashRate()!=0&&listDtos.get(position).getSlashRate()!=0.0) {
                    packageListViewHolder.amountBeforeDiscountTextView.setVisibility(View.VISIBLE);
                    packageListViewHolder.amountBeforeDiscountTextView.setPaintFlags(packageListViewHolder.amountBeforeDiscountTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    packageListViewHolder.amountBeforeDiscountTextView.setText(UserDTO.getUserDTO().getCurrency() + " " + formatter.format(Math.round(listDtos.get(position).getSlashRate())));
                    packageListViewHolder.amountBeforeDiscountTextView.setText(UserDTO.getUserDTO().getCurrency() + " " + nf.format(Math.round(listDtos.get(position).getSlashRate())));

                }else
                      packageListViewHolder.amountBeforeDiscountTextView.setVisibility(View.INVISIBLE);

                packageListViewHolder.packageCurrencyTextView.setText(UserDTO.getUserDTO().getCurrency());


                packageListViewHolder.packagePriceTextView.setText(" "+formatter.format(Math.round(listDtos.get(position).getStartFromPrice()))+" ");
                packageListViewHolder.packagePriceTextView.setText(" "+nf.format(Math.round(listDtos.get(position).getStartFromPrice()))+" ");

                packageListViewHolder.distanceTextView.setText(listDtos.get(position).getDistancePackage()+" "+(context.getString(R.string.kms)));
                packageListViewHolder.distanceFromTextView.setText(context.getString(R.string.From)+" "+listDtos.get(0).getCity());

                packageListViewHolder.getawaysImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, StaycationDetailsActivity.class);
                        intent.putExtra("packageId", listDtos.get(position).getPackageId());
                        intent.putExtra("cityId", listDtos.get(position).getCityId());
                        intent.putExtra("hotelId",listDtos.get(position).getHotelId());
                        intent.putExtra("location",listDtos.get(0).getCity());
                        context.startActivity(intent);

                    }
                });
               /* LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                llp.setMargins(0, 5, 0, 30); // llp.setMargins(left, top, right, bottom);

                if(listDtos.size()==position+1)
                    packageListViewHolder.getawaysListRelativeLayout.setLayoutParams(llp);
*/

                break;
        }

    }

    @Override
    public int getItemCount() {
        return listDtos.size();
    }




    public   class ViewHeader extends RecyclerView.ViewHolder{

        TextView headerTextView,headerDistanceTextView;

        private ViewHeader(View itemView) {
            super(itemView);

            headerTextView= (TextView) itemView.findViewById(R.id.headerTextView);
            headerDistanceTextView=(TextView)itemView.findViewById(R.id.headerDistanceTextView);
        }
    }


    private static final class packageListViewHolder extends RecyclerView.ViewHolder  {

        private ImageView getawaysImageView,exclusiveDealImageView;
        private TextView packageCurrencyTextView,distanceFromTextView,amountBeforeDiscountTextView,packageTitleTextView,packagePriceTextView,distanceTextView,packageAddressTextView,sellingFastTextView;
        private RelativeLayout getawaysListRelativeLayout;

        private packageListViewHolder(View itemView) {
            super(itemView);

            getawaysImageView=(ImageView) itemView.findViewById(R.id.getawaysImageView);
            amountBeforeDiscountTextView=(TextView)itemView.findViewById(R.id.amountBeforeDiscountTextView);
            packageTitleTextView= (TextView) itemView.findViewById(R.id.packageTitleTextView);
            packagePriceTextView=(TextView)itemView.findViewById(R.id.packagePriceTextView);
            distanceTextView=(TextView)itemView.findViewById(R.id.distanceTextView);
            exclusiveDealImageView=(ImageView)itemView.findViewById(R.id.exclusiveDealImageView);
            packageAddressTextView=(TextView)itemView.findViewById(R.id.packageHotelNameTextView);
            getawaysListRelativeLayout=(RelativeLayout) itemView.findViewById(R.id.getawaysListRelativeLayout);
            sellingFastTextView=(TextView)itemView.findViewById(R.id.sellingFastTextView);
            distanceFromTextView=(TextView)itemView.findViewById(R.id.distanceFromTextView);
            packageCurrencyTextView=(TextView)itemView.findViewById(R.id.packageCurrencyTextView);

        }

    }




}
