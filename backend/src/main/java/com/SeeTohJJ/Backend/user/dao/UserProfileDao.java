package com.SeeTohJJ.Backend.user.dao;

import com.SeeTohJJ.Backend.user.model.UserProfile;

import java.time.LocalDate;

public interface UserProfileDao {

    void setUserProfile(UserProfile userProfile);

    Integer getCurrentStreak(Long userId);
    LocalDate getLastLoginDate(Long userId);
    void updateLoginStreak(Long userId, int streak, LocalDate loginDate);
}
