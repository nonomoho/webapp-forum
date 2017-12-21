package com.miage.m2.webappforum.service;

import com.miage.m2.webappforum.entity.ObjectPermissionEnum;
import com.miage.m2.webappforum.entity.Permission;
import com.miage.m2.webappforum.entity.Projet;
import com.miage.m2.webappforum.entity.TypePermissionEnum;
import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.PermissionRepository;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionEvaluator implements PermissionEvaluator {

  @Autowired
  UserService us;

  @Autowired
  PermissionRepository pr;

  @Override
  public boolean hasPermission(Authentication authentication, Object targetDomainObject,
      Object permission) {
    Utilisateur utilisateur = us.getLoggedUser();
    if (targetDomainObject instanceof Projet) {
      Projet projet = (Projet) targetDomainObject;
      Permission perm = pr.findFirstByUtilisateurAndTypeObjectAndObjectIdAndType(utilisateur,
          ObjectPermissionEnum.PROJET, projet.getId(),
          (TypePermissionEnum) permission);
      return perm != null;
    }
    return false;
  }

  @Override
  public boolean hasPermission(Authentication authentication, Serializable targetId,
      String targetType, Object permission) {
    Utilisateur utilisateur = us.getLoggedUser();
    if (targetType.equals("Projet")) {
      Permission perm = pr.findFirstByUtilisateurAndTypeObjectAndObjectIdAndType(utilisateur,
          ObjectPermissionEnum.PROJET, targetId.toString(), (TypePermissionEnum) permission);
      return perm != null;
    }
    return false;
  }


}
