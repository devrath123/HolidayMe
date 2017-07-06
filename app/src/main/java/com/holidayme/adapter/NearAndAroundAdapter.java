package com.holidayme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.data.NearandAroundBean;
import com.holidayme.data.UserDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devrath.rathee on 02-03-2017.
 */

public class NearAndAroundAdapter extends RecyclerView.Adapter<NearAndAroundAdapter.NearViewHolder> {

    Context context;
    List<NearandAroundBean.FourSquare> nearAndAroundList = new ArrayList<>();

    public NearAndAroundAdapter(Context context, List<NearandAroundBean.FourSquare> nearandAroundBeanList) {
        this.context = context;
        this.nearAndAroundList.addAll(nearandAroundBeanList);
    }

    @Override
    public int getItemCount() {
        return nearAndAroundList.size();
    }

    @Override
    public void onBindViewHolder(NearViewHolder holder, int position) {

        NearandAroundBean.FourSquare bean = nearAndAroundList.get(position);

      /*  if (bean.getName().length() > 30)
            if (UserDTO.getUserDTO().getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE))
                bean.setName(bean.getName().substring(0, 29) + "...");

            else
                bean.setName(bean.getName().substring(0, 26) + "...");

        holder.nameTextView.setText(bean.getName());*/
        holder.distanceTextView.setText(bean.getDist());

        switch (bean.getCat()) {
            case 1:
                holder.nearImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.icn_mosque));
                holder.nameTextView.setText(context.getString(R.string.nearest_mosque));
                break;

            case 2:
                holder.nearImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.icn_shopping_mal));
                holder.nameTextView.setText(context.getString(R.string.nearest_shopping_mall));
                break;

            case 3:
                holder.nearImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.icn_halal_restaurant));
                holder.nameTextView.setText(context.getString(R.string.nearest_halal_restaurant));
                break;
        }

    }

    @Override
    public NearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.near_and_around_row, null);

        return new NearViewHolder(view);
    }

    public class NearViewHolder extends RecyclerView.ViewHolder {
        ImageView nearImageView;
        TextView nameTextView, distanceTextView;

        public NearViewHolder(View item) {
            super(item);
            nearImageView = (ImageView) item.findViewById(R.id.nearItemImageView);
            nameTextView = (TextView) item.findViewById(R.id.nearNameTextView);
            distanceTextView = (TextView) item.findViewById(R.id.nearDistanceTextView);
        }
    }
}
