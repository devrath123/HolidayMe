package com.holidayme.common;

import android.util.Base64;

import com.holidayme.Constants.Constant;
import com.holidayme.data.UserDTO;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.DateFormat;
import java.util.Formatter;
import java.util.TimeZone;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by santosh.patar on 30-07-2015.
 */
public class HmacSha1Signature {

    public static String calculateRFC2104HMAC(String _key, String methodName, String ts, String nonce)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        String data, key;

        data = Constant.ApplicationToken_new + Constant.ApplicationSecret_new + Constant.ApplicationName + methodName + ts + nonce;
        key = _key;

        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(),"HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(keySpec);

        byte[] result;
        String retVal = null;
        try {
            result = mac.doFinal(data.getBytes("UTF-8"));
            retVal = Base64.encodeToString(result, Base64.DEFAULT);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return retVal;

    }

    public static String nONCEGenerator() {
        final String uuid = UUID.randomUUID().toString();
        return uuid;
    }


    public static String generateTimeStamp() {

        DateFormat df = DateFormat.getTimeInstance();
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        String ts = df.format(new java.util.Date());

        if (ts.contains(".")) {
            ts = ts.substring(0, ts.indexOf("."));
        }

        long unixTime = System.currentTimeMillis() / 1000L;


        return String.valueOf(unixTime);


    }


}
