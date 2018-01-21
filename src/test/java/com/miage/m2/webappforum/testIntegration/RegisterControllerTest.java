package com.miage.m2.webappforum.testIntegration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.miage.m2.webappforum.WebappForumApplication;
import com.miage.m2.webappforum.control.ProjetController;
import com.miage.m2.webappforum.control.RegisterController;
import com.miage.m2.webappforum.entity.Projet;
import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import com.miage.m2.webappforum.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@SpringBootTest(classes = {WebappForumApplication.class})
@WebAppConfiguration
public class RegisterControllerTest {

    private MockMvc mvc;

    private ObjectMapper objectMapper = new ObjectMapper();


    private JacksonTester<Utilisateur> jsonTester;

    Utilisateur user;


    private UtilisateurRepository ur = mock(UtilisateurRepository.class);

    @Before
    public void setUp() {
        JacksonTester.initFields(this, objectMapper);
        RegisterController reg = mock(RegisterController.class);
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/resources/templates/register");
        resolver.setSuffix(".html");
        this.mvc = standaloneSetup(reg).setViewResolvers(resolver).build();
        Utilisateur user = new Utilisateur();
        user.setPseudo("test");
        user.setEmail("test@mail.com");
        user.setPassword("test");
        user.setId("123456");
        user.setInscription(new Date());
        this.user = user;
        ur.save(user);


    }

    @Test
    public void testPostRegister() throws Exception {

        final String userPost = jsonTester.write(user).getJson();
        this.mvc.perform(post("/register").param("password_confirm","test")
                .contentType(MediaType.APPLICATION_JSON).content(userPost))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRegister() throws Exception {

        this.mvc.perform(get("/register")).andExpect(status().isOk());

    }

}
