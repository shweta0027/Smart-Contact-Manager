package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PageController {
    
    @RequestMapping("/home")
    public String home(Model model){
        //to add data to html from backend
        model.addAttribute("name", "shwetaaaa");
        System.out.println("home page handler");
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage(){
        //to add data to html from backend
        System.out.println("about page handler");
        return "about";
    }
    
    @RequestMapping("/services")
    public String servicesPage(){
        //to add data to html from backend
        System.out.println("services page handler");
        return "services";
    }

}
