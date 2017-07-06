package com.holidayme.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.HolidayMeFont;
import com.holidayme.data.RoomRatesDto;
import com.holidayme.data.UserDTO;
import com.holidayme.gtm.GTMAnalytics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by shaikh.salim on 7/14/2016.
 */

@SuppressLint("SetTextI18n")
public class FareBreakupFragment extends BaseFragment {

    private Context context;
    private View rootView;
    private LinearLayout fareBreakupLinearLayout;
    private Button okButton;
    private ImageView backImageView;
    private TextView titleTextView,grossValueTextView,taxesValueTextView,grandTotalValueTextView,discountTextView,discountValueTextView;
    private RoomRatesDto mRoomRateDTO;
    private String discount;
    private GTMAnalytics gtmAnalytics;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_farebreakup, container, false);
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        Bundle bundle = this.getArguments();


        if (bundle != null) {

            if (bundle.getParcelable("FairBrakeUp") != null) {

                mRoomRateDTO = bundle.getParcelable("FairBrakeUp");
                discount=bundle.getString("discount");

            }
        }
    }

    @Override
    public void initUIElements() {
        fareBreakupLinearLayout = (LinearLayout) rootView.findViewById(R.id.fare_breakup_list);
        okButton = (Button) rootView.findViewById(R.id.btn_ok);
        backImageView = (ImageView) rootView.findViewById(R.id.headerBackPressImageView);
        titleTextView = (TextView) rootView.findViewById(R.id.headerTitleTextView);
        grossValueTextView=(TextView)rootView.findViewById(R.id.txt_gross_total_value);
        taxesValueTextView=(TextView)rootView.findViewById(R.id.txt_taxes_fees_value);
        grandTotalValueTextView=(TextView)rootView.findViewById(R.id.txt_grang_total_value);
        discountTextView=(TextView)rootView.findViewById(R.id.txt_discount_value);
        discountValueTextView=(TextView)rootView.findViewById(R.id.txt_discount);
        titleTextView.setText(context.getString(R.string.fare_breakup));

        if(UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
            HolidayMeFont.overrideFonts(getActivity(), okButton, Constant.NotoKufiArabic_Regular);
        }else{
            HolidayMeFont.overrideFonts(getActivity(), okButton, Constant.HelveticaNeueLight);

        }

        uiFunctionality();
        gtmAnalytics = AppController.getInstance().getGTMAnalytics(getActivity());
        gtmAnalytics.setScreenName("FareBreakup Screen");
    }

    public void uiFunctionality() {

        for (int i = 0; i < mRoomRateDTO.getRooms().size(); i++) {
            addView(i);
        }

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        grossValueTextView.setText( mRoomRateDTO.getSellingCurrency()+" "+String.format(Locale.US, "%.2f", mRoomRateDTO.getTotalNetRate())+""+"");
        discountTextView.setText(mRoomRateDTO.getSellingCurrency()+" "+discount);
        discountValueTextView.setText(getResources().getString(R.string.Dicount)+"( - )"+"");
        taxesValueTextView.setText( mRoomRateDTO.getSellingCurrency()+" "+String.format(Locale.US, "%.2f", mRoomRateDTO.getTotalTaxesPrice())+""+"");
        grandTotalValueTextView.setText( mRoomRateDTO.getSellingCurrency()+" "+String.format(Locale.US, "%.2f", mRoomRateDTO.getTotalSellingPrice()));
}

    public void addView(int pos) {

        TextView titleTextView = new TextView(context);
        int room_no=pos+1;
        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE))
            HolidayMeFont.overrideFonts(getActivity(), titleTextView, Constant.NotoKufiArabic_Bold);
            else
            HolidayMeFont.overrideFonts(getActivity(), titleTextView, Constant.HelveticaNeueRoman);

            titleTextView.setText(context.getString(R.string.room)+" "+ room_no);
        titleTextView.setTextColor(getResources().getColor(R.color.light_gray_text_color));
        titleTextView.setBackgroundColor(getResources().getColor(R.color.background_dark));
        titleTextView.setAllCaps(true);
        titleTextView.setPadding(20,20,25,20);
        fareBreakupLinearLayout.addView(titleTextView);

        ImageView imageView=new ImageView(context);
        imageView.setMaxWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setBackground(ContextCompat.getDrawable(context,R.drawable.line_gray));
        imageView.setMaxHeight(1);
        fareBreakupLinearLayout.addView(imageView);
        Date date;


            for (int i = 0; i < mRoomRateDTO.getRooms().get(pos).getRateBreakup().size(); i++) {
               // View fareBreakUpRowView = LayoutInflater.from(context).inflate(R.layout.fare_breakup_list_item, null);
                View fareBreakUpRowView = View.inflate(context,R.layout.fare_breakup_list_item,null);
                TextView dateTextView = (TextView) fareBreakUpRowView.findViewById(R.id.lable);
                dateTextView.setTextColor(getResources().getColor(R.color.light_gray_text_color));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", new Locale(UserDTO.getUserDTO().getLanguage()));
                try {
                    date = simpleDateFormat.parse(simpleDateFormat.format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.US).parse(mRoomRateDTO.getRooms().get(pos).getRateBreakup().get(i).getDate())));
                    dateTextView.setText(simpleDateFormat.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                TextView amountTextView = (TextView) fareBreakUpRowView.findViewById(R.id.value);
                amountTextView.setTextColor(getResources().getColor(R.color.dark_gray_text_color));
                amountTextView.setText(mRoomRateDTO.getSellingCurrency()+" "+String.format(Locale.US, "%.2f", mRoomRateDTO.getRooms().get(pos).getRateBreakup().get(i).getNetRate())+""+"");
                fareBreakupLinearLayout.addView(fareBreakUpRowView);



        }
        View view = View.inflate(context,R.layout.fare_breakup_list_item,null);
        TextView taxTextView = (TextView) view.findViewById(R.id.lable);
        taxTextView.setText(getResources().getString(R.string.Tax));
        taxTextView.setTextColor(getResources().getColor(R.color.light_gray_text_color));
        TextView taxValueTextView = (TextView) view.findViewById(R.id.value);
        taxValueTextView.setTextColor(getResources().getColor(R.color.dark_gray_text_color));
        taxValueTextView.setText(mRoomRateDTO.getSellingCurrency()+" "+String.format(Locale.US, "%.2f", mRoomRateDTO.getRooms().get(pos).getTotalTaxes())+""+"");
        fareBreakupLinearLayout.addView(view);


    }
}
