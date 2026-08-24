package com.SeeTohJJ.Backend.user.constant;

public class UserTopicMasteryConstant {

    public static final String INSERT_USER_INITIAL_TOPIC_PROGRESS = """
            INSERT INTO user_topic_mastery (user_id, topic_id, average_elo, average_p_know, last_updated)
            VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)
            """;

    public static final String GET_TOP_UNCOMPLETED_TUTORIAL_TOPIC = """
            SELECT topic_id
            FROM user_interested_topics
            WHERE user_id = ? AND tutorial_completed = 'false' AND status = 'ACTIVE'
            LIMIT 1;
            """;

    public static final String SET_AVERAGE_ELO = """
            UPDATE user_topic_mastery
            SET average_elo = ?, last_updated = CURRENT_TIMESTAMP
            WHERE user_id = ? AND topic_id = ?
            """;

    public static final String SET_AVERAGE_P_KNOW = """
            UPDATE user_topic_mastery
            SET average_p_know = ?, last_updated = CURRENT_TIMESTAMP
            WHERE user_id = ? AND topic_id = ?
            """;

    public static final String SET_NEXT_REVIEW_DATE = """
            UPDATE user_topic_mastery
            SET next_review_date = ?, last_updated = CURRENT_TIMESTAMP
            WHERE user_id = ? AND topic_id = ?
            """;

    public static final String SET_REVIEW_INTERVAL_DAY = """
            UPDATE user_topic_mastery
            SET review_interval_day = ?, last_updated = CURRENT_TIMESTAMP
            WHERE user_id = ? AND topic_id = ?
            """;

    public static final String GET_NEXT_REVIEW_DATE = """
            SELECT next_review_date
            FROM user_topic_mastery
            WHERE user_id = ? AND topic_id = ?
            """;

    public static final String GET_DUE_REVIEWS = """
            SELECT topic_id
            FROM user_topic_mastery
            WHERE user_id = ?
            AND next_review_date <= CURRENT_DATE;
            """;

    public static final String GET_AVERAGE_ELO = """
            SELECT average_elo
            FROM user_topic_mastery
            WHERE user_id = ? AND topic_id = ?
            """;

    public static final String GET_AVERAGE_P_KNOW = """
            SELECT average_p_know
            FROM user_topic_mastery
            WHERE user_id = ? AND topic_id = ?
            """;
}
