package com.holidayme.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.appevents.AppEventsLogger;
import com.holidayme.AppInterface.IRoomItemClickListener;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.Utilities;
import com.holidayme.data.UserDTO;
import com.holidayme.response.HotelRatesResponse;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by santosh.patar on 02-09-2015.
 */
public class HotelRoomListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private HotelRatesResponse hotelRatesResponse;
    private int noOfRooms;
    private IRoomItemClickListener roomItemClickListener;
    private FragmentManager fragmentManager;

    private String EVENT_CATEGORY = "Hotel Details";

    public HotelRoomListAdapter(HotelRatesResponse hotelRatesResponse, Context context, IRoomItemClickListener roomItemClickListener, FragmentManager fragmentManager) {

        this.context = context;
        this.hotelRatesResponse = hotelRatesResponse;
        this.roomItemClickListener = roomItemClickListener;
        this.fragmentManager = fragmentManager;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (hotelRatesResponse.getRoomTypes() != null)
            noOfRooms = hotelRatesResponse.getRoomTypes().size();


    }

    @Override
    public int getCount() {
        return noOfRooms;
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
    public View getView(final int position, View convertView, ViewGroup arg2) {

        final ViewHolder viewHolder;
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);


        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_room, null);
            viewHolder.bookingTextView = (TextView) convertView.findViewById(R.id.bookingTextView);
            viewHolder.roomTitleTextView = (TextView) convertView.findViewById(R.id.roomTitleTextView);
            viewHolder.adultCountTextView = (TextView) convertView.findViewById(R.id.adultCountTextView);
            viewHolder.cancellationStatusTextView = (TextView) convertView.findViewById(R.id.cancellationStatusTextView);
            viewHolder.roomPriceTextView = (TextView) convertView.findViewById(R.id.roomPriceTextView);
            viewHolder.inclusionTextView = (TextView) convertView.findViewById(R.id.inclusionTextView);
            viewHolder.cancellationPolicyTextView = (TextView) convertView.findViewById(R.id.cancellationPolicyTextView);
            viewHolder.priceForTextView = (TextView) convertView.findViewById(R.id.priceForTextView);
            viewHolder.taxStatusTextView = (TextView) convertView.findViewById(R.id.taxStatusTextView);
            viewHolder.payAtHotelTextView = (TextView) convertView.findViewById(R.id.payAtHotelTextView);
            viewHolder.roomLinearLayout=(LinearLayout) convertView.findViewById(R.id.roomLinearLayout);
            viewHolder.cancellationPolicyTextView.setTag(position);


            convertView.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) convertView.getTag();


        try {


            if (hotelRatesResponse.getRoomTypes().get(position).getMaxPax() != 0) {
                viewHolder.adultCountTextView.setVisibility(View.VISIBLE);
                viewHolder.adultCountTextView.setText(context.getString(R.string.max_adults) + " " + hotelRatesResponse.getRoomTypes().get(position).getMaxPax() + "");
            } else
                viewHolder.adultCountTextView.setVisibility(View.GONE);



            viewHolder.roomPriceTextView.setText(UserDTO.getUserDTO().getCurrency() + " " + formatter.format(Math.round(hotelRatesResponse.getRoomTypes().get(position).getPrice().getTotal())) + "");
            viewHolder.roomPriceTextView.setText(UserDTO.getUserDTO().getCurrency() + " " + nf.format(Math.round(hotelRatesResponse.getRoomTypes().get(position).getPrice().getTotal())) + "");

            viewHolder.roomTitleTextView.setText(hotelRatesResponse.getRoomTypes().get(position).getTtl());
            viewHolder.priceForTextView.setText(hotelRatesResponse.getPriceLabel());

            if (hotelRatesResponse.getRoomTypes().get(position).getIncludes() != null) {
                viewHolder.inclusionTextView.setText(context.getString(R.string.includes) + " " + hotelRatesResponse.getRoomTypes().get(position).getIncludes().toString().replace("[", "").replace("]", "") + "");
                viewHolder.inclusionTextView.setTextColor(ContextCompat.getColor(context, R.color.green));
            } else {
                viewHolder.inclusionTextView.setTextColor(ContextCompat.getColor(context, R.color.green));
                viewHolder.inclusionTextView.setText(context.getString(R.string.includes) + " " + context.getString(R.string.NA) + "");
            }

            if (hotelRatesResponse.getRoomTypes().get(position).isPayAtHotel())
                viewHolder.payAtHotelTextView.setVisibility(View.VISIBLE);
            else
                viewHolder.payAtHotelTextView.setVisibility(View.INVISIBLE);


            if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
                switch (hotelRatesResponse.getRoomTypes().get(position).getRefundableType()) {
                    case 1:
                        viewHolder.cancellationStatusTextView.setText(context.getString(R.string.Free_Cancellation));
                        viewHolder.cancellationStatusTextView.setTextColor(ContextCompat.getColor(context, R.color.green));
                        viewHolder.cancellationStatusTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icn_free_cancellation, 0);
                        break;
                    case 2:
                        viewHolder.cancellationStatusTextView.setText(context.getString(R.string.Non_Refundable));
                        viewHolder.cancellationStatusTextView.setTextColor(ContextCompat.getColor(context, R.color.nonrefundableRed));
                        viewHolder.cancellationStatusTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icn_non_refundable, 0);
                        break;
                    default:
                        viewHolder.cancellationStatusTextView.setText(context.getString(R.string.Refundable));
                        viewHolder.cancellationStatusTextView.setTextColor(ContextCompat.getColor(context, R.color.green));
                        viewHolder.cancellationStatusTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icn_free_cancellation, 0);
                        break;
                }


            } else {
                switch (hotelRatesResponse.getRoomTypes().get(position).getRefundableType()) {
                    case 1:
                        viewHolder.cancellationStatusTextView.setText(context.getString(R.string.Free_Cancellation));
                        viewHolder.cancellationStatusTextView.setTextColor(ContextCompat.getColor(context, R.color.green));
                        viewHolder.cancellationStatusTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icn_free_cancellation, 0, 0, 0);
                        break;
                    case 2:
                        viewHolder.cancellationStatusTextView.setText(context.getString(R.string.Non_Refundable));
                        viewHolder.cancellationStatusTextView.setTextColor(ContextCompat.getColor(context, R.color.nonrefundableRed));
                        viewHolder.cancellationStatusTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icn_non_refundable, 0, 0, 0);
                        break;
                    default:
                        viewHolder.cancellationStatusTextView.setText(context.getString(R.string.Refundable));
                        viewHolder.cancellationStatusTextView.setTextColor(ContextCompat.getColor(context, R.color.green));
                        viewHolder.cancellationStatusTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icn_free_cancellation, 0, 0, 0);
                        break;

                }

            }
            if (hotelRatesResponse.getRoomTypes().get(position).getTaxLabel() == null || hotelRatesResponse.getRoomTypes().get(position).getTaxLabel().isEmpty()) {
                viewHolder.taxStatusTextView.setTextColor(ContextCompat.getColor(context, R.color.green));
                viewHolder.taxStatusTextView.setText(context.getString(R.string.Taxes_included));

            } else {
                viewHolder.taxStatusTextView.setTextColor(ContextCompat.getColor(context, R.color.green));
                viewHolder.taxStatusTextView.setText(hotelRatesResponse.getRoomTypes().get(position).getTaxLabel());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder.cancellationPolicyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.getInstance().getGTMAnalytics((Activity) context).sendEvent("HotelRoomDetail Screen - " + viewHolder.roomTitleTextView.getText(), "Cancellation policy", "show cancellation policy");

                Utilities.pushFacebookEvent(AppEventsLogger.newLogger(context), EVENT_CATEGORY, "Cancellation policy", "show cancellation policy");
                String cancellation=Html.fromHtml(hotelRatesResponse.getRoomTypes().get(position).getCancellationPolicy()).toString();
                Utilities.commonErrorMessage(context, context.getString(R.string.Cancellation_Policy), cancellation, false, fragmentManager);


            }
        });
         viewHolder.roomLinearLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 roomItemClickListener.viewRoomDetail(position);
             }
         });

        viewHolder.bookingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.getInstance().getGTMAnalytics((Activity) context).sendEvent("HotelRoomDetail Screen - " + viewHolder.roomTitleTextView.getText(), "Book", "Book action");

                Utilities.pushFacebookEvent(AppEventsLogger.newLogger(context), EVENT_CATEGORY, "Book", "Book action");

                roomItemClickListener.bookingUrl(position);
            }
        });


        return convertView;
    }


    public class ViewHolder {

        private TextView roomTitleTextView, adultCountTextView, cancellationStatusTextView, roomPriceTextView, cancellationPolicyTextView, inclusionTextView, priceForTextView, taxStatusTextView, payAtHotelTextView, bookingTextView;
        private LinearLayout roomLinearLayout;

    }
}
