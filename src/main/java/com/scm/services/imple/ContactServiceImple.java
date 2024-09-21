package com.scm.services.imple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.*;

import com.scm.exceptions.ResourceNotFoundException;
import com.scm.model.Contact;
import com.scm.model.User;
import com.scm.repo.ContactRepo;
import com.scm.services.ContactService;


@Service
public class ContactServiceImple implements ContactService{

    @Autowired
    private ContactRepo contactRepo;
    
    @Override
    public Contact savec(Contact contact) {
        // TODO Auto-generated method stub
        String contactId=UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        // TODO Auto-generated method stub
        Contact contactOld=contactRepo.findById(contact.getId()).orElseThrow(()->new ResourceNotFoundException("contact not found"));
        contactOld.setName(contact.getName());
        contactOld.setEmail(contact.getEmail());
        contactOld.setPhoneNumber(contact.getPhoneNumber());
        contactOld.setDescription(contact.getDescription());
        contactOld.setAddress(contact.getAddress());
        contactOld.setPicture(contact.getPicture());
        contactOld.setFavorite(contact.isFavorite());
        contactOld.setLinkedInLink(contact.getLinkedInLink());
        contactOld.setWebsiteLink(contact.getWebsiteLink());
        contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());
        

        return contactRepo.save(contactOld);
    }

    @Override
    public List<Contact> getAll() {
        // TODO Auto-generated method stub
        return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
        // TODO Auto-generated method stub
       return contactRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("contact not found"));
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        var contact=contactRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("contact not found"));
        contactRepo.delete(contact);
    }

    // @Override
    // public List<Contact> search(String name, String email, String phoneNumber) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'search'");
    // }

    @Override
    public List<Contact> getByUserId(String userId) {
        // TODO Auto-generated method stub
        return contactRepo.findByUserId(userId);
    }

    @Override
    public Page<Contact> getByUser(User user, int pageNo, int pageSize, String sortBy, String direction) {
        // TODO Auto-generated method stub
      //  return contactRepo.findByUser(user);

      Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
      var pageable=PageRequest.of(pageNo, pageSize, sort);
      return contactRepo.findByUser(user, pageable);
        
    }

    @Override
    public Page<Contact> searchByName(String nameKeyword, int pageNo, int pageSize, String sortBy, String direction,  User user) {
        // TODO Auto-generated method stub

        Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable=PageRequest.of(pageNo, pageSize,sort);
        return contactRepo.findByUserAndNameContaining(user, nameKeyword, pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String emailKeyword, int pageNo, int pageSize, String sortBy, String direction,  User user) {
        // TODO Auto-generated method stub
        Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable=PageRequest.of(pageNo, pageSize,sort);
        return contactRepo.findByUserAndEmailContaining(user, emailKeyword, pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int pageNo, int pageSize, String sortBy,
            String direction,  User user) {
        // TODO Auto-generated method stub
        
        Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable=PageRequest.of(pageNo, pageSize,sort);
        return contactRepo.findByUserAndPhoneNumberContaining(user,phoneNumberKeyword, pageable);
    }

   

 

}
