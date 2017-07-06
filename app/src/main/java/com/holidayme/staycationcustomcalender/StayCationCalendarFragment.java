/*
package com.holidayme.staycationcustomcalender;

import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.holidayme.activities.R;
import com.holidayme.common.Log;
import com.holidayme.data.AllocationDTO;
import com.holidayme.data.PackagesListDto;
import com.holidayme.managers.APIManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;


*/
/**
 * Created by arshad.shaikh on 2/27/2017.
 *//*


public class StayCationCalendarFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private RecyclerView monthRecyclerView, dayDetailsRecyclerView;
    private MonthsAdapter monthsAdapter;
    private DayDetailsAdapter dayDetailsAdapter;
    private ImageView previousMonthButton, nextMonthButton;
    private Context context;
    private ArrayList<MonthsData> monthsArrayList;
    private int monthScrollPosition = 0, lastScrollPosition;
    private LinearLayoutManager monthsLinearLayoutManager;
    CustomLayoutManager dayDetailsLinearLayoutManager;
    // private LinearLayoutManager dayDetailsLinearLayoutManager;
    private ArrayList<DayDetails> dayDetailsArrayList;
    private boolean updateFlag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.staycation_calender_layout, container, false);

        context = getActivity();
        dayDetailsArrayList = new ArrayList<>();
        initUi();
        getAllocations();
        //getDayDetails();

        getMonths();
        setMonthsAdapter();

        return rootView;
    }




    private void initUi() {

        monthRecyclerView = (RecyclerView) rootView.findViewById(R.id.monthRecyclerView);
        previousMonthButton = (ImageView) rootView.findViewById(R.id.previousMonthButton);
        nextMonthButton = (ImageView) rootView.findViewById(R.id.nextMonthButton);

        previousMonthButton.setOnClickListener(this);
        nextMonthButton.setOnClickListener(this);

        dayDetailsRecyclerView = (RecyclerView) rootView.findViewById(R.id.dayDetailsRecyclerView);
        dayDetailsLinearLayoutManager = new CustomLayoutManager(context);

        dayDetailsRecyclerView.setLayoutManager(dayDetailsLinearLayoutManager);


    }

    private void getAllocations() {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("CurrencyCode", "AED");
            jsonObject.put("IpAddress", "115.248.109.178");
            jsonObject.put("PackageId", "55");
            jsonObject.put("RoomId", "159");
            jsonObject.put("SubPackageId", "147");

            new APIManager<JSONArray>().getAllocations("http://dev-getawayapi.holzoo.com/GetAwayAPI/package/allocation/en",jsonObject.toString(),
                                                        allocationSuccessListener(),errorListener(),JSONArray.class,getActivity());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Response.Listener<JSONArray> allocationSuccessListener()
    {
        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
              Log.i("Response :","Resp");
                if(jsonArray.length() > 0)
                {
                    Type listType = new TypeToken<ArrayList<AllocationDTO>>() {}.getType();
                    List<AllocationDTO> allocationDTOList = new GsonBuilder().create().fromJson(String.valueOf(jsonArray), listType);
                    Log.i("Val:",allocationDTOList.toString());
                }
            }
        };
    }

    public Response.ErrorListener errorListener()
    {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
              Log.i("Err : ","Err");
            }
        };
    }

    private void resetMonths() {

        for (int i = 0; i < monthsArrayList.size(); i++) {

            monthsArrayList.get(i).setSelected(false);

        }

    }

    private void getDayDetails() {


        dayDetailsAdapter = new DayDetailsAdapter(context, dayDetailsArrayList);
        dayDetailsRecyclerView.setAdapter(dayDetailsAdapter);
        dayDetailsAdapter.notifyDataSetChanged();

        dayDetailsRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == SCROLL_STATE_IDLE) {
                    int aa = dayDetailsLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                    Log.i("Log : ", aa + "");
                    Log.i(" Value : ", monthsArrayList.get(0).getDayIndex() + "");
                    for (int i = 0; i < monthsArrayList.size(); i++)
                    {
                        */
/*if(monthsArrayList.get(i).getDayIndex() > aa)
                        {
                            monthScrollPosition = i;
                            resetMonths();
                            monthsArrayList.get(i).setSelected(true);
                            monthsLinearLayoutManager.scrollToPositionWithOffset(i, 0);
                            monthsAdapter.notifyDataSetChanged();
                            break;
                        }*//*

                    }
                }

            }
        });

        dayDetailsAdapter.SetOnItemClickListener(new DayDetailsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                */
