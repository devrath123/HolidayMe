package com.holidayme.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.holidayme.activities.R;
import com.holidayme.data.HotelRoomBooking;
import com.holidayme.data.UserDTO;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by santosh.patar on 22-09-2015.
 */
public class RateBreakUPAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<HotelRoomBooking> hotelRoomBookingArrayList;
    private String CurrencyType;
    private int size = 0;

    public RateBreakUPAdapter(String CurrencyType, ArrayList<HotelRoomBooking> hotelRoomBookingArrayList, Context context) {
        this.context = context;
        this.hotelRoomBookingArrayList = hotelRoomBookingArrayList;

        if(CurrencyType!= null) {
            this.CurrencyType = CurrencyType;
        }else{
            this.CurrencyType = UserDTO.getUserDTO().getCurrency();
        }

        if(hotelRoomBookingArrayList != null){
            size =  hotelRoomBookingArrayList.size();
        }

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return size;
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
    public View getView(final int position, View convertview, ViewGroup arg2) {

        final ViewHolder holder;

        if (convertview == null) {

            holder = new ViewHolder();
            convertview = layoutInflater.inflate(R.layout.item_hotel_room_bookings, null);

            holder.roomTextView = (TextView) convertview.findViewById(R.id.room_text);
            holder.travellerTextView = (TextView) convertview.findViewById(R.id.Traveller_text);
            holder.totalRateTextView =(TextView)convertview.findViewById(R.id.Total_text);

            convertview.setTag(holder);

        } else
            holder = (ViewHolder) convertview.getTag();

        try {

            holder.roomTextView.setText(position+1 + "");
            if(hotelRoomBookingArrayList.get(position).getChildrensCount()!=0)
            {
                if(hotelRoomBookingArrayList.get(position).getAdultsCount()>1&& hotelRoomBookingArrayList.get(position).getChildrensCount()>1)
                holder.travellerTextView.setText(hotelRoomBookingArrayList.get(position).getAdultsCount()+" "+ context.getString(R.string.Adults)+" , "+ hotelRoomBookingArrayList.get(position).getChildrensCount()+" "+ context.getString(R.string.Kids));

                else if(hotelRoomBookingArrayList.get(position).getAdultsCount()==1&& hotelRoomBookingArrayList.get(position).getChildrensCount()>1)
                    holder.travellerTextView.setText(hotelRoomBookingArrayList.get(position).getAdultsCount() + context.getString(R.string.Adult)+" , "+ hotelRoomBookingArrayList.get(position).getChildrensCount()+" "+ context.getString(R.string.Kids));

                else if(hotelRoomBookingArrayList.get(position).getAdultsCount()>1&& hotelRoomBookingArrayList.get(position).getChildrensCount()==1)
                holder.travellerTextView.setText(hotelRoomBookingArrayList.get(position).getAdultsCount() + context.getString(R.string.Adult)+" , "+ hotelRoomBookingArrayList.get(position).getChildrensCount()+" "+ context.getString(R.string.Kid));

                else
                    holder.travellerTextView.setText(hotelRoomBookingArrayList.get(position).getAdultsCount() + context.getString(R.string.Adults)+" , "+ hotelRoomBookingArrayList.get(position).getChildrensCount()+" "+ context.getString(R.string.Kids));

            }

            else {
                if(hotelRoomBookingArrayList.get(position).getAdultsCount()==1)
                holder.travellerTextView.setText(hotelRoomBookingArrayList.get(position).getAdultsCount() + " " + context.getString(R.string.Adult));

                else
                    holder.travellerTextView.setText(hotelRoomBookingArrayList.get(position).getAdultsCount() + " " + context.getString(R.string.Adults));

            }

            holder.totalRateTextView.setText(CurrencyType +" "  + String.format(Locale.US, "%.2f", hotelRoomBookingArrayList.get(position).getSellingRate()) + "");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertview;
    }

    public class ViewHolder {
        private TextView totalRateTextView, travellerTextView, roomTextView;
    }
}