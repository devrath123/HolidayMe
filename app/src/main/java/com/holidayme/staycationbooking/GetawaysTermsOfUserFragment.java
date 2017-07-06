package com.holidayme.staycationbooking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.holidayme.activities.R;
import com.holidayme.fragments.BaseFragment;

/**
 * Created by arshad.shaikh on 4/3/2017.
 */

public class GetawaysTermsOfUserFragment extends BaseFragment {

    private View rootView;
    private String url;
    private ProgressBar progressBar;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        Bundle bundles = this.getArguments();

        if(bundles != null){
            url = bundles.getString("URL");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.terms_of_use_fragment, container, false);

        return rootView;
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initUIElements() {

        WebView webView = (WebView) rootView.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        webView.loadUrl(url);
        ((TextView)rootView.findViewById(R.id.toolbarTitleTextView)).setText(getActivity().getString(R.string.nav_item_terms_of_use));
        rootView.findViewById(R.id.toolbarBackImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);



        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

         /*   @Override
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
