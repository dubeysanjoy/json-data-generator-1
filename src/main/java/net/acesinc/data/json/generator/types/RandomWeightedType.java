package net.acesinc.data.json.generator.types;

import java.util.ArrayList;
import java.util.List;

public class RandomWeightedType extends TypeHandler {

	
    public static final String TYPE_NAME = "randomWeighted";
    public static final String TYPE_DISPLAY_NAME = "RandomWeighted";

    private List<Object> typedValues;
    private List<Integer> typedValuesWt;

    @Override
    public void setLaunchArguments(String[] launchArguments) {
        super.setLaunchArguments(launchArguments);
        typedValues = new ArrayList<>();
        typedValuesWt = new ArrayList<>();
        for (String s : launchArguments) {
            try {
                if (s.contains("\"") || s.contains("'")) {
                    typedValues.add(stripQuotes(s));
                } else {
                    if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
                        typedValues.add(Boolean.parseBoolean(s));
                    } else if (s.contains(".")) {
                        typedValues.add(Double.parseDouble(s));
                    }  else if (s.contains(":")) {
                    	String[] percentages = s.split(":");
                    	if(percentages.length > 0) {
                    		for(String wt : percentages) {
                    			try{
                    				typedValuesWt.add(Integer.parseInt(wt));
                    			} catch (Exception ex) {
                    				typedValuesWt.add(0);
                    			}
                    		}
                    	}
                        
                    }  
                    else {
                        typedValues.add(Long.parseLong(s));
                    }
                }
            } catch (Throwable t) {
                //error parsing, just assume string then
                typedValues.add(stripQuotes(s));
            }
        }
    }

    @Override
    public Object getNextRandomValue() {
    	
    	if(typedValues.size() != typedValuesWt.size()) {
    		throw new IllegalArgumentException("Weights do not correspond to random choices");
    	}
    	
    	int choice = getRand().nextInt(0, typedValues.size() - 1);
    	for(int wt : typedValuesWt ) {
    		if(choice <wt) {
    			return typedValues.get(getRand().nextInt(0, typedValues.size() - 1));
    		}
    	}
        return typedValues.get(getRand().nextInt(0, typedValues.size() - 1));
    }

    @Override
    public String getName() {
        return TYPE_NAME;
    }


	
	
	
	
}
