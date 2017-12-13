package com.miage.m2.webappforum.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private Date date;
    @ManyToOne
    private Utilisateur utilisateur;
    private String contenu;
    //FIXME différencier texte brut et html ?


    public Message() {
    }
}
