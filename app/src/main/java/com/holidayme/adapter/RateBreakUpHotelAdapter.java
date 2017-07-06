package com.holidayme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.holidayme.activities.R;
import com.holidayme.data.HotelRoomBooking;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by devrath.rathee on 28-04-2017.
 */

public class RateBreakUpHotelAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<HotelRoomBooking> mHotelRoomArrayList;
    LayoutInflater layoutInflater;

    public RateBreakUpHotelAdapter(Context context, ArrayList<HotelRoomBooking> roomArrayList)
    {
        this.mContext = context;
        mHotelRoomArrayList = new ArrayList<>();
        mHotelRoomArrayList.addAll(roomArrayList);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mHotelRoomArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mHotelRoomArrayList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

         ViewHolder viewHolder = null;
         HotelRoomBooking hotelRoomBooking = mHotelRoomArrayList.get(position);

        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.hotel_rate_breakup,null);

            viewHolder.roomNoTextView = (TextView) convertView.findViewById(R.id.roomNo_HotelBreakUp_TextView);
            viewHolder.confirmationNoTextView = (TextView) convertView.findViewById(R.id.confirmationNumber_HotelBreakUp_TextView);
            viewHolder.travellersTextView = (TextView) convertView.findViewById(R.id.travellersCount_HotelBreakUp_TextView);
            viewHolder.netChargesTextView = (TextView) convertView.findViewById(R.id.netChargesValue_HotelBreakUp_TextView);
            viewHolder.taxRecoveryChargesTextView = (TextView) convertView.findViewById(R.id.taxRecoveryValue_HotelBreakUp_TextView);
            viewHolder.totalChargesTextView = (TextView) convertView.findViewById(R.id.totalValue_HotelBreakUp_TextView);

            convertView.setTag(viewHolder);
        }
        else
          viewHolder = (ViewHolder) convertView.getTag();


        viewHolder.roomNoTextView.setText(mContext.getResources().getString(R.string.room) + " " + (position + 1));
        viewHolder.confirmationNoTextView.setText(hotelRoomBooking.getSupplierConfirmationNo());
        viewHolder.travellersTextView.setText(hotelRoomBooking.getAdultsCount() + " " + mContext.getResources().getString(R.string.Adults) );
        viewHolder.netChargesTextView.setText(String.format(Locale.US, "%.2f",hotelRoomBooking.getNetRate()));
        viewHolder.taxRecoveryChargesTextView.setText(String.format(Locale.US, "%.2f",hotelRoomBooking.getTaxesAndFees()));
        viewHolder.totalChargesTextView.setText(String.format(Locale.US, "%.2f",hotelRoomBooking.getSellingRate()) );

        return convertView;
    }

    public class ViewHolder
    {
        TextView roomNoTextView,confirmationNoTextView,travellersTextView,netChargesTextView,
                 taxRecoveryChargesTextView,totalChargesTextView;
    }
}
