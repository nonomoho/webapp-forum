package com.miage.m2.webappforum.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.mail.SimpleMailMessage;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(uniqueConstraints= @UniqueConstraint(columnNames = {"email", "pseudo"}))
public class Utilisateur {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String pseudo;
    private SimpleMailMessage email;
    private String password;
    private Date inscription;

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

    public SimpleMailMessage getEmail() {
        return email;
    }

    public void setEmail(SimpleMailMessage email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
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
}
