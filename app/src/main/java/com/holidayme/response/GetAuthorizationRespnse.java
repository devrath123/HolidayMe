package com.holidayme.response;

import android.annotation.SuppressLint;

import com.holidayme.data.GetAuthorization;

/**
 * Created by santosh.patar on 24-09-2015.
 */
@SuppressLint("ParcelCreator")
public class GetAuthorizationRespnse extends ResponseBase {
    private GetAuthorization GetAuthorization;

    public com.holidayme.data.GetAuthorization getGetAuthorization() {
        return GetAuthorization;
    }

    public void setGetAuthorization(com.holidayme.data.GetAuthorization getAuthorization) {
        GetAuthorization = getAuthorization;
    }
}
