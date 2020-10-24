package com.auth.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@Qualifier("mySsoLogoutHandler")
public class MySsoLogoutHandler implements LogoutHandler {



    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        System.out.println("executing MySsoLogoutHandler.logout");
        
        String redirecturl = request.getParameter("redirect-url");
	 	//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //new SecurityContextLogoutHandler().logout(request, null, auth);
        try {
            response.sendRedirect(redirecturl);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}