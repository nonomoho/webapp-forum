package com.miage.m2.webappforum.service;

import com.miage.m2.webappforum.entity.Permission;
import com.miage.m2.webappforum.entity.TargetPermission;
import com.miage.m2.webappforum.entity.TypePermissionEnum;
import com.miage.m2.webappforum.repository.PermissionRepository;
import com.miage.m2.webappforum.repository.UtilisateurRepository;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

  @Autowired
  UtilisateurRepository ur;
  @Autowired
  PermissionRepository permr;

  private Set<Permission> createPermission(Set<String> listIdUser,
      TypePermissionEnum typePermissionEnum, TargetPermission targetPermission) {
    Set<Permission> permissions = new HashSet<>();
    if (listIdUser != null) {
      listIdUser.forEach(ru -> permissions.add(
          new Permission(typePermissionEnum, ur.findOne(ru), targetPermission)));
    }
    return permissions;
  }

  public void getPermission(TargetPermission targetPermission) {
    targetPermission.setReadUsers(
        permr.findAllByTargetPermissionAndType(targetPermission, TypePermissionEnum.READ).stream()
            .map(p -> p.getUtilisateur().getId())
            .collect(Collectors.toSet()));

    targetPermission.setWriteUsers(
        permr.findAllByTargetPermissionAndType(targetPermission, TypePermissionEnum.WRITE).stream()
            .map(p -> p.getUtilisateur().getId())
            .collect(Collectors.toSet()));
  }

  public void setPermission(TargetPermission targetPermission){
    //delete all existing permissions for this project
    permr.deleteByTargetPermission(targetPermission);

    //create new permission
    Set<Permission> permissions = new HashSet<>();
    permissions.addAll(createPermission(targetPermission.getReadUsers(), TypePermissionEnum.READ,
        targetPermission));
    permissions.addAll(createPermission(targetPermission.getWriteUsers(), TypePermissionEnum.WRITE,
        targetPermission));
    permr.save(permissions);
  }

}
