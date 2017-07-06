package com.holidayme.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by supriya.sakore on 12-08-2015.
 */
public class ForgotPasswardResponse {

    @SerializedName("JsonResult")
    public ForgotPasswordDto JsonResult;
    @SerializedName("Error")
    public ErrorResponseBase Error;
}
