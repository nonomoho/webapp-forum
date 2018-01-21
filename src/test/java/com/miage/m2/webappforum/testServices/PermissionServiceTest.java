package com.miage.m2.webappforum.testServices;

import com.miage.m2.webappforum.WebappForumApplication;
import com.miage.m2.webappforum.entity.Permission;
import com.miage.m2.webappforum.entity.Projet;
import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.PermissionRepository;
import com.miage.m2.webappforum.repository.ProjetRepository;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import com.miage.m2.webappforum.service.FollowService;
import com.miage.m2.webappforum.service.PermissionService;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {WebappForumApplication.class,ProjetRepository.class ,PermissionService.class})
@DataJpaTest
public class PermissionServiceTest {

    @Autowired
    PermissionService permissionService;

    @Autowired
    ProjetRepository pr;

    @Autowired
    UtilisateurRepository ur;

    @Autowired
    PermissionRepository permr;

    Utilisateur user;

    Projet projet;

    @Before
    public void onSetUp() throws Exception {
        Utilisateur user = new Utilisateur();
        user.setPseudo("monsieur 26");
        user.setEmail("test@mail.com");
        user.setPassword("test");
        user.setId("26");
        user.setFollowTopicList(new HashSet<>());
        user.setInscription(new Date());
        this.user = user;
        ur.save(user);

        Projet projet = new Projet();

        projet.setNom("permission");
        projet.setReadUsers(new HashSet<>());
        projet.setWriteUsers(new HashSet<>());
        this.projet = projet;
        pr.save(projet);


        System.out.println(pr.findOne(projet.getId()).getNom());

        Authentication auth =  new UsernamePasswordAuthenticationToken(user,null);
        SecurityContextHolder.getContext().setAuthentication(auth);


    }

    @Test
    public void testPermissionRead() throws Exception {
        HashSet users = new HashSet<>();
        users.add(user.getId());
        projet.setReadUsers(users);
        pr.save(projet);

       // pr.findOne()
        permissionService.setPermission(projet);


        Assert.assertEquals(1,pr.findOne(projet.getId()).getReadUsers().size());
    }

    @Test
    public void testPermissionWrite() throws Exception {
        HashSet users = new HashSet<>();
        users.add(user.getId());
        projet.setWriteUsers(users);
        pr.save(projet);

        // pr.findOne()
        permissionService.setPermission(projet);


        Assert.assertEquals(1,pr.findOne(projet.getId()).getWriteUsers().size());
    }
}
