package com.SeeTohJJ.Backend.study.constant;

public class UserTopicMasteryConstant {

    public static final String INSERT_USER_INITIAL_TOPIC_PROGRESS = """
            INSERT INTO user_topic_mastery (user_id, topic_id, average_elo, average_p_know, last_updated)
            VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)
            """;

    public static final String GET_TOP_UNCOMPLETED_TUTORIAL_TOPIC = """
            SELECT topic_id
            FROM user_interested_topics
            WHERE user_id = ? AND tutorial_completed = 'false' AND status = 'ACTIVE'
            LIMIT 1;
            """;

    public static final String CHECK_TUTORIAL_COMPLETED = """
            SELECT tutorial_completed
            FROM user_subtopic_mastery
            WHERE user_id = ? AND subtopic_id = ?
            """;

}
