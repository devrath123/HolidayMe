package com.holidayme.activities.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.data.HotelsDto;
import com.holidayme.data.UserDTO;
import com.holidayme.gtm.GTMAnalytics;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by santosh.patar on 28-08-2015.
 */
public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private View contentsView;
    private Context context;
    private ArrayList<HotelsDto> hotelsDtoArrayList;
    private String PriceLable;
    private ImageLoader imageLoader;
    private ImageView hotelImageView;
    private TextView tripAdviserRatingImageTextView;
    private TextView hotelNameTextView, priceTextView;
    private String imageUrlStore;
    private Bitmap bitmap;
    private GTMAnalytics gtmAnalytics;



    public MyInfoWindowAdapter(Context context, String PriceLable, ArrayList<HotelsDto> HotelMaps, GTMAnalytics gtmAnalytics) {
        super();
        this.context = context;
        this.hotelsDtoArrayList = HotelMaps;

        this.gtmAnalytics=gtmAnalytics;
        this.PriceLable=PriceLable;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        imageLoader = ImageLoader.getInstance();

        contentsView = layoutInflater.inflate(R.layout.map_view_info_window, null);
        initialiseWidgets();
    }


    @Override
    public View getInfoWindow(Marker marker) {

        hotelImageView.setImageResource(R.drawable.icn_default_icon);

        return null;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View getInfoContents(final Marker marker) {

       int  snippetPostion = Integer.parseInt(marker.getSnippet());

        try {

            String uri = hotelsDtoArrayList.get(snippetPostion).getBasicInfo().getLImg().get(0);

            if(uri.equals(imageUrlStore)){

                hotelImageView.setImageBitmap(bitmap);

            }else{

                imageLoader.loadImage(uri, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                        hotelImageView.setImageBitmap(loadedImage);
                        bitmap = loadedImage;
                        if (marker != null && marker.isInfoWindowShown()) {
                            marker.showInfoWindow();
                            gtmAnalytics.sendEvent("Map", "hotel pin", "Hotel info condensed view");
                        }

                    }
                });
                imageUrlStore = uri;

            }


            Utilities.setStarRating((float) hotelsDtoArrayList.get(snippetPostion).getBasicInfo().getStar(), context, contentsView);


            if(UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
                hotelNameTextView.setText("\u200e" + hotelsDtoArrayList.get(snippetPostion).getTtl());
            }
            else {
                hotelNameTextView.setText(hotelsDtoArrayList.get(snippetPostion).getTtl());
            }

            if(hotelsDtoArrayList.get(snippetPostion).getBasicInfo().getTripAdvisor().getRating() == 0.0 || hotelsDtoArrayList.get(snippetPostion).getBasicInfo().getTripAdvisor().getRating()==0){
                tripAdviserRatingImageTextView.setVisibility(View.INVISIBLE);

            }else {
                tripAdviserRatingImageTextView.setVisibility(View.VISIBLE);
                tripAdviserRatingImageTextView.setText(String.format(Locale.US, "%.2f", hotelsDtoArrayList.get(snippetPostion).getBasicInfo().getTripAdvisor().getRating()));
            }

            priceTextView.setText(UserDTO.getUserDTO().getCurrency() + " " +String.format(Locale.US, "%.2f", hotelsDtoArrayList.get(snippetPostion).getPrice()) + " " +PriceLable);

        }catch(Exception e){
            e.printStackTrace();
        }

        return contentsView;
    }

  private void  initialiseWidgets()
    {
        hotelNameTextView = ((TextView) contentsView.findViewById(R.id.hotelNameTextView));
        priceTextView = ((TextView) contentsView.findViewById(R.id.priceTextView));
        hotelImageView=(ImageView) contentsView.findViewById(R.id.hotelImageView);
        tripAdviserRatingImageTextView=(TextView) contentsView.findViewById(R.id.tripAdviserRatingImageTextView);
    }
}
