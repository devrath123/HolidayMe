package com.holidayme.widgets;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

/**
 * Created by supriya.sakore on 11-05-2016.
 */
public class AppRater {
    private final static String APP_PNAME = "com.holidayme.activities";// Package Name

    public static void app_launched(Context mContext, boolean is_from_main) {
        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
        SharedPreferences.Editor editor = prefs.edit();
        if (!prefs.getBoolean("nevershown", false))
            if (is_from_main){
                if (prefs.getLong("launch_count", 0) == 5) {
                    showRateDialog(mContext,editor);

                }
            } else {
                showRateDialog(mContext,editor);
            }
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);
        editor.apply();

    }

    public static void showRateDialog(final Context mContext,final SharedPreferences.Editor editor) {

        // Increment launch counter

        RateUsCustomeDailog rateUsCustomeDailog = null;
        editor.putLong("launch_count", 0);
        editor.apply();
            rateUsCustomeDailog = new RateUsCustomeDailog(mContext);
        rateUsCustomeDailog.setDialogExitListner(new RateUsCustomeDailog.DialogExitListner() {
            @Override
            public void dialogExitWithRateus_now() {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
                    editor.putBoolean("nevershown", true);
                    editor.apply();

            }

            @Override
            public void dialogExitWithRemind_me_later() {
                   editor.putBoolean("nevershown", false);
                    editor.apply();

            }

            @Override
            public void dialogExitWithNo_thanks() {
                  editor.putBoolean("nevershown", true);
                    editor.apply();

            }
        });
        rateUsCustomeDailog.show();
    }
}