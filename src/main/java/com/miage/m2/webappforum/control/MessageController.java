package com.miage.m2.webappforum.control;

import com.miage.m2.webappforum.entity.Message;
import com.miage.m2.webappforum.entity.Topic;
import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.MessageRepository;
import com.miage.m2.webappforum.repository.ProjetRepository;
import com.miage.m2.webappforum.repository.TopicRepository;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import com.miage.m2.webappforum.service.UserService;
import java.util.Date;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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


  @Autowired
  private JavaMailSender sender;


  @GetMapping(value = "/projets/{id}/topics/{idTopic}/messages")
  public String getAllMessageTopic(@PathVariable("id") String id,
      @PathVariable("idTopic") String idTopic, Model model) {
    Topic topic = tr.findOne(idTopic);
    List<Message> messageList = topic.getMessageList();
    model.addAttribute("messageList", messageList);
    model.addAttribute("topic", topic);
    model.addAttribute("message", new Message());
    return "message/message";
  }


  @PostMapping(value = "projets/{id}/topics/{idTopic}/messages/save")
  public String addMessage(@PathVariable("idTopic") String idTopic, Model model,
      @ModelAttribute("message") @Valid Message message, BindingResult result) throws MessagingException {

    if (result.hasErrors()){
      Topic topic = tr.findOne(idTopic);
      model.addAttribute("messageList", topic.getMessageList());
      model.addAttribute("topic", topic);
      model.addAttribute("message", message);
      //result.rejectValue("contenu", "");
      return "message/message";
    }

    Topic topic = tr.findOne(idTopic);
    message.setDate(new Date());
    Utilisateur utilisateur = us.getLoggedUser();
    message.setUtilisateur(utilisateur);
    Message messageSaved = mr.save(message);
    List<Message> listeMessage = topic.getMessageList();
    listeMessage.add(messageSaved);
    tr.save(topic);
    sendEmail(topic);
    return "redirect:/projets/{id}/topics/{idTopic}/messages";

  }

  private void sendEmail(Topic topic) throws MessagingException {

    if (!topic.getFollowerList().isEmpty()) {
      String[] mails = topic.getFollowerList().stream().map(Utilisateur::getEmail)
          .toArray(String[]::new);
      MimeMessage message = sender.createMimeMessage();

      MimeMessageHelper helper = new MimeMessageHelper(message);

      String link = "http://localhost:8082/projets/" + topic.getProjet().getId()
          + "/topics/" + topic.getId() + "/messages";

      helper.setFrom("SpringSocial");
      helper.setTo(mails);
      helper.setText("<html>"
          + "<body>"
          + "<h1>Nouveau message dans le topic " + topic.getNom() + "</h1>"
          + "<p>Vous suivez le topic et un nouveau message a été posté.</p>"
          + "<p>Cliquez <a href=" + link
          + ">ici</a> pour voir les messages.</p>"
          + "</body>"
          + "</html>", true);
      helper.setSubject("Nouveau message");

      sender.send(message);
    }

  }


}
