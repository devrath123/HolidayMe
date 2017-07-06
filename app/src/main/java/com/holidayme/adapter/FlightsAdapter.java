package com.holidayme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.holidayme.activities.R;
import com.holidayme.data.FlightsBean;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by devrath.rathee on 04-07-2017.
 */

public class FlightsAdapter extends RecyclerView.Adapter<FlightsAdapter.FlightsViewHolder> {

    Context mContext;
    ArrayList<FlightsBean> mFlightsBeanArrayList = new ArrayList<>();
    LayoutInflater mLayoutInflator;

    public FlightsAdapter(Context context, ArrayList<FlightsBean> flightsBeanArrayList) {
        this.mContext = context;
        this.mFlightsBeanArrayList.addAll(flightsBeanArrayList);
        mLayoutInflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public FlightsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.flights_row,null);
        return new FlightsViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mFlightsBeanArrayList.size();
    }

    @Override
    public void onBindViewHolder(FlightsViewHolder holder, int position) {

        FlightsBean flightsBean = mFlightsBeanArrayList.get(position);

        holder.departureCitySmall_TextView.setText(flightsBean.getDepatureCitySmall());
        holder.departureCity_TextView.setText(flightsBean.getDepartureCity());
        holder.departureDate_TextView.setText(flightsBean.getDepartureDate());
        holder.departureTime_TextView.setText(flightsBean.getDepartureTime());
        holder.arrivalCitySmall_TextView.setText(flightsBean.getArrivalCitySmall());
        holder.arrivalCity_TextView.setText(flightsBean.getArrivalCity());
        holder.arrivalDate_TextView.setText(flightsBean.getArrivalDate());
        holder.arrivalTime_TextView.setText(flightsBean.getArrivalTime());
    }

    public static class FlightsViewHolder extends RecyclerView.ViewHolder {
        TextView departureCitySmall_TextView, departureCity_TextView, departureTime_TextView, departureDate_TextView,
                arrivalCitySmall_TextView, arrivalCity_TextView, arrivalTime_TextView, arrivalDate_TextView;

        public FlightsViewHolder(View itemView) {
            super(itemView);
            departureCitySmall_TextView = (TextView) itemView.findViewById(R.id.departureCitySmall_TextView);
            departureCity_TextView = (TextView) itemView.findViewById(R.id.departureCity_TextView);
            departureTime_TextView = (TextView) itemView.findViewById(R.id.departureTime_TextView);
            departureDate_TextView = (TextView) itemView.findViewById(R.id.departureDate_TextView);
            arrivalCitySmall_TextView = (TextView) itemView.findViewById(R.id.arrivalCitySmall_TextView);
            arrivalCity_TextView = (TextView) itemView.findViewById(R.id.arrivalCity_TextView);
            arrivalTime_TextView = (TextView) itemView.findViewById(R.id.arrivalTime_TextView);
            arrivalDate_TextView = (TextView) itemView.findViewById(R.id.arrivalDate_TextView);
        }
    }
}
