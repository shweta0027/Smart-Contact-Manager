package com.scm.config;

import java.io.IOException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.scm.helpers.MessageType;
import com.scm.helpers.Messagee;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthFailureHandler implements AuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        // TODO Auto-generated method stub
         
        //checking if exception generated bcpx of disabled user
        if(exception instanceof DisabledException){
            //user is disabled

            HttpSession session=request.getSession();
            session.setAttribute("message", Messagee.builder().content("Account is disabled, Please verify your Email").type(MessageType.red).build());

            response.sendRedirect("/login");
        }
        else
        response.sendRedirect("/login?error=true");

    }

}
