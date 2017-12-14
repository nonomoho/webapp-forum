package com.miage.m2.webappforum.repository;

import com.miage.m2.webappforum.entity.Utilisateur;
import org.springframework.data.repository.CrudRepository;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, String> {

  Boolean existsByPseudoAndPassword(String pseudo, String password);
}
