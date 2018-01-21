package com.miage.m2.webappforum.AOCTest;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.miage.m2.webappforum.control.MessageController;
import com.miage.m2.webappforum.AOC.MyAspect;
import com.miage.m2.webappforum.repository.MessageRepository;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import com.miage.m2.webappforum.service.MessageService;
import javax.annotation.processing.SupportedAnnotationTypes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@RunWith(MockitoJUnitRunner.class)
@SupportedAnnotationTypes(value = "LogExecutionTimes")
public class MessageTest {

  MockMvc mvc;

  UtilisateurRepository ur = mock(UtilisateurRepository.class);
  MessageRepository mr = mock(MessageRepository.class);
  MyAspect ma = mock(MyAspect.class);


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
