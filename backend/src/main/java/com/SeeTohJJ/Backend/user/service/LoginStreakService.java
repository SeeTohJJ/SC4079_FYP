package com.SeeTohJJ.Backend.user.service;

public interface LoginStreakService {

    void updateDailyLoginStreak(Long userId);
    int getCurrentStreak(Long userId);
    void insertInitialLoginStreak(Long userId);
}
