package com.smartware.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.smartware.config.DBConfigParams;

public class AppDBHelper {
	
	private static final Logger logger = Logger.getLogger(AppDBHelper.class.getName()); 

	private DBConfigParams getDBConfigParams() {
		DBConfigParams dbConfigParams = null;

		AppHelper appHelper = new AppHelper();

		Properties dbConfigProperties = appHelper.getFileProperties("config.properties");
		if (dbConfigProperties != null) {
			dbConfigParams = new DBConfigParams();
			dbConfigParams.setDriver(dbConfigProperties.getProperty("driver"));
			dbConfigParams.setUri(dbConfigProperties.getProperty("uri"));
			dbConfigParams.setUsername(dbConfigProperties.getProperty("username"));
			dbConfigParams.setPassword(dbConfigProperties.getProperty("password"));
		}

		return dbConfigParams; 
	}

	public Connection getMoneyTrackDBConnection() {
		Connection conn = null;

		DBConfigParams dbConfigParams = getDBConfigParams();
		if (dbConfigParams != null) {
			try {
				Class.forName(dbConfigParams.getDriver()); // Validating if MySQL JDBC Driver is registered in the classpath
			} catch (ClassNotFoundException e) {
				logger.log(Level.SEVERE, "Class not found");
				e.printStackTrace();
			}
			logger.config("MySQL is registered");
	
			try {
				conn = DriverManager.getConnection(dbConfigParams.getUri(), dbConfigParams.getUsername(), dbConfigParams.getPassword());
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Connection Failed! Check output console");
				e.printStackTrace();
			}
			logger.config("Connection to DB is succesfully!");
		}

		return conn;
	}

}
