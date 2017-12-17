package com.miage.m2.webappforum.configuration;

import javax.sql.DataSource;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class Security extends WebSecurityConfigurerAdapter {

  @Autowired
  DataSource dataSource;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("user").password("password")
        .roles("USER").and().withUser("admin").password("admin").roles("ADMIN");
    auth.jdbcAuthentication()
        .dataSource(dataSource)
        .passwordEncoder(passwordEncoder)
        .usersByUsernameQuery("SELECT pseudo, password, 1 FROM utilisateur WHERE pseudo=?")
        .authoritiesByUsernameQuery("select pseudo, 'USER' from utilisateur where pseudo=?");

  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers("/login").permitAll()
        .antMatchers("/test").access("hasRole('USER')")
        .antMatchers("/admin/**").access("hasRole('ADMIN')")
        .and()
        .formLogin().loginPage("/login")
        .defaultSuccessUrl("/login")
        .failureUrl("/login?error")
        .usernameParameter("username").passwordParameter("password")
        .and()
        .csrf();

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
