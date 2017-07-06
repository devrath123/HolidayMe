package com.holidayme.staycationbooking;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.SoftKeyboardStateWatcher;
import com.holidayme.activities.util.Utilities;
import com.holidayme.adapter.CountryCodeCustomAdapter;
import com.holidayme.adapter.SpinnerCustomAdapter;
import com.holidayme.adapter.SpinnerCustomAdapterMonth;
import com.holidayme.adapter.SpinnerCustomAdapterNationality;
import com.holidayme.adapter.SpinnerCustomAdapterYear;
import com.holidayme.common.CustomProgressDialog;
import com.holidayme.common.Log;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.common.Validation;
import com.holidayme.data.CheckAvailabilityRequest;
import com.holidayme.data.PaymentDetailsDto;
import com.holidayme.data.UserDTO;
import com.holidayme.fragments.GetawaysFareBreakupFragment;
import com.holidayme.fragments.GetawaysPaymentGatewayFragment;
import com.holidayme.gtm.GTMAnalytics;
import com.holidayme.staycation_details_mvp.StaycationDetailsActivity;
import com.holidayme.widgets.ContactUsCustomDialog;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

/**
 * Created by arshad.shaikh on 3/23/2017.
 */

public class GetawaysBookingFragment extends Fragment {

    private View rootView, dateInfoView;
    private LinearLayout /*codLinearLayout, cardLinearLayout*/ topLinearLayout, guestDetailLinearLayout, travellerLinearLayout, cardIncludeRelativeLayout
            /*visaCheckoutLinearLayout, masterCardLinearLayout*/;
    private Context context;
    private ScrollView mainScrollView;
    private ImageView hotelImageView, cardImageView, couponCodeStatusImageView,
            firstNameImageView, lastNameImageView, emailImageView, mobileImageView, cardNameImageView;
    ;
    private TextView txt_payment_desc, checkInDate, checkOutDate, nightCounts, payNowTextView, privacyPolicyTextView, guestTextView, hotelPaymentTextView, billingAmountTextView, bookingPolicyTextView, booking_policy_lable;
    private EditText firstNameEditText, lastNameEditText, emailEditText, mobileEditText, cardHolderEditText, cardNumberEditText, cvvEditText;
    private Spinner salutationSpinner, countryCodeSpinner, nationalitySpinner, expiryMonthSpinner, expiryYearSpinner;
    private String[] countryCodesArray, nationalityISDArray, monthValueArray, yearArray;
    private String hotelImageUrl, textCheckInDate, textCheckOutDate, textBookingPolicies, textHotelTitle, packageID, subPackageID, packageKey, cancellationPolicy, allocationDate,subPackageTitle;
    private int textNightCount;
    private double amountPayable;
    private SoftKeyboardStateWatcher softKeyboardStateWatcher;
    private RelativeLayout creditCardRelativeLayout;
    private RelativeLayout visaCardRelativeLayout;
    private RelativeLayout masterPassRelativeLayout, topInfoRelativeLayout;
    private ImageView masterPassImageView, visaCardImageView, creditCardImageView;
    private boolean creditCardExpandStatus/*, visaCheckoutExpandStatus, masterPassExpandStatus*/;
    private int firstScrollPos, allocation, paymentMode = 1;
    private UserDTO userDTO;
    private Long roomId;
    private ArrayList<String> allocationDatesArrayList;
    private Dialog spinningDialog;
    private GTMAnalytics gtmAnalytics;
    private int pos = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
        userDTO = UserDTO.getUserDTO();

