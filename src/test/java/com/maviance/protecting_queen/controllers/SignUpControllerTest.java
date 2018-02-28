package com.maviance.protecting_queen.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maviance.protecting_queen.domain.User;
import com.maviance.protecting_queen.domain.UserRegistration;
import com.maviance.protecting_queen.exceptions.UserAlreadyExistException;
import com.maviance.protecting_queen.services.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=SignUpController.class, secure=false)
public class SignUpControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	//We  have to add all these mocks to the spring ApplicationContext
	@MockBean
	private UserService userService;
	
	@Test
	public void SignUpTestWithInvalidParameters() throws Exception{
		
	    //test the method with empty username
		UserRegistration userWithEmptyUsernameAndPassword=new UserRegistration("", "", "");
		
		RequestBuilder requestBuilderEmptyUsername = MockMvcRequestBuilders
				.post("/signup")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(userWithEmptyUsernameAndPassword))
				.contentType(MediaType.APPLICATION_JSON);
		
		//Evaluating the assertions
		mockMvc.perform(requestBuilderEmptyUsername)
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message", is("Validation error")))
			.andExpect(jsonPath("$.status", is("BAD_REQUEST")))
			.andExpect(jsonPath("$.validationErrors[0].field", is("username")))
			.andExpect(jsonPath("$.validationErrors[0].message", is("Username required")))
			.andExpect(jsonPath("$.validationErrors[1].field", is("password")))
			.andExpect(jsonPath("$.validationErrors[1].message", is("Password required")))
			.andExpect(jsonPath("$.validationErrors[2].field", is("password")))
			.andExpect(jsonPath("$.validationErrors[2].message", is("Invalid password. The password size should be between 5 and 30")));
		
		//test the method with empty password
		UserRegistration userRegistrationWithEmptyPassword=new UserRegistration("maviance", "", "");
		
		RequestBuilder requestBuilderEmptypassword = MockMvcRequestBuilders
				.post("/signup")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(userRegistrationWithEmptyPassword))
				.contentType(MediaType.APPLICATION_JSON);
		
		//Evaluating the assertions
		mockMvc.perform(requestBuilderEmptypassword)
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message", is("Validation error")))
			.andExpect(jsonPath("$.status", is("BAD_REQUEST")))
			.andExpect(jsonPath("$.validationErrors[0].field", is("password")))
			.andExpect(jsonPath("$.validationErrors[0].message", is("Password required")))
			.andExpect(jsonPath("$.validationErrors[1].field", is("password")))
			.andExpect(jsonPath("$.validationErrors[1].message", is("Invalid password. The password size should be between 5 and 30")));

		//test the method with passwords not matching
		UserRegistration userRegistrationWithPasswordDoesNotMatch=new UserRegistration("maviance", "password", "password123");		
		RequestBuilder requestBuilderPasswordDoesNotMatch = MockMvcRequestBuilders
				.post("/signup")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(userRegistrationWithPasswordDoesNotMatch))
				.contentType(MediaType.APPLICATION_JSON);
		
		//Evaluating the assertions
		mockMvc.perform(requestBuilderPasswordDoesNotMatch)
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message", is("Validation error")))
			.andExpect(jsonPath("$.status", is("BAD_REQUEST")))
			.andExpect(jsonPath("$.validationErrors[0].field", is("password")))
			.andExpect(jsonPath("$.validationErrors[0].message", is("The passwords do not match")));
		
	}
	
	@Test
	public void registerTestInWhichTheUserAlreadyExists() throws Exception{
		
		User userExist=new User("maviance", "password");
		
		//Mock the register method of UserService
		when(userService.signUpUser(refEq(userExist))).thenThrow(new UserAlreadyExistException("This user already exists"));
		
		//Now we can test the register method
		UserRegistration registrationWithUserAlreadyExists=new UserRegistration("maviance", "password", "password");	
		RequestBuilder requestBuilderWithUserAlreadyExists = MockMvcRequestBuilders
				.post("/signup")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(registrationWithUserAlreadyExists))
				.contentType(MediaType.APPLICATION_JSON);
		
		//Evaluating the assertions
		mockMvc.perform(requestBuilderWithUserAlreadyExists)
			.andExpect(status().isForbidden())
			.andExpect(jsonPath("$.message", is("This user already exists")))
			.andExpect(jsonPath("$.status", is("FORBIDDEN")));
		
	}
	
	@Test
	public void registerTestWithUserCreated() throws Exception{
				
		User user=new User("maviance", "password");
		
		//Mock the signUpUser method of SignUpService
		when(userService.signUpUser(refEq(user))).thenReturn(user);		
		
		//Now we can test the register method
		UserRegistration registration=new UserRegistration("maviance", "password", "password");	
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/signup")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(registration))
				.contentType(MediaType.APPLICATION_JSON);
		
		//Evaluating the assertions
		mockMvc.perform(requestBuilder)
			.andExpect(status().isCreated());
	}

}
