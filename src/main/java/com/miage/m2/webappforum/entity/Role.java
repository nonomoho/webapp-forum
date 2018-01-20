package com.miage.m2.webappforum.entity;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  private String name;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable
  private Set<Utilisateur> utilisateurs;

  public Role() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Utilisateur> getUtilisateurs() {
    return utilisateurs;
  }

  public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
    this.utilisateurs = utilisateurs;
  }

  @Override
  public String getAuthority() {
    return name;
  }
}
