package com.smartware.common;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppDBHelper {

	private static final Logger logger = Logger.getLogger(AppDBHelper.class.getName());
	
	private static final String CONFIG_FILENAME = "config.properties";

	private static DBConfigParams dbConfigParams = null;
	private static Driver driver = null;

	private static Properties getFileProperties(String propertiesFileName) throws Exception {
		Properties prop = null;

		try {
			InputStream configPropStream = AppDBHelper.class.getClassLoader().getResourceAsStream(propertiesFileName);
			prop = new Properties();
			prop.load(configPropStream);

		} catch (IOException ioex) {
			String errMsg = "problem loading property file '" + propertiesFileName + "', could not be found in the classpath >> " + ioex.getMessage();
			logger.log(Level.SEVERE, errMsg);
			throw new Exception(errMsg);
		} catch (Exception ex) {
			String errMsg = ex.getMessage();
			logger.log(Level.SEVERE, errMsg);
			throw new Exception(errMsg);
		}

		return prop;
	}

	private static void setDBConfigParams() throws Exception {
		Properties dbConfigProperties = getFileProperties(CONFIG_FILENAME);

		dbConfigParams = new DBConfigParams();
		dbConfigParams.setJdbcDriver(dbConfigProperties.getProperty("jdbcdriver"));
		dbConfigParams.setUri(dbConfigProperties.getProperty("uri"));
		dbConfigParams.setUserName(dbConfigProperties.getProperty("username"));
		dbConfigParams.setPassword(dbConfigProperties.getProperty("password"));
	}

	public static Connection getMoneyTrackDBConnection() throws Exception {
		Connection conn = null;

		if (dbConfigParams == null) {
			setDBConfigParams();
		}

		try {
			if (driver == null) {
				Class jdbcDriverClass = null;
				jdbcDriverClass = Class.forName(dbConfigParams.getJdbcDriver()); // Validating if MySQL JDBC Driver is registered in the classpath
				driver = (Driver) jdbcDriverClass.newInstance();
				DriverManager.registerDriver(driver);
			}

			conn = java.sql.DriverManager.getConnection(dbConfigParams.getUri(), dbConfigParams.getUserName(), dbConfigParams.getPassword());

		} catch (ClassNotFoundException cnfex) {
			String errMsg = "JDBC Driver Class not found >> " + cnfex.getMessage();
			logger.log(Level.SEVERE, errMsg);
			throw new Exception(errMsg);
		} catch (InstantiationException iex) {
			String errMsg = iex.getMessage();
			logger.log(Level.SEVERE, errMsg);
			throw new Exception(errMsg);
		} catch (IllegalAccessException iaex) {
			String errMsg = iaex.getMessage();
			logger.log(Level.SEVERE, errMsg);
			throw new Exception(errMsg);
		} catch (SQLException sqlex) {
			String errMsg = sqlex.getMessage();
			logger.log(Level.SEVERE, errMsg);
			throw new Exception(errMsg);
		}
		finally {
			logger.config("Connection to DB is succesfully!");
		}

		return conn;
	}

	public static void CloseConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException sqlex) {
				String errMsg = "Connection close error >> " + sqlex.getMessage();
				logger.log(Level.WARNING, errMsg);
			}
		}
	}

	public static void CloseStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException sqlex) {
				String errMsg = "Statement close error >> " + sqlex.getMessage();
				logger.log(Level.WARNING, errMsg);
			}
		}
	}

	public static void CloseResutSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException sqlex) {
				String errMsg = "ResultSet close error >> " + sqlex.getMessage();
				logger.log(Level.WARNING, errMsg);
			}
		}
	}

}
