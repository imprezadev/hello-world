package com.smartware.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smartware.config.DBConfigParams;
import com.smartware.domain.Transaction;
import com.smartware.utils.AppHelper;

public class TransactionDAO {
	
	private Connection getMoneyTrackDBConnection() {
		Connection conn = null;
		
		AppHelper appHelper = new AppHelper();

		DBConfigParams dbConfigParams = appHelper.getDBConfigParams();
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
