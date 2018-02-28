package com.maviance.protecting_queen.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.maviance.protecting_queen.domain.User;
import com.maviance.protecting_queen.exceptions.UserAlreadyExistException;
import com.maviance.protecting_queen.repositories.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

	@InjectMocks
	private UserServiceImpl signUpService;
	
	/**
	 * We mock the userRepository dependence 
	 */
	@Mock
	private UserRepository userRepositoryMock;
	
	/**
	 * We mock the password encoder 
	 */
	@Mock
	private PasswordEncoder passwordEncoderMock;
	
	/**
	 * Function used to init the mocked objects before performing the tests
	 */
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);		
	}
	
	@Test
	public void testSignUpUser_ThrowUserAlreadyExistException(){
		
		User userExist=new User("maviance", "password");
		//We can mock the findByUsername method of the userRepository
		when(userRepositoryMock.findByUsername("maviance")).thenReturn(Optional.of(userExist));
		try{
			signUpService.signUpUser(userExist);
		}
		catch (Exception e) {
			assertThat(e)
			.isInstanceOf(UserAlreadyExistException.class)
            .hasMessage("This user already exists");
		}	
	}
	
	@Test
	public void testSignUpUser_SaveTheUser() throws UserAlreadyExistException{
		
		User userToSaved=new User("maviance", "password");
		User userSavedMock=new User("maviance", "passwordEncoded");
		//We can mock the dependencies
		when(userRepositoryMock.findByUsername("maviance")).thenReturn(Optional.empty());
		when(passwordEncoderMock.encode("password")).thenReturn("passwordEncoded");
		when(userRepositoryMock.save(refEq(userSavedMock))).thenReturn(userSavedMock);
		User userSaved=	signUpService.signUpUser(userToSaved);
		
		assertThat(userSaved.getUsername()).isEqualTo("maviance");
		assertThat(userSaved.getPassword()).isEqualTo("passwordEncoded");
			
	}
}
