package com.smartware.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smartware.domain.Transaction;

public class TransactionDAO {
	
	private Connection getMoneyTrackDBConnection() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver"); // Validating if MySQL JDBC Driver is registered in the classpath
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		System.out.println("MySQL is registered");

		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/moneytrack", "root", "root");
		} catch (Exception e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}
		System.out.println("Connection to DB is succesfully!");

		return conn;
	}

	public Transaction getTransaction() {
		Transaction transaction = null;
		
		Connection conn = getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = conn.prepareStatement("SELECT * FROM transaction LIMIT 1");
				rs = st.executeQuery();

				if (rs.next()) {
					transaction = new Transaction();
					transaction.setId(rs.getLong("id"));
					transaction.setDate(rs.getDate("date"));
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
					transaction.setDate(rs.getDate("date"));
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
