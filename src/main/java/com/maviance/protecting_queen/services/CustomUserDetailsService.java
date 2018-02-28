package com.maviance.protecting_queen.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.maviance.protecting_queen.repositories.UserRepository;

import static java.util.Collections.emptyList;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	/**
	 * We inject the user repository to allow Spring security to find the user with the credentials 
	 */
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * This method return an instance of a userDetails
	 * UserDetails is an interface in Spring Security which provides core user information
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<com.maviance.protecting_queen.domain.User> user = userRepository.findByUsername(userName);
        if(!user.isPresent()){
            throw new UsernameNotFoundException("UserName "+userName+" not found");
        }
        return new User(user.get().getUsername(), user.get().getPassword(), emptyList());
	}

}
