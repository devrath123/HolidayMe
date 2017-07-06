package com.holidayme.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.common.Log;
import com.holidayme.data.Destination;
import com.holidayme.fragments.HotelIndexFragment;
import com.holidayme.gtm.GTMAnalytics;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by shaikh.salim on 2/23/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {
    GTMAnalytics gtmAnalytics;
    public static final String UTM_CAMPAIGN = "utm_campaign";
    public static final String UTM_SOURCE = "utm_source";
    public static final String UTM_MEDIUM = "utm_medium";
    public static final String UTM_TERM = "utm_term";
    public static final String UTM_CONTENT = "utm_content";

    private final String[] sources = {
            UTM_CAMPAIGN, UTM_SOURCE, UTM_MEDIUM, UTM_TERM, UTM_CONTENT
    };

    public void manageFragment(Fragment fragment, boolean isAddToBackStack, String fragmentName, String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentByTag(fragmentName);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (currentFragment != null && currentFragment.isVisible()) {
            fragmentTransaction.show(currentFragment);
        } else {
            fragmentTransaction.replace(R.id.container_body, fragment, fragmentName);
            if (fragment instanceof HotelIndexFragment) {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            if (isAddToBackStack)
                fragmentTransaction.addToBackStack(fragmentName);
            fragmentTransaction.commitAllowingStateLoss();

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            // Remove default title text
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            // Get access to the custom title view
            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            mTitle.setText(title);
        }
    }

    public void startMainActivity(Activity activity, Destination destination) {
        Intent intent = new Intent(activity, HomeActivity.class);
        if (destination != null)
            intent.putExtra("deeplink", destination);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter("referral"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String referrerString = intent.getStringExtra("referral");
            try {
                Map<String, String> paramMap = getHashMapFromQuery(referrerString);
                if (gtmAnalytics == null)
                    gtmAnalytics = AppController.getInstance().getGTMAnalytics(BaseActivity.this);
                gtmAnalytics.setCampaignUrl(paramMap.toString());
            } catch (UnsupportedEncodingException e) {
                Log.e("Referrer Error", e.getMessage());
            }
        }
    };

    public static Map<String, String> getHashMapFromQuery(String query)
            throws UnsupportedEncodingException {

        Map<String, String> query_pairs = new LinkedHashMap<>();

        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
                    URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }

}
