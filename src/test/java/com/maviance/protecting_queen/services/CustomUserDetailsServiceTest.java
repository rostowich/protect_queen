package com.maviance.protecting_queen.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import com.maviance.protecting_queen.domain.User;
import com.maviance.protecting_queen.repositories.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomUserDetailsServiceTest {

	@InjectMocks
	private CustomUserDetailsService customUserDetailsService;
	
	/**
	 * We mock the userRepository
	 */
	@Mock
	private UserRepository userRepositoryMock;
	
	/**
	 * Function used to init the mocked objects before performing the tests
	 */
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);		
	}
	
	@Test
	public void testLoadUserByUsernameThrowsUsernameNotFoundException(){
		
		//Mocking the findByUsername of the userRepository mock
		when(userRepositoryMock.findByUsername("maviance")).thenReturn(Optional.empty());		
		//test the method
		try{
			customUserDetailsService.loadUserByUsername("maviance");
		}
		catch (Exception e) {
			assertThat(e)
			.isInstanceOf(UsernameNotFoundException.class)
            .hasMessage("UserName maviance not found");
		}	
	}
	
	@Test
	public void testLoadUserByUsernameReturnACustomerUserDetails(){
		
		User userMock=new User("maviance", "password");
		//Mocking the findByUsername of the userRepository mock
		when(userRepositoryMock.findByUsername("maviance")).thenReturn(Optional.of(userMock));		
		//test the method		
		UserDetails userDetails=customUserDetailsService.loadUserByUsername("maviance");
		
		//Make the assertions
		assertThat(userDetails).isNotNull();
		assertThat(userDetails).isInstanceOf(UserDetails.class);
		assertThat(userDetails.getUsername()).isEqualTo("maviance");
		assertThat(userDetails.getPassword()).isEqualTo("password");
	}

}
