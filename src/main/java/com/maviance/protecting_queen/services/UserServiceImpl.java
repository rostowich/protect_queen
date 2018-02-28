package com.maviance.protecting_queen.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maviance.protecting_queen.domain.User;
import com.maviance.protecting_queen.exceptions.UserAlreadyExistException;
import com.maviance.protecting_queen.repositories.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	/**
	 * We inject the user repository to save the user into the database
	 */
	@Autowired
	private UserRepository userRepository;
 
	/**
	 * We inject the passwordEncoder bean to encode the password before saving it into the database
	 */
	@Autowired
	PasswordEncoder passwordEncoder;
	
	/*
	 * (non-Javadoc)
	 * @see com.maviance.protecting_queen.services.SignUpService#signUpUser(com.maviance.protecting_queen.domain.User)
	 */
	@Override
	public User signUpUser(User user) throws UserAlreadyExistException {
		//Checking if the user does not exist into the database
		Optional<User> userFound=userRepository.findByUsername(user.getUsername());
		
		if(userFound.isPresent())
			throw new UserAlreadyExistException("This user already exists");
		//We encode the user password before saving it into the database
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	/*
	 * (non-Javadoc)
	 * @see com.maviance.protecting_queen.services.UserService#findUserByUsername(java.lang.String)
	 */
	@Override
	public Optional<User> findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}

	

}
