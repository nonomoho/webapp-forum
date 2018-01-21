package com.miage.m2.webappforum.service;

import com.miage.m2.webappforum.AOC.LogExecutionTimes;
import com.miage.m2.webappforum.entity.Topic;
import com.miage.m2.webappforum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TopicService {

  @Autowired
  TopicRepository tr;

  @LogExecutionTimes
  public void addTopics(int nb) {
    for (int i = 0; i <= nb; i++) {
      Topic topic = new Topic();
      topic.setNom("test" + i);
      tr.save(topic);
    }
  }
}
