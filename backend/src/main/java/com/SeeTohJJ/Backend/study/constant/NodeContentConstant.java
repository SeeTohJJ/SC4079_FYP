package com.SeeTohJJ.Backend.study.constant;

public class NodeContentConstant {

    public static final String GET_LESSON_NODE_CONTENT = """
        SELECT node_id, title, content
        FROM node_lesson_content
        WHERE node_id = ?
        """;

    public static final String GET_QUIZ_NODE_CONTENT = """
        SELECT node_id, title, content, option_a, option_b, option_c, option_d, hint
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

    public static final String GET_ALL_LESSONS = """
        SELECT sn.node_id, sn.topic_id, sn.subtopic_id, sn.title, sn.order_index, sn.required_mastery, sn.last_updated, sn.is_active, nlc.content
        FROM study_nodes sn
        JOIN node_lesson_content nlc ON sn.node_id = nlc.node_id
        WHERE sn.type = 'LESSON' AND sn.is_active = true
        ORDER BY sn.order_index
        """;

    public static final String GET_LESSON = """
        SELECT sn.node_id, sn.topic_id, sn.subtopic_id, sn.title, sn.order_index, sn.required_mastery, sn.last_updated, sn.is_active, nlc.content
        FROM study_nodes sn
        JOIN node_lesson_content nlc ON sn.node_id = nlc.node_id
        WHERE sn.node_id = ? AND sn.type = 'LESSON' AND sn.is_active = true
        """;

    public static final String INSERT_LESSON_NODE = """
        INSERT INTO study_nodes (node_id, topic_id, subtopic_id, type, title, order_index, required_mastery, last_updated, is_active)
        VALUES (?, ?, ?, 'LESSON', ?, ?, ?, CURRENT_TIMESTAMP, true)
        """;

    public static final String INSERT_LESSON_CONTENT = """
        INSERT INTO node_lesson_content (node_id, title, content, last_updated)
        VALUES (?, ?, ?, CURRENT_TIMESTAMP)
        """;

    public static final String UPDATE_LESSON_NODE = """
        UPDATE study_nodes
        SET topic_id = ?, subtopic_id = ?, title = ?, order_index = ?, required_mastery = ?, last_updated = CURRENT_TIMESTAMP
        WHERE node_id = ?
        """;

    public static final String UPDATE_LESSON_CONTENT = """
        UPDATE node_lesson_content
        SET title = ?, content = ?, last_updated = CURRENT_TIMESTAMP
        WHERE node_id = ?
        """;

    public static final String SET_LESSON_INACTIVE = """
        UPDATE study_nodes
        SET is_active = false
        WHERE node_id = ? AND type = 'LESSON'
        """;

    public static final String SET_LESSON_ACTIVE = """
        UPDATE study_nodes
        SET is_active = true
        WHERE node_id = ? AND type = 'LESSON'
        """;

    public static final String GET_ALL_QUIZZES = """
        SELECT sn.node_id, sn.topic_id, sn.subtopic_id, sn.title, sn.order_index, sn.required_mastery, sn.last_updated, sn.is_active,
        nqc.content, nqc.option_a, nqc.option_b, nqc.option_c, nqc.option_d, nqc.difficulty_rating, nqc.hint, nqc.explanation
        FROM study_nodes sn
        JOIN node_quiz_content nqc ON sn.node_id = nqc.node_id
        WHERE sn.type = 'QUIZ' AND sn.is_active = true
        ORDER BY sn.order_index
        """;

    public static final String GET_QUIZ = """
        SELECT sn.node_id, sn.topic_id, sn.subtopic_id, sn.title, sn.order_index, sn.required_mastery, sn.last_updated, sn.is_active,
        nqc.content, nqc.option_a, nqc.option_b, nqc.option_c, nqc.option_d, nqc.difficulty_rating, nqc.hint, nqc.explanation
        FROM study_nodes sn
        JOIN node_quiz_content nqc ON sn.node_id = nqc.node_id
        WHERE sn.node_id = ? AND sn.type = 'QUIZ' AND sn.is_active = true
        ORDER BY sn.order_index
        """;

    public static final String INSERT_QUIZ_NODE = """
        INSERT INTO study_nodes (node_id, topic_id, subtopic_id, type, title, order_index, required_mastery, last_updated, is_active)
        VALUES (?, ?, ?, 'QUIZ', ?, ?, ?, CURRENT_TIMESTAMP, true)
        """;

    public static final String INSERT_QUIZ_CONTENT = """
        INSERT INTO node_quiz_content (node_id, title, content, option_a, option_b, option_c, option_d, correct_answer, difficulty_rating, hint, explanation, last_updated)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)
        """;

    public static final String UPDATE_QUIZ_NODE = """
        UPDATE study_nodes
        SET topic_id = ?, subtopic_id = ?, title = ?, order_index = ?, required_mastery = ?, last_updated = CURRENT_TIMESTAMP
        WHERE node_id = ?
        """;

    public static final String UPDATE_QUIZ_CONTENT = """
        UPDATE study_nodes
        SET title = ?, content = ?, option_a = ?, option_b = ?, option_c = ?, option_d = ?, correct_answer = ?, difficulty_rating = ?, hint = ?, explanation = ?, last_updated = CURRENT_TIMESTAMP
        WHERE node_id = ?
        """;

    public static final String SET_QUIZ_INACTIVE = """
        UPDATE study_nodes
        SET is_active = false
        WHERE node_id = ? AND type = 'QUIZ'
        """;

    public static final String SET_QUIZ_ACTIVE = """
        UPDATE study_nodes
        SET is_active = true
        WHERE node_id = ? AND type = 'QUIZ'
        """;
}
