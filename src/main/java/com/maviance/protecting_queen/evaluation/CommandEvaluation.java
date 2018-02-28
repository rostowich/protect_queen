package com.maviance.protecting_queen.evaluation;

import com.maviance.protecting_queen.domain.Evaluation;

/**
 * We will use the Factory design pattern to evaluate each command. 
 * That is the interface that each implementation of a command has to implement
 * @author Rostow
 *
 */
public interface CommandEvaluation {

	/**
	 * Evaluate the command
	 * @param currentEvaluation
	 * @param command
	 * @return
	 */
	public Evaluation evaluateCommand(Evaluation currentEvaluation, String command);
}
