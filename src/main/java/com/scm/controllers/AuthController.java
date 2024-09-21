package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.helpers.MessageType;
import com.scm.helpers.Messagee;
import com.scm.model.User;
import com.scm.repo.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserRepo userRepo;
    //verify email
    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token, HttpSession session){
      User user=userRepo.findByEmailToken(token).orElse(null);
      if(user!=null)
      {
        //user is present
        if(user.getEmailToken().equals(token)){
           user.setEmailVerified(true);
           user.setEnabled(true);
           userRepo.save(user);


           session.setAttribute("message", 
           Messagee.builder().content("Email Verification Successfull!! Please Login to continue.")
           .type(MessageType.green).build());
           return "success_page";
        }
        
        session.setAttribute("message", 
        Messagee.builder().content("Email not verified!!")
        .type(MessageType.red).build());
        return "error_page";

      }
      session.setAttribute("message", 
      Messagee.builder().content("Email not verified!!")
      .type(MessageType.red).build());
      return "error_page";
    }
}
