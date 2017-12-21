package com.miage.m2.webappforum.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
  @Enumerated(EnumType.STRING)
  private ObjectPermissionEnum typeObject;
  private String objectId;

  public Permission() {
  }

  public Permission(TypePermissionEnum type, Utilisateur utilisateur,
      ObjectPermissionEnum typeObject, String objectId) {
    this.type = type;
    this.utilisateur = utilisateur;
    this.typeObject = typeObject;
    this.objectId = objectId;
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

  public ObjectPermissionEnum getTypeObject() {
    return typeObject;
  }

  public void setTypeObject(ObjectPermissionEnum typeObject) {
    this.typeObject = typeObject;
  }

  public String getObjectId() {
    return objectId;
  }

  public void setObjectId(String objectId) {
    this.objectId = objectId;
  }
}
