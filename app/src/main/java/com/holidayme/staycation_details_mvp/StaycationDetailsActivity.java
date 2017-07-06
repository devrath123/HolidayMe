package com.holidayme.staycation_details_mvp;


        import android.app.Dialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.res.Configuration;
        import android.content.res.Resources;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Color;
        import android.graphics.Typeface;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.design.widget.AppBarLayout;
        import android.support.v4.widget.NestedScrollView;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.text.Html;
        import android.text.TextUtils;
        import android.util.DisplayMetrics;
        import android.view.Gravity;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.RatingBar;
        import android.widget.RelativeLayout;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.NetworkResponse;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.bumptech.glide.Glide;
        import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;
        import com.holidayme.Constants.Constant;
        import com.holidayme.activities.ImageZoomInActivity;
        import com.holidayme.activities.R;
        import com.holidayme.activities.VolleySupport.AppController;
        import com.holidayme.activities.util.HolidayMeFont;
        import com.holidayme.activities.util.NonScrollListView;
        import com.holidayme.activities.util.Utilities;
        import com.holidayme.adapter.AmenitiesGridViewAdapter;
        import com.holidayme.adapter.CountryCodeCustomAdapter;
        import com.holidayme.adapter.SubpackageAdapter;
        import com.holidayme.adapter.NearAndAroundAdapter;
        import com.holidayme.adapter.TopReviewAdapter;
        import com.holidayme.common.AmenityGridView;
        import com.holidayme.common.CustomProgressDialog;
        import com.holidayme.common.Log;
        import com.holidayme.common.NetworkUtilities;
        import com.holidayme.data.AmenitiesDTO;
        import com.holidayme.data.CustomFields;
        import com.holidayme.data.FreshDeskRequest;
        import com.holidayme.data.GetawayDetailBean;
        import com.holidayme.data.NearandAroundBean;
        import com.holidayme.data.ReviewsDto;
        import com.holidayme.data.UserDTO;
        import com.holidayme.data.ZoomInImagesDTO;
        import com.holidayme.gtm.GTMAnalytics;
        import com.holidayme.response.CurrentLocationDetail;
        import com.holidayme.response.TripAdviserDataResponse;
        import com.nostra13.universalimageloader.core.DisplayImageOptions;
        import com.nostra13.universalimageloader.core.ImageLoader;
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
        import java.util.List;
        import java.util.Locale;
        import java.util.Map;


public class StaycationDetailsActivity extends AppCompatActivity implements IStaycationDetailsView {

