package com.SeeTohJJ.Backend.topic.dao;


import com.SeeTohJJ.Backend.auth.model.User;

public interface UserTopicMasteryDao {

    void insertInitialTopicMastery(Long userId, String topicId);

}
