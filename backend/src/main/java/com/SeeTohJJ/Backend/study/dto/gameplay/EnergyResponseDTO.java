package com.SeeTohJJ.Backend.study.dto.gameplay;

public class EnergyResponseDTO {

    private int currentEnergy;
    private int maxEnergy;
    private long secondsUntilNextEnergy;

    public EnergyResponseDTO(int currentEnergy, int maxEnergy, long secondsUntilNextEnergy) {
        this.currentEnergy = currentEnergy;
        this.maxEnergy = maxEnergy;
        this.secondsUntilNextEnergy = secondsUntilNextEnergy;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }
    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public long getSecondsUntilNextEnergy() {
        return secondsUntilNextEnergy;
    }

    public void setSecondsUntilNextEnergy(long secondsUntilNextEnergy) {
        this.secondsUntilNextEnergy = secondsUntilNextEnergy;
    }
}
