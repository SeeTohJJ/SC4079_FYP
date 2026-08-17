package com.SeeTohJJ.Backend.topic.constant;

public class SubtopicConstant {

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

    public static final String GET_NODE_ID_BY_ORDER_INDEX = """
            SELECT node_id
            FROM study_nodes
            WHERE subtopic_id = ? AND order_index = ? AND type = ?
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


    public static final String GET_P_INIT = """
            SELECT p_init
            FROM subtopics
            WHERE subtopic_id = ?
            """;


    public static final String GET_NODE_DIFFICULTY = """
            SELECT required_mastery
            FROM study_nodes
            WHERE node_id = ?
            """;

    public static final String GET_ALL_SUBTOPICS = """
            SELECT subtopic_id, topic_id, name, difficulty, p_init, p_transit, p_slip, p_guess, is_active
            FROM subtopics
            ORDER BY subtopic_id, topic_id ASC
            """;

    public static final String GET_SUBTOPIC_BY_ID = """
            SELECT subtopic_id, topic_id, name, difficulty, p_init, p_transit, p_slip, p_guess, is_active
            FROM subtopics
            WHERE subtopic_id = ?
            """;

    public static final String SET_SUBTOPIC_INACTIVE = """
            UPDATE subtopics
            SET is_active = false
            WHERE subtopic_id = ?
            """;

    public static final String SET_SUBTOPIC_ACTIVE = """
            UPDATE subtopics
            SET is_active = true
            WHERE subtopic_id = ?
            """;

    public static final String CHECK_SUBTOPIC_EXIST_BY_NAME = """
            SELECT EXISTS (
                SELECT 1
                FROM subtopics
                WHERE LOWER(name) = LOWER(?)
            )
            """;

    public static final String FIND_NEXT_SUBTOPIC_ID = """
            SELECT MAX(CAST(SUBSTRING(subtopic_id FROM POSITION('S' IN subtopic_id) + 1) AS INTEGER))
            FROM subtopics
            WHERE topic_id = ?;
            """;

    public static final String INSERT_SUBTOPIC = """
            INSERT INTO subtopics (subtopic_id, topic_id, name, difficulty, p_init, p_transit, p_slip, p_guess, is_active, last_updated)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, true, CURRENT_TIMESTAMP)
            """;

    public static final String UPDATE_SUBTOPIC = """
            UPDATE subtopics
            SET name = ?, difficulty = ?, p_init = ?, p_transit = ?, p_slip = ?, p_guess = ?, last_updated = CURRENT_TIMESTAMP
            WHERE subtopic_id = ?
            """;

    public static final String GET_ACTIVE_SUBTOPIC_COUNT = """
            SELECT COUNT(*)
            FROM subtopics
            WHERE is_active = true
            """;
}