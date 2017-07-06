package com.holidayme.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.holidayme.AppInterface.AutoCompleteClickListener;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.util.HolidayMeFont;
import com.holidayme.data.Destination;
import com.holidayme.data.UserDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaikh.salim on 3/11/2016.
 */
public class RecentSearchAdapter extends BaseAdapter {

    private ViewHolder viewHolder;
    private Context context;
    private List<Destination> resultList = new ArrayList<>();
    private AutoCompleteClickListener autoCompleteClickListener;
    private boolean isNearMe = false;

    public RecentSearchAdapter(Context context, AutoCompleteClickListener autoCompleteClickListener, List<Destination> resultList, boolean isNearMe) {
        this.context = context;
        this.autoCompleteClickListener = autoCompleteClickListener;
        this.resultList = resultList;
        this.isNearMe = isNearMe;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Destination getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.recentsearch_rowlayout, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.destination = (TextView) convertView.findViewById(R.id.text2);
            convertView.setTag(viewHolder);

            if(UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)){
                HolidayMeFont.overrideFonts(context, viewHolder.title, Constant.NotoKufiArabic_Regular);
                HolidayMeFont.overrideFonts(context, viewHolder.destination, Constant.NotoKufiArabic_Regular);
            }else{
                HolidayMeFont.overrideFonts(context, viewHolder.destination, Constant.HelveticaNeueRoman);
                HolidayMeFont.overrideFonts(context, viewHolder.title, Constant.HelveticaNeueRoman);
            }


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            if (isNearMe) {
                viewHolder.title.setText(context.getString(R.string.city));


            } else {

                viewHolder.title.setText(context.getString(R.string.city) + "/" +context.getString(R.string.Area)  + "/ "+context.getString(R.string.hotel));
            }
            viewHolder.title.setVisibility(View.VISIBLE);
        } else {
            viewHolder.title.setVisibility(View.GONE);
        }

      viewHolder.destination.setText(getItem(position).getDestinationName());
        viewHolder.destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoCompleteClickListener != null)
                    autoCompleteClickListener.selectedItem(getItem(position));
            }
        });
        return convertView;
    }



    class ViewHolder {

         TextView title,destination;

    }
}
