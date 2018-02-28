package com.maviance.protecting_queen.evaluation;

import com.maviance.protecting_queen.domain.Direction;
import com.maviance.protecting_queen.domain.Evaluation;


/**
 * This class provide its own implementation of evaluateCommand function i.e 'LEFT' command
 * @author Rostow
 *
 */
public class LeftCommandEvaluation implements CommandEvaluation{

	/*
	 * (non-Javadoc)
	 * @see com.maviance.protecting_queen.evaluation.CommandEvaluation#evaluateCommand(com.maviance.protecting_queen.domain.Evaluation, java.lang.String)
	 */
	@Override
	public Evaluation evaluateCommand(Evaluation currentEvaluation, String command) {
		// For this command, we have to change only the direction and not the position
		System.out.println("The command is "+command+" Before left "+currentEvaluation.toString());
		Direction newDirection=Direction.valueOf(currentEvaluation.getPosition().getDirection().getLeftValue());
		currentEvaluation.getPosition().setDirection(newDirection);
		System.out.println("After left "+currentEvaluation.toString());
		
		return currentEvaluation;
	}

}
