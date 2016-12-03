package net.acesinc.data.json.generator;

import org.junit.Test;

public class MiscTest {
	
	@Test public void testFieldParsing() {
		String test = "17,FieldReset('claimLineNumber', '1'";
	
		System.out.println(test.indexOf("FieldReset"));
		
		String _temp = test.substring(test.indexOf("FieldReset") + "FieldReset(".length());
		
		String[] arr = _temp.split(",");
		
		System.out.println(arr[0].replaceAll("'","") + arr[1]);
		
	}

}
