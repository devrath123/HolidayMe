package com.holidayme.fragments;



import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
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
import com.holidayme.activities.util.NonScrollListView;
import com.holidayme.activities.util.Utilities;
import com.holidayme.adapter.RateBreakUpHotelAdapter;
import com.holidayme.common.BookingStatusTypes;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.common.TransactionStatusTypes;
import com.holidayme.data.UserDTO;
import com.holidayme.gtm.GTMAnalytics;
import com.holidayme.response.HotelBookConfirmationResponse;
import com.holidayme.widgets.ContactUsCustomDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class HotelBookingConfirmationFragment extends Fragment implements View.OnClickListener, BackPressInterFace {

    private View view;
    private RelativeLayout bookingStatusRelativeLayout, packageInfoRelativeLayout, personDetailsRelativeLayout, impInformationRelativeLayout, cancelPolicyRelativeLayout, rateBreakupRelativeLayout,
            getawaysCallRelativeLayout, getawaysEmailRelativeLayout;
    private Context context;
    private ImageView importantInfoImageView, cancellationDetailsImageView, bookingStatusImageView, rateBreakUpImageView;
    private TextView bookingStatusTextView, bookingMessageTextView, bookingReferenceTextView, bookingRefNumberTextView, packageTitleTextView, getawaysCheckInDateTextView, getawaysCheckInDayTextView, getawaysCheckInMonthTextView, getawaysCheckOutDateTextView,
            getawaysCheckOutDayTextView, getawaysCheckOutMonthTextView, packageAddressTextView, moreInfoDetailsTextView,
            moreCancellationPoliciesTextView, travellerNameTextView, travellerContactTextView, travellerEmailTextView, totalCostTextView,
            totalAmountTextView, retryTextView, inclusionsTextView,
            confirmationNumberTextView, roomsTextView, nightsTextView, moreInfoDetailsHotelTextView, confirmationTextView;
    private boolean moreInfoFlag = true, moreCancelDetailFlag, moreBreakUpFlag = true,retryFlag;
    private HotelBookConfirmationResponse hotelBookConfirmationResponse;
    private UserDTO userDTO;
    private ImageView backImageView;
    private GTMAnalytics gtmAnalytics;

    NonScrollListView rateBreakUpListView;

    private Calendar calendarToDate = new GregorianCalendar();
    private Calendar calenderFromDate = new GregorianCalendar();
    private String checkInDate = "", checkOutDate = "";
    private Date dateTo, dateFrom;
    private boolean isMyBooking;
    private int days, advance_days;
    private int adultCount, childCount;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            hotelBookConfirmationResponse = bundle.getParcelable("hotelBookConfirmationResponse");
            isMyBooking = bundle.getBoolean("IsMyBooking");
        }

        ((HomeActivity) getActivity()).setBackPressListener(HotelBookingConfirmationFragment.this);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
        userDTO = UserDTO.getUserDTO();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.hotel_booking_confirmation, container, false);


        gtmAnalytics = AppController.getInstance().getGTMAnalytics(getActivity());
        gtmAnalytics.setScreenName("Confirmation Page");

        initUI();

        return view;
    }


    private boolean bookingStatus() {
        boolean isCod = hotelBookConfirmationResponse.isCod();
        boolean isPayAtHotel = hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().isPayAtHotel();
        if (isPayAtHotel) {
            if (hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelBookingStatus() == BookingStatusTypes.Confirmed.getBookingStatusVal())
                return true;

            if(hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelBookingStatus() == BookingStatusTypes.Fail.getBookingStatusVal())
                return false;


        } else {
            if (isCod) {
                if (hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getTransactionStatusId() == TransactionStatusTypes.InProgress.getTransactionVal()) {
                    if (hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelBookingStatus() == BookingStatusTypes.PendingRequest.getBookingStatusVal()) {
                        return true;
                    }
                }
            } else {
                if (hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getTransactionStatusId() == TransactionStatusTypes.Successful.getTransactionVal()) {
                    if (hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelBookingStatus() == BookingStatusTypes.Confirmed.getBookingStatusVal()) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    private void setData() {

        if (hotelBookConfirmationResponse != null) {


            //if (!getawayBookingConfirmationResponse.isFraud())


            // booking is confirmed

            if (hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelBookingStatus() == 4 || hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelBookingStatus() == 12) {
                bookingStatusRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.failled));
                bookingStatusImageView.setImageResource(R.drawable.icon_failled);

                bookingStatusTextView.setText(getString(R.string.cancelled));
                bookingMessageTextView.setText(getString(R.string.lblThisBookinghasBeenCancelled));

                confirmationTextView.setVisibility(View.GONE);
                confirmationNumberTextView.setVisibility(View.GONE);

                setValuesData();
                getDates();

            } else if (bookingStatus()) {

                bookingStatusRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.success_section));
                bookingStatusImageView.setImageResource(R.drawable.icn_success);

                bookingStatusTextView.setText(getString(R.string.success));
                bookingMessageTextView.setText(String.format(getString(R.string.congrats_msg_hotel), hotelBookConfirmationResponse.getBookingDetails().getGuestFirstName() + " " + hotelBookConfirmationResponse.getBookingDetails().getGuestLastName()));

                if (!isMyBooking && hotelBookConfirmationResponse != null) {
                    sendFaceBookAndCleverTapEvents(dateTo, dateFrom);
                    if(hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getPropertyName() != null)
                    gtmAnalytics.sendEvent("Booking Confirmation" + "-" + hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getPropertyName(), "Booking confirm", "Booking");
                }

                confirmationNumberTextView.setText(hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getItineraryId() + "");
                setValuesData();
                getDates();

            }

            // booking is failed
            else {
                if(hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().isPayAtHotel()) {
                    bookingStatusRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.payment_failled));
                    bookingStatusImageView.setImageResource(R.drawable.icon_failled);

                    bookingStatusTextView.setText(getString(R.string.booking_failed));
                    bookingMessageTextView.setText(getString(R.string.unfortunately_payment_failed));

                    confirmationTextView.setVisibility(View.GONE);
                    confirmationNumberTextView.setVisibility(View.GONE);
                    if (!isMyBooking)
                        gtmAnalytics.sendEvent("Booking Confirmation" + "-" + hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getPropertyName(), getString(R.string.unfortunately_payment_failed), getString(R.string.payment_failed));

                    setValuesData();
                    getDates();
                }else{
                    bookingStatusRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.failled));
                    bookingStatusImageView.setImageResource(R.drawable.icon_failled);

                    bookingStatusTextView.setText(getString(R.string.payment_failed));
                    bookingMessageTextView.setText(getString(R.string.unfortunately_card_charged));

                    confirmationTextView.setVisibility(View.GONE);
                    confirmationNumberTextView.setVisibility(View.GONE);
                    if (!isMyBooking)
                        gtmAnalytics.sendEvent("Booking Confirmation" + "-" + hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getPropertyName(), getString(R.string.unfortunately_payment_failed), getString(R.string.payment_failed));

                    setValuesData();
                    getDates();
                }

            }

        }


        // everything failed, try again
        else {

            retryFlag = true;
            bookingStatusRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.failled));
            bookingStatusImageView.setImageResource(R.drawable.icon_failled);

            bookingStatusTextView.setText(getString(R.string.payment_failed));
            bookingMessageTextView.setText(getString(R.string.sorry) + " " + getString(R.string.transaction_could_not_be_processed));

            if (!isMyBooking && hotelBookConfirmationResponse != null)
                gtmAnalytics.sendEvent("Booking Confirmation" + "-" + hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getPropertyName(), getString(R.string.sorry) + " " + getString(R.string.transaction_could_not_be_processed), getString(R.string.payment_failed));


            packageInfoRelativeLayout.setVisibility(View.GONE);
            personDetailsRelativeLayout.setVisibility(View.GONE);
            impInformationRelativeLayout.setVisibility(View.GONE);
            cancelPolicyRelativeLayout.setVisibility(View.GONE);

            totalAmountTextView.setVisibility(View.GONE);
            totalCostTextView.setVisibility(View.GONE);
            rateBreakupRelativeLayout.setVisibility(View.GONE);


            bookingRefNumberTextView.setVisibility(View.GONE);

            bookingReferenceTextView.setText(getString(R.string.bank_has_not_authorized));

            retryTextView.setVisibility(View.VISIBLE);

        }


    }

    private void setValuesData() {

        packageTitleTextView.setText(hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getPropertyName());
        bookingRefNumberTextView.setText(hotelBookConfirmationResponse.getBookingDetails().getHolzooBookingId() + "");
        if (hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelRoomBookings().size() == 1)
            roomsTextView.setText(hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelRoomBookings().size() + " " + getActivity().getResources().getString(R.string.one_room) + ": " + hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelRoomBookings().get(0).getRoomName());

        else if (hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelRoomBookings().size() == 2)
            roomsTextView.setText(hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelRoomBookings().size() + " " + getActivity().getResources().getString(R.string.two_room) + ": " + hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelRoomBookings().get(0).getRoomName());

        else
            roomsTextView.setText(hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelRoomBookings().size() + " " + getActivity().getResources().getString(R.string.three_ten_room) + ": " + hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelRoomBookings().get(0).getRoomName());


        inclusionsTextView.setText(getActivity().getResources().getString(R.string.inclusion) + " : " + hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getValueAdds());
        packageAddressTextView.setText("  " + hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getPropertyAddress() + "," + hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getPropertyCity());
        moreInfoDetailsHotelTextView.setText(hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getTariffNotes());
        moreCancellationPoliciesTextView.setText(Html.fromHtml(hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getCancelationPolicy()));
        travellerNameTextView.setText(hotelBookConfirmationResponse.getBookingDetails().getGuestSalutation() + hotelBookConfirmationResponse.getBookingDetails().getGuestFirstName() + " " + hotelBookConfirmationResponse.getBookingDetails().getGuestLastName());
        travellerContactTextView.setText(hotelBookConfirmationResponse.getBookingDetails().getGuestMobileNumber());
        travellerEmailTextView.setText(hotelBookConfirmationResponse.getBookingDetails().getBookedBy());


        RateBreakUpHotelAdapter rateBreakUpHotelAdapter = new RateBreakUpHotelAdapter(getActivity(), hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelRoomBookings());
        rateBreakUpListView.setAdapter(rateBreakUpHotelAdapter);
        rateBreakUpHotelAdapter.notifyDataSetChanged();

        double grandTotal = (hotelBookConfirmationResponse.getBookingDetails().getSellingPrice() - hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getPromoCodeAmount());
        totalCostTextView.setText(hotelBookConfirmationResponse.getBookingDetails().getBillingCurrencyType() + " " + String.format(Locale.US, "%.2f", grandTotal));

    }

    private void getDates() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy-EEE", new Locale(userDTO.getLanguage()));
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy-EEE", Locale.US);
        Date checkInDateEng = null, checkInDateArabic = null, checkOutDateEng = null, checkOutDateArabic = null;
        try {
            checkInDateArabic = sdf.parse(sdf.format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse(hotelBookConfirmationResponse.getBookingDetails().getArrivalDate())));
            checkInDateEng = sdf1.parse(sdf1.format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse(hotelBookConfirmationResponse.getBookingDetails().getArrivalDate())));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String checkinDateAr = sdf.format(checkInDateArabic);
        String checkinDate = sdf1.format(checkInDateEng);


        String checkInArDay = Utilities.splitDate(checkinDateAr).get(Constant.Date.DAY.name());
        String checkInArMonthName = Utilities.splitDate(checkinDateAr).get(Constant.Date.MONTH.name());
        String checkInDate = Utilities.splitDate(checkinDate).get(Constant.Date.DATE.name());
        String checkInMonthYear = Utilities.splitDate(checkinDate).get(Constant.Date.YEAR.name());


        getawaysCheckInDayTextView.setText(checkInArDay);
        getawaysCheckInMonthTextView.setText(checkInArMonthName + " " + checkInMonthYear);
        getawaysCheckInDateTextView.setText(checkInDate);


        try {

            checkOutDateEng = sdf1.parse(sdf1.format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse(hotelBookConfirmationResponse.getBookingDetails().getDepartureDate())));
            checkOutDateArabic = sdf.parse(sdf.format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse(hotelBookConfirmationResponse.getBookingDetails().getDepartureDate())));

            days = NetworkUtilities.daysBetween(checkInDateEng, checkOutDateEng);
            nightsTextView.setText(getActivity().getResources().getString(R.string.nights) + " : " + days);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String checkoutDate = sdf1.format(checkOutDateEng);
        String checkoutDateAr = sdf.format(checkOutDateArabic);


        String checkOutArDay = Utilities.splitDate(checkoutDateAr).get(Constant.Date.DAY.name());
        String checkOutArMonthName = Utilities.splitDate(checkoutDateAr).get(Constant.Date.MONTH.name());

        String checkOutDate = Utilities.splitDate(checkoutDate).get(Constant.Date.DATE.name());
        String checkOutMonthYear = Utilities.splitDate(checkoutDate).get(Constant.Date.YEAR.name());

        getawaysCheckOutDayTextView.setText(checkOutArDay);
        getawaysCheckOutMonthTextView.setText(checkOutArMonthName + " " + checkOutMonthYear);
        getawaysCheckOutDateTextView.setText(checkOutDate);

    }


    private void initUI() {

        confirmationTextView = (TextView) view.findViewById(R.id.confirmationTextView);
        bookingStatusRelativeLayout = (RelativeLayout) view.findViewById(R.id.bookingStatusRelativeLayout);
        bookingStatusImageView = (ImageView) view.findViewById(R.id.bookingStatusImageView);
        bookingStatusTextView = (TextView) view.findViewById(R.id.bookingStatusTextView);
        bookingMessageTextView = (TextView) view.findViewById(R.id.bookingMessageTextView);
        roomsTextView = (TextView) view.findViewById(R.id.roomsTextView);
        nightsTextView = (TextView) view.findViewById(R.id.nightsTextView);
        bookingReferenceTextView = (TextView) view.findViewById(R.id.bookingReferenceTextView);
        bookingRefNumberTextView = (TextView) view.findViewById(R.id.bookingRefNumberTextView);
        inclusionsTextView = (TextView) view.findViewById(R.id.inclusionsTextView);
        packageTitleTextView = (TextView) view.findViewById(R.id.packageTitleTextView);
        getawaysCheckInDateTextView = (TextView) view.findViewById(R.id.getawaysCheckInDateTextView);
        getawaysCheckInDayTextView = (TextView) view.findViewById(R.id.getawaysCheckInDayTextView);
        getawaysCheckInMonthTextView = (TextView) view.findViewById(R.id.getawaysCheckInMonthTextView);
        getawaysCheckOutDateTextView = (TextView) view.findViewById(R.id.getawaysCheckOutDateTextView);
        getawaysCheckOutDayTextView = (TextView) view.findViewById(R.id.getawaysCheckOutDayTextView);
        getawaysCheckOutMonthTextView = (TextView) view.findViewById(R.id.getawaysCheckOutMonthTextView);
        packageAddressTextView = (TextView) view.findViewById(R.id.packageAddressTextView);
        moreCancellationPoliciesTextView = (TextView) view.findViewById(R.id.moreCancellationPoliciesHotelsTextView);
        moreInfoDetailsHotelTextView = (TextView) view.findViewById(R.id.moreInfoDetailsHotelTextView);
        confirmationNumberTextView = (TextView) view.findViewById(R.id.confirmationNumberTextView);
        travellerNameTextView = (TextView) view.findViewById(R.id.travellerNameTextView);
        travellerContactTextView = (TextView) view.findViewById(R.id.travellerContactTextView);
        travellerEmailTextView = (TextView) view.findViewById(R.id.travellerEmailTextView);
        rateBreakUpListView = (NonScrollListView) view.findViewById(R.id.rateBreakUpListView);
        rateBreakUpImageView = (ImageView) view.findViewById(R.id.rateBreakUpImageView);
        importantInfoImageView = (ImageView) view.findViewById(R.id.importantInfoImageView);
        cancellationDetailsImageView = (ImageView) view.findViewById(R.id.cancellationDetailsImageView);
        moreInfoDetailsTextView = (TextView) view.findViewById(R.id.moreInfoDetailsHotelTextView);


        totalAmountTextView = (TextView) view.findViewById(R.id.totalAmountTextView);
        totalCostTextView = (TextView) view.findViewById(R.id.totalCostTextView);


        retryTextView = (TextView) view.findViewById(R.id.retryTextView);

        getawaysCallRelativeLayout = (RelativeLayout) view.findViewById(R.id.getawaysCallRelativeLayout);

        backImageView = (ImageView) view.findViewById(R.id.backImageView);

        packageInfoRelativeLayout = (RelativeLayout) view.findViewById(R.id.packageInfoRelativeLayout);
        personDetailsRelativeLayout = (RelativeLayout) view.findViewById(R.id.personDetailsRelativeLayout);
        impInformationRelativeLayout = (RelativeLayout) view.findViewById(R.id.impInformationRelativeLayout);
        cancelPolicyRelativeLayout = (RelativeLayout) view.findViewById(R.id.cancelPolicyRelativeLayout);
        rateBreakupRelativeLayout = (RelativeLayout) view.findViewById(R.id.rateBreakupRelativeLayout);

        getawaysEmailRelativeLayout = (RelativeLayout) view.findViewById(R.id.getawaysEmailRelativeLayout);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);

        try {
            if (hotelBookConfirmationResponse != null) {
                dateTo = simpleDateFormat.parse(simpleDateFormat.format(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getCheckInDate())));
                checkInDate = simpleDateFormat.format(dateTo);
                calendarToDate.setTime(dateTo);


                dateFrom = simpleDateFormat.parse(simpleDateFormat.format(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getCheckOutDate())));
                checkOutDate = simpleDateFormat.format(dateFrom);
                calenderFromDate.setTime(dateFrom);


                days = NetworkUtilities.daysBetween(calenderFromDate.getTime(), calendarToDate.getTime());
                advance_days = NetworkUtilities.daysBetween(calendarToDate.getTime(), Calendar.getInstance().getTime());


                for (int i = 0; i < hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelRoomBookings().size(); i++) {
                    adultCount = adultCount + hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelRoomBookings().get(i).getAdultsCount();
                    childCount = childCount + hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelRoomBookings().get(i).getChildrensCount();
                }
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        setData();
        rateBreakUpImageView.setOnClickListener(this);
        importantInfoImageView.setOnClickListener(this);
        cancellationDetailsImageView.setOnClickListener(this);
        getawaysCallRelativeLayout.setOnClickListener(this);
        backImageView.setOnClickListener(this);
        getawaysEmailRelativeLayout.setOnClickListener(this);

        retryTextView.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.importantInfoImageView:

                showMoreInfoDetails();

                break;

            case R.id.cancellationDetailsImageView:

                showMoreCancellationDetails();

                break;

            case R.id.getawaysEmailRelativeLayout:

                sendEmail();

                break;

            case R.id.rateBreakUpImageView:
                showMoreBreakUp();
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

                Constant.getawayActive = false;
                ((HomeActivity) getActivity()).setBackPressListener(null);
                if(retryFlag){

                   FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.popBackStack(manager.getBackStackEntryAt(manager.getBackStackEntryCount()-2).getId(),FragmentManager.POP_BACK_STACK_INCLUSIVE );

                }
                else {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("IsMyBooking", isMyBooking);
                    startActivity(intent);
                }

                retryFlag=false;

                break;

            case R.id.retryTextView:

                ((HomeActivity) getActivity()).setBackPressListener(null);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.popBackStack(manager.getBackStackEntryAt(manager.getBackStackEntryCount()-2).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);


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

        if (moreInfoFlag) {
            moreInfoDetailsTextView.setVisibility(View.GONE);
            moreInfoFlag = false;
            importantInfoImageView.setImageResource(R.drawable.icn_plus);
        } else {
            moreInfoDetailsTextView.setVisibility(View.VISIBLE);
            moreInfoFlag = true;
            importantInfoImageView.setImageResource(R.drawable.icn_minus);
        }

    }

    private void showMoreBreakUp() {
        if (moreBreakUpFlag) {
            rateBreakUpListView.setVisibility(View.GONE);
            moreBreakUpFlag = false;
            rateBreakUpImageView.setImageResource(R.drawable.icn_plus);
        } else {
            rateBreakUpListView.setVisibility(View.VISIBLE);
            moreBreakUpFlag = true;
            rateBreakUpImageView.setImageResource(R.drawable.icn_minus);
        }

    }

    private void showMoreCancellationDetails() {

        if (moreCancelDetailFlag) {
            moreCancellationPoliciesTextView.setVisibility(View.GONE);
            moreCancelDetailFlag = false;

            cancellationDetailsImageView.setImageResource(R.drawable.icn_plus);
        } else {
            moreCancellationPoliciesTextView.setVisibility(View.VISIBLE);
            moreCancelDetailFlag = true;
            cancellationDetailsImageView.setImageResource(R.drawable.icn_minus);
        }

    }

    private void sendFaceBookAndCleverTapEvents(Date checkInDate, Date checkOutDate) {

        //CleverTap Tracking
        if (AppController.getInstance().getCleverTapInstance() != null) {
            HashMap<String, Object> cleverTapEventsHashMap = new HashMap<>();
            cleverTapEventsHashMap.put("LOB", "Hotels");
            cleverTapEventsHashMap.put("Destination", hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getPropertyCity());

            cleverTapEventsHashMap.put("From Date", checkInDate);
            cleverTapEventsHashMap.put("To Date", checkOutDate);
            cleverTapEventsHashMap.put("Revenue", hotelBookConfirmationResponse.getBookingDetails().getTotalPriceInUSD());
            cleverTapEventsHashMap.put("Advance Purchase", advance_days);
            cleverTapEventsHashMap.put("Travel Nights", days);

            cleverTapEventsHashMap.put("Number of rooms", hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelRoomBookings().size());
            cleverTapEventsHashMap.put("Number of passengers", adultCount + "|" + childCount);
            cleverTapEventsHashMap.put("Coupon Code", "");
            if (hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getPromoCode() != null)
                cleverTapEventsHashMap.put("Coupon Code", hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getPromoCode());
            cleverTapEventsHashMap.put("Product Name", hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getPropertyName());
            cleverTapEventsHashMap.put("Booking ID", hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getBookingPropertyId());
            cleverTapEventsHashMap.put("Price", "(" + hotelBookConfirmationResponse.getBookingDetails().getBillingCurrencyType() + ") " + String.format(Locale.US, "%.2f", hotelBookConfirmationResponse.getBookingDetails().getBillingPrice()));
            cleverTapEventsHashMap.put("Discount Amount", "(" + hotelBookConfirmationResponse.getBookingDetails().getBillingCurrencyType() + ") " + hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getPromoCodeAmount());
            cleverTapEventsHashMap.put("Room Type", hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelRoomBookings().get(0).getRoomName());
            if (hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().isPayAtHotel()) {
                cleverTapEventsHashMap.put("Payment option", "Pay at Hotel");
            } else if (hotelBookConfirmationResponse.getBookingDetails().isCod()) {
                cleverTapEventsHashMap.put("Payment option", "Cash on Delivery");
            } else {
                cleverTapEventsHashMap.put("Payment option", "Pay now");
            }
            if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
                AppController.getInstance().getCleverTapInstance().event.push("AR Funnel Hotels Confirmation", cleverTapEventsHashMap);
            } else {
                AppController.getInstance().getCleverTapInstance().event.push("Funnel Hotels Confirmation Viewed", cleverTapEventsHashMap);
            }

        }

        //FaceBook Event Tracking


        double totalPriceInUSD = hotelBookConfirmationResponse.getBookingDetails().getTotalPriceInUSD();
        String transactionId = Long.toString(hotelBookConfirmationResponse.getBookingDetails().getHolzooBookingId());
        String coupon_code = "";
        if (hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getPromoCode() != null)
            coupon_code = hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getPromoCode();

        Product product = new Product()
                .setId(Long.toString(hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHolzooPropertyId()))
                .setName(hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getPropertyName())
                .setPrice(totalPriceInUSD)
                .setCouponCode(coupon_code)
                .setQuantity(1);

        ProductAction productAction = new ProductAction(ProductAction.ACTION_PURCHASE)
                .setTransactionId(transactionId)
                .setTransactionRevenue(totalPriceInUSD)
                .setTransactionAffiliation("HolidayMeAndroidApp");

        HitBuilders.ScreenViewBuilder builder = new HitBuilders.ScreenViewBuilder()
                .addProduct(product)
                .setProductAction(productAction);

        AppController.getInstance().getGTMAnalytics(getActivity()).setScreenName("transaction", builder);

    }

    @Override
    public void backPressCalled() {
        backImageView.performClick();
       // ((HomeActivity) getActivity()).setBackPressListener(null);
    }
}
