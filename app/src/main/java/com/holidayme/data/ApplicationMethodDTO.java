package com.holidayme.data;

/**
 * Created by santosh.patar on 24-09-2015.
 */
public class ApplicationMethodDTO {
    String MethodName;
    boolean IsActive;
    boolean RequiresUserLogin;

    public String getMethodName() {
        return MethodName;
    }

    public void setMethodName(String methodName) {
        MethodName = methodName;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setIsActive(boolean isActive) {
        IsActive = isActive;
    }

    public boolean isRequiresUserLogin() {
        return RequiresUserLogin;
    }

    public void setRequiresUserLogin(boolean requiresUserLogin) {
        RequiresUserLogin = requiresUserLogin;
    }
}
