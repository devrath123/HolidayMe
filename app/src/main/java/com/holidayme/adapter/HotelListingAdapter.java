package com.holidayme.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.facebook.appevents.AppEventsLogger;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.Utilities;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.common.Validation;
import com.holidayme.data.HotelsDto;
import com.holidayme.data.UserDTO;
import com.holidayme.fragments.HotelListingFragment;
import com.holidayme.gtm.GTMAnalytics;
import com.holidayme.request.HotelListingRequest;
import com.holidayme.response.HotelListingInfoResponse;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;



/**
 * Created by santosh.patar on 20-08-2015.
 */
public class HotelListingAdapter extends RecyclerView.Adapter<HotelListingAdapter.ViewHolder> {

    private ArrayList<HotelsDto> hotelsArrayList;
    private Context context;
    private int night = 1,advancePurchase,lastPosition=1;
    private AppEventsLogger appEventsLogger;
    private String EVENT_CATEGORY = "Listing page";
    private Long hotelID;
    private HotelListingInfoResponse hotelListingInfoResponse;
    public GTMAnalytics gtmAnalytics;
    private View itemLayoutView;
    private Date dateTo, dateFrom;
    private  HotelListingFragment hotelListingFragment;
    int hight;


    public HotelListingAdapter(HotelListingInfoResponse mHotelListingResponse, ArrayList<HotelsDto> stList, Context context ,GTMAnalytics gtmAnalytics,int hight ) {

        this.hotelListingInfoResponse = mHotelListingResponse;
        this.hotelsArrayList = stList;
        this.context = context;
        this.gtmAnalytics = gtmAnalytics;

        this.hight=hight;

    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
      itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_list_row, null);

