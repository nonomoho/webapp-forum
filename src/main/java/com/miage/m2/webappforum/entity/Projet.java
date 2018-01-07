package com.miage.m2.webappforum.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.access.prepost.PostFilter;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"nom"}))
public class Projet extends TargetPermission{

  @NotEmpty(message = "{emptyField}")
  private String nom;
  private String description;
  @OneToMany (mappedBy = "projet")
  private List<Topic> topicList;

  public Projet() {
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @PostFilter("hasPermission(filterObject, T(com.miage.m2.webappforum.entity.TypePermissionEnum).READ)")
  public List<Topic> getTopicList() {
    return topicList;
  }

  public void setTopicList(List<Topic> topicList) {
    this.topicList = topicList;
  }

}
