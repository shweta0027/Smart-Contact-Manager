package com.scm.helpers;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication){
         
        //to get principal
     //   Principal principal=(Principal)authentication.getPrincipal();

        if(authentication instanceof OAuth2AuthenticationToken){
           
            var aOAuth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
            var clientId=aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
             
            var oauth2User=(OAuth2User)authentication.getPrincipal();
            String username="";
         
            //signed in google
            if(clientId.equalsIgnoreCase("google")){
                System.out.println("getting email from google client");
                username=oauth2User.getAttribute("email").toString();

            }

           //signed in github
           return username;
        }
        else{
            //this means user is signed with email password
            System.out.println("getting data from local db");
            return authentication.getName();
        }
       // return "";
    }
}
