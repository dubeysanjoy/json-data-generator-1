package net.acesinc.data.json.generator.types;

import org.junit.Test;

public class RandomFromFileTest {

	
	private RandomFromFile randomGen;
	
	public RandomFromFileTest() {
		// TODO Auto-generated constructor stub
	}
	
	@Test public void testFileReading() {
	
		randomGen = new RandomFromFile();
		
		String[] launchArguments = {"C:\\rakesh\\temp\\data\\memberIdsData_6879622688657050390.json"};
		randomGen.setLaunchArguments(launchArguments);
		
		for(int i=0; i<=7; i++) {
			System.out.println(randomGen.getNextRandomValue());
		}
	}

}
