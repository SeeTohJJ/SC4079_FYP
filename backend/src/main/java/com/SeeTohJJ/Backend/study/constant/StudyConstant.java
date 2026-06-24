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
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

    public static final String GET_LESSON_NODE_CONTENT = """
        SELECT node_id, title, content
        FROM node_lesson_content
        WHERE node_id = ?
        """;

    public static final String GET_QUIZ_NODE_CONTENT = """
        SELECT node_id, title, content, option_a, option_b, option_c, option_d, correct_answer
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
}
