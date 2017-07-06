package com.holidayme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.holidayme.activities.R;
import com.holidayme.Constants.Constant;
import com.holidayme.data.CurrencyObject;
import com.holidayme.data.CurrencySectionItem;
import com.holidayme.data.IsSelectAnyLanguage;
import com.holidayme.data.Item;
import com.holidayme.data.UserDTO;

import java.util.List;

public class CustomListView extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Item> itemsList;
    IsSelectAnyLanguage selectLanguageListener;

    ViewHolder listViewHolder;

    public CustomListView(Context context, List<Item> itemsList, IsSelectAnyLanguage listener) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemsList = itemsList;
        this.selectLanguageListener = listener;

    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


       View view = convertView;

        final Item item = itemsList.get(position);
        if (item != null) {
            if (item.isSection()) {
                CurrencySectionItem currencySectionItem = (CurrencySectionItem) item;
                view = layoutInflater.inflate(R.layout.list_item_section, parent);

                view.setOnClickListener(null);
                view.setOnLongClickListener(null);
                view.setLongClickable(false);

                final TextView sectionView = (TextView) view.findViewById(R.id.list_item_section_text);
                sectionView.setText(currencySectionItem.getTitle());

            } else {
                listViewHolder = new ViewHolder();
                final CurrencyObject listStorage = (CurrencyObject) item;
                view = layoutInflater.inflate(R.layout.listview_with_checkbox, parent, false);
                listViewHolder.countryCurrencyTextView = (TextView) view.findViewById(R.id.countryCurrencyTextView);
                listViewHolder.currencyCodeTextView = (TextView) view.findViewById(R.id.currencyCodeTextView);
                listViewHolder.currencyCheckBox = (CheckBox) view.findViewById(R.id.currencyCheckBox);
                listViewHolder.languageRelativeLayout = (RelativeLayout) view.findViewById(R.id.languageRelativeLayout);
                view.setTag(listViewHolder);

                listViewHolder.currencyCheckBox.setChecked(listStorage.isCheck());
                listViewHolder.currencyCheckBox.setTag(position);
                listViewHolder.languageRelativeLayout.setTag(position);
                if (UserDTO.getUserDTO() != null && UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE))
                    listViewHolder.countryCurrencyTextView.setText(listStorage.getArabiclanguage());
                else
                    listViewHolder.countryCurrencyTextView.setText(listStorage.getName());
                listViewHolder.currencyCodeTextView.setText(listStorage.getLanguageCode());

                listViewHolder.languageRelativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (Integer) v.getTag();
                        for (int i = 0; i < itemsList.size(); i++) {
                            if (!itemsList.get(i).isSection()) {
                                Item item = itemsList.get(i);
                                if (i == pos) {
                                    if (((CurrencyObject) item).isCheck()) {
                                        ((CurrencyObject)itemsList.get(i)).setIsCheck(false);
                                        selectLanguageListener.isSelectLanguage(false, "");
                                    } else {
                                        ((CurrencyObject)itemsList.get(i)).setIsCheck(true);
                                        selectLanguageListener.isSelectLanguage(true, ((CurrencyObject) item).getLanguageCode());
                                    }
                                } else {
                                    ((CurrencyObject)itemsList.get(i)).setIsCheck(false);
                                }

                            }
                        }
                        notifyDataSetChanged();
                    }
                });


            }
        }
        return view;
    }


     class ViewHolder {

        TextView countryCurrencyTextView, currencyCodeTextView;
        CheckBox currencyCheckBox;
        RelativeLayout languageRelativeLayout;
    }
}

