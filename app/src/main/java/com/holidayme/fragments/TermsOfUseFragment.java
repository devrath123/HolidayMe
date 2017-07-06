package com.holidayme.fragments;

/**
 * Created by supriya.sakore on 23-09-2015.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.common.Log;
import com.holidayme.gtm.GTMAnalytics;

/**
 * Created by santosh.patar on 16-09-2015.
 */
public class TermsOfUseFragment extends  BaseFragment {
    //http://stackoverflow.com/questions/14664363/webchromeclient-opens-link-in-browser
    private View rootView;
    private WebView webView;
    private String url;
    private ProgressBar progressBar;
    private GTMAnalytics gtmAnalytics;

    public static final String TAG = TermsOfUseFragment.class.getSimpleName();


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        Bundle bundles = this.getArguments();

        if (bundles != null) {
            url = bundles.getString("URL");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.terms_of_use_fragment, container, false);

        return rootView;
    }

    @Override
    public void initUIElements() {
        gtmAnalytics = AppController.getInstance().getGTMAnalytics(getActivity());
        gtmAnalytics.setScreenName("Terms of use Screen");


        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        DrawerLayout mDrawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        LinearLayout language_layout = (LinearLayout) mDrawer.findViewById(R.id.languageLinearLayout);
        View view = mDrawer.findViewById(R.id.topBarView);
        if (language_layout != null) {
            language_layout.setVisibility(View.GONE);
            view.setVisibility(View.GONE);


            webView = (WebView) rootView.findViewById(R.id.webView);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setGeolocationEnabled(true);
            webSettings.setSupportMultipleWindows(true);
            webView.loadUrl(url);
            ((TextView) rootView.findViewById(R.id.toolbarTitleTextView)).setText(getActivity().getString(R.string.nav_item_terms_of_use));
            rootView.findViewById(R.id.toolbarBackImageView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = getFragmentManager().getBackStackEntryCount();

                    if (count == 0) {

                        getActivity().onBackPressed();

                    } else {
                        getFragmentManager().popBackStack();
                    }
                }
            });
            progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);


            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

               /* @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    // super.onReceivedSslError(view, handler, error);
                    handler.proceed();
                }*/

                @Override
                public void onPageFinished(WebView view, String url) {

                    super.onPageFinished(view, url);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadResource(WebView view, String url) {
                    super.onLoadResource(view, url);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    progressBar.setVisibility(View.VISIBLE);

                    view.loadUrl(url);
                    return false;
                }


            });
        }

    }
}
