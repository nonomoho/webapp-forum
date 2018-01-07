package com.miage.m2.webappforum;

import com.miage.m2.webappforum.entity.Role;
import com.miage.m2.webappforum.repository.RoleRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebappForumApplication {

  @Autowired
  RoleRepository rr;

  public static void main(String[] args) {
    SpringApplication.run(WebappForumApplication.class, args);
  }

  @Bean
  InitializingBean sendDatabase() {
    return () -> {
      Role r = rr.getRoleByName("USER");
      if (r == null) {
        Role role = new Role();
        role.setName("USER");
        rr.save(role);
      }
    };
  }

}
