package com.holidayme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.holidayme.activities.R;
import com.holidayme.data.HolidayCityBean;

import java.util.ArrayList;

/**
 * Created by devrath.rathee on 05-07-2017.
 */

public class RoomOptionsAdapter extends RecyclerView.Adapter<RoomOptionsAdapter.RoomOptionViewHolder>{

    Context mContext;
    ArrayList<HolidayCityBean.RoomOptions> mRoomOptionsArrayList = new ArrayList<>();
    LayoutInflater mLayoutInflator;

    public void RoomOptionsAdapter(Context context, ArrayList<HolidayCityBean.RoomOptions> roomOptionsArrayList)
    {
        this.mContext = context;
        mRoomOptionsArrayList.addAll(roomOptionsArrayList);
        mLayoutInflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return mRoomOptionsArrayList.size();
    }

    @Override
    public void onBindViewHolder(RoomOptionViewHolder holder, int position) {

        HolidayCityBean.RoomOptions roomOptions = mRoomOptionsArrayList.get(position);

        holder.roomTypeTextView.setText(roomOptions.getRoomType());
        holder.roomCostTextView.setText(roomOptions.getRoomCost());
    }

    @Override
    public RoomOptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.room_option_row,null);
        return new RoomOptionViewHolder(view);
    }

    public static class RoomOptionViewHolder extends RecyclerView.ViewHolder
    {
        TextView roomTypeTextView,roomCostTextView;
        CheckBox roomCheckbox;

        public RoomOptionViewHolder(View itemView)
        {
            super(itemView);
            roomTypeTextView = (TextView) itemView.findViewById(R.id.roomTypeTextView);
            roomCostTextView = (TextView) itemView.findViewById(R.id.roomCostTextView);
            roomCheckbox = (CheckBox) itemView.findViewById(R.id.roomCheckBox);
        }
    }
}
