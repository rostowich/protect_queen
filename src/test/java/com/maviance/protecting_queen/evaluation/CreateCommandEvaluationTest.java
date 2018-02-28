package com.maviance.protecting_queen.evaluation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.maviance.protecting_queen.domain.Evaluation;
import com.maviance.protecting_queen.evaluation.CreateCommandEvaluation;
import com.maviance.protecting_queen.exceptions.InvalidDimensionException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CreateCommandEvaluationTest {

	@Test
	public void testEvaluateCommand() throws InvalidDimensionException{
		Evaluation evaluation=new Evaluation();
		String command="CREATE 6,7";
		CreateCommandEvaluation createCommandEvaluation=new CreateCommandEvaluation();
		Evaluation resultEvaluation=createCommandEvaluation.evaluateCommand(evaluation, command);
		//Making of the assertions
		assertThat(resultEvaluation.getKingdom()).isNotNull();
		assertThat(resultEvaluation.getKingdom().getLength()).isEqualTo(6);
		assertThat(resultEvaluation.getKingdom().getWidth()).isEqualTo(7);
	}
}
