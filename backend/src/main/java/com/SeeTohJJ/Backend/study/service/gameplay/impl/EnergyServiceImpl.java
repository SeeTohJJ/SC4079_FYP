package com.SeeTohJJ.Backend.study.service.gameplay.impl;

import com.SeeTohJJ.Backend.common.exception.InsufficientEnergyException;
import com.SeeTohJJ.Backend.study.dao.gameplay.EnergyDao;
import com.SeeTohJJ.Backend.study.dto.gameplay.EnergyResponseDTO;
import com.SeeTohJJ.Backend.study.model.gameplay.UserEnergy;
import com.SeeTohJJ.Backend.study.service.gameplay.EnergyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class EnergyServiceImpl implements EnergyService {

    private static final Logger logger = LoggerFactory.getLogger(EnergyServiceImpl.class);
    private final EnergyDao energyDao;

    public EnergyServiceImpl(EnergyDao energyDao) {
        this.energyDao = energyDao;
    }

    private static final int ENERGY_REGEN_SECONDS = 180;

    @Override
    public EnergyResponseDTO getEnergy(Long userId) {
        logger.info("Starting getEnergy");

        UserEnergy energy = energyDao.findByUserId(userId);
        LocalDateTime now = LocalDateTime.now();

        updateRegeneration(energy, now);
        long secondsUntilNextEnergy = calculateSecondsUntilNextEnergy(energy, now);

        return new EnergyResponseDTO(energy.getCurrentEnergy(), energy.getMaxEnergy(), secondsUntilNextEnergy);
    }

    private void updateRegeneration(UserEnergy energy, LocalDateTime now) {

        if (energy.getCurrentEnergy() >= energy.getMaxEnergy()) {
            energy.setLastUpdated(now);
            return;
        }

        long secondsPassed = Duration.between(energy.getLastUpdated(), now).getSeconds();

        int regenerated = (int) (secondsPassed / ENERGY_REGEN_SECONDS);

        if (regenerated <= 0) {
            return;
        }

        int newEnergy = Math.min(energy.getCurrentEnergy() + regenerated, energy.getMaxEnergy());

        LocalDateTime newLastUpdated = energy.getLastUpdated().plusSeconds((long) regenerated * ENERGY_REGEN_SECONDS);

        energy.setCurrentEnergy(newEnergy);
        energy.setLastUpdated(newLastUpdated);

        energyDao.updateEnergy(energy.getUserId(), newEnergy, newLastUpdated);
    }

    private long calculateSecondsUntilNextEnergy(UserEnergy energy, LocalDateTime now){

        if (energy.getCurrentEnergy() >= energy.getMaxEnergy()) {
            return 0;
        }

        long secondsPassed = Duration.between(energy.getLastUpdated(), now).getSeconds();
        long secondsIntoCurrentCycle = secondsPassed % ENERGY_REGEN_SECONDS;

        return ENERGY_REGEN_SECONDS - secondsIntoCurrentCycle;
    }

    @Override
    public boolean hasEnoughEnergy(Long userId, int cost){
        logger.info("Starting hasEnoughEnergy");

        UserEnergy energy = energyDao.findByUserId(userId);
        return energy.getCurrentEnergy() >= cost;
    }


    @Override
    @Transactional
    public void consumeEnergy(Long userId, int cost) {
        logger.info("Starting consumeEnergy");

        UserEnergy energy = energyDao.findByUserId(userId);
        LocalDateTime now = LocalDateTime.now();

        updateRegeneration(energy, now);

        if (energy.getCurrentEnergy() < cost) {
            long secondsUntilNextEnergy =
                    calculateSecondsUntilNextEnergy(
                            energy,
                            now
                    );

            throw new InsufficientEnergyException(
                    energy.getCurrentEnergy(),
                    cost,
                    secondsUntilNextEnergy
            );
        }

        int newEnergy = energy.getCurrentEnergy() - cost;

        energyDao.updateEnergy(userId, newEnergy, energy.getLastUpdated());
    }

    @Override
    public void insertInitialEnergyTables(Long userId){
        logger.info("Starting insertInitialEnergyTables");

        energyDao.insert(userId);
    }


}
