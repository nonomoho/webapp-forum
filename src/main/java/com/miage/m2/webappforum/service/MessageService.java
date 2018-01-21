package com.miage.m2.webappforum.service;

import com.miage.m2.webappforum.AOC.LogExecutionTimes;
import com.miage.m2.webappforum.entity.Message;
import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.MessageRepository;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class MessageService {
  @Autowired
  UtilisateurRepository ur;

  @Autowired
  MessageRepository mr;

  @LogExecutionTimes
  public void addMessages(int nb) {
    Utilisateur utilisateur = new Utilisateur();
    utilisateur.setPseudo("test6");
    utilisateur.setEmail("test6@test.test");
    utilisateur.setPassword("test2");
    utilisateur.setInscription(new Date());
    ur.save(utilisateur);

    List<Message> messages = new ArrayList<>();
    for (int i = 0; i <= nb; i++) {
      Message message = new Message();
      message.setUtilisateur(utilisateur);
      message.setDate(new Date());
      message.setContenu("test content");
      messages.add(message);
    }
    mr.save(messages);
  }

}
