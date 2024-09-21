package com.scm.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.model.Contact;
import com.scm.model.User;
import java.util.*;
import java.util.List;


@Repository
public interface ContactRepo extends JpaRepository<Contact, String>{

    //custom finder method
    // List<Contact> findByUser(User user);
    Page<Contact> findByUser(User user, Pageable pageable);

    //custom query method
    //contact->user->id
    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(@Param("userId") String userId);

    //search
    Page<Contact> findByUserAndNameContaining( User user,String namekeyword, Pageable pageable);
    Page<Contact> findByUserAndEmailContaining( User user,String emailkeyword, Pageable pageable);
    Page<Contact> findByUserAndPhoneNumberContaining( User user,String phonekeyword, Pageable pageable);

}