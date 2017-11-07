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
import com.smartware.domain.catalog.ExpensePaymentType;

public class ExpenseDAO {
	
	private Expense populateExpense(ResultSet rs) {
		Expense expense = new Expense();
		try {
			expense.setId(rs.getLong("id"));
			expense.setDate(rs.getTimestamp("date"));
			expense.setAmount(rs.getFloat("amount"));
			expense.setCurrency(Currency.valueOf(rs.getString("currency")));
			expense.setPaymentType(ExpensePaymentType.valueOf(rs.getString("payment_type")));
			expense.setDetail(rs.getString("detail"));
			expense.setCategory(ExpenseCategory.valueOf(rs.getString("category")));
		}
		catch (Exception e) {
			e.printStackTrace();
			expense = null;
		}

		return expense;
	}

	public Expense getExpense(long id) {
		Expense expense = null;

		AppDBHelper appDBHelper = new AppDBHelper();
		Connection conn = appDBHelper.getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				String sql = 
						"SELECT mm.id, mm.date, mm.amount, mm.currency, e.payment_type, e.detail, e.category" +
						"  FROM expense e" +
						" INNER JOIN money_movement mm ON mm.id = e.id_money_movement" +
						" WHERE e.id_money_movement = ?";

				st = conn.prepareStatement(sql);
				st.setLong(1, id);
				rs = st.executeQuery();

				if (rs.next()) {
					expense = populateExpense(rs);
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
						"SELECT mm.id, mm.type, mm.date, mm.amount, mm.currency, e.payment_type, e.detail, e.category" +
						"  FROM expense e" +
						" INNER JOIN money_movement mm ON mm.id = e.id_money_movement";

				st = conn.prepareStatement(sql);
				rs = st.executeQuery();

				Expense expense = null;
				while (rs.next()) {
					expense = populateExpense(rs);
					expenses.add(expense);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				appDBHelper.CloseResutSet(rs);
				appDBHelper.CloseStatement(st);
				appDBHelper.CloseConnection(conn);
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
				String sql = "INSERT INTO expense (id_money_movement, payment_type, category, detail) VALUES (?, ?, ?, ?)";
				st = conn.prepareStatement(sql);
				st.setLong(1, expense.getId());
				st.setString(2, expense.getPaymentType().name());
				st.setString(3, expense.getCategory().name());
				st.setString(4, expense.getDetail());

				st.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				appDBHelper.CloseStatement(st);
				appDBHelper.CloseConnection(conn);
			}
		}
	}
}
