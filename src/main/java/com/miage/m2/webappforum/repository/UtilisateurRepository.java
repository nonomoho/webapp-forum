package com.miage.m2.webappforum.repository;

import com.miage.m2.webappforum.entity.Utilisateur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, String> {

  Boolean existsByPseudoAndPassword(String pseudo, String password);

  Boolean existsByPseudo(String peudo);

  Boolean existsByEmail(String email);

  Utilisateur findFirstByPseudo(String pseudo);

  @Query("SELECT u from Utilisateur  u WHERE u <> ?1 AND u.pseudo <> 'admin'")
  Iterable<Utilisateur> findAll(Utilisateur user);

}
