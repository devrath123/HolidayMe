package com.holidayme.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.appevents.AppEventsLogger;
import com.holidayme.AppInterface.AutoCompleteClickListener;
import com.holidayme.AppInterface.BackPressInterFace;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.HolidayMeFont;
import com.holidayme.activities.util.SoftKeyboardStateWatcher;
import com.holidayme.activities.util.Utilities;
import com.holidayme.adapter.AutoCompleteSearchAdapter;
import com.holidayme.adapter.RecentSearchAdapter;
import com.holidayme.common.EventsToHandle;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.data.Destination;
import com.holidayme.data.UserDTO;
import com.holidayme.gps_location.GetLocationInfoFromGPS;
import com.holidayme.gtm.GTMAnalytics;
import com.holidayme.hotelsearch.HotelSearchPresenter;
import com.holidayme.hotelsearch.IUserLandingView;
import com.holidayme.managers.DatabaseManager;
import com.holidayme.widgets.CustomDialog;
import com.holidayme.widgets.CustomeDailogWithTwoButtons;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaikh.salim on 3/10/2016.
 */
public class AutoCompleteFragment extends BaseFragment implements AutoCompleteClickListener, SoftKeyboardStateWatcher.SoftKeyboardStateListener, View.OnClickListener, IUserLandingView, IUserLandingView.IAutoCompleteView {

    private View rootView;
    private ListView recentSearchListView;
    private EditText searchAutocompleteEditText;
    private ImageView clearTextImageView;
    private Activity activity;
    private Context context;
    private TextView cancelTextView;

