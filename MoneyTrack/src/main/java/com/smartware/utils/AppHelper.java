package com.smartware.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import com.smartware.config.DBConfigParams;

public class AppHelper {

	private Properties getFileProperties(String propertiesFileName) {
		Properties prop = null;

		try {
			InputStream configPropStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName);
			
			if (configPropStream != null) {
				prop = new Properties();
				prop.load(configPropStream);
			} else {
				throw new FileNotFoundException("property file '" + propertiesFileName + "' not found in the classpath");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return prop;
	}
	
	public DBConfigParams getDBConfigParams() {
		DBConfigParams dbConfigParams = null;
		
		Properties dbConfigProperties = getFileProperties("config.properties");
		if (dbConfigProperties != null) {
			dbConfigParams = new DBConfigParams();
			dbConfigParams.setDriver(dbConfigProperties.getProperty("driver"));
			dbConfigParams.setUri(dbConfigProperties.getProperty("uri"));
			dbConfigParams.setUsername(dbConfigProperties.getProperty("username"));
			dbConfigParams.setPassword(dbConfigProperties.getProperty("password"));
		}

		return dbConfigParams; 
	}
	
}
