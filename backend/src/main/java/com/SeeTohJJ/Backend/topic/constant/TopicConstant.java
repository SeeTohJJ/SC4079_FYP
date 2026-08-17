package com.SeeTohJJ.Backend.topic.constant;

public class TopicConstant {

    public static final String GET_TOPIC_ID = """
            SELECT topic_id
            FROM study_nodes
            WHERE node_id = ?
            """;

    public static final String GET_TOPIC_NAME = """
            SELECT topic_name
            FROM topics
            WHERE topic_id = ?
            """;

    public static final String GET_ALL_TOPICS = """
            SELECT topic_id, topic_name, description, is_active
            FROM topics
            ORDER BY topic_id ASC
            """;

    public static final String GET_TOPIC_BY_ID = """
            SELECT topic_id, topic_name, description, is_active
            FROM topics
            WHERE topic_id = ?
            """;

    public static final String SET_TOPIC_INACTIVE = """
            UPDATE topics
            SET is_active = false
            WHERE topic_id = ?
            """;

    public static final String SET_TOPIC_ACTIVE = """
            UPDATE topics
            SET is_active = true
            WHERE topic_id = ?
            """;

    public static final String CHECK_TOPIC_EXIST_BY_NAME = """
            SELECT EXISTS (
                SELECT 1
                FROM topics
                WHERE LOWER(topic_name) = LOWER(?)
            )
            """;

    public static final String FIND_NEXT_TOPIC_ID = """
            SELECT COALESCE(
                MAX(CAST(SUBSTRING(topic_id, 2) AS INTEGER)),
                0
            )
            FROM topics
            WHERE topic_id LIKE 'T%'
            """;

    public static final String INSERT_TOPIC = """
            INSERT INTO topics (topic_id, topic_name, description)
            VALUES (?, ?, ?)
            """;

    public static final String UPDATE_TOPIC = """
            UPDATE topics
            SET topic_name = ?, description = ?
            WHERE topic_id = ?
            """;

    public static final String GET_ACTIVE_TOPIC_COUNT = """
            SELECT COUNT(*)
            FROM topics
            WHERE is_active = true
            """;
}
