package com.smartware.common;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppDBHelper {

	private static final Logger logger = Logger.getLogger(AppDBHelper.class.getName());
	
	private static final String CONFIG_FILENAME = "config.properties";

	DBConfigParams dbConfigParams = null;
	private Driver driver = null;

	private Properties getFileProperties(String propertiesFileName) {
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

	private DBConfigParams getDBConfigParams() {
		DBConfigParams dbConfigParams = null;

		Properties dbConfigProperties = getFileProperties(CONFIG_FILENAME);
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

		if (dbConfigParams != null) {
			dbConfigParams = getDBConfigParams();
		}

		if (dbConfigParams != null) {
			if (driver == null) {
				try {
					Class jdbcDriverClass = null;
					try {
						jdbcDriverClass = Class.forName(dbConfigParams.getDriver()); // Validating if MySQL JDBC Driver is registered in the classpath
					}
					catch (ClassNotFoundException e) {
						logger.log(Level.SEVERE, "Class not found");
						e.printStackTrace();
					}

					try {
						driver = (Driver) jdbcDriverClass.newInstance();
					}
					catch (InstantiationException e) {
						logger.log(Level.SEVERE, e.getMessage());
						e.printStackTrace();
					}
					catch (IllegalAccessException e) {
						logger.log(Level.SEVERE, e.getMessage());
						e.printStackTrace();
					}

					DriverManager.registerDriver(driver);
				}
				catch (Exception e) {
					logger.log(Level.SEVERE, e.getMessage());
					driver = null;
					e.printStackTrace();
				}
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
