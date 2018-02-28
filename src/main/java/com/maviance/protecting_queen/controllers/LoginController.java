package com.maviance.protecting_queen.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maviance.protecting_queen.domain.User;
import com.maviance.protecting_queen.domain.UserLogin;
import com.maviance.protecting_queen.services.UserService;

/**
 * This rest controller is charged of log in the client into the application
 * @author Rostow
 *
 */
@RestController
public class LoginController {

private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	/**
	 * Inject the UserDetailService dependency
	 */
	@Autowired
    private UserDetailsService userDetailsService;
	
	/**
	 * Inject the UserService dependency to be able to invoke the services present in its interface
	 */
	@Autowired
    private UserService userService;
	
	/**
	 * Inject the AuthenticationManager dependency 
	 */
	@Autowired
    private AuthenticationManager authenticationManager;
	
	/**
	 * This method allow authentication through rest service
	 * @param userLogin
	 * @return
	 */
	@RequestMapping(value="/authenticate", method=RequestMethod.POST)
	public ResponseEntity<User> login(@RequestBody UserLogin userLogin){
		
		logger.info("Login the user");
		UserDetails userDetails = userDetailsService.loadUserByUsername(userLogin.getUsername());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, userLogin.getPassword(), userDetails.getAuthorities());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if(usernamePasswordAuthenticationToken.isAuthenticated()){
        	logger.info("The user has been logged in successfully");
        	SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        	Optional<User> user=userService.findUserByUsername(userDetails.getUsername());
        	//Setting the user password before returning it
        	user.get().setPassword("");
        	return new ResponseEntity<>(user.get(),HttpStatus.OK);
        }
        logger.info("Authentication failure");
       	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
