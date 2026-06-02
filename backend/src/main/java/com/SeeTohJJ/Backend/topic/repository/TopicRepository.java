package com.SeeTohJJ.Backend.topic.repository;

import com.SeeTohJJ.Backend.topic.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, String> {
}
