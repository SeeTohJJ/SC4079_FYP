package com.SeeTohJJ.Backend.garden.constant;

public class GardenConstant {

    public static final int WATER_COST = 1;
    public static final int WATER_HAPPINESS = 10;
    public static final int DAILY_HAPPINESS_DECAY = 5;
    public static final double MAX_DAILY_GROWTH = 5;
    public static final int WATER_REWARD_LESSON = 1;
    public static final int WATER_REWARD_QUIZ = 3;

    public static final String GET_USER_PLANTS_BY_USER_ID = """
            SELECT *
            FROM user_plant
            WHERE user_id = ?
            """;

    public static final String GET_USER_CURRENCY_BY_USER_ID = """
            SELECT *
            FROM user_currency
            WHERE user_id = ?
            """;

    public static final String GET_USER_PLANT_BY_USER_ID_AND_TOPIC_ID = """
            SELECT *
            FROM user_plant
            WHERE user_id = ? AND topic_id = ?
            """;

    public static final String UPDATE_USER_PLANT = """
            UPDATE user_plant
            SET current_growth = ?, max_growth = ?, happiness = ?, last_watered = ?, last_growth_update = ?, stage = ?
            WHERE user_id = ? AND topic_id = ?
            """;

    public static final String INSERT_USER_PLANT = """
            INSERT INTO user_plant (current_growth, max_growth, happiness, last_watered, last_growth_update, stage, user_id, topic_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

    public static final String UPDATE_USER_CURRENCY = """
            UPDATE user_currency
            SET coins = ?, water = ?
            WHERE user_id = ?
            """;
}

