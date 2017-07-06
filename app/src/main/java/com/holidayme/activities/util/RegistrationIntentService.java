package com.holidayme.activities.util;

import android.app.IntentService;
import android.content.Intent;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.exceptions.CleverTapMetaDataNotFoundException;
import com.clevertap.android.sdk.exceptions.CleverTapPermissionsNotSatisfied;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.holidayme.activities.R;


import java.io.IOException;

/**
 * Created by supriya.sakore on 21-06-2016.
 */
public class RegistrationIntentService extends IntentService {

    public static  String token;
    CleverTapAPI cleverTap;

    public RegistrationIntentService() {
        super("RegistrationIntentService");
    }

    @Override
    public void onHandleIntent(Intent intent) {

        InstanceID instanceID = InstanceID.getInstance(this);

        try {

            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            cleverTap = CleverTapAPI.getInstance(getApplicationContext());
            cleverTap.data.pushGcmRegistrationId(token, true);


        } catch (IOException e) {
            e.printStackTrace();
        }catch (CleverTapMetaDataNotFoundException | CleverTapPermissionsNotSatisfied e) {
            e.printStackTrace();
        }
    }

}
