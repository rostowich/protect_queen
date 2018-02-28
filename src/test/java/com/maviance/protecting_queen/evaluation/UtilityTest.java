package com.maviance.protecting_queen.evaluation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UtilityTest {

	@Test
	public void testGetCommandAndParameters(){
		String command1="CREATE 9, 9";
		String command2="PLACE 0,0,  SOUTH";
		String command3="MOVE";
		
		List<String> commandAndParameters1=Utility.getCommandAndParameters(command1);
		List<String> commandAndParameters2=Utility.getCommandAndParameters(command2);
		List<String> commandAndParameters3=Utility.getCommandAndParameters(command3);
		
		assertThat(commandAndParameters1.size()).isEqualTo(3);
		assertThat(commandAndParameters2.size()).isEqualTo(4);
		assertThat(commandAndParameters3.size()).isEqualTo(1);
		
		assertThat(commandAndParameters1.get(0)).isEqualTo("CREATE");
		assertThat(commandAndParameters1.get(1)).isEqualTo("9");
		assertThat(commandAndParameters1.get(2)).isEqualTo("9");
		assertThat(commandAndParameters2.get(0)).isEqualTo("PLACE");
		assertThat(commandAndParameters2.get(1)).isEqualTo("0");
		assertThat(commandAndParameters2.get(2)).isEqualTo("0");
		assertThat(commandAndParameters2.get(3)).isEqualTo("SOUTH");
		assertThat(commandAndParameters3.get(0)).isEqualTo("MOVE");
		
	}
}
