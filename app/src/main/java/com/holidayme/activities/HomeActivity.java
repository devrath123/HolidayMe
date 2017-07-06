package com.holidayme.activities;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.holidayme.AppInterface.BackPressInterFace;
import com.holidayme.Constants.Constant;
import com.holidayme.NavigationDrawerMVP.NavigationDrawerFragment;
import com.holidayme.activities.util.HolidayMeFont;
import com.holidayme.activities.util.Utilities;
import com.holidayme.data.UserDTO;
import com.holidayme.fragments.ContactUsFragment;
import com.holidayme.fragments.HotelIndexFragment;
import com.holidayme.fragments.MyProfile;
import com.holidayme.fragments.PrivacyPolicyFragment;
import com.holidayme.fragments.TermsOfUseFragment;
import com.holidayme.holidaydetail_mvp.HolidayDetailFragment;
import com.holidayme.login_mvp.LoginWebViewActivity;
import com.holidayme.myBooking_mvp.MyBookingFragment;
import com.holidayme.staycationindex.StayCationIndexFragment;
import com.holidayme.widgets.CustomeDailogWithTwoButtons;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import info.semsamot.actionbarrtlizer.ActionBarRtlizer;
import info.semsamot.actionbarrtlizer.RtlizeEverything;


public class HomeActivity extends BaseActivity implements NavigationDrawerFragment.FragmentDrawerListener,View.OnClickListener {

