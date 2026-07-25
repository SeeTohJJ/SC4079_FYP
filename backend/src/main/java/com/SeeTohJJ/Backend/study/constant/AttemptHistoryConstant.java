package com.SeeTohJJ.Backend.study.constant;

public class AttemptHistoryConstant {

    public static final String SAVE_USER_QUESTION_ATTEMPT = """
        INSERT INTO user_question_attempts (user_id, node_id, is_correct, time_taken, answered_at, hint_used)
        VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, ?)
        """;

    public static final String GET_QUIZ_ATTEMPT_HISTORY_COUNT = """
        SELECT COUNT(*)
        FROM user_question_attempts
        WHERE user_id = ? AND node_id = ?
        """;
}
