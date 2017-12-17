package com.miage.m2.webappforum.repository;

import com.miage.m2.webappforum.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, String> {

  Role getRoleByName(String name);

}
