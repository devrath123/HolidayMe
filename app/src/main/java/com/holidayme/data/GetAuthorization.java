package com.holidayme.data;

import java.util.ArrayList;

/**
 * Created by santosh.patar on 07-08-2015.
 */
public class GetAuthorization {

     private ArrayList<ApplicationMethodDTO> ApplicationMethods;
      private String RoleId;
    private String DisplayName;
    private String UserTypeId;
    private String EmailId;
    private String UserSecret;



    public String getRoleId() {
        return RoleId;
    }

    public void setRoleId(String roleId) {
        RoleId = roleId;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getUserTypeId() {
        return UserTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        UserTypeId = userTypeId;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getUserSecret() {
        return UserSecret;
    }

    public void setUserSecret(String userSecret) {
        UserSecret = userSecret;
    }

    public ArrayList<ApplicationMethodDTO> getApplicationMethods() {
        return ApplicationMethods;
    }

    public void setApplicationMethods(ArrayList<ApplicationMethodDTO> applicationMethods) {
        ApplicationMethods = applicationMethods;
    }
}
