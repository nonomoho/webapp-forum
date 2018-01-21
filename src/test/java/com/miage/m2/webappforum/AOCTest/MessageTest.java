package com.miage.m2.webappforum.AOCTest;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.miage.m2.webappforum.AOC.MyAspect;
import com.miage.m2.webappforum.WebappForumApplication;
import com.miage.m2.webappforum.repository.MessageRepository;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import com.miage.m2.webappforum.service.MessageService;
import com.miage.m2.webappforum.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {MyAspect.class})
@SpringBootTest(classes = {WebappForumApplication.class,MyAspect.class})
@EnableAspectJAutoProxy
public class MessageTest {

  MockMvc mvc;

  UtilisateurRepository ur = mock(UtilisateurRepository.class);
  MessageRepository mr = mock(MessageRepository.class);
  @Autowired
  MyAspect ma;


  @InjectMocks
  private MessageService messageService;

  @Before
  public void setUp() {
    mvc = standaloneSetup(messageService).build();
  }

  @Test
  public void testMessages() {
    messageService.addMessages(10000);
  }


}
