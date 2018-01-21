package com.miage.m2.webappforum.repository;

import com.miage.m2.webappforum.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RoleRepository extends CrudRepository<Role, String> {

  @Transactional
  Role getRoleByName(String name);

}
