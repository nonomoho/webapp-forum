package com.miage.m2.webappforum.service;

import com.miage.m2.webappforum.entity.ObjectPermissionEnum;
import com.miage.m2.webappforum.entity.Permission;
import com.miage.m2.webappforum.entity.Projet;
import com.miage.m2.webappforum.entity.TargetPermission;
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
    TargetPermission target = (TargetPermission) targetDomainObject;
    Permission perm = pr.findFirstByUtilisateurAndTargetPermissionIdAndType(utilisateur,
        target, (TypePermissionEnum) permission);
    if ( perm != null){
      return true;
    }
    return false;
  }

  @Override
  public boolean hasPermission(Authentication authentication, Serializable targetId,
      String targetType, Object permission) {
    Utilisateur utilisateur = us.getLoggedUser();
    TargetPermission target = (TargetPermission)

    if (targetType.equals("Projet")) {
      Permission perm = pr.findFirstByUtilisateurAndTargetPermissionIdAndType(utilisateur,
          ObjectPermissionEnum.PROJET, targetId.toString(), (TypePermissionEnum) permission);
      return perm != null;
    }
    return false;
  }


}
