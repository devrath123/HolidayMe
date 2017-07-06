package com.holidayme.receiver;

/**
 * Created by supriya.sakore on 18-01-2017.
 */


import android.os.Bundle;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.NotificationInfo;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {



        if (remoteMessage.getData().size() > 0) {
            Bundle extras = new Bundle();
            for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
                extras.putString(entry.getKey(), entry.getValue());
            }

            NotificationInfo info = CleverTapAPI.getNotificationInfo(extras);

            if (info.fromCleverTap) {
                CleverTapAPI.createNotification(getApplicationContext(), extras);
            }
        }

    }


}
