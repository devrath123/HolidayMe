package com.holidayme.staycationbooking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.holidayme.AppInterface.BackPressInterFace;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.HomeActivity;
import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.Utilities;
import com.holidayme.common.Log;
import com.holidayme.data.GetawayBookingConfirmationResponse;
import com.holidayme.data.UserDTO;
import com.holidayme.gtm.GTMAnalytics;
import com.holidayme.staycation_calendar_mvp.StaycationCalendarActivity;
import com.holidayme.widgets.ContactUsCustomDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by arshad.shaikh on 3/16/2017.
 */

public class GetawaysBookingConfirmationFragment extends Fragment implements View.OnClickListener ,BackPressInterFace{

    private View view;
    private RelativeLayout bookingStatusRelativeLayout,packageInfoRelativeLayout,personDetailsRelativeLayout,impInformationRelativeLayout,cancelPolicyRelativeLayout,rateBreakupRelativeLayout,
            getawaysCallRelativeLayout,getawaysEmailRelativeLayout;
    private Context context;
    private ImageView importantInfoImageView,cancellationDetailsImageView, bookingStatusImageView;
    private TextView  bookingStatusTextView,bookingMessageTextView,bookingReferenceTextView,bookingRefNumberTextView,packageTitleTextView
            ,getawaysCheckInDateTextView,getawaysCheckInDayTextView,getawaysCheckInMonthTextView,getawaysCheckOutDateTextView,
            getawaysCheckOutDayTextView,getawaysCheckOutMonthTextView,packageAddressTextView,moreInfoDetailsTextView,
            moreCancellationPoliciesTextView,travellerNameTextView,travellerContactTextView,travellerEmailTextView,totalCostTextView,subPackageNameTextView,perPackageCostTextView,
            packagesCountTextView,packageTotalCostTextView,packageAddResTextView,packageHotelNameTextView,finalTotalCostTextView,totalAmountTextView,textFinalTotalCostTextView,retryTextView;
    private  boolean moreInfoFlag,moreCancelDetailFlag,retryflag;
    private  String bookingStatus;
    private GetawayBookingConfirmationResponse getawayBookingConfirmationResponse;
    private UserDTO userDTO;
    private  ImageView backImageView;
    private GTMAnalytics gtmAnalytics;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Bundle bundle=this.getArguments();

        getawayBookingConfirmationResponse=bundle.getParcelable("getawayBookingConfirmationResponse");

