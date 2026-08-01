package com.SeeTohJJ.Backend.study.service.adaptive;

import java.util.List;

public interface SpacedRepetitionService {

    void scheduleNextReview(Long userId, String topicId, double pKnow);
    boolean isReviewDue(Long userId, String topicId);
    List<String> getDueReviews(Long userId);
}
