package com.smartware.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smartware.domain.Expense;
import com.smartware.domain.catalog.Currency;
import com.smartware.domain.catalog.ExpenseCategory;
import com.smartware.domain.catalog.PaymentType;
import com.smartware.utils.AppDBHelper;

public class ExpenseDAO {

	public List<Expense> getExpenses() {
		List<Expense> expenses = new ArrayList<Expense>();

		AppDBHelper appDBHelper = new AppDBHelper();

		Connection conn = appDBHelper.getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				String sql = 
						"SELECT t.id, t.date, t.amount, t.currency, e.payment_type, t.concept, e.category" +
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
					expense.setConcept(rs.getString("concept"));
					expense.setCategory(ExpenseCategory.valueOf(rs.getString("category")));

					expenses.add(expense);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return expenses;
	}

}
