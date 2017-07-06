package com.holidayme.fragments;

/**
 * Created by supriya.sakore on 06-08-2015.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.data.UserDTO;
import com.holidayme.managers.SharedPreferenceManager;
import com.holidayme.response.HotelListingInfoResponse;
import com.holidayme.widgets.CustomRangeBar;

/**
 * Created by supriya.sakore on 24-08-2015.
 */

public class FragmentPrice extends BaseFragment {

    protected static final String TAG = null;
    private HotelListingInfoResponse hotelListingInfoResponse;
    private FilterFragment filterFragment;
    public FragmentPrice() {

    }

    public void setFilterFragment(FilterFragment filterFragment) {
        this.filterFragment = filterFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         View  rootView = inflater.inflate(R.layout.filter_range_bar, container, false);
        CustomRangeBar  customRangeBar = (CustomRangeBar) rootView.findViewById(R.id.custom_rangebar);
        customRangeBar.setSeekBarChangeListener(filterFragment);
        int minvalue,maxvalue,minSelectedPos,maxSelectedPos;

        double min,max;
        min = hotelListingInfoResponse.getMinPrc();
        max = hotelListingInfoResponse.getMaxPrc();
        minvalue = (int) min;
        maxvalue = (int) max + 1;
        customRangeBar.setMinMax(minvalue, maxvalue, UserDTO.getUserDTO().getCurrency());

        minSelectedPos= SharedPreferenceManager.getInstance(getActivity()).getIntegerPreference(Constant.MIN_SELECTED,0);
        maxSelectedPos=SharedPreferenceManager.getInstance(getActivity()).getIntegerPreference(Constant.MAX_SELECTED,0);

        if (minSelectedPos != 0 || maxSelectedPos != 0) {
            customRangeBar.setPreSelectedMinMax(minSelectedPos, maxSelectedPos);
        }

        return rootView;
    }


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        Bundle bundle = this.getArguments();

        if (bundle != null) {

            hotelListingInfoResponse = bundle.getParcelable(Constant.HOTEL_LISTING_RESPONSE);
        }
    }

    public void refresh() {

        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(FragmentPrice.this).attach(FragmentPrice.this).commit();
    }


    @Override
    public void initUIElements() {

    }
}






