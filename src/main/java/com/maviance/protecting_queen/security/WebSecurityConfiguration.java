package com.maviance.protecting_queen.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * This class represents the Resource service in OAuth2 protocol and used to configure security for endpoints
 * @author Rostow
 *
 */
@Configuration
@EnableResourceServer
@EnableWebSecurity
public class WebSecurityConfiguration extends ResourceServerConfigurerAdapter {
	
	  /*@Autowired
	  private UserDetailsService userDetailsService;
	 
	  @Autowired
	  private BCryptPasswordEncoder bCryptPasswordEncoder;*/
	 
	  @Override
	  public void configure(HttpSecurity httpSecurity) throws Exception {
	    httpSecurity
	      .cors().and() 	
	      .csrf()
	        .disable()
	      .authorizeRequests()
	        .antMatchers(HttpMethod.POST,"/signup").permitAll()
	        .antMatchers(HttpMethod.POST,"/authenticate").permitAll()
	        .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html","/webjars/**").permitAll()
	        .antMatchers("swagger-ui.html").permitAll()
	        .anyRequest().authenticated();
	        
	  }
	  
	}