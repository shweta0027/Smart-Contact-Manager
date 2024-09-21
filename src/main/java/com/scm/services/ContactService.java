package com.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.scm.model.Contact;
import com.scm.model.User;


public interface ContactService {

    //save contact
    Contact savec(Contact contact);

    //update contact
    Contact update(Contact contact);

    //get contacts
    List<Contact> getAll();

    // get contact by id
    Contact getById(String id);

    // delete contact
    void  delete(String id);

    //search contact 
    Page<Contact> searchByName(String nameKeyword, int pageNo, int pageSize, String sortBy, String direction, User user);
    Page<Contact> searchByEmail(String emailKeyword, int pageNo, int pageSize, String sortBy, String direction,  User user);
    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int pageNo, int pageSize, String sortBy, String direction,  User user);

    //get contact by userId- getting all contacts of user
    List<Contact> getByUserId(String userId);
    
    //get contact by user
    // List<Contact> getByUser(User user);
    Page<Contact> getByUser(User user, int pageNo, int pageSize, String sortBy, String direction);


}
