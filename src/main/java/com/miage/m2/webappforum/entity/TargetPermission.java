package com.miage.m2.webappforum.entity;

import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public abstract class TargetPermission {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Transient
  @ElementCollection
  private Set<String> readUsers;
  @Transient
  @ElementCollection
  private Set<String> writeUsers;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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
