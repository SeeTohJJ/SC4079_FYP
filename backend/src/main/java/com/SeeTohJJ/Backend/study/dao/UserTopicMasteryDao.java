package com.SeeTohJJ.Backend.study.dao;


public interface UserTopicMasteryDao {

    void insertInitialTopicProgress(Long userId, String topicId);
    String getTopUserUncompletedTopic(Long userId);
    boolean isTutorialCompleted(Long userId, String topicId);

}
