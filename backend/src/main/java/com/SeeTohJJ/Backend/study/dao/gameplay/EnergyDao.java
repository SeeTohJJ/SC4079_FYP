package com.SeeTohJJ.Backend.study.dao.gameplay;

import com.SeeTohJJ.Backend.study.model.gameplay.UserEnergy;

import java.time.LocalDateTime;

public interface EnergyDao {
    UserEnergy findByUserId(Long userId);
    void updateEnergy(Long userId, int currentEnergy, LocalDateTime lastUpdated);
    void insert(Long userId);
}
