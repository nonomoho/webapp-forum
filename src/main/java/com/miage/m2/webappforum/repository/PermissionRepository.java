package com.miage.m2.webappforum.repository;

import com.miage.m2.webappforum.entity.ObjectPermissionEnum;
import com.miage.m2.webappforum.entity.Permission;
import com.miage.m2.webappforum.entity.TypePermissionEnum;
import com.miage.m2.webappforum.entity.Utilisateur;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PermissionRepository extends CrudRepository<Permission, String> {

  @Transactional
  void deleteByTypeObjectAndObjectId(ObjectPermissionEnum typeObject, String objectId);

  Permission findFirstByUtilisateurAndTypeObjectAndObjectIdAndType(Utilisateur utilisateur,
      ObjectPermissionEnum typeObject, String objectId, TypePermissionEnum type);

  List<Permission> findAllByTypeObjectAndObjectIdAndType(ObjectPermissionEnum typeObject,
      String objectId, TypePermissionEnum type);

}