    private AutoCompleteSearchAdapter autoCompleteSearchAdapter;
    private ProgressBar progressBar;
    private SoftKeyboardStateWatcher softKeyboardStateWatcher;
    private View autoCompleteDividerLineView;
    private RadioButton recentRadioButton, nearMeRadioButton;
    private GTMAnalytics gtmAnalytics;
    private RelativeLayout parentRelativeLayout;
    private String EVENT_CATEGORY = "Search hotel screen";
    private HotelSearchPresenter hotelSearchPresenter;
    private RecentSearchAdapter recentSearchAdapter;
    private GetLocationInfoFromGPS getLocationInfoFromGPS;
    private static final int gpsPermission = 1212;
    private boolean dialogFlag = true;
    private Button recentButton, nearMeButton;
    private boolean flag = true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.autocomplete, container, false);
        setHasOptionsMenu(true);
        if (hotelSearchPresenter == null)
            hotelSearchPresenter = new HotelSearchPresenter(this);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        ((AppCompatActivity) activity).getSupportActionBar().hide();
        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        gtmAnalytics = AppController.getInstance().getGTMAnalytics(getActivity());
        gtmAnalytics.setScreenName("Search hotel/City Screen");

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void initUIElements() {

        // initializing widgets.
        autoCompleteDividerLineView = rootView.findViewById(R.id.autoCompleteDividerLineView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        parentRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.parentRelativeLayout);
        cancelTextView = (TextView) rootView.findViewById(R.id.cancelTextView);
        searchAutocompleteEditText = (EditText) rootView.findViewById(R.id.searchAutocompleteEditText);
        clearTextImageView = (ImageView) rootView.findViewById(R.id.clearTextImageView);
        recentSearchListView = (ListView) rootView.findViewById(R.id.recentSearchListView);
        autoCompleteSearchAdapter = new AutoCompleteSearchAdapter(getActivity(), activity, AutoCompleteFragment.this, getFragmentManager());
        recentSearchListView.setAdapter(autoCompleteSearchAdapter);
        softKeyboardStateWatcher = new SoftKeyboardStateWatcher(rootView);

        nearMeButton = (Button) rootView.findViewById(R.id.nearMeButton);
        recentButton = (Button) rootView.findViewById(R.id.recentButton);
        searchAutocompleteEditText.requestFocus();

        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
            HolidayMeFont.overrideFonts(getActivity(), recentButton, Constant.NotoKufiArabic_Regular);
            HolidayMeFont.overrideFonts(getActivity(), nearMeButton, Constant.NotoKufiArabic_Regular);
        } else {
            HolidayMeFont.overrideFonts(getActivity(), recentButton, Constant.HelveticaNeueLight);
            HolidayMeFont.overrideFonts(getActivity(), nearMeButton, Constant.HelveticaNeueRoman);
        }


        recentButton.setOnClickListener(this);
        nearMeButton.setOnClickListener(this);


        // applying listeners click on widgets
        softKeyboardStateWatcher.addSoftKeyboardStateListener(this);
        clearTextImageView.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);

        autoCompleteSearchAdapter = new AutoCompleteSearchAdapter(getActivity(), activity, AutoCompleteFragment.this, getFragmentManager());
        recentSearchListView.setAdapter(autoCompleteSearchAdapter);

        ArrayList<Destination> destinationArrayList = DatabaseManager.getAllData(UserDTO.getUserDTO().getLanguage());
        setRecentSearchAdapter(destinationArrayList);

        setFonts();
        searchEditTextChanged();

    }

    private void searchEditTextChanged() {


        searchAutocompleteEditText.setFocusableInTouchMode(true);
        searchAutocompleteEditText.setFocusable(true);
        searchAutocompleteEditText.requestFocus();
        searchAutocompleteEditText.setInputType(InputType.TYPE_MASK_CLASS | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        NetworkUtilities.showSoftKeyboard(searchAutocompleteEditText, context);

        Bitmap bitmap = Utilities.createBitmap_ScriptIntrinsicBlur(BitmapFactory.decodeResource(getResources(), R.drawable.bg2), 0, getActivity());
        parentRelativeLayout.setBackground(new BitmapDrawable(getResources(), bitmap));

        searchAutocompleteEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                searchAutocompleteEditText.setFocusableInTouchMode(true);
                searchAutocompleteEditText.setFocusable(true);
                searchAutocompleteEditText.requestFocus();
                if (!softKeyboardStateWatcher.isSoftKeyboardOpened()) {

                    NetworkUtilities.showSoftKeyboard(searchAutocompleteEditText, context);
                }
                return false;
            }
        });


        searchAutocompleteEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                searchAutocompleteEditText.requestFocus();
                if (getLocationInfoFromGPS != null) {
                    getLocationInfoFromGPS.setFlagStatus(false);
                    if (progressBar != null && (progressBar.getVisibility() == View.VISIBLE))
                        progressBar.setVisibility(View.INVISIBLE);
                }
                if (!searchAutocompleteEditText.getText().toString().equals("")) {
                    if (s.length() > 2) {

                        autoCompleteDividerLineView.setVisibility(View.GONE);


                        recentSearchListView.setAdapter(autoCompleteSearchAdapter);
                        if (progressBar != null && (progressBar.getVisibility() == View.INVISIBLE))
                            progressBar.setVisibility(View.VISIBLE);
                        clearTextImageView.setVisibility(View.VISIBLE);
                        if (UserDTO.getUserDTO().getUserIP() == null || UserDTO.getUserDTO().getUserIP().equals("")) {
                            NetworkUtilities.getUserpublicIP(context);
                        }
                    } else {
                        if (progressBar != null && (progressBar.getVisibility() == View.VISIBLE))
                            progressBar.setVisibility(View.INVISIBLE);

                        autoCompleteDividerLineView.setVisibility(View.VISIBLE);

                        ArrayList<Destination> destinationArrayList = DatabaseManager.getAllData(UserDTO.getUserDTO().getLanguage());

                        setRecentSearchAdapter(destinationArrayList);
                    }
                } else {
                    ;
                    autoCompleteDividerLineView.setVisibility(View.VISIBLE);

                    clearTextImageView.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void afterTextChanged(final Editable s) {
                autoCompleteSearchAdapter.getFilter().filter(s.toString().toLowerCase(), new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int count) {
                        if (progressBar != null && (progressBar.getVisibility() == View.VISIBLE))
                            progressBar.setVisibility(View.INVISIBLE);
                    }
                });

            }
        });


    }

    private void setFonts() {

        // setting fonts.
        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
            HolidayMeFont.overrideFonts(getActivity(), recentRadioButton, Constant.NotoKufiArabic_Bold);
            HolidayMeFont.overrideFonts(getActivity(), nearMeRadioButton, Constant.NotoKufiArabic_Bold);
        } else {
            HolidayMeFont.overrideFonts(getActivity(), recentRadioButton, Constant.HelveticaNeueRoman);
            HolidayMeFont.overrideFonts(getActivity(), nearMeRadioButton, Constant.HelveticaNeueRoman);
        }

    }

    @Override
    public void selectedItem(Destination destination) {


        Constant.autoCompleteHandlerFlag = false;
        NetworkUtilities.hideSoftKeyboard(searchAutocompleteEditText, context);
        Intent intent = new Intent();
        intent.putExtra("data", destination);
        getTargetFragment().onActivityResult(1, 2, intent);
        getActivity().onBackPressed();
    }

    private Handler handlerForDynamicLocation = new Handler() {

        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            final Bundle bundle = message.getData();

            if (bundle != null && message.what == EventsToHandle.LATLONG_FOUND.ordinal()) {

                double latitude = bundle.getDouble("LATITUDE");
                double longitude = bundle.getDouble("LONGITUDE");
                UserDTO userDTO = UserDTO.getUserDTO();



                if(latitude!=0.0 && longitude!=0.0) {

                    userDTO.setLatitude(latitude);
                    userDTO.setLongitude(longitude);

                    getCityID(UserDTO.getUserDTO().getLatitude(), UserDTO.getUserDTO().getLongitude());

                }
                else{

                    getCityID(UserDTO.getUserDTO().getLatitude(), UserDTO.getUserDTO().getLongitude());
                }


            } else if (message.what == EventsToHandle.FAILURE.ordinal()) {

                if (progressBar != null && (progressBar.getVisibility() == View.VISIBLE))
                    progressBar.setVisibility(View.INVISIBLE);

                if (dialogFlag) {

                    Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.app_name), getActivity().getString(R.string.unable_to_fetch_your_current_location), false, getFragmentManager());

                }


            }

        }
    };
    private CustomDialog customDialog = null;

    // this methods used to get near location.
    private void getNearMeLocation() {

        if (NetworkUtilities.isInternet(context)) {

            if (UserDTO.getUserDTO().getUserIP() == null || UserDTO.getUserDTO().getUserIP().equals("")) {

                NetworkUtilities.getUserpublicIP(context);
            }
            if (gpsStatusWithDilogBoxLocal(getActivity(), getString(R.string.you_need_to_switch_on_your_location_settings_for_tab_search))) {

                if (progressBar != null && (progressBar.getVisibility() == View.INVISIBLE))
                    progressBar.setVisibility(View.VISIBLE);
                getLocationInfoFromGPS = new GetLocationInfoFromGPS(context, handlerForDynamicLocation);
                getLocationInfoFromGPS.getLocationInfo();

            }

        } else {
            if (customDialog == null)

                if (dialogFlag) {
                    Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), true, getFragmentManager());
                    dialogFlag = false;
                }

        }
    }

    // get device gps
    private CustomeDailogWithTwoButtons customeDailogWithTwoButtons = null;
    private LocationManager locationManager = null;

    private boolean gpsStatusWithDilogBoxLocal(final Context context, final String messagedialog) {

        if (locationManager == null)
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            if (customeDailogWithTwoButtons == null)
                customeDailogWithTwoButtons = new CustomeDailogWithTwoButtons(context, getString(R.string.app_name), messagedialog, getString(R.string.No), getString(R.string.yes));
            customeDailogWithTwoButtons.setCancelable(false);
            customeDailogWithTwoButtons.setDialogExitListener(new CustomeDailogWithTwoButtons.DialogExitListener() {
                @Override
                public void dialogExitWithDone() {

                    customeDailogWithTwoButtons.dismiss();
                    Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(callGPSSettingIntent, 1);

                    //   searchOptionRadioGroup.check(R.id.nearMeRadioButton);
                }

                @Override
                public void dialogExitWithDismissOrCancel() {
                    //  searchOptionRadioGroup.check(R.id.recentRadioButton);
                    autoCompleteSearchAdapter = new AutoCompleteSearchAdapter(getActivity(), activity, AutoCompleteFragment.this, getFragmentManager());
                    recentSearchListView.setAdapter(autoCompleteSearchAdapter);
                    ArrayList<Destination> destinationsArrayList = DatabaseManager.getAllData(UserDTO.getUserDTO().getLanguage());

                    shuffle(true);

                    setRecentSearchAdapter(destinationsArrayList);
                    customeDailogWithTwoButtons.dismiss();
                }
            });

            if (!customeDailogWithTwoButtons.isShowing() && dialogFlag)
                customeDailogWithTwoButtons.show();

            return false;
        } else {

            return true;
        }
    }


    private void validatePermission() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        gpsPermission);

            } else {
                navigateToNearLocation();
            }
        } else {
            navigateToNearLocation();

        }

    }


    private void navigateToNearLocation() {

        gtmAnalytics.sendEvent("Search hotel/City Screen", "Near me", "show near me cityTextView");

        Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "Near me", "show near me cityTextView");

        autoCompleteSearchAdapter = new AutoCompleteSearchAdapter(getActivity(), activity, AutoCompleteFragment.this, getFragmentManager());
        recentSearchListView.setAdapter(autoCompleteSearchAdapter);

        getNearMeLocation();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case gpsPermission: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    navigateToNearLocation();

                } else {

                    shuffle(true);

                }
                break;

            }

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (customDialog != null) {
            if (customDialog.isShowing()) {
                customDialog.dismiss();
                customDialog = null;
            }
        }
    }

    /**
     * <p> Get City id From Server by Api Call</p>
     */
    private void getCityID(double mLatitude, double mLogitudue) {


        if (Constant.autoCompleteHandlerFlag) {

            hotelSearchPresenter.getNearByCity(Constant.REGION_API_ENDPOINT + Constant.NEAR_ME_URL + UserDTO.getUserDTO().getLanguage() + "/" + UserDTO.getUserDTO().getLatitude() + "/" + UserDTO.getUserDTO().getLongitude(), getActivity());
        }

    }

    @Override
    public void onSoftKeyboardClosed() {

        // searchOptionRadioGroup.setVisibility(View.VISIBLE);
        clearTextImageView.setVisibility(View.INVISIBLE);
    //    searchAutocompleteEditText.clearFocus();
        autoCompleteDividerLineView.setVisibility(View.VISIBLE);
        //   searchOptionRadioGroup.check(R.id.recentRadioButton);


      //  ArrayList<Destination> destinationArrayList = DatabaseManager.getAllData(UserDTO.getUserDTO().getLanguage());
      //  setRecentSearchAdapter(destinationArrayList);


    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {
        searchAutocompleteEditText.requestFocus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (softKeyboardStateWatcher != null)
            softKeyboardStateWatcher.removeSoftKeyboardStateListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.clearTextImageView:

                clearText();
                break;

            case R.id.cancelTextView:

                dialogFlag = false;
                Constant.autoCompleteHandlerFlag = false;
                NetworkUtilities.hideSoftKeyboard(searchAutocompleteEditText, context);
                getActivity().onBackPressed();
                break;

            case R.id.recentButton:


                shuffle(true);

                gtmAnalytics.sendEvent("Search hotel/City Screen", "Recent searches", "Show recent searches");

                Utilities.pushFacebookEvent(AppEventsLogger.newLogger(getActivity()), EVENT_CATEGORY, "Recent searches", "Show recent searches");

                autoCompleteSearchAdapter = new AutoCompleteSearchAdapter(getActivity(), activity, AutoCompleteFragment.this, getFragmentManager());
                recentSearchListView.setAdapter(autoCompleteSearchAdapter);

                ArrayList<Destination> destinationArrayList = DatabaseManager.getAllData(UserDTO.getUserDTO().getLanguage());
                setRecentSearchAdapter(destinationArrayList);
                break;


            case R.id.nearMeButton:


                shuffle(false);

                validatePermission();

                dialogFlag = true;

                break;

        }
    }

    private void shuffle(boolean b) {
        if (flag && b) {
            flag = false;

            if (UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {

                recentButton.setBackground(getResources().getDrawable(R.drawable.tab_recent_selected_ar));
                nearMeButton.setBackground(getResources().getDrawable(R.drawable.tab_recent_unselected));
            } else {
                nearMeButton.setBackground(getResources().getDrawable(R.drawable.tab_recent_unselected));
                recentButton.setBackground(getResources().getDrawable(R.drawable.tab_recent_selected));

            }

            recentButton.setTextColor(getResources().getColor(R.color.white));
            nearMeButton.setTextColor(getResources().getColor(R.color.black));

        } else if (!b) {
            flag = true;

            if (UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {

                nearMeButton.setBackground(getResources().getDrawable(R.drawable.tab_near_selected_ar));
                recentButton.setBackground(getResources().getDrawable(R.drawable.tab_recent_unselected));

            } else {

                recentButton.setBackground(getResources().getDrawable(R.drawable.tab_recent_unselected));
                nearMeButton.setBackground(getResources().getDrawable(R.drawable.tab_near_selected));
            }


            recentButton.setTextColor(getResources().getColor(R.color.black));
            nearMeButton.setTextColor(getResources().getColor(R.color.white));
        }
    }

    // this method clears text of autocomplete ediText.
    private void clearText() {

        searchAutocompleteEditText.setText("");
        UserDTO.getUserDTO().setHotel(false);
        ArrayList<Destination> destinations = DatabaseManager.getAllData(UserDTO.getUserDTO().getLanguage());
        ;
        setRecentSearchAdapter(destinations);

        NetworkUtilities.showSoftKeyboard(searchAutocompleteEditText, context);
    }

    @Override
    public void hideShowProgressBar(int visibility) {

        switch (visibility) {
            case View.INVISIBLE:
                if (progressBar != null && (progressBar.getVisibility() == View.VISIBLE))
                    progressBar.setVisibility(visibility);
                break;

            case View.VISIBLE:
                if (progressBar != null && (progressBar.getVisibility() == View.INVISIBLE))
                    progressBar.setVisibility(visibility);
                break;
        }
    }

    @Override
    public void errorDialog(String title, String message) {

        final CustomDialog customDialog = new CustomDialog(getActivity(), title, message, getString(R.string.ok));
        customDialog.setDialogExitListener(new CustomDialog.DialogExitListener() {
            @Override
            public void dialogExitWithDone() {

            }

            @Override
            public void dialogExitWithDismissOrCancel() {
                customDialog.dismiss();
            }
        });
        if (!customDialog.isShowing()) {
            customDialog.show();
        }
    }

    // sets recent search data to listView.
    @Override
    public void setRecentSearchAdapter(List<Destination> destinationList) {

        recentSearchAdapter = new RecentSearchAdapter(context, AutoCompleteFragment.this, destinationList, false);
        recentSearchListView.setAdapter(recentSearchAdapter);
        recentSearchAdapter.notifyDataSetChanged();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        dialogFlag = false;

        if (customeDailogWithTwoButtons != null || customeDailogWithTwoButtons.isShowing()) {

            customeDailogWithTwoButtons.dismiss();
        }

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            getNearMeLocation();

        } else {

            if (customeDailogWithTwoButtons.isShowing())
                customeDailogWithTwoButtons.dismiss();
            //  searchOptionRadioGroup.check(R.id.recentRadioButton);

            shuffle(true);

            ArrayList<Destination> destinationsArrayList = DatabaseManager.getAllData(UserDTO.getUserDTO().getLanguage());
            setRecentSearchAdapter(destinationsArrayList);


        }
    }


}
