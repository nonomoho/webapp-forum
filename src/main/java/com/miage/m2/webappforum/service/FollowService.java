package com.miage.m2.webappforum.service;

import com.miage.m2.webappforum.entity.Topic;
import com.miage.m2.webappforum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {


    @Autowired
    TopicRepository tr;

    public void addTopicFollowing(String topicId){
        Topic topic = tr.findOne(topicId);
        //TODO get user in session
        //topic.getFollowerList().add(null);
        tr.save(topic);
    }
}
