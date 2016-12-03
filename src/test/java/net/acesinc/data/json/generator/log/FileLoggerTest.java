/**
 * 
 */
package net.acesinc.data.json.generator.log;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * @author cho922
 *
 */
public class FileLoggerTest {

	FileLogger fileLogger;
	
	private Map<String, Object> fileLoggerOptions;
	
	@Before public void setUp() {
		fileLoggerOptions = new HashMap<String, Object>();
		
		fileLoggerOptions.put(FileLogger.OUTPUT_DIRECTORY_PROP_NAME, "c:/rakesh/temp/data");
		fileLoggerOptions.put(FileLogger.FILE_EXTENSION_PROP_NAME, "json");
		fileLoggerOptions.put(FileLogger.FILE_MAXRECORDS_PROP_NAME, "10");
		fileLoggerOptions.put(FileLogger.FILE_PREFIX_PROP_NAME, "test_med_file_");
		
		
	}
	
	@Test public void testLogEvent_FixedTuples() throws Exception {
	
		fileLogger = new FileLogger(fileLoggerOptions);
		
		for(int i=0; i<200; i++) {
			fileLogger.logEvent("Test");
		}
		
	}

	@Test public void testLogEvent_NONEWLINE() throws Exception {
		
		fileLogger = new FileLogger(fileLoggerOptions);
		
		for(int i=0; i<200; i++) {
			fileLogger.logEvent("Test");
		}
		
	}

	@Test public void testLogEvent_NONEWLINE_INVALIDVALUE() throws Exception {
		
		fileLoggerOptions.put(FileLogger.FILE_NEWLINE_PROP_NAME, "@!#Fwe");
		
		fileLogger = new FileLogger(fileLoggerOptions);
		
		assertEquals(false, fileLogger.isNewLine());
		
	}

	@Test public void testLogEvent_NONEWLINE_VALIDVALUE() throws Exception {
		
		fileLoggerOptions.put(FileLogger.FILE_NEWLINE_PROP_NAME, "1");
		
		fileLogger = new FileLogger(fileLoggerOptions);
		
		assertEquals(false, fileLogger.isNewLine());
		
	}

	
	
}
