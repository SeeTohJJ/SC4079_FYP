package com.SeeTohJJ.Backend.garden.dao;

import com.SeeTohJJ.Backend.garden.model.UserPlant;

import java.util.List;

public interface UserPlantDao {

    List<UserPlant> findByUserId(Long userId);
    UserPlant findByUserIdAndTopicId(Long userId,String topicId);
    void update(UserPlant plant);
    void insert(Long userId, String topicId);

}
