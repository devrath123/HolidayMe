package com.holidayme.holidaydetail_mvp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.holidayme.activities.R;
import com.holidayme.adapter.FlightsAdapter;
import com.holidayme.data.FlightsBean;

import java.util.ArrayList;

public class HolidayDetailFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_holiday_detail, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.flightsRecyclerView);
        FlightsAdapter flightsAdapter = new FlightsAdapter(getActivity(),getFlightsArrayList());
        recyclerView.setAdapter(flightsAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        flightsAdapter.notifyDataSetChanged();

        return view;
    }


    private ArrayList<FlightsBean> getFlightsArrayList()
    {
        ArrayList<FlightsBean> flightsBeanArrayList = new ArrayList<>();
        flightsBeanArrayList.add(new FlightsBean("DEL","New Delhi","04:00","10 July, 2017","PUN","Pune","06:00","10 July, 2017"));
        flightsBeanArrayList.add(new FlightsBean("MUM","Mumbai","16:45","12 October, 2017","BKK","Bangkok","20:15","12 Oct, 2017"));
        flightsBeanArrayList.add(new FlightsBean("CHN","Chennai","11:15","21 September, 2017","KRL","Kerela","01:30","22 September, 2017"));
        flightsBeanArrayList.add(new FlightsBean("DAR","Darjelling","09:50","30 August, 2017","DEL","New Delhi","13:20","30 August, 2017"));

        return flightsBeanArrayList;
    }



}
