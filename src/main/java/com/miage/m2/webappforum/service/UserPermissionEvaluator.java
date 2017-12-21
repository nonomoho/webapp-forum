package com.miage.m2.webappforum.service;

import com.miage.m2.webappforum.entity.Projet;
import com.miage.m2.webappforum.entity.Utilisateur;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionEvaluator implements PermissionEvaluator {

  @Autowired
  UserService us;

  @Override
  public boolean hasPermission(Authentication authentication, Object targetDomainObject,
      Object permission) {
    Utilisateur utilisateur = us.getLoggedUser();
    if (targetDomainObject instanceof Projet) {
      Projet projet = (Projet) targetDomainObject;
      if (permission.toString().equals("READ")) {
        return projet.getReadUsers().contains(utilisateur);
      }
      if (permission.toString().equals("WRITE")) {
        return projet.getWriteUsers().contains(utilisateur);
      }
    }
    return false;
  }

  @Override
  public boolean hasPermission(Authentication authentication, Serializable targetId,
      String targetType, Object permission) {
    /*Utilisateur utilisateur = us.getLoggedUser();
    if (targetType.equals("Projet")) {
      Projet projet = pr.findOne(targetId.toString());
      if (permission.toString().equals("READ")) {
        return projet.getReadUsers().contains(utilisateur);
      }
      if (permission.toString().equals("WRITE")) {
        return projet.getWriteUsers().contains(utilisateur);
      }
    }*/

    return false;
  }


}
