/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.acesinc.data.json.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.acesinc.data.json.generator.config.SimulationConfig;
import net.acesinc.data.json.generator.config.WorkflowConfig;
import net.acesinc.data.json.generator.config.JSONConfigReader;
import net.acesinc.data.json.generator.log.EventLogger;
import net.acesinc.data.json.generator.log.FileLogger;
import net.acesinc.data.json.generator.workflow.Workflow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author andrewserff
 */
public class SimulationRunner {

	private static final Logger log = LogManager.getLogger(SimulationRunner.class);
	private SimulationConfig config;
	private List<EventGenerator> eventGenerators;
	private List<Thread> eventGenThreads;
	private boolean running;
	private List<EventLogger> eventLoggers;
	private ExecutorService executor;
	
	//private final ArrayBlockingQueue<String> queue;
	//private long patience = 6000;    // 6000 1 mt.        //300000 - 5 mt.

	public SimulationRunner(SimulationConfig config, List<EventLogger> loggers) {
		this.config = config;
		this.eventLoggers = loggers;
		eventGenerators = new ArrayList<EventGenerator>();
		eventGenThreads = new ArrayList<Thread>();

		//executor = Executors.newFixedThreadPool(getThreadSize());
		
		setupSimulation();
	}
	
	
	private long getFileCapacity() {
		long result = 0;
		for(EventLogger logger : eventLoggers) {
			if(logger instanceof FileLogger) {
				FileLogger _fileLogger = (FileLogger) logger;
				result = _fileLogger.getMaxRecords();
				break;
			}
		}
		
		return result;
		
	}
	
	
	
	
	private int getThreadSize() {
		return Runtime.getRuntime().availableProcessors() -1;
	}

	private void setupSimulation() {
		running = false;
		for (WorkflowConfig workflowConfig : config.getWorkflows()) {
			try {
				Workflow w = JSONConfigReader.readConfig(
						this.getClass().getClassLoader().getResourceAsStream(workflowConfig.getWorkflowFilename()),
						Workflow.class);
				final EventGenerator gen = new EventGenerator(w, workflowConfig.getWorkflowName(), eventLoggers);
				log.info("Adding EventGenerator for [ " + workflowConfig.getWorkflowName() + ","
						+ workflowConfig.getWorkflowFilename() + " ]");
				eventGenerators.add(gen);
				//executor.execute(gen);
				eventGenThreads.add(new Thread(gen));
			} catch (IOException ex) {
				log.error("Error reading config: " + workflowConfig.getWorkflowName(), ex);
			}
		}
	}

	public void startSimulation() {
		log.info("Starting Simulation");

		long startTime = System.currentTimeMillis();

		if (eventGenThreads.size() > 0) {
			for (Thread t : eventGenThreads) {
				t.start();
			}
			running = true;
		}

		log.info("Checking Thread Status");
		
		try {
			// check thread status
			while (true) {
				if (eventGenThreads.size() > 0) {
					for (Thread t : eventGenThreads) {

						while (t.isAlive()) {
							t.join(50000); // 50sec
/*							if (((System.currentTimeMillis() - startTime) > patience) && t.isAlive()) {
								t.interrupt();
								t.join();
							}*/
						}
					}
					running = false;
					break;
				}
			}
		} catch (InterruptedException ie) {

		}

		shutDownEventLoggers();
		
	
		running = false;
		
		log.info("Execution took " + (System.currentTimeMillis() - startTime) + " ms");

	}

	public void stopSimulation() {
		log.info("Stopping Simulation");
		for (Thread t : eventGenThreads) {
			t.interrupt();
		}
		
		shutDownEventLoggers();
		
		running = false;
	}
	
	private void shutDownEventLoggers() {
		for (EventLogger l : eventLoggers) {
			l.shutdown();
		}
		
	}

	public boolean isRunning() {
		return running;
	}

}
