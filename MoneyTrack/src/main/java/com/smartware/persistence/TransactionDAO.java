package com.smartware.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.smartware.config.DBConfigParams;
import com.smartware.domain.Currency;
import com.smartware.domain.Transaction;
import com.smartware.utils.AppHelper;

public class TransactionDAO {
	
	private static Logger logger = Logger.getLogger(TransactionDAO.class.getName());
	
	private Connection getMoneyTrackDBConnection() {
		Connection conn = null;
		
		AppHelper appHelper = new AppHelper();

		DBConfigParams dbConfigParams = appHelper.getDBConfigParams();
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
					transaction.setCurrency(Currency.valueOf(rs.getString("currency")));
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
					transaction.setCurrency(Currency.valueOf(rs.getString("currency")));
					transaction.setConcept(rs.getString("concept"));
					
					transactions.add(transaction);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return transactions;
	}

	public void insertTransaction(Transaction transaction) {
		Connection conn = getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = conn.prepareStatement("INSERT INTO transaction (date, amount, currency, concept) VALUES (?, ?, ?, ?)");
				st.setDate(1, new java.sql.Date(transaction.getDate().getTime()));
				st.setFloat(2, transaction.getAmount());
				st.setString(3, transaction.getCurrency().name());
				st.setString(4, transaction.getConcept());

				st.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