        ((StaycationCalendarActivity) getActivity()).setBackPressListener(GetawaysBookingConfirmationFragment.this);

    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context= getActivity();
        userDTO = UserDTO.getUserDTO();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.getaways_booking_confirmation_page, container, false);

        initUI();
      //  setLayouts();
        gtmAnalytics = AppController.getInstance().getGTMAnalytics(getActivity());
        gtmAnalytics.setScreenName("Confirmation Page");

        setData();

        return view;
    }

    private void setLayouts() {


        String lag = userDTO.getLanguage();

        if (Constant.ENGLISH_LANGUAGE_CODE.equals(lag)) {

            setLocale("en");
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        } else if (Constant.ARABIC_LANGUAGE_CODE.equals(lag)) {

            setLocale("ar");
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        }

    }

    private void setLocale(String lang) {


        Locale locale = new Locale(lang);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
        getActivity().getResources().updateConfiguration(configuration, getActivity().getResources().getDisplayMetrics());

    }


    private void setData() {




        if(getawayBookingConfirmationResponse!=null) {


            if (!getawayBookingConfirmationResponse.isFraud()) {


                // booking is confirmed
                if (getawayBookingConfirmationResponse.getBookingStatus() == 2) {

                    bookingStatusRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.success_section));
                    bookingStatusImageView.setImageResource(R.drawable.icn_success);

                    bookingStatusTextView.setText(getString(R.string.success));
                  //  bookingMessageTextView.setText(String.format(getString(R.string.congrats_msg_hotel),hotelBookConfirmationResponse.getBookingDetails().getGuestFirstName() + " " + hotelBookConfirmationResponse.getBookingDetails().getGuestLastName()));

                    bookingMessageTextView.setText(String.format(getString(R.string.congrats_msg_hotel),getawayBookingConfirmationResponse.getTravelerDetail().get(0).getTravelerFirstName()+" "+getawayBookingConfirmationResponse.getTravelerDetail().get(0).getTravelerLastName()));

                    Product product = new Product()
                            .setId(Long.toString(getawayBookingConfirmationResponse.getPackageId()))
                            .setName(getawayBookingConfirmationResponse.getPackageTitle())
                            .setPrice(Double.parseDouble(getawayBookingConfirmationResponse.getRevenueInUsd()))
                            .setCouponCode("")
                            .setQuantity(1);

                    ProductAction productAction = new ProductAction(ProductAction.ACTION_PURCHASE)
                            .setTransactionId(String.valueOf(getawayBookingConfirmationResponse.getBookingId()))
                            .setTransactionRevenue(Double.parseDouble(getawayBookingConfirmationResponse.getRevenueInUsd()))
                            .setTransactionAffiliation("HolidayMeAndroidAppGetaways");

                    HitBuilders.ScreenViewBuilder builder = new HitBuilders.ScreenViewBuilder()
                            .addProduct(product)
                            .setProductAction(productAction);

                    AppController.getInstance().getGTMAnalytics(getActivity()).setScreenName("transaction", builder);
                    gtmAnalytics.sendEvent("Confirmation Page ","Successful-"+ "Congratulations " + getawayBookingConfirmationResponse.getTravelerDetail().get(0).getTravelerFirstName()+ "," + " Your Booking is Successful", "Successful-"+ "Congratulations " + getawayBookingConfirmationResponse.getTravellerFirstName() + "," + " Your Booking is Successful");

                    setValuesData();
                    getDates();

                }

                // booking is failed
                else if (getawayBookingConfirmationResponse.getBookingStatus() == 3) {


                    bookingStatusRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.failled));
                    bookingStatusImageView.setImageResource(R.drawable.icon_failled);

                    bookingStatusTextView.setText(getString(R.string.payment_failed));
                    bookingMessageTextView.setText(getString(R.string.unfortunately_payment_failed));

                    gtmAnalytics.sendEvent("Confirmation Page ","Payment Fail- Unfortunately your payment failed" ,"Payment Fail- Unfortunately your payment failed");

                    setValuesData();
                    getDates();

                }

                // booking is pending.
                else if (getawayBookingConfirmationResponse.getBookingStatus() == 5) {

                    bookingStatusRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.failled));
                    bookingStatusImageView.setImageResource(R.drawable.icon_payment_failled);

                    bookingStatusTextView.setText(getString(R.string.failed));
                    bookingMessageTextView.setText(getString(R.string.unfortunately_card_charged));

                    gtmAnalytics.sendEvent("Confirmation Page ","Booking Fail- Unfortunately your card was charged but booking failed" ,"Booking Fail -Unfortunately your card was charged but booking failed");

                    setValuesData();
                    getDates();


                }

                else{


                    bookingStatusRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.failled));
                    bookingStatusImageView.setImageResource(R.drawable.icon_failled);

                    bookingStatusTextView.setText(getString(R.string.payment_failed));
                    bookingMessageTextView.setText(getString(R.string.sorry)+" "+getString(R.string.transaction_could_not_be_processed));

                    gtmAnalytics.sendEvent("Confirmation Page ","Payment Fail- Sorry your transaction could not be processed" ,"Payment Fail -Sorry your transaction could not be processed");


                    packageInfoRelativeLayout.setVisibility(View.GONE);
                    personDetailsRelativeLayout.setVisibility(View.GONE);
                    impInformationRelativeLayout.setVisibility(View.GONE);
                    cancelPolicyRelativeLayout.setVisibility(View.GONE);

                    totalAmountTextView.setVisibility(View.GONE);
                    totalCostTextView.setVisibility(View.GONE);
                    rateBreakupRelativeLayout.setVisibility(View.GONE);
                    textFinalTotalCostTextView.setVisibility(View.INVISIBLE);
                    finalTotalCostTextView.setVisibility(View.GONE);

                    bookingRefNumberTextView.setVisibility(View.GONE);

                    bookingReferenceTextView.setText(getString(R.string.bank_has_not_authorized));

                    retryTextView.setVisibility(View.VISIBLE);
                    retryflag=true;



                }


            }

            // if booking is fraud, then show retry page.
            else {

                gtmAnalytics.sendEvent("Confirmation Page ","Fraud -Payment has been received and your booking is in process" ,"Fraud - Payment has been received and your booking is in process");

                bookingStatusRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.payment_failled));
                bookingStatusImageView.setImageResource(R.drawable.process);

                bookingStatusTextView.setText(getString(R.string.booking_in_process));
                bookingMessageTextView.setText(getString(R.string.payment_for_getaway)+" "+getawayBookingConfirmationResponse.getPackageTitle()+" "+getString(R.string.payment_received_booking_in_process));

                setValuesData();
                getDates();




            }
        }

        // everything failed, try again
        else{



            bookingStatusRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.failled));
            bookingStatusImageView.setImageResource(R.drawable.icon_failled);

            bookingStatusTextView.setText(getString(R.string.payment_failed));
            bookingMessageTextView.setText(getString(R.string.sorry)+" "+getString(R.string.transaction_could_not_be_processed));



            packageInfoRelativeLayout.setVisibility(View.GONE);
            personDetailsRelativeLayout.setVisibility(View.GONE);
            impInformationRelativeLayout.setVisibility(View.GONE);
            cancelPolicyRelativeLayout.setVisibility(View.GONE);

            totalAmountTextView.setVisibility(View.GONE);
            totalCostTextView.setVisibility(View.GONE);
            rateBreakupRelativeLayout.setVisibility(View.GONE);
            textFinalTotalCostTextView.setVisibility(View.GONE);
            finalTotalCostTextView.setVisibility(View.INVISIBLE);

            bookingRefNumberTextView.setVisibility(View.GONE);

            bookingReferenceTextView.setText(getString(R.string.bank_has_not_authorized));

            retryTextView.setVisibility(View.VISIBLE);

            retryflag=true;


        }



    }

    private void setValuesData() {

        packageTitleTextView.setText(getawayBookingConfirmationResponse.getPackageTitle());
        bookingRefNumberTextView.setText(getawayBookingConfirmationResponse.getReceiptNumber());
        packageHotelNameTextView.setText(getawayBookingConfirmationResponse.getHotelTitle());
//

        packageAddressTextView.setText(getawayBookingConfirmationResponse.getProductAddress());
        subPackageNameTextView.setText(getawayBookingConfirmationResponse.getSubPackageTitle());


        travellerNameTextView.setText(getawayBookingConfirmationResponse.getTravelerDetail().get(0).getSalutation()+" "+ getawayBookingConfirmationResponse.getTravelerDetail().get(0).getTravelerFirstName()+" "+
                getawayBookingConfirmationResponse.getTravelerDetail().get(0).getTravelerLastName());
        travellerContactTextView.setText(getawayBookingConfirmationResponse.getTravelerDetail().get(0).getTravelerPhoneNumber());
        travellerEmailTextView.setText(getawayBookingConfirmationResponse.getTravelerDetail().get(0).getTravelerEmailId());

        String cancellation= String.valueOf(Html.fromHtml(getawayBookingConfirmationResponse.getCancellationPolicy()));
        String impInfo=String.valueOf(Html.fromHtml(getawayBookingConfirmationResponse.getImportantInformation()));

        moreInfoDetailsTextView.setText(impInfo);
        moreCancellationPoliciesTextView.setText(cancellation);

        double perPackageCost=getawayBookingConfirmationResponse.getTotalPriceInUserCurrency()/getawayBookingConfirmationResponse.getNoOfPackages();



        perPackageCostTextView.setText(userDTO.getCurrency()+" "+String.format(Locale.US, "%.2f",perPackageCost));


        packagesCountTextView.setText(String.valueOf(getawayBookingConfirmationResponse.getNoOfPackages()));
        packageTotalCostTextView.setText(userDTO.getCurrency()+" "+String.format(Locale.US, "%.2f", getawayBookingConfirmationResponse.getTotalPriceInUserCurrency()));



        String originalPrice = String.format(Locale.US, "%.2f",  getawayBookingConfirmationResponse.getSellingPrice());
        totalCostTextView.setText(userDTO.getCurrency()+" "+originalPrice);

        finalTotalCostTextView.setText(userDTO.getCurrency()+" "+String.format(Locale.US, "%.2f",  getawayBookingConfirmationResponse.getTotalPriceInUserCurrency()));


    }

    private void getDates() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy-EEE",new Locale(userDTO.getLanguage()));
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy-EEE", Locale.US);
        Date date=null,dateAr = null;
        try {
            dateAr = sdf.parse(sdf.format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.US).parse(getawayBookingConfirmationResponse.getCheckInDate())));
            date = sdf1.parse(sdf1.format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.US).parse(getawayBookingConfirmationResponse.getCheckInDate())));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String checkinDateAr = sdf.format(dateAr);
        String checkinDate = sdf1.format(date);


        String checkInArDay=Utilities.splitDate(checkinDateAr).get(Constant.Date.DAY.name());
        String checkInArMonthName=Utilities.splitDate(checkinDateAr).get(Constant.Date.MONTH.name());
        String checkInDate=Utilities.splitDate(checkinDate).get(Constant.Date.DATE.name());
        String checkInMonthYear=Utilities.splitDate(checkinDate).get(Constant.Date.YEAR.name());



        getawaysCheckInDayTextView.setText(checkInArDay);
        getawaysCheckInMonthTextView.setText(checkInArMonthName+" "+checkInMonthYear);
        getawaysCheckInDateTextView.setText(checkInDate);



        try {

            date = sdf1.parse(sdf1.format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse(getawayBookingConfirmationResponse.getCheckOutDate())));
            dateAr = sdf.parse(sdf.format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse(getawayBookingConfirmationResponse.getCheckOutDate())));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String checkoutDate = sdf1.format(date);
        String checkoutDateAr = sdf.format(dateAr);


        String checkOutArDay=Utilities.splitDate(checkoutDateAr).get(Constant.Date.DAY.name());
        String checkOutArMonthName=Utilities.splitDate(checkoutDateAr).get(Constant.Date.MONTH.name());

        String checkOutDate=Utilities.splitDate(checkoutDate).get(Constant.Date.DATE.name());
        String checkOutMonthYear=Utilities.splitDate(checkoutDate).get(Constant.Date.YEAR.name());

        getawaysCheckOutDayTextView.setText(checkOutArDay);
        getawaysCheckOutMonthTextView.setText(checkOutArMonthName+" "+checkOutMonthYear);
        getawaysCheckOutDateTextView.setText(checkOutDate);

    }


    private void initUI() {

        bookingStatusRelativeLayout= (RelativeLayout) view.findViewById(R.id.bookingStatusRelativeLayout);
        bookingStatusImageView= (ImageView) view.findViewById(R.id.bookingStatusImageView);
        bookingStatusTextView= (TextView) view.findViewById(R.id.bookingStatusTextView);
        bookingMessageTextView= (TextView) view.findViewById(R.id.bookingMessageTextView);

        bookingReferenceTextView= (TextView) view.findViewById(R.id.bookingReferenceTextView);
        bookingRefNumberTextView= (TextView) view.findViewById(R.id.bookingRefNumberTextView);

        packageTitleTextView= (TextView) view.findViewById(R.id.packageTitleTextView);

        getawaysCheckInDateTextView= (TextView) view.findViewById(R.id.getawaysCheckInDateTextView);
        getawaysCheckInDayTextView= (TextView) view.findViewById(R.id.getawaysCheckInDayTextView);
        getawaysCheckInMonthTextView= (TextView) view.findViewById(R.id.getawaysCheckInMonthTextView);

        getawaysCheckOutDateTextView= (TextView) view.findViewById(R.id.getawaysCheckOutDateTextView);
        getawaysCheckOutDayTextView= (TextView) view.findViewById(R.id.getawaysCheckOutDayTextView);
        getawaysCheckOutMonthTextView= (TextView) view.findViewById(R.id.getawaysCheckOutMonthTextView);

        packageAddressTextView= (TextView) view.findViewById(R.id.packageAddResTextView);
        subPackageNameTextView= (TextView) view.findViewById(R.id.subPackageNameTextView);

        packageHotelNameTextView= (TextView) view.findViewById(R.id.packageHotelNameTextView);





        travellerNameTextView= (TextView) view.findViewById(R.id.travellerNameTextView);
        travellerContactTextView= (TextView) view.findViewById(R.id.travellerContactTextView);
        travellerEmailTextView= (TextView) view.findViewById(R.id.travellerEmailTextView);

        perPackageCostTextView= (TextView) view.findViewById(R.id.perPackageCostTextView);
        packageTotalCostTextView= (TextView) view.findViewById(R.id.packageTotalCostTextView);



        importantInfoImageView= (ImageView) view.findViewById(R.id.importantInfoImageView);
        cancellationDetailsImageView= (ImageView) view.findViewById(R.id.cancellationDetailsImageView);
        moreInfoDetailsTextView= (TextView) view.findViewById(R.id.moreInfoDetailsTextView);
        moreCancellationPoliciesTextView= (TextView) view.findViewById(R.id.moreCancellationPoliciesTextView);

        totalCostTextView= (TextView) view.findViewById(R.id.totalCostTextView);
        packagesCountTextView= (TextView) view.findViewById(R.id.packagesCountTextView);

        finalTotalCostTextView= (TextView) view.findViewById(R.id.finalTotalCostTextView);

        totalAmountTextView= (TextView) view.findViewById(R.id.totalAmountTextView);
        textFinalTotalCostTextView= (TextView) view.findViewById(R.id.textFinalTotalCostTextView);

        retryTextView= (TextView) view.findViewById(R.id.retryTextView);

        getawaysCallRelativeLayout= (RelativeLayout) view.findViewById(R.id.getawaysCallRelativeLayout);

        backImageView= (ImageView) view.findViewById(R.id.backImageView);

        packageInfoRelativeLayout= (RelativeLayout) view.findViewById(R.id.packageInfoRelativeLayout);
        personDetailsRelativeLayout= (RelativeLayout) view.findViewById(R.id.personDetailsRelativeLayout);
        impInformationRelativeLayout= (RelativeLayout) view.findViewById(R.id.impInformationRelativeLayout);
        cancelPolicyRelativeLayout= (RelativeLayout) view.findViewById(R.id.cancelPolicyRelativeLayout);
        rateBreakupRelativeLayout= (RelativeLayout) view.findViewById(R.id.rateBreakupRelativeLayout);

        getawaysEmailRelativeLayout= (RelativeLayout) view.findViewById(R.id.getawaysEmailRelativeLayout);



        importantInfoImageView.setOnClickListener(this);
        cancellationDetailsImageView.setOnClickListener(this);
        getawaysCallRelativeLayout.setOnClickListener(this);
        backImageView.setOnClickListener(this);
        getawaysEmailRelativeLayout.setOnClickListener(this);

        retryTextView.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.importantInfoImageView:

                showMoreInfoDetails();

                break;

            case R.id.cancellationDetailsImageView:

                showMoreCancellationDetails();

                break;

            case R.id.getawaysEmailRelativeLayout:

                sendEmail();

                break;


            case R.id.getawaysCallRelativeLayout:
                ContactUsCustomDialog contactUsCustomDialog = null;
                contactUsCustomDialog = new ContactUsCustomDialog(context, "en");

                final ContactUsCustomDialog finalContactUsCustomDialog = contactUsCustomDialog;
                contactUsCustomDialog.setDialogExitListner(new ContactUsCustomDialog.DialogExitListener() {
                    @Override
                    public void dialogExitWithDone() {
                    }

                    @Override
                    public void dialogExitWithDismissOrCancel() {
                        finalContactUsCustomDialog.dismiss();
                    }
                });
                contactUsCustomDialog.show();

                break;

            case R.id.backImageView:

                Constant.getawayActive=true;

                if(retryflag){

                    getActivity().finish();
                }
                else {

                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }

                retryflag=false;

                break;

            case R.id.retryTextView:

                getActivity().finish();

                break;


        }
    }

    private void sendEmail() {


        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"care@holidayme.com"});

        final PackageManager pm = getActivity().getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
        ResolveInfo best = null;
        for (final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") ||
                    info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
        if (best != null)
            intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
        startActivity(intent);
    }


    private void showMoreInfoDetails() {

        if (moreInfoFlag){
            moreInfoDetailsTextView.setVisibility(View.VISIBLE);
            moreInfoFlag = false;
            importantInfoImageView.setImageResource(R.drawable.icn_minus);
        }
        else{
            moreInfoDetailsTextView.setVisibility(View.GONE);
            moreInfoFlag = true;
            importantInfoImageView.setImageResource(R.drawable.icn_plus);
        }

    }

    private void showMoreCancellationDetails() {



        if(moreCancelDetailFlag){
            moreCancellationPoliciesTextView.setVisibility(View.VISIBLE);
            moreCancelDetailFlag = false;
            cancellationDetailsImageView.setImageResource(R.drawable.icn_minus);

        }

        else{

            moreCancellationPoliciesTextView.setVisibility(View.GONE);
            moreCancelDetailFlag = true;
            cancellationDetailsImageView.setImageResource(R.drawable.icn_plus);

        }



    }



    @Override
    public void backPressCalled() {
        backImageView.performClick();
    }
}
