/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.acesinc.data.json.generator.types;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author andrewserff
 */
public class AlphaNumericType extends TypeHandler {
    public static final String TYPE_NAME = "alphaNumeric";
    public static final String TYPE_DISPLAY_NAME = "Alpha Numeric";
    
    private int length;
    private boolean constant;
    
    private String refField = null;
    private String refFieldVal = null;
    
    private String refFieldCurrentValue = null;
    
    private String constantVal;

    
    
    public AlphaNumericType() {
    }

    @Override
    public void setLaunchArguments(String[] launchArguments) {
        super.setLaunchArguments(launchArguments);
        if (launchArguments.length == 0) {
            throw new IllegalArgumentException("You must specifc a length for Alpha Numeric types");
        }
        
        length = Integer.parseInt(launchArguments[0]);

        if(launchArguments.length == 2) {
        	constant = Boolean.parseBoolean((launchArguments[1] != null) ?launchArguments[1] : "false");
        } else {
        	if(launchArguments.length> 2) {
		        if(refField == null) {
		        	refField = (launchArguments[1] != null) ?launchArguments[1] : "";
		        }
	        }
	        if(launchArguments.length> 3) {
		        if(refFieldVal == null) {
		        	refFieldVal = (launchArguments[2] != null) ?launchArguments[2] : "";
		        }
		        refFieldCurrentValue = (launchArguments[3] != null) ?launchArguments[3] : "";
	        }
        }
        

    }
    
    @Override
    public String getNextRandomValue() {
    	
		if(!StringUtils.isEmpty(refField) && !StringUtils.isEmpty(refFieldVal)) {
			System.out.println("!!! refFieldCurrentValue:" + refFieldCurrentValue +" refFieldVal:" +refFieldVal + refFieldCurrentValue.equalsIgnoreCase(refFieldVal));
			if(refFieldCurrentValue.equalsIgnoreCase(refFieldVal)) {
				if(!constant) { // toggle if off then turn on
					constant = true;
				} else { // if already on then reset constantVal so a new one can be generated.
					constantVal = null;
				}
			}
		}
    	
    	if(constant ) {
    		if(null == constantVal) {
    			constantVal = RandomStringUtils.randomAlphanumeric(length);
    		}
    		
    		return constantVal;
    		
    	} else {
    		return RandomStringUtils.randomAlphanumeric(length);
    	}
    }

    @Override
    public String getName() {
        return TYPE_NAME;
    }
            
}
