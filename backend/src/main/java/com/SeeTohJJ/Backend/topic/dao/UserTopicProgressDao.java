package com.SeeTohJJ.Backend.topic.dao;


import java.util.List;

public interface UserTopicProgressDao {

    void insertInitialTopicProgress(Long userId, String topicId);
    boolean isTutorialCompleted(Long userId, String topicId);
    String getTopUserUncompletedTopic(Long userId);

}
