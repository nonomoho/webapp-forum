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

import org.mockito.Mock;
import org.mockito.Mockito;
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

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebappForumApplication.class})
public class MessageControllerTest {


    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    MessageRepository mr = mock(MessageRepository.class);


    UtilisateurRepository ur = mock(UtilisateurRepository.class);


    ProjetRepository pr = mock(ProjetRepository.class);


    TopicRepository tr = mock(TopicRepository.class);

    private JacksonTester<Message> jsonTester;

    private Message messagePost;

    @Autowired
    UserService us;

    @Before
    public void setUp() {
        JacksonTester.initFields(this, objectMapper);
        MessageController msg = mock(MessageController.class);
        this.mvc = standaloneSetup(msg).alwaysExpect(status().isOk()).build();
        Projet projet = new Projet();
        projet.setId("2");
        projet.setNom("COUCOU");
        projet.setDescription("coucou");
        pr.save(projet);
        Topic topic = new Topic();
        topic.setProjet(projet);
        topic.setId("2");
        tr.save(topic);
      //  when(tr.findOne("2")).thenReturn(topic);
        Utilisateur user = new Utilisateur();
        user.setId("5");
        user.setPseudo("5");
        user.setPassword("coucou");
        user.setEmail("coucou@mail.com");
        ur.save(user);
        Message message = new Message();
        message.setContenu("post message");
        message.setDate(new Date());
        message.setUtilisateur(user);
        messagePost = message;

    }
    @Test
    public void testGetMessage() throws Exception {

        this.mvc.perform(get("/projets/2/topics/2/messages")).andExpect(view().name("projets/2/topics/2/messages"));

    }
    @Test
    public void testSaveMessage() throws  Exception{

        final String msgJson = jsonTester.write(messagePost).getJson();
        this.mvc.perform(post("/projets/2/topics/2/messages/save")
                .contentType(MediaType.APPLICATION_JSON).content(msgJson))
                .andExpect(view().name("projets/2/topics/2/messages/save")
                ).andExpect(status().isOk());

    }

    @Test
    public void testAddMessage() throws Exception {

        this.mvc.perform(get("/projets/2/topics/2/messages/add")).andExpect(view().name("projets/2/topics/2/messages/add"));

    }

}
