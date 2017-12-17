package com.miage.m2.webappforum.control;

import com.miage.m2.webappforum.entity.Projet;
import com.miage.m2.webappforum.entity.Topic;
import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.ProjetRepository;
import com.miage.m2.webappforum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TopicController {

    @Autowired
    TopicRepository tr;
    @Autowired
    ProjetRepository pr;


    @GetMapping(value = "/projets/{id}/topics")
    public String getAllTopicsOfProject(@PathVariable("id") String id, Model model) {
        //TODO use real user in session
        Utilisateur utilisateur = new Utilisateur();
        Projet projet = pr.findOne(id);
        List<Topic> topicList = projet.getTopicList();
        topicList.forEach(t -> t.setFollowedByUser(t.getFollowerList().contains(utilisateur)));
        model.addAttribute("topicList", topicList);
        return "topic/topic";
    }


}
