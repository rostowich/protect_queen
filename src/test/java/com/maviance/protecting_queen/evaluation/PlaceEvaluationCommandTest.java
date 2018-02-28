package com.maviance.protecting_queen.evaluation;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.maviance.protecting_queen.domain.Direction;
import com.maviance.protecting_queen.domain.Evaluation;
import com.maviance.protecting_queen.domain.Kingdom;
import com.maviance.protecting_queen.evaluation.PlaceCommandEvaluation;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PlaceEvaluationCommandTest {

	@Test
	public void testEvaluationCommand(){
		
		Kingdom kingdom=new Kingdom(2, 3);
		Evaluation currentEvaluation=new Evaluation();
		currentEvaluation.setKingdom(kingdom);
		//Now we can test the method
		PlaceCommandEvaluation placeCommandEvaluation=new PlaceCommandEvaluation();
		currentEvaluation=placeCommandEvaluation.evaluateCommand(currentEvaluation, "PLACE 0,1,NORTH");
		//We can make the assertion to check the method
		assertThat(currentEvaluation.getKingdom().getLength()).isEqualTo(2);
		assertThat(currentEvaluation.getKingdom().getWidth()).isEqualTo(3);
		assertThat(currentEvaluation.getPosition().getX()).isEqualTo(0);
		assertThat(currentEvaluation.getPosition().getY()).isEqualTo(1);
		assertThat(currentEvaluation.getPosition().getDirection()).isEqualTo(Direction.NORTH);
		
	}
	
	
}
