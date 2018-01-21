package com.miage.m2.webappforum.repository;

import com.miage.m2.webappforum.entity.Utilisateur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, String> {

  @Transactional
  Boolean existsByPseudoAndPassword(String pseudo, String password);

  @Transactional
  Boolean existsByPseudo(String peudo);

  @Transactional
  Boolean existsByEmail(String email);

  @Transactional
  Utilisateur findFirstByPseudo(String pseudo);

  @Transactional
  @Query("SELECT u from Utilisateur  u WHERE u <> ?1 AND u.pseudo <> 'admin'")
  Iterable<Utilisateur> findAll(Utilisateur user);

}
