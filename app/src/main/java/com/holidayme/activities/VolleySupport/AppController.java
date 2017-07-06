package com.holidayme.activities.VolleySupport;

import android.app.Activity;
import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.exceptions.CleverTapMetaDataNotFoundException;
import com.clevertap.android.sdk.exceptions.CleverTapPermissionsNotSatisfied;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.holidayme.activities.BuildConfig;
import com.holidayme.activities.R;
import com.holidayme.dao.DBAccessObject;
import com.holidayme.gtm.GTMAnalytics;
import com.holidayme.managers.DatabaseManager;

import java.util.HashMap;

public class AppController extends Application {


    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static AppController mInstance;
    public static GoogleAnalytics analytics;
    public static Tracker tracker;

    @Override
    public void onCreate() {
        CleverTapAPI.setDebugLevel(1277182231);
        ActivityLifecycleCallback.register(this);
        super.onCreate();
        mInstance = this;
        new DBAccessObject().initializeInstance(getApplicationContext());
        DatabaseManager.initializeInstance(getApplicationContext());
        initGoogleAnalytics();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    private void initGoogleAnalytics() {

        analytics = GoogleAnalytics.getInstance(this);
        analytics.dispatchLocalHits();
        if (BuildConfig.DEBUG)
            analytics.setDryRun(false);
        else
            analytics.setDryRun(false);

        tracker = getTracker(TrackerName.APP_TRACKER);
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
    }

    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<>();

    public synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(getString(R.string.ga_app_tracker_id))
                    : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.app_global_tracker)
                    : analytics.newTracker(R.xml.ecommerce_tracker);
            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }

    public GTMAnalytics getGTMAnalytics(Activity activity) {
        GTMAnalytics gtmAnalytics = new GTMAnalytics(activity);
        return gtmAnalytics;
    }

    public CleverTapAPI getCleverTapInstance() {
        CleverTapAPI cleverTap = null;
        try {
            cleverTap = CleverTapAPI.getInstance(getApplicationContext());
        } catch (CleverTapMetaDataNotFoundException e) {
            // thrown if you haven't specified your CleverTap Account ID or Token in your AndroidManifest.xml
        } catch (CleverTapPermissionsNotSatisfied e) {
            // thrown if you haven’t requested the required permissions in your AndroidManifest.xml
        }
        return cleverTap;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        AppEventsLogger.deactivateApp(this);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}