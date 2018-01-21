package com.miage.m2.webappforum.testServices;

import com.miage.m2.webappforum.WebappForumApplication;
import com.miage.m2.webappforum.entity.Topic;
import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.TopicRepository;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import com.miage.m2.webappforum.service.FollowService;
import com.miage.m2.webappforum.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashSet;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {WebappForumApplication.class,FollowService.class,UserService.class})
@DataJpaTest
public class FollowServiceTest {

    @Autowired
    FollowService followService;

    @Autowired
    TopicRepository tr;

    @Autowired
    UtilisateurRepository ur;

    @Autowired
    UserService us;

    Utilisateur user;

    Topic topic;

    @Before
    public void onSetUp() throws Exception {

        Utilisateur user = new Utilisateur();
        user.setPseudo("test");
        user.setEmail("test@mail.com");
        user.setPassword("test");
        user.setId("123456");
        user.setFollowTopicList(new HashSet<>());
        user.setInscription(new Date());
        this.user = user;
        ur.save(user);

        Authentication auth =  new UsernamePasswordAuthenticationToken(user,null);
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    @Test
    public void testTopicFollowing() throws Exception {
        Topic topic = new Topic();
        topic.setId("2");
        tr.save(topic);
        followService.addTopicFollowing("2");

        Utilisateur utilisateur = us.getLoggedUser();
        Assert.assertEquals(1,utilisateur.getFollowTopicList().size());


    }

}
