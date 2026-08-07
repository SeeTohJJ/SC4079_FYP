package com.SeeTohJJ.Backend.study.service.gameplay;

import com.SeeTohJJ.Backend.study.dto.gameplay.EnergyResponseDTO;

public interface EnergyService {

    EnergyResponseDTO getEnergy(Long userId);
    boolean hasEnoughEnergy(Long userId, int cost);
    void consumeEnergy(Long userId, int cost);
//    void addEnergy(Long userId, int amount);
//    void refillEnergy(Long userId);
}
