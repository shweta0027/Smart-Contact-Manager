package com.scm.services.imple;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.scm.services.EmailService;

@Service
public class EmailServiceImple implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;
    
    @Value("${spring.mail.properties.domain_name}")
    private String domainName;


    @Override
    public void sendEmail(String to, String subject, String body) {
        // TODO Auto-generated method stub
        
          SimpleMailMessage message=new SimpleMailMessage();
          message.setTo(to);
          message.setSubject(subject);
          message.setText(body);
          //domain given by email trap
          message.setFrom(domainName);

          javaMailSender.send(message);
    }

    @Override
    public void sendEmailWithHtml() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendEmailWithHtml'");
    }

    @Override
    public void sendEmailWithAttachment() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendEmailWithAttachment'");
    }

}
