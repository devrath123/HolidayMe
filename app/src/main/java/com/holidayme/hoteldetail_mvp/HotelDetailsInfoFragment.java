package com.holidayme.hoteldetail_mvp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.fragments.BaseFragment;
import com.holidayme.response.HotelDetailInfoResponse;

/**
 * Created by santosh.patar on 03-09-2015.
 */
public class HotelDetailsInfoFragment extends BaseFragment {
    private View rootView;
    private static HotelDetailInfoResponse hotelDetailInfoResponse;

     static HotelDetailsInfoFragment newInstance(HotelDetailInfoResponse hotelDetailInfoResponse) {
        HotelDetailsInfoFragment.hotelDetailInfoResponse = hotelDetailInfoResponse;
        return new HotelDetailsInfoFragment();
    }


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.hotel_details_info_fragment, container, false);
        setHasOptionsMenu(true);
        return rootView;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void initUIElements() {

        TextView checkInInstructionTextView = (TextView) rootView.findViewById(R.id.checkInInstructionTextView);
        TextView checkInInstructionTitleTextView = (TextView) rootView.findViewById(R.id.checkInInstructionTitleTextView);

        try{

            ((TextView) rootView.findViewById(R.id.hotelTitleTextView)).setText(getActivity().getResources().getString(R.string.DETAILS)+" : "+ hotelDetailInfoResponse.getTtl());
            ((TextView) rootView.findViewById(R.id.shortDescriptionTextView)).setText(Html.fromHtml(hotelDetailInfoResponse.getBasicInfo().getSDesc()).toString());
            ((TextView) rootView.findViewById(R.id.hotelAddressTextView)).setText(hotelDetailInfoResponse.getBasicInfo().getAdrs());
            ((TextView) rootView.findViewById(R.id.longDescriptionTextView)).setText(Html.fromHtml(hotelDetailInfoResponse.getMoreInfo().getLDesc()).toString());
            ((TextView) rootView.findViewById(R.id.hotelPolicyTitleTextView)).setText(getActivity().getResources().getString(R.string.HOTEL_POLICY_OF) + " : " + hotelDetailInfoResponse.getTtl());
            ((TextView) rootView.findViewById(R.id.hotelPolicyTextView)).setText(Html.fromHtml(hotelDetailInfoResponse.getMoreInfo().getHotelPolicy()).toString());
            if(hotelDetailInfoResponse.getMoreInfo().getCheckInInstructions()!=null&&!hotelDetailInfoResponse.getMoreInfo().getCheckInInstructions().equals("")) {
                checkInInstructionTitleTextView.setVisibility(View.VISIBLE);
                checkInInstructionTextView.setVisibility(View.VISIBLE);
                checkInInstructionTextView.setText(Html.fromHtml(hotelDetailInfoResponse.getMoreInfo().getCheckInInstructions()));
            }
            else{
                checkInInstructionTitleTextView.setVisibility(View.GONE);
                checkInInstructionTextView.setVisibility(View.GONE);
            }



        }catch(Exception e){
            e.printStackTrace();
        }

        AppController.getInstance().getGTMAnalytics(getActivity()).setScreenName("HotelDetailInfo Screen - " + hotelDetailInfoResponse.getTtl());
    }
}
