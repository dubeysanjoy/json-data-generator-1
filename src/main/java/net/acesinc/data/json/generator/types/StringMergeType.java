/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.acesinc.data.json.generator.types;

import java.util.Arrays;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author andrewserff
 */
public class StringMergeType extends TypeHandler {

    public static final String TYPE_NAME = "stringMerge";
    public static final String TYPE_DISPLAY_NAME = "String Merge";
    public static final String SPACE="b";

    private static final Logger log = Logger.getLogger(StringMergeType.class);
    private String delimiter;
    private String[] stringsToMerge;

    @Override
    public void setLaunchArguments(String[] launchArguments) {
        delimiter = launchArguments[0];
        stringsToMerge = Arrays.copyOfRange(launchArguments, 1, launchArguments.length);
    }

    @Override
    public Object getNextRandomValue() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stringsToMerge.length; i++) {
            String s = stringsToMerge[i];
            
            if(s.startsWith("alpha")) {
            	sb.append(resolveAlphaType(s));
            } else if(s.startsWith("integer")) {
            	sb.append(resolveIntegerType(s));
            } else { 
            
            	sb.append(s);
            }

            if (i < stringsToMerge.length - 1) {
            	
                if(SPACE.equalsIgnoreCase(delimiter)) {
                	sb.append(" ");
                } else {
                	sb.append(delimiter);
                }
            }
        }
        return sb.toString();
    }

	private String resolveAlphaType(String s) {
		try {
			log.debug("embedded type is" + s);
			String op = s.substring(0, s.indexOf("("));
			String len = s.substring(s.indexOf("(") + 1, s.lastIndexOf(")"));
			if("alpha".equalsIgnoreCase(op)) {
				return RandomStringUtils.randomAlphabetic(Integer.parseInt(len));
			}
		} catch (Exception ex) {
			log.error("parsing embedded type error", ex);
		}
		
		return null;
	}

	private String resolveIntegerType(String s) {
		try {
			log.debug("embedded type is" + s);
			String op = s.substring(0, s.indexOf("("));
			String op_params = s.substring(s.indexOf("(") + 1, s.lastIndexOf(")"));
			String[] values = op_params.split("-");
			IntegerType itype = new IntegerType();
			itype.setLaunchArguments(values);
			return itype.getNextRandomValue().toString();
		} catch (Exception ex) {
			log.error("parsing embedded type error", ex);
		}
		
		return null;
	}
	
	
    @Override
    public String getName() {
        return TYPE_NAME;
    }

}
