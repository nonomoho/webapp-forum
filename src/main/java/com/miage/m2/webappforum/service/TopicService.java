package com.miage.m2.webappforum.service;

import com.miage.m2.webappforum.entity.Topic;
import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.TopicRepository;
import com.sun.javafx.scene.traversal.TopMostTraversalEngine;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

public class TopicService {

  @Autowired
  TopicRepository tr;

  public void addTopics(int nb) {
    for (int i = 0; i <= nb; i++) {
      Topic topic = new Topic();
      topic.setNom("test" + i);
      tr.save(topic);
    }
  }
}
