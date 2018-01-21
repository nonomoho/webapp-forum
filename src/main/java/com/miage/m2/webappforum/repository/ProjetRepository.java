package com.miage.m2.webappforum.repository;

import com.miage.m2.webappforum.entity.Projet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.transaction.annotation.Transactional;

public interface ProjetRepository extends CrudRepository<Projet, String> {

  @Transactional
  @Override
  @PostFilter("hasPermission(filterObject, T(com.miage.m2.webappforum.entity.TypePermissionEnum).READ)")
  Iterable<Projet> findAll();

  @Transactional
  Boolean existsByNom(String nom);

}
