package com.holidayme.request;

/**
 * Created by santosh.patar on 10-08-2015.
 */
public class UserSignUpRequest {




    private String Email;

    private String Password;
    private int RegistrationType; //
    private String UserCategory;//
    private String LanguageCode;//
   // private String ActivationUrl = "https://www.holidayme.com/";

    private boolean IsSubscribUser = false;
    private String Ip;// = "115.248.109.178";


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }



    public String getUserCategory() {
        return UserCategory;
    }

    public void setUserCategory(String userCategory) {
        UserCategory = userCategory;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public boolean isSubscribUser() {
        return IsSubscribUser;
    }

    public void setIsSubscribUser(boolean isSubscribUser) {
        IsSubscribUser = isSubscribUser;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }


    ///////////////////


    public int getRegistrationType() {
        return RegistrationType;
    }

    public void setRegistrationType(int registrationType) {
        RegistrationType = registrationType;
    }
}
