package com.holidayme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.data.UserDTO;
import com.holidayme.staycationcustomcalender.DayDetailsAdapter;


/**
 * Created by devrath.rathee on 20-03-2017.
 */

public class StaycationAllocationAdapter extends BaseAdapter {

    Context context;
    int allocations;
    LayoutInflater layoutInflater;
    boolean[] allocationArray;
    AllocationListener mAllocationListener;


    public StaycationAllocationAdapter(Context context, int allocationCount) {
        this.context = context;
        allocations = allocationCount;
        allocationArray = new boolean[allocationCount];
        for (int i = 0; i < allocationCount; i++) {
            if(i==0)
                allocationArray[i] = true;

            else
            allocationArray[i] = false;
        }
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return allocationArray.length;
    }

    @Override
    public Object getItem(int i) {
        return allocationArray[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.allocation_row, null);

            if(UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE))
                view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

            viewHolder.allocationCheckBox = (CheckBox) view.findViewById(R.id.numberOfAllocationCheckBox);

            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();

        viewHolder.allocationCheckBox.setText("" + (position+1));

        viewHolder.allocationCheckBox.setChecked(allocationArray[position]);

        viewHolder.allocationCheckBox.setId(position);
        viewHolder.allocationCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i=0;i<allocationArray.length;i++)
                {
                    if(position == i)
                        allocationArray[i] = true;

                    else
                        allocationArray[i] = false;
                }

                notifyDataSetChanged();

                mAllocationListener.onAllocationSelected(position+1);


            }
        });

        return view;
    }

    public class ViewHolder {
        CheckBox allocationCheckBox;
    }

    public interface AllocationListener{
        void onAllocationSelected(int position);
    }

    public void setSelectedAllocation(AllocationListener allocationListener)
    {
        this.mAllocationListener = allocationListener;
    }
}
