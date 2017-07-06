package com.holidayme.adapter;

/**
 * Created by supriya.sakore on 26-10-2015.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.common.BookingStatusTypes;
import com.holidayme.common.Validation;
import com.holidayme.data.BookingListDto;
import com.holidayme.data.UserDTO;
import com.holidayme.myBooking_mvp.IMyBookingView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by supriya.sakore on 25-08-2015.
 */
public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.ViewHolder> {

    private Context context;
    private ArrayList<BookingListDto> bookingListDtoArrayList;
    private IMyBookingView.IMyBookingViewClickListener iMyBookingViewClickListener;

    public MyBookingAdapter(ArrayList<BookingListDto> bookingListDtoArrayList, Context context,IMyBookingView.IMyBookingViewClickListener iMyBookingViewClickListener) {
        this.bookingListDtoArrayList = bookingListDtoArrayList;
        this.iMyBookingViewClickListener = iMyBookingViewClickListener;
        this.context = context;
    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_booking_row, null);
        // create ViewHolder
        return new ViewHolder(itemLayoutView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        SimpleDateFormat dateFormat;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
            dateFormat = new SimpleDateFormat("dd-MMM-yyyy", new Locale("ar"));
        }
        else {
            dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        }

        String checkInDate = "", checkOutDate = "", currentDate = "";
        Date date;
        try {

            date = simpleDateFormat.parse(bookingListDtoArrayList.get(position).getCheckInDate());
            checkInDate = dateFormat.format(date);
            date = simpleDateFormat.parse(bookingListDtoArrayList.get(position).getCheckOutDate());
            checkOutDate = dateFormat.format(date);
            date = simpleDateFormat.parse(bookingListDtoArrayList.get(position).getBookingDate());
            currentDate = dateFormat.format(date);
            viewHolder.currentDateTextView.setText(currentDate);
            viewHolder.checkInDateTextView.setText(checkInDate);
            viewHolder.checkOutDateTextView.setText(checkOutDate);
            viewHolder.bookingIdTextView.setText(bookingListDtoArrayList.get(position).getHolzooBookingId() + "");
            viewHolder.billingAmountTextView.setText(bookingListDtoArrayList.get(position).getDisplayCurrencyType() + " " + String.format(Locale.US, "%.2f", bookingListDtoArrayList.get(position).getDisplayAmount()) + "");
            viewHolder.hotelNameTextView.setText(bookingListDtoArrayList.get(position).getTitle());
            viewHolder.destinationTextView.setText(bookingListDtoArrayList.get(position).getCity()+", "+bookingListDtoArrayList.get(position).getCountry());

            if((bookingListDtoArrayList.get(position).getBookingStatus())== BookingStatusTypes.Confirmed.getBookingStatusVal()){
                viewHolder.bookingStatusTextView.setVisibility(View.VISIBLE);
                 viewHolder.bookingStatusTextView.setText(context.getString(R.string.Confirmed));
                  viewHolder.bookingStatusTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.confirmed_status));
            }else if(bookingListDtoArrayList.get(position).getBookingStatus()== 4){
                viewHolder.bookingStatusTextView.setVisibility(View.VISIBLE);
                viewHolder.bookingStatusTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.cancelled_status));
                viewHolder.bookingStatusTextView.setText(context.getString(R.string.cancelled));
            }else{
                viewHolder.bookingStatusTextView.setVisibility(View.GONE);
            }
            if((bookingListDtoArrayList.get(position).getBookingStatus())== BookingStatusTypes.Confirmed.getBookingStatusVal()&& new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse(bookingListDtoArrayList.get(position).getCheckInDate()).after(new SimpleDateFormat("dd-MMM-yyyy-EEE", Locale.US).parse(Validation.getCurrentDate()))){
                viewHolder.requestCancellationTextView.setVisibility(View.VISIBLE);
            }else{
                viewHolder.requestCancellationTextView.setVisibility(View.GONE);
            }
            viewHolder.viewVoucherTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iMyBookingViewClickListener.viewVoucherClickListener(position);
                }
            });
            viewHolder.requestCancellationTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iMyBookingViewClickListener.requestCancellationClickListener(position);
                }
            });
            viewHolder.emailToUserTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iMyBookingViewClickListener.sendVoucherClickListener(position);
                }
            });
            Glide.with(context)
                    .load(bookingListDtoArrayList.get(position).getListingThumbnailUrl())
                    .error(R.drawable.icn_default_image_loading)
                    .placeholder(R.drawable.icn_default_image_loading)
                    .centerCrop()
                    .crossFade()
                    .into(viewHolder.hotelImageView);
            viewHolder.bookingListDto = bookingListDtoArrayList.get(position);
            if (bookingListDtoArrayList.get(position).getChildrensCount() == 0) {
                viewHolder.childCountTextView.setVisibility(View.GONE);
            } else {

                viewHolder.childCountTextView.setVisibility(View.VISIBLE);
                viewHolder.childCountTextView.setText(context.getResources().getString(R.string.Kids) + " : " + bookingListDtoArrayList.get(position).getChildrensCount());
            }
            viewHolder.adultCountTextView.setText(context.getResources().getString(R.string.Adults) + " : " + bookingListDtoArrayList.get(position).getAdultsCount());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    } // Return the size arraylist

    @Override
    public int getItemCount() {
        return bookingListDtoArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView bookingIdTextView, currentDateTextView, checkInDateTextView, checkOutDateTextView, billingAmountTextView, hotelNameTextView, destinationTextView, adultCountTextView, childCountTextView,viewVoucherTextView,requestCancellationTextView,emailToUserTextView,bookingStatusTextView;
        BookingListDto bookingListDto;
        ImageView hotelImageView;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            hotelImageView = (ImageView) itemLayoutView.findViewById(R.id.hotelImageView);
            bookingIdTextView = (TextView) itemLayoutView.findViewById(R.id.bookingIdTextView);
            currentDateTextView = (TextView) itemLayoutView.findViewById(R.id.currentDateTextView);
            checkInDateTextView = (TextView) itemLayoutView.findViewById(R.id.checkInTextView);
            checkOutDateTextView = (TextView) itemLayoutView.findViewById(R.id.checkOutTextView);
            billingAmountTextView = (TextView) itemLayoutView.findViewById(R.id.billingAmountTextView);
            hotelNameTextView = (TextView) itemLayoutView.findViewById(R.id.hotelNameTextView);
            destinationTextView = (TextView) itemLayoutView.findViewById(R.id.destinationTextView);
            adultCountTextView = (TextView) itemLayoutView.findViewById(R.id.adultCountTextView);
            childCountTextView = (TextView) itemLayoutView.findViewById(R.id.childCountTextView);
            viewVoucherTextView=(TextView)itemLayoutView.findViewById(R.id.viewVoucherTextView);
            requestCancellationTextView=(TextView)itemLayoutView.findViewById(R.id.requestCancellationTextView);
            emailToUserTextView=(TextView)itemLayoutView.findViewById(R.id.emailToUserTextView);
            bookingStatusTextView=(TextView)itemLayoutView.findViewById(R.id.bookingStatusTextView);

        }

    }


}
