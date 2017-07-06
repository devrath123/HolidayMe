
package com.holidayme.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.holidayme.activities.R;
import com.holidayme.staycation_calendar_mvp.StaycationCalendarActivity;
import com.holidayme.data.GetawayDetailBean;
import com.holidayme.data.UserDTO;
import com.holidayme.gtm.GTMAnalytics;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Created by devrath.rathee on 23-02-2017.
 */


public class SubpackageAdapter extends RecyclerView.Adapter<SubpackageAdapter.GetawayViewHolder> {

    private Context context;
    private List<GetawayDetailBean.SubPackageBean> getawayBeanList = new ArrayList<>();
    private LayoutInflater inflater;
    private Map<Integer, Boolean> viewInclusionActionMap;
    private LinearLayout.LayoutParams layoutParams;
    private long packageid;
    private String hotelImageUrl,hotelTitle,bookingPolicy,packageName,cancellationPolicy;
    private GTMAnalytics gtmAnalytics;
   // private DecimalFormat formatter ;
    //private NumberFormat numberFormat;

    public SubpackageAdapter(Context context, List<GetawayDetailBean.SubPackageBean> getawayBeanList,long packageId,
                             String hotelImageurl,String hotelTitle,String bookingPolicy,
                             String packageName,GTMAnalytics gtmAnalytics,String cancellationPolicy) {
        this.context = context;
        if(getawayBeanList != null && getawayBeanList.size() > 0)
        this.getawayBeanList.addAll(getawayBeanList);
        this.packageid = packageId;
        this.hotelImageUrl = hotelImageurl;
        this.hotelTitle = hotelTitle;
        this.bookingPolicy = bookingPolicy;
        this.packageName=packageName;
        this.gtmAnalytics=gtmAnalytics;
        this.cancellationPolicy = cancellationPolicy;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewInclusionActionMap = new HashMap<>();
       // layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       // layoutParams.setMargins(0, 0, 0, 10);


        //formatter = new DecimalFormat("#,###,###.##");
       // numberFormat = NumberFormat.getInstance(new Locale("en","US"));
    }

    @Override
    public int getItemCount() {
        return getawayBeanList.size();
    }

    @Override
    public void onBindViewHolder(final GetawayViewHolder holder, final int position) {

        final GetawayDetailBean.SubPackageBean bean = getawayBeanList.get(position);
        viewInclusionActionMap.put(position, false);

        holder.titleTextView.setText(bean.getTitle());
        holder.whatYouGetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gtmAnalytics.sendEvent("Getaway Details- " + packageName, "What You Get" , "What You Get");

                if (viewInclusionActionMap.get(position)) {
                    holder.inclusionsLinearLayout.removeAllViews();
                    viewInclusionActionMap.put(position, false);
                } else {

                    View row = inflater.inflate(R.layout.inclusion_row, null);
                    ((TextView) row.findViewById(R.id.inclusionRowTextView)).setText(Html.fromHtml(bean.getWhatYouGet()));
                   // row.setLayoutParams(layoutParams);
                    holder.inclusionsLinearLayout.addView(row);
                    viewInclusionActionMap.put(position, true);
                }

            }
        });

        if(bean.getMarketPrice() > 0.0) {
            //formatter.format(bean.getMarketPrice());
            holder.oldCurrencyPriceTextView.setText(UserDTO.getUserDTO().getCurrency() + " " +  String.format(Locale.US, "%.2f",bean.getMarketPrice()));
            holder.oldCurrencyPriceTextView.setPaintFlags(holder.oldCurrencyPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
            holder.newCurrencyPriceTextView.setText(UserDTO.getUserDTO().getCurrency() + " " +  String.format(Locale.US, "%.2f",bean.getSellingPrice()));


        holder.subpackageCardView.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,StaycationCalendarActivity.class);
                gtmAnalytics.sendEvent("Getaway Details- " + packageName, "Subpackage "+bean.getTitle() , "Subpackage Selected");

                intent.putExtra("PackageId",String.valueOf(packageid));
                intent.putExtra("RoomId",String.valueOf(bean.getRoomId()));
                intent.putExtra("SubPackageId",String.valueOf(bean.getId()));
                intent.putExtra("Nights",bean.getNights());
                intent.putExtra("SlashPrice",bean.getMarketPrice());
                intent.putExtra("Price",bean.getSellingPrice());
                intent.putExtra("booking policy",bookingPolicy);
                intent.putExtra("hotelimageurl",hotelImageUrl);
                intent.putExtra("hoteltitle",hotelTitle);
                intent.putExtra("cutOffDays",bean.getCutOffDays());
                intent.putExtra("SubPackage",bean.getTitle());
                intent.putExtra("Package",packageName);
                intent.putExtra("cancellationPolicy",cancellationPolicy);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public GetawayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View getawayView = LayoutInflater.from(context).inflate(R.layout.gateway_details_row, null);
        return new GetawayViewHolder(getawayView);
    }

    public static class GetawayViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, whatYouGetTextView, newCurrencyPriceTextView, oldCurrencyPriceTextView;
        LinearLayout inclusionsLinearLayout;
        CardView subpackageCardView;

        public GetawayViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            titleTextView = (TextView) itemLayoutView.findViewById(R.id.titleTextView);
            whatYouGetTextView = (TextView) itemLayoutView.findViewById(R.id.whatYouGetTextView);
            newCurrencyPriceTextView = (TextView) itemLayoutView.findViewById(R.id.newCurrencyPriceTextView);
            oldCurrencyPriceTextView = (TextView) itemLayoutView.findViewById(R.id.oldCurrencyPriceTextView);
            inclusionsLinearLayout = (LinearLayout) itemLayoutView.findViewById(R.id.inclusionsLinearLayout);
            subpackageCardView = (CardView) itemLayoutView.findViewById(R.id.subpackageCardView);
        }

    }
}

