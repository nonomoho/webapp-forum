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
}
