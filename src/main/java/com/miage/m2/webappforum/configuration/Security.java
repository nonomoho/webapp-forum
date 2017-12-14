package com.miage.m2.webappforum.configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

  @Autowired
  DataSource dataSource;

  @Autowired
  protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("user").password("password")
        .roles("USER").and().withUser("admin").password("admin").roles("ADMIN");
    auth.jdbcAuthentication()
        .dataSource(dataSource)
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
}
