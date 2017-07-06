package com.holidayme.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.facebook.appevents.AppEventsLogger;
import com.holidayme.AppInterface.OnAdultsCountChnageListener;
import com.holidayme.AppInterface.OnItemClick;
import com.holidayme.AppInterface.OnKidsCountChnageListener;
import com.holidayme.activities.R;
import com.holidayme.activities.util.Utilities;
import com.holidayme.data.HotelAccommodation;
import com.holidayme.gtm.GTMAnalytics;
import com.holidayme.widgets.HorizontalNumberPicker;
import com.holidayme.widgets.KIDSHorizontalNumberPicker;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 25-08-2015.
 */
public class AddRoomAdapter extends RecyclerView.Adapter<AddRoomAdapter.ViewHolder> {

    private ArrayList<HotelAccommodation> hotelAccommodationsArrayList = new ArrayList<>();
    private Context context;
    private int expandPosition;
    private OnItemClick onItemClick;
    private GTMAnalytics gtmAnalytics;
    private AppEventsLogger appEventsLogger ;
    private String EVENT_CATEGORY = "Hotel Index";
    private FragmentManager fragmentManager;


    public AddRoomAdapter(ArrayList<HotelAccommodation> hotelAccommodationsArrayList, Context context, OnItemClick onItemClick, GTMAnalytics gtmAnalytics, FragmentManager fragmentManager) {
        for (int i = 0; i < hotelAccommodationsArrayList.size(); i++) {
            HotelAccommodation hotelAccommodation = new HotelAccommodation();
            hotelAccommodation.setAdultsCount(hotelAccommodationsArrayList.get(i).getAdultsCount());
            hotelAccommodation.setChildrenAges(hotelAccommodationsArrayList.get(i).getChildrenAges());
            hotelAccommodation.setKid1Age(hotelAccommodationsArrayList.get(i).getKid1Age());
            hotelAccommodation.setKid2Age(hotelAccommodationsArrayList.get(i).getKid2Age());
            hotelAccommodation.setKids(hotelAccommodationsArrayList.get(i).getKids());
            this.hotelAccommodationsArrayList.add(hotelAccommodation);
            this.fragmentManager =fragmentManager;
        }
        this.context = context;
        expandPosition = hotelAccommodationsArrayList.size() - 1;
        this.onItemClick = onItemClick;
        this.gtmAnalytics = gtmAnalytics;
    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_room_row, parent, false);

        // create ViewHolder
        appEventsLogger = AppEventsLogger.newLogger(context);

