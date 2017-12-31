package com.miage.m2.webappforum.control;

import com.miage.m2.webappforum.entity.Message;
import com.miage.m2.webappforum.entity.Projet;
import com.miage.m2.webappforum.entity.Topic;
import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.MessageRepository;
import com.miage.m2.webappforum.repository.ProjetRepository;
import com.miage.m2.webappforum.repository.TopicRepository;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import com.miage.m2.webappforum.service.UserService;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MessageController {


  @Autowired
  MessageRepository mr;

  @Autowired
  UtilisateurRepository ur;

  @Autowired
  ProjetRepository pr;

  @Autowired
  TopicRepository tr;

  @Autowired
  UserService us;



  @GetMapping(value = "/projets/{id}/topics/{idTopic}/messages")
  public String getAllMessageTopic(@PathVariable("id") String id, @PathVariable("idTopic") String idTopic, Model model) {
    Utilisateur utilisateur = us.getLoggedUser();
    Projet projet = pr.findOne(id);
    Topic topic = tr.findOne(idTopic);
    List<Message> messageList = topic.getMessageList();
    model.addAttribute("messageList", messageList);
    return "message/message";
  }

  @GetMapping(value = "/projets/{id}/topics/{idTopic}/messages/add")
  public String addMessageForm(@PathVariable("id") String id,@PathVariable("idTopic") String idTopic, Model model) {
    model.addAttribute("message", new Message());
    Projet projet = pr.findOne(id);
    Topic topic = tr.findOne(idTopic);
    model.addAttribute("topic", topic);
    model.addAttribute("projet", projet);
    return "message/formMessage";

  }




  @PostMapping(value = "projets/{id}/topics/{idTopic}/messages/save")
  public String addMessage(Model model, @PathVariable("id") String id,@PathVariable("idTopic") String idTopic,@ModelAttribute("message") @Valid Message message,
      BindingResult result) {
    System.out.println("helloo ca mere");
    Projet projet = pr.findOne(id);
    Topic topic = tr.findOne(idTopic);
    message.setContenu(message.getContenu());
    /*if (result.hasErrors()) {
      model.addAttribute("message", message);
      model.addAttribute("topic",topic);
      model.addAttribute("projet",projet);
      System.out.println("zzzzzz");
      return "message/formMessage";
    }*/
      Message messageSaved = mr.save(message);
      List<Message> listeMessage = topic.getMessageList();
      listeMessage.add(messageSaved);
      tr.save(topic);
      return "redirect:/projets/{id}/topics/{idTopic}/messages";


  }

}
