/*
 * Copyright 2016 Basit Parkar.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 *  @date 4/4/16 11:52 PM
 *  @modified 4/4/16 11:52 PM
 */

package com.holidayme.gtm;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.holidayme.activities.VolleySupport.AppController;

/**
 * @author basitparkar
 */
public class GTMAnalytics {

    private final String TAG = getClass().getSimpleName();

    private Activity mActivity;

    public GTMAnalytics(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void sendEvent(int categoryId, int actionId, int labelId) {

        Tracker t = ((AppController) mActivity.getApplication()).getTracker(
                AppController.TrackerName.APP_TRACKER);

        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder()
                .setCategory(mActivity.getString(categoryId))
                .setAction(mActivity.getString(actionId))
                .setLabel(mActivity.getString(labelId))
                .build());
    }


    public void sendEvent(String category, String action, String label) {

        Tracker t = ((AppController) mActivity.getApplication()).getTracker(AppController.TrackerName.APP_TRACKER);
        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label)
                .build());
    }

    public void setScreenName(String title) {

        if (null == title || title.isEmpty())
            return;

        Tracker t = ((AppController) mActivity.getApplication()).getTracker(
                AppController.TrackerName.APP_TRACKER);
        t.setScreenName(title);
        Log.i(TAG, "setScreenName: " + title);
        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void setScreenName(String title, HitBuilders.ScreenViewBuilder builder) {

        if (null == title || title.isEmpty())
            return;

        Tracker t = ((AppController) mActivity.getApplication()).getTracker(
                AppController.TrackerName.APP_TRACKER);
        t.setScreenName(title);

        // Send a screen view.
        t.send(builder.build());
    }

    public void setCampaignUrl(String url) {

        if (null == url || url.isEmpty())
            return;

        Tracker t = ((AppController) mActivity.getApplication()).getTracker(AppController.TrackerName.APP_TRACKER);

        t.send(new HitBuilders.ScreenViewBuilder().setCampaignParamsFromUrl(url).build());
    }
}
