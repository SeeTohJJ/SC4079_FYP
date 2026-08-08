package com.SeeTohJJ.Backend.topic.constant;

public class TopicConstant {

    public static final String GET_SUBTOPIC_ID = """
            SELECT subtopic_id
            FROM study_nodes
            WHERE node_id = ? AND is_active = true
            """;

    public static final String GET_BKT_PARAMETERS = """
            SELECT p_init, p_transit, p_slip, p_guess
            FROM subtopics
            WHERE subtopic_id = ? AND is_active = true
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
            WHERE subtopic_id = ? AND order_index = ? AND type = ?
            """;

    public static final String GET_P_INIT = """
            SELECT p_init
            FROM subtopics
            WHERE subtopic_id = ?
            """;

    public static final String GET_TOPIC_ID = """
            SELECT topic_id
            FROM study_nodes
            WHERE node_id = ?
            """;

    public static final String GET_NODE_DIFFICULTY = """
            SELECT required_mastery
            FROM study_nodes
            WHERE node_id = ?
            """;

    public static final String GET_TOPIC_NAME = """
            SELECT topic_name
            FROM topics
            WHERE topic_id = ?
            """;
}
