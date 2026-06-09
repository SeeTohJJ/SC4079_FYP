package com.SeeTohJJ.Backend.topic.constant;

public class TopicConstant {

    public static final String INSERT_USER_INTERESTED_TOPIC = """
            INSERT INTO user_interested_topics (user_id, topic_id, status, created_at, last_updated)
            VALUES (?, ?, ?, ?, ?)
            """;

    public static final String INSERT_USER_INITIAL_TOPIC_MASTERY = """
            INSERT INTO user_topic_mastery (user_id, topic_id, mastery_score, created_at, last_updated)
            VALUES (?, ?, ?, ?, ?)
            """;
}
