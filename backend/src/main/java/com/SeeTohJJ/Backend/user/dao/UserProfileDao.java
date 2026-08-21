package com.SeeTohJJ.Backend.user.dao;

import com.SeeTohJJ.Backend.user.model.UserProfile;

import java.time.LocalDate;

public interface UserProfileDao {

    void setUserProfile(UserProfile userProfile);
    int getActiveUserCount();
    int getActiveAdminCount();
    String getNameFromId(Long userId);
}
