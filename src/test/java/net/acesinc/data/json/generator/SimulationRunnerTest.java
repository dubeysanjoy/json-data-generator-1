/**
 * 
 */
package net.acesinc.data.json.generator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.acesinc.data.json.generator.config.SimulationConfig;

/**
 * @author cho922
 *
 */
public class SimulationRunnerTest {
	
	private static final Logger log = LoggerFactory.getLogger(SimulationRunnerTest.class);
	
	SimulationRunner _simRunner;
	
	String _testConfig = "{\"workflows\": [{\"workflowName\": \"test\",\"workflowFilename\": \"medWorkflow.json\"}],\"producers\":[{\"type\":\"logger\"},{\"type\":\"file\",\"output.directory\": \"C:/spring-workspace/json-data-generator/data\",\"file.prefix\": \"med_data_\",\"file.extension\":\".json\",\"file.maxRecords\":\"10\"}]}";

	String configFile = "instClaimConfig.json";
	
	SimulationConfig simConfig;
	
	@Before public void setUp() throws Exception {
		//simConfig = JSONConfigReader.readConfig(_testConfig, SimulationConfig.class);
		
	}
	/*
	private void checkFile(String filename) {
		File _f = new File(filename)
	}*/
	
	@Test public void testRunSimulation() { 
		JsonDataGenerator _dataGen = new JsonDataGenerator(configFile);
		
		
		
		long start = System.currentTimeMillis();
		
		_dataGen.startRunning();
		
		System.out.println("sysout: total time:" + (System.currentTimeMillis() - start));
		
		log.info("Total Time:" + (System.currentTimeMillis() - start));
	}

	@Test public void testRunSimulation_provider() {
		JsonDataGenerator _dataGen = new JsonDataGenerator("providerConfig.json");
		
		
		
		long start = System.currentTimeMillis();
		
		_dataGen.startRunning();
		
		System.out.println("sysout: total time:" + (System.currentTimeMillis() - start));
		
		log.info("Total Time:" + (System.currentTimeMillis() - start));
	}


	@Test public void testRunSimulation_member() {
		JsonDataGenerator _dataGen = new JsonDataGenerator("membersConfig.json");
		
		
		
		long start = System.currentTimeMillis();
		
		_dataGen.startRunning();
		
		System.out.println("sysout: total time:" + (System.currentTimeMillis() - start));
		
		log.info("Total Time:" + (System.currentTimeMillis() - start));
	}

	@Test public void testRunSimulation_memberIdOnly() {
		JsonDataGenerator _dataGen = new JsonDataGenerator("memberIdConfig.json");
		
		long start = System.currentTimeMillis();
		
		_dataGen.startRunning();
		
		System.out.println("sysout: total time:" + (System.currentTimeMillis() - start));
		
		log.info("Total Time:" + (System.currentTimeMillis() - start));
	}
	
	@Test public void testRunSimulation_providerIdOnly() {
		JsonDataGenerator _dataGen = new JsonDataGenerator("providerIdOnlyConfig.json");
		
		long start = System.currentTimeMillis();
		
		_dataGen.startRunning();
		
		System.out.println("sysout: total time:" + (System.currentTimeMillis() - start));
		
		log.info("Total Time:" + (System.currentTimeMillis() - start));
	}
	
	@Test public void testRunSimulation_megEpisode() {
		JsonDataGenerator _dataGen = new JsonDataGenerator("megEpisodeConfig.json");
		
		long start = System.currentTimeMillis();
		
		_dataGen.startRunning();
		
		System.out.println("sysout: total time:" + (System.currentTimeMillis() - start));
		
		log.info("Total Time:" + (System.currentTimeMillis() - start));
	}

	@Test public void testRunSimulation_claimNumbers() {
		JsonDataGenerator _dataGen = new JsonDataGenerator("claimNumbersConfig.json");
		
		long start = System.currentTimeMillis();
		
		_dataGen.startRunning();
		
		System.out.println("sysout: total time:" + (System.currentTimeMillis() - start));
		
		log.info("Total Time:" + (System.currentTimeMillis() - start));
	}	
	
	@Ignore public void testGetProcessors() {
		int cpus = Runtime.getRuntime().availableProcessors();
		assertEquals(4, cpus);
	}
	

}
