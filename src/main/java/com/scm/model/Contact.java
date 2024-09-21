package com.scm.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// @Builder
public class Contact {

    @Id
    private String id;
    private String name;
    private String email;
    public String phoneNumber;
    public String address;
    @Column(length=1000)
    private String description;
    private boolean favorite=false;
    private String websiteLink;
    private String linkedInLink;
    public String picture;
    public String cloudinaryImagePublicId;

    //private List<String> socialLink=new ArrayList<>();
    @ManyToOne
    @JsonIgnore //to avoid recursion
    private User user;

    @OneToMany(mappedBy = "contact", cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval = true)
    private List<SocialLink> links=new ArrayList<>();
}
