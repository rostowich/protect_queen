package com.maviance.protecting_queen.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.maviance.protecting_queen.domain.User;

/**
 * This is an implementation to the UserDetails interface
 * UserDetails is an interface in Spring Security which provides core user information. 
 * @author Rostow
 *
 */
public class CustomUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L; 
	
	/**
	 * GrantedAuthority instances represent roles that the user has in the system
	 */
	private Collection<? extends GrantedAuthority> authorities; 
	
	/**
	 * Password of the user in the system
	 */
	private String password; 
	
	/**
	 * Username of the user in the system
	 */
	private String username;
	
	public CustomUserDetails(User user) { 
		this.username = user.getUsername(); 
		this.password = user.getPassword();
		
	} 
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities; 
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username; 
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
