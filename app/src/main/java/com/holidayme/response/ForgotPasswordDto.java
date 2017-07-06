package com.holidayme.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by supriya.sakore on 25-02-2016.
 */
public class ForgotPasswordDto {
    @SerializedName("ID")
    public Long ID;
    @SerializedName("ResultMessage")
    public String ResultMessage;
    @SerializedName("Result")
    public boolean Result=true;
    @SerializedName("ErrorMessage")
    public String ErrorMessage;
    @SerializedName("IsTempPassword")
    public boolean IsTempPassword;
    @SerializedName("Email")
    public String Email;
    @SerializedName("ApplicationSignInUrl")
    public String ApplicationSignInUrl;
    @SerializedName("MasterToken")
    public String MasterToken;
    @SerializedName("UserToken")
    public String UserToken;
}
