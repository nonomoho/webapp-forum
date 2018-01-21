package com.miage.m2.webappforum.testIntegration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miage.m2.webappforum.WebappForumApplication;
import com.miage.m2.webappforum.control.ProjetController;
import com.miage.m2.webappforum.control.TopicController;
import com.miage.m2.webappforum.entity.Projet;
import com.miage.m2.webappforum.entity.Topic;
import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.*;
import com.miage.m2.webappforum.service.PermissionService;
import com.miage.m2.webappforum.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class TopicControllerTest {


    TopicRepository tr = mock(TopicRepository.class);

    PermissionService ps = mock(PermissionService.class);

    UserService us = mock(UserService.class);

    ProjetRepository pr = mock(ProjetRepository.class);

    UtilisateurRepository ur = mock(UtilisateurRepository.class);



    @InjectMocks
    TopicController topicController;

    Topic topicPost;

    private ObjectMapper objectMapper = new ObjectMapper();

    private JacksonTester<Topic> jsonTester;

    MockMvc mvc;

    @Before
    public void setUp() {
        JacksonTester.initFields(this, objectMapper);
       // TopicController proj = new TopicController();
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/resources/templates/topic");
        resolver.setSuffix(".html");
        mvc = standaloneSetup(topicController).setViewResolvers(resolver).build();
        Topic topic = new Topic();
        topic.setId("2");
        topicPost = topic;
        when(tr.findOne(anyString())).thenReturn(topic);

        List topicsList = new ArrayList<Topic>();
        topicsList.add(topic);

        Projet projet = new Projet();
        projet.setDescription("mock");
        projet.setNom("mock");
        projet.setTopicList(topicsList);

        when(pr.findOne(anyString())).thenReturn(projet);
        when(tr.findByProjet(any(Projet.class))).thenReturn(projet.getTopicList());

        Utilisateur user = new Utilisateur();
        HashSet<Topic> topics = new HashSet<>();
        HashSet<Utilisateur> users = new HashSet<>();
        topics.add(new Topic());
        users.add(user);
         user.setFollowTopicList(topics);
        topic.setFollowerList(users);
        when(us.getLoggedUser()).thenReturn(user);
        when(ur.save(any(Utilisateur.class))).thenReturn(user);


    }


    @Test
    public void testGetProjetTopic() throws Exception {

        //when(ps.getPermission(any(Projet.class)))
        this.mvc.perform(get("/projets/mock/topics/add")).andExpect(status().isOk()).andExpect(view().name("topic/singleTopic"));

    }
    @Test
    public void testGetTopic() throws Exception {


        this.mvc.perform(get("/followedTopics")).andExpect(status().isOk()).andExpect(view().name("topic/topic"));

    }
    @Test
    public void testGetTopicOfProjet() throws Exception {


        this.mvc.perform(get("/projets/2/topics")).andExpect(status().isOk()).andExpect(view().name("topic/topic"));

    }
    @Test
    public void testPostTopic() throws Exception {
        final String topicJson = jsonTester.write(topicPost).getJson();
        this.mvc.perform(post("/topics/2/follow").contentType(MediaType.APPLICATION_JSON).content(topicJson)).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/followedTopics"));
    }

    @Test
    public void testEditPostTopic() throws Exception {

        when(tr.save(any(Topic.class))).thenReturn(new Topic());
        this.mvc.perform(post("/projets/mock/topics/save").param("nom", "")
                .param("ouvert", String.valueOf(false))
                .param("readUsers",new String[]{"bob", "margret"})
                .param("writeUsers",new String[]{"bob", "margret"})).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/projets/{id}/topics"));
    }

    @Test
    public void testEditTopicForm() throws Exception {

        //when(ps.getPermission(any(Projet.class)))
        this.mvc.perform(get("/projets/mock/topics/edit/2")).andExpect(status().isOk()).andExpect(view().name("topic/singleTopic"));

    }
}
