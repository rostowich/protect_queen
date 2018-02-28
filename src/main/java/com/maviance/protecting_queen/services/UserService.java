package com.maviance.protecting_queen.services;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.maviance.protecting_queen.domain.User;
import com.maviance.protecting_queen.exceptions.UserAlreadyExistException;

/**
 * This interface exposes all the business methods related to a user
 * @author Rostow
 *
 */
@Component
public interface UserService {

	/**
	 * This method is used to insert a new user into the database
	 * @param user
	 * @return
	 */
	public User signUpUser(User user) throws UserAlreadyExistException;
	
	/**
	 * This method find the user by its username
	 * @param username
	 * @return
	 */
	public Optional<User> findUserByUsername(String username);
		
}
