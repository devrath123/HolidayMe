package com.holidayme.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.util.Utilities;
import com.holidayme.booking_mvp.IBookingPaymentGatewayPresenter;
import com.holidayme.booking_mvp.IBookingView;
import com.holidayme.common.BookingStatusTypes;
import com.holidayme.common.CustomProgressDialog;
import com.holidayme.common.Log;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.common.TransactionStatusTypes;
import com.holidayme.data.UserDTO;
import com.holidayme.response.HotelBookConfirmationResponse;


import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

/**
 * Created by santosh.patar on 16-09-2015.
 */
public class BookingPaymentGatewayFragment extends BaseFragment implements IBookingView.IBookingPaymentGatewayView {

    private WebView webView;
    private Context context;
    private Dialog spinningDialog;
    private boolean isPop;
    private String html;
    private HotelBookConfirmationResponse hotelBookConfirmationResponse;
    //private BookingFailCustomDialog bookingFailCustomDialog;
    private IBookingPaymentGatewayPresenter iBookingPaymentGatewayPresenter;
    private ProgressBar progressBar;
    View rootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = this.getArguments();
        isPop = false;
        if (bundle != null) {
            html = bundle.getString("html");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (isPop) {
            getActivity().getSupportFragmentManager().popBackStack();
        }

        rootView = inflater.inflate(R.layout.booking_fragment, container, false);
        context = getActivity();

        iBookingPaymentGatewayPresenter = new IBookingPaymentGatewayPresenter(this);

      //  new Utilities().trustEveryone();

        DrawerLayout mDrawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        ((TextView) rootView.findViewById(R.id.headerTitleTextView)).setText(R.string.hotel_bookings);

        (rootView.findViewById(R.id.headerBackPressImageView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        webView = (WebView) rootView.findViewById(R.id.webView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                if(url.contains("Confirmation") || url.contains("bookingId")){
                    webView.stopLoading();
                    if (url.contains("=")) {
                        String[] token = url.split("=");
                        String propertyBookingId = token[2];
                        String bookingIdArray[] = token[1].split("&");
                        String bookingId = bookingIdArray[0];

                        Intent localBroadcastIntent = new Intent(Constant.MY_ACTION_CONFIRMATION);
                        localBroadcastIntent.putExtra("bookingid", bookingId);
                        Bundle bundle = new Bundle();
                        bundle.putString("bookingid", bookingId);
                        bundle.putString("propertyBookingId", propertyBookingId);

                        try {
                            if (NetworkUtilities.isInternet(getActivity())) {
                                spinningDialog = CustomProgressDialog.showProgressDialog(context);
                                //spinningDialog.show();

                                webView.setVisibility(WebView.INVISIBLE);

                                iBookingPaymentGatewayPresenter.getBookedHotelDetails(Constant.HOTEL_BOOKING_CONFORMATION_ENDPOINT + Constant.GETHOTELBOOKDETAILMETHOD + "?bookingId=" + bookingId + "&bookingPropertyId=" + propertyBookingId + "&currencyCode=" + UserDTO.getUserDTO().getCurrency(), getActivity());

                            } else
                                Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), true, getFragmentManager());
                        }catch (Exception ex)
                        {
                            ex.printStackTrace();
                            Log.i("Exception : ",ex.getMessage());
                        }
                    }
                }
            }

          /*  @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }*/
            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    if (url.contains(Constant.PaymentExceptionURL)) {
                        webView.stopLoading();

                        Fragment fragment = new HotelBookingConfirmationFragment();
                        Bundle bundle = new Bundle();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        bundle.putParcelable("hotelBookConfirmationResponse", null);
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.container_body, fragment);
                        isPop = true;
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commitAllowingStateLoss();

                        /*getTargetFragment().onActivityResult(5, 2, null);
                        getActivity().onBackPressed();*/
                        return false;
                    }
                    view.loadUrl(url);
                }catch (Exception e)
                {

                }
                return true;
            }
        });
        webView.loadData(html, "text/html", "UTF-8");

        return rootView;
    }

    @Override
    public void initUIElements() {
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
    }


    @Override
    public void setHotelBookingConfirmationResponse(HotelBookConfirmationResponse hotelBookConfirmationResponse) {

        if (hotelBookConfirmationResponse.getError() == null) {
            this.hotelBookConfirmationResponse = hotelBookConfirmationResponse;

            Fragment fragment = new HotelBookingConfirmationFragment();
            Bundle bundle = new Bundle();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            bundle.putParcelable("hotelBookConfirmationResponse", hotelBookConfirmationResponse);
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.container_body, fragment);
            isPop = true;
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commitAllowingStateLoss();

            /*if (bookingStatus()) {
                Fragment fragment = new BookingConfirmationFragment();
                Bundle bundle = new Bundle();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                bundle.putParcelable("hotelBookConfirmationResponse", hotelBookConfirmationResponse);
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container_body, fragment);
                isPop = true;
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commitAllowingStateLoss();
            }
            else {
                if (bookingFailCustomDialog == null)
                    bookingFailCustomDialog = new BookingFailCustomDialog(getActivity());

                bookingFailCustomDialog.setDialogExitListener(new BookingFailCustomDialog.DialogExitListener() {
                    @Override
                    public void dialogExitWithDone() {
                        getActivity().onBackPressed();
                    }

                    @Override
                    public void dialogExitWithDismissOrCancel() {
                        getActivity().onBackPressed();
                    }
                });
                bookingFailCustomDialog.show();
            }*/
        }
    }


    @Override
    public void showDialog() {

        if (spinningDialog != null && !spinningDialog.isShowing()) {
            spinningDialog.show();
        }
    }

    @Override
    public void dismissDialog() {
        if (spinningDialog != null && spinningDialog.isShowing())
            spinningDialog.dismiss();


    }


    private boolean bookingStatus() {
        boolean isCod = hotelBookConfirmationResponse.isCod();
        boolean isPayAtHotel = hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().isPayAtHotel();
        if (isPayAtHotel) {
            if (hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelBookingStatus() == BookingStatusTypes.Confirmed.getBookingStatusVal())
                return true;
        } else {
            if (isCod) {
                if (hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getTransactionStatusId() == TransactionStatusTypes.InProgress.getTransactionVal()) {
                    if (hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelBookingStatus() == BookingStatusTypes.PendingRequest.getBookingStatusVal()) {
                        return true;
                    }
                }
            } else {
                if (hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getTransactionStatusId() == TransactionStatusTypes.Successful.getTransactionVal()) {
                    if (hotelBookConfirmationResponse.getBookingDetails().getBookingEntity().getHotelBookingStatus() == BookingStatusTypes.Confirmed.getBookingStatusVal()) {
                        return true;
                    }
                }
            }

        }
        return false;
    }
}

