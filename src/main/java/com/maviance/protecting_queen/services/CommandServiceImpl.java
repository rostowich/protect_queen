package com.maviance.protecting_queen.services;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.maviance.protecting_queen.domain.Command;
import com.maviance.protecting_queen.domain.Direction;
import com.maviance.protecting_queen.domain.Evaluation;
import com.maviance.protecting_queen.evaluation.CommandEvaluation;
import com.maviance.protecting_queen.evaluation.CommandEvaluationFactory;
import com.maviance.protecting_queen.evaluation.Utility;
import com.maviance.protecting_queen.exceptions.InvalidDimensionException;
import com.maviance.protecting_queen.exceptions.InvalidCommandException;
import com.maviance.protecting_queen.exceptions.QueenIsfallingIntoTheValleyException;

/**
 * This class implements all the business logic about the commands
 * @author Rostow
 *
 */
@Service
public class CommandServiceImpl implements CommandService{

	private final String SEPARATOR="\n";
	
	/**
	 * Regular expression to validate the PLACE X,Y,F command
	 */
	private final String placePattern="\\s*"+Command.PLACE+"\\s+"+"\\d+"+"\\s*"+"[,]{1}"+"\\s*"+"\\d+"+"\\s*"+"[,]{1}"+"\\s*"+"("+
			Direction.EAST+"|"+Direction.NORTH+"|"+Direction.SOUTH+"|"+Direction.WEST+")"+"\\s*";
	
	/**
	 * Regular expression to validate MOVE command
	 */
	private final String movePattern="\\s*"+Command.MOVE.toString()+"\\s*";
	
	/**
	 * Regular Expression to validate LEFT command
	 */
	private final String leftPattern="\\s*"+Command.LEFT.toString()+"\\s*";
	
	/**
	 * Regular expression to validate RIGHT command
	 */
	private final String rightPattern="\\s*"+Command.RIGHT.toString()+"\\s*";
	
	/**
	 * Regular expression to validate REPORT command
	 */
	private final String reportPattern="\\s*"+Command.REPORT.toString()+"\\s*";
	
	/**
	 * Regular expression to validate CREATE command
	 */
	private final String createPattern="\\s*"+Command.CREATE+"\\s+"+"\\d+"+"\\s*"+"[,]{1}"+"\\s*"+"\\d+"+"\\s*";
	
	/**
	 * Injection of the Environmement dependence to read values from application.properties file
	 */
	@Autowired 
	private Environment environment;
	
	/*
	 * (non-Javadoc)
	 * @see com.maviance.protecting_queen.services.CommandService#isAValidCommand(java.lang.String)
	 */
	@Override
	public boolean isAValidCommand(String commands) throws InvalidCommandException {
							
		//We put all the commands into a list. The size of the list is the number of commands
		List<String> listOfCommand=Arrays.asList(commands.split(this.SEPARATOR));
		
		//We can't have less than 3 commands, If the first command is not CREATE and the second is not PLACE command, 
		//then the command is invalid
		Pattern patternForCreate=Pattern.compile(createPattern,Pattern.CASE_INSENSITIVE);
		Pattern patternForPlace=Pattern.compile(placePattern,Pattern.CASE_INSENSITIVE);
		if(listOfCommand.size()<3 || !patternForCreate.matcher(listOfCommand.get(0)).matches() || !patternForPlace.matcher(listOfCommand.get(1)).matches())
			throw new InvalidCommandException("This command is invalid");
		
		//The last command should be a REPORT command
		Pattern patternForReport=Pattern.compile(reportPattern,Pattern.CASE_INSENSITIVE);
		if(!patternForReport.matcher(listOfCommand.get(listOfCommand.size()-1)).matches())
			throw new InvalidCommandException("This command is invalid");
		
		//The others command should be different from CREATE and REPORT
		if(listOfCommand.size()>3){
			String otherCommand=placePattern+"|"+movePattern+"|"+leftPattern+"|"+rightPattern;
			Pattern OtherPattern=Pattern.compile(otherCommand,Pattern.CASE_INSENSITIVE);
			for (int i = 2; i < listOfCommand.size()-1; i++) {
				Matcher matcher=OtherPattern.matcher(listOfCommand.get(i));
				boolean isValid=matcher.matches();
				if(!isValid)
					throw new InvalidCommandException("This command is invalid");
			}
		}
		
		return true;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.maviance.protecting_queen.services.CommandService#evaluateCommand(com.maviance.protecting_queen.domain.Evaluation, java.lang.String)
	 */
	@Override
	public Evaluation evaluateAListOfCommand(Evaluation currentEvaluation, String commands) throws Exception {

		//We put all the commands into a list. The size of the list is the number of commands
		List<String> listOfCommand=Arrays.asList(commands.split(this.SEPARATOR));
				
		CommandEvaluationFactory commandEvaluationFactory=new CommandEvaluationFactory();
		//Using the Iterator to iterate each command and perform it
		Iterator<String> iteratorOfCommand=listOfCommand.iterator();
		while(iteratorOfCommand.hasNext()){
			String commandToEvaluate=iteratorOfCommand.next();
			String commandName=Utility.getCommandAndParameters(commandToEvaluate).get(0);
			CommandEvaluation commandEvaluation=commandEvaluationFactory.getCommandEvaluation(commandName);
			currentEvaluation=commandEvaluation.evaluateCommand(currentEvaluation, commandToEvaluate);
			
			//check the length and width of the kingdom according to the values from properties file
			if(currentEvaluation.getKingdom().getLength()<=Integer.parseInt(environment.getProperty("kingdom.dimension.minlength"))||
			   currentEvaluation.getKingdom().getLength()>=Integer.parseInt(environment.getProperty("kingdom.dimension.maxlength"))||
			   currentEvaluation.getKingdom().getWidth()<=Integer.parseInt(environment.getProperty("kingdom.dimension.minwidth"))||
			   currentEvaluation.getKingdom().getWidth()>=Integer.parseInt(environment.getProperty("kingdom.dimension.maxwidth")))
				throw new InvalidDimensionException("Dimensions of the kingdom are not valid");
			
			//Checking if the queen is not falling into the valley
			if(currentEvaluation.getPosition()!=null){
				if(currentEvaluation.getPosition().getX()<0 || currentEvaluation.getPosition().getX()>currentEvaluation.getKingdom().getLength() ||
				 currentEvaluation.getPosition().getY()<0 || currentEvaluation.getPosition().getY()>currentEvaluation.getKingdom().getWidth())
					throw new QueenIsfallingIntoTheValleyException("Warning, the queen is falling into the valley");
			}
		}
			
		return currentEvaluation;
	}	

}
