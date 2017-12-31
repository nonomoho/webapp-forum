package com.miage.m2.webappforum.control;

import com.miage.m2.webappforum.entity.Projet;
import com.miage.m2.webappforum.entity.Topic;
import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.ProjetRepository;
import com.miage.m2.webappforum.repository.TopicRepository;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import com.miage.m2.webappforum.service.UserService;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;
import java.util.List;


@Controller
public class TopicController {

  @Autowired
  TopicRepository tr;
  @Autowired
  ProjetRepository pr;

  @Autowired
  UtilisateurRepository ur;

  @Autowired
  UserService us;


  @GetMapping(value = "/followedTopics")
  public String getFollowedTopics(Model model) {
    Utilisateur utilisateur = us.getLoggedUser();
    Set<Topic> topicList = utilisateur.getFollowTopicList();
    topicList.forEach(t -> t.setFollowedByUser(true));
    model.addAttribute("topicList", topicList);
    return "topic/topic";
  }



  @GetMapping(value = "/projets/{id}/topics")
  public String getAllTopicsOfProject(@PathVariable("id") String id, Model model) {
      Utilisateur utilisateur = us.getLoggedUser();
    Projet projet = pr.findOne(id);
    List<Topic> topicList = projet.getTopicList();
    topicList.forEach(t -> t.setFollowedByUser(t.getFollowerList().contains(utilisateur)));
    model.addAttribute("topicList", topicList);
    return "topic/topic";
  }


  @PostMapping(value = "/topics/{idTopic}/follow")
  public String addTopicFollowing(@PathVariable("idTopic") String topicId) {
    Topic topic = tr.findOne(topicId);
    Utilisateur utilisateur = us.getLoggedUser();
    if (utilisateur != null) {
      if (utilisateur.getFollowTopicList().contains(topic)) {
        utilisateur.getFollowTopicList().remove(topic);
      } else {
        utilisateur.getFollowTopicList().add(topic);
      }
      ur.save(utilisateur);
    }
    return "redirect:/followedTopics";
  }

  @GetMapping(value = "/projets/{id}/topics/add")
  public String addTopicForm(@PathVariable("id") String id, Model model) {
    model.addAttribute("users", ur.findAll());
    model.addAttribute("topic", new Topic());
    Projet projet = pr.findOne(id);
    model.addAttribute("projet", projet);
    return "topic/singleTopic";

  }

  @PostMapping(value = "projets/{id}/topics/save")
  public String addTopic(Model model, @PathVariable("id") String id,@ModelAttribute("topic") @Valid Topic topic,
      BindingResult result) {
    Projet projet = pr.findOne(id);
    topic.setNom(topic.getNom().trim().toUpperCase());
    if (topic.getId().isEmpty() && pr.existsByNom(topic.getNom())) {
      result.rejectValue("nom", "topic.nameAlreadyExist");
    }
    if (result.hasErrors()) {
      model.addAttribute("users", ur.findAll());
      model.addAttribute("topic", topic);
      model.addAttribute("projet",projet);
      return "topic/singleTopic";
    } else {
      Topic topicSaved = tr.save(topic);
      List<Topic> listeTopic = projet.getTopicList();
      listeTopic.add(topicSaved);
      pr.save(projet);
      return "redirect:/projets/{id}/topics";
    }

  }

  /*    TODO: permission for edit topic    */

  @GetMapping(value = "/projets/{idProjet}/topics/edit/{idTopic}")
  public String editTopicForm(Model model, @PathVariable("idProjet") String idProjet, @PathVariable("idTopic") String idTopic) {
    model.addAttribute("projet", pr.findOne(idProjet));
    model.addAttribute("topic", tr.findOne(idTopic));
    return "topic/singleTopic";
  }

}
