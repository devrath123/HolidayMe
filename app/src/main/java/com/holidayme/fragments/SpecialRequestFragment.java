package com.holidayme.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.HolidayMeFont;
import com.holidayme.adapter.SpecialRequestAdapter;
import com.holidayme.data.SpecialRequestsDto;
import com.holidayme.data.UserDTO;
import com.holidayme.response.BookingDetailsResponse;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 12-07-2016.
 */

public class SpecialRequestFragment extends BaseFragment {

    private View rootView;
    private RecyclerView recyclerView;
    private RadioButton cancelRadioButton, applyRadioButton;
    private ImageView backImageView;
    private LinearLayoutManager linearLayoutManager;
    private BookingDetailsResponse mBookingDetailsResponse;
    private SpecialRequestAdapter specialRequestAdapter;
    private ArrayList<SpecialRequestsDto> specialRequestsDtoArrayList;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            if (bundle.getParcelable("BookingDetailResponse") != null) {
                mBookingDetailsResponse = bundle.getParcelable("BookingDetailResponse");
                specialRequestsDtoArrayList = bundle.getParcelableArrayList("selecteddata");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_special_request, container, false);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        return rootView;
    }

    @Override
    public void initUIElements() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_sp_req);
        cancelRadioButton = (RadioButton) rootView.findViewById(R.id.rdo_cancel);
        backImageView = (ImageView) rootView.findViewById(R.id.headerBackPressImageView);
        applyRadioButton = (RadioButton) rootView.findViewById(R.id.rdo_apply);

        if(mBookingDetailsResponse.getBookingDetails()!=null&&mBookingDetailsResponse.getBookingDetails().getSpecialRequests()!=null) {
            for (int i = 0; i < mBookingDetailsResponse.getBookingDetails().getSpecialRequests().size(); i++) {
                mBookingDetailsResponse.getBookingDetails().getSpecialRequests().get(i).setCheck(false);
                for (int j = 0; j < specialRequestsDtoArrayList.size(); j++) {
                    if (mBookingDetailsResponse.getBookingDetails().getSpecialRequests().get(i).getId().equals(specialRequestsDtoArrayList.get(j).getId())) {
                        mBookingDetailsResponse.getBookingDetails().getSpecialRequests().get(i).setCheck(true);
                        break;
                    }
                }
            }
        }
        AppController.getInstance().getGTMAnalytics(getActivity()).setScreenName("Special Request Screen");
        setFonts();
        UIFunctionality();

    }

    private void setFonts() {

        if(UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)){
            HolidayMeFont.overrideFonts(getActivity(),cancelRadioButton , Constant.NotoKufiArabic_Bold);
            HolidayMeFont.overrideFonts(getActivity(),applyRadioButton , Constant.NotoKufiArabic_Bold);
        }else{
            HolidayMeFont.overrideFonts(getActivity(), cancelRadioButton, Constant.HelveticaNeueRoman);
            HolidayMeFont.overrideFonts(getActivity(), applyRadioButton, Constant.HelveticaNeueRoman);
        }

    }

    public void UIFunctionality() {

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        specialRequestAdapter = new SpecialRequestAdapter(mBookingDetailsResponse.getBookingDetails().getSpecialRequests());
        recyclerView.setAdapter(specialRequestAdapter);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        applyRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<SpecialRequestsDto> specialRequestsDtoArrayList = new ArrayList<>();
                Intent intent = new Intent();
                if(mBookingDetailsResponse.getBookingDetails()!=null&&mBookingDetailsResponse.getBookingDetails().getSpecialRequests()!=null) {
                    for (int i = 0; i < mBookingDetailsResponse.getBookingDetails().getSpecialRequests().size(); i++) {
                        if (mBookingDetailsResponse.getBookingDetails().getSpecialRequests().get(i).isCheck()) {
                            specialRequestsDtoArrayList.add(mBookingDetailsResponse.getBookingDetails().getSpecialRequests().get(i));
                        }
                    }
                }
                intent.putParcelableArrayListExtra("listdata", specialRequestsDtoArrayList);
                getTargetFragment().onActivityResult(1, 2, intent);
                getActivity().onBackPressed();
            }
        });

        cancelRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

}