        //getCurrentLocationDetailByIp();

    }

    private void getBundleParameters(Bundle bundle) {

        hotelImageUrl = bundle.getString("hotelimageurl");

        textCheckInDate = bundle.getString("Checkin");
        textCheckOutDate = bundle.getString("Checkout");

        String[] checkInTokens = textCheckInDate.split(" ");
        String month = checkInTokens[2].substring(0,3);
        textNightCount = bundle.getInt("nightcount");
        amountPayable = bundle.getDouble("amounts");
        textBookingPolicies = bundle.getString("booking policy");
        textHotelTitle = bundle.getString("hoteltitle");

        packageID = bundle.getString("packageid");
        subPackageID = bundle.getString("subpackageid");
        packageKey = bundle.getString("packagekey");
        allocationDatesArrayList = bundle.getStringArrayList("allocationArray");
        roomId = bundle.getLong("roomId");
        allocation = bundle.getInt("selectedAllocation");
        cancellationPolicy = bundle.getString("cancellationPolicy");
        allocationDate = bundle.getString("allocationDate");

        subPackageTitle=bundle.getString("subPackage");

       cancellationPolicy= Html.fromHtml(cancellationPolicy).toString();
        textBookingPolicies=Html.fromHtml(textBookingPolicies).toString();


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = this.getArguments();
        //   if (bundle != null)
        getBundleParameters(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.staycation_booking, container, false);

        initUI();
        // ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        gtmAnalytics = AppController.getInstance().getGTMAnalytics(getActivity());
        gtmAnalytics.setScreenName("Guest Detail Page  ");

        setLayouts();

        initialiseObjects();
        UIFunctionality();
        setClickListners();
        setTextChangeListener();
        creditCardClick();

        setData();
        return rootView;
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

    public void setLocale(String lang) {

        Locale locale = new Locale(lang);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
        getActivity().getResources().updateConfiguration(configuration, getActivity().getResources().getDisplayMetrics());

    }


    private void initUI() {


        hotelImageView = (ImageView) rootView.findViewById(R.id.img_hotel);
        mainScrollView = (ScrollView) rootView.findViewById(R.id.main_scrollView);
        topInfoRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.RelativeLayout1);
        txt_payment_desc = (TextView) rootView.findViewById(R.id.txt_payment_desc);


        guestDetailLinearLayout = (LinearLayout) rootView.findViewById(R.id.layout_scroll2);
        topLinearLayout = (LinearLayout) rootView.findViewById(R.id.layout_scroll1);


        guestTextView = (TextView) rootView.findViewById(R.id.txt_guest);
        checkInDate = (TextView) rootView.findViewById(R.id.txt_checkin_date);
        checkOutDate = (TextView) rootView.findViewById(R.id.txt_checkout_date);
        nightCounts = (TextView) rootView.findViewById(R.id.txt_night_count);

        salutationSpinner = (Spinner) rootView.findViewById(R.id.spinner_salutation);
        countryCodeSpinner = (Spinner) rootView.findViewById(R.id.spinner_county_code);
        nationalitySpinner = (Spinner) rootView.findViewById(R.id.spinner_nationality);
        expiryMonthSpinner = (Spinner) rootView.findViewById(R.id.spinner_exp_month);
        expiryYearSpinner = (Spinner) rootView.findViewById(R.id.spinner_exp_year);
        hotelPaymentTextView = (TextView) rootView.findViewById(R.id.txt_hotel_payamt);
        billingAmountTextView = (TextView) rootView.findViewById(R.id.billingAmountTextView);
        firstNameEditText = (EditText) rootView.findViewById(R.id.editText_first_name);
        lastNameEditText = (EditText) rootView.findViewById(R.id.edt_last_name);
        emailEditText = (EditText) rootView.findViewById(R.id.edt_email);
        mobileEditText = (EditText) rootView.findViewById(R.id.editText_mob_no);
        cardHolderEditText = (EditText) rootView.findViewById(R.id.edt_card_holder_name);
        cardNumberEditText = (EditText) rootView.findViewById(R.id.edt_card_no);
        cvvEditText = (EditText) rootView.findViewById(R.id.edt_cvv);
        creditCardRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.creditCardRelativeLayout);
        visaCardRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.visaCardRelativeLayout);
        masterPassRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.masterPassRelativeLayout);
        creditCardImageView = (ImageView) rootView.findViewById(R.id.creditCardImageView);
        masterPassImageView = (ImageView) rootView.findViewById(R.id.masterPassImageView);
        visaCardImageView = (ImageView) rootView.findViewById(R.id.visaCardImageView);
        privacyPolicyTextView = (TextView) rootView.findViewById(R.id.privacyPolicyTextView);
        cardIncludeRelativeLayout = (LinearLayout) rootView.findViewById(R.id.include_cod);
        cardImageView = (ImageView) rootView.findViewById(R.id.img_card);
        travellerLinearLayout = (LinearLayout) rootView.findViewById(R.id.linearLayout3);
        topLinearLayout = (LinearLayout) rootView.findViewById(R.id.layout_scroll1);
        mainScrollView = (ScrollView) rootView.findViewById(R.id.main_scrollView);
        topInfoRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.RelativeLayout1);
        guestTextView = (TextView) rootView.findViewById(R.id.txt_guest);
        firstNameImageView = (ImageView) rootView.findViewById(R.id.clear_text_first_name);
        lastNameImageView = (ImageView) rootView.findViewById(R.id.clear_text_last_name);
        mobileImageView = (ImageView) rootView.findViewById(R.id.clear_text_mobile);
        emailImageView = (ImageView) rootView.findViewById(R.id.clear_text_email);
        cardNameImageView = (ImageView) rootView.findViewById(R.id.clear_text_card_name);
        payNowTextView = (TextView) rootView.findViewById(R.id.txt_proceed_pay);
        bookingPolicyTextView = (TextView) rootView.findViewById(R.id.bookingPolicyTextView);
        booking_policy_lable = (TextView) rootView.findViewById(R.id.booking_policy_lable);
        dateInfoView = (View) rootView.findViewById(R.id.dateInfoView);
        visaCardRelativeLayout.setVisibility(View.INVISIBLE);
        masterPassRelativeLayout.setVisibility(View.INVISIBLE);

        if (UserDTO.getUserDTO().getUserName() != null && !UserDTO.getUserDTO().getUserName().isEmpty()) {
            // travellerLinearLayout.setVisibility(View.VISIBLE);

            firstNameEditText.setText(UserDTO.getUserDTO().getFirstName());
            lastNameEditText.setText(UserDTO.getUserDTO().getLastName());
            emailEditText.setText(UserDTO.getUserDTO().getEmailID());
        } else {
            //  travellerLinearLayout.setVisibility(View.GONE);
            firstNameEditText.setText("");
            lastNameEditText.setText("");
            emailEditText.setText("");
        }
    }

    private void setData() {


    }

    private void initialiseObjects() {

        countryCodesArray = new Utilities().getCountryCodes();
        monthValueArray = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        yearArray = new String[30];

        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 30; i++) {
            yearArray[i] = Integer.toString(year);
            year = year + 1;
        }

    }

    private void UIFunctionality() {

        getPaymentOptions();
        Glide.with(context)
                .load(hotelImageUrl)
                .error(R.drawable.icn_default_image_loading)
                .placeholder(R.drawable.icn_default_image_loading)
                .centerCrop()
                .crossFade()
                .into(hotelImageView);



        if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ENGLISH_LANGUAGE_CODE)){

            String[] checkInTokens = textCheckInDate.split(" ");

            String month = checkInTokens[2].substring(0,3);

            checkInDate.setText(checkInTokens[0]+" "+checkInTokens[1]+" "+month+" "+checkInTokens[3]);

            String[] checkOutTokens=textCheckOutDate.split(" ");

            String checKoutMonth=checkOutTokens[2].substring(0,3);
            checkOutDate.setText(checkOutTokens[0]+" "+checkOutTokens[1]+" "+checKoutMonth+" "+checkOutTokens[3]);



        }else{

            checkInDate.setText(textCheckInDate);
            checkOutDate.setText(textCheckOutDate);
        }





        ((TextView) rootView.findViewById(R.id.text_hotel_title)).setText(textHotelTitle);

        if(allocation<2){

            ((TextView) rootView.findViewById(R.id.text_address)).setText(" ("+allocation+" "+getResources().getString(R.string.number_package)+") "+subPackageTitle);
        }else{

            ((TextView) rootView.findViewById(R.id.text_address)).setText(" ("+allocation+" "+getResources().getString(R.string.number_packages)+") "+subPackageTitle);

        }



        hotelPaymentTextView.setText(userDTO.getCurrency() + " " + amountPayable);
        billingAmountTextView.setText(userDTO.getCurrency() + " " + amountPayable);
        nightCounts.setText(String.valueOf(textNightCount) + " N");

        payNowTextView.setText(getResources().getString(R.string.proceed_to_book) + " " + userDTO.getCurrency() + " " + String.valueOf(amountPayable));

        if (textBookingPolicies != null) {
            bookingPolicyTextView.setText(textBookingPolicies);
            bookingPolicyTextView.setVisibility(View.VISIBLE);
            booking_policy_lable.setVisibility(View.VISIBLE);
            dateInfoView.setVisibility(View.VISIBLE);
        } else {
            bookingPolicyTextView.setVisibility(View.GONE);
            booking_policy_lable.setVisibility(View.GONE);
            dateInfoView.setVisibility(View.GONE);
        }


        fieldValidation();


        softKeyboardStateWatcher = new SoftKeyboardStateWatcher(rootView);


        salutationSpinner.setAdapter(new SpinnerCustomAdapter(getActivity(), R.layout.spinner_drop_down_row, new String[]{"Mr.", "Mrs.", "Ms.", "Dr.", "Miss.", "Mstr.", "M/S."}));

        // setting country code spinner data
        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
            countryCodeSpinner.setAdapter(new CountryCodeCustomAdapter(getActivity(), R.layout.spinner_drop_down_row, new Utilities().getArabicCountryCode(), countryCodesArray));
        } else {
            countryCodeSpinner.setAdapter(new CountryCodeCustomAdapter(getActivity(), R.layout.spinner_drop_down_row, new Utilities().getEnglishCountryCode(), countryCodesArray));
        }
        countryCodeSpinner.setSelection(StaycationDetailsActivity.indexselected);

        // setting nationality spinner dara
        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
            nationalitySpinner.setAdapter(new SpinnerCustomAdapterNationality(getActivity(), R.layout.spinner_drop_down_row, new Utilities().getCountriesArabic()));
        } else {
            nationalitySpinner.setAdapter(new SpinnerCustomAdapterNationality(getActivity(), R.layout.spinner_drop_down_row, new Utilities().getCountries()));
        }
        nationalitySpinner.setSelection(StaycationDetailsActivity.indexselected);

        //setting expiry month and year spinner

        expiryMonthSpinner.setAdapter(new SpinnerCustomAdapterMonth(getActivity(), R.layout.spinner_drop_down_row, new String[]{"01 (Jan)", "02 (Feb)", "03 (Mar)", "04 (Apr)", "05 (May)", "06 (Jun)", "07 (Jul)", "08 (Aug)", "09 (Sep)", "10 (Oct)", "11 (Nov)", "12 (Dec)"}, monthValueArray));
        expiryYearSpinner.setAdapter(new SpinnerCustomAdapterYear(getActivity(), R.layout.spinner_drop_down_row, yearArray));


        mainScrollView.setSmoothScrollingEnabled(true);


