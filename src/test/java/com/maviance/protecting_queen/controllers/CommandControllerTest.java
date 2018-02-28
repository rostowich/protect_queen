package com.maviance.protecting_queen.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maviance.protecting_queen.domain.Direction;
import com.maviance.protecting_queen.domain.Evaluation;
import com.maviance.protecting_queen.domain.Kingdom;
import com.maviance.protecting_queen.domain.Position;
import com.maviance.protecting_queen.exceptions.InvalidCommandException;
import com.maviance.protecting_queen.exceptions.InvalidDimensionException;
import com.maviance.protecting_queen.exceptions.QueenIsfallingIntoTheValleyException;
import com.maviance.protecting_queen.services.CommandService;


@RunWith(SpringRunner.class)
@WebMvcTest(value=CommandController.class, secure=false)
public class CommandControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	//We  have to add all these mocks to the spring ApplicationContext
	@MockBean
	private CommandService commandService;
	
	@Test
	public void testPerformCommandThrowsInvalidCommandException() throws Exception{
		
		String command="PLACE 6,7,NORTH \n REPORT";
		//We can mock the commandService dependency
		when(commandService.isAValidCommand(command)).thenThrow(new InvalidCommandException());
		
		//We can call the REST API
		RequestBuilder requestBuilder=MockMvcRequestBuilders
				.post("/api/perform")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(command))
				.contentType(MediaType.APPLICATION_JSON);		
		try{
			//Now we can test the concrete method
			mockMvc.perform(requestBuilder).andReturn();
		}
		catch(Exception e){
			assertThat(e)
			.isInstanceOf(InvalidCommandException.class)
			.hasMessage("This command is invalid");
		}	
	}
	
	@Test
	public void testPerformCommandThrowsInvalidDimensionException() throws Exception{
		
		String command="CREATE 20,30 \n PLACE 6,7,NORTH \n MOVE \n REPORT";
		//We can mock the commandService dependency
		when(commandService.isAValidCommand(command)).thenReturn(true);
		when (commandService.evaluateAListOfCommand(new Evaluation(), command)).thenThrow(new InvalidDimensionException());
		
		//We can call the REST API
		RequestBuilder requestBuilder=MockMvcRequestBuilders
				.post("/api/perform")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(command))
				.contentType(MediaType.APPLICATION_JSON);		
		try{
			//Now we can test the concrete method
			mockMvc.perform(requestBuilder).andReturn();
		}
		catch(Exception e){
			assertThat(e)
			.isInstanceOf(InvalidDimensionException.class)
			.hasMessage("Dimensions of the kingdom are not valid");
		}	
	}
	
	
	@Test
	public void testPerformCommandThrowsQueenIsfallingIntoTheValleyException() throws Exception{
		
		String command="CREATE 20,30 \n PLACE 6,7,NORTH \n MOVE \n REPORT";
		//We can mock the commandService dependency
		when(commandService.isAValidCommand(command)).thenReturn(true);
		when (commandService.evaluateAListOfCommand(new Evaluation(), command)).thenThrow(new QueenIsfallingIntoTheValleyException());
		
		//We can call the REST API
		RequestBuilder requestBuilder=MockMvcRequestBuilders
				.post("/api/perform")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(command))
				.contentType(MediaType.APPLICATION_JSON);		
		try{
			//Now we can test the concrete method
			mockMvc.perform(requestBuilder).andReturn();
		}
		catch(Exception e){
			assertThat(e)
			.isInstanceOf(QueenIsfallingIntoTheValleyException.class)
			.hasMessage("Warning, the queen is falling into the valley");
		}	
	}
	
	@Test
	public void testPerformCommandReturnThePositionOfTheQueen() throws Exception{
		
		String command="CREATE 5,5 \n PLACE 1,0, NORTH \n MOVE \n REPORT";
		//We can mock the commandService dependency
		when(commandService.isAValidCommand(command)).thenReturn(true);
		Position position=new Position(1, 1, Direction.NORTH);
		Kingdom kingdom=new Kingdom(5, 5);
		Evaluation finalEvaluation=new Evaluation(kingdom, position);
		when (commandService.evaluateAListOfCommand(anyObject(), anyString())).thenReturn(finalEvaluation);
		
		//We can call the REST API
		RequestBuilder requestBuilder=MockMvcRequestBuilders
				.post("/api/perform")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(command))
				.contentType(MediaType.APPLICATION_JSON);
		
		//Evaluating the assertions
		mockMvc.perform(requestBuilder)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.kingdom.length", is(5)))
			.andExpect(jsonPath("$.kingdom.width", is(5)))
			.andExpect(jsonPath("$.position.x", is(1)))
			.andExpect(jsonPath("$.position.y", is(1)))
			.andExpect(jsonPath("$.position.direction", is("NORTH")));	
		
	}
}
