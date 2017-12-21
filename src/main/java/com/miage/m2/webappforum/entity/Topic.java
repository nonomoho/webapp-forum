package com.miage.m2.webappforum.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Topic {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;
  private String nom;
  private Boolean ouvert;
  @ManyToOne
  private Utilisateur createur;
  private Date creation;
  @OneToMany
  private List<Message> messageList;
  @ManyToMany(mappedBy = "followTopicList")
  private Set<Utilisateur> followerList;
  @Transient
  private boolean followedByUser;

  @Transient
  @ElementCollection
  private Set<String> readUsers;
  @Transient
  @ElementCollection
  private Set<String> writeUsers;

  public Topic() {
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

  public Boolean getOuvert() {
    return ouvert;
  }

  public void setOuvert(Boolean ouvert) {
    this.ouvert = ouvert;
  }

  public Utilisateur getCreateur() {
    return createur;
  }

  public void setCreateur(Utilisateur createur) {
    this.createur = createur;
  }

  public Date getCreation() {
    return creation;
  }

  public void setCreation(Date creation) {
    this.creation = creation;
  }

  public List<Message> getMessageList() {
    return messageList;
  }

  public void setMessageList(List<Message> messageList) {
    this.messageList = messageList;
  }

  public Set<Utilisateur> getFollowerList() {
    return followerList;
  }

  public void setFollowerList(Set<Utilisateur> followerList) {
    this.followerList = followerList;
  }

  public boolean isFollowedByUser() {
    return followedByUser;
  }

  public void setFollowedByUser(boolean followedByUser) {
    this.followedByUser = followedByUser;
  }
}
