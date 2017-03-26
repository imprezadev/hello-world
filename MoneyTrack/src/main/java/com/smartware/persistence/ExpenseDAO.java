package com.smartware.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smartware.common.AppDBHelper;
import com.smartware.domain.Expense;
import com.smartware.domain.catalog.Currency;
import com.smartware.domain.catalog.ExpenseCategory;
import com.smartware.domain.catalog.PaymentType;

public class ExpenseDAO {

	public Expense getExpense(long id) {
		Expense expense = null;

		AppDBHelper appDBHelper = new AppDBHelper();
		Connection conn = appDBHelper.getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				String sql = 
						"SELECT t.id, t.date, t.amount, t.currency, e.payment_type, e.detail, e.category" +
						"  FROM expense e" +
						" INNER JOIN transaction t ON t.id = e.id_transaction" +
						" WHERE e.id_transaction = ?";

				st = conn.prepareStatement(sql);
				st.setLong(1, id);
				rs = st.executeQuery();

				if (rs.next()) {
					expense = new Expense();
					expense.setId(rs.getLong("id"));
					expense.setDate(rs.getTimestamp("date"));
					expense.setAmount(rs.getFloat("amount"));
					expense.setCurrency(Currency.valueOf(rs.getString("currency")));
					expense.setPaymenType(PaymentType.valueOf(rs.getString("payment_type")));
					expense.setDetail(rs.getString("detail"));
					expense.setCategory(ExpenseCategory.valueOf(rs.getString("category")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return expense;
	}

	public List<Expense> getExpenses() {
		List<Expense> expenses = new ArrayList<Expense>();

		AppDBHelper appDBHelper = new AppDBHelper();

		Connection conn = appDBHelper.getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				String sql = 
						"SELECT t.id, t.date, t.amount, t.currency, e.payment_type, e.detail, e.category" +
						"  FROM expense e" +
						" INNER JOIN transaction t ON t.id = e.id_transaction";

				st = conn.prepareStatement(sql);
				rs = st.executeQuery();

				Expense expense = null;
				while (rs.next()) {
					expense = new Expense();
					expense.setId(rs.getLong("id"));
					expense.setDate(rs.getTimestamp("date"));
					expense.setAmount(rs.getFloat("amount"));
					expense.setCurrency(Currency.valueOf(rs.getString("currency")));
					expense.setPaymenType(PaymentType.valueOf(rs.getString("payment_type")));
					expense.setDetail(rs.getString("detail"));
					expense.setCategory(ExpenseCategory.valueOf(rs.getString("category")));

					expenses.add(expense);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return expenses;
	}

	public void insertExpense(Expense expense) {
		AppDBHelper appDBHelper = new AppDBHelper();
		Connection conn = appDBHelper.getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			try {
				String sql = "INSERT INTO expense (id_transaction, payment_type, category, detail) VALUES (?, ?, ?, ?)";
				st = conn.prepareStatement(sql);
				st.setLong(1, expense.getId());
				st.setString(2, expense.getPaymenType().name());
				st.setString(3, expense.getCategory().name());
				st.setString(4, expense.getDetail());

				st.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