        return new ViewHolder(itemLayoutView);
    }

   private HotelAccommodation mHotelAccommodation;

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        viewHolder.roomCountTextView.setText(context.getResources().getString(R.string.room) + " " + (position + 1));

        if (hotelAccommodationsArrayList.size() == 1) {
            expandPosition = 0;
        }
        if (expandPosition == position) {
             new Utilities().expand(viewHolder.windowRelativeLayout);
            if (hotelAccommodationsArrayList.size() < 6 && getTotalCount() < 24) {
                viewHolder.addRoomTextView.setTextColor(ContextCompat.getColor(context,R.color.colorRed));
                viewHolder.addRoomTextView.setTag(true);
            } else {
                viewHolder.addRoomTextView.setTextColor(ContextCompat.getColor(context,R.color.light_gray_text_color));
                viewHolder.addRoomTextView.setTag(false);
            }
            mHotelAccommodation = hotelAccommodationsArrayList.get(position);
            viewHolder.adultCountNumberPicker.setDigit(mHotelAccommodation.getAdultsCount());
            viewHolder.kidCountNumberPicker.setDigit(mHotelAccommodation.getKids());

            int kids = mHotelAccommodation.getKids();
            if (kids == 0) {
                viewHolder.dividerView.setVisibility(View.GONE);
                viewHolder.firstChildAgeRelativeLayout.setVisibility(View.GONE);
                viewHolder.secondChildAgeRelativeLayout.setVisibility(View.GONE);
            } else if (kids == 1) {
                viewHolder.dividerView.setVisibility(View.VISIBLE);
                viewHolder.firstChildAgeRelativeLayout.setVisibility(View.VISIBLE);
                viewHolder.secondChildAgeRelativeLayout.setVisibility(View.GONE);
            } else if (kids == 2) {
                viewHolder.dividerView.setVisibility(View.VISIBLE);
                viewHolder.firstChildAgeRelativeLayout.setVisibility(View.VISIBLE);
                viewHolder.secondChildAgeRelativeLayout.setVisibility(View.VISIBLE);
            }
            viewHolder.firstChildAgeSeekBar.setProgress(mHotelAccommodation.getKid1Age());
            viewHolder.secondChildAgeSeekBar.setProgress(mHotelAccommodation.getKid2Age());
            viewHolder.firstKidAgeTextView.setText(viewHolder.firstChildAgeSeekBar.getProgress() + " " +"yrs");
            viewHolder.secondKidAgeTextView.setText(viewHolder.secondChildAgeSeekBar.getProgress()+" yrs");

            viewHolder.firstChildAgeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (progress == 0) {
                /* if seek bar value is lesser than min value then set min value to seek bar */
                        seekBar.setProgress(1);
                        mHotelAccommodation.setKid1Age(1);
                        viewHolder.firstKidAgeTextView.setText(1 + " " + "yrs");
                    } else {
                        mHotelAccommodation.setKid1Age(progress);
                        viewHolder.firstKidAgeTextView.setText(progress + " " + "yrs");
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            viewHolder.secondChildAgeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    if (progress == 0) {
                /* if seek bar value is lesser than min value then set min value to seek bar */
                        seekBar.setProgress(1);
                        mHotelAccommodation.setKid2Age(1);
                        viewHolder.secondKidAgeTextView.setText(1 + " " + "yrs");
                    } else {
                        mHotelAccommodation.setKid2Age(progress);
                        viewHolder.secondKidAgeTextView.setText(progress + " " + "yrs");
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            viewHolder.kidCountNumberPicker.setOnKidsCountChangeListener(new OnKidsCountChnageListener() {
                @Override
                public void currentKidCount(int oldValue, int newValue) {
                    if (newValue == 0) {
                        viewHolder.dividerView.setVisibility(View.GONE);
                        viewHolder.firstChildAgeRelativeLayout.setVisibility(View.GONE);
                        viewHolder.secondChildAgeRelativeLayout.setVisibility(View.GONE);
                    } else if (newValue == 1) {
                        viewHolder.dividerView.setVisibility(View.VISIBLE);
                        viewHolder.firstChildAgeRelativeLayout.setVisibility(View.VISIBLE);
                        viewHolder.secondChildAgeRelativeLayout.setVisibility(View.GONE);
                    } else if (newValue == 2) {
                        viewHolder.dividerView.setVisibility(View.VISIBLE);
                        viewHolder.firstChildAgeRelativeLayout.setVisibility(View.VISIBLE);
                        viewHolder.secondChildAgeRelativeLayout.setVisibility(View.VISIBLE);
                    }
                    if (oldValue < newValue) {
                        if (getTotalCount() < 24) {
                            mHotelAccommodation.setKids(newValue);
                            if (hotelAccommodationsArrayList.size() < 6 && getTotalCount() < 24) {
                                viewHolder.addRoomTextView.setTextColor(ContextCompat.getColor(context,R.color.colorRed));
                                viewHolder.addRoomTextView.setTag(true);
                            } else {
                                viewHolder.addRoomTextView.setTextColor(ContextCompat.getColor(context,R.color.light_gray_text_color));
                                viewHolder.addRoomTextView.setTag(false);
                            }
                        } else {
                            viewHolder.kidCountNumberPicker.setDigit(oldValue);
                            if (oldValue == 0) {
                                viewHolder.dividerView.setVisibility(View.GONE);
                                viewHolder.firstChildAgeRelativeLayout.setVisibility(View.GONE);
                                viewHolder.secondChildAgeRelativeLayout.setVisibility(View.GONE);
                            } else if (oldValue == 1) {
                                viewHolder.dividerView.setVisibility(View.VISIBLE);
                                viewHolder.firstChildAgeRelativeLayout.setVisibility(View.VISIBLE);
                                viewHolder.secondChildAgeRelativeLayout.setVisibility(View.GONE);
                            } else if (oldValue == 2) {
                                viewHolder.dividerView.setVisibility(View.VISIBLE);
                                viewHolder.firstChildAgeRelativeLayout.setVisibility(View.VISIBLE);
                                viewHolder.secondChildAgeRelativeLayout.setVisibility(View.VISIBLE);
                            }

                            Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.limit_restriction),false, fragmentManager);

                        }
                    } else {
                        mHotelAccommodation.setKids(newValue);
                        if (hotelAccommodationsArrayList.size() < 6 && getTotalCount() < 24) {
                            viewHolder.addRoomTextView.setTextColor(ContextCompat.getColor(context,R.color.colorRed));
                            viewHolder.addRoomTextView.setTag(true);
                        } else {
                            viewHolder.addRoomTextView.setTextColor(ContextCompat.getColor(context,R.color.light_gray_text_color));
                            viewHolder.addRoomTextView.setTag(false);
                        }
                    }
                }
            });
            viewHolder.adultCountNumberPicker.setOnAdultsCountChangeListener(new OnAdultsCountChnageListener() {
                @Override
                public void currentAdultCount(int oldValue, int newValue) {
                    if (oldValue < newValue) {
                        if (getTotalCount() < 24) {
                            mHotelAccommodation.setAdultsCount(newValue);
                            if (hotelAccommodationsArrayList.size() < 6 && getTotalCount() < 24) {
                                viewHolder.addRoomTextView.setTextColor(ContextCompat.getColor(context,R.color.colorRed));
                                viewHolder.addRoomTextView.setTag(true);
                            } else {
                                viewHolder.addRoomTextView.setTextColor(ContextCompat.getColor(context,R.color.light_gray_text_color));
                                viewHolder.addRoomTextView.setTag(false);
                            }
                        } else {
                            viewHolder.adultCountNumberPicker.setDigit(oldValue);

                            Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.limit_restriction),false, fragmentManager);

                        }
                    } else {
                        mHotelAccommodation.setAdultsCount(newValue);
                        if (hotelAccommodationsArrayList.size() < 6 && getTotalCount() < 24) {
                            viewHolder.addRoomTextView.setTextColor(ContextCompat.getColor(context,R.color.colorRed));
                            viewHolder.addRoomTextView.setTag(true);
                        } else {
                            viewHolder.addRoomTextView.setTextColor(ContextCompat.getColor(context,R.color.light_gray_text_color));
                            viewHolder.addRoomTextView.setTag(false);
                        }
                    }
                }
            });

            viewHolder.topBarView.setVisibility(View.VISIBLE);
            if (hotelAccommodationsArrayList.size() == 1) {
                viewHolder.closeWindowImageView.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.closeWindowImageView.setVisibility(View.VISIBLE);
            }
            viewHolder.membersCountTextView.setVisibility(View.GONE);

        } else {
            viewHolder.membersCountTextView.setVisibility(View.VISIBLE);
            if (hotelAccommodationsArrayList.get(position).getAdultsCount() == 1 && hotelAccommodationsArrayList.get(position).getKids() == 0) {
                viewHolder.membersCountTextView.setText(hotelAccommodationsArrayList.get(position).getAdultsCount() + " " + context.getString(R.string.Adult));
            } else if (hotelAccommodationsArrayList.get(position).getAdultsCount() > 1 && hotelAccommodationsArrayList.get(position).getKids() == 0) {
                viewHolder.membersCountTextView.setText(hotelAccommodationsArrayList.get(position).getAdultsCount() + " " + context.getString(R.string.Adults));
            } else if (hotelAccommodationsArrayList.get(position).getAdultsCount() == 1 && hotelAccommodationsArrayList.get(position).getKids() == 1) {
                viewHolder.membersCountTextView.setText(hotelAccommodationsArrayList.get(position).getAdultsCount() + " " + context.getString(R.string.Adult) + " " + hotelAccommodationsArrayList.get(position).getKids() + " " + context.getString(R.string.Kid));
            } else if (hotelAccommodationsArrayList.get(position).getAdultsCount() > 1 && hotelAccommodationsArrayList.get(position).getKids() > 1) {
                viewHolder.membersCountTextView.setText(hotelAccommodationsArrayList.get(position).getAdultsCount() + " " + context.getString(R.string.Adults) + " " + hotelAccommodationsArrayList.get(position).getKids() + " " + context.getString(R.string.Kids));
            } else if (hotelAccommodationsArrayList.get(position).getAdultsCount() > 1 && hotelAccommodationsArrayList.get(position).getKids() == 1) {
                viewHolder.membersCountTextView.setText(hotelAccommodationsArrayList.get(position).getAdultsCount() + " " + context.getString(R.string.Adults) + " " + hotelAccommodationsArrayList.get(position).getKids() + " " + context.getString(R.string.Kid));
            } else if (hotelAccommodationsArrayList.get(position).getAdultsCount() == 1 && hotelAccommodationsArrayList.get(position).getKids() > 1) {
                viewHolder.membersCountTextView.setText(hotelAccommodationsArrayList.get(position).getAdultsCount() + " " + context.getString(R.string.Adult) + " " + hotelAccommodationsArrayList.get(position).getKids() + " " + context.getString(R.string.Kids));
            }
            new Utilities().collapse(viewHolder.windowRelativeLayout);
            viewHolder.topBarView.setVisibility(View.GONE);
            viewHolder.closeWindowImageView.setVisibility(View.INVISIBLE);
        }
        viewHolder.addRoomTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity) context).getSupportActionBar().hide();

                if ((Boolean) v.getTag()) {
                    if (hotelAccommodationsArrayList.size() < 6) {
                        
                        gtmAnalytics.sendEvent("Hotel Index Screen", "Add room", "add a room");

                        Utilities.pushFacebookEvent(appEventsLogger,EVENT_CATEGORY,"Add room","add a room");

                        HotelAccommodation mHotelAccommodation = new HotelAccommodation();
                        mHotelAccommodation.setAdultsCount(1);
                        mHotelAccommodation.setKids(0);
                        mHotelAccommodation.setKid1Age(1);
                        mHotelAccommodation.setKid2Age(1);
                        hotelAccommodationsArrayList.add(mHotelAccommodation);
                        expandPosition = hotelAccommodationsArrayList.size() - 1;
                        notifyDataSetChanged();
                        onItemClick.scrollTo(expandPosition);


                    } else {
                        ((TextView) v).setTextColor(ContextCompat.getColor(context,R.color.light_gray_text_color));
                        v.setTag(false);
                    }
                }


            }
        });
        viewHolder.roomTopBarRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandPosition != viewHolder.getAdapterPosition()) {
                    expandPosition = viewHolder.getAdapterPosition();
                    notifyDataSetChanged();
                    onItemClick.scrollTo(expandPosition);
                }
            }
        });
        viewHolder.closeWindowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                gtmAnalytics.sendEvent("Hotel Index Screen", "Remove Room", "remove selected room");

                hotelAccommodationsArrayList.remove(viewHolder.getAdapterPosition());
                expandPosition = hotelAccommodationsArrayList.size() - 1;
                notifyDataSetChanged();
                onItemClick.scrollTo(expandPosition);
            }
        });
        viewHolder.doneButtonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((AppCompatActivity) context).getSupportActionBar().hide();


                Utilities.pushFacebookEvent(appEventsLogger,EVENT_CATEGORY,"Done-room","done adding room");

                gtmAnalytics.sendEvent("Hotel Index Screen", "Done-room", "done adding room");
                onItemClick.doneClick(hotelAccommodationsArrayList);

            }
        });
    }



    // Return the size arrayList
    @Override
    public int getItemCount() {
        return hotelAccommodationsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView roomCountTextView, addRoomTextView, doneButtonTextView, membersCountTextView, firstKidAgeTextView, secondKidAgeTextView;
        private ImageView closeWindowImageView;
        private View topBarView, dividerView;
        private HorizontalNumberPicker adultCountNumberPicker;
        private KIDSHorizontalNumberPicker kidCountNumberPicker;
        private SeekBar firstChildAgeSeekBar, secondChildAgeSeekBar;
        private RelativeLayout roomTopBarRelativeLayout, windowRelativeLayout, firstChildAgeRelativeLayout, secondChildAgeRelativeLayout;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            firstChildAgeRelativeLayout = (RelativeLayout) itemLayoutView.findViewById(R.id.firstChildAgeRelativeLayout);
            secondChildAgeRelativeLayout = (RelativeLayout) itemLayoutView.findViewById(R.id.secondChildAgeRelativeLayout);
            windowRelativeLayout = (RelativeLayout) itemLayoutView.findViewById(R.id.windowRelativeLayout);

            roomCountTextView = (TextView) itemLayoutView.findViewById(R.id.roomCountTextView);
            addRoomTextView = (TextView) itemLayoutView.findViewById(R.id.addRoomTextView);
            membersCountTextView = (TextView) itemLayoutView.findViewById(R.id.membersCountTextView);
            firstKidAgeTextView = (TextView) itemLayoutView.findViewById(R.id.firstKidAgeTextView);
            secondKidAgeTextView = (TextView) itemLayoutView.findViewById(R.id.secondKidAgeTextView);
            doneButtonTextView = (TextView) itemLayoutView.findViewById(R.id.doneButtonTextView);
           // adultTextView = (TextView) itemLayoutView.findViewById(R.id.adultTextView);
           // adultLimitTextView = (TextView) itemLayoutView.findViewById(R.id.adultLimitTextView);
           // kidTextView = (TextView) itemLayoutView.findViewById(R.id.kidTextView);
           // kidLimitTextView = (TextView) itemLayoutView.findViewById(R.id.kidLimitTextView);
          /*  firstChildTitleTextView = (TextView) itemLayoutView.findViewById(R.id.firstChildTitleTextView);
            secondChildTitleTextView = (TextView) itemLayoutView.findViewById(R.id.secondChildTitleTextView);
            secondChildAgeMinValueTextView = (TextView) itemLayoutView.findViewById(R.id.secondChildAgeMinValueTextView);
            secondChildAgeMaxValueTextView = (TextView) itemLayoutView.findViewById(R.id.secondChildAgeMaxValueTextView);
            childAgeMaxTextView = (TextView) itemLayoutView.findViewById(R.id.childAgeMaxTextView);
            firstChildAgeMinValueTextView = (TextView) itemLayoutView.findViewById(R.id.firstChildAgeMinValueTextView);*/

            closeWindowImageView = (ImageView) itemLayoutView.findViewById(R.id.closeWindowImageView);
            roomTopBarRelativeLayout = (RelativeLayout) itemLayoutView.findViewById(R.id.roomTopBarRelativeLayout);
            topBarView = itemLayoutView.findViewById(R.id.topBarView);
            dividerView = itemLayoutView.findViewById(R.id.dividerView);
            secondChildAgeSeekBar = (SeekBar) itemLayoutView.findViewById(R.id.secondChildAgeSeekBar);
            firstChildAgeSeekBar = (SeekBar) itemLayoutView.findViewById(R.id.firstChildAgeSeekBar);
            adultCountNumberPicker = (HorizontalNumberPicker) itemLayoutView.findViewById(R.id.adultCountNumberPicker);
            kidCountNumberPicker = (KIDSHorizontalNumberPicker) itemLayoutView.findViewById(R.id.kidCountNumberPicker);

        }
    }



    private int getTotalCount() {
        int adultCount = 0, kidCount = 0;
        for (int i = 0; i < hotelAccommodationsArrayList.size(); i++) {
            adultCount = adultCount + hotelAccommodationsArrayList.get(i).getAdultsCount();
            kidCount = kidCount + hotelAccommodationsArrayList.get(i).getKids();
        }
        return adultCount + kidCount;
    }
}
