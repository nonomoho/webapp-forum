package com.miage.m2.webappforum.testServices;

import com.miage.m2.webappforum.WebappForumApplication;
import com.miage.m2.webappforum.entity.Projet;
import com.miage.m2.webappforum.entity.TypePermissionEnum;
import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.PermissionRepository;
import com.miage.m2.webappforum.repository.ProjetRepository;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import com.miage.m2.webappforum.service.PermissionService;
import com.miage.m2.webappforum.service.UserPermissionEvaluator;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {WebappForumApplication.class,UserService.class ,PermissionService.class,ProjetRepository.class,PermissionRepository.class ,UserPermissionEvaluator.class})
@DataJpaTest
public class UserPermissionEvaluatorTest {

    @Autowired
    UserPermissionEvaluator UserpermissionService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    UserService us;

    @Autowired
    ProjetRepository pr;

    @Autowired
    UtilisateurRepository ur;

    @Autowired
    PermissionRepository permr;

    Utilisateur user;

    Projet projet;

    Authentication authentication;

    @Before
    public void onSetUp() throws Exception {
        Utilisateur user = new Utilisateur();
        user.setPseudo("monsieur 26");
        user.setEmail("test@mail.com");
        user.setPassword("test");
        user.setId("26");
        user.setRoles(new HashSet<>());
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


        Authentication auth =  new UsernamePasswordAuthenticationToken(user,null);
        authentication = auth;
        SecurityContextHolder.getContext().setAuthentication(auth);


    }

    @Test
    public void testPermissionReadTrue() throws Exception {
        HashSet users = new HashSet<>();
        users.add(user.getId());
        projet.setReadUsers(users);
        pr.save(projet);
        //faut set les permissions dans ce test aussi car il s'appuie dessus
        permissionService.setPermission(projet);

        Boolean result = UserpermissionService.hasPermission(authentication,projet, TypePermissionEnum.READ);
        Assert.assertEquals(true,result);
    }

    @Test
    public void testPermissionReadTrueWithoutDirectRights() throws Exception {

        Projet proj = new Projet();
        proj.setNom("permission2");
        proj.setReadUsers(new HashSet<>());
        proj.setWriteUsers(new HashSet<>());
        pr.save(proj);
        //faut set les permissions dans ce test aussi car ce test s'appuie dessus
        permissionService.setPermission(proj);

        Boolean result = UserpermissionService.hasPermission(authentication,proj, TypePermissionEnum.READ);
        Assert.assertEquals(true,result);
    }

    @Test
    public void testPermissionWriteTrueWithoutDirectRights() throws Exception {

        Projet proj = new Projet();
        proj.setNom("permission3");
        proj.setReadUsers(new HashSet<>());
        proj.setWriteUsers(new HashSet<>());
        pr.save(proj);
        //faut set les permissions dans ce test aussi car ce test s'appuie dessus
        permissionService.setPermission(proj);

        Boolean result = UserpermissionService.hasPermission(authentication,proj, TypePermissionEnum.WRITE);
        Assert.assertEquals(false,result);
    }

    @Test
    public void testPermissionEditFalse() throws Exception {
        HashSet writers = new HashSet<>();
        writers.add(user.getId());
        Projet proj = new Projet();
        proj.setNom("permission4");
        proj.setReadUsers(new HashSet<>());
        proj.setWriteUsers(writers);
        pr.save(proj);

        //faut set les permissions dans ce test aussi car ce test s'appuie dessus
        permissionService.setPermission(proj);

        Boolean result = UserpermissionService.hasPermission(authentication,proj, TypePermissionEnum.EDIT);
        Assert.assertEquals(false,result);
    }
}
