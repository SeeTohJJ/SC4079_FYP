package com.SeeTohJJ.Backend.garden.dto;

import java.util.List;

public class GardenDTO {

    private int water;
    private int coins;
    private List<PlantDTO> plants;

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public List<PlantDTO> getPlants() {
        return plants;
    }

    public void setPlants(List<PlantDTO> plants) {
        this.plants = plants;
    }
}
