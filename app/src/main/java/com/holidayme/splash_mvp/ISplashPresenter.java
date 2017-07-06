package com.holidayme.splash_mvp;

import android.content.Context;

/**
 * Created by devrath.rathee on 02-11-2016.
 */

public interface ISplashPresenter {

    void setTrackingAndSessionId(Context context);
    void setDeepLinkDefault(Context context);
    void setUserDTO(Context context);
}
