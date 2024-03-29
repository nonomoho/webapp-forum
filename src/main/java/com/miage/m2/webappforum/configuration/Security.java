package com.miage.m2.webappforum.configuration;

import com.miage.m2.webappforum.entity.Role;
import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.RoleRepository;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import java.util.Date;
import java.util.HashSet;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class Security extends WebSecurityConfigurerAdapter {


  @Qualifier("dataSource")
  @Autowired
  DataSource dataSource;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    auth.jdbcAuthentication()
        .dataSource(dataSource)
        .passwordEncoder(passwordEncoder)
        .usersByUsernameQuery("SELECT pseudo, password, 1 FROM utilisateur WHERE pseudo=?")
        .authoritiesByUsernameQuery(
            "select u.pseudo, r.name from utilisateur u join role_utilisateurs ru on u.id = utilisateurs_id join role r on ru.roles_id = r.id where pseudo = ?");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers("/login").permitAll()
        .antMatchers("/test").access("hasRole('USER')")
        .antMatchers("/admin/**").access("hasRole('ADMIN')")
        .and()
        .formLogin().loginPage("/login")
        .defaultSuccessUrl("/")
        .failureUrl("/login?error")
        .usernameParameter("username").passwordParameter("password")
        .and()
        .logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout")
        .and()
        .csrf();
  }

  @Bean
  public PrincipalExtractor principalExtractor(UtilisateurRepository utilisateurRepository,
      RoleRepository roleRepository) {
    return map -> {
      Utilisateur user = utilisateurRepository.findFirstByPseudo((String) map.get("given_name"));
//      System.out.println(map);
      if (user == null) {
        user = new Utilisateur();
        user.setId((String) map.get("id"));
        user.setEmail((String) map.get("email"));
        user.setPseudo((String) map.get("given_name"));
        user.setPassword("google");
        user.setInscription(new Date());
        utilisateurRepository.save(user);

        Role role = roleRepository.getRoleByName("USER");
        role.getUtilisateurs().add(user);
        roleRepository.save(role);
      }
      return user;
    };
  }

  @Bean
  public boolean initData(UtilisateurRepository utilisateurRepository,
      RoleRepository roleRepository) {

    Utilisateur u = utilisateurRepository.findFirstByPseudo("admin");
    if (u == null) {
      u = new Utilisateur();
      u.setPseudo("admin");
      u.setPassword(passwordEncoder.encode("admin"));
      u.setEmail("admin@admin");
      u.setInscription(new Date());
      utilisateurRepository.save(u);
    }

    String[] roles = {"ADMIN", "USER"};
    for (String role : roles) {
      Role r = roleRepository.getRoleByName(role);
      if (r == null) {
        r = new Role();
        r.setName(role);
        r.setUtilisateurs(new HashSet<>());
        if (role.equals("ADMIN")) {
          r.getUtilisateurs().add(u);
        }
        roleRepository.save(r);
      }
    }

    return true;

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}