    private net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout collapsingToolbar;
    private RecyclerView getawayRecyclerView;
    private AmenityGridView gridView;
    private List<AmenitiesDTO.Amenity> amenitiesDTOList = new ArrayList<>();
    private int initialCount = 6;
    private ArrayList<AmenitiesDTO.Amenity> tempAmenitiesList = new ArrayList<>();
    boolean loadMore;
    private RecyclerView nearAndAroundRecyclerView;
    private RelativeLayout requestForm, requestSuccessForm;
    private TextView alternatelyTextView,titleTextView,overviewTextView,ammenityShowLess, overviewReadMoreTextView, viewAllNearAroundTextView, policiesTextView, hotelPoliciesTextView;
    private NestedScrollView nestedScrollView;
    private NonScrollListView reviewsScrollView;
    private ArrayList<NearandAroundBean.FourSquare> nearAndAroundBeanList = new ArrayList<>();
    private String hotelTitle, hotelImageUrl, bookingPolicy,cancellationPolicy;
    private Dialog spinningDialog;
    private GTMAnalytics gtmAnalytics;
    private String packageName;
    private StaycationDetailsPresenter mStaycationDetailPresenter;
    private boolean onRequestFormVisibility;
    private CurrentLocationDetail currentLocationDetail;
    private int pos = 0;
    public static int indexselected;
    AppBarLayout appBarLayout;
    Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_staycation_details);


        initialiseUI();
        packageDetailsNetworkCall();

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
    public void onBackPressed() {

        if (onRequestFormVisibility) {
            onRequestFormVisibility = false;
            (findViewById(R.id.requestFormStaycationDetails)).setVisibility(View.GONE);
        } else {
            finish();
        }


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


    private void initialiseUI() {

        spinningDialog = CustomProgressDialog.showProgressDialog(this);
        gtmAnalytics = AppController.getInstance().getGTMAnalytics(this);

        if(UserDTO.getUserDTO().getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE)) {
            toolbar = (Toolbar) findViewById(R.id.toolbarNew);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_white);

        }
        String lag = UserDTO.getUserDTO().getLanguage();

        if (Constant.ENGLISH_LANGUAGE_CODE.equals(lag)) {

            setLocale("en");
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        } else if (Constant.ARABIC_LANGUAGE_CODE.equals(lag)) {

            setLocale("ar");
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

            (findViewById(R.id.backImageView_Details)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gtmAnalytics.sendEvent("Getaway Details- " + packageName, "Back button", "Back button Click");
                    finish();
                }
            });
        }


        requestSuccessForm = (RelativeLayout) findViewById(R.id.requestSuccessFormStaycationDetails);
        getawayRecyclerView = (RecyclerView) findViewById(R.id.getawayRecyclerView);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        collapsingToolbar = (net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout)findViewById(R.id.appbar);
        overviewTextView = (TextView) findViewById(R.id.overviewTextView);
        overviewReadMoreTextView = (TextView) findViewById(R.id.overviewReadMoreTextView);
        // notificationTextView = (TextView) findViewById(R.id.notificationTextView);
        hotelPoliciesTextView = (TextView) findViewById(R.id.hotelPoliciesTextView);
        gridView = (AmenityGridView) findViewById(R.id.amenitiesGridView);
        nearAndAroundRecyclerView = (RecyclerView) findViewById(R.id.nearAndAroundRecyclerView);
        viewAllNearAroundTextView = (TextView) findViewById(R.id.viewAllNearAroundTextView);
        viewAllNearAroundTextView.setVisibility(View.GONE);
        policiesTextView = (TextView) findViewById(R.id.policiesTextView);
        ammenityShowLess=(TextView)findViewById(R.id.ammenityShowLess);
        titleTextView=(TextView)findViewById(R.id.titleTextView);
        alternatelyTextView=(TextView)findViewById(R.id.alternatelyTextView);
        reviewsScrollView = (com.holidayme.activities.util.NonScrollListView) findViewById(R.id.reviewsAdapterListView);

        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
            HolidayMeFont.overrideFonts(this, findViewById(R.id.bookNowButton), Constant.NotoKufiArabic_Regular);
        } else {
            HolidayMeFont.overrideFonts(this, findViewById(R.id.bookNowButton), Constant.NotoKufiArabic_Bold);

        }

        findViewById(R.id.bookNowButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nestedScrollView.smoothScrollTo(0, 1);
            }
        });

        findViewById(R.id.openRequestImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRequestForm();
            }
        });

        findViewById(R.id.closeTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestForm.setVisibility(View.GONE);
                onRequestFormVisibility = false;
            }
        });

        findViewById(R.id.closeSucessTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestSuccessForm.setVisibility(View.GONE);
                onRequestFormVisibility = false;
            }
        });

        viewAllNearAroundTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nearAndAroundBeanList.size() > 0) {
                    if (viewAllNearAroundTextView.getText().toString().equals("View All")) {
                        updateNearAndAroundAdapter(getNearAndAroundArray("more", nearAndAroundBeanList));
                        viewAllNearAroundTextView.setText("View Less");
                    } else {
                        updateNearAndAroundAdapter(getNearAndAroundArray("less", nearAndAroundBeanList));
                        viewAllNearAroundTextView.setText("View All");
                    }
                } else
                    viewAllNearAroundTextView.setVisibility(View.GONE);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                gtmAnalytics.sendEvent("Getaway Details- " + packageName, "Back button", "Back button Click");
                finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }


    //Package Details
    private void packageDetailsNetworkCall() {

        try {

            if (NetworkUtilities.isInternet(this)) {

                getCurrentLocationDetailByIp();

                if (mStaycationDetailPresenter == null)
                    mStaycationDetailPresenter = new StaycationDetailsPresenter(StaycationDetailsActivity.this, this);

                Intent intent = getIntent();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("CityId", intent.getLongExtra("cityId", 0));
                jsonObject.put("CurrencyCode", UserDTO.getUserDTO().getCurrency());
                jsonObject.put("IpAddress", NetworkUtilities.getIp());
                jsonObject.put("PackageId", intent.getLongExtra("packageId", 0));

                //((TextView) findViewById(R.id.distanceTextView)).setText("From " + intent.getStringExtra("location"));

                mStaycationDetailPresenter.getPackageDetails(Constant.GetawaysEndPointUrl+Constant.STAYCATION_PACKAGE_DETAILS + UserDTO.getUserDTO().getLanguage(),
                        jsonObject.toString(), this);
            } else
                Utilities.commonErrorMessage(this, getString(R.string.Network_not_avilable), getString(R.string.please_check_your_internet_connection), false, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPackageDetails(GetawayDetailBean getawayDetailBean) {
        spinningDialog.dismiss();
        if (getawayDetailBean != null) {
            packageName = getawayDetailBean.getTitle();
            gtmAnalytics.setScreenName("Getaway Details- " + packageName);
            collapsingToolbar(getawayDetailBean.getTitle(), getawayDetailBean.getAddress1(), getawayDetailBean.getListingImage(), getawayDetailBean.getDetailImages());
            header(getawayDetailBean.getDistance(), getawayDetailBean.getTripAdvisorRating(), getawayDetailBean.getStartRating());
            cancellationPolicy = getawayDetailBean.getTermsAndCondition();
            subPackages(getawayDetailBean.getSubPackagesList(), getawayDetailBean.getPackageId());
            description(getawayDetailBean.getDescription());

            if(getawayDetailBean.getImportantNotes() != null && getawayDetailBean.getImportantNotes().length() > 0)
            ((TextView) findViewById(R.id.importantTextView)).setText(Html.fromHtml(getawayDetailBean.getImportantNotes()));

            else
                findViewById(R.id.importantTitleTextView).setVisibility(View.GONE);

            amenitiesNetworkCall(getawayDetailBean.getAmeneties());


            nearAndAroundNetworkCall(Constant.API_URL+Constant.STAYCATION_NEAR_AND_AROUND + getawayDetailBean.getHotelId() + "&languageCode=" + UserDTO.getUserDTO().getLanguage());
            bookingPolicy = getawayDetailBean.getImportantNotes();

            if(getawayDetailBean.getHotelPolicy() != null && getawayDetailBean.getHotelPolicy().length() > 0)
            hotelPoliciesTextView.setText(Html.fromHtml(getawayDetailBean.getHotelPolicy()));

            else
                findViewById(R.id.hotelPoliciesTitleTextView).setVisibility(View.GONE);

            if(getawayDetailBean.getTermsAndCondition() != null && getawayDetailBean.getTermsAndCondition().length() > 0)
            policiesTextView.setText(Html.fromHtml(getawayDetailBean.getTermsAndCondition()));

            else
                (findViewById(R.id.policies)).setVisibility(View.GONE);

            tripAdvisorReviewsNetworkCall(Constant.API_URL + Constant.TRIPADVISORDATA + "?HotelId=" + getawayDetailBean.getHotelId() + "&languageCode=" + UserDTO.getUserDTO().getLanguage());
            subPackages(getawayDetailBean.getSubPackagesList(),getawayDetailBean.getPackageId());

        }
    }


    ImageLoader imageLoader = ImageLoader.getInstance();

    //Collapsable Toolbar Info
    private void collapsingToolbar(final String hotelName, String address, final String hotelImagePath, final String[] hotelAllImages) {

        this.hotelTitle = hotelName;
        this.hotelImageUrl = hotelImagePath;
       if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)){
           collapsingToolbar.setTitle(hotelName);
           titleTextView.setText(" ");
       }else {
           collapsingToolbar.setTitle(" ");
           titleTextView.setText(hotelName);
       }

       // collapsingToolbar.setTitle(" ");

       // collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.transparent));

        // Typeface typeface = Typeface.createFromAsset(getAssets(), "NotoKufiArabic-Regular.ttf");
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        if(UserDTO.getUserDTO().getLanguage().equals(Constant.ENGLISH_LANGUAGE_CODE)) {
            collapsingToolbar.setExpandedTitleTypeface(Typeface.createFromAsset(getAssets(), Constant.HelveticaNeueRoman));
            collapsingToolbar.setCollapsedTitleTypeface(Typeface.createFromAsset(getAssets(), Constant.HelveticaNeueRoman));
            ((TextView) findViewById(R.id.imageCountTextView)).setText("+" + hotelAllImages.length);
        }
        else
        {
            collapsingToolbar.setExpandedTitleTypeface(Typeface.createFromAsset(getAssets(), Constant.NotoKufiArabic_Regular));
            collapsingToolbar.setCollapsedTitleTypeface(Typeface.createFromAsset(getAssets(), Constant.NotoKufiArabic_Regular));
            ((TextView) findViewById(R.id.imageCountTextView)).setText(hotelAllImages.length + "+");
        }


        Glide.with(this)
                .load(hotelImagePath)
                .error(R.drawable.icn_default_image_loading)
                .placeholder(R.drawable.icn_default_image_loading)
                .into((ImageView) findViewById(R.id.backdropImageView));

        ((TextView) findViewById(R.id.addressTextView)).setText(" " + address);


        (findViewById(R.id.imageRelativeLayout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gtmAnalytics.sendEvent("Getaway Details- " + packageName, "Images", "Click on Images");

                ArrayList<ZoomInImagesDTO> zoomInImagesDTOArrayList = new ArrayList<>();
                for (int i = 0; i < hotelAllImages.length; i++) {
                    ZoomInImagesDTO bean = new ZoomInImagesDTO();
                    bean.setUrl(hotelAllImages[i]);
                    zoomInImagesDTOArrayList.add(bean);
                }

                if (zoomInImagesDTOArrayList.size() > 0) {
                    Intent intent = new Intent(StaycationDetailsActivity.this, ImageZoomInActivity.class);
                    intent.putExtra("ImagesDTO", zoomInImagesDTOArrayList);
                    intent.putExtra("ImagePos", 0);
                    startActivity(intent);
                }
            }
        });

        ammenityShowLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialCount=6;
                updateAdapter(0);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(hotelImagePath);
                    Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hotel);
                    Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 200, 300, true);
                    final int color = newBitmap.getPixel(0, 0);
                    newBitmap.recycle();

                    collapsingToolbar.setStatusBarScrimColor(color);
                    collapsingToolbar.setContentScrimColor(color);
                } catch (Exception e) {
                    collapsingToolbar.setStatusBarScrimColor(Color.GREEN);
                    e.printStackTrace();
                }
            }
        }).start();


    }

    // Distance,TripAdvisor and StarRating Info
    private void header(double distance, String tripAdvisorRatingUrl, float starRating) {

        try {
            ImageView reviewImageView = (ImageView) findViewById(R.id.reviewImageView);

            DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(0)
                    .showImageForEmptyUri(0).showImageOnFail(0).cacheInMemory(true)
                    .considerExifParams(true)
                    .build();

            imageLoader.displayImage(tripAdvisorRatingUrl, reviewImageView, displayImageOptions, null);

            // ((TextView) findViewById(R.id.distanceValueTextView)).setText(distance + " km");


            ((TextView) findViewById(R.id.ratingTextView)).setText("" + Math.round(starRating));
            //Utilities.setStarRating(starRating,this,findViewById(R.id.ratingBar));
            ((RatingBar) findViewById(R.id.ratingBar)).setRating(starRating);
        } catch (Exception e) {
            Log.i("Exception : ", "");
        }

    }

    // Sub Packages
    private void subPackages(ArrayList<GetawayDetailBean.SubPackageBean> subPackageBean, long packageId) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        getawayRecyclerView.setLayoutManager(linearLayoutManager);

        SubpackageAdapter getawayAdapter = new SubpackageAdapter(this, subPackageBean, packageId, hotelImageUrl, hotelTitle, bookingPolicy, packageName, gtmAnalytics,cancellationPolicy);
        getawayRecyclerView.setAdapter(getawayAdapter);
        getawayAdapter.notifyDataSetChanged();
    }

    // Hotel Description
    private void description(final String description) {

        if(description !=null && description.length() > 0) {

            if (description.length() > 250) {
                overviewTextView.setText(Html.fromHtml(description.substring(0, 250)));
            } else
                overviewReadMoreTextView.setVisibility(View.GONE);

            overviewReadMoreTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gtmAnalytics.sendEvent("Getaway Details- " + packageName, "Overview", "Overview");

                    if (overviewReadMoreTextView.getText().toString().equals(getResources().getString(R.string.read_more))) {
                        overviewTextView.setText(Html.fromHtml(description));
                        overviewReadMoreTextView.setText(getResources().getString(R.string.read_less));
                    } else {
                        overviewTextView.setText(Html.fromHtml(description.substring(0, 250)));
                        overviewReadMoreTextView.setText(getResources().getString(R.string.read_more));
                    }
                }
            });
        }
        else
        {
            findViewById(R.id.overviewTitleTextView).setVisibility(View.GONE);
            overviewReadMoreTextView.setVisibility(View.GONE);

        }
    }


    //Amenity bean
    public class AmenityRequest {
        Integer[] AmenityIds;
        String LanguageCode;

        public AmenityRequest(Integer[] amenityIds, String languageCode) {
            AmenityIds = amenityIds;
            LanguageCode = languageCode;
        }

        public void setAmenityArray(Integer[] amenityArray) {
            this.AmenityIds = amenityArray;
        }

        public String getLanguageCode() {
            return LanguageCode;
        }

        public void setLanguageCode(String languageCode) {
            LanguageCode = languageCode;
        }
    }

    // Amenities
    private void amenitiesNetworkCall(Integer[] AmenityIds) {
        try {

            if (NetworkUtilities.isInternet(this)) {
                AmenityRequest amenityRequest = new AmenityRequest(AmenityIds, UserDTO.getUserDTO().getLanguage());

                mStaycationDetailPresenter.getAmenitiesDetails(Constant.API_URL+Constant.STATCATION_AMENITIES,
                        new GsonBuilder().serializeNulls().create().toJson(amenityRequest), this);

            } else
                Utilities.commonErrorMessage(this, getString(R.string.Network_not_avilable), getString(R.string.please_check_your_internet_connection), false, null);

        } catch (Exception e) {

        }
    }

    @Override
    public void setAmenitiesDetails(AmenitiesDTO amenitiesDTO) {
        if (amenitiesDTO != null && amenitiesDTO.getAmenities() != null) {
            amenitiesDTOList.addAll(amenitiesDTO.getAmenities());
            updateAdapter(0);
        }
    }

    // Updating data of Amenity Adapter
    public void updateAdapter(int update) {

        initialCount = initialCount + update;

        if (amenitiesDTOList.size() <= initialCount) {
            initialCount = amenitiesDTOList.size();
            loadMore = false;
            if(amenitiesDTOList.size()>6)
            ammenityShowLess.setVisibility(View.VISIBLE);
            else
                ammenityShowLess.setVisibility(View.GONE);

        } else {
            loadMore = true;
            ammenityShowLess.setVisibility(View.GONE);

        }



        tempAmenitiesList.clear();
        for (int i = 0; i < initialCount; i++) {
            tempAmenitiesList.add(amenitiesDTOList.get(i));
        }

        AmenitiesGridViewAdapter adapter = new AmenitiesGridViewAdapter(this, tempAmenitiesList, loadMore);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    //Trip Advisor
    private void tripAdvisorReviewsNetworkCall(String url) {
        if (NetworkUtilities.isInternet(this))
            mStaycationDetailPresenter.getTripAdvisorDetails(url, this);

        else
            Utilities.commonErrorMessage(this, getString(R.string.Network_not_avilable), getString(R.string.please_check_your_internet_connection), false, null);

    }

    @Override
    public void setTripAdvisorDetails(TripAdviserDataResponse tripAdviserDataResponse) {
        if (tripAdviserDataResponse != null && tripAdviserDataResponse.getTripAdvisorDetail() != null && tripAdviserDataResponse.getTripAdvisorDetail().getReviews() != null
                && tripAdviserDataResponse.getTripAdvisorDetail().getReviews().size() > 0 )
            reviewData(tripAdviserDataResponse.getTripAdvisorDetail().getReviews());

        else
            (findViewById(R.id.reviewsTextView)).setVisibility(View.GONE);
    }

    //Reviews Adapter
    private void reviewData(ArrayList<ReviewsDto> reviewsArrayList) {
        TopReviewAdapter mTopReviewAdapter = new TopReviewAdapter(reviewsArrayList, this);
        reviewsScrollView.setAdapter(mTopReviewAdapter);
        mTopReviewAdapter.notifyDataSetChanged();
    }


    //Near and Around
    private void nearAndAroundNetworkCall(String url) {
        if (NetworkUtilities.isInternet(this))
            mStaycationDetailPresenter.getNearAndAroundDetails(url, this);

        else
            Utilities.commonErrorMessage(this, getString(R.string.Network_not_avilable), getString(R.string.please_check_your_internet_connection), false, null);

    }

    @Override
    public void setNearAndAround(NearandAroundBean nearandAroundBean) {
        if (nearandAroundBean.getFourSquarePoi() != null && nearandAroundBean.getFourSquarePoi().size() > 0) {

            for (NearandAroundBean.FourSquare bean : nearandAroundBean.getFourSquarePoi()) {
                if (bean.getCat() == 1 || bean.getCat() == 2 || bean.getCat() == 3) {
                    String s[] = bean.getDist().split(" ");
                    double result = Double.parseDouble((s[0]));
                    if(result<20.0)
                    nearAndAroundBeanList.add(bean);
                }
            }

            updateNearAndAroundAdapter(nearAndAroundBeanList);

            /*nearAndAroundBeanList.addAll(nearandAroundBean.getFourSquarePoi());

            if (nearandAroundBean.getFourSquarePoi().size() > 3) {
                viewAllNearAroundTextView.setVisibility(View.VISIBLE);
                updateNearAndAroundAdapter(getNearAndAroundArray("less", nearandAroundBean.getFourSquarePoi()));
            } else {
                viewAllNearAroundTextView.setVisibility(View.GONE);
                updateNearAndAroundAdapter(getNearAndAroundArray("more", nearandAroundBean.getFourSquarePoi()));
            }*/
        } else
            viewAllNearAroundTextView.setVisibility(View.GONE);
    }

    //Near and Around Adapter
    public void updateNearAndAroundAdapter(ArrayList<NearandAroundBean.FourSquare> nearAndAroundBeanList) {
        NearAndAroundAdapter nearAndAroundAdapter = new NearAndAroundAdapter(this, nearAndAroundBeanList);
        nearAndAroundRecyclerView.setAdapter(nearAndAroundAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        nearAndAroundRecyclerView.setLayoutManager(linearLayoutManager);

        nearAndAroundAdapter.notifyDataSetChanged();
    }

    // Near and Around View More and Less
    public ArrayList<NearandAroundBean.FourSquare> getNearAndAroundArray(String flag, ArrayList<NearandAroundBean.FourSquare> fourSquareArrayList) {

        if (flag.equals("less")) {
            ArrayList<NearandAroundBean.FourSquare> tempArrayList = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                tempArrayList.add(fourSquareArrayList.get(i));
            }
            return tempArrayList;


        } else
            return fourSquareArrayList;

    }


    // Common Error Listener
    public Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                if (spinningDialog.isShowing())
                    spinningDialog.dismiss();

                NetworkResponse response = volleyError.networkResponse;
                if (response != null && response.data != null) {
                    switch (response.statusCode) {
                        case 400:
                            String json = new String(response.data);
                            Toast.makeText(StaycationDetailsActivity.this, json, Toast.LENGTH_SHORT).show();
                            break;
                    }

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
                                indexselected = list.indexOf(currentLocationDetail.getCountry_name());
                            }
                        }


                    }
                }

            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void openRequestForm() {
        onRequestFormVisibility = true;
        requestForm = (RelativeLayout) findViewById(R.id.requestFormStaycationDetails);
        final Spinner countryCodeSpinner = (Spinner) requestForm.findViewById(R.id.county_code_spinner);
        final String[] countryCodesArray = new Utilities().getCountryCodes();

        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
            countryCodeSpinner.setAdapter(new CountryCodeCustomAdapter(this, R.layout.spinner_drop_down_row, new Utilities().getArabicCountryCode(), countryCodesArray));
        } else {
            countryCodeSpinner.setAdapter(new CountryCodeCustomAdapter(this, R.layout.spinner_drop_down_row, new Utilities().getEnglishCountryCode(), countryCodesArray));
        }
        countryCodeSpinner.setSelection(pos);
        requestForm.setVisibility(View.VISIBLE);

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
                new Utilities().contactUsDialog(StaycationDetailsActivity.this);

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

                                if (NetworkUtilities.isInternet(StaycationDetailsActivity.this)) {
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



                                    new FreshDeskAsyncTask(new GsonBuilder().serializeNulls().create().toJson(jsonObject),StaycationDetailsActivity.this).execute(new String[]{Constant.FreshDeskURl});

                                } else
                                    Utilities.commonErrorMessage(StaycationDetailsActivity.this, getString(R.string.Network_not_avilable), getString(R.string.please_check_your_internet_connection), false, null);


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
                    requestSuccessForm.setVisibility(View.VISIBLE);
                    alternatelyTextView.setText(String.format(getResources().getString(R.string.alternatively), getResources().getString(R.string.worldwide_toll_free_number)));

                    requestSuccessForm.findViewById(R.id.closeRequestButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onRequestFormVisibility = false;
                            requestSuccessForm.setVisibility(View.GONE);
                        }
                    });
                } else
                    Utilities.commonErrorMessage(StaycationDetailsActivity.this, getString(R.string.app_name), getString(R.string.common_error_msg), false, null);


            } catch (Exception e) {
                requestForm.setVisibility(View.GONE);
                Utilities.commonErrorMessage(StaycationDetailsActivity.this, getString(R.string.app_name), getString(R.string.common_error_msg), false, null);
                e.printStackTrace();
            }
        }
    }

}
