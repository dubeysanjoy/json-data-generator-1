package net.acesinc.data.json.generator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.junit.Test;

public class MiscTest {
	
	@Test public void testFieldParsing() {
		String test = "17,FieldReset('claimLineNumber', '1'";
	
		System.out.println(test.indexOf("FieldReset"));
		
		String _temp = test.substring(test.indexOf("FieldReset") + "FieldReset(".length());
		
		String[] arr = _temp.split(",");
		
		System.out.println(arr[0].replaceAll("'","") + arr[1]);
		
	}
	
	@Test public void testTimestamp() {
		System.out.println(new Date().getTime());
	}
	
	@Test public void testSecureRandom() {
		RandomDataGenerator xx = new RandomDataGenerator();
		
		for (int i=0; i<1999; i++) {
			System.out.println(xx.nextInt(0, 1999));
		}
	}

	@Test public void testSecureRandomSecure() {
		RandomDataGenerator xx = new RandomDataGenerator();
		
		for (int i=0; i<1999; i++) {
			System.out.println(xx.nextSecureInt(0, 1999));
		}
	}

	@Test public void testPop() {
		List<String> _data = Arrays.asList(new String[]{"1", "1", "3", "4", "5", "6"});

		Collections.shuffle(_data);
		
		for(String _d : _data) {
			System.out.println(_d);
		}

		LinkedList<String> _q = new LinkedList<String>(_data);
		System.out.println("From Queue");
		for(int i=0; i<10; i++) {
			if(_q.size() == 0) {
				System.out.println("reseeded the queue.. and off we go...");;
				_q.addAll(_data);
			}
			try {
				System.out.println(_q.pop());
			} catch (Exception ex) {
				
			}
		}
		
	}
	
	
}
