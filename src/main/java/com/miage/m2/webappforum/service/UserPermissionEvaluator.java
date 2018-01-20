package com.miage.m2.webappforum.service;

import com.miage.m2.webappforum.entity.Permission;
import com.miage.m2.webappforum.entity.Role;
import com.miage.m2.webappforum.entity.TargetPermission;
import com.miage.m2.webappforum.entity.Topic;
import com.miage.m2.webappforum.entity.TypePermissionEnum;
import com.miage.m2.webappforum.entity.Utilisateur;
import com.miage.m2.webappforum.repository.PermissionRepository;
import com.miage.m2.webappforum.repository.TargetPermissionRepository;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
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

    if (isAdmin()){
      return true;
    }

    TargetPermission target = (TargetPermission) targetDomainObject;

    if (permission == TypePermissionEnum.READ && !target.getNeedAuth()) {
      return true;
    }

    Utilisateur utilisateur = us.getLoggedUser();

    if (utilisateur != null) {
      Permission perm = pr.findFirstByUtilisateurAndTargetPermissionAndType(utilisateur,
          target, (TypePermissionEnum) permission);
      if (perm != null) {
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean hasPermission(Authentication authentication, Serializable targetId,
      String targetType, Object permission) {
    TargetPermission target = tpr.findOne((String) targetId);
    return hasPermission(authentication, target, permission);
  }

  public boolean isOwner(Topic topic) {
    if (topic == null) {
      return false;
    }
    return topic.getCreateur().equals(us.getLoggedUser());
  }


  public boolean isOwnerOfTopics(Set<Topic> topics) {
    for (Topic topic : topics) {
      if (isOwner(topic)) {
        return true;
      }
    }
    return false;
  }

  public boolean isAdmin(){
    Set<String> roles = us.getLoggedUser().getRoles().stream().map(Role::getName).collect(Collectors.toSet());
    return roles.contains("ADMIN");
  }


}
