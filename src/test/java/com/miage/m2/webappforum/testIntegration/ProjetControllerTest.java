package com.miage.m2.webappforum.testIntegration;


import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miage.m2.webappforum.WebappForumApplication;
import com.miage.m2.webappforum.control.ProjetController;
import com.miage.m2.webappforum.entity.Projet;
import com.miage.m2.webappforum.repository.ProjetRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@SpringBootTest(classes = {WebappForumApplication.class})
@WebAppConfiguration
public class ProjetControllerTest {


  private MockMvc mvc;


  private ObjectMapper objectMapper = new ObjectMapper();

  //@Autowired
  ProjetRepository pr = mock(ProjetRepository.class);

  // UtilisateurRepository ur = mock(UtilisateurRepository.class);

  //PermissionRepository permr = mock(PermissionRepository.class);


  Projet projetPost;

  Projet projetEditPost;


  private JacksonTester<Projet> jsonTester;


  @Before
  public void setUp() {
    JacksonTester.initFields(this, objectMapper);
    ProjetController proj = mock(ProjetController.class);
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/resources/templates/projet");
    resolver.setSuffix(".html");
    this.mvc = standaloneSetup(proj).alwaysExpect(status().isOk()).setViewResolvers(resolver)
        .build();
    Projet projet = new Projet();
    projet.setId("2");
    projet.setNom("COUCOU");
    projet.setDescription("coucou");
    pr.save(projet);
    projetEditPost = projet;

    Projet projet2 = new Projet();
    projet.setId("3");
    projet.setNom("COUCOU");
    projet.setDescription("coucou");

    projetPost = projet2;


  }

  @Test
  public void testGetProjet() throws Exception {

    RequestBuilder request = MockMvcRequestBuilders.get("/projets");
    this.mvc.perform(request);

  }

  @Test
  public void testAddProjet() throws Exception {

    this.mvc.perform(get("/projets/add")).andExpect(view().name("projets/add"));

  }

  @Test
  public void testSaveMessage() throws Exception {

    final String projetJson = jsonTester.write(projetPost).getJson();
    this.mvc.perform(post("/projets/save")
        .contentType(MediaType.APPLICATION_JSON).content(projetJson))
        .andExpect(status().isOk());

  }

  @Test
  public void testEditMessage() throws Exception {

    this.mvc.perform(get("/projets/edit/2"))
        .andExpect(status().isOk());

  }

}
