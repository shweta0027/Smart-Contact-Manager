package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.helpers.Helper;
import com.scm.model.User;
import com.scm.services.UserService;

@ControllerAdvice
public class RootController { //methods inside this will be executed for all req

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication){

        if(authentication==null){
            return;
        }
        System.out.println("adding loggedin user info to the model");
       String username=Helper.getEmailOfLoggedInUser(authentication);
       
       //can fetch user from db
       //can fetch other details of user
       User user=userService.getUserByEmail(username);
       System.out.println(user);

       if(user==null){
            model.addAttribute("loggedinUser", null);
       }
       else{
       
       System.out.println(user.getName());

       model.addAttribute("loggedinUser", user);
       }

    }
}
