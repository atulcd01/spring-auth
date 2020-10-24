package com.auth.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component("customLogoutSuccessHandler")
public class CustomLogoutSuccessHandler extends 
SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

  /*@Autowired 
  private AuditService auditService; */

  @Override
  public void onLogoutSuccess(
    HttpServletRequest request, 
    HttpServletResponse response, 
    Authentication authentication) 
    throws IOException, ServletException {

      String refererUrl = request.getHeader("Referer");
    //  auditService.track("Logout from: " + refererUrl);
      String redirecturl = request.getParameter("redirect-url");
      try {
          response.sendRedirect(redirecturl);
      } catch (IOException e) {
          e.printStackTrace();
      }
    //  super.onLogoutSuccess(request, response, authentication);
  }
}
