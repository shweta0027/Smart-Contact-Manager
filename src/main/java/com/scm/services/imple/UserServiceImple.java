package com.scm.services.imple;

import java.util.*;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.exceptions.ResourceNotFoundException;
import com.scm.helpers.AppConstants;
import com.scm.model.User;
import com.scm.repo.UserRepo;
import com.scm.services.UserService;

@Service
public class UserServiceImple implements UserService{
    //cntrl+. enter to add unimelemened methods

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger=LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        // TODO Auto-generated method stub

        //generating user id
        String userId=UUID.randomUUID().toString();
        user.setUserId(userId);

        //password encode 
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        //set the user role
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        // TODO Auto-generated method stub
         return userRepo.findById(id);
    }

    @Override
    public Optional<User> updatUser(User user) {
        // TODO Auto-generated method stub
        User user1=userRepo.findById(user.getUserId()).orElseThrow(()->
        new ResourceNotFoundException("User Not Found"));
        //updating user2 from user //user2 is data in db //user is current data
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        user1.setPhoneNo(user.getPhoneNo());
        user1.setAbout(user.getAbout());
        user1.setProfilePic(user.getProfilePic());
        user1.setEnabled(user.isEnabled());
        user1.setEmailVerified(user.isEmailVerified());
        user1.setPhoneVerified(user.isPhoneVerified());
        user1.setProvider(user.getProvider());
        user1.setProviderUserId(user.getProviderUserId());

        //saving user in db
        User savedUser=userRepo.save(user1);
        return Optional.ofNullable(savedUser); //if null empty optional we be sent

    }

    @Override
    public void deleteUser(String id) {
        // TODO Auto-generated method stub
        User user1=userRepo.findById(id).orElseThrow(()->
        new ResourceNotFoundException("User Not Found"));
        userRepo.delete(user1);

    }

    @Override
    public boolean isUserExist(String userId) {
        // TODO Auto-generated method stub
        User user1=userRepo.findById(userId).orElse(null);
        return user1!=null ? true : false;
       
    }

    @Override
    public boolean isUserExistByUserName(String email) {
        // TODO Auto-generated method stub
       User user1=userRepo.findByEmail(email).orElse(null);
       return user1!=null ? true : false;

    }

    @Override
    public List<User> getAllUser() {
        // TODO Auto-generated method stub
        return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        // TODO Auto-generated method stub
        return userRepo.findByEmail(email).orElse(null);

    }

}