//        mainScrollView.setOnTouchListener(new View.OnTouchListener() {
//            private ViewTreeObserver observer;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                if (observer == null) {
//                    observer = mainScrollView.getViewTreeObserver();
//                    observer.addOnScrollChangedListener(onScrollChangedListener);
//                } else if (!observer.isAlive()) {
//                    observer.removeOnScrollChangedListener(onScrollChangedListener);
//                    observer = mainScrollView.getViewTreeObserver();
//                    observer.addOnScrollChangedListener(onScrollChangedListener);
//                }
//                v.performClick();
//                return false;
//            }
//        });

    }

    private void fieldValidation() {


        firstNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Validation.bookingNameValidation(firstNameEditText.getText().toString(), firstNameEditText, context, getResources().getString(R.string.first_name));
                }
            }
        });
        lastNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    Validation.bookingNameValidation(lastNameEditText.getText().toString(), lastNameEditText, context, getResources().getString(R.string.Last_Name));

            }
        });
        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    Validation.EmailValidation(emailEditText.getText().toString(), emailEditText, context);

            }
        });
        mobileEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    Validation.PhoneNumberValidation(mobileEditText.getText().toString(), mobileEditText, context);

            }
        });
        /*if (insertBookingDetailRequest.getPaymentMode() == 2) {//COD
            streetEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        Validation.bookingCodValidation(streetEditText.getText().toString(), streetEditText, context, "");

                }
            });
            buildingEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        Validation.bookingCodValidation(buildingEditText.getText().toString(), buildingEditText, context, getResources().getString(R.string.building_name));

                }
            });
            floorNumberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        Validation.bookingCodValidation(floorNumberEditText.getText().toString(), floorNumberEditText, context, getResources().getString(R.string.floor_number));

                }
            });
            apartmentNumberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        Validation.bookingCodValidation(apartmentNumberEditText.getText().toString(), apartmentNumberEditText, context, getResources().getString(R.string.apartment_number));

                }
            });
        }*/
