package com.maviance.protecting_queen.evaluation;

import java.util.List;

import com.maviance.protecting_queen.domain.Direction;
import com.maviance.protecting_queen.domain.Evaluation;
import com.maviance.protecting_queen.domain.Position;

/**
 * This class provide its own implementation of evaluateCommand function i.e 'PLACE' command
 * @author Rostow
 *
 */
public class PlaceCommandEvaluation implements CommandEvaluation{

	@Override
	public Evaluation evaluateCommand(Evaluation currentEvaluation, String command) {
		System.out.println("The command is "+command+" Before placing "+currentEvaluation.toString());
		//Get all the parameters from the command
		List<String> parameters=Utility.getCommandAndParameters(command);
		
		//We update the position of the queen in the kingdom
		Position actualPosition=new Position(Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)),
														Direction.valueOf(parameters.get(3)));
		currentEvaluation.setPosition(actualPosition);
		return currentEvaluation;
	}

}
