package com.holidayme.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.RegistrationIntentService;
import com.holidayme.data.Destination;
import com.holidayme.data.UserDTO;
import com.holidayme.managers.SharedPreferenceManager;
import com.holidayme.selectlanguage_mvp.SelectLanguageActivity;
import com.holidayme.splash_mvp.SplashPresenter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SplashScreenActivity extends BaseActivity {

    private final int SPLASH_TIME_OUT = 1500;
    private boolean isDeepLinked;
    String cityId = "", cityName = "", hotelId = "", campaignUrl = "";
    private Destination destination = null;
    private final String SPLASH_SCREEN = "Splash Screen", USER_IP = "USERIP";
    SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        //Start Registration Service to get Token and push to CleverTap
        Intent registrationIntent = new Intent(SplashScreenActivity.this, RegistrationIntentService.class);
        startService(registrationIntent);

        splashPresenter = new SplashPresenter();
        splashPresenter.setTrackingAndSessionId(this);


        Intent intent = getIntent();

        if (intent.getAction() == Intent.ACTION_VIEW) {

            Uri uri = intent.getData();

            if (uri != null) {
                if (uri.getQueryParameter("cityId") != null) {
                    cityId = uri.getQueryParameter("cityId");
                    cityName = uri.getQueryParameter("cityName");
                } else if (uri.getQueryParameter("hotelId") != null) {
                    hotelId = uri.getQueryParameter("hotelId");
                } else {
                    campaignUrl = intent.getDataString();
                }
            }
            isDeepLinked = true;
        } else {
            isDeepLinked = false;
        }


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                gtmAnalytics = AppController.getInstance().getGTMAnalytics(SplashScreenActivity.this);
                gtmAnalytics.setScreenName(SPLASH_SCREEN);
                if (!TextUtils.equals(campaignUrl, ""))
                    gtmAnalytics.setCampaignUrl(campaignUrl);

                if (isDeepLinked)
                    setDefault();

                else {
                    // First Time User
                    if (SharedPreferenceManager.getInstance(SplashScreenActivity.this).getBooleanPreference("isFirstTime",true)) {
                        getUserPublicIPAddress();
                        startActivity(new Intent(SplashScreenActivity.this, SelectLanguageActivity.class));
                        finish();

                    } else {
                        splashPresenter.setUserDTO(SplashScreenActivity.this);
                        startMainActivity(SplashScreenActivity.this, destination);
                    }
                }
            }
        }, SPLASH_TIME_OUT);
    }


    public void setDefault() {

        splashPresenter.setDeepLinkDefault(SplashScreenActivity.this);

        if (!TextUtils.equals(hotelId, "")) {
            destination = new Destination();
            destination.setCategory("Hotel");
            destination.setKey(hotelId);
            destination.setIsHotel(true);
        } else if (!TextUtils.equals(cityId, "") && !TextUtils.equals(cityName, "")) {
            destination = new Destination();
            destination.setCategory("City");
            destination.setKey(getOnlyDigits(cityId));
            destination.setIsCity(true);
            destination.setDestinationName(cityName);
    }
        startMainActivity(SplashScreenActivity.this, destination);
    }

    public String getOnlyDigits(String s) {
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(s);
        return matcher.replaceAll("");
    }


    // Get User IP Address
    public void getUserPublicIPAddress() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {

                    URL whatIsMyIP = new URL("http://checkip.amazonaws.com/");
                    URLConnection connection = whatIsMyIP.openConnection();
                    connection.addRequestProperty("Protocol", "Http/1.1");
                    connection.addRequestProperty("Connection", "keep-alive");
                    connection.addRequestProperty("Keep-Alive", "1000");
                    connection.addRequestProperty("User-Agent", "Web-Agent");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String ipAddress = bufferedReader.readLine();
                    UserDTO.getUserDTO().setUserIP(ipAddress);
                    SharedPreferenceManager.getInstance(SplashScreenActivity.this).savePreference(USER_IP, ipAddress);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }
}
