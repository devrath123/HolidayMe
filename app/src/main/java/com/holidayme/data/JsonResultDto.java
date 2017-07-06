package com.holidayme.data;

/**
 * Created by santosh.patar on 07-08-2015.
 */
public class JsonResultDto {

    private String ID;
    private String ResultMessage;
    private String Result;
    private String ErrorMessage;
    private String IsTempPassword;
    private String Email;
    private String ApplicationSignInUrl;
    private String MasterToken;
    private String UserToken;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getResultMessage() {
        return ResultMessage;
    }

    public void setResultMessage(String resultMessage) {
        ResultMessage = resultMessage;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public String getIsTempPassword() {
        return IsTempPassword;
    }

    public void setIsTempPassword(String isTempPassword) {
        IsTempPassword = isTempPassword;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getApplicationSignInUrl() {
        return ApplicationSignInUrl;
    }

    public void setApplicationSignInUrl(String applicationSignInUrl) {
        ApplicationSignInUrl = applicationSignInUrl;
    }

    public String getMasterToken() {
        return MasterToken;
    }

    public void setMasterToken(String masterToken) {
        MasterToken = masterToken;
    }

    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String userToken) {
        UserToken = userToken;
    }
}
