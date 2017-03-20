package com.smartware.persistence;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.smartware.config.DBConfigParams;
import com.smartware.domain.Transaction;

public class TransactionDAO {
	
	private DBConfigParams getDBConfigParams() {
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
	
	private Connection getMoneyTrackDBConnection() {
		Connection conn = null;
		
		DBConfigParams dbConfigParams = getDBConfigParams();
		if (dbConfigParams != null) {
			try {
				Class.forName(dbConfigParams.getDriver()); // Validating if MySQL JDBC Driver is registered in the classpath
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("MySQL is registered");
	
			try {
				conn = DriverManager.getConnection(dbConfigParams.getUri(), dbConfigParams.getUsername(), dbConfigParams.getPassword());
			} catch (Exception e) {
				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				return null;
			}
			System.out.println("Connection to DB is succesfully!");
		}

		return conn;
	}

	public Transaction getTransaction(long id) {
		Transaction transaction = null;
		
		Connection conn = getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = conn.prepareStatement("SELECT * FROM transaction WHERE id = ?");
				st.setLong(1, id);
				rs = st.executeQuery();

				if (rs.next()) {
					transaction = new Transaction();
					transaction.setId(rs.getLong("id"));
					transaction.setDate(rs.getTimestamp("date"));
					transaction.setAmount(rs.getFloat("amount"));
					transaction.setConcept(rs.getString("concept"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return transaction;
	}

	public List<Transaction> getTransactions() {
		List<Transaction> transactions = new ArrayList<Transaction>();

		Connection conn = getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = conn.prepareStatement("SELECT * FROM transaction");
				rs = st.executeQuery();

				Transaction transaction = null;
				while (rs.next()) {
					transaction = new Transaction();
					transaction.setId(rs.getLong("id"));
					transaction.setDate(rs.getTimestamp("date"));
					transaction.setAmount(rs.getFloat("amount"));
					transaction.setConcept(rs.getString("concept"));
					
					transactions.add(transaction);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return transactions;
	}
}
