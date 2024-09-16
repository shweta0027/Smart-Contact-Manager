package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.helpers.AppConstants;
import com.scm.model.Providers;
import com.scm.model.User;
import com.scm.repo.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationHandler implements AuthenticationSuccessHandler{

    @Autowired
    private UserRepo userRepo;
    Logger logger=LoggerFactory.getLogger(OAuthAuthenticationHandler.class);
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // TODO Auto-generated method stub
            
        logger.info("OAuthAuthenticationHandler");


        //identifying provider
        var oAuth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
        String authorizedClientRegistrationId= oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        logger.info(authorizedClientRegistrationId);
        
        var oauthUser=(DefaultOAuth2User)authentication.getPrincipal();
        
        oauthUser.getAttributes().forEach((key,value)->{
            logger.info(key+":"+value);
        });
         
        User user=new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setPassword("dummy");
        


        if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
        user.setEmail(oauthUser.getAttribute("email").toString());
        user.setName(oauthUser.getAttribute("name").toString());
        user.setProfilePic(oauthUser.getAttribute("picture").toString());
        user.setProviderUserId(oauthUser.getName());
        user.setProvider(Providers.GOOGLE);
        user.setAbout("this acc is created with googlr acc");
        }
        else{
            logger.info("unknown provider");
        }
              User user2=userRepo.findByEmail(user.getEmail()).orElse(null);
        if(user2==null){
            userRepo.save(user);
            logger.info("user saved to db"+user.getEmail());
        }
        


    //     //saving data in database:
    //     //fetching data
    //     DefaultOAuth2User user= (DefaultOAuth2User)authentication.getPrincipal();
    //     String email=user.getAttribute("email").toString();
    //     String name=user.getAttribute("name").toString();
    //     String picture=user.getAttribute("picture").toString();

    //     //creating user with this details
    //     User user1=new User();
    //     user1.setEmail(email);
    //     user1.setName(name);
    //     user1.setProfilePic(picture);
    //     user1.setPassword("password");
    //     user1.setUserId(UUID.randomUUID().toString());
    //     user1.setProvider(Providers.GOOGLE);
    //     user1.setEnabled(true);
    //  //   user1.setAccountNotExpired(true); //method not present
    //     user1.setEmailVerified(true);
    //     user1.setProviderUserId(user.getName());
    //     user1.setRoleList(List.of(AppConstants.ROLE_USER));
    //     user1.setAbout("This user is created with google acc");
        
    //     //if user is not present in db then save it in db
    //     User user2=userRepo.findByEmail(email).orElse(null);
    //     if(user2==null){
    //         userRepo.save(user1);
    //         logger.info("user saved"+email);
    //     }
        
         new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }

}
