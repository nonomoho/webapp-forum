package com.miage.m2.webappforum.service;

import com.miage.m2.webappforum.entity.Topic;
import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.TopicRepository;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {


  @Autowired
  TopicRepository tr;
  @Autowired
  UtilisateurRepository ur;

  @Autowired
  UserService us;

  public void addTopicFollowing(String topicId) {
    Topic topic = tr.findOne(topicId);
    Utilisateur utilisateur = us.getLoggedUser();
    if (utilisateur.getFollowTopicList().contains(topic)) {
      utilisateur.getFollowTopicList().remove(topic);
    } else {
      utilisateur.getFollowTopicList().add(topic);
    }
    ur.save(utilisateur);
  }
}
