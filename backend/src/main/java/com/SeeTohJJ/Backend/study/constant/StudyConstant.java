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

//    public static final String SAVE_USER_QUESTION_ATTEMPT = """
//        INSERT INTO user_question_attempts (user_id, node_id, is_correct, time_taken, answered_at)
//        VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)
//        """;

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

    public static final String CHECK_IF_NODE_POS_EXIST_IN_PROGRESS = """
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

    public static final String GET_NODE_PATH_LAST_POS_INDEX = """
        SELECT position_index
        FROM user_node_progress
        WHERE user_id = ?
        ORDER BY position_index DESC
        LIMIT 1
        """;

    public static final String COMPLETE_TUTORIAL_FOR_INTERESTED_TOPIC = """
        UPDATE user_interested_topics
        SET tutorial_completed = true, last_updated = CURRENT_TIMESTAMP
        WHERE user_id = ? AND topic_id = ?
        """;
}
