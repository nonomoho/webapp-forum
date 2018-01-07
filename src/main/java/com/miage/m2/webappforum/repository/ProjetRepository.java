package com.miage.m2.webappforum.repository;

import com.miage.m2.webappforum.entity.Projet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostFilter;

public interface ProjetRepository extends CrudRepository<Projet, String> {

  @Override
  @PostFilter("hasPermission(filterObject, T(com.miage.m2.webappforum.entity.TypePermissionEnum).READ) or hasRole('ADMIN')")
  Iterable<Projet> findAll();

  Boolean existsByNom(String nom);

}
