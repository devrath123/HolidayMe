package com.holidayme.staycationListing_mvp;

/**
 * Created by supriya.sakore on 06-08-2015.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.holidayme.AppInterface.BackPressInterFace;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.HomeActivity;
import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.HolidayMeFont;
import com.holidayme.activities.util.Utilities;
import com.holidayme.adapter.CountryCodeCustomAdapter;
import com.holidayme.adapter.StaycationListingAdapter;
import com.holidayme.common.CustomProgressDialog;
import com.holidayme.common.Log;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.data.CustomFields;
import com.holidayme.data.FreshDeskRequest;
import com.holidayme.data.GetawaysFilterRequestDto;
import com.holidayme.data.GetawaysListingRequest;
import com.holidayme.data.PackageDetailResponse;
import com.holidayme.data.PackagesListDto;
import com.holidayme.data.UserDTO;
import com.holidayme.fragments.BaseFragment;
import com.holidayme.gtm.GTMAnalytics;
import com.holidayme.response.CurrentLocationDetail;
import com.holidayme.staycation_calendar_mvp.StaycationCalendarActivity;
import com.holidayme.staycation_details_mvp.StaycationDetailsActivity;
import com.holidayme.staycationcustomcalender.DayDetails;
import com.holidayme.widgets.FlowLayout;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


public class StaycationListFragment extends BaseFragment implements IStyacationListView ,BackPressInterFace {

    private View rootView;
    private RecyclerView getawaysLisRecyclerView;
    private ImageView closeFilterImageView,filterFloatingActionButton,getawaysListBackPress,OnRequestActionButton;
    private RelativeLayout getawaysFilter,onRequestForm;
    private Dialog spinningDialog;
    private StaycationListingPresenter staycationListingPresenter;
    private FlowLayout chipsBoxLayout;
    private FlowLayout.LayoutParams params;
    private SeekBar distanceSeekBar;
    private PackageDetailResponse packageDetailResponse;
    private TextView applyFilterTextView,resetFilterTextView,headerTextView,alternatelyTextView;
    private RadioButton popularitySortRadioButton,priceSortRadioButton;
    private ArrayList<Long>categoriesId=new ArrayList<>();

    private int orderType=2,sortBy=2,tempOrderType=2,tempSortBy=2;
    private boolean isFilterApply,isApplyClick;
    private  long cityId;
    private GTMAnalytics gtmAnalytics;
    private RelativeLayout requestSuccessForm;
    private CurrentLocationDetail currentLocationDetail;
    private int pos=0,  count=0;
    private int previousProgress=600;




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle =this.getArguments();
        if(bundle !=null) {
               cityId = bundle.getLong("cityId");

            }
        ((HomeActivity) getActivity()).setBackPressListener(this);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.staycation_listing_fragment, container, false);
        staycationListingPresenter=new StaycationListingPresenter(this);

        gtmAnalytics = AppController.getInstance().getGTMAnalytics(getActivity());
        return rootView;
    }



    @Override
    public void initUIElements() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        setHasOptionsMenu(true);

        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getawaysLisRecyclerView = (RecyclerView)rootView.findViewById(R.id.ListingRecyclerView);
        filterFloatingActionButton=(ImageView)rootView.findViewById(R.id.filterActionButton) ;
        getawaysFilter=(RelativeLayout)rootView.findViewById(R.id.getawaysFilter);
        closeFilterImageView=(ImageView)rootView.findViewById(R.id.closeFilterImageView);
        getawaysListBackPress=(ImageView)rootView.findViewById(R.id.getawaysListBackPress);
        applyFilterTextView=(TextView)rootView.findViewById(R.id.applyFilterTextView);
        popularitySortRadioButton=(RadioButton)rootView.findViewById(R.id.popularitySortRadioButton);
        priceSortRadioButton=(RadioButton)rootView.findViewById(R.id.priceSortRadioButton);
        distanceSeekBar=(SeekBar)rootView.findViewById(R.id.distanceSeekBar);
        resetFilterTextView=(TextView)rootView.findViewById(R.id.resetFilterTextView);
        chipsBoxLayout = (FlowLayout)rootView.findViewById(R.id.categoriesLayout);
        OnRequestActionButton=(ImageView)rootView.findViewById(R.id.OnRequestActionButton);
        onRequestForm=(RelativeLayout)rootView.findViewById(R.id.on_request_form) ;
        requestSuccessForm = (RelativeLayout) rootView.findViewById(R.id.on_request_success);
        headerTextView=(TextView)rootView.findViewById(R.id.headerTextView);
        alternatelyTextView=(TextView)rootView.findViewById(R.id.alternatelyTextView);


        getGetawaysList(true);
        getCurrentLocationDetailByIp();

        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
            HolidayMeFont.overrideFonts(getActivity(), popularitySortRadioButton, Constant.NotoKufiArabic_Regular);
            HolidayMeFont.overrideFonts(getActivity(), priceSortRadioButton, Constant.NotoKufiArabic_Regular);

        }
            else{
            HolidayMeFont.overrideFonts(getActivity(), popularitySortRadioButton, Constant.HelveticaNeueLight);
            HolidayMeFont.overrideFonts(getActivity(), priceSortRadioButton, Constant.HelveticaNeueLight);
        }

        uIFunctionality();

    }

    private void uIFunctionality(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        getawaysLisRecyclerView.setLayoutManager(linearLayoutManager);

        filterFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gtmAnalytics.sendEvent("Getaway Listing - " + packageDetailResponse.getPackageDetails().get(0).getCity(), "Filter" , "Filter Box click");

                getawaysFilter.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_up);
                animation.setDuration(500);
                getawaysFilter.setAnimation(animation);
                getawaysFilter.animate();
                animation.start();
                OnRequestActionButton.setVisibility(View.GONE);
                filterFloatingActionButton.setVisibility(View.GONE);
            }
        });

        closeFilterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_down);
                animation.setDuration(500);
                getawaysFilter.setAnimation(animation);
                getawaysFilter.animate();
                if(packageDetailResponse.getCategoryFilter()!=null) {
                    for (int i = 0; i < packageDetailResponse.getCategoryFilter().size(); i++) {
                        packageDetailResponse.getCategoryFilter().get(i).setCheck(false);
                        for (int j = 0; j < categoriesId.size(); j++) {
                            if (packageDetailResponse.getCategoryFilter().get(i).getId()==(categoriesId.get(j))) {
                                packageDetailResponse.getCategoryFilter().get(i).setCheck(true);
                                break;
                            }
                        }
                    }
                }
                if(sortBy!=tempSortBy){
                    orderType=tempOrderType;
                    if(sortBy==1)
                    {
                        priceSortRadioButton.performClick();
                    }else{
                        popularitySortRadioButton.performClick();
                    }
                }
                animation.start();
                getawaysFilter.setVisibility(View.GONE);
                OnRequestActionButton.setVisibility(View.VISIBLE);
                filterFloatingActionButton.setVisibility(View.VISIBLE);
                distanceSeekBar.setProgress(previousProgress);
                chipsBoxLayout.removeAllViews();
                filterFunctionality(true);
            }
        });
        distanceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
              
                if (progress > 0 && progress < 150) {
                    seekBar.setProgress(150);

                }
                else  if (progress > 151 && progress < 300) {
                    seekBar.setProgress(300);

                }
                else  if (progress > 301 && progress < 450) {
                    seekBar.setProgress(450);

                }
                else  if (progress > 451 ) {
                    seekBar.setProgress(600);

                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        OnRequestActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnRequestActionButton.setVisibility(View.GONE);
                filterFloatingActionButton.setVisibility(View.GONE);
                openRequestForm();
            }
        });
        applyFilterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(packageDetailResponse!=null&&packageDetailResponse.getCategoryFilter()!=null) {
                    for (int i = 0; i < packageDetailResponse.getCategoryFilter().size(); i++) {
                        if (packageDetailResponse.getCategoryFilter().get(i).isCheck()) {
                            isFilterApply=true;
                            categoriesId.add(packageDetailResponse.getCategoryFilter().get(i).getId());
                        }else{
                            categoriesId.remove(packageDetailResponse.getCategoryFilter().get(i).getId());

                        }
                    }
                }
                orderType=tempOrderType;
                sortBy=tempSortBy;
                gtmAnalytics.sendEvent("Getaway Listing - " + packageDetailResponse.getPackageDetails().get(0).getCity(), "Apply" , "Fliter Applied");

                if(isFilterApply||distanceSeekBar.getProgress()<600) {
                    isApplyClick=true;
                    previousProgress=distanceSeekBar.getProgress();
                    getGetawaysList(false);
                }
                closeFilterImageView.performClick();
            }
        });
        getawaysListBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();
                gtmAnalytics.sendEvent("Getaway Listing - " + packageDetailResponse.getPackageDetails().get(0).getCity(), "Back button" , "Back button Click");

            }
        });

        resetFilterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int j=0;j<packageDetailResponse.getCategoryFilter().size();j++)
                if(packageDetailResponse.getCategoryFilter().get(j).isCheck()) {
                    packageDetailResponse.getCategoryFilter().get(j).setCheck(false);
                    categoriesId.remove(packageDetailResponse.getCategoryFilter().get(j).getId());
                    chipsBoxLayout.removeAllViews();
                    filterFunctionality(true);
                }
                if(distanceSeekBar.getProgress()<600)
                    distanceSeekBar.setProgress(600);

                applyFilterTextView.performClick();
            }
        });

        priceSortRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempSortBy = 1;
                isFilterApply = true;
                if (UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
                    popularitySortRadioButton.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.getaways_sort_selector), null);

                    if (priceSortRadioButton.isChecked()) {
                        if (tempOrderType == 2) {
                            tempOrderType = 1;
                            priceSortRadioButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.arrow_up), null, getResources().getDrawable(R.drawable.getaways_sort_selector), null);
                        } else {
                            tempOrderType = 2;
                            priceSortRadioButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.arrow_down), null, getResources().getDrawable(R.drawable.getaways_sort_selector), null);
                        }
                    } else {
                        tempOrderType = 1;
                        priceSortRadioButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.arrow_up), null, getResources().getDrawable(R.drawable.getaways_sort_selector), null);

                    }
                }else{
                    popularitySortRadioButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.getaways_sort_selector), null, null, null);

                    if (priceSortRadioButton.isChecked()) {
                        if (tempOrderType == 2) {
                            tempOrderType = 1;
                            priceSortRadioButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.getaways_sort_selector), null, getResources().getDrawable(R.drawable.arrow_up), null);
                        } else {
                            tempOrderType = 2;
                            priceSortRadioButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.getaways_sort_selector), null, getResources().getDrawable(R.drawable.arrow_down), null);
                        }
                    } else {
                        tempOrderType = 2;
                        priceSortRadioButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.getaways_sort_selector), null, getResources().getDrawable(R.drawable.arrow_down), null);

                    }
                }
                }

        });
        popularitySortRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempSortBy=2;
                isFilterApply=true;
                if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
                    priceSortRadioButton.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.getaways_sort_selector), null);

                    if (popularitySortRadioButton.isChecked()) {
                        if (tempOrderType == 2) {
                            tempOrderType = 1;
                            gtmAnalytics.sendEvent("Getaway Listing - " + packageDetailResponse.getPackageDetails().get(0).getCity(), "Sort By Popularity" , "Sort by - High to low");
                            popularitySortRadioButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.arrow_down), null, getResources().getDrawable(R.drawable.getaways_sort_selector), null);
                        } else {
                            tempOrderType = 2;
                            popularitySortRadioButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.arrow_up), null, getResources().getDrawable(R.drawable.getaways_sort_selector), null);
                        }
                    } else {
                        tempOrderType = 1;
                        gtmAnalytics.sendEvent("Getaway Listing - " + packageDetailResponse.getPackageDetails().get(0).getCity(), "Sort By Popularity" , "Sort by - High to low");
                        popularitySortRadioButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.arrow_up), null, getResources().getDrawable(R.drawable.getaways_sort_selector), null);

                    }
                }else{
                    priceSortRadioButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.getaways_sort_selector), null, null, null);

                    if (popularitySortRadioButton.isChecked()) {
                        if (tempOrderType == 2) {
                            tempOrderType = 1;
                            popularitySortRadioButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.getaways_sort_selector), null, getResources().getDrawable(R.drawable.arrow_down), null);
                        } else {
                            tempOrderType = 2;
                            popularitySortRadioButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.getaways_sort_selector), null, getResources().getDrawable(R.drawable.arrow_up), null);
                        }
                    } else {
                        tempOrderType = 1;
                        popularitySortRadioButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.getaways_sort_selector), null, getResources().getDrawable(R.drawable.arrow_up), null);

                    }

                }
            }
        });
       params = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 10, 10, 10);



    }
    public void openRequestForm() {


        final Spinner countryCodeSpinner = (Spinner) rootView.findViewById(R.id.county_code_spinner);
        final String[] countryCodesArray = new Utilities().getCountryCodes();

        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
            countryCodeSpinner.setAdapter(new CountryCodeCustomAdapter(getActivity(), R.layout.spinner_drop_down_row, new Utilities().getArabicCountryCode(), countryCodesArray));
        } else {
            countryCodeSpinner.setAdapter(new CountryCodeCustomAdapter(getActivity(), R.layout.spinner_drop_down_row, new Utilities().getEnglishCountryCode(), countryCodesArray));
        }
        countryCodeSpinner.setSelection(pos);
        onRequestForm.setVisibility(View.VISIBLE);
        TextView closeTextView=(TextView)rootView.findViewById(R.id.closeTextView);

        TextView callInfoTextView= (TextView) rootView.findViewById(R.id.callInfoTextView);

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
                new Utilities().contactUsDialog(getActivity());

            }
        });





        closeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRequestForm.setVisibility(View.GONE);
                OnRequestActionButton.setVisibility(View.VISIBLE);
                filterFloatingActionButton.setVisibility(View.VISIBLE);
            }
        });
        rootView.findViewById(R.id.submitRequestButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText nameEditText = (EditText) rootView.findViewById(R.id.edt_last_name);
                EditText emailEditText = (EditText) rootView.findViewById(R.id.edt_email);
                EditText mobileNumberEditText = (EditText) rootView.findViewById(R.id.editText_mob_no);
                EditText commentEditText = (EditText) rootView.findViewById(R.id.edt_comment);
                String selection = countryCodesArray[countryCodeSpinner.getSelectedItemPosition()];


                if (nameEditText.getText().toString().trim().length() > 0) {
                    if (emailEditText.getText().toString().trim().length() > 0) {
                        if (mobileNumberEditText.getText().toString().trim().length() > 0) {

                            try {

                                if (NetworkUtilities.isInternet(getActivity())) {
                                    spinningDialog.show();

                                    FreshDeskRequest jsonObject = new FreshDeskRequest();

                                    jsonObject.setName(nameEditText.getText().toString().trim());
                                    jsonObject.setEmail(emailEditText.getText().toString().trim());
                                    jsonObject.setPhone(selection + mobileNumberEditText.getText().toString().trim());
                                    jsonObject.setSubject("FreshDesk ticket");
                                    jsonObject.setDescription(commentEditText.getText().toString().trim());
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


                                    new FreshDeskAsyncTask(new GsonBuilder().serializeNulls().create().toJson(jsonObject),getActivity()).execute(new String[]{Constant.FreshDeskURl});

                                } else
                                    Utilities.commonErrorMessage(getActivity(), getString(R.string.Network_not_avilable), getString(R.string.please_check_your_internet_connection), false, null);


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

    @Override
    public void backPressCalled() {
        if(  getawaysFilter.getVisibility()==View.VISIBLE){
            closeFilterImageView.performClick();
        }else{
            ((HomeActivity) getActivity()).setBackPressListener(null);
            getActivity().onBackPressed();
        }
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
              //  stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
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

                onRequestForm.setVisibility(View.GONE);

                if (responseJsonObject.getLong("id")!=0) {
                    alternatelyTextView.setText(String.format(getResources().getString(R.string.alternatively), getResources().getString(R.string.worldwide_toll_free_number)));
                    requestSuccessForm.setVisibility(View.VISIBLE);
                  TextView  closeSucessTextView=(TextView)rootView.findViewById(R.id.closeSucessTextView);
                    closeSucessTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestSuccessForm.findViewById(R.id.closeRequestButton).performClick();
                        }
                    });
                    requestSuccessForm.findViewById(R.id.closeRequestButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            requestSuccessForm.setVisibility(View.GONE);
                            OnRequestActionButton.setVisibility(View.VISIBLE);
                            filterFloatingActionButton.setVisibility(View.VISIBLE);
                        }
                    });
                } else
                    Utilities.commonErrorMessage(getActivity(), getString(R.string.app_name), getString(R.string.common_error_msg), false, null);


            } catch (Exception e) {
                OnRequestActionButton.setVisibility(View.VISIBLE);
                filterFloatingActionButton.setVisibility(View.VISIBLE);
                onRequestForm.setVisibility(View.GONE);
                Utilities.commonErrorMessage(getActivity(), getString(R.string.app_name), getString(R.string.common_error_msg), false, null);

                e.printStackTrace();
            }
        }
    }

    private void filterFunctionality(boolean isFirstTime){
      int j=0;

    while(j<packageDetailResponse.getCategoryFilter().size())
    {
        TextView t = new TextView(getActivity());
        t.setTag(j);
        t.setLayoutParams(params);
        t.setPadding(25, 25, 25, 25);
        t.setGravity(Gravity.CENTER);
        t.setText(packageDetailResponse.getCategoryFilter().get(j).getTitle());
        if(packageDetailResponse.getCategoryFilter().get(j).isCheck())
        {
            packageDetailResponse.getCategoryFilter().get(j).setCheck(true);

            t.setTextColor(Color.WHITE);
            t.setBackground(getResources().getDrawable(R.drawable.categories_selector));
        }else {
            packageDetailResponse.getCategoryFilter().get(j).setCheck(false);

            t.setTextColor(Color.BLACK);
            t.setBackground(getResources().getDrawable(R.drawable.bg_category_rounded_button));
        }
        t.setOnClickListener(getOnClickDoSomething(t));
     //   if(packageDetailResponse.getCategoryFilter().get(j).getId()==categoriesId.get(j))
        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
            HolidayMeFont.overrideFonts(getActivity(), t, Constant.NotoKufiArabic_Regular);
        }
        else{
            HolidayMeFont.overrideFonts(getActivity(), t, Constant.HelveticaNeueLight);
        }
        if(isFirstTime) {

            chipsBoxLayout.addView(t);
        }
        j++;
    }




 }
    private View.OnClickListener getOnClickDoSomething(final TextView categoryTextView)  {
        return new View.OnClickListener() {
            public void onClick(View v) {
                int Position=(Integer) v.getTag();

                     if(packageDetailResponse.getCategoryFilter().get(Position).isCheck()) {
                         packageDetailResponse.getCategoryFilter().get(Position).setCheck(false);
                        // categoriesId.remove(packageDetailResponse.getCategoryFilter().get(Position).getId());
                         categoryTextView.setTextColor(Color.BLACK);
                         categoryTextView.setBackground(getResources().getDrawable(R.drawable.bg_category_rounded_button));
                     }
                    else {
                         packageDetailResponse.getCategoryFilter().get(Position).setCheck(true);

                        // categoriesId.add(packageDetailResponse.getCategoryFilter().get(Position).getId());
                        // isFilterApply=true;
                         categoryTextView.setTextColor(Color.WHITE);
                         categoryTextView.setBackground(getResources().getDrawable(R.drawable.categories_selector));
                     }




            }
        };
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
    @Override
    public void dismissDialog() {
        if(spinningDialog!=null&&spinningDialog.isShowing())
            spinningDialog.dismiss();
    }

    @Override
    public void showDialog() {
        spinningDialog = CustomProgressDialog.showProgressDialog(getActivity());
        if(spinningDialog!=null&&!spinningDialog.isShowing()) {
            spinningDialog.show();
        }
    }
     private void getGetawaysList(boolean isFirstTime){
         GetawaysListingRequest getawaysListingRequest=new GetawaysListingRequest();
         getawaysListingRequest.setCityId(cityId);
         getawaysListingRequest.setCurrencyCode(UserDTO.getUserDTO().getCurrency());
         getawaysListingRequest.setIpAddress(NetworkUtilities.getIp());
         getawaysListingRequest.setSortBy(sortBy);
         getawaysListingRequest.setOrderBy(orderType);
         ArrayList<GetawaysFilterRequestDto>getawaysFilterRequestDtos=new ArrayList<>();

        if(categoriesId.size()!=0) {
            gtmAnalytics.sendEvent("Getaway Listing - " + packageDetailResponse.getPackageDetails().get(0).getCity(), "Filter By Category" , "Filter By Category");
            getawaysFilterRequestDtos.add(new GetawaysFilterRequestDto(1, categoriesId, 0.0, 0.0));
        }
        if(distanceSeekBar.getProgress()<600) {
            gtmAnalytics.sendEvent("Getaway Listing - " + packageDetailResponse.getPackageDetails().get(0).getCity(), "Filter By Distance" , "Filter By Distance");
           if(distanceSeekBar.getProgress()>450)
            getawaysFilterRequestDtos.add(new GetawaysFilterRequestDto(2, new ArrayList<Long>(), 450, distanceSeekBar.getProgress()));
            else if(distanceSeekBar.getProgress()>300&&distanceSeekBar.getProgress()<451)
               getawaysFilterRequestDtos.add(new GetawaysFilterRequestDto(2, new ArrayList<Long>(), 300, distanceSeekBar.getProgress()));
            else if(distanceSeekBar.getProgress()>150&&distanceSeekBar.getProgress()<301)
               getawaysFilterRequestDtos.add(new GetawaysFilterRequestDto(2, new ArrayList<Long>(), 150, distanceSeekBar.getProgress()));
            else
               getawaysFilterRequestDtos.add(new GetawaysFilterRequestDto(2, new ArrayList<Long>(), 0, distanceSeekBar.getProgress()));

        }

         getawaysListingRequest.setFilter(getawaysFilterRequestDtos);
         if (NetworkUtilities.isInternet(getActivity())) {
             staycationListingPresenter.PostStaycationList(Constant.GetawaysEndPointUrl+Constant.getawaysListUrl+UserDTO.getUserDTO().getLanguage(), new GsonBuilder().serializeNulls().create().toJson(getawaysListingRequest), getActivity(), getFragmentManager(),isFirstTime);
         } else
             Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), false, getFragmentManager());

     }
    @Override
    public void setGetawaysListResponse(PackageDetailResponse packageDetailResponse,boolean isFirstTime) {
        ArrayList<PackagesListDto>listDtos= new ArrayList<>();
        if(this.packageDetailResponse==null)
       this.packageDetailResponse=packageDetailResponse;
        gtmAnalytics.setScreenName("Getaway Listing - " + packageDetailResponse.getPackageDetails().get(0).getCity());

        Collections.sort(packageDetailResponse.getPackageDetails(),new myListSort());

         count=0;
       if(packageDetailResponse.getPackageDetails()!=null) {
           gtmAnalytics.setScreenName("Getaway Listing - " + packageDetailResponse.getPackageDetails().get(0).getCity());
           for (int i = 0; i < packageDetailResponse.getPackageDetails().size(); i++) {
               listDtos.add(new PackagesListDto(packageDetailResponse.getPackageDetails().get(i).getCity(), packageDetailResponse.getPackageDetails().get(i).getCountry(), packageDetailResponse.getPackageDetails().get(i).getDistance(), "header", packageDetailResponse.getPackageDetails().get(i).getPackages().size()));
               count=count+packageDetailResponse.getPackageDetails().get(i).getPackages().size();
               for (int j = 0; j <packageDetailResponse.getPackageDetails().get(i).getPackages().size() ; j++) {
                   listDtos.add(new PackagesListDto(packageDetailResponse.getPackageDetails().get(i).getPackages().get(j).getPackageId(), packageDetailResponse.getPackageDetails().get(i).getPackages().get(j).getHotelId(), packageDetailResponse.getPackageDetails().get(i).getPackages().get(j).getCityId(), packageDetailResponse.getPackageDetails().get(i).getPackages().get(j).getTitle(), packageDetailResponse.getPackageDetails().get(i).getPackages().get(j).getListingImage(), packageDetailResponse.getPackageDetails().get(i).getPackages().get(j).getCityName(), packageDetailResponse.getPackageDetails().get(i).getPackages().get(j).getCountryName(), packageDetailResponse.getPackageDetails().get(i).getPackages().get(j).getIsExlusiveDeal(), packageDetailResponse.getPackageDetails().get(i).getPackages().get(j).getIsSellingFast(), packageDetailResponse.getPackageDetails().get(i).getPackages().get(j).getSlashRate(), packageDetailResponse.getPackageDetails().get(i).getPackages().get(j).getStartFromPrice(), packageDetailResponse.getPackageDetails().get(i).getPackages().get(j).getDistance(), packageDetailResponse.getPackageDetails().get(i).getPackages().get(j).getBuyingCurrency(), "row"));

               }
           }
       }



        if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)){

            headerTextView.setText(getResources().getString(R.string.getaways_actionbar_title)+" "+count);
        }
        else{

            headerTextView.setText(""+count+" "+getResources().getString(R.string.getaways_actionbar_title));
        }

        filterFunctionality(isFirstTime);

        StaycationListingAdapter staycationListingAdapter = new StaycationListingAdapter(getActivity(),listDtos);
        getawaysLisRecyclerView.setAdapter(staycationListingAdapter);
    }
    class myListSort implements Comparator<PackagesListDto> {

        @Override
        public int compare(PackagesListDto e1, PackagesListDto e2) {
            return e1.getDistance().compareTo(e2.getDistance());
        }
    }
}



