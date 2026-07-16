package com.SeeTohJJ.Backend.topic.constant;

public class TopicConstant {

    public static final String INSERT_USER_INTERESTED_TOPIC = """
            INSERT INTO user_interested_topics (user_id, topic_id, status, created_at, last_updated)
            VALUES (?, ?, ?, ?, ?)
            """;

    public static final String INSERT_USER_INITIAL_TOPIC_PROGRESS = """
            INSERT INTO user_topic_mastery (user_id, topic_id, average_elo, average_p_know, last_updated)
            VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)
            """;

    public static final String CHECK_TUTORIAL_COMPLETED = """
            SELECT tutorial_completed
            FROM user_subtopic_mastery
            WHERE user_id = ? AND subtopic_id = ?
            """;

    public static final String GET_USER_TOPIC_FROM_USERID = """
            SELECT topic_id
            FROM user_interested_topics
            WHERE user_id = ? AND status = 'ACTIVE'
            """;

    public static final String GET_TOP_UNCOMPLETED_TUTORIAL_TOPIC = """
            SELECT topic_id
            FROM user_interested_topics
            WHERE user_id = ? AND tutorial_completed = 'false' AND status = 'ACTIVE'
            LIMIT 1;
            """;

    public static final String GET_SUBTOPIC_ID = """
            SELECT subtopic_id
            FROM subtopics
            WHERE topic_id = ? AND is_active = true
            """;

    public static final String GET_BKT_PARAMETERS = """
            SELECT subtopic_id, p_init, p_transit, p_slip, p_guess
            FROM subtopics
            WHERE topic_id = ? AND is_active = true
            """;

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

    public static final String CHECK_SUBTOPIC_MASTERED = """
            SELECT is_mastered
            FROM user_subtopic_mastery
            WHERE user_id = ? AND subtopic_id = ?
            """;

    public static final String GET_TOPIC_ID_FROM_SUBTOPIC_ID = """
            SELECT topic_id
            FROM subtopics
            WHERE subtopic_id = ?
            """;

    public static final String CHECK_SUBTOPIC_EXIST = """
            SELECT EXISTS (
                SELECT 1
                FROM subtopics
                WHERE subtopic_id = ?
            )
            """;

    public static final String GET_RANDOM_UNINTERESTED_TOPIC = """
            SELECT subtopic_id
            FROM subtopics
            WHERE topic_id NOT IN (
                SELECT topic_id
                FROM user_interested_topics
                WHERE user_id = ? AND status = 'ACTIVE'
            )
            ORDER BY RANDOM()
            LIMIT 1;
            """;

    public static final String CHECK_NODE_INDEX_EXISTS = """
            SELECT EXISTS (
                SELECT 1
                FROM study_nodes
                WHERE topic_id = ? AND order_index = ? AND type = ?
            )
            """;

    public static final String GET_NODE_ID_BY_ORDER_INDEX = """
            SELECT node_id
            FROM study_nodes
            WHERE topic_id = ? AND order_index = ? AND type = ?
            """;
}
