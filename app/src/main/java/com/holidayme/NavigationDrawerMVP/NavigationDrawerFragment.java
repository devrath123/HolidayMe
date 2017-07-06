package com.holidayme.NavigationDrawerMVP;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.holidayme.Constants.Constant;
import com.holidayme.activities.HomeActivity;
import com.holidayme.activities.R;
import com.holidayme.activities.util.HolidayMeFont;
import com.holidayme.activities.util.Utilities;
import com.holidayme.adapter.ExpandableListAdapter;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.data.CurrencyObject;
import com.holidayme.data.Item;
import com.holidayme.data.UserDTO;
import com.holidayme.fragments.BaseFragment;
import com.holidayme.login_mvp.LoginWebViewActivity;
import com.holidayme.widgets.LightFontTextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by santosh.patar on 20-05-2015.
 */
public class NavigationDrawerFragment extends BaseFragment implements ExpandableListView.OnChildClickListener, INavigationDrawerView {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private List<String> dataHeaderList;
    private ExpandableListView expandableListView;
    private HashMap<String, ArrayList<Item>> stringArrayListHashMap;
    private FragmentDrawerListener drawerListener;
    private RadioGroup radioGroup;
    private TextView englishTextView, arabicTextView, currencyTextView, logInInstructionTextView, languageSelectedTextView, languageLabelTextView, currencyLabelTextView, logOutButton, sarTextView, aedTextView, usdTextView, userNameTextView;
    private RadioButton indexRadioButton, myProfileRadioButton, myBookingsRadioButton, contactUsRadioButton, termsOfUseRadioButton, privacyPolicyRadioButton;
    private View englishSelectedView, languageSelectedView, currencyView, rootView, lastDividerView;
    private ImageView languageImageView, currencyImageView, loginImageView;
    private LinearLayout languageSectionLinearLayout, currencySelectionLinearLayout, logOutLinearLayout;
    private RelativeLayout languageRelativeLayout, currencyRelativeLayout, loginRelativeLayout, drawerRelativeLayout;
    NavigationDrawerPresenter navigationDrawerPresenter;
    private  Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (UserDTO.getUserDTO() != null) {
            if (UserDTO.getUserDTO().getLanguage() != null) {
                if (UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE))
                    NetworkUtilities.setLocale(Constant.ARABIC_LANGUAGE_CODE, getActivity());
                else
                    NetworkUtilities.setLocale(Constant.ENGLISH_LANGUAGE_CODE, getActivity());

            }
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_drawer_new, container, false);

        navigationDrawerPresenter = new NavigationDrawerPresenter(this,getActivity());
     //   toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        return rootView;
    }

    public void setUp(int fragmentId, final DrawerLayout drawerLayout, final Toolbar toolbar) {
        this.drawerLayout = drawerLayout;
        this.toolbar=toolbar;
        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
                setDisplayName();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);

        if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)){

            toolbar.setNavigationIcon(R.drawable.hamburger_menu);

        }
        else{
            toolbar.setNavigationIcon(R.drawable.hamburger_menu2);
        }


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)){

                    drawerLayout.openDrawer(Gravity.RIGHT);

                }

                else{

                    drawerLayout.openDrawer(Gravity.LEFT);

                }
            }
        });

        this.drawerLayout.setDrawerListener(actionBarDrawerToggle);
        this.drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                actionBarDrawerToggle.syncState();
            }
        });

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }


    private void setDisplayName() {

        if (UserDTO.getUserDTO().getUserName() == null || UserDTO.getUserDTO().getUserName().equals("") || UserDTO.getUserDTO().getUserName().isEmpty()) {
            logInInstructionTextView.setText(getActivity().getResources().getString(R.string.tap_here_to_login));
            userNameTextView.setText(getActivity().getString(R.string.welcome_guest));
            logOutLinearLayout.setVisibility(View.GONE);
        } else {
            if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
                userNameTextView.setGravity(Gravity.END);
                logInInstructionTextView.setPadding(0,0,0,0);
                logInInstructionTextView.setGravity(Gravity.END);
            } else {
                userNameTextView.setGravity(Gravity.START);
                logInInstructionTextView.setPadding(0,10,0,0);
                logInInstructionTextView.setGravity(Gravity.START);
            }
            logInInstructionTextView.setText(UserDTO.getUserDTO().getEmailID());
            userNameTextView.setText(UserDTO.getUserDTO().getUserName());
            logOutLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initUIElements() {
        radioGroup = (RadioGroup) rootView.findViewById(R.id.radioGroup);
        indexRadioButton = (RadioButton) rootView.findViewById(R.id.indexRadioButton);
        drawerRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.drawerRelativeLayout);
        myProfileRadioButton = (RadioButton) rootView.findViewById(R.id.myProfileRadioButton);
        myBookingsRadioButton = (RadioButton) rootView.findViewById(R.id.myBookingsRadioButton);
        contactUsRadioButton = (RadioButton) rootView.findViewById(R.id.contactUsRadioButton);
        termsOfUseRadioButton = (RadioButton) rootView.findViewById(R.id.termsOfUseRadioButton);
        privacyPolicyRadioButton = (RadioButton) rootView.findViewById(R.id.privacyPolicyRadioButton);
        lastDividerView = rootView.findViewById(R.id.lastDividerView);
        loginImageView = (ImageView) rootView.findViewById(R.id.loginImageView);
        loginRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.loginRelativeLayout);
        logOutLinearLayout = (LinearLayout) rootView.findViewById(R.id.logOutLinearLayout);
        logOutButton = (TextView) rootView.findViewById(R.id.logOutButton);
        userNameTextView = (TextView) rootView.findViewById(R.id.userNameTextView);
        languageLabelTextView = (LightFontTextView) rootView.findViewById(R.id.languageLabelTextView);
        currencyLabelTextView = (LightFontTextView) rootView.findViewById(R.id.currencyLabelTextView);
        logInInstructionTextView = (TextView) rootView.findViewById(R.id.logInInstructionTextView);
        sarTextView = (TextView) rootView.findViewById(R.id.sarTextView);
        aedTextView = (TextView) rootView.findViewById(R.id.aedTextView);
        usdTextView = (TextView) rootView.findViewById(R.id.usdTextView);
        languageRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.englishRelativeLayout);
        currencyRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.currencyRelativeLayout);
        languageSectionLinearLayout = (LinearLayout) rootView.findViewById(R.id.languageSectionLinearLayout);
        currencySelectionLinearLayout = (LinearLayout) rootView.findViewById(R.id.currencySelectionLinearLayout);
        englishSelectedView = rootView.findViewById(R.id.englishSelectedView);
        languageSelectedView = rootView.findViewById(R.id.languageSelectedView);
        currencyView = rootView.findViewById(R.id.currencyView);
        englishTextView = (TextView) rootView.findViewById(R.id.englishTextView);
        arabicTextView = (TextView) rootView.findViewById(R.id.arabicTextView);
        languageSelectedTextView = (TextView) rootView.findViewById(R.id.languageSelectedTextView);
        languageImageView = (ImageView) rootView.findViewById(R.id.languageImageView);
        currencyImageView = (ImageView) rootView.findViewById(R.id.currencyImageView);
        currencyTextView = (TextView) rootView.findViewById(R.id.currencyTextView);
        expandableListView = (ExpandableListView) rootView.findViewById(R.id.expandableListView);

        setOnClickListenerTag();
        setDisplayName();
        setFont();

        updateButtonsText();

        currencyTextView.setText(UserDTO.getUserDTO().getCurrency());

        if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)){

            loginImageView.setImageResource(R.drawable.login_ar);
        }
        else{

            loginImageView.setImageResource(R.drawable.login);
        }

        uIFunctionality();

    }

    private void setOnClickListenerTag() {

        indexRadioButton.setTag(0);
        myProfileRadioButton.setTag(1);
        myBookingsRadioButton.setTag(2);
        contactUsRadioButton.setTag(3);
        termsOfUseRadioButton.setTag(4);
        privacyPolicyRadioButton.setTag(5);

        logOutLinearLayout.setOnClickListener(logout);
        loginRelativeLayout.setOnClickListener(login);
        indexRadioButton.setOnClickListener(menuItemSelected);
        myProfileRadioButton.setOnClickListener(menuItemSelected);
        myBookingsRadioButton.setOnClickListener(menuItemSelected);
        contactUsRadioButton.setOnClickListener(menuItemSelected);
        termsOfUseRadioButton.setOnClickListener(menuItemSelected);
        privacyPolicyRadioButton.setOnClickListener(menuItemSelected);
        sarTextView.setOnClickListener(popularCurrencySelected);
        aedTextView.setOnClickListener(popularCurrencySelected);
        usdTextView.setOnClickListener(popularCurrencySelected);
    }

    private void setFont() {
        if (UserDTO.getUserDTO()!=null&&UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {

            HolidayMeFont.overrideFonts(getActivity(), indexRadioButton, Constant.NotoKufiArabic_Bold);
            HolidayMeFont.overrideFonts(getActivity(), myProfileRadioButton, Constant.NotoKufiArabic_Bold);
            HolidayMeFont.overrideFonts(getActivity(), myBookingsRadioButton, Constant.NotoKufiArabic_Bold);
            HolidayMeFont.overrideFonts(getActivity(), contactUsRadioButton, Constant.NotoKufiArabic_Bold);
            HolidayMeFont.overrideFonts(getActivity(), termsOfUseRadioButton, Constant.NotoKufiArabic_Bold);
            HolidayMeFont.overrideFonts(getActivity(), privacyPolicyRadioButton, Constant.NotoKufiArabic_Bold);
            HolidayMeFont.overrideFonts(getActivity(), languageLabelTextView, Constant.NotoKufiArabic_Regular);
            HolidayMeFont.overrideFonts(getActivity(), currencyLabelTextView, Constant.NotoKufiArabic_Regular);
            if (UserDTO.getUserDTO().getUserName() == null || UserDTO.getUserDTO().getUserName().equals("") || UserDTO.getUserDTO().getUserName().isEmpty()){
            HolidayMeFont.overrideFonts(getActivity(), logInInstructionTextView, Constant.NotoKufiArabic_Regular);
                HolidayMeFont.overrideFonts(getActivity(), userNameTextView, Constant.NotoKufiArabic_Regular);
            }else{
                HolidayMeFont.overrideFonts(getActivity(), logInInstructionTextView, Constant.HelveticaNeueLight);
                HolidayMeFont.overrideFonts(getActivity(), userNameTextView, Constant.HelveticaNeueRoman);
            }


        } else {
            HolidayMeFont.overrideFonts(getActivity(), indexRadioButton, Constant.HelveticaNeueRoman);
            HolidayMeFont.overrideFonts(getActivity(), myProfileRadioButton, Constant.HelveticaNeueRoman);
            HolidayMeFont.overrideFonts(getActivity(), myBookingsRadioButton, Constant.HelveticaNeueRoman);
            HolidayMeFont.overrideFonts(getActivity(), contactUsRadioButton, Constant.HelveticaNeueRoman);
            HolidayMeFont.overrideFonts(getActivity(), termsOfUseRadioButton, Constant.HelveticaNeueRoman);
            HolidayMeFont.overrideFonts(getActivity(), privacyPolicyRadioButton, Constant.HelveticaNeueRoman);
            HolidayMeFont.overrideFonts(getActivity(), languageLabelTextView, Constant.HelveticaNeueLight);
            HolidayMeFont.overrideFonts(getActivity(), currencyLabelTextView, Constant.HelveticaNeueLight);
            HolidayMeFont.overrideFonts(getActivity(), logInInstructionTextView, Constant.HelveticaNeueLight);
            HolidayMeFont.overrideFonts(getActivity(), userNameTextView, Constant.HelveticaNeueRoman);
        }
    }

    private void uIFunctionality() {
        Bitmap bitmap = Utilities.createBitmap_ScriptIntrinsicBlur(BitmapFactory.decodeResource(getResources(), R.drawable.bg2), 0, getActivity());
        drawerRelativeLayout.setBackground(new BitmapDrawable(getResources(), bitmap));

        languageRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (languageImageView.getTag() == null) {
                    closeCurrencySection();
                    openLanguageSection();
                } else {
                    closeCurrencySection();
                    closeLanguageSection();
                }
            }
        });

        currencyRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currencyImageView.getTag() == null) {
                    prepareListData();
                    expandableListView.setAdapter(new ExpandableListAdapter(getActivity().getApplication(), dataHeaderList, stringArrayListHashMap));
                    expandableListView.setOnChildClickListener(NavigationDrawerFragment.this);
                    closeLanguageSection();
                    openCurrencySection();
                } else {
                    closeCurrencySection();
                    closeLanguageSection();
                }
            }
        });
        englishTextView.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                closeLanguageSection();
                closeCurrencySection();
                navigationDrawerPresenter.setLanguage(Constant.ENGLISH_LANGUAGE_CODE);
                languageSelectedTextView.setText(getActivity().getString(R.string.EN));
                Intent localBroadcastIntent = new Intent(Constant.REFRESH_FRAGMENT);
                LocalBroadcastManager myLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
                myLocalBroadcastManager.sendBroadcast(localBroadcastIntent);
                getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                userNameTextView.setGravity(Gravity.START);
                logInInstructionTextView.setPadding(0,10,0,0);
                logInInstructionTextView.setGravity(Gravity.START);
                setFont();
                updateButtonsText();


                actionBarDrawerToggle.setDrawerIndicatorEnabled(false);

                toolbar.setNavigationIcon(R.drawable.hamburger_menu2);

                loginImageView.setImageResource(R.drawable.login);






            }
        });
        arabicTextView.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                closeLanguageSection();
                closeCurrencySection();
                navigationDrawerPresenter.setLanguage(Constant.ARABIC_LANGUAGE_CODE);
                languageSelectedTextView.setText(getActivity().getString(R.string.AR));
                Intent localBroadcastIntent = new Intent(Constant.REFRESH_FRAGMENT);
                LocalBroadcastManager myLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
                myLocalBroadcastManager.sendBroadcast(localBroadcastIntent);
                getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                userNameTextView.setGravity(Gravity.END);
                logInInstructionTextView.setPadding(0,0,0,0);
                logInInstructionTextView.setGravity(Gravity.END);
                setFont();
                updateButtonsText();

                actionBarDrawerToggle.setDrawerIndicatorEnabled(false);

                toolbar.setNavigationIcon(R.drawable.hamburger_menu);

                loginImageView.setImageResource(R.drawable.login_ar);



            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        // ListView Group expanded listener
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                // Collapse previous parent if expanded.
                if ((previousGroup != -1) && (groupPosition != previousGroup))
                    expandableListView.collapseGroup(previousGroup);

                previousGroup = groupPosition;
                if (groupPosition == dataHeaderList.size() - 1)
                    lastDividerView.setVisibility(View.GONE);

            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                if (groupPosition == dataHeaderList.size() - 1)
                    lastDividerView.setVisibility(View.VISIBLE);

            }
        });

    }

    private View.OnClickListener popularCurrencySelected = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            TextView textView = (TextView) v;
            currencyTextView.setText(textView.getText().toString().trim().toUpperCase());
            navigationDrawerPresenter.setCurrency(textView.getText().toString().trim().toUpperCase());
            closeLanguageSection();
            closeCurrencySection();
        }
    };


    private View.OnClickListener menuItemSelected = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            drawerListener.onDrawerItemSelected(v, (int) v.getTag(), Constant.NAVIGATIONDRAWER);
            drawerLayout.closeDrawers();
        }
    };


    public interface FragmentDrawerListener {
        void onDrawerItemSelected(View view, int position, int Typr);
    }

    @Override
    public void onStop() {
        super.onStop();
        drawerLayout.closeDrawers();
    }


    private void prepareListData() {
        dataHeaderList = new ArrayList<>();

        dataHeaderList.add(getActivity().getString(R.string.AFRICA_MIDDLE_EAST));
        dataHeaderList.add(getActivity().getString(R.string.Asia_Pacific));
        dataHeaderList.add(getActivity().getString(R.string.EUROPE));
        dataHeaderList.add(getActivity().getString(R.string.AMERICAS));

        stringArrayListHashMap = Utilities.getDrawerCurrencyList(getActivity());


    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {
        parent.collapseGroup(groupPosition);
        CurrencyObject currencyObject = (CurrencyObject) stringArrayListHashMap.get(dataHeaderList.get(groupPosition)).get(childPosition);
        currencyTextView.setText(currencyObject.getLanguageCode());
        navigationDrawerPresenter.setCurrency(currencyObject.getLanguageCode());
        closeCurrencySection();
        return true;
    }

    private void openLanguageSection() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        layoutParams.addRule(RelativeLayout.BELOW, R.id.languageSectionLinearLayout);
        layoutParams.setMargins(0, 0, 0, 0);
        currencyView.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams1.addRule(RelativeLayout.BELOW, R.id.currencyView);
        radioGroup.setLayoutParams(layoutParams1);

        languageSectionLinearLayout.setVisibility(View.VISIBLE);
        englishSelectedView.setVisibility(View.VISIBLE);
        currencyView.setVisibility(View.VISIBLE);
        languageImageView.setImageResource(R.drawable.arrow_pink_d);
        languageSelectedTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
        languageImageView.setTag(1);
    }

    private void closeLanguageSection() {
        languageSectionLinearLayout.setVisibility(View.GONE);
        englishSelectedView.setVisibility(View.GONE);
        languageImageView.setImageResource(R.drawable.arrow_d);
        languageSelectedTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
        languageImageView.setTag(null);
        currencyView.setVisibility(View.GONE);
    }

    private void openCurrencySection() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        layoutParams.addRule(RelativeLayout.BELOW, R.id.currencySelectionLinearLayout);
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        layoutParams.setMargins(margin, 0, margin, 0);
        currencyView.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams1.addRule(RelativeLayout.BELOW, R.id.lastDividerView);
        radioGroup.setLayoutParams(layoutParams1);

        lastDividerView.setVisibility(View.VISIBLE);
        currencySelectionLinearLayout.setVisibility(View.VISIBLE);
        languageSelectedView.setVisibility(View.VISIBLE);
        currencyView.setVisibility(View.VISIBLE);
        expandableListView.setVisibility(View.VISIBLE);
        currencyImageView.setImageResource(R.drawable.arrow_pink_d);
        currencyTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
        currencyImageView.setTag(1);
    }

    private void closeCurrencySection() {
        lastDividerView.setVisibility(View.GONE);
        currencySelectionLinearLayout.setVisibility(View.GONE);
        languageSelectedView.setVisibility(View.GONE);
        expandableListView.setVisibility(View.GONE);
        currencyImageView.setImageResource(R.drawable.arrow_d);
        currencyTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
        currencyImageView.setTag(null);
        currencyView.setVisibility(View.GONE);
    }
    @SuppressLint("SetTextI18n")
    private void updateButtonsText() {
        indexRadioButton.setText(getActivity().getString(R.string.nav_item_home));
        myProfileRadioButton.setText(getActivity().getString(R.string.nav_item_profile));
        myBookingsRadioButton.setText(getActivity().getString(R.string.nav_item_booking));
        contactUsRadioButton.setText(getActivity().getString(R.string.nav_item_contact_us));
        termsOfUseRadioButton.setText(getActivity().getString(R.string.nav_item_terms_of_use));
        privacyPolicyRadioButton.setText(getActivity().getString(R.string.nav_item_privacy_policy));
        logInInstructionTextView.setText(getActivity().getString(R.string.tap_here_to_login) + "");
        logOutButton.setText(getActivity().getString(R.string.log_out));
        currencyLabelTextView.setText(getActivity().getString(R.string.currency));
        languageLabelTextView.setText(getActivity().getString(R.string.laguage));
/*
        loginImageView.setImageDrawable(null);
        loginImageView.setImageResource(R.drawable.login);
*/

        indexRadioButton.setBackgroundResource(R.color.transparent);
        indexRadioButton.setBackgroundResource(R.drawable.btn_selector);
        myProfileRadioButton.setBackgroundResource(R.color.transparent);
        myProfileRadioButton.setBackgroundResource(R.drawable.btn_selector);
        myBookingsRadioButton.setBackgroundResource(R.color.transparent);
        myBookingsRadioButton.setBackgroundResource(R.drawable.btn_selector);
        contactUsRadioButton.setBackgroundResource(R.color.transparent);
        contactUsRadioButton.setBackgroundResource(R.drawable.btn_selector);
        termsOfUseRadioButton.setBackgroundResource(R.color.transparent);
        termsOfUseRadioButton.setBackgroundResource(R.drawable.btn_selector);
        privacyPolicyRadioButton.setBackgroundResource(R.color.transparent);
        privacyPolicyRadioButton.setBackgroundResource(R.drawable.btn_selector);

        if (UserDTO.getUserDTO().getUserName() == null || UserDTO.getUserDTO().getUserName().equals("") || UserDTO.getUserDTO().getUserName().isEmpty()) {
            logInInstructionTextView.setText(getActivity().getResources().getString(R.string.tap_here_to_login) + "");
            userNameTextView.setText(getActivity().getString(R.string.welcome_guest));
            logOutLinearLayout.setVisibility(View.GONE);
        } else {
            logInInstructionTextView.setText(UserDTO.getUserDTO().getEmailID());
            userNameTextView.setText(UserDTO.getUserDTO().getUserName());
            logOutLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    public View.OnClickListener logout = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (NetworkUtilities.isInternet(getActivity())) {

                navigationDrawerPresenter.setUserInfo();

                drawerLayout.closeDrawers();

                Bundle bundle = new Bundle();
                bundle.putString("logout", "yes");
                traverseToLogin(bundle);
            } else {
                Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), false, getFragmentManager());
            }

        }
    };

    public View.OnClickListener login = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (NetworkUtilities.isInternet(getActivity())) {
                if (UserDTO.getUserDTO().getUserName() == null || UserDTO.getUserDTO().getUserName().equals("") || UserDTO.getUserDTO().getUserName().isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("ISFIRSTTIME", false);
                    traverseToLogin(bundle);
                }
            } else
                Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), false, getFragmentManager());


        }
    };



    public void notifySelecterChange(){

    }

    @Override
    public void traverseToLogin(Bundle bundle) {
        Intent intent = new Intent(getActivity(), LoginWebViewActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}


