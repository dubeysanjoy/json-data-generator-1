/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.acesinc.data.json.generator.types;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author andrewserff
 */
public class CounterType extends TypeHandler {
    public static final String TYPE_NAME = "counter";
    public static final String TYPE_DISPLAY_NAME = "Counter";
    
    private String currentCounterName;
    private Map<String, Long> namedCounterMap;
    private long startVal;
    private long maxVal;
    
    public CounterType() {
        namedCounterMap = new HashMap<>();
    }

    @Override
    public void setLaunchArguments(String[] launchArguments) {
        super.setLaunchArguments(launchArguments);
 /*       if (launchArguments.length != 1) {
            throw new IllegalArgumentException("You must specify a name for the Counter");
        }*/
        currentCounterName = launchArguments[0];
        
        startVal = 0; // default.
        try {
        	startVal = (launchArguments[1] != null) ? Long.parseLong(launchArguments[1]) : 0l;
        } catch (NumberFormatException ne) {
        	
        }
        
        maxVal = 0;
        try {
        	maxVal = (launchArguments[2] != null) ? Long.parseLong(launchArguments[2]) : 0l;
        } catch (NumberFormatException ne) {
        	
        }

        if (namedCounterMap.get(currentCounterName) == null) {
            namedCounterMap.put(currentCounterName, startVal);
        }

        
    }
    
    @Override
    public Long getNextRandomValue() {
        Long count = namedCounterMap.get(currentCounterName);
        if(count < maxVal) {
        	namedCounterMap.put(currentCounterName, count + 1);
        } else {
        	namedCounterMap.put(currentCounterName, startVal);
        }
        return count;
    }
            
    @Override
    public String getName() {
        return TYPE_NAME;
    }

}
