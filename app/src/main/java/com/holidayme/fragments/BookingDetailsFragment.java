package com.holidayme.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.HolidayMeFont;
import com.holidayme.activities.util.NonScrollListView;
import com.holidayme.activities.util.SoftKeyboardStateWatcher;
import com.holidayme.activities.util.Utilities;
import com.holidayme.adapter.CountryCodeCustomAdapter;
import com.holidayme.adapter.SpecialRequestListAdapter;
import com.holidayme.adapter.SpinnerCustomAdapter;
import com.holidayme.adapter.SpinnerCustomAdapterMonth;
import com.holidayme.adapter.SpinnerCustomAdapterNationality;
import com.holidayme.adapter.SpinnerCustomAdapterYear;
import com.holidayme.booking_mvp.BookingDetailsPresenter;
import com.holidayme.booking_mvp.IBookingView;
import com.holidayme.common.BookingStatusTypes;
import com.holidayme.common.CustomProgressDialog;
import com.holidayme.common.Log;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.common.TransactionStatusTypes;
import com.holidayme.common.Validation;
import com.holidayme.data.SpecialRequestsDto;
import com.holidayme.data.UserDTO;
import com.holidayme.data.UserInfo;
import com.holidayme.gtm.GTMAnalytics;
import com.holidayme.request.ApplyCouponCodeRequest;
import com.holidayme.request.HotelListingRequest;
import com.holidayme.request.InsertBookingDetailRequest;
import com.holidayme.request.RoomDetail;
import com.holidayme.response.ApplyCouponCodeResponse;
import com.holidayme.response.BookingDetailsResponse;
import com.holidayme.response.CurrentLocationDetail;
import com.holidayme.response.HotelBookConfirmationResponse;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by supriya.sakore on 08-07-2016.
 */
@SuppressLint("SetTextI18n")
public class BookingDetailsFragment extends BaseFragment implements SpecialRequestListAdapter.OnItemRemove, IBookingView.IBookingDetailsView {

    private LinearLayout /*codLinearLayout, cardLinearLayout*/ topLinearLayout, guestDetailLinearLayout, travellerLinearLayout,cardIncludeRelativeLayout
            /*visaCheckoutLinearLayout, masterCardLinearLayout*/;
    private RelativeLayout applyCouponCodeRelativeLayout, topInfoRelativeLayout;

    private Button applyCouponCodeButton;
    private ImageView hotelImageView, cardImageView, couponCodeStatusImageView,
            firstNameImageView, lastNameImageView, emailImageView, mobileImageView, cardNameImageView;
            /*streetNameImageView, buildingImageView, floorImageView, apartmentImageView, landmarkImageView*/;
    private TextView currencyDescTextView, billingAmountTextView, amountBeforeCouponCodeTextView,
            amountDescTextView, travelerCountTextView, specialRequestTextView, hotelPaymentTextView,txt_policies,
            payAtHotelTextView, paymentDescTextView, payNowTextView/*, readMoreVisaCheckoutTextView,
            readMoreMasterPassTextView*/, guestTextView, privacyPolicyTextView/*visaCheckoutTextView,masterCardTextView*/;
    private EditText firstNameEditText, lastNameEditText, emailEditText, mobileEditText,
            cardHolderEditText, cardNumberEditText, cvvEditText, /*streetEditText, buildingEditText,
            floorNumberEditText, apartmentNumberEditText, landmarkEditText,*/
            couponCodeEditText;
    private RadioButton creditCardRadioButton /*visaCheckOutRadioButton masterCardRadioButton*/;
    private View rootView;
    private CheckBox travellerCheckBox;
    private Dialog spinningDialog;
    private ScrollView mainScrollView;
    private Spinner salutationSpinner, countryCodeSpinner, nationalitySpinner/*, areaSpinner*/, expiryMonthSpinner, expiryYearSpinner;

    private boolean topLinearLayoutVisible, guestDetailLinearLayoutVisible;
    private long hotelId;
    private int night, advance_days, firstScrollPos;

    private BookingDetailsResponse mBookingDetailsResponse;
    private Context context;
    private SoftKeyboardStateWatcher softKeyboardStateWatcher;
    private SpecialRequestListAdapter specialRequestListAdapter;
    private String tag, discount, amount, off, amountUsed, promoCode = "", EVENT_CATEGORY = "Booking Page", paymentMode = "Card";
    private CurrentLocationDetail locationDetail;
    private GTMAnalytics gtmAnalytics;
    private HotelBookConfirmationResponse hotelBookConfirmationResponse;
    private Calendar calenderFromDate, calenderToDate;
    private Date dateTo, dateFrom;
    private InsertBookingDetailRequest insertBookingDetailRequest;
    private NonScrollListView nonScrollListView;
    private ArrayList<SpecialRequestsDto> specialRequestsDtoArrayList;
    private ArrayList<String> areaArrayList, areaIdsArrayList;
    private String[] countryCodesArray, nationalityISDArray, monthValueArray, yearArray;
    private Integer[] salutationValuesArray;
    //private BookingFailCustomDialog bookingFailCustomDialog;
    private boolean creditCardExpandStatus/*, visaCheckoutExpandStatus, masterPassExpandStatus*/;
    BookingDetailsPresenter hotelBookingPresenter;
    View creditCardBottomLineView, visaCheckOutBottomLineView, masterCardBottomLineView, visaCheckOutSecondBottomLineView;
    RadioButton visaCheckOutRadioButton,masterCardRadioButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_booking, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        context = getActivity();
        initialiseObjects();

