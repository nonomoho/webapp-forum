package com.miage.m2.webappforum.repository;

import com.miage.m2.webappforum.entity.Topic;
import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends CrudRepository<Topic, String> {
}
