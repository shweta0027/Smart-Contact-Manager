package com.scm.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name="user") //we need store data in database
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails{
    @Id
    private String userId;
    @Column(name="user_name", nullable = false)
    private String name;
    @Column(unique=true, nullable = false)
    private String email;
    @Getter(value=AccessLevel.NONE)
    private String password;
    @Column(length=1000)
    private String about;
    @Column(length=1000)
    private String profilePic;
    private String phoneNo;
    
   
    //information
    @Getter(value=AccessLevel.NONE) //exclude from create getter by lombok
    private boolean enabled=true;
    private boolean emailVerified=false;
    private boolean phoneVerified=false;

    //to see using what user has signed up //self, google, linkedin
    @Enumerated(value=EnumType.STRING)
    private Providers provider=Providers.SELF;
    private String providerUserId;

    //add more if needed

    //one user have many contacts
    //when we specify mappedby seprate col will be created
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true) //if user is deleted contact will  get deleted to
    private List<Contact> contacts=new ArrayList<>();

    @ElementCollection(fetch=FetchType.EAGER)
    private List<String> roleList = new ArrayList<>(); //user can have multiple role


    @Override  //used when which user has which role
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        //list of roles[USER, ADMIN]
        //collection of SimpleGrantedAuthority[roles{ADMIN, USER}]
        //iterating over rolelist and conerting it to granted authority
        Collection<SimpleGrantedAuthority> roles=roleList.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
       
       return roles;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return this.email;
    }

    @Override
    public boolean isEnabled(){
        return this.enabled;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
       return this.password;
    }

    
}
