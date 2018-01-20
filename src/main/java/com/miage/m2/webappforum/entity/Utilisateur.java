package com.miage.m2.webappforum.entity;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"email", "pseudo"}))
public class Utilisateur implements UserDetails {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;
  @NotEmpty(message = "{emptyField}")
  private String pseudo;
  @NotEmpty(message = "{emptyField}")
  @Email(message = "{mailNotValid}")
  private String email;
  @NotEmpty(message = "{emptyField}")
  private String password;
  @ManyToMany()
  private Set<Topic> followTopicList;
  private Date inscription;
  @ManyToMany(mappedBy = "utilisateurs", fetch = FetchType.EAGER)
  private Set<Role> roles;

  public Utilisateur() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPseudo() {
    return pseudo;
  }

  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getInscription() {
    return inscription;
  }

  public void setInscription(Date inscription) {
    this.inscription = inscription;
  }

  public Set<Topic> getFollowTopicList() {
    return followTopicList;
  }

  public void setFollowTopicList(Set<Topic> followTopicList) {
    this.followTopicList = followTopicList;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return pseudo;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
