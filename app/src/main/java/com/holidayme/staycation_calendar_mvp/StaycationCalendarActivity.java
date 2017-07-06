package com.holidayme.staycation_calendar_mvp;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PointF;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.holidayme.AppInterface.BackPressInterFace;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.Utilities;
import com.holidayme.adapter.CountryCodeCustomAdapter;
import com.holidayme.common.CustomProgressDialog;
import com.holidayme.common.Log;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.data.AllocationDTO;
import com.holidayme.data.CustomFields;
import com.holidayme.data.FreshDeskRequest;
import com.holidayme.data.UserDTO;
import com.holidayme.gtm.GTMAnalytics;
import com.holidayme.response.CurrentLocationDetail;
import com.holidayme.staycationcustomcalender.DayDetails;
import com.holidayme.staycationcustomcalender.DayDetailsAdapter;
import com.holidayme.staycationcustomcalender.MonthsAdapter;
import com.holidayme.staycationcustomcalender.MonthsData;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

public class StaycationCalendarActivity extends AppCompatActivity implements View.OnClickListener, IStaycationCalendarView {

    private RecyclerView dayDetailsRecyclerView, monthRecyclerView;
    private MonthsAdapter monthsAdapter;
    private DayDetailsAdapter dayDetailsAdapter;
    private ImageView previousMonthButton, nextMonthButton;
    private ArrayList<MonthsData> monthsArrayList;
    private LinearLayoutManager monthsLinearLayoutManager;
    private CustomLayoutManager dayDetailsLinearLayoutManager;
    private ArrayList<DayDetails> dayDetailsArrayList;
    private boolean onRequestFormVisibility,onRequestSuccessVisibility;
    private int nights, cutOffDays, currentMonthPosition, pos;
    private double slashPrice, price;
    private ArrayList<AllocationDTO> allocationDTOList;
    private String bookingPolicy, hotelImageUrl, hotelTitle, packageId, subPackageId, roomId, subPackage, packageName,cancellationPolicy;
    private ArrayList<String> weeksArrayList = new ArrayList<>();
    private Dialog spinningDialog;
    private RelativeLayout requestForm, requestSuccessForm;
    private GTMAnalytics gtmAnalytics;
    private StaycationCalendarPresenter mStaycationCalendarPresenter;
    private CurrentLocationDetail currentLocationDetail;
    private Locale locale;
    private BackPressInterFace backPressInterface;
    private  TextView alternatelyTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staycation_calender_layout);

        String lag = UserDTO.getUserDTO().getLanguage();

        if (Constant.ENGLISH_LANGUAGE_CODE.equals(lag)) {

            setLocale("en");
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        } else if (Constant.ARABIC_LANGUAGE_CODE.equals(lag)) {

            setLocale("ar");
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        initUi();
        getAllocations();

    }


    public void setLocale(String lang) {

        Locale locale = new Locale(lang);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

    }


    @Override
    public void showProgressDialog() {
        if (spinningDialog != null)
            spinningDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (spinningDialog != null && spinningDialog.isShowing())
            spinningDialog.dismiss();
    }

    @Override
    public void setAllocationDetails(JSONArray jsonArray) {
        if (jsonArray.length() > 0) {
            Type listType = new TypeToken<ArrayList<AllocationDTO>>() {
            }.getType();
            allocationDTOList = new GsonBuilder().create().fromJson(String.valueOf(jsonArray), listType);
            getMonths();
        }
    }

    public void backPress(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (backPressInterface != null) {
            backPressInterface.backPressCalled();
        }

        if (onRequestFormVisibility) {
            if(onRequestSuccessVisibility)
            {
                onRequestSuccessVisibility = false;
                (findViewById(R.id.requestSuccessForm)).setVisibility(View.GONE);
            }
            onRequestFormVisibility = false;
            (findViewById(R.id.requestForm)).setVisibility(View.GONE);
            dayDetailsAdapter.unCheckBox();
        } else {
            dayDetailsAdapter.unCheckBox();
            if (count == 0)
                finish();

            else
                getSupportFragmentManager().popBackStack();


        }
    }
    public void setBackPressListener(BackPressInterFace backPressInterFace) {
        this.backPressInterface = backPressInterFace;
    }

    private void initUi() {

        try {
            spinningDialog = CustomProgressDialog.showProgressDialog(this);
            mStaycationCalendarPresenter = new StaycationCalendarPresenter(StaycationCalendarActivity.this, this);
            gtmAnalytics = AppController.getInstance().getGTMAnalytics(this);

            dayDetailsArrayList = new ArrayList<>();
            allocationDTOList = new ArrayList<>();

            monthRecyclerView = (RecyclerView) findViewById(R.id.monthRecyclerView);
            previousMonthButton = (ImageView) findViewById(R.id.previousMonthButton);
            nextMonthButton = (ImageView) findViewById(R.id.nextMonthButton);

            previousMonthButton.setOnClickListener(this);
            nextMonthButton.setOnClickListener(this);

            dayDetailsRecyclerView = (RecyclerView) findViewById(R.id.dayDetailsRecyclerView);
            dayDetailsLinearLayoutManager = new CustomLayoutManager(this);
            alternatelyTextView=(TextView)findViewById(R.id.alternatelyTextView);
            dayDetailsRecyclerView.setLayoutManager(dayDetailsLinearLayoutManager);

            ((TextView) findViewById(R.id.subPackageTextView)).setText(getIntent().getStringExtra("SubPackage"));
            requestSuccessForm = (RelativeLayout) findViewById(R.id.requestSuccessForm);

            findViewById(R.id.closeTextView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestForm.setVisibility(View.GONE);
                    onRequestFormVisibility = false;
                    dayDetailsAdapter.unCheckBox();
                }
            });

            findViewById(R.id.closeSucessTextView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestSuccessForm.setVisibility(View.GONE);
                    onRequestFormVisibility = false;
                    onRequestSuccessVisibility = false;
                    dayDetailsAdapter.unCheckBox();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getAllocations() {

        try {
            if (NetworkUtilities.isInternet(this)) {

                getCurrentLocationDetailByIp();

                Intent intent = getIntent();
                packageId = intent.getStringExtra("PackageId");
                subPackageId = intent.getStringExtra("SubPackageId");
                cutOffDays = intent.getIntExtra("cutOffDays", 0);
                nights = intent.getIntExtra("Nights", 0);
                slashPrice = intent.getDoubleExtra("SlashPrice", 0);
                price = intent.getDoubleExtra("Price", 0);
                bookingPolicy = intent.getStringExtra("booking policy");
                cancellationPolicy = intent.getStringExtra("cancellationPolicy");
                hotelImageUrl = intent.getStringExtra("hotelimageurl");
                hotelTitle = intent.getStringExtra("hoteltitle");
                roomId = intent.getStringExtra("RoomId");
                subPackage = intent.getStringExtra("SubPackage");
                packageName = intent.getStringExtra("Package");


                JSONObject jsonObject = new JSONObject();
                jsonObject.put("CurrencyCode", UserDTO.getUserDTO().getCurrency());
                jsonObject.put("IpAddress", NetworkUtilities.getIp());
                jsonObject.put("PackageId", packageId);
                jsonObject.put("RoomId", roomId);
                jsonObject.put("SubPackageId", subPackageId);

                mStaycationCalendarPresenter.getAllocations(Constant.GetawaysEndPointUrl+Constant.STAYCATION_PACKAGE_ALLOCATION + UserDTO.getUserDTO().getLanguage(),
                        jsonObject.toString(), StaycationCalendarActivity.this);

            } else {
                getMonths();
                Utilities.commonErrorMessage(this, getString(R.string.Network_not_avilable), getString(R.string.please_check_your_internet_connection), false, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetMonths() {

        for (int i = 0; i < monthsArrayList.size(); i++) {
            monthsArrayList.get(i).setSelected(false);
        }

    }

    private void getCurrentLocationDetailByIp() {
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
    }

    public void openRequestForm(final DayDetails dayDetailsBean) {
        onRequestFormVisibility = true;
        requestForm = (RelativeLayout) findViewById(R.id.requestForm);
        final Spinner countryCodeSpinner = (Spinner) requestForm.findViewById(R.id.county_code_spinner);
        final String[] countryCodesArray = new Utilities().getCountryCodes();

        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
            countryCodeSpinner.setAdapter(new CountryCodeCustomAdapter(this, R.layout.spinner_drop_down_row, new Utilities().getArabicCountryCode(), countryCodesArray));
        } else {
            countryCodeSpinner.setAdapter(new CountryCodeCustomAdapter(this, R.layout.spinner_drop_down_row, new Utilities().getEnglishCountryCode(), countryCodesArray));
        }
        countryCodeSpinner.setSelection(pos);
        requestForm.setVisibility(View.VISIBLE);
        requestForm.requestFocus();

        TextView callInfoTextView= (TextView) requestForm.findViewById(R.id.callInfoTextView);

        String phoneNumber;


        if(UserDTO.getUserDTO().getCountryCode().equalsIgnoreCase("AE")){


            phoneNumber=getString(R.string.uae_toll_free_number);
            callInfoTextView.setText(getString(R.string.call_us_on)+" "+phoneNumber);


        }
        else if(UserDTO.getUserDTO().getCountryCode().equalsIgnoreCase("SA")){

            phoneNumber=getString(R.string.saudi_toll_free_number);
            callInfoTextView.setText(getString(R.string.call_us_on)+" "+phoneNumber);
        }
        else{

            phoneNumber=getString(R.string.worldwide_toll_free_number);
            callInfoTextView.setText(getString(R.string.call_us_on)+" "+phoneNumber);
        }



        callInfoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //   Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
                //  startActivity(intent);
                new Utilities().contactUsDialog(StaycationCalendarActivity.this);

            }
        });

        requestForm.findViewById(R.id.submitRequestButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gtmAnalytics.sendEvent("Getaway Details- " + packageName, "Request Form Submit", "Request Form Submit");
                EditText nameEditText = (EditText) requestForm.findViewById(R.id.edt_last_name);
                EditText emailEditText = (EditText) requestForm.findViewById(R.id.edt_email);
                EditText mobileNumberEditText = (EditText) requestForm.findViewById(R.id.editText_mob_no);
                EditText commentEditText = (EditText) requestForm.findViewById(R.id.edt_comment);
                String selection = countryCodesArray[countryCodeSpinner.getSelectedItemPosition()];

                if (nameEditText.getText().toString().trim().length() > 0) {
                    if (emailEditText.getText().toString().trim().length() > 0) {
                        if (mobileNumberEditText.getText().toString().trim().length() > 0) {

                            try {

                                if (NetworkUtilities.isInternet(StaycationCalendarActivity.this)) {
                                    spinningDialog.show();

                                    FreshDeskRequest jsonObject = new FreshDeskRequest();

                                    jsonObject.setName(nameEditText.getText().toString());
                                    jsonObject.setEmail(emailEditText.getText().toString());
                                    jsonObject.setPhone(selection + mobileNumberEditText.getText().toString());
                                    jsonObject.setSubject("FreshDesk ticket");
                                    jsonObject.setDescription(commentEditText.getText().toString());
                                    jsonObject.setPriority(3);
                                    jsonObject.setStatus(2);
                                    jsonObject.setProduct_id(Long.valueOf(Constant.productId));
                                    CustomFields CustomFields=new CustomFields();
                                    if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE))
                                        CustomFields.setLanguage("Arabic");
                                    else
                                        CustomFields.setLanguage("English");


                                    CustomFields.setNot_buying_reasons("None");
                                    jsonObject.setCustom_fields(CustomFields);


                                    new FreshDeskAsyncTask(new GsonBuilder().serializeNulls().create().toJson(jsonObject),StaycationCalendarActivity.this).execute(new String[]{Constant.FreshDeskURl});

                                } else
                                    Utilities.commonErrorMessage(StaycationCalendarActivity.this, getString(R.string.Network_not_avilable), getString(R.string.please_check_your_internet_connection), false, null);


                            } catch (Exception e) {

                            }

                        } else
                            mobileNumberEditText.setError("Enter Mobile Number");
                    } else
                        emailEditText.setError("Enter Email Id");
                } else
                    nameEditText.setError("Enter Full Name");

            }
        });

    }

    public class FreshDeskAsyncTask extends AsyncTask<String, Void, String> {

        String mRequest;
        Context mContext;

        public FreshDeskAsyncTask(String request, Context context) {
            mRequest = request;
            mContext = context;
        }

        @Override
        protected String doInBackground(String... params) {

            InputStream inputStream;
            String response = null;
            try {

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(params[0]);

                StringEntity stringEntity = new StringEntity(mRequest);
                Map<String, String> headerMap = NetworkUtilities.getFreshDeskHeader();
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
                spinningDialog.dismiss();

                JSONObject responseJsonObject = new JSONObject(response);

                requestForm.setVisibility(View.GONE);

                if (responseJsonObject.getLong("id")!=0) {
                    alternatelyTextView.setText(String.format(getResources().getString(R.string.alternatively), getResources().getString(R.string.worldwide_toll_free_number)));

                    requestSuccessForm.setVisibility(View.VISIBLE);
                    onRequestSuccessVisibility = true;
                    requestSuccessForm.findViewById(R.id.closeRequestButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onRequestSuccessVisibility = false;
                            requestSuccessForm.setVisibility(View.GONE);
                            dayDetailsAdapter.unCheckBox();
                        }
                    });
                } else
                    Utilities.commonErrorMessage(StaycationCalendarActivity.this, getString(R.string.app_name), getString(R.string.common_error_msg), false, null);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private ArrayList<String> getArabicMonths() {
        DateFormatSymbols symbols = new DateFormatSymbols(new Locale(Constant.ARABIC_LANGUAGE_CODE));
        ArrayList<String> monthsNameArrayList = new ArrayList<>();
        String[] months_Names = symbols.getMonths();
        for (String s : months_Names) {
            if (!s.trim().isEmpty()) {
                monthsNameArrayList.add(s);
            }
        }
        return monthsNameArrayList;
    }

    private void getArabicWeeks() {

        weeksArrayList.clear();
        DateFormatSymbols symbols = new DateFormatSymbols(new Locale(Constant.ARABIC_LANGUAGE_CODE));
        String[] months_Names = symbols.getShortWeekdays();
        for (String s : months_Names) {
            if (!s.trim().isEmpty()) {
                weeksArrayList.add(s);
            }
        }

    }


    private String getWeek(int index) {
        if (index > 6)
            index = index % 7;

        return weeksArrayList.get(index);

    }

    private int getWeekIndex(String week) {
        String[] weeks = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        for (int i = 0; i < weeks.length; i++) {
            if (week.equals(weeks[i]))
                return i;
        }

        return 0;
    }

    public void getMonths() {

        String monthString;
        Calendar now = Calendar.getInstance();
        Calendar beginCalendar = Calendar.getInstance();
        Calendar finishCalendar = Calendar.getInstance();

        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        String months[] = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "May", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        //String arabicMonths[] = {"يناير","فبراير","مارس","أبريل","مايو","يونيو","يوليو","أغسطس","سبتمبر","أكتوبر","نوفمبر","ديسمبر"};
        String[] arabicMonths = new String[getArabicMonths().size()];
        getArabicMonths().toArray(arabicMonths);

        if (UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
            locale = new Locale(Constant.ARABIC_LANGUAGE_CODE);
        } else {
            locale = new Locale(Constant.ENGLISH_LANGUAGE_CODE);
        }

        if (monthsArrayList == null)
            monthsArrayList = new ArrayList<>();

        monthsArrayList.clear();


        if (month < months.length)
            monthString = months[month];
        else
            monthString = "Invalid month";


        String currentDate = monthString + " " + String.valueOf(year);
        String finalDate = monthString + " " + String.valueOf(year + 2);

        DateFormat dateFormat = new SimpleDateFormat("MMMM yyyy",Locale.US);


        getArabicWeeks();

        try {
            beginCalendar.setTime(dateFormat.parse(currentDate));
            finishCalendar.setTime(dateFormat.parse(finalDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EE",Locale.US);

        int i = 0, j = month;
        boolean beginFlag = false;
        String date, week;
        while (beginCalendar.before(finishCalendar)) {

            date = dateFormat.format(beginCalendar.getTime()).toUpperCase();

            int currentMonth = beginCalendar.get(Calendar.MONTH);
            String dayMonth = date.split(" ")[0];
            String dayYear = date.split(" ")[1];

            if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
                date = arabicMonths[j] + " " + date.split(" ")[1];
                dayMonth = arabicMonths[j];
            }
            monthsArrayList.add(new MonthsData(date, i));

            while (currentMonth == beginCalendar.get(Calendar.MONTH)) {

                int currentDay = beginCalendar.get(Calendar.DAY_OF_MONTH);
                if (currentDay == day && !beginFlag) {
                    beginFlag = true;
                }

                if (beginFlag) {
                    i++;

                    week = simpleDateFormat.format(beginCalendar.getTime());

                    if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {

                        if (i == 1)
                            week = getWeek(getWeekIndex(week));

                        else
                            week = getWeek(i - 1);
                    }


                    String allocationMonth, allocationDay;

                    if ((beginCalendar.get(Calendar.MONTH) + 1) < 10)
                        allocationMonth = "0" + (beginCalendar.get(Calendar.MONTH) + 1);

                    else
                        allocationMonth = (beginCalendar.get(Calendar.MONTH) + 1) + "";

                    if (currentDay < 10)
                        allocationDay = "0" + currentDay;

                    else
                        allocationDay = currentDay + "";

                    String allocationDate = beginCalendar.get(Calendar.YEAR) + "-" + allocationMonth + "-" + allocationDay + "T00:00:00";

                    dayDetailsArrayList.add(new DayDetails(currentDay + "", week, dayMonth, dayYear, allocationDate, price, slashPrice, nights, 2, 0, false));
                }
                beginCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            if (j == 11)
                j = 0;

            else
                j++;
        }
        setMonthsAdapter();
        getDayDetails();

    }

    private void setMonthsAdapter() {

        //if(UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE))
         //   Collections.reverse(monthsArrayList);

        monthsAdapter = new MonthsAdapter(this, monthsArrayList);
        monthsLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        monthRecyclerView.setLayoutManager(monthsLinearLayoutManager);
        monthRecyclerView.setAdapter(monthsAdapter);

        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE)) {
            monthsArrayList.get(0).setSelected(true);
            currentMonthPosition = 0;
        } else {
            currentMonthPosition = 0;

            if(monthsArrayList.size() > 0) {
                monthRecyclerView.scrollToPosition(0);
                monthsArrayList.get(0).setSelected(true);
            }
        }

        monthsAdapter.SetOnItemClickListener(new MonthsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                gtmAnalytics.sendEvent("Getaway Details- " + packageName, "Select Month " + subPackage, "Select Month " + subPackage);
                currentMonthPosition = position;
                resetMonths();
                monthsArrayList.get(position).setSelected(true);
                monthsLinearLayoutManager.scrollToPositionWithOffset(position, 0);
                monthsAdapter.notifyDataSetChanged();
                dayDetailsRecyclerView.smoothScrollToPosition(monthsArrayList.get(currentMonthPosition).getDayIndex());

            }
        });


    }

    private void getDayDetails() {


        dayDetailsAdapter = new DayDetailsAdapter(this, cutOffDays, dayDetailsArrayList, allocationDTOList, bookingPolicy,
                                                    hotelTitle, hotelImageUrl, packageId, subPackageId, roomId, subPackage,
                                                    gtmAnalytics, packageName,cancellationPolicy,spinningDialog);

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

                if (newState == SCROLL_STATE_IDLE) {
                    int dayVisiblePosition = dayDetailsLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                    /*if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE))
                        calculateArabicMonth(dayVisiblePosition);

                    else*/
                        calculateMonth(dayVisiblePosition);
                }
            }
        });


    }

    private void calculateArabicMonth(int dayIndex) {

        for (int i = (monthsArrayList.size() - 1); i >= 0; i--) {

            if (monthsArrayList.get(i).getDayIndex() >= dayIndex) {
                if (i >= 0) {

                    currentMonthPosition = i + 1;

                    resetMonths();
                    monthsArrayList.get(currentMonthPosition).setSelected(true);
                    monthsLinearLayoutManager.scrollToPositionWithOffset(currentMonthPosition, 0);
                    monthsAdapter.notifyDataSetChanged();
                    break;
                }
            }
            if (monthsArrayList.get(0).getDayIndex() < dayIndex) {
                resetMonths();
                currentMonthPosition = 0;
                monthsArrayList.get(currentMonthPosition).setSelected(true);
                monthsLinearLayoutManager.scrollToPositionWithOffset(currentMonthPosition, 0);
                monthsAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    private void calculateMonth(int dayIndex) {

        for (int i = 0; i < monthsArrayList.size(); i++) {
            if (monthsArrayList.get(i).getDayIndex() >= dayIndex) {
                if (i > 0) {

                    if (i == (monthsArrayList.size()))
                        currentMonthPosition = i;

                    else
                        currentMonthPosition = i - 1;

                    resetMonths();
                    monthsArrayList.get(currentMonthPosition).setSelected(true);
                    monthsLinearLayoutManager.scrollToPositionWithOffset(currentMonthPosition, 0);
                    monthsAdapter.notifyDataSetChanged();
                    break;
                }
            }
            if (monthsArrayList.get(monthsArrayList.size() - 1).getDayIndex() < dayIndex) {
                resetMonths();
                currentMonthPosition = monthsArrayList.size() - 1;
                monthsArrayList.get(currentMonthPosition).setSelected(true);
                monthsLinearLayoutManager.scrollToPositionWithOffset(currentMonthPosition, 0);
                monthsAdapter.notifyDataSetChanged();
                break;
            }
        }
    }


    public class CustomLayoutManager extends LinearLayoutManager {


        public CustomLayoutManager(Context context) {
            super(context, VERTICAL, false);
        }


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
        }*/


        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
            RecyclerView.SmoothScroller smoothScroller = new CustomLayoutManager.TopSnappedSmoothScroller(recyclerView.getContext());
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


                    if (currentMonthPosition > 0) {
                        resetMonths();
                        currentMonthPosition = currentMonthPosition - 1;
                        monthsArrayList.get(currentMonthPosition).setSelected(true);
                        monthsLinearLayoutManager.scrollToPositionWithOffset(currentMonthPosition, 0);
                        monthsAdapter.notifyDataSetChanged();

                        dayDetailsRecyclerView.smoothScrollToPosition(monthsArrayList.get(currentMonthPosition).getDayIndex());
                    }




                break;

            case R.id.nextMonthButton:

                if (currentMonthPosition < monthsArrayList.size() - 1) {
                    resetMonths();
                    currentMonthPosition = currentMonthPosition + 1;
                    monthsArrayList.get(currentMonthPosition).setSelected(true);
                    monthsLinearLayoutManager.scrollToPositionWithOffset(currentMonthPosition, 0);
                    monthsAdapter.notifyDataSetChanged();

                    dayDetailsRecyclerView.smoothScrollToPosition(monthsArrayList.get(currentMonthPosition).getDayIndex());
                }

                break;

        }
    }

}

