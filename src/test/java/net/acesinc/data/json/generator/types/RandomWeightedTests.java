package net.acesinc.data.json.generator.types;

import static org.junit.Assert.assertNotSame;

import org.junit.Before;
import org.junit.Test;

public class RandomWeightedTests {

	private  RandomWeightedType randomW;
	
	@Before public void setUp() {
		randomW = new RandomWeightedType();
	}
	
	@Test public void testAlgo() {
		String[] launchArguments = {"A", "B", "95:5"};
		randomW.setLaunchArguments(launchArguments);
		for(int i=0;i<100;i++) {
			System.out.println((String) randomW.getNextRandomValue());
		}
		//assertNotSame(val1, val2);

	}
	
	
	
}
