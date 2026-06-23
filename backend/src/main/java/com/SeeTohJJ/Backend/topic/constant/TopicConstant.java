package com.SeeTohJJ.Backend.topic.constant;

public class TopicConstant {

    public static final String INSERT_USER_INTERESTED_TOPIC = """
            INSERT INTO user_interested_topics (user_id, topic_id, status, created_at, last_updated)
            VALUES (?, ?, ?, ?, ?)
            """;

    public static final String INSERT_USER_INITIAL_TOPIC_PROGRESS = """
            INSERT INTO user_topic_progress (user_id, topic_id, mastery_score, elo_rating, knowledge_probability, tutorial_completed, current_node_id, nodes_completed, created_at, last_updated)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    public static final String CHECK_TUTORIAL_COMPLETED = """
            SELECT tutorial_completed
            FROM user_topic_progress
            WHERE user_id = ? AND topic_id = ?
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
}
