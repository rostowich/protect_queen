package com.maviance.protecting_queen.evaluation;

import com.maviance.protecting_queen.domain.Direction;
import com.maviance.protecting_queen.domain.Evaluation;

/**
 * This class provide its own implementation of evaluateCommand function i.e 'RIGHT' command
 * @author Rostow
 *
 */
public class RightCommandEvaluation implements CommandEvaluation{

	@Override
	public Evaluation evaluateCommand(Evaluation currentEvaluation, String command) {
		// For this command, we have to change only the direction but not the position
		Direction newDirection=Direction.valueOf(currentEvaluation.getPosition().getDirection().getRightValue());
		currentEvaluation.getPosition().setDirection(newDirection);
		return currentEvaluation;
	}

}
