package com.miage.m2.webappforum.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Permission {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;
  @Enumerated(EnumType.STRING)
  private TypePermissionEnum type;
  @ManyToOne
  private Utilisateur utilisateur;
  @ManyToOne
  private TargetPermission targetPermission;

  public Permission() {
  }

  public Permission(TypePermissionEnum type, Utilisateur utilisateur,
      TargetPermission targetPermission) {
    this.type = type;
    this.utilisateur = utilisateur;
    this.targetPermission = targetPermission;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public TypePermissionEnum getType() {
    return type;
  }

  public void setType(TypePermissionEnum type) {
    this.type = type;
  }

  public Utilisateur getUtilisateur() {
    return utilisateur;
  }

  public void setUtilisateur(Utilisateur utilisateur) {
    this.utilisateur = utilisateur;
  }

  public TargetPermission getTargetPermission() {
    return targetPermission;
  }

  public void setTargetPermission(TargetPermission targetPermission) {
    this.targetPermission = targetPermission;
  }
}
