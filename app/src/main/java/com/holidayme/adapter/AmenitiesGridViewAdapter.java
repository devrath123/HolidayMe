package com.holidayme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.holidayme.staycation_details_mvp.StaycationDetailsActivity;
import com.holidayme.activities.R;
import com.holidayme.data.AmenitiesDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devrath.rathee on 02-03-2017.
 */

public class AmenitiesGridViewAdapter extends BaseAdapter {

    Context context;
    List<AmenitiesDTO.Amenity> amenitiesDTOList = new ArrayList<>();
    LayoutInflater layoutInflater;

    public AmenitiesGridViewAdapter(Context context, List<AmenitiesDTO.Amenity> amenitiesDTOList,boolean loadMoreFlag)
    {
        this.context = context;
        this.amenitiesDTOList.addAll(amenitiesDTOList);

        if(loadMoreFlag) {
            this.amenitiesDTOList.remove(5);
            this.amenitiesDTOList.add(new AmenitiesDTO.Amenity(0, 0, context.getResources().getString(R.string.six_more), "", ""));
        }
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return amenitiesDTOList.size();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return amenitiesDTOList.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final ViewHolder viewHolder ;
        final AmenitiesDTO.Amenity amenitiesDTO = amenitiesDTOList.get(i);

        if(view == null)
        {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.amenities_item,null);
            viewHolder.amenityImageView =  (ImageView) view.findViewById(R.id.amenityImageView);
            viewHolder.amenityTextView = (TextView) view.findViewById(R.id.amenityTextView);
            viewHolder.amenityRelativeLayout = (RelativeLayout) view.findViewById(R.id.amenityRelativeLayout);

            view.setTag(viewHolder);
        }
        else
          viewHolder = (ViewHolder) view.getTag();

      if(amenitiesDTO.getUrl().equals("")) {
          viewHolder.amenityImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.more_icon));
          viewHolder.amenityTextView.setTextColor(context.getResources().getColor(R.color.light_purple));
      }
        else {
          Glide.with(context)
                  .load(amenitiesDTO.getUrl())
                  .error(R.drawable.icn_default_image_loading)
                  .placeholder(R.drawable.icn_default_image_loading)
                  .into(viewHolder.amenityImageView);
      }
        viewHolder.amenityTextView.setText(amenitiesDTO.getTtl());

        viewHolder.amenityRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewHolder.amenityTextView.getText().equals(context.getResources().getString(R.string.six_more)))
                {
                    ((StaycationDetailsActivity)context).updateAdapter(6);
                }
            }
        });

        return view;
    }

    public class ViewHolder
    {
        TextView amenityTextView;
        ImageView amenityImageView;
        RelativeLayout amenityRelativeLayout;
    }
}