    public static Toolbar toolbar;
    private UserDTO userDTO;
    public static Dialog spinningDialog;
    private BackPressInterFace backPressInterFace;
    private RelativeLayout bottomNavigationRelativeLayout;
    public  static   TextView hotelsTextView ,gatewaysTextView;
    public static boolean isGetaways;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_home);
        bottomNavigationRelativeLayout = (RelativeLayout) findViewById(R.id.bottomNavigation);

        initUI();

        getUserIP();
        new Utilities().getCurrentLocationDetailByIp();

        if(Constant.getawayActive){


            gatewaysTextView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.getaways_active,0,0);
            hotelsTextView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.hotel, 0, 0);

            gatewaysTextView.setTextColor(ContextCompat.getColor(HomeActivity.this,R.color.dark_gray_text_color));
            hotelsTextView.setTextColor(ContextCompat.getColor(HomeActivity.this,R.color.light_gray_text_color));


        }

        userDTO = UserDTO.getUserDTO();


        String lag = userDTO.getLanguage();

        if (Constant.ENGLISH_LANGUAGE_CODE.equals(lag)) {

            setLocale("en");
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        } else if (Constant.ARABIC_LANGUAGE_CODE.equals(lag)) {

            setLocale("ar");
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }


        ImageLoaderConfiguration config1 = new ImageLoaderConfiguration.Builder(this).memoryCacheExtraOptions(480, 800)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory().memoryCache(new LruMemoryCache(2 * 1024 * 1024)).memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .build();
        ImageLoader.getInstance().init(config1);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(getString(R.string.Hotel));

        hotelsTextView.setText(getString(R.string.Hotel));
        gatewaysTextView.setText(getString(R.string.getaways_title));
        HolidayMeFont.overrideFonts(this, mTitle, Constant.NotoKufiArabic_Regular);


        drawerFragment.notifySelecterChange();

        displayView(0);

        boolean isMyBooking = false;

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            isMyBooking = extras.getBoolean("IsMyBooking");

        if(isMyBooking){
            Fragment myBookingFragment  = new MyBookingFragment();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container_body, myBookingFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

    }
    private String generatePakageKey(){
        String key = "h:pkg:pkginfo:55#14#3#15-04-2017";
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(),"HmacSHA1");
        String base64="";

        try {
            byte[] data = key.getBytes("UTF-8");
         base64 = Base64.encodeToString(data, Base64.DEFAULT);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return base64;
    }
    private void initUI() {

         hotelsTextView = (TextView) findViewById(R.id.hotelsTextView);
         gatewaysTextView = (TextView) findViewById(R.id.gatewaysTextView);
        generatePakageKey();
        RelativeLayout hotelsRelativeLayout = (RelativeLayout) findViewById(R.id.hotelsRelativeLayout);
        RelativeLayout getawaysRelativeLayout = (RelativeLayout) findViewById(R.id.getawaysRelativeLayout);

        hotelsRelativeLayout.setOnClickListener(this);
        getawaysRelativeLayout.setOnClickListener(this);

        HolidayMeFont.overrideFonts(this, hotelsTextView, Constant.NotoKufiArabic_Regular);
        HolidayMeFont.overrideFonts(this, gatewaysTextView, Constant.NotoKufiArabic_Regular);



    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        ActionBarRtlizer actionBarRtlizer = new ActionBarRtlizer(this);
        ViewGroup actionBarView = actionBarRtlizer.getActionBarView();
        ViewGroup homeView = (ViewGroup) actionBarRtlizer.getHomeView();
        actionBarRtlizer.flipActionBarUpIconIfAvailable(homeView);
        RtlizeEverything.rtlize(actionBarView);
        RtlizeEverything.rtlize(homeView);
        return super.onPrepareOptionsMenu(menu);

    }


    @Override

    public void onDrawerItemSelected(View view, int position, int type) {

        if (type == Constant.NAVIGATIONDRAWER) {

            displayView(position);
        }
    }

    public void bottomNavigationVisibility(int visibility)
    {
        bottomNavigationRelativeLayout.setVisibility(visibility);
    }


    private void displayView(int position) {
        switch (position) {
            case 0:

                UserDTO.getUserDTO().setDrawerSelectedPosition(position);

                if(Constant.getawayActive||isGetaways){

                    manageFragment(new StayCationIndexFragment(), true, new StayCationIndexFragment().getClass().getName(), "Getaways");
                }
                else{

                    manageFragment(new HotelIndexFragment(), true, new HotelIndexFragment().getClass().getName(), getString(R.string.Hotel));

                }

                break;
            case 1:
                if (userDTO.getUserName() == null || userDTO.getUserName().equals("") || userDTO.getUserName().isEmpty()) {
                    final CustomeDailogWithTwoButtons customeDailogWithTwoButtons = new CustomeDailogWithTwoButtons(HomeActivity.this, getString(R.string.nav_item_profile), getString(R.string.Please_Login_First_profile), getString(R.string.No), getString(R.string.yes));
                    customeDailogWithTwoButtons.setDialogExitListener(new CustomeDailogWithTwoButtons.DialogExitListener() {
                        @Override
                        public void dialogExitWithDone() {
                            customeDailogWithTwoButtons.dismiss();
                            Intent intent = new Intent(HomeActivity.this, LoginWebViewActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("ISFIRSTTIME", false);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                        @Override
                        public void dialogExitWithDismissOrCancel() {
                            customeDailogWithTwoButtons.dismiss();
                        }
                    });
                    customeDailogWithTwoButtons.show();

                } else {
                    UserDTO.getUserDTO().setDrawerSelectedPosition(position);
                    manageFragment(new MyProfile(), true, new MyProfile().getClass().getName(), getString(R.string.My_profile));

                }
                break;
            case 2:
                if (userDTO.getUserName() == null || userDTO.getUserName().equals("") || userDTO.getUserName().isEmpty()) {
                    final CustomeDailogWithTwoButtons customeDailogWithTwoButtons = new CustomeDailogWithTwoButtons(HomeActivity.this, getString(R.string.nav_item_booking), getString(R.string.Please_Login_First_booking), getString(R.string.No), getString(R.string.yes));
                    customeDailogWithTwoButtons.setDialogExitListener(new CustomeDailogWithTwoButtons.DialogExitListener() {
                        @Override
                        public void dialogExitWithDone() {
                            customeDailogWithTwoButtons.dismiss();
                            Intent intent = new Intent(HomeActivity.this, LoginWebViewActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("ISFIRSTTIME", false);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                        @Override
                        public void dialogExitWithDismissOrCancel() {
                            customeDailogWithTwoButtons.dismiss();
                        }
                    });
                    customeDailogWithTwoButtons.show();
                } else {

                    UserDTO.getUserDTO().setDrawerSelectedPosition(position);
                    manageFragment(new MyBookingFragment(), true, new MyBookingFragment().getClass().getName(), getString(R.string.My_Booking));
                }
                break;
            case 3:
                UserDTO.getUserDTO().setDrawerSelectedPosition(position);
                manageFragment(new ContactUsFragment(), true, new ContactUsFragment().getClass().getName(), getString(R.string.nav_item_contact_us));
                break;

            case 4:
                UserDTO.getUserDTO().setDrawerSelectedPosition(position);
                Fragment fragment = new TermsOfUseFragment();
                Bundle bundle = new Bundle();
                if (userDTO.getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
                    bundle.putString("URL", Constant.ARABIC_TERMSOFUSE_URL+"?IsMobile=true");
                } else {
                    bundle.putString("URL", Constant.ENGLISH_TERMSOFUSE_URL+"?IsMobile=true");
                }
                fragment.setArguments(bundle);
                manageFragment(fragment, true, fragment.getClass().getName(), getString(R.string.Term_of_Use));

                break;

            case 5:
                UserDTO.getUserDTO().setDrawerSelectedPosition(position);
                Fragment privacyPolicyFragment = new PrivacyPolicyFragment();
                bundle = new Bundle();
                if (userDTO.getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
                    bundle.putString("URL", Constant.ARABIC_PRIVACYPOLICY_URL+"?IsMobile=true");
                } else {
                    bundle.putString("URL", Constant.ENGLISH_PRIVACYPOLICY_URL+"?IsMobile=true");
                }
                privacyPolicyFragment.setArguments(bundle);
                manageFragment(privacyPolicyFragment, true, new PrivacyPolicyFragment().getClass().getName(), getString(R.string.Privacy_policy));
                break;

            default:
                break;
        }
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

        Constant.autoCompleteHandlerFlag=false;

        if (backPressInterFace != null) {
            backPressInterFace.backPressCalled();
        }


        else {
            FragmentManager fragmentManager = this.getSupportFragmentManager();

            if (fragmentManager.getBackStackEntryCount() == 1) {
              DrawerLayout  drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawers();
                } else {
                    android.os.Process.killProcess(android.os.Process.myPid());
                  //  ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText(getString(R.string.Hotel));
                    android.support.v7.app.ActionBar actionBar = getSupportActionBar();
                    actionBar.hide();

                }
            } else if (findViewById(R.id.sortPanelLinearLayout) != null && findViewById(R.id.sortPanelLinearLayout).getVisibility() == View.VISIBLE) {
                findViewById(R.id.sortPanelLinearLayout).setVisibility(View.INVISIBLE);
               ((TextView) findViewById(R.id.sortingTextView)).setTextColor(getResources().getColor(R.color.save_pink));
                ((ImageView)findViewById(R.id.callImageView)).setVisibility(View.VISIBLE);
                ((ImageView)findViewById(R.id.filterFloatingActionButton)) .setVisibility(View.VISIBLE);
     //           ((TextView) findViewById(R.id.textSortByTextView)).setTextColor(getResources().getColor(R.color.white));

            }else if(findViewById(R.id.on_request_form)!=null&&findViewById(R.id.on_request_form).getVisibility()==View.VISIBLE||findViewById(R.id.on_request_success)!=null&&findViewById(R.id.on_request_success).getVisibility()==View.VISIBLE){
                findViewById(R.id.on_request_form).setVisibility(View.GONE);
                findViewById(R.id.on_request_success).setVisibility(View.GONE);
                findViewById(R.id.OnRequestActionButton).setVisibility(View.VISIBLE);
                findViewById(R.id.filterActionButton).setVisibility(View.VISIBLE);

            }else {
                super.onBackPressed();
            }
        }

    }

    private void getUserIP() {
        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();
                try {

                    URL ipURL = new URL("http://checkip.amazonaws.com/");
                    URLConnection connection = ipURL.openConnection();
                    connection.addRequestProperty("Protocol", "Http/1.1");
                    connection.addRequestProperty("Connection", "keep-alive");
                    connection.addRequestProperty("Keep-Alive", "1000");
                    connection.addRequestProperty("User-Agent", "Web-Agent");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String ipAddress = bufferedReader.readLine(); //you get the IP as a String
                    UserDTO.getUserDTO().setUserIP(ipAddress);
                    SharedPreferences UserInfo = getSharedPreferences(Constant.USERPREFERENCE, MODE_PRIVATE);
                    SharedPreferences.Editor preferencesEditer = UserInfo.edit();
                    preferencesEditer.putString("USERIP", ipAddress);

                    preferencesEditer.apply();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        t.start();
    }

    public void setBackPressListener(BackPressInterFace backPressInterFace) {
        this.backPressInterFace = backPressInterFace;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){



            case R.id.hotelsRelativeLayout:
               isGetaways=false;
                Fragment fragment= new HotelIndexFragment();
                setFragmentTransactions(fragment);
                hotelsTextView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.hotel_active, 0, 0);
                gatewaysTextView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.getaways,0,0);

                hotelsTextView.setTextColor(ContextCompat.getColor(HomeActivity.this,R.color.bottom_navigation_active_textcolor));
                gatewaysTextView.setTextColor(ContextCompat.getColor(HomeActivity.this,R.color.light_gray_text_color));

                break;

            case R.id.getawaysRelativeLayout:
               isGetaways=true;
                //Fragment fragmentStayCation= new StayCationIndexFragment();
                Fragment fragmentStayCation= new HolidayDetailFragment();
                setFragmentTransactions(fragmentStayCation);
                gatewaysTextView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.getaways_active,0,0);
                hotelsTextView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.hotel, 0, 0);

                gatewaysTextView.setTextColor(ContextCompat.getColor(HomeActivity.this,R.color.bottom_navigation_active_textcolor));
                hotelsTextView.setTextColor(ContextCompat.getColor(HomeActivity.this,R.color.light_gray_text_color));


                break;

        }

    }

    private void setFragmentTransactions(Fragment fragment) {

        try {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}