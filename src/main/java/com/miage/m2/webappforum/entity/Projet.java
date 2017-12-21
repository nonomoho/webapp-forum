package com.miage.m2.webappforum.entity;

import java.util.List;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"nom"}))

public class Projet {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;
  @NotEmpty(message = "{emptyField}")
  private String nom;
  private String description;
  @OneToMany
  private List<Topic> topicList;
  @Transient
  @ElementCollection
  private Set<String> readUsers;
  @Transient
  @ElementCollection
  private Set<String> writeUsers;

  public Projet() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public List<Topic> getTopicList() {
    return topicList;
  }

  public void setTopicList(List<Topic> topicList) {
    this.topicList = topicList;
  }

  public Set<String> getReadUsers() {
    return readUsers;
  }

  public void setReadUsers(Set<String> readUsers) {
    this.readUsers = readUsers;
  }

  public Set<String> getWriteUsers() {
    return writeUsers;
  }

  public void setWriteUsers(Set<String> writeUsers) {
    this.writeUsers = writeUsers;
  }
}
