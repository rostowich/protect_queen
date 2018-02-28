package com.maviance.protecting_queen.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import com.maviance.protecting_queen.domain.Direction;
import com.maviance.protecting_queen.domain.Evaluation;
import com.maviance.protecting_queen.exceptions.InvalidCommandException;
import com.maviance.protecting_queen.exceptions.InvalidDimensionException;
import com.maviance.protecting_queen.exceptions.QueenIsfallingIntoTheValleyException;


@SpringBootTest
@RunWith(SpringRunner.class)
public class CommandServiceTest {
	
	@InjectMocks
	private CommandServiceImpl commandService;
	
	/**
	 * We mock the Environment dependence (length and width of the kingdom from the properties file)
	 */
	@Mock
	private Environment environmentMock;
	
	/**
	 * Function used to init the mocked objects before performing the tests
	 */
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);		
	}
	
	@Test
	public void testIsValidCommand_ThrowInvalidCommandException(){
		
		String command1="PLACE 6,7,NORTH \n REPORT";
		try{
			commandService.isAValidCommand(command1);
		}
		catch (Exception e) {
			assertThat(e)
			.isInstanceOf(InvalidCommandException.class)
            .hasMessage("This command is invalid");
		}	
	}
	
	@Test
	public void testIsValidCommand_ThrowInvalidCommandException2(){
		
		String command2="CREATE 6,7 \n PLACE 2,0, NORTH";
		try{
			commandService.isAValidCommand(command2);
		}
		catch (Exception e) {
			assertThat(e)
			.isInstanceOf(InvalidCommandException.class)
            .hasMessage("This command is invalid");
		}
	
	}
	
	@Test
	public void testIsValidCommand_WithAValidCommand() throws InvalidCommandException{
		
		String command="CREATE 6,7 \n PLACE 2,0, NORTH \n MOVE \n REPORT";
	    assertThat(commandService.isAValidCommand(command)).isTrue();
		
	}
	
	@Test
	public void testEvaluateAListOfCommand_ThrowInvalidDimensionException() throws Exception{
		
		String commands="CREATE 6,7 \n PLACE 2,0, NORTH \n MOVE \n REPORT";
		
		//Mocking the dependence (Environment bean)
		when(environmentMock.getProperty("kingdom.dimension.minlength")).thenReturn("1");
		when(environmentMock.getProperty("kingdom.dimension.maxlength")).thenReturn("5");
		when(environmentMock.getProperty("kingdom.dimension.minwidth")).thenReturn("0");
		when(environmentMock.getProperty("kingdom.dimension.maxwidth")).thenReturn("5");
		
		//We can now test the method
		try{
			commandService.evaluateAListOfCommand(new Evaluation(), commands);
		}
		catch (Exception e) {
			assertThat(e)
			.isInstanceOf(InvalidDimensionException.class)
            .hasMessage("Dimensions of the kingdom are not valid");
		}
	}
	
	@Test
	public void testEvaluateAListOfCommand_ThrowQueenIsfallingIntoTheValleyException(){
		
		String commands="CREATE 2,2 \n PLACE 2,0, NORTH \n MOVE \n REPORT";
		
		//Mocking the dependence (Environment bean)
		when(environmentMock.getProperty("kingdom.dimension.minlength")).thenReturn("0");
		when(environmentMock.getProperty("kingdom.dimension.maxlength")).thenReturn("10");
		when(environmentMock.getProperty("kingdom.dimension.minwidth")).thenReturn("0");
		when(environmentMock.getProperty("kingdom.dimension.maxwidth")).thenReturn("10");
		
		//We can now test the method
		try{
			commandService.evaluateAListOfCommand(new Evaluation(), commands);
		}
		catch (Exception e) {
			assertThat(e)
			.isInstanceOf(QueenIsfallingIntoTheValleyException.class)
            .hasMessage("Warning, the queen is falling into the valley");
		}
	}
	
	@Test
	public void testEvaluateAListOfCommand_ReturnAnEvaluation() throws Exception{
		
		String commands="CREATE 4, 4 \n PLACE 2,0, EAST \n MOVE \n LEFT \n REPORT";
		
		//Mocking the dependence (Environment bean)
		when(environmentMock.getProperty("kingdom.dimension.minlength")).thenReturn("0");
		when(environmentMock.getProperty("kingdom.dimension.maxlength")).thenReturn("10");
		when(environmentMock.getProperty("kingdom.dimension.minwidth")).thenReturn("0");
		when(environmentMock.getProperty("kingdom.dimension.maxwidth")).thenReturn("10");
		
		//We can now test the method
		
		Evaluation evaluation=commandService.evaluateAListOfCommand(new Evaluation(), commands);
		assertThat(evaluation.getKingdom().getLength()).isEqualTo(4);
		assertThat(evaluation.getKingdom().getWidth()).isEqualTo(4);
		assertThat(evaluation.getPosition().getX()).isEqualTo(3);
		assertThat(evaluation.getPosition().getY()).isEqualTo(0);
		assertThat(evaluation.getPosition().getDirection()).isEqualTo(Direction.NORTH);
		
	}
}
