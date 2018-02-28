package com.maviance.protecting_queen.evaluation;

import com.maviance.protecting_queen.domain.Evaluation;

/**
 * This class provide its own implementation of evaluateCommand function i.e 'REPORT' command
 * @author Rostow
 *
 */
public class ReportCommandEvaluation implements CommandEvaluation{

	/*
	 * (non-Javadoc)
	 * @see com.maviance.protecting_queen.evaluation.CommandEvaluation#evaluateCommand(com.maviance.protecting_queen.domain.Evaluation, java.lang.String)
	 */
	@Override
	public Evaluation evaluateCommand(Evaluation currentEvaluation, String command) {
		// TODO Auto-generated method stub
		return currentEvaluation;
	}

}
