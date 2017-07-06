package com.holidayme.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.holidayme.common.Log;

/**
 * Created by shaikh.salim on 8/10/2016.
 */

public class InstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getExtras() != null) {
            Intent in = new Intent("referral");
            in.putExtra("referral", intent.getExtras().getString("referrer"));
            context.sendBroadcast(in);
        }
        // Pass the intent to other receivers.
        // When you're done, pass the intent to the Google Analytics receiver.
        new CampaignTrackingReceiver().onReceive(context, intent);
    }
}