        getPaymentOptions();
        return rootView;
    }

    private void getPaymentOptions() {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("LanguageCode", mBookingDetailsResponse.getBookingDetails().getLanguageCode());
            jsonObject.put("IpAddress", mBookingDetailsResponse.getBookingDetails().getBookingIpAddress());
            jsonObject.put("SellingCurrency", mBookingDetailsResponse.getBookingDetails().getSellingCurrency());
            jsonObject.put("Entity", "Hotel");
            jsonObject.put("IsCOD", "true");
            jsonObject.put("IsPayAtHotel", mBookingDetailsResponse.getBookingDetails().isPayAtHotel() + "");

          //  TrustManager.allowAllSSL();

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
                    paymentMode = (String) responseJsonObject.get("0");
                    insertBookingDetailRequest.setPaymentMode(paymentMode);

                    creditCardRadioButton.setChecked(true);
                    new Utilities().expand(cardIncludeRelativeLayout);
                    setCreditCardClickListener();


                    /*if(creditCardExpandStatus || (!creditCardExpandStatus && !visaCheckoutExpandStatus && !masterPassExpandStatus)) {
                        creditCardRadioButton.setChecked(true);
                        new Utilities().expand(cardIncludeRelativeLayout);
                        setCreditCardClickListerner();
                    }*/
                }

                if (responseJsonObject.get("2") != null) {
                    visaCheckOutRadioButton.setVisibility(View.VISIBLE);
                    visaCheckOutBottomLineView.setVisibility(View.VISIBLE);
                    visaCheckOutSecondBottomLineView.setVisibility(View.VISIBLE);
                    creditCardBottomLineView.setVisibility(View.VISIBLE);

                  /*  if(visaCheckoutExpandStatus)
                    {
                        visaCheckOutRadioButton.setChecked(true);
                        new Utilities().expand(visaCheckoutLinearLayout);
                        setVisaCheckoutClickListerner();
                    }*/
                }
                if (responseJsonObject.get("3") != null) {
                    masterCardRadioButton.setVisibility(View.VISIBLE);
                    masterCardBottomLineView.setVisibility(View.VISIBLE);
                    /*if(masterPassExpandStatus)
                    {
                        masterCardRadioButton.setChecked(true);
                        new Utilities().expand(masterCardLinearLayout);
                        setMasterCardClickListener();
                    }*/
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private void initialiseObjects() {

        hotelBookingPresenter = new BookingDetailsPresenter(this);

        countryCodesArray = new Utilities().getCountryCodes();
        nationalityISDArray = new Utilities().getCountriesISD();
        monthValueArray = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        yearArray = new String[30];
        salutationValuesArray = new Integer[]{1, 2, 3, 4, 7, 5, 6};

        spinningDialog = CustomProgressDialog.showProgressDialog(getActivity());

        if (specialRequestsDtoArrayList == null)
            specialRequestsDtoArrayList = new ArrayList<>();

        if (areaArrayList == null)
            areaArrayList = new ArrayList<>();

        if (areaIdsArrayList == null)
            areaIdsArrayList = new ArrayList<>();

        if (calenderToDate == null)
            calenderToDate = new GregorianCalendar();

        if (calenderFromDate == null)
            calenderFromDate = new GregorianCalendar();

        if (insertBookingDetailRequest == null)
            insertBookingDetailRequest = new InsertBookingDetailRequest();

        if (gtmAnalytics == null)
            gtmAnalytics = AppController.getInstance().getGTMAnalytics(getActivity());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = this.getArguments();

        if (bundle != null) {

            if (bundle.getParcelable("BookingDetailResponse") != null) {
                mBookingDetailsResponse = bundle.getParcelable("BookingDetailResponse");
            }
            if (bundle.getParcelable("location") != null) {
                locationDetail = bundle.getParcelable("location");
            }
            hotelId = bundle.getLong("hotelid");
        }
    }


    @Override
    public void initUIElements() {

        hotelImageView = (ImageView) rootView.findViewById(R.id.img_hotel);
        travellerCheckBox = (CheckBox) rootView.findViewById(R.id.checkBox_lead_traveller);
        // cardLinearLayout = (LinearLayout) rootView.findViewById(R.id.layout_card_include);
        // codLinearLayout = (LinearLayout) rootView.findViewById(R.id.layout_cod_include);
        creditCardRadioButton = (RadioButton) rootView.findViewById(R.id.rdo_credit_card);
        //   codRadioButton = (RadioButton) rootView.findViewById(R.id.rdo_COD);
        travelerCountTextView = (TextView) rootView.findViewById(R.id.txt_traveler_count);
        specialRequestTextView = (TextView) rootView.findViewById(R.id.txt_special_request);
        specialRequestTextView.setVisibility(View.VISIBLE);
        salutationSpinner = (Spinner) rootView.findViewById(R.id.spinner_salutation);
        countryCodeSpinner = (Spinner) rootView.findViewById(R.id.spinner_county_code);
        applyCouponCodeButton = (Button) rootView.findViewById(R.id.btn_apply_coupon_code);
        couponCodeEditText = (EditText) rootView.findViewById(R.id.edt_txt_coupon_code);
        couponCodeStatusImageView = (ImageView) rootView.findViewById(R.id.coupon_code_status_img);
        nationalitySpinner = (Spinner) rootView.findViewById(R.id.spinner_nationality);
        //  areaSpinner = (Spinner) rootView.findViewById(R.id.spinner_area);
        expiryMonthSpinner = (Spinner) rootView.findViewById(R.id.spinner_exp_month);
        expiryYearSpinner = (Spinner) rootView.findViewById(R.id.spinner_exp_year);
        firstNameEditText = (EditText) rootView.findViewById(R.id.editText_first_name);
        lastNameEditText = (EditText) rootView.findViewById(R.id.edt_last_name);
        emailEditText = (EditText) rootView.findViewById(R.id.edt_email);
        mobileEditText = (EditText) rootView.findViewById(R.id.editText_mob_no);
        cardHolderEditText = (EditText) rootView.findViewById(R.id.edt_card_holder_name);
        cardNumberEditText = (EditText) rootView.findViewById(R.id.edt_card_no);
        cvvEditText = (EditText) rootView.findViewById(R.id.edt_cvv);
      /*  streetEditText = (EditText) rootView.findViewById(R.id.edt_street);
        buildingEditText = (EditText) rootView.findViewById(R.id.edt_building);
        floorNumberEditText = (EditText) rootView.findViewById(R.id.edt_floor);
        apartmentNumberEditText = (EditText) rootView.findViewById(R.id.edt_apartment);
        landmarkEditText = (EditText) rootView.findViewById(R.id.edt_landmark);*/
        hotelPaymentTextView = (TextView) rootView.findViewById(R.id.txt_hotel_payamt);
        payNowTextView = (TextView) rootView.findViewById(R.id.txt_proceed_pay);
        applyCouponCodeRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.layout_apply_coupon_code);
        payAtHotelTextView = (TextView) rootView.findViewById(R.id.txt_payat_hote);
        paymentDescTextView = (TextView) rootView.findViewById(R.id.txt_payment_desc);
        currencyDescTextView = (TextView) rootView.findViewById(R.id.txt_currency_desc);
        billingAmountTextView = (TextView) rootView.findViewById(R.id.billingAmountTextView);
        amountBeforeCouponCodeTextView = (TextView) rootView.findViewById(R.id.txt_amt_before_coupon_code);
        amountDescTextView = (TextView) rootView.findViewById(R.id.txt_amt_dsc);
        cardImageView = (ImageView) rootView.findViewById(R.id.img_card);
        travellerLinearLayout = (LinearLayout) rootView.findViewById(R.id.linearLayout3);
        cvvEditText.setSelection(cvvEditText.getText().length());
        guestDetailLinearLayout = (LinearLayout) rootView.findViewById(R.id.layout_scroll2);
        topLinearLayout = (LinearLayout) rootView.findViewById(R.id.layout_scroll1);
        mainScrollView = (ScrollView) rootView.findViewById(R.id.main_scrollView);
        topInfoRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.RelativeLayout1);
        guestTextView = (TextView) rootView.findViewById(R.id.txt_guest);
        firstNameImageView = (ImageView) rootView.findViewById(R.id.clear_text_first_name);
        lastNameImageView = (ImageView) rootView.findViewById(R.id.clear_text_last_name);
        mobileImageView = (ImageView) rootView.findViewById(R.id.clear_text_mobile);
        emailImageView = (ImageView) rootView.findViewById(R.id.clear_text_email);
        cardNameImageView = (ImageView) rootView.findViewById(R.id.clear_text_card_name);
      /*  streetNameImageView = (ImageView) rootView.findViewById(R.id.clear_text_street_name);
        apartmentImageView = (ImageView) rootView.findViewById(R.id.clear_text_apartment);
        buildingImageView = (ImageView) rootView.findViewById(R.id.clear_text_building);
        floorImageView = (ImageView) rootView.findViewById(R.id.clear_text_floor);
        landmarkImageView = (ImageView) rootView.findViewById(R.id.clear_text_landmark);*/
        nonScrollListView = ((NonScrollListView) rootView.findViewById(R.id.recycler_special_request));
        visaCheckOutRadioButton = (RadioButton) rootView.findViewById(R.id.visaCheckOutRadioButton);
        masterCardRadioButton = (RadioButton) rootView.findViewById(R.id.masterCardRadioButton);

        cardIncludeRelativeLayout = (LinearLayout) rootView.findViewById(R.id.include_cod);
        // cardIncludeLinearLayout = (LinearLayout) rootView.findViewById(R.id.layout_card_include);
       // visaCheckoutLinearLayout = (LinearLayout) rootView.findViewById(R.id.visaCheckoutLinearLayout);
       // masterCardLinearLayout = (LinearLayout) rootView.findViewById(R.id.masterPassLinearLayout);
       // readMoreVisaCheckoutTextView = (TextView) rootView.findViewById(R.id.readMoreVisaCheckoutTextView);
       // readMoreMasterPassTextView = (TextView) rootView.findViewById(R.id.readMoreMasterPassTextView);
        visaCheckOutBottomLineView = rootView.findViewById(R.id.visaCheckoutBottomLineView);
        visaCheckOutSecondBottomLineView = rootView.findViewById(R.id.visaCheckoutSecondBottomLineView);
        masterCardBottomLineView = rootView.findViewById(R.id.masterCardBottomLineView);
        creditCardBottomLineView = rootView.findViewById(R.id.creditCardBottomLineView);
        paymentDescTextView.setTextSize(10);

        privacyPolicyTextView = (TextView) rootView.findViewById(R.id.privacyPolicyTextView);

        setFonts();

        cvvEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

        int year = Calendar.getInstance().get(Calendar.YEAR);

        for (int i = 0; i < 30; i++) {
            yearArray[i] = Integer.toString(year);
            year = year + 1;
        }

        setUpSpecialRequestAdapter();

        if (UserDTO.getUserDTO().getUserName()!= null && !UserDTO.getUserDTO().getUserName().isEmpty()) {
            travellerLinearLayout.setVisibility(View.VISIBLE);
            travellerCheckBox.setChecked(true);
            firstNameEditText.setText(UserDTO.getUserDTO().getFirstName());
            lastNameEditText.setText(UserDTO.getUserDTO().getLastName());
            emailEditText.setText(UserDTO.getUserDTO().getEmailID());
        } else {
            travellerLinearLayout.setVisibility(View.GONE);
            travellerCheckBox.setChecked(false);
            firstNameEditText.setText("");
            lastNameEditText.setText("");
            emailEditText.setText("");
        }

        UIFunctionality();

        gtmAnalytics.setScreenName("Booking Screen");
    }

    private void setFonts() {
        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
            //  HolidayMeFont.overrideFonts(context, codRadioButton, Constant.NotoKufiArabic_Bold);
            HolidayMeFont.overrideFonts(context, creditCardRadioButton, Constant.NotoKufiArabic_Bold);
            HolidayMeFont.overrideFonts(context, applyCouponCodeButton, Constant.NotoKufiArabic_Bold);
        } else {
            //  HolidayMeFont.overrideFonts(context, codRadioButton, Constant.HelveticaNeueRoman);
            HolidayMeFont.overrideFonts(context, creditCardRadioButton, Constant.HelveticaNeueRoman);
            HolidayMeFont.overrideFonts(context, applyCouponCodeButton, Constant.HelveticaNeueRoman);
        }
    }

    @Override
    public void showDialog() {
        spinningDialog.show();
    }

    @Override
    public void dismissDialog() {
        if (spinningDialog != null && spinningDialog.isShowing()) {
            spinningDialog.dismiss();
        }
    }

    private void UIFunctionality() {


        softKeyboardStateWatcher = new SoftKeyboardStateWatcher(rootView);
        currencyDescTextView.setText(String.format(getResources().getString(R.string.billing_currency_text1_new), mBookingDetailsResponse.getBillingCurrencyCode(), String.format(Locale.US, "%.2f", mBookingDetailsResponse.getSellingPriceInBillingCurrencyCode())));
        hotelPaymentTextView.setText(mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getSellingCurrency() + " " + String.format(Locale.US, "%.2f", mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getTotalSellingPrice()));
        ((TextView) rootView.findViewById(R.id.txt_hotel)).setText(mBookingDetailsResponse.getBookingDetails().getTitle());
        fieldValidation();
        amountDescTextView.setText(Html.fromHtml(getResources().getString(R.string.booking_page_amount_total_text1) + " " + mBookingDetailsResponse.getBookingDetails().getTitle() + " " + (getResources().getString(R.string.booking_page_amount_total_text2)) + "<font color='#008000'><b>" + " " + mBookingDetailsResponse.getBillingCurrencyCode() + " " + String.format(Locale.US, "%.2f",mBookingDetailsResponse.getSellingPriceInBillingCurrencyCode()) + "</b></font>"));
        billingAmountTextView.setText(mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getSellingCurrency() + " " + String.format(Locale.US, "%.2f", mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getTotalSellingPrice()));

        if (mBookingDetailsResponse.getBookingDetails().isPayAtHotel()) {
            payAtHotelTextView.setVisibility(View.VISIBLE);
            applyCouponCodeRelativeLayout.setVisibility(View.GONE);
            amountBeforeCouponCodeTextView.setVisibility(View.GONE);
            // codRadioButton.setVisibility(View.GONE);
//            codLinearLayout.setVisibility(View.GONE);
            currencyDescTextView.setVisibility(View.GONE);
            amountDescTextView.setVisibility(View.VISIBLE);

            payNowTextView.setText(getString(R.string.proceed_to_book));
            paymentDescTextView.setText(getResources().getString(R.string.no_card_charge));

        } else {
            paymentDescTextView.setText(getResources().getString(R.string.card_charge));
            //   codRadioButton.setVisibility(View.VISIBLE);
            payAtHotelTextView.setVisibility(View.GONE);
            applyCouponCodeRelativeLayout.setVisibility(View.VISIBLE);
            if (mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getSellingCurrency().equals(mBookingDetailsResponse.getBillingCurrencyCode())) {
                currencyDescTextView.setVisibility(View.GONE);
            } else {
                currencyDescTextView.setVisibility(View.VISIBLE);
            }
            payNowTextView.setText(getResources().getString(R.string.proceed_to_book));
          //  payNowTextView.setText(getString(R.string.pay_securely));
            amountDescTextView.setVisibility(View.GONE);

        }

        setClickListeners();
        setTextChangeListener();

        int pos=0;
        if (locationDetail != null) {
            if (locationDetail.getCountry_name() != null) {
                List<String> list = Arrays.asList(new Utilities().getCountries());
                if (list.contains(locationDetail.getCountry_name())) {
                    pos = list.indexOf(locationDetail.getCountry_name());
                }
            }
        }

        salutationSpinner.setAdapter(new SpinnerCustomAdapter(getActivity(), R.layout.spinner_drop_down_row, new String[]{"Mr.", "Mrs.", "Ms.", "Dr.", "Miss.", "Mstr.", "M/S."}));

        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
            countryCodeSpinner.setAdapter(new CountryCodeCustomAdapter(getActivity(), R.layout.spinner_drop_down_row, new Utilities().getArabicCountryCode(), countryCodesArray));
        } else {
            countryCodeSpinner.setAdapter(new CountryCodeCustomAdapter(getActivity(), R.layout.spinner_drop_down_row, new Utilities().getEnglishCountryCode(), countryCodesArray));
        }
        countryCodeSpinner.setSelection(pos);

        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
            nationalitySpinner.setAdapter(new SpinnerCustomAdapterNationality(getActivity(), R.layout.spinner_drop_down_row, new Utilities().getCountriesArabic()));
        } else {
            nationalitySpinner.setAdapter(new SpinnerCustomAdapterNationality(getActivity(), R.layout.spinner_drop_down_row, new Utilities().getCountries()));
        }
        nationalitySpinner.setSelection(pos);

        if (mBookingDetailsResponse.getCodAvailable().isCod()) {

            if (mBookingDetailsResponse.getCodAvailable().getCodCities() != null && !mBookingDetailsResponse.getCodAvailable().getCodCities().isEmpty()) {
                if (areaArrayList.isEmpty() || areaArrayList == null) {
                    for (String key : mBookingDetailsResponse.getCodAvailable().getCodCities().keySet()) {
                        areaIdsArrayList.add(key);
                        areaArrayList.add(mBookingDetailsResponse.getCodAvailable().getCodCities().get(key));
                    }
                }
              /*  final ArrayAdapter<String> areaAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, areaArrayList);
                areaSpinner.setAdapter(areaAdapter);
                areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        if (NetworkUtilities.isInternet(getActivity())) {

                            showDialog();
                            insertBookingDetailRequest.setCodCityId(Integer.parseInt(areaIdsArrayList.get(arg2)));

                            if (NetworkUtilities.isInternet(getActivity()))
                                hotelBookingPresenter.getAreaByCityId(Constant.HOTEL_BOOKING_CONFORMATION_ENDPOINT + Constant.GETAREA + "?cityId=" + areaIdsArrayList.get(arg2), getActivity());

                            else
                                Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), false, null);

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });*/

            }
        }

        expiryMonthSpinner.setAdapter(new SpinnerCustomAdapterMonth(getActivity(), R.layout.spinner_drop_down_row, new String[]{"01 (Jan)", "02 (Feb)", "03 (Mar)", "04 (Apr)", "05 (May)", "06 (Jun)", "07 (Jul)", "08 (Aug)", "09 (Sep)", "10 (Oct)", "11 (Nov)", "12 (Dec)"}, monthValueArray));
        expiryYearSpinner.setAdapter(new SpinnerCustomAdapterYear(getActivity(), R.layout.spinner_drop_down_row, yearArray));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy-EEE", new Locale(UserDTO.getUserDTO().getLanguage()));
        SimpleDateFormat usaDateFormat = new SimpleDateFormat("dd-MMM-yyyy-EEE", Locale.US);

        try {
            dateTo = simpleDateFormat.parse(simpleDateFormat.format(new SimpleDateFormat("dd-MMM-yyyy", Locale.US).parse(mBookingDetailsResponse.getBookingDetails().getCheckInDate())));
            calenderToDate.setTime(dateTo);
            dateFrom = simpleDateFormat.parse(simpleDateFormat.format(new SimpleDateFormat("dd-MMM-yyyy", Locale.US).parse(mBookingDetailsResponse.getBookingDetails().getCheckOutDate())));
            calenderFromDate.setTime(dateFrom);

            night = NetworkUtilities.daysBetween(calenderFromDate.getTime(), calenderToDate.getTime());
            advance_days = NetworkUtilities.daysBetween(calenderToDate.getTime(), Calendar.getInstance().getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ((TextView) rootView.findViewById(R.id.txt_night_count)).setText("0" + night + " N");
        ((TextView) rootView.findViewById(R.id.txt_checkin_date)).setText(Utilities.splitDate(simpleDateFormat.format(dateTo)).get(Constant.Date.DAY.name()) + ", " + Utilities.splitDate(simpleDateFormat.format(dateTo)).get(Constant.Date.MONTH.name()) + " " + Utilities.splitDate(usaDateFormat.format(dateTo)).get(Constant.Date.DATE.name()));
        ((TextView) rootView.findViewById(R.id.txt_checkout_date)).setText(Utilities.splitDate(simpleDateFormat.format(dateFrom)).get(Constant.Date.DAY.name()) + ", " + Utilities.splitDate(simpleDateFormat.format(dateFrom)).get(Constant.Date.MONTH.name()) + " " + Utilities.splitDate(usaDateFormat.format(dateFrom)).get(Constant.Date.DATE.name()));

        int adultCount = 0, childCount = 0;

        for (int i = 0; i < HotelListingRequest.getHotelListRequest().getOccupancy().size(); i++) {
            adultCount = adultCount + HotelListingRequest.getHotelListRequest().getOccupancy().get(i).getNoOfAdults();
            childCount = childCount + HotelListingRequest.getHotelListRequest().getOccupancy().get(i).getChildAges().size();

        }

        ((TextView) rootView.findViewById(R.id.text_address)).setText(mBookingDetailsResponse.getBookingDetails().getAddress1());
        ((TextView) rootView.findViewById(R.id.txt_room_type)).setText(mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getSupplierRoomTypeName());
        ((TextView) rootView.findViewById(R.id.txt_room)).setText(HotelListingRequest.getHotelListRequest().getOccupancy().size() + "" + "");

        if (childCount == 0) {
            if (adultCount == 1)
                travelerCountTextView.setText(adultCount + " " + context.getString(R.string.Adult));
            else
                travelerCountTextView.setText(adultCount + " " + context.getString(R.string.Adults));
        } else if (childCount == 1) {
            if (adultCount == 1)
                travelerCountTextView.setText(adultCount + " " + context.getString(R.string.Adult) + " " + childCount + " " + context.getString(R.string.Kid));
            else
                travelerCountTextView.setText(adultCount + " " + context.getString(R.string.Adults) + " " + childCount + " " + context.getString(R.string.Kid));
        } else if (childCount > 1) {
            if (adultCount == 1)
                travelerCountTextView.setText(adultCount + " " + context.getString(R.string.Adult) + " " + childCount + " " + context.getString(R.string.Kids));
            else
                travelerCountTextView.setText(adultCount + " " + context.getString(R.string.Adults) + " " + childCount + " " + context.getString(R.string.Kids));

        }

        ((TextView) rootView.findViewById(R.id.txt_booking_detail)).setText(travelerCountTextView.getText() + " , " + Utilities.splitDate(usaDateFormat.format(dateTo)).get(Constant.Date.DATE.name()) + " - " + Utilities.splitDate(simpleDateFormat.format(dateTo)).get(Constant.Date.MONTH.name()) + " - " + Utilities.splitDate(usaDateFormat.format(dateTo)).get(Constant.Date.YEAR.name()) + " " + getActivity().getString(R.string.to) + " " + Utilities.splitDate(usaDateFormat.format(dateFrom)).get(Constant.Date.DATE.name()) + " - " + Utilities.splitDate(simpleDateFormat.format(dateFrom)).get(Constant.Date.MONTH.name()) + " - " + Utilities.splitDate(usaDateFormat.format(dateFrom)).get(Constant.Date.YEAR.name()));

        ((TextView) rootView.findViewById(R.id.text_hotel_title)).setText(mBookingDetailsResponse.getBookingDetails().getTitle());
        Utilities.setStarRating((float) mBookingDetailsResponse.getBookingDetails().getStarRating(), context, rootView);

        Glide.with(context)
                .load(mBookingDetailsResponse.getBookingDetails().getHotelImage())
                .error(R.drawable.icn_default_image_loading)
                .placeholder(R.drawable.icn_default_image_loading)
                .centerCrop()
                .crossFade()
                .into(hotelImageView);


        insertBookingDetailRequest.setPaymentMode(paymentMode);

        travellerCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UserInfo userInfo = mBookingDetailsResponse.getUserInfo();
                if (isChecked) {
                    firstNameEditText.setText(userInfo.getFirstName());
                    lastNameEditText.setText(userInfo.getLastName());
                    emailEditText.setText(userInfo.getEmail());
                } else {

                    firstNameEditText.setText("");
                    lastNameEditText.setText("");
                    emailEditText.setText("");
                }
            }
        });

        if (AppController.getInstance().getCleverTapInstance() != null) {
            HashMap<String, Object> cleverTapEventsHashMap = new HashMap<>();
            cleverTapEventsHashMap.put("LOB", "Hotels");
            cleverTapEventsHashMap.put("Destination", mBookingDetailsResponse.getBookingDetails().getCity());
            cleverTapEventsHashMap.put("From Date", dateTo);
            cleverTapEventsHashMap.put("To Date", dateFrom);
            cleverTapEventsHashMap.put("Advance Purchase", advance_days);
            cleverTapEventsHashMap.put("Travel Nights", night);
            cleverTapEventsHashMap.put("Number of rooms", HotelListingRequest.getHotelListRequest().getOccupancy().size());
            cleverTapEventsHashMap.put("Number of passengers", adultCount + "|" + childCount);
            cleverTapEventsHashMap.put("Currency", UserDTO.getUserDTO().getCurrency());
            cleverTapEventsHashMap.put("Destination ID", mBookingDetailsResponse.getBookingDetails().getDestinationId());
            cleverTapEventsHashMap.put("Product Name", mBookingDetailsResponse.getBookingDetails().getTitle());
            cleverTapEventsHashMap.put("Product ID", hotelId);
            cleverTapEventsHashMap.put("Booking URL", mBookingDetailsResponse.getBookingDetails().getTempBookingId());
            cleverTapEventsHashMap.put("Price", "(" + mBookingDetailsResponse.getBillingCurrencyCode() + ") " + String.format(Locale.US, "%.2f", mBookingDetailsResponse.getSellingPriceInBillingCurrencyCode()));
            if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
                AppController.getInstance().getCleverTapInstance().event.push("AR Funnel City Hotel Booking Viewed", cleverTapEventsHashMap);
            } else {
                AppController.getInstance().getCleverTapInstance().event.push("Funnel City Hotel Booking Viewed", cleverTapEventsHashMap);
            }

        }
        mainScrollView.setSmoothScrollingEnabled(true);


        mainScrollView.setOnTouchListener(new View.OnTouchListener() {
            private ViewTreeObserver observer;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (observer == null) {
                    observer = mainScrollView.getViewTreeObserver();
                    observer.addOnScrollChangedListener(onScrollChangedListener);
                } else if (!observer.isAlive()) {
                    observer.removeOnScrollChangedListener(onScrollChangedListener);
                    observer = mainScrollView.getViewTreeObserver();
                    observer.addOnScrollChangedListener(onScrollChangedListener);
                }
                v.performClick();
                return false;
            }
        });
    }

    final ViewTreeObserver.OnScrollChangedListener onScrollChangedListener = new
            ViewTreeObserver.OnScrollChangedListener() {

                @Override
                public void onScrollChanged() {

                    firstScrollPos = hotelImageView.getHeight() + topInfoRelativeLayout.getHeight() + applyCouponCodeRelativeLayout.getHeight();
                    int secondScroll = firstScrollPos + (rootView.findViewById(R.id.relative_layout1)).getHeight() + (rootView.findViewById(R.id.inlude_guest_detail)).getHeight() - nationalitySpinner.getHeight() - mobileEditText.getHeight();
                    int paymentDetailLayoutHeight = (rootView.findViewById(R.id.inlude_payment_detail)).getHeight();
                    int scrollY = mainScrollView.getScrollY();

                    if (scrollY < 120) {
                        topLinearLayout.setVisibility(View.GONE);
                        topLinearLayout.clearAnimation();
                        guestDetailLinearLayout.clearAnimation();
                    }
                    if (scrollY < firstScrollPos) {
                        guestDetailLinearLayout.setVisibility(View.GONE);
                        guestDetailLinearLayout.clearAnimation();
                        guestDetailLinearLayout.setClickable(false);
                        if (topLinearLayoutVisible) {
                            topLinearLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up));
                            topLinearLayoutVisible = false;
                        }

                    }
                    if (scrollY >= firstScrollPos) {
                        guestDetailLinearLayout.setVisibility(View.GONE);
                        if (!topLinearLayoutVisible) {
                            topLinearLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
                            topLinearLayout.setVisibility(View.VISIBLE);
                            topLinearLayoutVisible = true;
                        }


                    }
                    if (scrollY >= secondScroll) {

                        topLinearLayout.setVisibility(View.VISIBLE);

                        if (!guestDetailLinearLayoutVisible) {
                            guestDetailLinearLayout.setClickable(true);
                            guestDetailLinearLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
                            if (getActivity() != null && isAdded()) {
                                if ((firstNameEditText.getText().toString()).equals("") && (lastNameEditText.getText().toString()).equals("")) {
                                    guestTextView.setTextColor(getResources().getColor(R.color.red_text));
                                    guestTextView.setText(getActivity().getResources().getString(R.string.travel_detail_msg));
                                } else {
                                    guestTextView.setTextColor(getResources().getColor(R.color.back_text));
                                    guestTextView.setText(firstNameEditText.getText() + " " + lastNameEditText.getText());
                                    ((TextView) rootView.findViewById(R.id.txt_guest_detail)).setText(emailEditText.getText() + "   " + mobileEditText.getText());

                                }
                            }
                            guestDetailLinearLayoutVisible = true;
                        }

                    }

                    if (scrollY <= paymentDetailLayoutHeight) {
                        if (guestDetailLinearLayoutVisible) {
                            guestDetailLinearLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up));
                            guestDetailLinearLayoutVisible = false;
                        }

                    }
                    if (!(firstNameEditText.getText().toString()).equals("") && !(lastNameEditText.getText().toString()).equals("") && !(mobileEditText.getText().toString()).equals("")) {
                        if (AppController.getInstance().getCleverTapInstance() != null) {
                            HashMap<String, Object> cleverTapHashMap = new HashMap<>();
                            cleverTapHashMap.put("LOB", "Hotels");

                            if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
                                AppController.getInstance().getCleverTapInstance().event.push("AR Hotel Booking Info Filled", cleverTapHashMap);
                            } else {
                                AppController.getInstance().getCleverTapInstance().event.push("EN Hotel Booking Info Filled", cleverTapHashMap);
                            }

                            HashMap<String, Object> profileUpdateHashMap = new HashMap<>();
                            profileUpdateHashMap.put("name", firstNameEditText.getText().toString() + " " + lastNameEditText.getText().toString());
                            profileUpdateHashMap.put("Email", emailEditText.getText().toString().trim());
                            profileUpdateHashMap.put("Phone no", mobileEditText.getText().toString().trim());
                            profileUpdateHashMap.put("countryTextView", nationalitySpinner.getSelectedItem().toString());

                            AppController.getInstance().getCleverTapInstance().profile.push(profileUpdateHashMap);

                        }
                    }
                }
            };


    public void setArea(String areaKey, String areaValue) {
        insertBookingDetailRequest.setCodAreaId(Integer.parseInt(areaKey));
       // ((TextView) rootView.findViewById(R.id.txt_city)).setText(areaValue);
    }

    private void setMasterCardClickListener()
    {
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.masterpass_terms));
        ClickableSpan termsOfUseClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                gtmAnalytics.sendEvent("Booking Screen", "Terms of use", "Proceed to check terms of use");
                Bundle bundle = new Bundle();
                bundle.putString("Terms of use", "Proceed to check terms of use");

                Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "Terms of use", "Proceed to check terms of use");

                hideSoftKeyboard(rootView);

                Fragment fragment = new TermsOfUseFragment();

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
                gtmAnalytics.sendEvent("Booking Screen", "Cancellation Policy", "Proceed to check cancellation policy");

                Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "Cancellation Policy", "Proceed to check cancellation policy");

                hideSoftKeyboard(cvvEditText);

                Utilities.commonErrorMessage(context, getString(R.string.Cancellation_Policy),Html.fromHtml( mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getCancelationPolicy()).toString(), false, getFragmentManager());

            }
        };

        if(UserDTO.getUserDTO().getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE)) {
            spannableString.setSpan(termsOfUseClickableSpan, 60, 78, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(cancellationPolicyClickableSpan, 96, 115, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        else
        {
            spannableString.setSpan(termsOfUseClickableSpan, 82, 93, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(cancellationPolicyClickableSpan, 94, 111, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        privacyPolicyTextView.setText(spannableString);
        privacyPolicyTextView.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void setVisaCheckoutClickListener()
    {
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.visa_terms));
        ClickableSpan termsOfUseClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                gtmAnalytics.sendEvent("Booking Screen", "Terms of use", "Proceed to check terms of use");
                Bundle bundle = new Bundle();
                bundle.putString("Terms of use", "Proceed to check terms of use");

                Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "Terms of use", "Proceed to check terms of use");

                hideSoftKeyboard(rootView);

                Fragment fragment = new TermsOfUseFragment();

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
                gtmAnalytics.sendEvent("Booking Screen", "Cancellation Policy", "Proceed to check cancellation policy");

                Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "Cancellation Policy", "Proceed to check cancellation policy");

                hideSoftKeyboard(cvvEditText);

                Utilities.commonErrorMessage(context, getString(R.string.Cancellation_Policy),Html.fromHtml(mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getCancelationPolicy()).toString(), false, getFragmentManager());

            }
        };

        if(UserDTO.getUserDTO().getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE)) {
            spannableString.setSpan(termsOfUseClickableSpan, 63, 81, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(cancellationPolicyClickableSpan, 99, 118, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        else
        {
            spannableString.setSpan(termsOfUseClickableSpan, 87, 98, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(cancellationPolicyClickableSpan, 99,116, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        privacyPolicyTextView.setText(spannableString);
        privacyPolicyTextView.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void setCreditCardClickListener()
    {
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.card_terms));
        ClickableSpan termsOfUseClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                gtmAnalytics.sendEvent("Booking Screen", "Terms of use", "Proceed to check terms of use");
                Bundle bundle = new Bundle();
                bundle.putString("Terms of use", "Proceed to check terms of use");

                Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "Terms of use", "Proceed to check terms of use");

                hideSoftKeyboard(rootView);

                Fragment fragment = new TermsOfUseFragment();

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
                gtmAnalytics.sendEvent("Booking Screen", "Privacy Policy", "Proceed to check terms of use");
                Bundle bundle = new Bundle();
                bundle.putString("Privacy Policy", "Proceed to check terms of use");

                Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "Privacy Policy", "Proceed to check terms of use");

                hideSoftKeyboard(rootView);

                Fragment fragment = new PrivacyPolicyFragment();

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
        if(UserDTO.getUserDTO().getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE)) {
            spannableString.setSpan(termsOfUseClickableSpan, 29, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(privacyPolicyClickableSpan, 37, 51, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        else
        {
            spannableString.setSpan(termsOfUseClickableSpan, 39, 50, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(privacyPolicyClickableSpan, 53, 67, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        privacyPolicyTextView.setText(spannableString);
        privacyPolicyTextView.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void setClickListeners() {

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

      /*  landmarkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                landmarkEditText.setText("");
            }
        });

        floorImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floorNumberEditText.setText("");
            }
        });

        buildingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildingEditText.setText("");
            }
        });
        apartmentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apartmentNumberEditText.setText("");
            }
        });

        streetNameImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                streetEditText.setText("");
            }
        });
*/
        guestDetailLinearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                firstScrollPos = hotelImageView.getHeight() + topInfoRelativeLayout.getHeight() + applyCouponCodeRelativeLayout.getHeight();
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

        creditCardRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setCreditCardClickListener();

                if (!creditCardExpandStatus) {
                    creditCardExpandStatus = true;
                    new Utilities().expand(cardIncludeRelativeLayout);
                } else {
                    creditCardExpandStatus = false;
                    new Utilities().collapse(cardIncludeRelativeLayout);
                }

               // new Utilities().collapse(visaCheckoutLinearLayout);
              //  new Utilities().collapse(masterCardLinearLayout);
                insertBookingDetailRequest.setPaymentMode(paymentMode);

                if (mBookingDetailsResponse.getBookingDetails().isPayAtHotel()) {
                    paymentDescTextView.setText(getResources().getString(R.string.no_card_charge));
                } else {
                    paymentDescTextView.setText(getResources().getString(R.string.card_charge));
                }

                payNowTextView.setText(getResources().getString(R.string.proceed_to_book));

              //  visaCheckoutExpandStatus = false;
              //  masterPassExpandStatus = false;
            }
        });

/*
        masterCardRadioButton.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {

                switch (target)
                {
                    case LEFT:
                        if(UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
                            final CustomDialog customDialog = new CustomDialog(getActivity(), getResources().getString(R.string.what_is_masterpass), getResources().getString(R.string.master_pass_long_text), "Ok");
                            customDialog.setDialogExitListener(new CustomDialog.DialogExitListener() {
                                @Override
                                public void dialogExitWithDone() {
                                    customDialog.dismiss();
                                }

                                @Override
                                public void dialogExitWithDismissOrCancel() {
                                    customDialog.dismiss();
                                }
                            });
                            customDialog.show();
                        }
                        break;

                    case RIGHT:
                        if(UserDTO.getUserDTO().getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE)) {
                            final CustomDialog customDialog = new CustomDialog(getActivity(), getResources().getString(R.string.what_is_masterpass), getResources().getString(R.string.master_pass_long_text), "Ok");
                            customDialog.setDialogExitListener(new CustomDialog.DialogExitListener() {
                                @Override
                                public void dialogExitWithDone() {
                                    customDialog.dismiss();
                                }

                                @Override
                                public void dialogExitWithDismissOrCancel() {
                                    customDialog.dismiss();
                                }
                            });
                            customDialog.show();
                        }
                        break;
                }

            }
        });
*/

/*
        visaCheckOutRadioButton.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target)
                {

                    case LEFT:
                        if(UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE))
                        {
                            final CustomDialog customDialog = new CustomDialog(getActivity(), getResources().getString(R.string.what_is_visa_checkout), getResources().getString(R.string.visa_checkout_long_text), "Ok");
                            customDialog.setDialogExitListener(new CustomDialog.DialogExitListener() {
                                @Override
                                public void dialogExitWithDone() {
                                    customDialog.dismiss();
                                }

                                @Override
                                public void dialogExitWithDismissOrCancel() {
                                    customDialog.dismiss();
                                }
                            });
                            customDialog.show();
                        }
                        break;

                    case RIGHT:
                        if(UserDTO.getUserDTO().getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE)) {
                            final CustomDialog customDialog = new CustomDialog(getActivity(), getResources().getString(R.string.what_is_visa_checkout), getResources().getString(R.string.visa_checkout_long_text), "Ok");
                            customDialog.setDialogExitListener(new CustomDialog.DialogExitListener() {
                                @Override
                                public void dialogExitWithDone() {
                                    customDialog.dismiss();
                                }

                                @Override
                                public void dialogExitWithDismissOrCancel() {
                                    customDialog.dismiss();
                                }
                            });
                            customDialog.show();
                        }
                        break;
                }
            }
        });
*/

        visaCheckOutRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertBookingDetailRequest.setPaymentMode("VisaCheckOut");
                payNowTextView.setText(getResources().getString(R.string.proceed_with_visa_checkout));

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


        masterCardRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertBookingDetailRequest.setPaymentMode("MasterPass");
                payNowTextView.setText(getResources().getString(R.string.proceed_with_master_pass));

                setMasterCardClickListener();

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

/*
        readMoreMasterPassTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CustomDialog customDialog = new CustomDialog(getActivity(), getResources().getString(R.string.what_is_masterpass), getResources().getString(R.string.master_pass_long_text), "Ok");
                customDialog.setDialogExitListener(new CustomDialog.DialogExitListener() {
                    @Override
                    public void dialogExitWithDone() {
                        customDialog.dismiss();
                    }

                    @Override
                    public void dialogExitWithDismissOrCancel() {
                        customDialog.dismiss();
                    }
                });
                customDialog.show();
            }
        });

        readMoreVisaCheckoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomDialog customDialog = new CustomDialog(getActivity(), getResources().getString(R.string.what_is_visa_checkout), getResources().getString(R.string.visa_checkout_long_text), "Ok");
                customDialog.setDialogExitListener(new CustomDialog.DialogExitListener() {
                    @Override
                    public void dialogExitWithDone() {
                        customDialog.dismiss();
                    }

                    @Override
                    public void dialogExitWithDismissOrCancel() {
                        customDialog.dismiss();
                    }
                });
                customDialog.show();
            }
        });*/

        (rootView.findViewById(R.id.txt_inclusion)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.commonErrorMessage(context, getString(R.string.inclusion), mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getMealPlans().replace(",", ", "), false, getFragmentManager());
            }
        });

        (rootView.findViewById(R.id.backImageView)).setOnClickListener(new View.OnClickListener()

                                                                       {
                                                                           @Override
                                                                           public void onClick(View v) {
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
                                                                          (new Utilities()).contactUsDialog(getActivity());
                                                                      }
                                                                  }
        );

        (rootView.findViewById(R.id.img_info)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.commonErrorMessage(context, getString(R.string.payment_info_title), getString(R.string.payment_info), false, getFragmentManager());
            }
        });

        (rootView.findViewById(R.id.txt_fair_brakeup)).setOnClickListener(new View.OnClickListener()

                                                                          {
                                                                              @Override
                                                                              public void onClick(View v) {
                                                                                  gtmAnalytics.sendEvent("Booking Screen", "Fare Breakup", "Proceed to check fare breakup");
                                                                                  Bundle bundle = new Bundle();
                                                                                  bundle.putString("Fare Breakup", "Proceed to check fare breakup");

                                                                                  Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "Fare Breakup", "Proceed to check fare breakup");
                                                                                  hideSoftKeyboard(cvvEditText);

                                                                                  Fragment fragment = new FareBreakupFragment();

                                                                                  if (applyCouponCodeButton.getTag().equals("2")) {
                                                                                      bundle.putString("discount", discount);
                                                                                      bundle.putString("grandTotal", String.format(Locale.US, "%.2f", mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getTotalSellingPrice()));
                                                                                  } else {
                                                                                      bundle.putString("discount", "0.00");
                                                                                      bundle.putString("grandTotal", String.format(Locale.US, "%.2f", mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getTotalSellingPrice()));
                                                                                  }
                                                                                  bundle.putParcelable("FairBrakeUp", mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0));
                                                                                  fragment.setArguments(bundle);

                                                                                  fragmentTransition( fragment);

                                                                              }
                                                                          }

        );




        rootView.findViewById(R.id.txt_policies).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getTariffNotes()==null ||
                        mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getTariffNotes().equals("")){


                    Utilities.commonErrorMessage(getActivity(),getResources().getString(R.string.booking_policy), getResources().getString(R.string.booking_policy_null),false,getFragmentManager());

                }

                else{

                    Utilities.commonErrorMessage(getActivity(),getResources().getString(R.string.booking_policy), mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getTariffNotes(),false,getFragmentManager());

                }

            }
        });

        (rootView.findViewById(R.id.txt_cancelation_policy)).setOnClickListener(new View.OnClickListener()

                                                                                {
                                                                                    @Override
                                                                                    public void onClick(View v) {
                                                                                        gtmAnalytics.sendEvent("Booking Screen", "Cancellation Policy", "Proceed to check cancellation policy");

                                                                                        Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "Cancellation Policy", "Proceed to check cancellation policy");

                                                                                        hideSoftKeyboard(cvvEditText);

                                                                                        Utilities.commonErrorMessage(context, getString(R.string.Cancellation_Policy),Html.fromHtml(mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getCancelationPolicy()).toString(), false, getFragmentManager());

                                                                                    }
                                                                                }

        );

        specialRequestTextView.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          gtmAnalytics.sendEvent("Booking Screen", "Special Request", "Proceed to check special request");
                                                          Bundle bundle = new Bundle();
                                                          bundle.putString("Special Request", "Proceed to check special request");

                                                          Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "Special Request", "Proceed to check special request");
                                                          hideSoftKeyboard(rootView);

                                                          Fragment fragment = new SpecialRequestFragment();
                                                          fragment.setTargetFragment(BookingDetailsFragment.this, 1);
                                                          bundle.putParcelable("BookingDetailResponse", mBookingDetailsResponse);
                                                          bundle.putParcelableArrayList("selecteddata", specialRequestsDtoArrayList);
                                                          fragment.setArguments(bundle);

                                                          fragmentTransition(fragment);
                                                      }
                                                  }
        );


        applyCouponCodeButton.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {

                                                         if (v.getTag().equals("1")) {
                                                             couponCodeEditText.setBackgroundColor(getResources().getColor(R.color.apply_coupon_bg));
                                                             applyCoupon();
                                                         } else if (v.getTag().equals("2")) {
                                                             v.setTag("1");
                                                             removeCoupon();
                                                         }
                                                     }
                                                 }

        );
        couponCodeStatusImageView.setOnClickListener(new View.OnClickListener()

                                                     {
                                                         @Override
                                                         public void onClick(View v) {
                                                             gtmAnalytics.sendEvent("Booking Screen", "Coupon Code-" + promoCode, "Removed Coupon Code");

                                                             Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "Coupon Code-" + promoCode, "Removed Coupon Code");

                                                             if (applyCouponCodeButton.getTag().equals("3")) {
                                                                 applyCouponCodeButton.setTag("1");
                                                                 applyCouponCodeButton.setText(getResources().getString(R.string.apply_coupon));
                                                                 couponCodeEditText.setText("");
                                                                 couponCodeEditText.setFocusableInTouchMode(true);
                                                                 couponCodeEditText.setFocusable(true);
                                                                 couponCodeStatusImageView.setVisibility(View.GONE);
                                                             }
                                                         }
                                                     }

        );

        payNowTextView.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  hideSoftKeyboard(cvvEditText);
                                                  showDialog();
                                                  if (validateForm()) {

                                                      if (insertBookingDetailRequest.getPaymentMode().equals("Card") ||
                                                              insertBookingDetailRequest.getPaymentMode().equals("VisaCheckOut") ||
                                                              insertBookingDetailRequest.getPaymentMode().equals("MasterPass") ||
                                                              insertBookingDetailRequest.getPaymentMode().equals("PayAtHotel")) {
                                                          //isCOD = false;
                                                          if (mBookingDetailsResponse.getBookingDetails().isPayAtHotel()) {
                                                              gtmAnalytics.sendEvent("Booking Screen", "Pay at hotel", "doing payment by pay at hotel mode");

                                                              Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "Pay at hotel", "doing payment by pay at hotel mode");

                                                          } else {
                                                              gtmAnalytics.sendEvent("Booking Screen", "Pay now", "doing payment by pay now mode");

                                                              Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "Pay now", "doing payment by pay now mode");

                                                          }
                                                          insertBookingDetailRequest.setCodCityId(0);
                                                          insertBookingDetailRequest.setCodAreaId(0);
                                                          insertBookingDetailRequest.setGuestAddress(",,,,");
                                                          insertBookingDetailRequest.setAddressLine1(",,,,");
                                                          insertBookingDetailRequest.setCardNumber(cardNumberEditText.getText().toString().replaceAll(" ","").trim());
                                                          insertBookingDetailRequest.setCardHolderName(cardHolderEditText.getText().toString().trim());
                                                          insertBookingDetailRequest.setCardCvvNumber(cvvEditText.getText().toString());
                                                          insertBookingDetailRequest.setExpiryTime(expiryYearSpinner.getSelectedItem().toString().trim() + "-" + monthValueArray[expiryMonthSpinner.getSelectedItemPosition()] + "-01T00:00:00");

                                                          String creditCardNumber = cardNumberEditText.getText().toString().trim();
                                                          Pattern visaPattern = Pattern.compile(Constant.regVisa);
                                                          Pattern masterPattern = Pattern.compile(Constant.regMaster);
                                                          Pattern amExPattern = Pattern.compile(Constant.regAmEx);
                                                          Matcher visaMatcher = visaPattern.matcher(creditCardNumber);
                                                          Matcher masterMatcher = masterPattern.matcher(creditCardNumber);
                                                          Matcher amExMatcher = amExPattern.matcher(creditCardNumber);

                                                          if (visaMatcher.matches())
                                                              insertBookingDetailRequest.setCardType(1);
                                                          else if (masterMatcher.matches())
                                                              insertBookingDetailRequest.setCardType(2);
                                                          else if (amExMatcher.matches())
                                                              insertBookingDetailRequest.setCardType(3);

                                                      } /*else { //COD
                                                          gtmAnalytics.sendEvent("Booking Screen", "Cash on Delivery", "doing payment by COD mode");

                                                          Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "Cash on Delivery", "doing payment by COD mode");

                                                          isCOD = true;
                                                          insertBookingDetailRequest.setGuestAddress(streetEditText.getText().toString() + "," + buildingEditText.getText().toString() + "," + apartmentNumberEditText.getText().toString() + "," + floorNumberEditText.getText().toString() + "," + landmarkEditText.getText().toString());
                                                          insertBookingDetailRequest.setAddressLine1(streetEditText.getText().toString() + "," + buildingEditText.getText().toString() + "," + apartmentNumberEditText.getText().toString() + "," + floorNumberEditText.getText().toString() + "," + landmarkEditText.getText().toString());
                                                          insertBookingDetailRequest.setCardNumber("");
                                                          insertBookingDetailRequest.setCardHolderName("");
                                                          insertBookingDetailRequest.setCardCvvNumber("");
                                                          insertBookingDetailRequest.setExpiryTime("");
                                                      }*/

                                                      insertBookingDetailRequest.setGuestSalutation(salutationSpinner.getSelectedItem().toString());
                                                      insertBookingDetailRequest.setGuestMiddleName("");
                                                      insertBookingDetailRequest.setGuestFirstName(firstNameEditText.getText().toString().trim());
                                                      insertBookingDetailRequest.setGuestCountryISDCode(countryCodesArray[countryCodeSpinner.getSelectedItemPosition()]);
                                                      insertBookingDetailRequest.setGuestLastName(lastNameEditText.getText().toString().trim());
                                                      insertBookingDetailRequest.setGuestMobileNumber(mobileEditText.getText().toString().trim());
                                                      insertBookingDetailRequest.setGuestEmail(emailEditText.getText().toString().trim());
                                                      insertBookingDetailRequest.setPassportCountry(nationalityISDArray[nationalitySpinner.getSelectedItemPosition()]);

                                                      ArrayList<RoomDetail> roomDetailArrayList = new ArrayList<>();
                                                      RoomDetail roomDetail = new RoomDetail();
                                                      roomDetail.setTravellerSalutationId(salutationValuesArray[salutationSpinner.getSelectedItemPosition()]);
                                                      roomDetail.setTravellerSalutation(salutationSpinner.getSelectedItem().toString().trim());
                                                      roomDetail.setTravellerFirstName(firstNameEditText.getText().toString().trim());
                                                      roomDetail.setTravellerMiddleName("");
                                                      roomDetail.setTravellerLastName(lastNameEditText.getText().toString().trim());
                                                      if (!specialRequestsDtoArrayList.isEmpty()) {
                                                          ArrayList<Long> idsArrayList = new ArrayList<>();
                                                          for (int i = 0; i < specialRequestsDtoArrayList.size(); i++) {
                                                              idsArrayList.add(specialRequestsDtoArrayList.get(i).getId());
                                                          }
                                                          roomDetail.setSpecialRequests(idsArrayList);
                                                      }
                                                      roomDetailArrayList.add(roomDetail);
                                                      if (applyCouponCodeButton.getTag().equals("2")) {
                                                          insertBookingDetailRequest.setPromoCode(promoCode);
                                                      } else {
                                                          insertBookingDetailRequest.setPromoCode(null);
                                                      }

                                                      Log.i("Temp : ", mBookingDetailsResponse.getBookingDetails().getTempBookingId());
                                                      insertBookingDetailRequest.setRoomDetails(roomDetailArrayList);
                                                      insertBookingDetailRequest.setCustomerIPAddress(mBookingDetailsResponse.getBookingDetails().getBookingIpAddress());
                                                      insertBookingDetailRequest.setCustomerUserAgent(NetworkUtilities.getUserAgent(context));
                                                      insertBookingDetailRequest.setCountryCode(nationalityISDArray[nationalitySpinner.getSelectedItemPosition()]);
                                                      insertBookingDetailRequest.setNationality(nationalityISDArray[nationalitySpinner.getSelectedItemPosition()]);
                                                      insertBookingDetailRequest.setTempBookingId(mBookingDetailsResponse.getBookingDetails().getTempBookingId());
                                                      insertBookingDetailRequest.setBillingCurrencyType(mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getSellingCurrency());
                                                      insertBookingDetailRequest.setBillingPrice(mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getTotalSellingPrice());
                                                      insertBookingDetailRequest.setLanguageCode(mBookingDetailsResponse.getBookingDetails().getLanguageCode());
                                                      insertBookingDetailRequest.setUserIpAddress(mBookingDetailsResponse.getBookingDetails().getBookingIpAddress());
                                                      insertBookingDetailRequest.setUserAgent(NetworkUtilities.getUserAgent(context));
                                                      if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE))
                                                      insertBookingDetailRequest.setAffiliateId(Integer.parseInt(Constant.AffilateId_ar));
                                                      else
                                                          insertBookingDetailRequest.setAffiliateId(Integer.parseInt(Constant.AffilateId_en));


                                                      //constants for booking
                                                      insertBookingDetailRequest.setCountryOfResidence("us");
                                                      insertBookingDetailRequest.setState("CA");
                                                      insertBookingDetailRequest.setCity("Mountain View");
                                                      insertBookingDetailRequest.setCountry("us");
                                                      insertBookingDetailRequest.setPostalCode("94043");
                                                      insertBookingDetailRequest.setBookingID(0);
                                                      insertBookingDetailRequest.setBookingPropertyID(0);
                                                      insertBookingDetailRequest.setOwnerType(1);
                                                      insertBookingDetailRequest.setTransactionStatusId(0);
                                                      insertBookingDetailRequest.setPromotionFor(0);
                                                      insertBookingDetailRequest.setIsIrsAgent(false);
                                                      insertBookingDetailRequest.setIsMobile(true);
                                                      insertBookingDetailRequest.setRequestId("");
                                                      insertBookingDetailRequest.setIsSubscribUser(false);

                                                      new PaymentAsyncTask(new GsonBuilder().serializeNulls().create().toJson(insertBookingDetailRequest)).execute(new String[]{Constant.HOTEL_BOOKING_CONFORMATION_ENDPOINT + Constant.INSERTBOOKINGDETAIL});

                                                  } else {

                                                      dismissDialog();
                                                      mainScrollView.smoothScrollTo(0, firstScrollPos);
                                                  }
                                              }
                                          }
        );

    }

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
                Map<String, String> headerMap = NetworkUtilities.getSignatureHeader(Constant.INSERTBOOKINGDETAIL);
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

                paymentResponseStringBuilder.append(String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\">\n", "ApplicationToken", Constant.ApplicationToken_new));

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

                setInsertBookingDetailsResponse(paymentResponseStringBuilder);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void applyCoupon() {
        if (!TextUtils.equals(couponCodeEditText.getText().toString(), "")) {
            if (NetworkUtilities.isInternet(getActivity())) {
                showDialog();

                ApplyCouponCodeRequest applyCouponCodeRequest = new ApplyCouponCodeRequest();
                applyCouponCodeRequest.setTempBookingId(mBookingDetailsResponse.getBookingDetails().getTempBookingId());
                applyCouponCodeRequest.setPromoCode(couponCodeEditText.getText().toString());
                applyCouponCodeRequest.setCurrencyCode(UserDTO.getUserDTO().getCurrency());
                applyCouponCodeRequest.setEntityType(1);
                applyCouponCodeRequest.setExtranetActivity(false);
                applyCouponCodeRequest.setPromotionFor(2);
                applyCouponCodeRequest.setMobile(true);
                applyCouponCodeRequest.setEmail(emailEditText.getText().toString());
                applyCouponCodeRequest.setRequestId("");
                applyCouponCodeRequest.setSubscribUser(false);
                applyCouponCodeRequest.setLanguageCode(mBookingDetailsResponse.getBookingDetails().getLanguageCode());
                applyCouponCodeRequest.setTrackingCookie(null);
                applyCouponCodeRequest.setUserAgent(NetworkUtilities.getUserAgent(context));
                applyCouponCodeRequest.setUserIpAddress(mBookingDetailsResponse.getBookingDetails().getBookingIpAddress());
                if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE))
                applyCouponCodeRequest.setAffiliateId(Integer.parseInt(Constant.AffilateId_ar));
                else
                    applyCouponCodeRequest.setAffiliateId(Integer.parseInt(Constant.AffilateId_en));


                if (NetworkUtilities.isInternet(getActivity()))
                    hotelBookingPresenter.applyCouponCode(Constant.HOTEL_BOOKING_CONFORMATION_ENDPOINT + Constant.APPLYCOUPNCODE,
                            new GsonBuilder().serializeNulls().create().toJson(applyCouponCodeRequest), getActivity());

                else
                    Utilities.commonErrorMessage(getActivity(), getString(R.string.Network_not_avilable), getString(R.string.please_check_your_internet_connection), false, null);

            }
        } else {
            Utilities.commonErrorMessage(context, getString(R.string.app_name), getString(R.string.coupon_code_validation), false, getFragmentManager());
        }
    }

    @SuppressLint("SetTextI18n")
    private void removeCoupon() {
        gtmAnalytics.sendEvent("Booking Screen", "Coupon Code-" + promoCode, "Removed Coupon Code");

        Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "Coupon Code-" + promoCode, "Removed Coupon Code");

        promoCode = "";
        applyCouponCodeButton.setText(getResources().getString(R.string.Apply));
        couponCodeEditText.setText("");
        couponCodeEditText.setFocusableInTouchMode(true);
        couponCodeEditText.setFocusable(true);
        couponCodeStatusImageView.setVisibility(View.GONE);
        amountBeforeCouponCodeTextView.setVisibility(View.GONE);
        hotelPaymentTextView.setText(mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getSellingCurrency() + " " + String.format(Locale.US, "%.2f", mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getTotalSellingPrice()));
        billingAmountTextView.setText(mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getSellingCurrency() + " " + String.format(Locale.US, "%.2f", mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getTotalSellingPrice()));
        currencyDescTextView.setText(String.format(getResources().getString(R.string.billing_currency_text1_new), mBookingDetailsResponse.getBillingCurrencyCode(), String.format(Locale.US, "%.2f", mBookingDetailsResponse.getSellingPriceInBillingCurrencyCode())));

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setApplyCoupon(ApplyCouponCodeResponse applyCouponCodeResponse) {

        if (applyCouponCodeResponse.getError() == null && applyCouponCodeResponse.isPromocodeApplied()) {
            applyCouponCodeButton.setTag("2");
            promoCode = couponCodeEditText.getText().toString();
            gtmAnalytics.sendEvent("Booking Screen", "Coupon Code-" + promoCode, "Applied Coupon Code");
            couponCodeEditText.setBackgroundColor(getResources().getColor(R.color.transparent));
            applyCouponCodeButton.setText(getResources().getString(R.string.remove_coupon));
            couponCodeEditText.setGravity(Gravity.CENTER);

            if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE))
                couponCodeEditText.setTextDirection(View.TEXT_DIRECTION_RTL);
            else
                couponCodeEditText.setTextDirection(View.TEXT_DIRECTION_LTR);

            couponCodeEditText.setText(couponCodeEditText.getText().toString() + " " + getResources().getString(R.string.applied));
            couponCodeEditText.setFocusable(false);
            couponCodeEditText.setClickable(true);
            couponCodeStatusImageView.setVisibility(View.VISIBLE);
            amountBeforeCouponCodeTextView.setVisibility(View.VISIBLE);

            discount = String.format(Locale.US, "%.2f", applyCouponCodeResponse.getPromoCodeAmountInRequestedCurrency());

            amountBeforeCouponCodeTextView.setText(mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getSellingCurrency() + " " + String.format(Locale.US, "%.2f", applyCouponCodeResponse.getBillingPriceInRequestedCurrency()));
            amountBeforeCouponCodeTextView.setPaintFlags(amountBeforeCouponCodeTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            amountBeforeCouponCodeTextView.setTag(mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getSellingCurrency() + " " + String.format(Locale.US, "%.2f", applyCouponCodeResponse.getBillingPriceInRequestedCurrency()));

            hotelPaymentTextView.setText(mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getSellingCurrency() + " " + String.format(Locale.US, "%.2f", applyCouponCodeResponse.getBillingPriceApplyingPromoCodeInRequestedCurrency()));
            hotelPaymentTextView.setTag(String.format(Locale.US, "%.2f", applyCouponCodeResponse.getBillingPriceApplyingPromoCodeInRequestedCurrency()));

            billingAmountTextView.setText(mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getSellingCurrency() + " " + String.format(Locale.US, "%.2f", applyCouponCodeResponse.getBillingPriceApplyingPromoCodeInRequestedCurrency()));
            currencyDescTextView.setText(String.format(getResources().getString(R.string.billing_currency_text1_new), " " + mBookingDetailsResponse.getBillingCurrencyCode(), String.format(Locale.US, "%.2f", applyCouponCodeResponse.getBillingPriceApplyingPromoCode())));
            currencyDescTextView.setTag(String.format(Locale.US, "%.2f", applyCouponCodeResponse.getBillingPriceApplyingPromoCode()));

            couponCodeStatusImageView.setImageResource(R.drawable.ic_tick);

        } else {
            promoCode = "";
            Utilities.commonErrorMessage(context, getString(R.string.app_name), applyCouponCodeResponse.getError().getErrorMessage(), false, getFragmentManager());
        }

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
            private  char space=' ';
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

    private void hideSoftKeyboard(View view) {
        if (softKeyboardStateWatcher.isSoftKeyboardOpened()) {
            NetworkUtilities.hideSoftKeyboard(view, getActivity());
        }
    }

    private void addFragment(Fragment fragment) {
        try {
            FragmentTransaction fragmentTransition = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransition.add(R.id.container_body, fragment);
            fragmentTransition.addToBackStack(null);
            fragmentTransition.commit();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void fragmentTransition(Fragment fragment) {
        try {
            FragmentTransaction fragmentTransition = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransition.replace(R.id.container_body, fragment);
            fragmentTransition.addToBackStack(null);
            fragmentTransition.commit();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        tag = (String) applyCouponCodeButton.getTag();
        amount = (String) hotelPaymentTextView.getTag();
        off = (String) amountBeforeCouponCodeTextView.getTag();
        amountUsed = (String) currencyDescTextView.getTag();
    }

    @Override
    public void onResume() {
        super.onResume();

       /* if (codRadioButton.isChecked()) {
            codRadioButton.performClick();
            if (mBookingDetailsResponse.getBookingDetails().getCodAvailable() != null)
                if (mBookingDetailsResponse.getCodAvailable().isCod()) {
                    codLinearLayout.setVisibility(View.VISIBLE);
                    cardLinearLayout.setVisibility(View.GONE);
                } else {
                    creditCardRadioButton.performClick();
                    paymentDescTextView.setText(getResources().getString(R.string.Cod_not_avilable));
                    insertBookingDetailRequest.setPaymentMode(1);
                    codLinearLayout.setVisibility(View.GONE);
                }
        } else */

        /*if (creditCardRadioButton.isChecked()) {
            codLinearLayout.setVisibility(View.GONE);
            cardLinearLayout.setVisibility(View.VISIBLE);
        }*/

        if (tag != null) {
            if (tag.equals("2")) {
                couponCodeEditText.setBackgroundColor(getResources().getColor(R.color.transparent));
                applyCouponCodeButton.setTag("2");
                hotelPaymentTextView.setTag(amount);
                amountBeforeCouponCodeTextView.setTag(off);
                currencyDescTextView.setTag(amountUsed);
                applyCouponCodeButton.setText(getResources().getString(R.string.remove_coupon));
                couponCodeEditText.setGravity(Gravity.CENTER);
                couponCodeEditText.setFocusable(false);
                couponCodeEditText.setClickable(true);
                couponCodeStatusImageView.setVisibility(View.VISIBLE);
                amountBeforeCouponCodeTextView.setVisibility(View.VISIBLE);
                amountBeforeCouponCodeTextView.setText(off);
                amountBeforeCouponCodeTextView.setPaintFlags(amountBeforeCouponCodeTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                hotelPaymentTextView.setText(mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getSellingCurrency() + " " + amount);
                billingAmountTextView.setText(mBookingDetailsResponse.getBookingDetails().getRates().getSupplierRates().get(0).getRoomRates().get(0).getSellingCurrency() + " " + amount);
                currencyDescTextView.setText(String.format(getResources().getString(R.string.billing_currency_text1_new), " " + mBookingDetailsResponse.getBillingCurrencyCode(), amountUsed));
                couponCodeStatusImageView.setImageResource(R.drawable.ic_tick);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                specialRequestsDtoArrayList = data.getParcelableArrayListExtra("listdata");
                specialRequestListAdapter.notifyDataSetChanged();
                if (!specialRequestsDtoArrayList.isEmpty()) {
                    specialRequestTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ico_checkbox_selected, 0, 0, 0);
                } else {
                    specialRequestTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ico_checkbox, 0, 0, 0);
                }
            }
        }

        /*if (requestCode == 5) {
            if (bookingFailCustomDialog == null)
                bookingFailCustomDialog = new BookingFailCustomDialog(getActivity());
            bookingFailCustomDialog.setDialogExitListener(new BookingFailCustomDialog.DialogExitListener() {
                @Override
                public void dialogExitWithDone() {
                    bookingFailCustomDialog.dismiss();
                }

                @Override
                public void dialogExitWithDismissOrCancel() {
                    getActivity().onBackPressed();
                }
            });

            bookingFailCustomDialog.show();
        }*/
    }

    @Override
    public void setInsertBookingDetailsResponse(StringBuilder stringBuilder) {
        Fragment fragment = new BookingPaymentGatewayFragment();
        fragment.setTargetFragment(BookingDetailsFragment.this, 5);
        Bundle bundle = new Bundle();
        bundle.putString("html", stringBuilder.toString());
        fragment.setArguments(bundle);

        fragmentTransition( fragment);
    }


    @Override
    public void setBookedHotelDetailsResponse(HotelBookConfirmationResponse hotelBookConfirmationResponse) {
        this.hotelBookConfirmationResponse = hotelBookConfirmationResponse;
        if (bookingStatus()) {

            Fragment fragment = new HotelBookingConfirmationFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("hotelBookConfirmationResponse", hotelBookConfirmationResponse);
            bundle.putParcelable("RoomRate", hotelBookConfirmationResponse);
            fragment.setArguments(bundle);

            fragmentTransition(fragment);

        } else {

            Fragment fragment = new HotelBookingConfirmationFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("hotelBookConfirmationResponse", null);
            //bundle.putParcelable("RoomRate", hotelBookConfirmationResponse);
            fragment.setArguments(bundle);

            fragmentTransition(fragment);

            /*if (bookingFailCustomDialog == null)
                bookingFailCustomDialog = new BookingFailCustomDialog(getActivity());


            bookingFailCustomDialog.setDialogExitListener(new BookingFailCustomDialog.DialogExitListener() {
                @Override
                public void dialogExitWithDone() {
                    bookingFailCustomDialog.dismiss();
                }

                @Override
                public void dialogExitWithDismissOrCancel() {
                    getActivity().onBackPressed();
                }
            });
            bookingFailCustomDialog.show();*/
        }
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
        if (insertBookingDetailRequest.getPaymentMode() == ("Card")) {//Credit Card
            cardNumberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        Validation.CreditCardValidation(cardNumberEditText.getText().toString().replaceAll(" ",""), cardNumberEditText, context);
                }
            });
            cardHolderEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        Validation.bookingNameValidation(cardHolderEditText.getText().toString(), cardHolderEditText, context, getResources().getString(R.string.card_holder_name));
                }
            });
            cvvEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        Validation.cvvValidation(cvvEditText.getText().toString(), cvvEditText, context);
                }
            });
        }

    }

    private boolean bookingStatus() {

        if (hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().isPayAtHotel()) {
            if (hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelBookingStatus() == BookingStatusTypes.Confirmed.getBookingStatusVal())
                return true;
        } else {
            if (hotelBookConfirmationResponse.isCod()) {
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

       /* if (insertBookingDetailRequest.getPaymentMode() == 2) {//COD
            if (!Validation.bookingCodValidation(streetEditText.getText().toString(), streetEditText, context, "")) {
                streetEditText.requestFocus();
                return false;
            }

            if (!Validation.bookingCodValidation(buildingEditText.getText().toString(), buildingEditText, context, getResources().getString(R.string.building_name))) {
                buildingEditText.requestFocus();
                return false;
            }
            if (!Validation.bookingCodValidation(floorNumberEditText.getText().toString(), floorNumberEditText, context, getResources().getString(R.string.floor_number))) {
                buildingEditText.requestFocus();
                return false;
            }
            if (!Validation.bookingCodValidation(apartmentNumberEditText.getText().toString(), apartmentNumberEditText, context, getResources().getString(R.string.apartment_number))) {
                buildingEditText.requestFocus();
                return false;
            }
        }*/
        if (insertBookingDetailRequest.getPaymentMode().equals("Card")) {//Credit Card
            /*if (cardLinearLayout.getVisibility() == View.GONE)
                creditCardRadioButton.setChecked(true);*/
            // codRadioButton.setChecked(false);
            //  cardLinearLayout.setVisibility(View.VISIBLE);
            if (!Validation.CreditCardValidation(cardNumberEditText.getText().toString().replaceAll(" ",""), cardNumberEditText, context)) {
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

        return true;
    }

    @Override
    public void itemPosition(int index) {
        specialRequestsDtoArrayList.remove(index);
        setUpSpecialRequestAdapter();
    }

    public void setUpSpecialRequestAdapter() {
        specialRequestListAdapter = new SpecialRequestListAdapter(specialRequestsDtoArrayList, getActivity(), BookingDetailsFragment.this);
        nonScrollListView.setAdapter(specialRequestListAdapter);
        specialRequestListAdapter.notifyDataSetChanged();
        if (!specialRequestsDtoArrayList.isEmpty()) {
            if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE))
                specialRequestTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_checkbox_selected, 0);
            else
                specialRequestTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ico_checkbox_selected, 0, 0, 0);
        } else {
            if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE))
                specialRequestTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_checkbox, 0);
            else
                specialRequestTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ico_checkbox, 0, 0, 0);
        }
    }
}