//            if (insertBookingDetailRequest.getPaymentMode() == ("Card")) {//Credit Card
//                cardNumberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus) {
//                        if (!hasFocus)
//                            Validation.CreditCardValidation(cardNumberEditText.getText().toString(), cardNumberEditText, context);
//                    }
//                });
//                cardHolderEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus) {
//                        if (!hasFocus)
//                            Validation.bookingNameValidation(cardHolderEditText.getText().toString(), cardHolderEditText, context, getResources().getString(R.string.card_holder_name));
//                    }
//                });
//                cvvEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus) {
//                        if (!hasFocus)
//                            Validation.cvvValidation(cvvEditText.getText().toString(), cvvEditText, context);
//                    }
//                });
//            }


    }

    private void setClickListners() {


        firstNameImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstNameEditText.setText("");
            }
        });

        lastNameImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastNameEditText.setText("");
            }
        });
        emailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailEditText.setText("");
            }
        });

        mobileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileEditText.setText("");
            }
        });

        cardNameImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardHolderEditText.setText("");
            }
        });

        guestDetailLinearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                firstScrollPos = hotelImageView.getHeight() + topInfoRelativeLayout.getHeight();
                hideSoftKeyboard(cvvEditText);
                mainScrollView.smoothScrollTo(0, firstScrollPos);
            }
        });

        topLinearLayout.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                hideSoftKeyboard(cvvEditText);
                mainScrollView.smoothScrollTo(0, 0);
            }
        });

        (rootView.findViewById(R.id.txt_cancelation_policy)).setOnClickListener(new View.OnClickListener()

                                                                                {
                                                                                    @Override
                                                                                    public void onClick(View v) {

                                                                                        gtmAnalytics.sendEvent("Guest Detail Page ", "Cancellation", "Cancellation");

                                                                                        hideSoftKeyboard(cvvEditText);
                                                                                        if(cancellationPolicy!=null)
                                                                                        Utilities.commonErrorMessage(context, getString(R.string.Cancellation_Policy), cancellationPolicy, false, getFragmentManager());

                                                                                    }
                                                                                }

        );

        creditCardRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                creditCardRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.payment_check));
                visaCardRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.payment_no_check));
                masterPassRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.payment_no_check));

                creditCardImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.card_payment_active));
                masterPassImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.masterpass_normal));
                visaCardImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.visa_checkout_normal));
                payNowTextView.setText(getResources().getString(R.string.proceed_to_book));
                creditCardClick();
                paymentMode = 1;

                if (!creditCardExpandStatus) {
                    creditCardExpandStatus = true;
                    new Utilities().expand(cardIncludeRelativeLayout);
                } else {
                    creditCardExpandStatus = false;
                    new Utilities().collapse(cardIncludeRelativeLayout);
                }


            }
        });

        visaCardRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                visaCardRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.payment_check));
                creditCardRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.payment_no_check));
                masterPassRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.payment_no_check));

                creditCardImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.card_payment_normal));
                masterPassImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.masterpass_normal));
                visaCardImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.visa_checkout_active));

                payNowTextView.setText(getResources().getString(R.string.proceed_with_visa_checkout));
                paymentMode = 8;

                setVisaCheckoutClickListener();

               /* if (visaCheckoutExpandStatus) {
                    visaCheckoutExpandStatus = false;
                    new Utilities().collapse(visaCheckoutLinearLayout);
                } else {
                    visaCheckoutExpandStatus = true;
                    new Utilities().expand(visaCheckoutLinearLayout);
                }*/

                new Utilities().collapse(cardIncludeRelativeLayout);
                //   new Utilities().collapse(masterCardLinearLayout);

                creditCardExpandStatus = false;
                //  masterPassExpandStatus = false;


            }
        });

        masterPassRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                masterPassRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.payment_check));
                creditCardRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.payment_no_check));
                visaCardRelativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.payment_no_check));

                creditCardImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.card_payment_normal));
                masterPassImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.masterpass_active));
                visaCardImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.visa_checkout_normal));

                payNowTextView.setText(getResources().getString(R.string.proceed_with_master_pass));

                setMasterCardClickListener();
                paymentMode = 9;
                /*if (masterPassExpandStatus) {
                    masterPassExpandStatus = false;
                    new Utilities().collapse(masterCardLinearLayout);
                } else {
                    masterPassExpandStatus = true;
                    new Utilities().expand(masterCardLinearLayout);
                }*/

                new Utilities().collapse(cardIncludeRelativeLayout);
                //  new Utilities().collapse(visaCheckoutLinearLayout);

                creditCardExpandStatus = false;
                //  visaCheckoutExpandStatus = false;


            }
        });

        rootView.findViewById(R.id.txt_fair_brakeup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gtmAnalytics.sendEvent("Guest Detail Page  ", "Price breakup ", "Price breakup ");

                Bundle bundle = new Bundle();
                bundle.putInt("selectedAllocation", allocation);
                bundle.putDouble("amounts", amountPayable);


                hideSoftKeyboard(rootView);


                Fragment fragment = new GetawaysFareBreakupFragment();


                fragment.setArguments(bundle);
                addFragment(fragment);
            }
        });

        (rootView.findViewById(R.id.backImageView)).setOnClickListener(new View.OnClickListener()

                                                                       {
                                                                           @Override
                                                                           public void onClick(View v) {
                                                                               gtmAnalytics.sendEvent("Guest Detail Page ", "Back button ", "Back button click");

                                                                               if (softKeyboardStateWatcher.isSoftKeyboardOpened()) {
                                                                                   NetworkUtilities.hideSoftKeyboard(cvvEditText, context);
                                                                               } else {
                                                                                   getActivity().onBackPressed();
                                                                               }
                                                                           }
                                                                       }

        );

        (rootView.findViewById(R.id.img_call)).setOnClickListener(new View.OnClickListener() {
                                                                      @Override
                                                                      public void onClick(View v) {
                                                                          hideSoftKeyboard(cvvEditText);

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


                                                                      }
                                                                  }
        );


        payNowTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(cvvEditText);
                gtmAnalytics.sendEvent("Guest Detail Page ", "Book Now ", "Book Now ");
                if (validateForm()) {
                    if (NetworkUtilities.isInternet(getActivity())) {
                        ShowDialog();
                        checkAvailabilityCall();
                    } else {
                        dismissDialog();
                        Utilities.commonErrorMessage(context, context.getString(R.string.Network_not_avilable), context.getString(R.string.please_check_your_internet_connection), false, getFragmentManager());
                    }
                }
            }
        });

    }

    private void setTextChangeListener() {

        lastNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {
                    lastNameImageView.setVisibility(View.VISIBLE);
                } else {
                    lastNameImageView.setVisibility(View.INVISIBLE);
                }
                if (s.length() > 3) {
                    lastNameEditText.setError(null);
                }
            }
        });
        firstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {
                    firstNameImageView.setVisibility(View.VISIBLE);
                } else {
                    firstNameImageView.setVisibility(View.INVISIBLE);

                }
                if (s.length() > 3) {
                    firstNameEditText.setError(null);
                }

            }
        });
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {
                    emailImageView.setVisibility(View.VISIBLE);
                } else {
                    emailImageView.setVisibility(View.INVISIBLE);
                }
                if (s.length() > 3) {
                    emailEditText.setError(null);
                }

            }
        });
        mobileEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {
                    mobileImageView.setVisibility(View.VISIBLE);
                } else {
                    mobileImageView.setVisibility(View.INVISIBLE);
                }
                if (s.length() > 3) {
                    mobileEditText.setError(null);
                }

            }
        });

        cardHolderEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {
                    cardNameImageView.setVisibility(View.VISIBLE);
                } else {
                    cardNameImageView.setVisibility(View.INVISIBLE);
                }
                if (s.length() > 3) {
                    cardHolderEditText.setError(null);
                }


            }
        });

       /* streetEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {
                    streetNameImageView.setVisibility(View.VISIBLE);
                } else {
                    streetNameImageView.setVisibility(View.INVISIBLE);
                }
                if (s.length() > 3) {
                    streetEditText.setError(null);
                }
            }
        });
        apartmentNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {
                    apartmentImageView.setVisibility(View.VISIBLE);
                } else {
                    apartmentImageView.setVisibility(View.INVISIBLE);
                }
                if (s.length() > 3) {
                    apartmentNumberEditText.setError(null);
                }
            }
        });
        buildingEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {
                    buildingImageView.setVisibility(View.VISIBLE);
                } else {
                    buildingImageView.setVisibility(View.INVISIBLE);
                }
                if (s.length() > 3) {
                    buildingEditText.setError(null);
                }
            }
        });
        landmarkEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {
                    landmarkImageView.setVisibility(View.VISIBLE);
                } else {
                    landmarkImageView.setVisibility(View.INVISIBLE);
                }

                if (s.length() > 3) {
                    landmarkEditText.setError(null);
                }
            }
        });
        floorNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {
                    floorImageView.setVisibility(View.VISIBLE);
                } else {
                    floorImageView.setVisibility(View.INVISIBLE);
                }
                if (s.length() > 3) {
                    floorNumberEditText.setError(null);
                }
            }
        });*/

        cardNumberEditText.addTextChangedListener(new TextWatcher() {

            private char space = ' ';

            @Override
            public void afterTextChanged(Editable s) {
                cardImageView.setVisibility(View.VISIBLE);
                if (!TextUtils.equals(cardNumberEditText.getText().toString().trim(), "")) {
                    char ch = cardNumberEditText.getText().toString().trim().charAt(0);
                    if (ch == '4') {
                        cardImageView.setImageResource(R.drawable.visa);
                    } else if (ch == '5') {
                        cardImageView.setImageResource(R.drawable.mastercard);
                    } else {
                        cardImageView.setVisibility(View.GONE);
                    }
                } else {
                    cardImageView.setVisibility(View.GONE);
                }
                if (s.length() > 3) {
                    cardNumberEditText.setError(null);
                }

                if (s.length() > 0 && (s.length() % 5) == 0) {
                    final char c = s.charAt(s.length() - 1);
                    if (space == c) {
                        s.delete(s.length() - 1, s.length());
                    }
                }

                if (s.length() > 0 && (s.length() % 5) == 0) {
                    char c = s.charAt(s.length() - 1);
                    // Only if its a digit where there should be a space we insert a space
                    if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                        s.insert(s.length() - 1, String.valueOf(space));
                    }
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
    }

    private void setMasterCardClickListener() {
        try {

            SpannableString spannableString = new SpannableString(getResources().getString(R.string.masterpass_terms));
            ClickableSpan termsOfUseClickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Terms of use", "Proceed to check terms of use");


                    hideSoftKeyboard(rootView);

                    Fragment fragment = new GetawaysTermsOfUserFragment();

                    if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
                        bundle.putString("URL", Constant.ARABIC_TERMSOFUSE_URL + "?IsMobile=true");
                    } else {
                        bundle.putString("URL", Constant.ENGLISH_TERMSOFUSE_URL + "?IsMobile=true");
                    }
                    fragment.setArguments(bundle);
                    addFragment(fragment);
                    //  fragmentTransition(getActivity().getSupportFragmentManager().beginTransaction(), fragment);
                }
            };

            ClickableSpan cancellationPolicyClickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {

                    hideSoftKeyboard(cvvEditText);
                    if(cancellationPolicy!=null)
                    Utilities.commonErrorMessage(context, getString(R.string.Cancellation_Policy), cancellationPolicy, false, getFragmentManager());

                }
            };

            if (UserDTO.getUserDTO().getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE)) {
                spannableString.setSpan(termsOfUseClickableSpan, 60, 78, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(cancellationPolicyClickableSpan, 96, 115, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                spannableString.setSpan(termsOfUseClickableSpan, 82, 93, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(cancellationPolicyClickableSpan, 94, 111, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            privacyPolicyTextView.setText(spannableString);
            privacyPolicyTextView.setMovementMethod(LinkMovementMethod.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setVisaCheckoutClickListener() {
        try {
            SpannableString spannableString = new SpannableString(getResources().getString(R.string.visa_terms));
            ClickableSpan termsOfUseClickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {

                    Bundle bundle = new Bundle();
                    bundle.putString("Terms of use", "Proceed to check terms of use");


                    hideSoftKeyboard(rootView);

                    Fragment fragment = new GetawaysTermsOfUserFragment();

                    if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
                        bundle.putString("URL", Constant.ARABIC_TERMSOFUSE_URL + "?IsMobile=true");
                    } else {
                        bundle.putString("URL", Constant.ENGLISH_TERMSOFUSE_URL + "?IsMobile=true");
                    }
                    fragment.setArguments(bundle);
                    addFragment(fragment);
                    //fragmentTransition(getActivity().getSupportFragmentManager().beginTransaction(), fragment);
                }
            };

            ClickableSpan cancellationPolicyClickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {


                    hideSoftKeyboard(cvvEditText);
                    Utilities.commonErrorMessage(context, getString(R.string.Cancellation_Policy), cancellationPolicy, false, getFragmentManager());

                }
            };

            if (UserDTO.getUserDTO().getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE)) {
                spannableString.setSpan(termsOfUseClickableSpan, 63, 81, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(cancellationPolicyClickableSpan, 99, 118, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                spannableString.setSpan(termsOfUseClickableSpan, 87, 98, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(cancellationPolicyClickableSpan, 99, 116, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            privacyPolicyTextView.setText(spannableString);
            privacyPolicyTextView.setMovementMethod(LinkMovementMethod.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void creditCardClick() {

        SpannableString spannableString = new SpannableString(getResources().getString(R.string.card_terms));
        ClickableSpan termsOfUseClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

                Bundle bundle = new Bundle();
                bundle.putString("Terms of use", "Proceed to check terms of use");


                hideSoftKeyboard(rootView);

                Fragment fragment = new GetawaysTermsOfUserFragment();

                if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
                    bundle.putString("URL", Constant.ARABIC_TERMSOFUSE_URL + "?IsMobile=true");
                } else {
                    bundle.putString("URL", Constant.ENGLISH_TERMSOFUSE_URL + "?IsMobile=true");
                }
                fragment.setArguments(bundle);
                addFragment(fragment);
                //  fragmentTransition(getActivity().getSupportFragmentManager().beginTransaction(), fragment);
            }
        };

        ClickableSpan privacyPolicyClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

                Bundle bundle = new Bundle();
                bundle.putString("Privacy Policy", "Proceed to check terms of use");


                hideSoftKeyboard(rootView);

                Fragment fragment = new GetawaysPrivacyPolicyFragment();

                if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
                    bundle.putString("URL", Constant.ARABIC_PRIVACYPOLICY_URL + "?IsMobile=true");
                } else {
                    bundle.putString("URL", Constant.ENGLISH_PRIVACYPOLICY_URL + "?IsMobile=true");
                }
                fragment.setArguments(bundle);
                addFragment(fragment);
                // fragmentTransition(getActivity().getSupportFragmentManager().beginTransaction(), fragment);
            }
        };
        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE)) {
            spannableString.setSpan(termsOfUseClickableSpan, 29, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(privacyPolicyClickableSpan, 37, 51, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            spannableString.setSpan(termsOfUseClickableSpan, 39, 50, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(privacyPolicyClickableSpan, 53, 67, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        privacyPolicyTextView.setText(spannableString);
        privacyPolicyTextView.setMovementMethod(LinkMovementMethod.getInstance());


    }


    private boolean validateForm() {
        if (!Validation.bookingNameValidation(firstNameEditText.getText().toString(), firstNameEditText, context, getResources().getString(R.string.first_name))) {
            firstNameEditText.requestFocus();
            return false;
        }

        if (!Validation.bookingNameValidation(lastNameEditText.getText().toString(), lastNameEditText, context, getResources().getString(R.string.Last_Name))) {
            lastNameEditText.requestFocus();
            return false;
        }

        if (!Validation.EmailValidation(emailEditText.getText().toString(), emailEditText, context)) {
            emailEditText.requestFocus();
            return false;
        }

        if (!Validation.PhoneNumberValidation(mobileEditText.getText().toString(), mobileEditText, context)) {
            mobileEditText.requestFocus();
            return false;
        }

        if (paymentMode == 1) {//Credit Card
    /*if (cardLinearLayout.getVisibility() == View.GONE)
        creditCardRadioButton.setChecked(true);*/
            // codRadioButton.setChecked(false);
            //  cardLinearLayout.setVisibility(View.VISIBLE);
            if (!Validation.CreditCardValidation(cardNumberEditText.getText().toString().replaceAll(" ", ""), cardNumberEditText, context)) {
                cardNumberEditText.requestFocus();
                return false;
            }

            if (!Validation.bookingNameValidation(cardHolderEditText.getText().toString(), cardHolderEditText, context, getResources().getString(R.string.card_holder_name))) {
                cardHolderEditText.requestFocus();
                return false;
            }

            if (!Validation.cvvValidation(cvvEditText.getText().toString(), cvvEditText, context)) {
                cvvEditText.requestFocus();
                return false;
            }

            if (Validation.creditCardExpiryDateValidation(monthValueArray[expiryMonthSpinner.getSelectedItemPosition()], expiryYearSpinner.getSelectedItem().toString().trim())) {
                Utilities.commonErrorMessage(context, getString(R.string.app_name), getString(R.string.expire_date_validation), false, getFragmentManager());
                return false;
            }
        }


        if (paymentMode == 8 || paymentMode == 9) {//Master and Visa.
            return true;

        }
//        if (insertBookingDetailRequest.getPaymentMode().equals("Card")) {//Credit Card
//            /*if (cardLinearLayout.getVisibility() == View.GONE)
//                creditCardRadioButton.setChecked(true);*/
//            // codRadioButton.setChecked(false);
//            //  cardLinearLayout.setVisibility(View.VISIBLE);
//            if (!Validation.CreditCardValidation(cardNumberEditText.getText().toString(), cardNumberEditText, context)) {
//                cardNumberEditText.requestFocus();
//                return false;
//            }
//
//            if (!Validation.bookingNameValidation(cardHolderEditText.getText().toString(), cardHolderEditText, context, getResources().getString(R.string.card_holder_name))) {
//                cardHolderEditText.requestFocus();
//                return false;
//            }
//
//            if (!Validation.cvvValidation(cvvEditText.getText().toString(), cvvEditText, context)) {
//                cvvEditText.requestFocus();
//                return false;
//            }
//
//            if (Validation.creditCardExpiryDateValidation(monthValueArray[expiryMonthSpinner.getSelectedItemPosition()], expiryYearSpinner.getSelectedItem().toString().trim())) {
//                Utilities.commonErrorMessage(context, getString(R.string.app_name), getString(R.string.expire_date_validation), false, getFragmentManager());
//                return false;
//            }
//        }

        return true;
    }


//    final ViewTreeObserver.OnScrollChangedListener onScrollChangedListener = new
//            ViewTreeObserver.OnScrollChangedListener() {
//
//                @Override
//                public void onScrollChanged() {
//
//                    firstScrollPos = hotelImageView.getHeight() + topInfoRelativeLayout.getHeight() ;
//                    int secondScroll = firstScrollPos + (rootView.findViewById(R.id.relative_layout1)).getHeight() + (rootView.findViewById(R.id.inlude_guest_detail)).getHeight() - nationalitySpinner.getHeight() - mobileEditText.getHeight();
//                    int paymentDetailLayoutHeight = (rootView.findViewById(R.id.include_cod)).getHeight();
//                    int scrollY = mainScrollView.getScrollY();
//
//                    if (scrollY < 120) {
//                        topLinearLayout.setVisibility(View.GONE);
//                        topLinearLayout.clearAnimation();
//                        guestDetailLinearLayout.clearAnimation();
//                    }
//                    if (scrollY < firstScrollPos) {
//                        guestDetailLinearLayout.setVisibility(View.GONE);
//                        guestDetailLinearLayout.clearAnimation();
//                        guestDetailLinearLayout.setClickable(false);
//                        if (topLinearLayoutVisible) {
//                            topLinearLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up));
//                            topLinearLayoutVisible = false;
//                        }
//
//                    }
//                    if (scrollY >= firstScrollPos) {
//                        guestDetailLinearLayout.setVisibility(View.GONE);
//                        if (!topLinearLayoutVisible) {
//                            topLinearLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
//                            topLinearLayout.setVisibility(View.VISIBLE);
//                            topLinearLayoutVisible = true;
//                        }
//
//
//                    }
//                    if (scrollY >= secondScroll) {
//
//                        topLinearLayout.setVisibility(View.VISIBLE);
//
//                        if (!guestDetailLinearLayoutVisible) {
//                            guestDetailLinearLayout.setClickable(true);
//                            guestDetailLinearLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
//                            if (getActivity() != null && isAdded()) {
//                                if ((firstNameEditText.getText().toString()).equals("") && (lastNameEditText.getText().toString()).equals("")) {
//                                    guestTextView.setTextColor(getResources().getColor(R.color.red_text));
//                                    guestTextView.setText(getActivity().getResources().getString(R.string.travel_detail_msg));
//                                } else {
//                                    guestTextView.setTextColor(getResources().getColor(R.color.back_text));
//                                    guestTextView.setText(firstNameEditText.getText() + " " + lastNameEditText.getText());
//                                    ((TextView) rootView.findViewById(R.id.txt_guest_detail)).setText(emailEditText.getText() + "   " + mobileEditText.getText());
//
//                                }
//                            }
//                            guestDetailLinearLayoutVisible = true;
//                        }
//
//                    }
//
//                    if (scrollY <= paymentDetailLayoutHeight) {
//                        if (guestDetailLinearLayoutVisible) {
//                            guestDetailLinearLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up));
//                            guestDetailLinearLayoutVisible = false;
//                        }
//
//                    }
//                    if (!(firstNameEditText.getText().toString()).equals("") && !(lastNameEditText.getText().toString()).equals("") && !(mobileEditText.getText().toString()).equals("")) {
//                        if (AppController.getInstance().getCleverTapInstance() != null) {
//                            HashMap<String, Object> cleverTapHashMap = new HashMap<>();
//                            cleverTapHashMap.put("LOB", "Hotels");
//
//                            if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
//                                AppController.getInstance().getCleverTapInstance().event.push("AR Hotel Booking Info Filled", cleverTapHashMap);
//                            } else {
//                                AppController.getInstance().getCleverTapInstance().event.push("EN Hotel Booking Info Filled", cleverTapHashMap);
//                            }
//
//                            HashMap<String, Object> profileUpdateHashMap = new HashMap<>();
//                            profileUpdateHashMap.put("name", firstNameEditText.getText().toString() + " " + lastNameEditText.getText().toString());
//                            profileUpdateHashMap.put("Email", emailEditText.getText().toString().trim());
//                            profileUpdateHashMap.put("Phone no", mobileEditText.getText().toString().trim());
//                            profileUpdateHashMap.put("countryTextView", nationalitySpinner.getSelectedItem().toString());
//
//                            AppController.getInstance().getCleverTapInstance().profile.push(profileUpdateHashMap);
//
//                        }
//                    }
//                }
//            };

    private void hideSoftKeyboard(View view) {
        if (softKeyboardStateWatcher.isSoftKeyboardOpened()) {
            NetworkUtilities.hideSoftKeyboard(view, getActivity());
        }
    }

    private void addFragment(Fragment fragment) {
        try {
            FragmentTransaction fragmentTransition = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransition.add(R.id.calenderRelativeLayout, fragment);
            fragmentTransition.addToBackStack(null);
            fragmentTransition.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkAvailabilityCall() {
        CheckAvailabilityRequest checkAvailabilityRequest = new CheckAvailabilityRequest();
        checkAvailabilityRequest.setRoomId(roomId);
        checkAvailabilityRequest.setPackageId(Long.parseLong(packageID));
        checkAvailabilityRequest.setSubPackageId(Long.parseLong(subPackageID));
        checkAvailabilityRequest.setIpAddress(NetworkUtilities.getIp());
        checkAvailabilityRequest.setCurrencyCode(UserDTO.getUserDTO().getCurrency());
        checkAvailabilityRequest.setAllocationDates(allocationDatesArrayList);
        checkAvailabilityRequest.setAllocation(allocation);

        try {
            String response = new checkAllocationAsyncTask(new GsonBuilder().serializeNulls().create().toJson(checkAvailabilityRequest)).execute(Constant.GetawaysEndPointUrl + Constant.checkAvailabilityURL + UserDTO.getUserDTO().getLanguage()).get();
            if (response.equals("true"))
                insertBookingDetailCall();
        } catch (InterruptedException e) {
            e.printStackTrace();
            dismissDialog();
        } catch (ExecutionException e) {
            e.printStackTrace();
            dismissDialog();

        }


    }

    private void ShowDialog() {
        spinningDialog = CustomProgressDialog.showProgressDialog(getActivity());
        spinningDialog.show();
    }

    private void dismissDialog() {
        if (spinningDialog != null && spinningDialog.isShowing()) {
            spinningDialog.dismiss();
        }
    }

    private void insertBookingDetailCall() {
        ShowDialog();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Date date = null;
        try {
            date = sdf.parse(sdf.format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse(allocationDate)));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String checkinDate = sdf.format(date);

        InsertBookingDetailsRequest insertBookingDetailsRequest = new InsertBookingDetailsRequest();
        GuestDetailsDTO guestDetailsDTO = new GuestDetailsDTO();
        guestDetailsDTO.setGuestAddress(null);
        guestDetailsDTO.setGuestCountryIsdCode(countryCodesArray[countryCodeSpinner.getSelectedItemPosition()]);
        guestDetailsDTO.setGuestEmail(emailEditText.getText().toString().trim());
        guestDetailsDTO.setGuestFirstName(firstNameEditText.getText().toString().trim());
        guestDetailsDTO.setGuestLastName(lastNameEditText.getText().toString().trim());
        guestDetailsDTO.setGuestSalutation(salutationSpinner.getSelectedItem().toString());
        guestDetailsDTO.setGuestMobileNumber(mobileEditText.getText().toString().trim());
        guestDetailsDTO.setLeaderTravellerSalutation(salutationSpinner.getSelectedItem().toString());
        guestDetailsDTO.setLeadTravellerFirstName(firstNameEditText.getText().toString().trim());
        guestDetailsDTO.setLeadTravellerLastName(lastNameEditText.getText().toString().trim());
        guestDetailsDTO.setPrimaryGuest(true);
        guestDetailsDTO.setLeadTraveller(true);
        insertBookingDetailsRequest.setGuestDetail(guestDetailsDTO);
        PaymentDetailsDto paymentDetailsDto = new PaymentDetailsDto();
        paymentDetailsDto.setCardNumber(cardNumberEditText.getText().toString().replaceAll(" ", "").trim());
        paymentDetailsDto.setCardCvvNumber(cvvEditText.getText().toString());
        paymentDetailsDto.setCardHolderName(cardHolderEditText.getText().toString().trim());
        paymentDetailsDto.setCardExpiryDate(monthValueArray[expiryMonthSpinner.getSelectedItemPosition()] + "/" + "01/" + expiryYearSpinner.getSelectedItem().toString().trim());
        paymentDetailsDto.setPaymentMode(paymentMode);
        insertBookingDetailsRequest.setPaymentDetails(paymentDetailsDto);
        insertBookingDetailsRequest.setPackageKey(packageID + ":" + subPackageID + ":" + allocation + ":" + checkinDate);
        insertBookingDetailsRequest.setLanguageCode(UserDTO.getUserDTO().getLanguage());
        insertBookingDetailsRequest.setCurrencyCode(UserDTO.getUserDTO().getCurrency());
        insertBookingDetailsRequest.setAccountId(UserDTO.getUserDTO().getAccountId());
        insertBookingDetailsRequest.setAppToken("AppToken");
        insertBookingDetailsRequest.setBookingUrl("http://localhost:50385//Book");
        if (UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE))
            insertBookingDetailsRequest.setReturnUrl(Constant.getawaysBookingReturnArURL);
        else
            insertBookingDetailsRequest.setReturnUrl(Constant.getawaysBookingReturnEnURL);

        insertBookingDetailsRequest.setUserAgent("Android");

        new PaymentAsyncTask(new GsonBuilder().serializeNulls().create().toJson(insertBookingDetailsRequest)).execute(Constant.GetawaysEndPointUrl + Constant.insertBookingDetailURL + UserDTO.getUserDTO().getLanguage());

    }

    public class checkAllocationAsyncTask extends AsyncTask<String, Void, String> {

        String mRequest;

        public checkAllocationAsyncTask(String request) {
            mRequest = request;
        }

        @Override
        protected String doInBackground(String... params) {

            InputStream inputStream;
            String response = null;
            try {

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(params[0]);

                StringEntity stringEntity = new StringEntity(mRequest);
                Map<String, String> headerMap = NetworkUtilities.getStayCationHeader();
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    if (!(entry.getKey().equals("Accept")))
                        httpPost.addHeader(entry.getKey(), entry.getValue());
                }
                stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
                response = stringBuilder.toString();

            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }
            dismissDialog();
            return response;


        }

     /*   @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try {


                if(response.equals("true")){
                    insertBookingDetailCall();
                }else{
                    Utilities.commonErrorMessage(getActivity(),getResources().getString(R.string.app_name),getResources().getString(R.string.common_error_msg),false,getFragmentManager());
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }*/
    }

    /*private void getCurrentLocationDetailByIp() {
        new AsyncTask<Void, Void, String>() {


            @Override
            protected String doInBackground(final Void... params) {
                String location = "";
                try {
                    URL url = new URL(Constant.GET_MY_DETAIL_FROM_IP);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                    while ((location = bufferedReader.readLine()) != null) {
                        return location;
                    }
                    bufferedReader.close();
                } catch (MalformedURLException e) {
                    Log.e("error on fetching detail from ip", "Malformed URL: " + e.getMessage());
                } catch (IOException e) {
                    Log.e("error on fetching detail from ip", "I/O Error: " + e.getMessage());
                }
                return location;
            }

            @Override
            protected void onPostExecute(String location) {
                if (!TextUtils.equals(location, "")) {
                    currentLocationDetail = new Gson().fromJson(location, CurrentLocationDetail.class);


                    if (currentLocationDetail != null) {
                        if (currentLocationDetail.getCountry_name() != null) {
                            List<String> list = Arrays.asList(new Utilities().getCountries());
                            if (list.contains(currentLocationDetail.getCountry_name())) {
                                pos = list.indexOf(currentLocationDetail.getCountry_name());
                            }
                        }


                    }
                }

            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }*/
    public class PaymentAsyncTask extends AsyncTask<String, Void, String> {

        String mRequest;

        public PaymentAsyncTask(String request) {
            mRequest = request;
        }

        @Override
        protected String doInBackground(String... params) {

            InputStream inputStream;
            String response = null;
            try {

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(params[0]);

                StringEntity stringEntity = new StringEntity(mRequest);
                Map<String, String> headerMap = NetworkUtilities.getStayCationHeader();
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    if (!(entry.getKey().equals("Accept")))
                        httpPost.addHeader(entry.getKey(), entry.getValue());
                }
                stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
                response = stringBuilder.toString();

            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }

            return response;


        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try {
                 dismissDialog();
                if (response != null) {
                    StringBuilder paymentResponseStringBuilder = new StringBuilder();
                    paymentResponseStringBuilder.append("<html><body onload=\"document.forms[0].submit()\">");

                    JSONObject responseJsonObject = new JSONObject(response);
                    JSONObject paymentGatewayJsonObject = responseJsonObject.getJSONObject("PaymentGatewayPostRequest");
                    String postUrl = paymentGatewayJsonObject.getString("PostUrl");
                    JSONObject postDataJsonObject = paymentGatewayJsonObject.getJSONObject("PostData");

                    paymentResponseStringBuilder.append(String.format("<form method=\"post\" action=\"%s\">", postUrl));
                    HashMap<String, String> map = new Gson().fromJson(postDataJsonObject.toString(), HashMap.class);

                    for (Map.Entry<String, String> ss : map.entrySet()) {
                        paymentResponseStringBuilder.append(String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\">\n", ss.getKey(), ss.getValue()));
                    }

                    paymentResponseStringBuilder.append(String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\">\n", "ApplicationToken", "AndroidAppToken"));

                    //added on 21-11-2016..by Supriya
                    paymentResponseStringBuilder.append(String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\">\n", "UserTrackingId", UserDTO.getUserDTO().getUserTrackingId()));
                    paymentResponseStringBuilder.append(String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\">\n", "SessionId", UserDTO.getUserDTO().getSessionId()));

               /* if(isCOD) {
                    paymentResponseStringBuilder.append(String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\">\n", "FloorNumber","" + floorNumberEditText.getText().toString()));
                    paymentResponseStringBuilder.append(String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\">\n", "NearestLandmark", "" + landmarkEditText.getText().toString()));
                    paymentResponseStringBuilder.append(String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\">\n", "StreetName", "" +  streetEditText.getText().toString()));
                    paymentResponseStringBuilder.append(String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\">\n", "BuildingNumber", "" + buildingEditText.getText().toString()));
                    paymentResponseStringBuilder.append(String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\">\n", "ApartmentNumber", "" + apartmentNumberEditText.getText().toString()));
                }*/

                    paymentResponseStringBuilder.append("</form></body></html>");

                    dismissDialog();

                    Fragment fragment = new GetawaysPaymentGatewayFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("html", paymentResponseStringBuilder.toString());
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransition = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransition.replace(R.id.calenderRelativeLayout, fragment);
                    fragmentTransition.addToBackStack(null);
                    fragmentTransition.commit();
                } else {
                    dismissDialog();
                    Utilities.commonErrorMessage(getActivity(), getResources().getString(R.string.app_name), getResources().getString(R.string.common_error_msg), false, getFragmentManager());

                }

            } catch (Exception e) {
                e.printStackTrace();
                dismissDialog();
                Utilities.commonErrorMessage(getActivity(), getResources().getString(R.string.app_name), getResources().getString(R.string.common_error_msg), false, getFragmentManager());

            }

        }
    }



    private void getPaymentOptions() {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("LanguageCode", UserDTO.getUserDTO().getLanguage());
            jsonObject.put("IpAddress", NetworkUtilities.getIp());
            jsonObject.put("SellingCurrency", UserDTO.getUserDTO().getCurrency());
            jsonObject.put("Entity", "Getaway");
            jsonObject.put("IsCOD", "false");
            jsonObject.put("IsPayAtHotel", "false");

        //    TrustManager.allowAllSSL();

            new PaymentOptionsAsyncTask(jsonObject.toString()).execute(new String[]{Constant.PAYMENT_OPTIONS_URL});

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class PaymentOptionsAsyncTask extends AsyncTask<String, Void, String> {

        String mRequest;

        public PaymentOptionsAsyncTask(String request) {
            mRequest = request;
        }

        @Override
        protected String doInBackground(String... params) {

            InputStream inputStream;
            String response = null;
            try {

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(params[0]);

                StringEntity stringEntity = new StringEntity(mRequest);
                Map<String, String> headerMap = NetworkUtilities.getHeaders(getActivity());
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    if (!(entry.getKey().equals("Accept")))
                        httpPost.addHeader(entry.getKey(), entry.getValue());
                }
                stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
                response = stringBuilder.toString();

            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }

            return response;


        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try {

                // visaCheckOutRadioButton.setVisibility(View.VISIBLE);
                // masterCardRadioButton.setVisibility(View.VISIBLE);

                JSONObject responseJsonObject = new JSONObject(response);
                if (responseJsonObject.get("0") != null) {
                  /*  paymentMode = (String) responseJsonObject.get("0");
                    insertBookingDetailRequest.setPaymentMode(paymentMode);
*/
                    creditCardRelativeLayout.performClick();
                    new Utilities().expand(cardIncludeRelativeLayout);
                }

                if (responseJsonObject.get("2") != null) {
                    visaCardRelativeLayout.setVisibility(View.VISIBLE);
                }
                if (responseJsonObject.get("3") != null) {
                    masterPassRelativeLayout.setVisibility(View.VISIBLE);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
