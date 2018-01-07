package com.miage.m2.webappforum.entity;


import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Topic extends TargetPermission {

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

  public Topic() {
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
