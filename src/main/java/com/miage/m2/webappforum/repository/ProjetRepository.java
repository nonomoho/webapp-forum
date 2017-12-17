package com.miage.m2.webappforum.repository;

import com.miage.m2.webappforum.entity.Projet;
import org.springframework.data.repository.CrudRepository;

public interface ProjetRepository extends CrudRepository<Projet, String> {

  Boolean existsByNom(String nom);

}
