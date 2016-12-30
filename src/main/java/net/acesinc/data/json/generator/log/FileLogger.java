/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.acesinc.data.json.generator.log;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author andrewserff
 * @author choudhary, Rakesh - Added max record options and newline true| false
 *         options,
 */
public class FileLogger implements EventLogger {

	public boolean isInitialized() {
		return initialized;
	}

	public File getOutputDirectory() {
		return outputDirectory;
	}

	public String getFilePrefix() {
		return filePrefix;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public long getMaxRecords() {
		return maxRecords;
	}

	public long getNumTuples() {
		return numTuples;
	}

	public boolean isNewLine() {
		return newLine;
	}

	private static final Logger log = LogManager.getLogger(FileLogger.class);
	public static final String OUTPUT_DIRECTORY_PROP_NAME = "output.directory";
	public static final String FILE_PREFIX_PROP_NAME = "file.prefix";
	public static final String FILE_EXTENSION_PROP_NAME = "file.extension";
	public static final String FILE_MAXRECORDS_PROP_NAME = "file.maxRecords";
	public static final String FILE_NEWLINE_PROP_NAME = "file.newLine";
	public static final String FILE_NEWLINE_DELIMI_PROP_NAME = "file.newLineDelimiter";
	public static final String JSON_ROOT_START_ELEMENT="[";
	public static final String JSON_ROOT_END_ELEMENT="]";
	public static final String JSON_TUPLE_DELIMITER=",";

	boolean initialized = false;

	private File outputDirectory;
	private String filePrefix;
	private String fileExtension;
	private long maxRecords;
	private long numTuples;
	private File _file;
	private boolean newLine;
	private String newLineDelimiter;

	public FileLogger(Map<String, Object> props) throws IOException {
		String outputDir = (String) props.get(OUTPUT_DIRECTORY_PROP_NAME);
		outputDirectory = new File(outputDir);
		if (!outputDirectory.exists()) {
			if (!outputDirectory.mkdir()) {
				if (!outputDirectory.mkdirs()) {
					throw new IOException("Output directory does not exist and we are unable to create it");
				}
			}
		}
		filePrefix = (String) props.get(FILE_PREFIX_PROP_NAME);
		fileExtension = (String) props.get(FILE_EXTENSION_PROP_NAME);

		try {
			maxRecords = Long.parseLong((String) (props.get(FILE_MAXRECORDS_PROP_NAME)));
		} catch (Exception ex) {
			log.warn("###  Error parsing maxRecord defaulting to 100");
			maxRecords = 100;
		}

		newLine = (props.get(FILE_NEWLINE_PROP_NAME) == null) ? false : Boolean.valueOf((String) props.get(FILE_NEWLINE_PROP_NAME));

		newLineDelimiter=(props.get(FILE_NEWLINE_DELIMI_PROP_NAME) == null) ? JSON_TUPLE_DELIMITER : (String) props.get(FILE_NEWLINE_DELIMI_PROP_NAME);
		
	}

	@Override
	public void logEvent(String event) {

		//log.debug("## event to log" + event);
		
		try {
			if (!initialized) {
				
				numTuples = 0;
				initialized = true;
				_file = File.createTempFile(filePrefix, fileExtension, outputDirectory);
				
				log.info("initilizing new file???" + _file.getName());
				
//				FileUtils.writeStringToFile(_file, JSON_ROOT_START_ELEMENT);
			}

			// write only if maxRecords have not been reached.
			if (checkStatus()) {
				FileUtils.writeStringToFile(_file, event, "UTF-8", true);

				if(checkFutureStatus()) {
					FileUtils.writeStringToFile(_file, newLineDelimiter, "UTF-8", true);
				}
				
				if (newLine) {
					FileUtils.writeByteArrayToFile(_file, System.getProperty("line.separator").getBytes(), true);
				}

				numTuples++;
			} else {
				// write end root doc
				initialized = false;
//				FileUtils.writeStringToFile(_file, JSON_ROOT_END_ELEMENT, "UTF-8", true);
				
			}
		} catch (IOException ioe) {
			log.error("Unable to create temp file");
		}

	}

	/**
	 * 
	 * Activity Log status check. It will be closed as per configured time or
	 * configured max. no. of records
	 * 
	 * @return boolean true indicates current activity log file has been closed
	 */
	private boolean checkStatus() {
		boolean flag = false;
		
		log.info("record Count -> " + numTuples);
		
		if (numTuples < maxRecords) {

			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * Activity Log status check. It will be closed as per configured time or
	 * configured max. no. of records
	 * 
	 * @return boolean true indicates current activity log file has been closed
	 */
	private boolean checkFutureStatus() {
		boolean flag = false;
		if ((numTuples + 1)< maxRecords) {

			flag = true;
		}
		return flag;
	}

	
	@Override
	public void shutdown() {
		// we don't need to shut anything down
	}

}
