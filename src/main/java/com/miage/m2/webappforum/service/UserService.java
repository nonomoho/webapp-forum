package com.miage.m2.webappforum.service;

import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UtilisateurRepository ur;

  public Utilisateur getLoggedUser() {

    if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      return (ur.findFirstByPseudo(user.getUsername()));
    } else {
      return (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }




  }
}
