package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.forms.UserForm;
import com.scm.helpers.MessageType;
import com.scm.helpers.Messagee;
import com.scm.model.User;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }
    
    
    @RequestMapping("/home")
    public String home(Model model){
        //to add data to html from backend
        model.addAttribute("name", "shwetaaaa");
        System.out.println("home page handler");
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage(){
        System.out.println("about page handler");
        return "about";
    }
    
    @RequestMapping("/services")
    public String servicesPage(){
        System.out.println("services page handler");
        return "services";
    }

    @RequestMapping("/contact")
    public String contactPage(){
      //  System.out.println("services page handler");
        return "contact";
    }

    @GetMapping("/login")
    public String login(){
      //  System.out.println("services page handler");
         return new String("login");
    }

    @RequestMapping("/register")
    public String register(Model model){
      //this will go on register page
        UserForm userForm=new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    //processing register
    @RequestMapping(value="/do-register", method=RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rbindingResult, HttpSession session)
    {
      //  fetch data
      //UserForm
      System.out.println(userForm);
      //validate form data
      if(rbindingResult.hasErrors()){
        return "register";
      }
      // save to database
      // User user=User.builder()
      //               .name(userForm.getName())
      //               .email(userForm.getEmail())
      //               .password(userForm.getPassword())
      //               .about(userForm.getAbout())
      //               .phoneNo(userForm.getPhoneNumber())
      //               .profilePic("https://t4.ftcdn.net/jpg/02/66/72/41/360_F_266724172_Iy8gdKgMa7XmrhYYxLCxyhx6J7070Pr8.jpg")
      //               .build();

      User user=new User();
      user.setName(userForm.getName());
      user.setEmail(userForm.getEmail());
      user.setPassword(userForm.getPassword());
      user.setAbout(userForm.getAbout());
      user.setPhoneNo(userForm.getPhoneNumber());
      user.setEnabled(false);
      user.setProfilePic("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png");

      User savedUser=userService.saveUser(user);
      System.out.println("saved user....");

      //messeage registration succesfull
      Messagee msg=Messagee.builder().content("Registration successfull").type(MessageType.green).build();
      session.setAttribute("message", msg);
      //redirect to page
        return "redirect:/register";
    }

}
