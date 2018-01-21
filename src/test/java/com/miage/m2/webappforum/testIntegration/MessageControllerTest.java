package com.miage.m2.webappforum.testIntegration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.miage.m2.webappforum.WebappForumApplication;

import com.miage.m2.webappforum.control.MessageController;
import com.miage.m2.webappforum.entity.Message;
import com.miage.m2.webappforum.entity.Projet;
import com.miage.m2.webappforum.entity.Topic;
import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.*;
import com.miage.m2.webappforum.service.UserService;
import org.apache.tools.ant.Project;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import javax.sql.DataSource;
import javax.validation.constraints.AssertTrue;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class MessageControllerTest {


    private MockMvc mvc;


     ObjectMapper objectMapper = new ObjectMapper();

    MessageRepository mr = mock(MessageRepository.class);


    UtilisateurRepository ur = mock(UtilisateurRepository.class);


    ProjetRepository pr = mock(ProjetRepository.class);


    TopicRepository tr = mock(TopicRepository.class);

    private JacksonTester<Message> jsonTester;

    private Message messagePost;

    private Topic topic;

    private Projet projet;

    @InjectMocks
    MessageController msg;


    UserService us = mock(UserService.class);

    @Before
    public void setUp() {
        JacksonTester.initFields(this, objectMapper);

        this.mvc = standaloneSetup(msg).build();
        Projet projet = new Projet();
        projet.setId("2");
        projet.setNom("COUCOU");
        projet.setDescription("coucou");
        pr.save(projet);
        this.projet = projet;
        Topic topic = new Topic();
        topic.setProjet(projet);
        topic.setMessageList(new ArrayList<>());
        topic.setFollowerList(new HashSet<>());
        tr.save(topic);
        this.topic = topic;
      //  when(tr.findOne("2")).thenReturn(topic);
        Utilisateur user = new Utilisateur();
        user.setId("5");
        user.setPseudo("5");
        user.setPassword("coucou");
        user.setEmail("coucou@mail.com");
        ur.save(user);
        when(us.getLoggedUser()).thenReturn(user);
        Message message = new Message();
        message.setContenu("post message");
        message.setDate(new Date());
        message.setUtilisateur(user);
        messagePost = message;

    }
    @Test
    public void testGetMessage() throws Exception {
        when(tr.findOne(anyString())).thenReturn(topic);
        this.mvc.perform(get("/projets/" + projet.getId()+"/topics/" + topic.getId()  +"/messages")).andExpect(view().name("message/message"));

    }
    @Test
    public void testSaveMessage() throws  Exception{
        when(tr.findOne(anyString())).thenReturn(topic);
        final String msgJson = jsonTester.write(messagePost).getJson();
        this.mvc.perform(post("/projets/" + projet.getId()+"/topics/" + topic.getId()  +"/messages/save")
                .contentType(MediaType.APPLICATION_JSON).content(msgJson))
                .andExpect(view().name("redirect:/projets/{id}/topics/{idTopic}/messages")
                ).andExpect(status().is3xxRedirection());

    }

}
