package com.smartware.common;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {
	
	private static final Logger logger = Logger.getLogger(Utils.class.getName());

	public Properties getFileProperties(String propertiesFileName) {
		Properties prop = null;
		
		try {
			InputStream configPropStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName);
			
			if (configPropStream != null) {
				prop = new Properties();
				prop.load(configPropStream);
			} else {
				logger.log(Level.SEVERE, "property file '" + propertiesFileName + "' not found in the classpath");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return prop;
	}
	
}