package com.holidayme.data;

/**
 * Created by santosh.patar on 06-08-2015.
 *
 * <p> This class hold usere requst logininfo</p>
 */
public class UserLogin {

    private String EmailId;
    private String Password;
    private String ApplicationToken;
    private String ReturnUrl = "www.holidayme.com";


    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getApplicationToken() {
        return ApplicationToken;
    }

    public void setApplicationToken(String applicationToken) {
        ApplicationToken = applicationToken;
    }

    public String getReturnUrl() {
        return ReturnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        ReturnUrl = returnUrl;
    }
}
