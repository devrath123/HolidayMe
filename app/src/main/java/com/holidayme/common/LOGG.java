package com.holidayme.common;

/**
 * Created by supriya.sakore on 31-08-2015.
 */
public class LOGG {
    public static void d(String TAG, String message) {
        int maxLogSize = 3000;
        for(int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            boolean ISLogEnable = false;
            if(ISLogEnable)
            android.util.Log.d(TAG, message.substring(start, end));
        }
    }
}
