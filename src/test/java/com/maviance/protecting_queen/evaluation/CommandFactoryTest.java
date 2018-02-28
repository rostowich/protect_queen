package com.maviance.protecting_queen.evaluation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.maviance.protecting_queen.evaluation.CommandEvaluation;
import com.maviance.protecting_queen.evaluation.CommandEvaluationFactory;
import com.maviance.protecting_queen.evaluation.CreateCommandEvaluation;
import com.maviance.protecting_queen.evaluation.LeftCommandEvaluation;
import com.maviance.protecting_queen.evaluation.MoveCommandEvaluation;
import com.maviance.protecting_queen.evaluation.PlaceCommandEvaluation;
import com.maviance.protecting_queen.evaluation.ReportCommandEvaluation;
import com.maviance.protecting_queen.evaluation.RightCommandEvaluation;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommandFactoryTest {
	
	@Test
	public void testGetCommandEvaluation(){
		
		CommandEvaluationFactory commandEvaluationFactory=new CommandEvaluationFactory();
		
		CommandEvaluation createCommandEvaluation=commandEvaluationFactory.getCommandEvaluation("CREATE");
		CommandEvaluation placeCommandEvaluation=commandEvaluationFactory.getCommandEvaluation("PLACE");
		CommandEvaluation moveCommandEvaluation=commandEvaluationFactory.getCommandEvaluation("MOVE");
		CommandEvaluation rightCommandEvaluation=commandEvaluationFactory.getCommandEvaluation("RIGHT");
		CommandEvaluation leftCommandEvaluation=commandEvaluationFactory.getCommandEvaluation("LEFT");
		CommandEvaluation reportCommandEvaluation=commandEvaluationFactory.getCommandEvaluation("REPORT");
		CommandEvaluation nullCommandEvaluation=commandEvaluationFactory.getCommandEvaluation(null);
		CommandEvaluation nonExistCommandEvaluation=commandEvaluationFactory.getCommandEvaluation("NONEXIST");
		
		
		assertThat(createCommandEvaluation).isInstanceOf(CreateCommandEvaluation.class);
		assertThat(placeCommandEvaluation).isInstanceOf(PlaceCommandEvaluation.class);
		assertThat(moveCommandEvaluation).isInstanceOf(MoveCommandEvaluation.class);
		assertThat(rightCommandEvaluation).isInstanceOf(RightCommandEvaluation.class);
		assertThat(leftCommandEvaluation).isInstanceOf(LeftCommandEvaluation.class);
		assertThat(reportCommandEvaluation).isInstanceOf(ReportCommandEvaluation.class);
		assertThat(nullCommandEvaluation).isNull();
		assertThat(nonExistCommandEvaluation).isNull();
	}

}