        appEventsLogger=AppEventsLogger.newLogger(context);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Calendar calendarCheckIn = new GregorianCalendar();
        Calendar calendarCheckOut = new GregorianCalendar();


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);


        try {

            dateTo = simpleDateFormat.parse(simpleDateFormat.format(new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).parse(HotelListingRequest.getHotelListRequest().getCheckInDate())));
            calendarCheckOut.setTime(dateTo);
            dateFrom = simpleDateFormat.parse(simpleDateFormat.format(new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).parse(HotelListingRequest.getHotelListRequest().getCheckOutDate())));
            calendarCheckIn.setTime(dateFrom);
            night = NetworkUtilities.daysBetween(calendarCheckIn.getTime(), calendarCheckOut.getTime());

            calendarCheckIn.setTime( simpleDateFormat.parse(simpleDateFormat.format(new SimpleDateFormat("dd-MMM-yyyy-EEE",Locale.ENGLISH).parse(Validation.getCurrentDate()))));
            advancePurchase=NetworkUtilities.daysBetween(calendarCheckOut.getTime(), calendarCheckIn.getTime());

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            viewHolder.hotelNameTextView.setText(hotelsArrayList.get(position).getTtl());
            viewHolder.hotelLocationTextView.setText(hotelsArrayList.get(position).getBasicInfo().getCnty());

            Glide.with(context)
                    .load(hotelsArrayList.get(position).getBasicInfo().getLImg().get(0))
                    .error(R.drawable.icn_default_image_loading)
                    .override(600,200)
                    .placeholder(R.drawable.icn_default_image_loading)
                    .centerCrop()
                    .into(viewHolder.hotelImageView);
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);

            viewHolder.hotelsDto = hotelsArrayList.get(position);
            UserDTO userDTO = UserDTO.getUserDTO();

            if (hotelsArrayList.get(position).getBasicInfo().isPAH()) {
                viewHolder.payAtHotelTextView.setVisibility(View.VISIBLE);
            } else {
                viewHolder.payAtHotelTextView.setVisibility(View.INVISIBLE);
            }

            if (hotelsArrayList.get(position).getBasicInfo().getTripAdvisor().getRevCnt() == 0) {
                viewHolder.reviewTextView.setVisibility(View.INVISIBLE);
            } else {

                viewHolder.reviewTextView.setVisibility(View.VISIBLE);
                viewHolder.reviewTextView.setText(hotelsArrayList.get(position).getBasicInfo().getTripAdvisor().getRevCnt() + " " + context.getString(R.string.Reviews));
            }

            viewHolder.hotelNameTextView.setText(hotelsArrayList.get(position).getTtl());
            viewHolder.hotelLocationTextView.setText(hotelsArrayList.get(position).getBasicInfo().getCty() + " " + hotelsArrayList.get(position).getBasicInfo().getAdrs());
            Utilities.setStarRating(hotelsArrayList.get(position).getBasicInfo().getStar(),context,viewHolder.itemView);
         //   viewHolder.distanceTextView.setText(hotelsArrayList.get(position).getBasicInfo().getDist());

            if (hotelsArrayList.get(position).getPrice() != 0 || hotelsArrayList.get(position).getPrice() != 0.00) {
                viewHolder.packageCurrencyTextView.setText(userDTO.getCurrency() );
                viewHolder.priceTextView.setText(" " +formatter.format( Math.round(hotelsArrayList.get(position).getPrice())) + "");
                viewHolder.priceTextView.setText(" " +nf.format( Math.round(hotelsArrayList.get(position).getPrice())) + "");

            }

            if (hotelsArrayList.get(position).getBasicInfo().getTripAdvisor().getRating() != 0 || hotelsArrayList.get(position).getBasicInfo().getTripAdvisor().getRating() != 0.0) {

                viewHolder.tripAdvisorRatingImageView.setVisibility(View.VISIBLE);
                new Utilities().setTripAdviserRating(viewHolder.tripAdvisorRatingImageView, (int) hotelsArrayList.get(position).getBasicInfo().getTripAdvisor().getRating());
            } else {
                viewHolder.tripAdvisorRatingImageView.setVisibility(View.GONE);

            }


            viewHolder. hotelSlashPriceTextView.setPaintFlags(viewHolder.hotelSlashPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

           if(hotelsArrayList.get(position).getSlashedPrice()!=0){
               viewHolder.hotelSlashPriceTextView.setVisibility(View.VISIBLE);
               viewHolder.hotelSlashPriceTextView.setText(userDTO.getCurrency()+" "+formatter.format((int) hotelsArrayList.get(position).getSlashedPrice()));
               viewHolder.hotelSlashPriceTextView.setText(userDTO.getCurrency()+" "+nf.format((int) hotelsArrayList.get(position).getSlashedPrice()));

           }

/*

          if(hotelListingInfoResponse.getTaxLabel()!=null&&!hotelListingInfoResponse.getTaxLabel().equals("")){
              viewHolder.perNightTextView.setText(hotelListingInfoResponse.getTaxLabel());
          }else{
                viewHolder.perNightTextView.setText(context.getString(R.string.Taxes_included));
        }
*/


            if(Constant.animationFlag)
            setAnimation(viewHolder.itemView,position);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.setMargins(3, 3, 3, hight); // llp.setMargins(left, top, right, bottom);

            if(hotelsArrayList.size()==3&&hotelsArrayList.size()==position+1 )
                viewHolder.listing_row.setLayoutParams(llp);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAnimation(View viewHolder, int position) {

        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.bottom_to_up_listing_animation);
            viewHolder.startAnimation(animation);
            lastPosition = position;
        }


    }



    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {

        holder.itemView.clearAnimation();
    }

    // Return the size arrayList
    @Override
    public int getItemCount() {
        return hotelsArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView hotelNameTextView, payAtHotelTextView,packageCurrencyTextView, tripAdviserRatingTextView, perNightTextView, priceTextView, reviewTextView, hotelLocationTextView,
                hotelSlashPriceTextView,distanceTextView;
        private ImageView hotelImageView,tripAdvisorRatingImageView;
        LinearLayout listing_row;

        private HotelsDto hotelsDto;

        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);

            hotelNameTextView = (TextView) itemLayoutView.findViewById(R.id.hotelNameTextView);
            priceTextView = (TextView) itemLayoutView.findViewById(R.id.priceTextView);
            reviewTextView = (TextView) itemLayoutView.findViewById(R.id.reviewTextView);
            hotelLocationTextView = (TextView) itemLayoutView.findViewById(R.id.hotelLocationTextView);
            payAtHotelTextView = (TextView) itemLayoutView.findViewById(R.id.payAtHotelTextView);
            hotelImageView = (ImageView) itemLayoutView.findViewById(R.id.hotelImageView);
            tripAdvisorRatingImageView=(ImageView)itemLayoutView.findViewById(R.id.tripAdvisorRatingImageView);
            packageCurrencyTextView=(TextView) itemLayoutView.findViewById(R.id.packageCurrencyTextView);
            hotelSlashPriceTextView=(TextView)itemLayoutView.findViewById(R.id.hotelSlashPriceTextView);

           // tripAdviserRatingTextView = (TextView) itemLayoutView.findViewById(R.id.tripAdviserRatingTextView);
        //    perNightTextView = (TextView) itemLayoutView.findViewById(R.id.perNightTextView);
          //  distanceTextView = (TextView) itemLayoutView.findViewById(R.id.distanceTextView);
       //     listing_row=(LinearLayout)itemLayoutView.findViewById(R.id.listing_row);


            // Onclick event for the row to show the data in toast
            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    gtmAnalytics = AppController.getInstance().getGTMAnalytics((Activity) itemLayoutView.getContext());
                    gtmAnalytics.sendEvent("HotelListing Screen - " + UserDTO.getUserDTO().getDestinationName(), "Hotel section - " + hotelNameTextView.getText(), "Proceed to hotel details");

                    Utilities.pushFacebookEvent(appEventsLogger, EVENT_CATEGORY, "Hotel section", "Proceed to hotel details");

                    int adultCount = 0, childCount = 0, traveller;

                    for (int i = 0; i < HotelListingRequest.getHotelListRequest().getOccupancy().size(); i++) {

                        adultCount = adultCount + HotelListingRequest.getHotelListRequest().getOccupancy().get(i).getNoOfAdults();
                        childCount = childCount + HotelListingRequest.getHotelListRequest().getOccupancy().get(i).getChildAges().size();
                    }

                    traveller = adultCount + childCount;


                    if (AppController.getInstance().getCleverTapInstance() != null) {

                        HashMap<String, Object> cleverTapHashMap = new HashMap<>();
                        cleverTapHashMap.put("LOB", "Hotels");
                        cleverTapHashMap.put("Destination", hotelsDto.getBasicInfo().getCty());
                        cleverTapHashMap.put("Destination ID", hotelsDto.getBasicInfo().getCntyId());
                        cleverTapHashMap.put("From Date", dateFrom);
                        cleverTapHashMap.put("To Date", dateTo);
                        cleverTapHashMap.put("Advance Purchase", advancePurchase);
                        cleverTapHashMap.put("Travel Nights", night);
                        cleverTapHashMap.put("Number of rooms", HotelListingRequest.getHotelListRequest().getOccupancy().size());
                        cleverTapHashMap.put("Number of passengers", traveller);
                        cleverTapHashMap.put("Currency", UserDTO.getUserDTO().getCurrency());
                        cleverTapHashMap.put("Product Name", hotelsDto.getTtl());
                        cleverTapHashMap.put("Product ID", hotelsDto.getId());

                        if (hotelListingFragment != null) {

                            hotelID = hotelsDto.getId();
                            hotelListingFragment.onHotelLisItemClick(hotelID, cleverTapHashMap);
                        }
                    }
                }

            });


        }
    }

        public void setOnItemClickListener(HotelListingFragment hotelListingFragment) {
            this.hotelListingFragment = hotelListingFragment;
        }


}
