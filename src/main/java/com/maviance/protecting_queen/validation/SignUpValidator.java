package com.maviance.protecting_queen.validation;


import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.maviance.protecting_queen.domain.UserRegistration;


/**
 * This class is used to validate data sent by the user while registering
 * @author Rostow
 *
 */
public class SignUpValidator implements Validator{
	
	@Override
	public boolean supports(Class<?> clazz) {
		return  UserRegistration.class.equals(clazz);
	}
	

	@Override
	public void validate(Object target, Errors errors) {
		UserRegistration userRegistration=(UserRegistration) target;
		
		//reject if the username is empty
		if(userRegistration.getUsername()==null || userRegistration.getUsername().equals("")){
			errors.rejectValue("username", "","Username required");
		}
		//reject if password is empty
		if(userRegistration.getPassword()==null || userRegistration.getPassword().equals("")){
			errors.rejectValue("password", "","Password required");
		}
		
		if(userRegistration.getPassword()!=null){
			//Reject if the size of the password is not between 5 and 30
			if(userRegistration.getPassword().length()<5 || userRegistration.getPassword().length()>30){
			   errors.rejectValue("password", "","Invalid password. The password size should be between 5 and 30");
			}
			//reject if the passwords do not match
			if(!userRegistration.getPassword().equals(userRegistration.getConfirmPassword())){
	            errors.rejectValue("password","", "The passwords do not match");
	        }
		}
	}
}
