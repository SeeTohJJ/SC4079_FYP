package com.SeeTohJJ.Backend.common.exception;

public class InsufficientEnergyException extends RuntimeException {

    private final int currentEnergy;
    private final int requiredEnergy;
    private final long secondsUntilNextEnergy;

    public InsufficientEnergyException(
            int currentEnergy,
            int requiredEnergy,
            long secondsUntilNextEnergy) {

        super("Not enough energy");

        this.currentEnergy = currentEnergy;
        this.requiredEnergy = requiredEnergy;
        this.secondsUntilNextEnergy = secondsUntilNextEnergy;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public int getRequiredEnergy() {
        return requiredEnergy;
    }

    public long getSecondsUntilNextEnergy() {
        return secondsUntilNextEnergy;
    }
}