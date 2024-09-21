package com.scm.services;

import java.util.*;

import com.scm.model.User;

public interface UserService {

    User saveUser(User user);
    Optional<User> getUserById(String id);
    Optional<User> updatUser(User user); //no need to do additional null checks
    void deleteUser(String id);
    boolean isUserExist(String userId);
    boolean isUserExistByUserName(String email); //email is our username
    List<User> getAllUser();

    //add more methods
    User getUserByEmail(String email);

    
}
