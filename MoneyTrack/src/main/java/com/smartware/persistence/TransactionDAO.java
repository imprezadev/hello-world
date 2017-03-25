package com.smartware.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smartware.domain.Transaction;
import com.smartware.domain.catalog.Currency;
import com.smartware.utils.AppDBHelper;

public class TransactionDAO {
	
	public Transaction getTransaction(long id) {
		Transaction transaction = null;
		
		AppDBHelper appDBHelper = new AppDBHelper();
		
		Connection conn = appDBHelper.getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = conn.prepareStatement("SELECT id, date, amount, currency, concept FROM transaction WHERE id = ?");
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

		AppDBHelper appDBHelper = new AppDBHelper();
		Connection conn = appDBHelper.getMoneyTrackDBConnection();
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
		AppDBHelper appDBHelper = new AppDBHelper();
		Connection conn = appDBHelper.getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			try {
				st = conn.prepareStatement("INSERT INTO transaction (date, amount, currency, concept) VALUES (?, ?, ?, ?)");
				st.setTimestamp(1, new java.sql.Timestamp(transaction.getDate().getTime()));
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
