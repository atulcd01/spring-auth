package com.auth.resource.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.resource.model.UserProfile;
import com.auth.resource.service.UserDetailsServiceImpl;


@RestController
public class RestResource 
{
	@Autowired
	UserDetailsServiceImpl userDetails;
	
	
	@RequestMapping("/api/users/me")
	public ResponseEntity<UserProfile> profile() 
	{
		//Build some dummy data to return for testing
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String email = user.getUsername() + "@howtodoinjava.com";

	
		
		UserProfile profile = userDetails.loadUserByUsername(user.getUsername());
		profile.setPassword("********");
		System.out.println(profile.getUsername());
		return ResponseEntity.ok(profile);
	}
	
	@RequestMapping("/auth")
	public int isAuthenticated(HttpServletRequest request, HttpServletResponse response) 
	{
		
		
       
		return 0;
	}
	 @RequestMapping("/logout")
	    public void exit(HttpServletRequest request, HttpServletResponse response) {
		 
		 String redirecturl = request.getParameter("redirect-url");
		 	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        new SecurityContextLogoutHandler().logout(request, null, auth);
	        try {
	            response.sendRedirect(redirecturl);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	 
	 /*@Resource(name="tokenServices")
	 ConsumerTokenServices tokenServices;
	      
	 @RequestMapping(method = RequestMethod.POST, value = "/tokens/revoke/{tokenId:.*}")
	 @ResponseBody
	 public String revokeToken(@PathVariable String tokenId) {
	     tokenServices.revokeToken(tokenId);
	     return tokenId;
	 }*/
}
