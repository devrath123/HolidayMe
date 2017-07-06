package com.holidayme.adapter;

/**
 * Created by supriya.sakore on 17-03-2016.
 */

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.data.CurrencyObject;
import com.holidayme.data.Item;
import com.holidayme.data.UserDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> dataHeaderList;
    private HashMap<String, ArrayList<Item>> dataChildList;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, ArrayList<Item>> listChildData) {
        this.context = context;
        this.dataHeaderList = listDataHeader;
        this.dataChildList = listChildData;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.dataChildList.get(this.dataHeaderList.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final CurrencyObject currencyObject = (CurrencyObject) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.currency_row, parent,false);
        }
        TextView currencyTitleTextView = (TextView) convertView.findViewById(R.id.currencyTitleTextView);
        TextView currencyCodeTextView = (TextView) convertView.findViewById(R.id.text_currency_code);

        try {
            if(UserDTO.getUserDTO().getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE)) {
                currencyTitleTextView.setText(currencyObject.getName());
            }
            else {
                currencyTitleTextView.setText(currencyObject.getArabiclanguage());

            }

            currencyCodeTextView.setText(currencyObject.getLanguageCode());

            currencyTitleTextView.setTextColor(ContextCompat.getColor(context,R.color.black));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.dataChildList.get(this.dataHeaderList.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.dataHeaderList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.dataHeaderList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, parent,false);
        }

        TextView currencyHeaderTextView = (TextView) convertView.findViewById(R.id.currencyHeaderTextView);
        currencyHeaderTextView.setText(headerTitle);

        ((ImageView) convertView.findViewById(R.id.explist_group_indicator)).setImageResource(isExpanded ? R.drawable.arrow_d : R.drawable.arrow2_d);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}