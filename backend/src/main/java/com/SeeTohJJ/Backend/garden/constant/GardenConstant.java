package com.SeeTohJJ.Backend.garden.constant;

import com.SeeTohJJ.Backend.garden.service.impl.GardenServiceImpl;

import java.time.LocalDateTime;

public class GardenConstant {

    // Constants for plant growth and happiness
    public static final int WATER_COST = 1;
    public static final int WATER_HAPPINESS = 10;
    public static final int DAILY_HAPPINESS_DECAY = 5;
    public static final double MAX_DAILY_GROWTH = 5;

    // Constants for study nodes reward
    public static final int WATER_REWARD_LESSON = 1;
    public static final int WATER_REWARD_QUIZ = 3;

    public static final String GET_USER_PLANTS_BY_USER_ID = """
            SELECT up.*, t.topic_name, utm.average_p_know
            FROM user_plant up
            JOIN topics t ON up.topic_id = t.topic_id
            JOIN user_topic_mastery utm ON up.user_id = utm.user_id AND up.topic_id = utm.topic_id
            WHERE up.user_id = ?
            """;

    public static final String GET_USER_CURRENCY_BY_USER_ID = """
            SELECT *
            FROM user_currency
            WHERE user_id = ?
            """;

    public static final String GET_USER_PLANT_BY_USER_ID_AND_TOPIC_ID = """
            SELECT up.*, t.topic_name, utm.average_p_know
            FROM user_plant up
            INNER JOIN topics t ON up.topic_id = t.topic_id
            INNER JOIN user_topic_mastery utm ON up.user_id = utm.user_id AND up.topic_id = utm.topic_id
            WHERE up.user_id = ? AND up.topic_id = ?
            """;

    public static final String UPDATE_USER_PLANT = """
            UPDATE user_plant
            SET current_growth = ?, max_growth = ?, happiness = ?, last_watered = ?, last_growth_update = ?, stage = ?
            WHERE user_id = ? AND topic_id = ?
            """;

    public static final String INSERT_USER_PLANT = """
            INSERT INTO user_plant (user_id, topic_id)
            VALUES (?, ?)
            """;

    public static final String UPDATE_USER_CURRENCY = """
            UPDATE user_currency
            SET coins = ?, water = ?
            WHERE user_id = ?
            """;

    public static final String INSERT_USER_CURRENCY = """
            INSERT INTO user_currency (user_id)
            VALUES (?)
            """;
}

