package com.maviance.protecting_queen.services;


import org.springframework.stereotype.Component;

import com.maviance.protecting_queen.domain.Evaluation;
import com.maviance.protecting_queen.exceptions.InvalidCommandException;

/**
 * This interface exposes all the business methods about the Commands
 * @author Rostow
 *
 */
@Component
public interface CommandService {

	/**
	 * This method id used to know whether the commands are valid or not
	 * It uses regular expressions to evaluate each command
	 * @param command
	 * @return true if yes and false else
	 */
	public boolean isAValidCommand(String commands) throws InvalidCommandException;
	
	/**
	 * This method performs the evaluation of a list of commands and return the new position and the update kingdom parameters (for CREATE command)
	 * Assuming that all the commands are the valid commands
	 * @param currentEvaluation
	 * @param command
	 * @return
	 */
	public Evaluation evaluateAListOfCommand(Evaluation currentEvaluation, String commands) throws Exception;
	
}
