package com.SeeTohJJ.Backend.garden.service.impl;

import com.SeeTohJJ.Backend.garden.constant.GardenConstant;
import com.SeeTohJJ.Backend.garden.dao.UserCurrencyDao;
import com.SeeTohJJ.Backend.garden.dao.UserPlantDao;
import com.SeeTohJJ.Backend.garden.dto.GardenDTO;
import com.SeeTohJJ.Backend.garden.dto.PlantDTO;
import com.SeeTohJJ.Backend.garden.model.UserCurrency;
import com.SeeTohJJ.Backend.garden.model.UserPlant;
import com.SeeTohJJ.Backend.garden.service.GardenService;
import com.SeeTohJJ.Backend.study.model.StudyNode;
import com.SeeTohJJ.Backend.study.service.progress.UserTopicService;
import com.SeeTohJJ.Backend.topic.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GardenServiceImpl implements GardenService {

    private static final Logger logger = LoggerFactory.getLogger(GardenServiceImpl.class);
    private final UserPlantDao userPlantDao;
    private final UserCurrencyDao userCurrencyDao;
    private final UserTopicService userTopicService;
    private final TopicService topicService;

    @Autowired
    public GardenServiceImpl(UserPlantDao userPlantDao, UserCurrencyDao userCurrencyDao, UserTopicService userTopicService, TopicService topicService) {
        this.userPlantDao = userPlantDao;
        this.userCurrencyDao = userCurrencyDao;
        this.userTopicService = userTopicService;
        this.topicService = topicService;
    }

    public enum PlantStage {
        SEEDING,
        SPROUTING,
        VEGETATING,
        FLOWERING,
        RIPENING
    }

    @Override
    public GardenDTO getGarden(Long userId){
        logger.info("Starting getGarden");

        List<UserPlant> plants = userPlantDao.findByUserId(userId);
        UserCurrency currency = userCurrencyDao.getCurrency(userId);

        GardenDTO dto = new GardenDTO();

        dto.setWater(currency.getWater());
        dto.setCoins(currency.getCoins());

        List<PlantDTO> list = new ArrayList<>();

        for(UserPlant plant : plants){
            updateDailyHappinessDecay(plant);
            updateGrowth(plant);

            PlantDTO plantDTO = convertToDTO(plant);

            list.add(plantDTO);

        }

        dto.setPlants(list);

        return dto;
    }

    private PlantDTO convertToDTO(UserPlant plant){
        logger.info("Starting convertToDTO");

        PlantDTO dto = new PlantDTO();

        dto.setTopicId(plant.getTopicId());
        dto.setPlantType(plant.getTopicId()); // TODO: create plant types or just remove this para
        dto.setTopicName(plant.getTopicName());
        dto.setGrowth(plant.getCurrentGrowth());
        dto.setMaxGrowth(plant.getMaxGrowth());
        dto.setHappiness(plant.getHappiness());
        dto.setStage(plant.getStage());

        return dto;
    }

    @Override
    public PlantDTO waterPlant(Long userId, String topicId){
        logger.info("Starting waterPlant");

        UserCurrency currency = userCurrencyDao.getCurrency(userId);

        if (currency.getWater() <= 0) {
            throw new RuntimeException("No water");
        }

        UserPlant plant = userPlantDao.findByUserIdAndTopicId(userId, topicId);

        if(plant == null){
            throw new RuntimeException("Plant not found");
        }

        currency.setWater(currency.getWater() - GardenConstant.WATER_COST);
        plant.setHappiness(Math.min(100, plant.getHappiness() + GardenConstant.WATER_HAPPINESS));

        updateGrowth(plant);
        userCurrencyDao.update(currency);
        userPlantDao.update(plant);

        return convertToDTO(plant);
    }

    @Override
    public void onStudyCompleted(Long userId, String topicId, StudyNode.NodeType nodeType, boolean isCorrectAnswer) {
        logger.info("Starting onStudyCompleted");

        UserCurrency currency = userCurrencyDao.getCurrency(userId);
        UserPlant plant = userPlantDao.findByUserIdAndTopicId(userId, topicId);

        switch (nodeType){
            case LESSON:
                currency.setWater(currency.getWater() + GardenConstant.WATER_REWARD_LESSON);
                double averagePKnow = userTopicService.getAveragePKnow(userId, topicId);
                plant.setMaxGrowth(averagePKnow);
                break;

            case QUIZ:
                if (isCorrectAnswer) {
                    currency.setWater(currency.getWater() + GardenConstant.WATER_REWARD_QUIZ);
                }

                break;

            default:
                throw new RuntimeException("Unknown node type");

        }

        userCurrencyDao.update(currency);

        if(plant == null){
            throw new RuntimeException("Plant not found");
        }

        userPlantDao.update(plant);
    }

    private void updateGrowth(UserPlant plant){
        logger.info("Starting updateGrowth");

        LocalDateTime now = LocalDateTime.now();
        long elapseDays = calculateElapsedDays(plant);

        if(elapseDays == 0){
            return;
        }
        double growth = plant.getCurrentGrowth() + calculateGrowthGain(elapseDays, calculateGrowthRate(plant));
        growth = Math.min(growth, plant.getMaxGrowth());

        plant.setCurrentGrowth(growth);
        plant.setLastGrowthUpdate(now);
        plant.setStage(calculateStage(growth));

        userPlantDao.update(plant);
    }

    private long calculateElapsedDays(UserPlant plant){
        return Duration.between(plant.getLastGrowthUpdate(), LocalDateTime.now()).toDays();
    }

    private double calculateGrowthGain(long elapsedDays, double growthRate){
        return elapsedDays * growthRate;
    }

    private String calculateStage(double growth){
        logger.info("Starting calculateStage");

        if(growth<20) {
            return PlantStage.SEEDING.name();
        }
        if(growth<40) {
            return PlantStage.SPROUTING.name();
        }
        if(growth<60) {
            return PlantStage.VEGETATING.name();
        }
        if(growth<80) {
            return PlantStage.FLOWERING.name();
        }
        return PlantStage.RIPENING.name();
    }

    @Override
    public void createInitialPlantsDuringRegistration(Long userId){
        logger.info("Starting createInitialPlantsDuringRegistration");

        List<String> topics = topicService.getUserTopicFromUserId(userId);

        for(String topicId : topics){
            UserPlant plant = new UserPlant();
            plant.setUserId(userId);
            plant.setTopicId(topicId);
            plant.setCurrentGrowth(0);
            plant.setMaxGrowth(0);
            plant.setHappiness(100);
            plant.setStage(PlantStage.SEEDING.name());
            plant.setLastGrowthUpdate(LocalDateTime.now());
            plant.setLastWatered(LocalDateTime.now());
            userPlantDao.insert(plant);

        }
    }

    @Override
    public void createPlant(Long userId, String topicId){
        logger.info("Starting createPlant");

        UserPlant plant = new UserPlant();

        plant.setUserId(userId);
        plant.setTopicId(topicId);
        plant.setCurrentGrowth(0);
        plant.setMaxGrowth(0);
        plant.setHappiness(100);
        plant.setStage(PlantStage.SEEDING.name());
        plant.setLastGrowthUpdate(LocalDateTime.now());
        plant.setLastWatered(LocalDateTime.now());

        userPlantDao.insert(plant);
    }

    private void updateDailyHappinessDecay(UserPlant plant){
        logger.info("Starting updateDailyHappinessDecay");

        LocalDateTime now = LocalDateTime.now();

        long days = Duration.between(plant.getLastWatered(), now).toDays();

        if(days==0){
            return;
        }

        int happiness = plant.getHappiness() - (int)(days * GardenConstant.DAILY_HAPPINESS_DECAY);
        happiness = Math.max(0, happiness);
        plant.setHappiness(happiness);
    }

    private double calculateGrowthRate(UserPlant plant){
        logger.info("Starting calculateGrowthRate");

        int happiness = plant.getHappiness();

        if(happiness>=80) {
            return 3;
        }
        if(happiness>=60) {
            return 2;
        }
        if(happiness>=40) {
            return 1;
        }
        return 0;
    }



}
