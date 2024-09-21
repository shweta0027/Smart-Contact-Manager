package com.scm.controllers;

import java.security.Principal;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.helpers.Helper;
import com.scm.model.User;
import com.scm.services.UserService;



@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger=org.slf4j.LoggerFactory.getLogger(UserController.class);

    //user dashboard page
    @RequestMapping(value="/dashboard")
    public String userDashboard() {
        System.out.println("INSIDE USER DASHBOARD");
        return "user/dashboard"; //returning view
    }

    //user profile page
    @RequestMapping(value="/profile")
    // public String userPeofile(Principal principal) {
        public String userPeofile(Model model, Authentication authentication) {
        //String name= principal.getName();
        //  logger.info("User looged in: {}", name);

        // String username=Helper.getEmailOfLoggedInUser(authentication);
         
        // //can fetch user from db
        // //can fetch other details of user
        // User user=userService.getUserByEmail(username);
        // System.out.println(user.getName());

        // model.addAttribute("loggedinUser", user);
        return "user/profile";
    }
    //adding more methods
    

    //user add contact page

    //user view contact page

    //user edit contact page

    //user delete contact page

    //user search contact page
}
