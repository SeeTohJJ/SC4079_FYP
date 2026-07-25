package com.SeeTohJJ.Backend.study.constant;

public class NodeContentConstant {

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

    public static final String GET_QUESTION_RATING = """
        SELECT difficulty_rating
        FROM node_quiz_content
        WHERE node_id = ?
        """;

    public static final String GET_QUIZ_HINT = """
        SELECT hint
        FROM node_quiz_content
        WHERE node_id = ?
        """;

    public static final String GET_QUIZ_EXPLANATION = """
        SELECT explanation
        FROM node_quiz_content
        WHERE node_id = ?
        """;
}
