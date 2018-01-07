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

//  public Utilisateur getLoggedUser() {
//
//    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    System.out.println(user.getUsername());
//    return (ur.findFirstByPseudo(user.getUsername()));
////
////    Authentication a = SecurityContextHolder.getContext().getAuthentication();
////
////    Map<String, String> map = (Map<String, String>) ((OAuth2Authentication) a)
////        .getUserAuthentication().getDetails();
////    System.out.println(map.get("given_name"));
////    return (ur.findFirstByPseudo(map.get("given_name")));
//
//  }
 public Utilisateur getLoggedUser() {
    try {
      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      return (ur.findFirstByPseudo(user.getUsername()));
    } catch (Exception e) {
      return null;
    }
  }
}
