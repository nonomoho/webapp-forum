package com.miage.m2.webappforum.repository;

import com.miage.m2.webappforum.entity.Projet;
import com.miage.m2.webappforum.entity.Topic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostFilter;

public interface TopicRepository extends CrudRepository<Topic, String> {

  @PostFilter("hasPermission(filterObject, T(com.miage.m2.webappforum.entity.TypePermissionEnum).READ)")
  Iterable<Topic> findByProjet(Projet projet);
}
