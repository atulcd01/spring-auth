package com.auth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
	 @Autowired
	 DataSource dataSource;
	 
	 @Autowired
	 MySimpleUrlAuthenticationSuccessHandler mySimpleUrlAuthenticationSuccessHandler;
	
	 @Autowired
	 CustomLogoutSuccessHandler customLogoutSuccessHandler;
	 
	 @Autowired
	 MySsoLogoutHandler mySsoLogoutHandler;
	 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
         //.antMatchers("/login", "/oauth/authorize","/logout","/oauth/token")
        .antMatchers("/login", "/oauth/authorize","/logout","/oauth/token")
          .and()
          .authorizeRequests()
          .anyRequest().authenticated()
         // .and().antMatchers("/resources/**", "/registration")
          .and()
          .formLogin().loginPage("/login").permitAll()
          .and()
          .logout()
         
          // using this antmatcher allows /logout from GET without csrf as indicated in
          // https://docs.spring.io/spring-security/site/docs/current/reference/html/csrf.html#csrf-logout
          .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
          // this LogoutHandler invalidate user token from SSO
          .deleteCookies("JSESSIONID")
          .addLogoutHandler(mySsoLogoutHandler)
          .and().csrf().disable()
        
          
         // .successHandler(mySimpleUrlAuthenticationSuccessHandler)
          ;// here can have a form login html .loginPage("/login.html")
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      
    	System.out.println("Encoded password->"+passwordEncoder().encode("nimda"));
    	 auth.jdbcAuthentication().dataSource(dataSource)
    	  .usersByUsernameQuery(
    	   "select username,password, enabled from users where username=?")
    	  .authoritiesByUsernameQuery(
    	   "select username, authority from authorities where username=?");
    	  //.groupAuthoritiesByUsername(query);
    	 //nimda is the password
      
    }
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){ 
        return new BCryptPasswordEncoder(); 
    }
    
    public static void main( String[] args )
    {
    	BCryptPasswordEncoder d = new BCryptPasswordEncoder();
        System.out.println(d.encode("nimda") );
    }
}