/*Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();

                dayDetailsLinearLayoutManager.scrollToPositionWithOffset(position, 0);


                resetDay();

                dayDetailsArrayList.get(position).setSelect(true);
                dayDetailsAdapter.notifyDataSetChanged();*//*


            }
        });
    }

    private void setMonthsAdapter() {

        monthsAdapter = new MonthsAdapter(context, monthsArrayList);
        monthsLinearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        monthRecyclerView.setLayoutManager(monthsLinearLayoutManager);
        monthRecyclerView.setAdapter(monthsAdapter);

        monthsArrayList.get(0).setSelected(true);


        monthsAdapter.SetOnItemClickListener(new MonthsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                monthScrollPosition = position;
                resetMonths();
                monthsArrayList.get(position).setSelected(true);
                monthsLinearLayoutManager.scrollToPositionWithOffset(position, 0);
                monthsAdapter.notifyDataSetChanged();
                //dayDetailsLinearLayoutManager.scrollToPositionWithOffset(monthsArrayList.get(monthScrollPosition).getDayIndex(),0);
                dayDetailsRecyclerView.smoothScrollToPosition(monthsArrayList.get(monthScrollPosition).getDayIndex());

            }
        });


    }

    private void getMonths() {

        String monthString;
        Calendar now = Calendar.getInstance();
        Calendar beginCalendar = Calendar.getInstance();
        Calendar finishCalendar = Calendar.getInstance();

        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        String months[] = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

        if (monthsArrayList == null)
            monthsArrayList = new ArrayList<>();

        monthsArrayList.clear();

        if (month < months.length)
            monthString = months[month];
        else
            monthString = "Invalid month";

        Log.e("month", "" + monthString);

        String currentDate = monthString + " " + String.valueOf(year);
        String finalDate = monthString + " " + String.valueOf(year + 2);

        DateFormat dateFormat = new SimpleDateFormat("MMM yyyy");

        try {
            beginCalendar.setTime(dateFormat.parse(currentDate));
            finishCalendar.setTime(dateFormat.parse(finalDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EE");

        int i = 0;
        boolean beginFlag = false;
        while (beginCalendar.before(finishCalendar)) {
            String date = dateFormat.format(beginCalendar.getTime()).toUpperCase();
            monthsArrayList.add(new MonthsData(date, i));
            int currentMonth = beginCalendar.get(Calendar.MONTH);
            while (currentMonth == beginCalendar.get(Calendar.MONTH)) {


                int currentDay = beginCalendar.get(Calendar.DAY_OF_MONTH);
                if (currentDay == day && !beginFlag) {
                    beginFlag = true;
                }

                if(beginFlag) {
                    i++;
                    String week = simpleDateFormat.format(beginCalendar.getTime());
                    dayDetailsArrayList.add(new DayDetails(week, currentDay + "", "AED", "123.34", 2, 5, false));
                }
                beginCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }

        }
        getDayDetails();

    }


    private void resetDay() {

        for (int i = 0; i < dayDetailsArrayList.size(); i++) {

            dayDetailsArrayList.get(i).setSelect(false);

        }

    }

    public class CustomLayoutManager extends LinearLayoutManager {


        public CustomLayoutManager(Context context) {
            super(context, VERTICAL, false);
        }


      */
/*  @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {


            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getActivity()) {

                private static final float SPEED = 10f;// Change this value (default=25f)


                @Override
                public PointF computeScrollVectorForPosition(int targetPosition)
                {
                    int yDelta = calculateSpeedPerPixel(targetPosition);
                    return new PointF(0, yDelta);
                }

                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return SPEED / displayMetrics.densityDpi;
                }

            };
            smoothScroller.setTargetPosition(position);
            startSmoothScroll(smoothScroller);
        }*//*



        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
            RecyclerView.SmoothScroller smoothScroller = new TopSnappedSmoothScroller(recyclerView.getContext());
            smoothScroller.setTargetPosition(position);
            startSmoothScroll(smoothScroller);
        }

        private class TopSnappedSmoothScroller extends LinearSmoothScroller {
            public TopSnappedSmoothScroller(Context context) {
                super(context);

            }

            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return CustomLayoutManager.this.computeScrollVectorForPosition(targetPosition);
            }

            @Override
            protected int getVerticalSnapPreference() {
                return SNAP_TO_START;
            }
        }


    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.previousMonthButton:

                if (monthScrollPosition > 0) {
                    if (!updateFlag)
                        monthScrollPosition = monthsLinearLayoutManager.findFirstCompletelyVisibleItemPosition();

                    monthScrollPosition--;

                    resetMonths();
                    if (lastScrollPosition == monthScrollPosition) {
                        monthScrollPosition--;
                        updateFlag = true;
                    }

                    if (monthScrollPosition < 0)
                        monthScrollPosition = 0;

                    monthsArrayList.get(monthScrollPosition).setSelected(true);
                    monthsLinearLayoutManager.scrollToPositionWithOffset(monthScrollPosition, 0);
                    monthsAdapter.notifyDataSetChanged();

                    // dayDetailsLinearLayoutManager.scrollToPositionWithOffset(monthsArrayList.get(monthScrollPosition).getDayIndex(),0);
                    dayDetailsRecyclerView.smoothScrollToPosition(monthsArrayList.get(monthScrollPosition).getDayIndex());

                    lastScrollPosition = monthScrollPosition;
                }

                break;

            case R.id.nextMonthButton:


                if (monthsArrayList.size() > monthScrollPosition + 1) {
                    if (!updateFlag)
                        monthScrollPosition = monthsLinearLayoutManager.findFirstCompletelyVisibleItemPosition();

                    monthScrollPosition++;

                    resetMonths();
                    if (lastScrollPosition == monthScrollPosition) {
                        monthScrollPosition++;
                        updateFlag = true;
                    }

                    monthsArrayList.get(monthScrollPosition).setSelected(true);
                    monthsLinearLayoutManager.scrollToPositionWithOffset(monthScrollPosition, 0);
                    monthsAdapter.notifyDataSetChanged();

                    dayDetailsRecyclerView.smoothScrollToPosition(monthsArrayList.get(monthScrollPosition).getDayIndex());

                    // dayDetailsLinearLayoutManager.scrollToPositionWithOffset(monthsArrayList.get(monthScrollPosition).getDayIndex(),0);
                    // dayDetailsLinearLayoutManager.scrollToPosition(monthsArrayList.get(monthScrollPosition).getDayIndex());

                    lastScrollPosition = monthScrollPosition;
                }

                break;

        }
    }
}
*/
