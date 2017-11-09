package com.smartware.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.smartware.common.AppDBHelper;
import com.smartware.domain.Expense;
import com.smartware.domain.catalog.Currency;
import com.smartware.domain.catalog.ExpenseCategory;
import com.smartware.domain.catalog.ExpensePaymentType;

public class ExpenseDAO {
	
	private Expense populateExpense(ResultSet rs) throws Exception {
		Expense expense = new Expense();
		expense.setId(rs.getLong("id"));
		expense.setDate(rs.getTimestamp("date"));
		expense.setAmount(rs.getFloat("amount"));
		expense.setCurrency(Currency.valueOf(rs.getString("currency")));
		expense.setPaymentType(ExpensePaymentType.valueOf(rs.getString("payment_type")));
		expense.setDetail(rs.getString("detail"));
		expense.setCategory(ExpenseCategory.valueOf(rs.getString("category")));

		return expense;
	}

	public Expense getExpense(long id) throws Exception {
		Expense expense = null;

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			String sql = 
					"SELECT mm.id, mm.date, mm.amount, mm.currency, e.payment_type, e.detail, e.category" +
					"  FROM expense e" +
					" INNER JOIN money_movement mm ON mm.id = e.id_money_movement" +
					" WHERE e.id_money_movement = ?";

			conn = AppDBHelper.getMoneyTrackDBConnection();
			st = conn.prepareStatement(sql);
			st.setLong(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				expense = populateExpense(rs);
			}
		} finally {
			AppDBHelper.CloseResutSet(rs);
			AppDBHelper.CloseStatement(st);
			AppDBHelper.CloseConnection(conn);
		}

		return expense;
	}

	public List<Expense> getExpenses() throws Exception {
		List<Expense> expenses = new ArrayList<Expense>();

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			String sql = 
					"SELECT mm.id, mm.type, mm.date, mm.amount, mm.currency, e.payment_type, e.detail, e.category" +
					"  FROM expense e" +
					" INNER JOIN money_movement mm ON mm.id = e.id_money_movement";

			conn = AppDBHelper.getMoneyTrackDBConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();

			Expense expense = null;
			while (rs.next()) {
				expense = populateExpense(rs);
				expenses.add(expense);
			}
		} finally {
			AppDBHelper.CloseResutSet(rs);
			AppDBHelper.CloseStatement(st);
			AppDBHelper.CloseConnection(conn);
		}

		return expenses;
	}

	public void insertExpense(Expense expense) throws Exception {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			String sql = "INSERT INTO expense (id_money_movement, payment_type, category, detail) VALUES (?, ?, ?, ?)";

			conn = AppDBHelper.getMoneyTrackDBConnection();
			st = conn.prepareStatement(sql);
			st.setLong(1, expense.getId());
			st.setString(2, expense.getPaymentType().name());
			st.setString(3, expense.getCategory().name());
			st.setString(4, expense.getDetail());

			st.execute();
		} finally {
			AppDBHelper.CloseStatement(st);
			AppDBHelper.CloseConnection(conn);
		}
	}

}
