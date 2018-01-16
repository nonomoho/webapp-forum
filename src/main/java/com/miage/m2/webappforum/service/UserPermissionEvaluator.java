package com.miage.m2.webappforum.service;

import com.miage.m2.webappforum.entity.Permission;
import com.miage.m2.webappforum.entity.TargetPermission;
import com.miage.m2.webappforum.entity.TypePermissionEnum;
import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.PermissionRepository;
import com.miage.m2.webappforum.repository.TargetPermissionRepository;
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

  @Autowired
  TargetPermissionRepository tpr;

  @Override
  public boolean hasPermission(Authentication authentication, Object targetDomainObject,
      Object permission) {

    TargetPermission target = (TargetPermission) targetDomainObject;

    if (!target.getNeedAuth()){
      return true;
    }

    Utilisateur utilisateur = us.getLoggedUser();
    Permission perm = pr.findFirstByUtilisateurAndTargetPermissionAndType(utilisateur,
        target, (TypePermissionEnum) permission);
    if ( perm != null){
      return true;
    }
    return false;
  }

  @Override
  public boolean hasPermission(Authentication authentication, Serializable targetId,
      String targetType, Object permission) {
    TargetPermission target = tpr.findOne((String) targetId);
    return hasPermission(authentication, target, permission);
  }


}
