package com.miage.m2.webappforum.repository;

import com.miage.m2.webappforum.entity.Projet;
import com.miage.m2.webappforum.entity.Topic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.transaction.annotation.Transactional;

public interface TopicRepository extends CrudRepository<Topic, String> {

  @Transactional
  @PostFilter("hasPermission(filterObject, T(com.miage.m2.webappforum.entity.TypePermissionEnum).READ)")
  Iterable<Topic> findByProjet(Projet projet);

  @Transactional
  Boolean existsByNomAndProjet(String nom, Projet projet);

}
