package com.holidayme.common;

/**
 * Created by santosh.patar on 06-08-2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.holidayme.Constants.Constant;
import com.holidayme.data.UserDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class NetworkUtilities {

    public static final int MY_SOCKET_TIMEOUT_MS = 1 * 90 * 1000; // m1.30min

    /**
     * Check Internet connection.
     */
    public static boolean isInternet(Context context) {
        boolean isInternet = false;

        ConnectivityManager con = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (con.getNetworkInfo(0) != null && con.getNetworkInfo(1) != null) {

            if (con.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
                    || con.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING
                    || con.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED
                    || con.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING) {
                isInternet = true;

            } else if (con.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
                    || con.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
               /* Toast.makeText(context, context.getString(R.string.no_neteork),
                        Toast.LENGTH_LONG).show();*/
                isInternet = false;

            }
        } else if (con.getNetworkInfo(0) != null) {

            if (con.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
                    || con.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING) {
                isInternet = true;

            } else if (con.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED) {
                /*Toast.makeText(context, context.getString(R.string.no_neteork),
                        Toast.LENGTH_LONG).show();*/
                isInternet = false;

            }
        } else if (con.getNetworkInfo(1) != null) {

            if (con.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED
                    || con.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING) {
                isInternet = true;

            } else if (con.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
                /*Toast.makeText(context, context.getString(R.string.no_neteork),
                        Toast.LENGTH_LONG).show();*/
                isInternet = false;

            }
        }
        return isInternet;
    }


    /**
     * Get IP address from first non-localhost interface
     * t
     * useIPv4  true=return ipv4, false=return ipv6
     *
     * @return address or empty string
     */

    public static String getIp() {

        if (UserDTO.getUserDTO().getUserIP() != null && !UserDTO.getUserDTO().getUserIP().equals("")) {
            return UserDTO.getUserDTO().getUserIP();
        } else {
            try {
                boolean useIPv4 = true;
                List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface intf : interfaces) {
                    List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                    for (InetAddress addr : addrs) {
                        if (!addr.isLoopbackAddress()) {
                            String sAddr = addr.getHostAddress();
                            //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                            boolean isIPv4 = sAddr.indexOf(':') < 0;

                            if (useIPv4) {
                                if (isIPv4)
                                    return sAddr;
                            } else {
                                if (!isIPv4) {
                                    int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                    return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex) {
            } // for now eat exceptions
        }
        return UserDTO.getUserDTO().getUserIP();
    }


    public static String getUserAgent(Context context) {

        return "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0";
    }


    public static int daysBetween(java.util.Date d1, java.util.Date d2) {

        return Math.abs((int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24)));
    }

    /**
     * <p> to set Language as per  select by user</p>
     *
     * @param lang
     */
    public static void setLocale(String lang, Context mContext) {

        Locale myLocale = new Locale(lang);
        Resources res = mContext.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        ((Activity) mContext).getBaseContext().getResources().updateConfiguration(conf, ((Activity) mContext).getBaseContext().getResources().getDisplayMetrics());

    }

    public static void hideSoftKeyboard(View view, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static void showSoftKeyboard(View view, Context mContext) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }


    /**
     * <p> get user Ip</p>
     */
    public static void getUserpublicIP(final Context context) {
        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();
                try {

                    URL whatismyip = new URL("http://checkip.amazonaws.com/");
                    //  BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
                    URLConnection connection = whatismyip.openConnection();
                    connection.addRequestProperty("Protocol", "Http/1.1");
                    connection.addRequestProperty("Connection", "keep-alive");
                    connection.addRequestProperty("Keep-Alive", "1000");
                    connection.addRequestProperty("User-Agent", "Web-Agent");
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String ip = in.readLine(); //you get the IP as a String
                    UserDTO.getUserDTO().setUserIP(ip);
                    SharedPreferences UserInfo = context.getSharedPreferences(Constant.USERPREFERENCE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor preferencesEditer = UserInfo.edit();
                    preferencesEditer.putString("USERIP", ip);

                    preferencesEditer.apply();

                    Log.d("Ip address", ip + "");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        t.start();
    }


    public static Map<String, String> getSignatureHeader(String methodName) {

        methodName = methodName.replace("/", "%2F");
        String timeStamp = HmacSha1Signature.generateTimeStamp();
        String nOnce = HmacSha1Signature.nONCEGenerator();

        Map<String, String> headerHashMap = new HashMap<>();

        try {

            String signature = HmacSha1Signature.calculateRFC2104HMAC(Constant.ApplicationSecret_new, methodName, timeStamp, nOnce);

            AuthInfo authInfo = new AuthInfo();
            authInfo.setApplicationToken(Constant.ApplicationToken_new);
            authInfo.setMethodName(methodName);
            authInfo.setTimeStamp(timeStamp);
            authInfo.setNonce(nOnce);
            authInfo.setSignature(signature);
            authInfo.setConsumerToken(UserDTO.getUserDTO().getToken());

            Gson gsonWihoutEncode = new GsonBuilder().disableHtmlEscaping().create();
            String header;
            header = gsonWihoutEncode.toJson(authInfo);
            header = header.replace("\\n", "");
            header = header.replace("\n", "");
            headerHashMap.put("AuthInfo", header);
            headerHashMap.put("Accept", "application/json");
            headerHashMap.put("Content-Type", "application/json; charset=utf-8");
            headerHashMap.put("UserTrackingId",UserDTO.getUserDTO().getUserTrackingId());


        } catch (Exception e) {
            e.printStackTrace();
        }

        return headerHashMap;
    }

    public static Map<String, String> getHeaders(Context mContext) {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", Constant.ContentType);
        headers.put("SessionId", UserDTO.getUserDTO().getSessionId());
        headers.put("UserAgent", Constant.UserAgent);
        headers.put("IpAddress", NetworkUtilities.getIp());
        headers.put("UserTrackingId", UserDTO.getUserDTO().getUserTrackingId());
        headers.put("Referrer", Constant.Referrer);
        headers.put("AppToken", Constant.ApplicationToken_new);
        if (UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE))
            headers.put("AffiliateId", Constant.AffilateId_ar);
        else
            headers.put("AffiliateId", Constant.AffilateId_en);

        headers.put("Accept", "application/json");


        return headers;
    }

    public static Map<String,String> getFreshDeskHeader()
    {
        String text=Constant.freshDeskKey+":X";
        byte[] data = new byte[0];
        try {
            data = text.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("Authorization", "Basic " +base64.replace("\n", ""));


        return headers;
    }

    public static Map<String, String> getStayCationHeader() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("content-type", Constant.ContentType);
        headers.put("sessionid", UserDTO.getUserDTO().getSessionId());
        headers.put("transactionid", "TransactionId");
        headers.put("UserAgent", Constant.UserAgent);
        headers.put("usertrackingid",UserDTO.getUserDTO().getUserTrackingId());
        headers.put("AppToken", "AndroidAppToken");
        headers.put("affiliate", "1");
        headers.put("IpAddress",NetworkUtilities.getIp());

        return headers;
    }

}
