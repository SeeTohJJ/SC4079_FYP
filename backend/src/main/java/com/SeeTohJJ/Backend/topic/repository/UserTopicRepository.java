package com.SeeTohJJ.Backend.topic.repository;

import com.SeeTohJJ.Backend.topic.model.Topic;
import com.SeeTohJJ.Backend.topic.model.UserInterestedTopic;
import com.SeeTohJJ.Backend.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTopicRepository extends JpaRepository<UserInterestedTopic, String> {

    boolean existsByUserAndTopic(
            User user,
            Topic topic
    );
}
