package com.miage.m2.webappforum.service;

import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UtilisateurRepository ur;

  public Utilisateur getLoggedUser() {

    if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      return (ur.findFirstByPseudo(user.getUsername()));
    }
    if (SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal() instanceof Utilisateur) {
      return (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }
    return null;


  }
}
