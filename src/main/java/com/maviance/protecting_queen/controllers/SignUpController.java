package com.maviance.protecting_queen.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maviance.protecting_queen.domain.User;
import com.maviance.protecting_queen.domain.UserRegistration;
import com.maviance.protecting_queen.exceptions.UserAlreadyExistException;
import com.maviance.protecting_queen.services.UserService;
import com.maviance.protecting_queen.validation.SignUpValidator;

/**
 * This Rest controller is charged of create a new user in the system in order to allow him to connect 
 * @author Rostow
 *
 */
@RestController
public class SignUpController {

	/**
	 * We inject the SignUpService to save the user into the database
	 */
	@Autowired
	private UserService signupService;
	
	/**
	 * We set the validator. This validator will be used  to validate  the UserRegistration object before processing the request
	 * @param binder
	 */
	@InitBinder()
	protected void initBinder(WebDataBinder binder) {
	    binder.setValidator(new SignUpValidator());
	}
	
	/**
	 * This method takes a User object and insert into the database if the user does not exist
	 * @param user
	 * @return
	 * @throws UserAlreadyExistException
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<User> signup(@Valid @RequestBody  UserRegistration userRegistration) throws UserAlreadyExistException{
		User user=new User(userRegistration.getUsername(), userRegistration.getPassword());
		signupService.signUpUser(user);
		return new ResponseEntity<User>(HttpStatus.CREATED);
	}
}
