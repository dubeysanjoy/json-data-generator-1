package net.acesinc.data.json.generator.types;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

public class AlphaNumericTypeTest {
	
	private  AlphaNumericType alpha;
	
	@Before public void setUp() {
		alpha = new AlphaNumericType();
	}
	
	@Test public void testNullConstant() {
		String[] launchArguments = {"1"};
		alpha.setLaunchArguments(launchArguments);
		String val1 = alpha.getNextRandomValue();
		String val2 = alpha.getNextRandomValue();
		System.out.println(val1+ "val2:"+ val2);
		assertNotSame(val1, val2);

	}

	@Test public void test_invalidValue() {
		String[] launchArguments = {"1", "DFSD"};
		alpha.setLaunchArguments(launchArguments);
		String val1 = alpha.getNextRandomValue();
		String val2 = alpha.getNextRandomValue();
		System.out.println(val1+ "val2:"+ val2);
		assertNotSame(val1, val2);
	}

	@Test public void test_trueValue() {
		String[] launchArguments = {"1", "true"};
		alpha.setLaunchArguments(launchArguments);
		String val1 = alpha.getNextRandomValue();
		String val2 = alpha.getNextRandomValue();
		System.out.println(val1+ "val2:"+ val2);
		assertSame(val1, val2);
		
	}
	
	@Test public void test_FieldReset() {

		String test = "1";
		String test2 = new String("1");
		System.out.println(test.equalsIgnoreCase(test2));
		
/*		
		AlphaNumericType _test = new AlphaNumericType();
		
		String[] launchArguments = {"17", "claimLineNumber", "1", ""};
		
		for(int i=1; i<10; i++) {
			launchArguments[3] = String.valueOf(i);
			alpha.setLaunchArguments(launchArguments);	
			
			System.out.println("Value:" + alpha.getNextRandomValue());
		}

		for(int i=1; i<10; i++) {
			launchArguments[3] = String.valueOf(1);
			alpha.setLaunchArguments(launchArguments);	
			
			System.out.println("Value:" + alpha.getNextRandomValue());
		}
*/
	}
	
	
}
