import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import net.acesinc.data.json.generator.RandomJsonGenerator;

/**
 * 
 */

/**
 * @author cho922
 *
 */
public class RandomJsonGeneratorTest {

	private RandomJsonGenerator gen;
	
	private Map<String, Object> config;
	
	@Before public void setUp() {
		config = new LinkedHashMap<String, Object>();
	}
	
	
	
	@Test public void testStringMerge() {
	
		
		config.put("memberId","stringMerge(alpha(4), integer(11111111, 99999999), alpha(6))");
		
		gen = new RandomJsonGenerator(config);
		
		gen.generateJson();
	}

}
