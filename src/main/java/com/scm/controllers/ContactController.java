package com.scm.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.PropertyMapper.Source;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.helpers.AppConstants;
import com.scm.helpers.Helper;
import com.scm.helpers.MessageType;
import com.scm.helpers.Messagee;
import com.scm.model.Contact;
import com.scm.model.User;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;




@Controller
@RequestMapping("/user/contacts")
public class ContactController {
    private Logger logger=LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    //add contact page
    @RequestMapping("/add")
    public String addContactView(Model model){
        ContactForm contactForm=new ContactForm();
        // contactForm.setFavorite(true);
        model.addAttribute("contactForm", contactForm); //inintially form will be blank

        return "user/contacts/add_contact";
    }
    
    //Proceesing form data and save it it to databse
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result, Authentication authentication,
    HttpSession session){
         //processing form data 
         
         //validate form
         if(result.hasErrors()){

            session.setAttribute("message", 
            Messagee.builder()
            .content("Please Enter Valid Details")
            .type(MessageType.red)
            .build()
            );

            return "user/contacts/add_contact";
         }
         String username=Helper.getEmailOfLoggedInUser(authentication);
         User user =userService.getUserByEmail(username);

        //processing contact image
        logger.info("file info {}", contactForm.getContactImage().getOriginalFilename());


         //form->contact       
         Contact contact=new Contact();
         contact.setName(contactForm.getName());
         contact.setFavorite(contactForm.isFavorite());
         contact.setEmail(contactForm.getEmail());
         contact.setPhoneNumber(contactForm.getPhoneNumber());
         contact.setAddress(contactForm.getAddress());
         contact.setDescription(contactForm.getDescription());
         //to get user we need authentication
         contact.setUser(user);
         contact.setLinkedInLink(contactForm.getLinkedInLink());
         contact.setWebsiteLink(contactForm.getWebsiteLink());

         
        //code to upload image
        //checking if img is selected
        if(contactForm.getContactImage()!=null && !contactForm.getContactImage().isEmpty()){
            String filename=UUID.randomUUID().toString();
            String fileURL=imageService.uploadImage(contactForm.getContactImage(), filename);
    
            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(filename);
        }

         contactService.savec(contact);
         System.out.println(contactForm);

         //set message to be displayed on the view
         session.setAttribute("message", 
         Messagee.builder()
         .content("You have succesfully added a new contact")
         .type(MessageType.green)
         .build()
         );
        return "redirect:/user/contacts/add";
    }

     
    //view contacts
    @RequestMapping("/viewAll") //nedd to give this url in frontend (dashbord)'/user/contacts/viewAll'
    public String viewContacts(
        @RequestParam(value="pageNo", defaultValue = "0" ) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE+"") int pageSize,
        @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
        @RequestParam(value = "direction", defaultValue = "asc") String direction,
        Model model, Authentication authentication){

        //load all user contacts
       String username= Helper.getEmailOfLoggedInUser(authentication);
       User user=userService.getUserByEmail(username);

        Page<Contact> pageContactList=contactService.getByUser(user,pageNo,pageSize,sortBy,direction);
        model.addAttribute("pageContactList", pageContactList); //view
        model.addAttribute("pgSize", AppConstants.PAGE_SIZE);

        model.addAttribute("contactSearchForm", new ContactSearchForm());
        return "user/contacts/contacts";
    }

    //search contact handler
    @RequestMapping("/search")
    public String searchHnadler(
    @ModelAttribute ContactSearchForm contactSearchForm,
    // @RequestParam("field") String feild,
    // @RequestParam("keyword") String value,
    @RequestParam(value="Pagesize", defaultValue = AppConstants.PAGE_SIZE+"") int Pagesize,
    @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
    @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
    @RequestParam(value = "direction", defaultValue = "asc") String direction,
    Model model,
    Authentication authentication
    ){
           
        logger.info("field {} keyword {}",contactSearchForm.getField(),contactSearchForm.getValue());

        var user=userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));


        Page<Contact> pageContact=null;
        if(contactSearchForm.getField().equalsIgnoreCase("name")){
        pageContact=contactService.searchByName(contactSearchForm.getValue(), pageNo, Pagesize, sortBy, direction,user);
            
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("email")){
            pageContact=contactService.searchByEmail(contactSearchForm.getValue(), pageNo, Pagesize, sortBy, direction,user);
                
            }
            if(contactSearchForm.getField().equalsIgnoreCase("phone")){
                pageContact=contactService.searchByPhoneNumber(contactSearchForm.getValue(), pageNo, Pagesize, sortBy, direction, user);
                    
                }
        logger.info("pageContact {}", pageContact);
        model.addAttribute("contactSearchForm", contactSearchForm); //sending data back
        model.addAttribute("pageContactList", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/contacts/search";
    }

    //delete contact
    @RequestMapping("/delete/{id}")
    public String deleteContact(@PathVariable String id, HttpSession session){
        contactService.delete(id);
        logger.info("contact {} deleted",id);

        session.setAttribute("message", 
        Messagee.builder()
        .content("Contact Deleted Succesfully")
        .type(MessageType.green)
        .build());
        return "redirect:/user/contacts/viewAll";
    }

    //update contact - form will displayed
    @GetMapping("/view/{contactId}")
    public String updateContactFormView(@PathVariable("contactId") String contactId, Model model){
           
        var contact=contactService.getById(contactId);
        
        //user will see for with filled in data
        ContactForm contactForm=new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setDescription(contact.getDescription());
        contactForm.setAddress(contact.getAddress());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setPicture(contact.getPicture());

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);

        return "user/contacts/update_contact_view";
           
    }

    //update -processing form data recieved from user
    @RequestMapping(value="/update/{contactId}", method=RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId, 
    @Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "user/contacts/update_contact_view";
        }

        var con=contactService.getById(contactId);
        con.setId(contactId);
        con.setEmail(contactForm.getEmail());
        con.setName(contactForm.getName());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setDescription(contactForm.getDescription());
        con.setAddress(contactForm.getAddress());       
        con.setFavorite(contactForm.isFavorite());
        con.setLinkedInLink(contactForm.getLinkedInLink());
        con.setWebsiteLink(contactForm.getWebsiteLink());
       
        //process image update
        if(contactForm.getContactImage()!=null && !contactForm.getContactImage().isEmpty()){
            String fileName=UUID.randomUUID().toString();
            String imageURL=imageService.uploadImage(contactForm.getContactImage(), fileName);
            con.setCloudinaryImagePublicId(fileName);
            con.setPicture(imageURL);
            contactForm.setPicture(imageURL);
        }

       var updatedCon= contactService.update(con);
       model.addAttribute("message", 
       Messagee.builder().content("Contact Updated Successfully").type(MessageType.green).build());
       
        return "redirect:/user/contacts/view/"+contactId;
    }
}
