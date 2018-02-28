package com.maviance.protecting_queen.evaluation;

/**
 * This class is an implementation of the factory pattern to evaluate globally the command
 * @author Rostow
 *
 */
public class CommandEvaluationFactory {

	public CommandEvaluation getCommandEvaluation(String commandType){
		if(commandType==null)
			return null;
		if(commandType.equalsIgnoreCase("CREATE"))
			return new CreateCommandEvaluation();
		else if(commandType.equalsIgnoreCase("PLACE"))
			return new PlaceCommandEvaluation();
		else if (commandType.equalsIgnoreCase("MOVE"))
			return new MoveCommandEvaluation();
		else if (commandType.equalsIgnoreCase("LEFT"))
			return new LeftCommandEvaluation();
		else if(commandType.equalsIgnoreCase("RIGHT"))
			return new RightCommandEvaluation();
		else if (commandType.equalsIgnoreCase("REPORT"))
			return new ReportCommandEvaluation();
		
		return null;
			
	}
}
