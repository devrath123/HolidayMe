package com.holidayme.login_mvp;


import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.holidayme.Constants.Constant;
import com.holidayme.activities.HomeActivity;
import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.HolidayMeFont;
import com.holidayme.activities.util.Utilities;
import com.holidayme.common.Log;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.data.UserDTO;
import com.holidayme.managers.SharedPreferenceManager;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;


public class LoginWebViewActivity extends AppCompatActivity implements ILoginView {

    private WebView webView;
    private String userToken = "";
    private boolean isFirstTime;
    private LoginPresenter loginPresenter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_web_view);

        ((TextView)findViewById(R.id.toolbarTitleTextView)).setText(getResources().getString(R.string.loging_header_text));
        findViewById(R.id.toolbarBackImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (findViewById(R.id.skipButton)).performClick();
            }
        });

        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
            HolidayMeFont.overrideFonts(LoginWebViewActivity.this, (findViewById(R.id.skipButton)), Constant.NotoKufiArabic_Bold);

        } else {
            HolidayMeFont.overrideFonts(LoginWebViewActivity.this, (findViewById(R.id.skipButton)), Constant.HelveticaNeueRoman);
        }
        loginPresenter = new LoginPresenter(LoginWebViewActivity.this, this);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        try {

            AppController.getInstance().getGTMAnalytics(LoginWebViewActivity.this).setScreenName("Login Screen");

            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                isFirstTime = bundle.getBoolean("ISFIRSTTIME");
            }

            (findViewById(R.id.skipButton)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isFirstTime) {
                        AppController.getInstance().getGTMAnalytics(LoginWebViewActivity.this).sendEvent("Login Screen", "skip-login", "skip login for now");
                        SharedPreferenceManager.getInstance(LoginWebViewActivity.this).saveBooleanPreference("isFirstTime", false);
                        Intent homeActivityIntent = new Intent(LoginWebViewActivity.this, HomeActivity.class);
                        startActivity(homeActivityIntent);
                        finish();

                    } else {
                        finish();
                    }

                }
            });

            /*
            * TO AVOID REJECTION FROM
            * GOOGLE PLAY STORE PLEASE
            * COMMENT trustEveryone() METHOD
            *
            */
          // new Utilities().trustEveryone();

            if (webView == null) {
                webView = (WebView) findViewById(R.id.loginWebView);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
                webView.getSettings().setDomStorageEnabled(true);

            }

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    Log.i("Response : ", url);
                   if (url.contains(Constant.LOGIN_RESPONSE_URL))
                    {
                        SharedPreferenceManager.getInstance(LoginWebViewActivity.this).saveBooleanPreference("isFirstTime", false);
                        try {
                            String[] array = url.split("\\?");
                            for (int i = 0; i < array.length; i++) {
                                if (array[i].contains("ut")) {
                                    String[] userTokenArray = array[i].split("&");
                                    for (int j = 0; j < userTokenArray.length; j++) {
                                        if (userTokenArray[j].contains("ut")) {
                                            String[] tokenArray = userTokenArray[j].split("=");
                                            for (int k = 0; k < tokenArray.length; k++) {
                                                userToken = tokenArray[k + 1];
                                                callUserDetails(userToken);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            Log.d("Exception : ", ex.getMessage());
                        }
                    }

                    if (url.equals(Constant.LOGIN_SUCCESS_CALLBACK_URL)) {
                        Intent homeActivityIntent = new Intent(LoginWebViewActivity.this, HomeActivity.class);
                        startActivity(homeActivityIntent);
                        finish();

                    } else if (url.equals(Constant.ENGLISH_TERMSOFUSE_URL) || url.equals(Constant.ARABIC_TERMSOFUSE_URL)) {

                        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE))
                            view.loadUrl(Constant.ARABIC_TERMSOFUSE_URL + "?IsMobile=true");

                        else
                            view.loadUrl(Constant.ENGLISH_TERMSOFUSE_URL + "?IsMobile=true");

                        return super.shouldOverrideUrlLoading(view, url);
                    }
                    else {
                        view.loadUrl(url);
                        return false;
                    }

                    return true;
                }

               @Override
                public void onReceivedSslError(WebView view,final SslErrorHandler handler, SslError error) {
                    handler.proceed();
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    progressBar.setVisibility(View.GONE);
                    super.onPageFinished(view, url);
                }
            });

            if (getIntent().getExtras().getString("logout") == null) {
                progressBar.setVisibility(View.VISIBLE);
                if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE))
                    webView.loadUrl(Constant.Login_Url_ar+Constant.ApplicationToken_new+Constant.Login_callBack_Url_ar);
                else
                    webView.loadUrl(Constant.Login_Url_en+Constant.ApplicationToken_new+Constant.Login_CallBack_Url_en);


            } else
                webView.loadUrl(Constant.LOGOUT_URL);

        } catch (Exception ex) {
            Log.i("Exception : ", ex.getMessage());
        }
    }


    /*
    * UNCOMMENT BELOW METHOD IN QA ENVIRONMENT
    *
    *
    *
    */

    private void callUserDetails(String userToken) {
        if(NetworkUtilities.isInternet(LoginWebViewActivity.this))
        loginPresenter.getUserDetails(LoginWebViewActivity.this, userToken);

        else
            Utilities.commonErrorMessage(this, this.getString(R.string.Network_not_avilable), this.getString(R.string.please_check_your_internet_connection), false,null);

    }

    @Override
    public void goToHomeScreen() {
        if (isFirstTime) {
            Intent homeActivityIntent = new Intent(LoginWebViewActivity.this, HomeActivity.class);
            startActivity(homeActivityIntent);
        } else {
            finish();
        }
    }


}
