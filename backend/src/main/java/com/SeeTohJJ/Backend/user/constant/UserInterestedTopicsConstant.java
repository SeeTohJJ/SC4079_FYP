package com.SeeTohJJ.Backend.user.constant;

public class UserInterestedTopicsConstant {

    public static final String INSERT_USER_INTERESTED_TOPIC = """
            INSERT INTO user_interested_topics (user_id, topic_id, status, created_at, last_updated)
            VALUES (?, ?, ?, ?, ?)
            """;

    public static final String GET_USER_TOPIC_FROM_USERID = """
            SELECT topic_id
            FROM user_interested_topics
            WHERE user_id = ? AND status = 'ACTIVE'
            ORDER BY topic_id
            """;

    public static final String GET_RANDOM_UNINTERESTED_TOPIC = """
            SELECT subtopic_id
            FROM subtopics
            WHERE topic_id NOT IN (
                SELECT topic_id
                FROM user_interested_topics
                WHERE user_id = ? AND status = 'ACTIVE'
            )
            ORDER BY RANDOM()
            LIMIT 1;
            """;

    public static final String COMPLETE_TUTORIAL_FOR_INTERESTED_TOPIC = """
            UPDATE user_interested_topics
            SET tutorial_completed = true, last_updated = CURRENT_TIMESTAMP
            WHERE user_id = ? AND topic_id = ?
            """;

    public static final String GET_INTERESTED_TOPICS_BY_USER_ID = """
            SELECT t.topic_id, t.topic_name
            FROM topics t
            JOIN user_interested_topics uit
            ON t.topic_id = uit.topic_id
            WHERE uit.user_id = ?
            ORDER BY t.topic_id;
            """;
}
