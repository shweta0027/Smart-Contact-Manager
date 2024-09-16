package com.scm.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.*;

@Entity
public class Contact {

    @Id
    private int id;
    private String name;
    private String email;
    public String phoneNumber;
    public String address;
    @Column(length=1000)
    private String description;
    private boolean favorite=false;

    //private List<String> socialLink=new ArrayList<>();
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "contact", cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval = true)
    private List<SocialLink> links=new ArrayList<>();
}
