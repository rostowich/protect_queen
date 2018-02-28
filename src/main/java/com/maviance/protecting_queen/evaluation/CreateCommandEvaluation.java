package com.maviance.protecting_queen.evaluation;

import java.util.List;

import com.maviance.protecting_queen.domain.Evaluation;
import com.maviance.protecting_queen.domain.Kingdom;

/**
 * This class provide its own implementation of evaluateCommand function i.e 'CREATE' command
 * @author Rostow
 *
 */
public class CreateCommandEvaluation implements CommandEvaluation{

	/*
	 * (non-Javadoc)
	 * @see com.maviance.protecting_queen.evaluation.CommandEvaluation#evaluateCommand(com.maviance.protecting_queen.domain.Evaluation, java.lang.String)
	 */
	@Override
	public Evaluation evaluateCommand(Evaluation currentEvaluation, String command){
		// Getting the length and the width of the kingdom from the command
		List<String> lengthAndWidth=Utility.getCommandAndParameters(command);
		//We get the length and the width
		int length=Integer.parseInt(lengthAndWidth.get(1));
		int width=Integer.parseInt(lengthAndWidth.get(2));
		
		//With the create command, we just create the kingdom and the position does not change
		Kingdom kingdom=new Kingdom(length, width);
		currentEvaluation.setKingdom(kingdom);
		return currentEvaluation;
	}

}
