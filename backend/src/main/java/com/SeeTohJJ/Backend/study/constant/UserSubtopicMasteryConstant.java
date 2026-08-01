package com.SeeTohJJ.Backend.study.constant;

public class UserSubtopicMasteryConstant {

    public static final String GET_USER_P_KNOW = """
            SELECT p_know
            FROM user_subtopic_mastery
            WHERE user_id = ? AND subtopic_id = ?
            """;

    public static final String UPDATE_USER_P_KNOW = """
            UPDATE user_subtopic_mastery
            SET p_know = ?, last_updated = CURRENT_TIMESTAMP
            WHERE user_id = ? AND subtopic_id = ?
            """;

    public static final String GET_USER_ELO = """
            SELECT elo
            FROM user_subtopic_mastery
            WHERE user_id = ? AND subtopic_id = ?
            """;

    public static final String SET_USER_NEW_ELO_RATING = """
            UPDATE user_subtopic_mastery
            SET elo = ?, last_updated = CURRENT_TIMESTAMP
            WHERE user_id = ? AND subtopic_id = ?
            """;

    public static final String SET_IS_MASTERED_TO_TRUE = """
            UPDATE user_subtopic_mastery
            SET is_mastered = true
            WHERE user_id = ? AND subtopic_id = ?
            """;

    public static final String SET_IS_MASTERED_TO_FALSE = """
            UPDATE user_subtopic_mastery
            SET is_mastered = false
            WHERE user_id = ? AND subtopic_id = ?
            """;

    public static final String CHECK_SUBTOPIC_MASTERED = """
            SELECT is_mastered
            FROM user_subtopic_mastery
            WHERE user_id = ? AND subtopic_id = ?
            """;

    public static final String INSERT_NEW_SUBTOPIC_MASTERY = """
            INSERT INTO user_subtopic_mastery (user_id, subtopic_id, p_know, is_mastered, last_updated)
            VALUES (?, ?, ?, false, CURRENT_TIMESTAMP)
            """;

    public static final String GET_USER_ATTEMPT_HISTORY = """
            SELECT attempt_count, correct_count, wrong_count
            FROM user_subtopic_mastery
            WHERE user_id = ? AND subtopic_id = ?
            """;

    public static final String UPDATE_USER_CORRECT_ATTEMPT_HISTORY = """
            UPDATE user_subtopic_mastery
            SET attempt_count = ?, correct_count = ?, last_updated = CURRENT_TIMESTAMP
            WHERE user_id = ? AND subtopic_id = ?
            """;

    public static final String UPDATE_USER_WRONG_ATTEMPT_HISTORY = """
            UPDATE user_subtopic_mastery
            SET attempt_count = ?, wrong_count = ?, last_updated = CURRENT_TIMESTAMP
            WHERE user_id = ? AND subtopic_id = ?
            """;

    public static final String GET_LOWEST_P_KNOW_SUBTOPIC = """
            SELECT subtopic_id
            FROM user_subtopic_mastery
            WHERE user_id = ?
            ORDER BY p_know ASC
            LIMIT 1
            """;

    public static final String GET_LOWEST_P_KNOW_SUBTOPIC_NOT_MASTERED = """
            SELECT subtopic_id
            FROM user_subtopic_mastery
            WHERE user_id = ? AND is_mastered = false
            ORDER BY p_know ASC
            LIMIT 1
            """;

    public static final String GET_USER_SUBTOPIC_CURRENT_CHAIN = """
            SELECT current_chain
            FROM user_subtopic_mastery
            WHERE user_id = ? AND subtopic_id = ?
            """;

    public static final String GET_USER_SUBTOPIC_LAST_UPDATED = """
            SELECT last_updated
            FROM user_subtopic_mastery
            WHERE user_id = ? AND subtopic_id = ?
            """;

    public static final String SET_USER_SUBTOPIC_P_KNOW = """
            UPDATE user_subtopic_mastery
            SET p_know = ?, last_updated = CURRENT_TIMESTAMP
            WHERE user_id = ? AND subtopic_id = ?
            """;

    public static final String INCREMENT_HINT_USAGE = """
            UPDATE user_subtopic_mastery
            SET hint_used = hint_used + 1, last_updated = CURRENT_TIMESTAMP
            WHERE user_id = ? AND subtopic_id = ?
            """;

    public static final String CHECK_TUTORIAL_COMPLETED = """
            SELECT tutorial_completed
            FROM user_subtopic_mastery
            WHERE user_id = ? AND subtopic_id = ?
            """;

    public static final String CALCULATE_AVERAGE_ELO_OF_TOPIC = """
            SELECT AVG(elo) AS average_elo
            FROM user_subtopic_mastery
            WHERE user_id = ? AND subtopic_id IN (
                SELECT subtopic_id
                FROM subtopics
                WHERE topic_id = ?
            )
            """;

    public static final String CALCULATE_AVERAGE_P_KNOW_OF_TOPIC = """
            SELECT AVG(p_know) AS average_p_know
            FROM user_subtopic_mastery
            WHERE user_id = ? AND subtopic_id IN (
                SELECT subtopic_id
                FROM subtopics
                WHERE topic_id = ?
            )
            """;
}
