package com.holidayme.adapter;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.holidayme.activities.R;
import com.holidayme.data.SpecialRequestSelectedDto;
import com.holidayme.data.SpecialRequestsDto;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 25-08-2015.
 */
public class SpecialRequestAdapter extends RecyclerView.Adapter<SpecialRequestAdapter.ViewHolder> {

    private ArrayList<SpecialRequestsDto> specialRequestsDtoArrayList;
    private ArrayList<SpecialRequestSelectedDto> specialRequestSelectedDtoArrayList = new ArrayList<>();

    public SpecialRequestAdapter(ArrayList<SpecialRequestsDto> specialRequestsDtoArrayList) {
        this.specialRequestsDtoArrayList = specialRequestsDtoArrayList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {

        View specialRequestRowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_sp_rquest_row, null);
        ViewHolder viewHolder = new ViewHolder(specialRequestRowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        viewHolder.requestTextView.setText(specialRequestsDtoArrayList.get(position).getDescription());

        viewHolder.specialRequestsDto = specialRequestsDtoArrayList.get(position);

        viewHolder.specialRequestCheckBox.setChecked(specialRequestsDtoArrayList.get(position).isCheck());
        viewHolder.specialRequestCheckBox.setTag(position);

        viewHolder.specialRequestRelativeLayout.setTag(position);

        viewHolder.specialRequestRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean specialRequestChecked = false;
                int pos = (Integer) v.getTag();

                if (specialRequestsDtoArrayList.get(pos).isCheck()) {
                    specialRequestsDtoArrayList.get(pos).setCheck(false);
                } else {
                    specialRequestsDtoArrayList.get(pos).setCheck(true);
                }

                for (int i = 0; i < specialRequestsDtoArrayList.size(); i++) {
                    if (specialRequestsDtoArrayList.get(i).isCheck()) {
                        specialRequestChecked = true;
                        break;
                    }
                }
                Message message = new Message();

                Bundle bundle = new Bundle();

                bundle.putBoolean("ISCHECK", specialRequestChecked);
                bundle.putParcelableArrayList("SpecialRequest",specialRequestsDtoArrayList);
                message.setData(bundle);
                viewHolder.specialRequestCheckBox.setChecked(specialRequestsDtoArrayList.get(pos).isCheck());
                viewHolder.specialRequestCheckBox.setChecked(specialRequestsDtoArrayList.get(pos).isCheck());
                notifyDataSetChanged();
            }
        });
        if (!specialRequestSelectedDtoArrayList.isEmpty())
            specialRequestSelectedDtoArrayList.clear();
        for (int i = 0; i < specialRequestsDtoArrayList.size(); i++) {
           SpecialRequestSelectedDto specialRequestSelectedDto = new SpecialRequestSelectedDto();
            if(specialRequestsDtoArrayList.get(position).isCheck())
            specialRequestSelectedDto.setTitle(specialRequestsDtoArrayList.get(position).getDescription());
            specialRequestSelectedDtoArrayList.add(specialRequestSelectedDto);
        }

    }

    @Override
    public int getItemCount() {
        return specialRequestsDtoArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView requestTextView;
        public CheckBox specialRequestCheckBox;
        public SpecialRequestsDto specialRequestsDto;
        public RelativeLayout specialRequestRelativeLayout;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            requestTextView =(TextView) itemLayoutView.findViewById(R.id.txt_sp_req);
            specialRequestCheckBox =(CheckBox) itemLayoutView.findViewById(R.id.check_sp_req);
            specialRequestRelativeLayout =(RelativeLayout)itemLayoutView.findViewById(R.id.sp_req_layout);
        }

    }


}
