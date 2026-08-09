package com.SeeTohJJ.Backend.user.dao;

import java.time.LocalDate;

public interface LoginStreakDao {
    Integer getCurrentStreak(Long userId);
    LocalDate getLastLoginDate(Long userId);
    void updateLoginStreak(Long userId, int streak, LocalDate loginDate);
    void insert(Long userId);
}
