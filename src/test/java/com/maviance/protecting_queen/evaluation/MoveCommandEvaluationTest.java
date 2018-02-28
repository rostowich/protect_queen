package com.maviance.protecting_queen.evaluation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.maviance.protecting_queen.domain.Direction;
import com.maviance.protecting_queen.domain.Evaluation;
import com.maviance.protecting_queen.domain.Kingdom;
import com.maviance.protecting_queen.domain.Position;
import com.maviance.protecting_queen.evaluation.MoveCommandEvaluation;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MoveCommandEvaluationTest {
	
	@Test
	public void testEvaluateCommand(){
		//We initialize the parameters
		Kingdom kingdom=new Kingdom(2, 3);
		Evaluation currentEvaluation=new Evaluation();
		currentEvaluation.setKingdom(kingdom);
		Position position=new Position(0, 0, Direction.NORTH);
		currentEvaluation.setPosition(position);
		String command="MOVE";
		//We test the method
		MoveCommandEvaluation moveCommandEvaluation=new MoveCommandEvaluation();
		currentEvaluation=moveCommandEvaluation.evaluateCommand(currentEvaluation, command);
		//We can make the assertions
		assertThat(currentEvaluation.getKingdom().getLength()).isEqualTo(2);
		assertThat(currentEvaluation.getKingdom().getWidth()).isEqualTo(3);
		assertThat(currentEvaluation.getPosition().getX()).isEqualTo(0);
		assertThat(currentEvaluation.getPosition().getY()).isEqualTo(1);
		assertThat(currentEvaluation.getPosition().getDirection()).isEqualTo(Direction.NORTH);
		
	}

}
