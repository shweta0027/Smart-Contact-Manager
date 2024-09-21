package com.scm.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.scm.services.imple.SecurityCustomUserDetailsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    //user create and login using java  code with in memory service
//     @Bean
//     public UserDetailsService userDetailsService(){
      //manually creating user
        
//     UserDetails user1=User
//                      .withDefaultPasswordEncoder()
//                      .username("shweta")
//                      .password("shweta")
//                      .roles("ADMIN", "USER")
//                      .build();

//     var inMemoryUserDetailsManager=new InMemoryUserDetailsManager(user1);
//     return inMemoryUserDetailsManager;
// }
    @Autowired
    private SecurityCustomUserDetailsService userDetailsService;  
    
    @Autowired
    private OAuthAuthenticationHandler handler;

    @Autowired
    private AuthFailureHandler authFailureHandler;

    //configuration of authentication provider
    @Bean
   public AuthenticationProvider authenticationProvider(){
      DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
      
      //need userdetailservice obj
      daoAuthenticationProvider.setUserDetailsService(userDetailsService);
      //need password encoder obj
      daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
      return daoAuthenticationProvider;
   }

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
    
       //configuration -url
       //which will remain public n private
       httpSecurity.authorizeHttpRequests(authorize->{
        // authorize.requestMatchers("/home","/register", "/services").permitAll(); //public url -can be accessed
       authorize.requestMatchers("/user/**").authenticated(); //securing onlu request which starts with /user
       authorize.anyRequest().permitAll();  //permitting all other request // means theyre public 
       });
       
       //form login default 
       //will be changed later when needed
    //    httpSecurity.formLogin(Customizer.withDefaults()); //default login form will come for protected url
    httpSecurity.formLogin(formLogin->{
           formLogin.loginPage("/login");
           formLogin.loginProcessingUrl("/authenticate"); //login processing will be done here
           formLogin.successForwardUrl("/user/profile"); //after succeesfull login it will go to this page
        //    formLogin.failureForwardUrl("/login?error=true");
        //    formLogin.defaultSuccessUrl("/home")
           formLogin.usernameParameter("email");
           formLogin.passwordParameter("password");

        //    formLogin.failureHandler(new AuthenticationFailureHandler() {

        //     @Override
        //     public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        //             AuthenticationException exception) throws IOException, ServletException {
        //         // TODO Auto-generated method stub
        //         throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'");
        //     }          
        //    });
        //    formLogin.successHandler(new AuthenticationSuccessHandler() {

        //     @Override
        //     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        //             Authentication authentication) throws IOException, ServletException {
        //         // TODO Auto-generated method stub
        //         throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
        //     }
            
        //    });
        //

        //to handle disabled user
        formLogin.failureHandler(authFailureHandler);

    });
       
    

       //Oauth configuration
    //    httpSecurity.oauth2Login(Customizer.withDefaults()); //this opens only google login not our login page
    httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(handler); //we need data from google
      
    });

       //logout
       httpSecurity.csrf(AbstractHttpConfigurer::disable);
       httpSecurity.logout(logoutForm->{
        logoutForm.logoutUrl("/logout");
        logoutForm.logoutSuccessUrl("/login?logout=true");
       });

       return httpSecurity.build();
   }
   
   //to encrypt password
   @Bean
   public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
   }
}
