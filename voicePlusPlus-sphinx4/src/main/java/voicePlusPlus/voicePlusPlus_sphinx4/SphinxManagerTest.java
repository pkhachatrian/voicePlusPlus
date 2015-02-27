package voicePlusPlus.voicePlusPlus_sphinx4;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class SphinxManagerTest {

	@Before
	public void setUp() {
	
	}

	@After
	public void tearDown(){
	
	}

	@Test
	public void testGetCommand() {
		setUp();
		
		//ARRANGE
		String input = "hi my name is julio invocabot schedule an appointment with chris at twelve p m tomorrow";
		String desiredOutput = "schedule an appointment with chris at twelve p m tomorrow";
		
		//ACT
		String output = SphinxManager.GetCommand(input);
		
		//ASSERT
		assertEquals(desiredOutput, output);
		
		tearDown();
	}

}
