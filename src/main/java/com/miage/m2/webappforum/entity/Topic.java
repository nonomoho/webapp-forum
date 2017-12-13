package com.miage.m2.webappforum.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    public Topic() {
    }
}
