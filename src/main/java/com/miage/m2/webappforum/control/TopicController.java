package com.miage.m2.webappforum.control;

import com.miage.m2.webappforum.entity.Permission;
import com.miage.m2.webappforum.entity.Projet;
import com.miage.m2.webappforum.entity.Topic;
import com.miage.m2.webappforum.entity.TypePermissionEnum;
import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.PermissionRepository;
import com.miage.m2.webappforum.repository.ProjetRepository;
import com.miage.m2.webappforum.repository.TopicRepository;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import com.miage.m2.webappforum.service.PermissionService;
import com.miage.m2.webappforum.service.UserService;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class TopicController {

  @Autowired
  TopicRepository tr;
  @Autowired
  ProjetRepository pr;
  @Autowired
  UtilisateurRepository ur;
  @Autowired
  PermissionRepository permr;

  @Autowired
  UserService us;
  @Autowired
  PermissionService ps;


  @PreAuthorize("isAuthenticated()")
  @GetMapping(value = "/followedTopics")
  public String getFollowedTopics(Model model) {

    Utilisateur utilisateur = us.getLoggedUser();
    Set<Topic> topicList = utilisateur.getFollowTopicList();
    if (topicList != null) {
      topicList.forEach(t -> t.setFollowedByUser(true));
    }
    model.addAttribute("topicList", topicList);
    model.addAttribute("follow", true);
    return "topic/topic";
  }


  @PreAuthorize("hasPermission(#id, 'Projet', T(com.miage.m2.webappforum.entity.TypePermissionEnum).READ)")
  @GetMapping(value = "/projets/{id}/topics")
  public String getAllTopicsOfProject(@PathVariable("id") String id, Model model) {
    Utilisateur utilisateur = us.getLoggedUser();
    Projet projet = pr.findOne(id);
    Iterable<Topic> topicList = tr.findByProjet(projet);
    topicList.forEach(t -> t.setFollowedByUser(t.getFollowerList().contains(utilisateur)));
    model.addAttribute("topicList", topicList);
    model.addAttribute("projet", projet);
    return "topic/topic";
  }

  @PreAuthorize("isAuthenticated()")
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

  @PreAuthorize("hasPermission(#id, 'Projet', T(com.miage.m2.webappforum.entity.TypePermissionEnum).WRITE)")
  @GetMapping(value = "/projets/{id}/topics/add")
  public String addTopicForm(@PathVariable("id") String id, Model model) {
    model.addAttribute("users", ur.findAll(us.getLoggedUser()));
    Topic topic = new Topic();
    Projet projet = pr.findOne(id);
    model.addAttribute("projet", projet);
    ps.getPermission(projet);
    topic.setOuvert(true);
    topic.setReadUsers(projet.getReadUsers());
    topic.setWriteUsers(projet.getWriteUsers());
    topic.setNeedAuth(projet.getNeedAuth());
    model.addAttribute("topic", topic);
    return "topic/singleTopic";

  }

  @PreAuthorize("hasPermission(#id, 'Projet', T(com.miage.m2.webappforum.entity.TypePermissionEnum).WRITE)")
  @PostMapping(value = "projets/{id}/topics/save")
  public String addTopic(Model model, @PathVariable("id") String id,
      @ModelAttribute("topic") @Valid Topic topic,
      BindingResult result) {
    Projet projet = pr.findOne(id);
    topic.setNom(topic.getNom().trim().toUpperCase());
    if (topic.getId().isEmpty() && tr.existsByNomAndProjet(topic.getNom(), projet)) {
      result.rejectValue("nom", "topic.nameAlreadyExist");
    }
    if (result.hasErrors()) {
      model.addAttribute("users", ur.findAll(us.getLoggedUser()));
      model.addAttribute("topic", topic);
      model.addAttribute("projet", projet);
      return "topic/singleTopic";
    } else {
      Utilisateur utilisateur = us.getLoggedUser();

      //si le topic est crée
      if (topic.getId().isEmpty()) {
        topic.setCreation(new Date());
        topic.setCreateur(utilisateur);
        topic.setFollowerList(new HashSet<Utilisateur>());
        topic.getFollowerList().add(utilisateur);
      } else {
        Topic originial = tr.findOne(topic.getId());
        topic.setCreation(originial.getCreation());
        topic.setCreateur(originial.getCreateur());
      }
      topic.setProjet(projet);
      Topic topicSaved = tr.save(topic);

      //creation des permissions
      topicSaved.setReadUsers(topic.getReadUsers());
      topicSaved.setWriteUsers(topic.getWriteUsers());
      ps.setPermission(topicSaved);

      //only current user will get the write to edit it
      Permission permission = new Permission();
      permission.setTargetPermission(topicSaved);
      permission.setType(TypePermissionEnum.EDIT);
      permission.setUtilisateur(utilisateur);
      permr.save(permission);

      return "redirect:/projets/{id}/topics";
    }

  }

  @PreAuthorize("hasPermission(#idTopic, 'Topic', T(com.miage.m2.webappforum.entity.TypePermissionEnum).EDIT)")
  @GetMapping(value = "/projets/{idProjet}/topics/edit/{idTopic}")
  public String editTopicForm(Model model, @PathVariable("idProjet") String idProjet,
      @PathVariable("idTopic") String idTopic) {
    Topic topic = tr.findOne(idTopic);
    model.addAttribute("projet", pr.findOne(idProjet));
    model.addAttribute("topic", topic);
    model.addAttribute("users", ur.findAll(us.getLoggedUser()));
    ps.getPermission(topic);

    return "topic/singleTopic";
  }

}
