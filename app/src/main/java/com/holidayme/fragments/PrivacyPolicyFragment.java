package com.holidayme.fragments;

/**
 * Created by supriya.sakore on 23-09-2015.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.holidayme.activities.R;
import com.holidayme.common.Log;

/**
 * Created by santosh.patar on 16-09-2015.
 */
public class PrivacyPolicyFragment extends  BaseFragment {

    View rootView;
    WebView web;
    private String privacyPolicyURL;
    ProgressBar progressBar;

    public static final String TAG = PrivacyPolicyFragment.class
            .getSimpleName();


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        Bundle bundle = this.getArguments();

        if(bundle != null){
            privacyPolicyURL = bundle.getString("URL");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.privacy_policy_fragment, container, false);
        return rootView;
    }

    @Override
    public void initUIElements() {

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        DrawerLayout  mDrawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        LinearLayout language_layout = (LinearLayout)mDrawer.findViewById(R.id.languageLinearLayout);
        View view= mDrawer.findViewById(R.id.topBarView);
        if(language_layout!= null) {
            language_layout.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressbar);
        web=(WebView)rootView.findViewById(R.id.webView);
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        web.loadUrl(privacyPolicyURL);
        ((TextView)rootView.findViewById(R.id.toolbarTitleTextView)).setText(getActivity().getString(R.string.nav_item_privacy_policy));
        rootView.findViewById(R.id.toolbarBackImageView)
        .setOnClickListener(new View.OnClickListener() {
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
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                progressBar.setVisibility(View.VISIBLE);

                if (url.equals("mailto:care@holidayme.com")) {
                    view.loadUrl(privacyPolicyURL);
                    Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "care@holidayme.com"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "");
                    intent.putExtra(Intent.EXTRA_TEXT, "");
                    startActivity(intent);

                }
                    return super.shouldOverrideUrlLoading(view, url);
            }

        });
    }

}
