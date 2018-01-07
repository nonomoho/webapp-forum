package com.miage.m2.webappforum.repository;

import com.miage.m2.webappforum.entity.Permission;
import com.miage.m2.webappforum.entity.TargetPermission;
import com.miage.m2.webappforum.entity.TypePermissionEnum;
import com.miage.m2.webappforum.entity.Utilisateur;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PermissionRepository extends CrudRepository<Permission, String> {

  @Transactional
  void deleteByTargetPermission(TargetPermission targetPermission);

  Permission findFirstByUtilisateurAndTargetPermissionAndType(Utilisateur utilisateur,
      TargetPermission targetPermission, TypePermissionEnum type);

  List<Permission> findAllByTargetPermissionAndType(TargetPermission targetPermission,
      TypePermissionEnum type);

}
