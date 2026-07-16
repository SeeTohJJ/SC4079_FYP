package com.SeeTohJJ.Backend.study.constant;

public class StudyConstant {

    public static final String GET_TUTORIAL_NODES = """
        SELECT node_id, type, order_index
        FROM study_nodes
        WHERE topic_id = ? AND is_tutorial = true AND is_active = true
        ORDER BY order_index
        """;

    public static final String GET_EXISTING_NODE_PATH = """
        SELECT node_id, node_type, position_index, is_unlocked, is_completed
        FROM user_node_progress
        WHERE user_id = ?
        ORDER BY position_index
        """;

    public static final String COUNT_ACTIVE_NODES = """
        SELECT COUNT(*)
        FROM user_node_progress
        WHERE user_id = ?
          AND is_unlocked = true
          AND is_completed = false
        """;

    public static final String INSERT_NODE_INTO_USER_PROGRESS = """
        INSERT INTO user_node_progress (user_id, node_id, node_type, position_index, is_unlocked, is_completed, last_updated)
        VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)
        """;

    public static final String GET_LESSON_NODE_CONTENT = """
        SELECT node_id, title, content
        FROM node_lesson_content
        WHERE node_id = ?
        """;

    public static final String GET_QUIZ_NODE_CONTENT = """
        SELECT node_id, title, content, option_a, option_b, option_c, option_d
        FROM node_quiz_content
        WHERE node_id = ?
        """;

    public static final String GET_DECISION_NODE_CONTENT = """
        SELECT node_id, title, content, option_a, option_b, result_a, result_b
        FROM node_decision_content
        WHERE node_id = ?
        """;

    public static final String GET_EVENT_NODE_CONTENT = """
        SELECT node_id, title, content, result
        FROM event_node_content
        WHERE node_id = ?
        """;

    public static final String COMPLETE_NODE_WITH_USER_ID = """
        UPDATE user_node_progress
        SET is_completed = true, last_updated = CURRENT_TIMESTAMP
        WHERE user_id = ? AND node_id = ?
        """;

    public static final String GET_CORRECT_ANSWER = """
        SELECT correct_answer
        FROM node_quiz_content
        WHERE node_id = ?
        """;

    public static final String SAVE_USER_QUESTION_ATTEMPT = """
        INSERT INTO user_question_attempts (user_id, node_id, is_correct, time_taken, answered_at)
        VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)
        """;

    public static final String GET_QUESTION_RATING = """
        SELECT difficulty_rating
        FROM node_quiz_content
        WHERE node_id = ?
        """;

    public static final String GET_NODE_POSITIONAL_INDEX = """
        SELECT position_index
        FROM user_node_progress
        WHERE user_id = ? AND node_id = ?
        """;

    public static final String UNLOCK_NEXT_NODE = """
        UPDATE user_node_progress
        SET is_unlocked = true, last_updated = CURRENT_TIMESTAMP
        WHERE user_id = ? AND position_index = ?
        """;

    public static final String CHECK_NEXT_NODE_EXIST = """
        SELECT COUNT(*)
        FROM user_node_progress
        WHERE user_id = ? AND position_index = ?
        """;

    public static final String GET_CURRENT_SUBTOPIC = """
        SELECT subtopic_id
        FROM user_node_progress
        WHERE user_id = ? AND is_completed = true
        ORDER BY last_updated
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

    public static final String GET_NODE_PATH_LAST_POS_INDEX = """
        SELECT position_index
        FROM user_node_progress
        WHERE user_id = ?
        ORDER BY position_index DESC
        LIMIT 1
        """;
}
