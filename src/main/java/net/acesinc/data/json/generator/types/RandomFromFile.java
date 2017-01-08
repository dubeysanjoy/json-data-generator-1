/**
 * 
 */
package net.acesinc.data.json.generator.types;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author cho922
 *
 */
public class RandomFromFile extends TypeHandler {

	public static final String TYPE_NAME = "randomFromFile";
	
	private String fileName;
	private final List<String> valuesArr;
	private LinkedList<String> _queue;

	private static final Logger log = LogManager.getLogger(RandomFromFile.class);
	/**
	 * 
	 */
	public RandomFromFile() {
		valuesArr = new ArrayList<String>();
	}

    @Override
    public void setLaunchArguments(String[] launchArguments) {
        super.setLaunchArguments(launchArguments);
       
        fileName = launchArguments[0];
        // no need to relaod every time.
        if(valuesArr.size() == 0) {
        	readFile(fileName);
        }
        
        //readJsonFile(fileName);
        // readFile and populate Values
    }

	
	/* (non-Javadoc)
	 * @see net.acesinc.data.json.generator.types.TypeHandler#getNextRandomValue()
	 */
	@Override
	public Object getNextRandomValue() {
		if(valuesArr ==null || valuesArr.size() <=0) {
			return "null";
		}
		//String retVal = valuesArr.get(getRand().nextInt(0, valuesArr.size()-2));
		if(_queue.size() ==0) {
			// Queue depleted... repopulate
			Collections.shuffle(valuesArr);
			_queue.addAll(valuesArr);
				
		}
		String retVal = _queue.pop();
		
		//log.info(retVal);
		
		String[] _data = retVal.substring(retVal.indexOf("{"), retVal.indexOf("}")).split(":");
		 
		return _data[1].replaceAll("\"", "");
	}

	/* (non-Javadoc)
	 * @see net.acesinc.data.json.generator.types.TypeHandler#getName()
	 */
    @Override
    public String getName() {
        return TYPE_NAME;
    }


	private void readFile(String fileName) {
		log.debug("reading File", fileName);
		try {
			
			
			InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
			// if file is not in classpath then inputstream will be null
			BufferedReader br = null;
			if(is == null) {
				log.warn(String.format("File %s is not in classpath", fileName));
				File f = new File(fileName);
				br = new BufferedReader(new FileReader(f));
			} else {
				br = new BufferedReader(new InputStreamReader(is, Charset.defaultCharset()));	
			}
			
			String line = null;
			while((line = br.readLine()) != null) {
				if(line.length() > 5) {
					valuesArr.add(line);
				}
			}
			
			//log.info(valuesArr.size());
			//log.info("first line"+ valuesArr.get(0));
			//log.info("last line"+ valuesArr.get(valuesArr.size()-1));
		} catch (IOException ex) {
			log.error("Unable to read file", fileName);
		}
		
		Collections.shuffle(valuesArr);
		
		_queue = new LinkedList<String>(valuesArr);
	}
	
	private void readJsonFile(String fileName) {
		
		try {
			
			//InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
			File f = new File(fileName);
			JsonReader rdr = Json.createReader(new FileInputStream(f));
			JsonObject _data= rdr.readObject();
			
			//log.info(_data);
		} catch (Exception ex) {
			log.error("Unable to read file", fileName);
		}
	}

	
}
