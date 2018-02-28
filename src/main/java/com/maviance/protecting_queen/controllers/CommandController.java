package com.maviance.protecting_queen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maviance.protecting_queen.domain.Evaluation;
import com.maviance.protecting_queen.services.CommandService;

/**
 * This Rest controller is charged of evaluating the commands of the Queeen.  It takes as input the list of the commands
 * and return the position of the queen in her kingdom
 * @author Rostow
 *
 */
@RestController
@RequestMapping(value="api/")
public class CommandController {
	
	/**
	 * Dependency injection of the CommandService
	 */
	@Autowired
	private CommandService commandService;
	
	/**
	 * Function that evaluates the input commands and return the position of the queen in her kingdom
	 * @param commands
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="perform", method=RequestMethod.POST)
	public ResponseEntity<Evaluation>  performCommand(@RequestBody String commands) throws Exception {
		
		//We ensure that all the commands are the valid commands
		commandService.isAValidCommand(commands);
		Evaluation currentEvaluation=new Evaluation();
		currentEvaluation=commandService.evaluateAListOfCommand(currentEvaluation, commands);
		return ResponseEntity.ok().body(currentEvaluation); 		
	}
	
}
