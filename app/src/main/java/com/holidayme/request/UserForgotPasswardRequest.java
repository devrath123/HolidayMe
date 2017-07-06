package com.holidayme.request;

/**
 * Created by supriya.sakore on 10-08-2015.
 */
public class UserForgotPasswardRequest {
            private String EmailId;
            private String LanguageCode;
            private String Mobile;
            private String ReturnUrl="www.holidayme.com";

    public String getEmail() {
        return EmailId;
    }

    public void setEmail(String email) {
        EmailId = email;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getReturnUrl() {
        return ReturnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        ReturnUrl = returnUrl;
    }
}
