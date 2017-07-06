package com.holidayme.request;

import com.holidayme.data.UserProfileDto;

/**
 * Created by supriya.sakore on 26-10-2015.
 */
public class EditProfileRequest {
    private UserProfileDto UserProfile;

    public UserProfileDto getUserProfile() {
        return UserProfile;
    }

    public void setUserProfile(UserProfileDto userProfile) {
        UserProfile = userProfile;
    }
}
