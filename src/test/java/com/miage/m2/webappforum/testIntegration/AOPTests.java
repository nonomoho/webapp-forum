package com.miage.m2.webappforum.testIntegration;

import com.miage.m2.webappforum.entity.Message;
import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.MessageRepository;
import java.util.Arrays;
import java.util.Date;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.miage.m2.webappforum.testIntegration"})
class SpringContextAOP {

}

@Aspect
@Component
class MyAspect {

  @Before("execution(* com.miage.m2.webappforum.testIntegration.*(..))")
  public void before(JoinPoint joinPoint) {
    System.out.print("Before ");
    System.out.print(joinPoint.getSignature().getName() + " called with ");
    System.out.println(Arrays.toString(joinPoint.getArgs()));
  }

  @AfterReturning(pointcut = "execution(* com.miage.m2.webappforum.testIntegration.*(..))", returning = "result")
  public void after(JoinPoint joinPoint, Object result) {
    System.out.print("After ");
    System.out.print(joinPoint.getSignature().getName());
    System.out.println(" result is " + result);
  }

}

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringContextAOP.class)
public class AOPTests {

  @Autowired
  AddMessageService service;

  @Test
  public void testMessages() {

    service.add1000Messages();

  }


  @Component
  class AddMessageService {

    @Autowired
    MessageRepository messageRepository;

    private void add1000Messages() {

      for (int i = 0; i < 1000; i++) {
        Message message = new Message();
        message.setContenu("Bonjour");
        message.setDate(new Date());
        message.setUtilisateur(new Utilisateur());

        messageRepository.save(message);
      }
    }
  }

}
