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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    return "message/message";
  }

  @GetMapping(value = "/projets/{id}/topics/{idTopic}/messages/add")
  public String addMessageForm(@PathVariable("id") String id,
      @PathVariable("idTopic") String idTopic, Model model) {
    model.addAttribute("message", new Message());
    Projet projet = pr.findOne(id);
    Topic topic = tr.findOne(idTopic);
    model.addAttribute("topic", topic);
    model.addAttribute("projet", projet);
    return "message/formMessage";

  }


  @PostMapping(value = "projets/{id}/topics/{idTopic}/messages/save")
  public String addMessage(@PathVariable("idTopic") String idTopic,
      @ModelAttribute("message") @Valid Message message) throws MessagingException {
    Topic topic = tr.findOne(idTopic);
    message.setContenu(message.getContenu());
    message.setDate(new Date());
    Utilisateur utilisateur = us.getLoggedUser();
    message.setUtilisateur(utilisateur);
    Message messageSaved = mr.save(message);
    List<Message> listeMessage = topic.getMessageList();
    listeMessage.add(messageSaved);
    tr.save(topic);
    //gtsendEmail(topic);
    return "redirect:/projets/{id}/topics/{idTopic}/messages";

  }

  private void sendEmail(Topic topic) throws MessagingException {

    String[] mails = topic.getFollowerList().stream().map(Utilisateur::getEmail)
        .toArray(String[]::new);

    MimeMessage message = sender.createMimeMessage();

    MimeMessageHelper helper = new MimeMessageHelper(message);

    helper.setFrom("SpringSocial");
    helper.setTo(mails);
    helper.setSubject("Un nouveau message a été posté");
    helper.setText("How are you?", true);

    sender.send(message);

  }


}
