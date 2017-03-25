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
import com.smartware.domain.Expense;
import com.smartware.domain.ExpenseCategory;
import com.smartware.domain.PaymentType;
import com.smartware.utils.AppHelper;

public class ExpenseDAO {

	private static Logger logger = Logger.getLogger(ExpenseDAO.class.getName());
	
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

	public List<Expense> getExpenses() {
		List<Expense> expenses = new ArrayList<Expense>();

		Connection conn = getMoneyTrackDBConnection();
		if (conn != null) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = conn.prepareStatement("SELECT * FROM expense");
				rs = st.executeQuery();

				Expense expense = null;
				while (rs.next()) {
					expense = new Expense();
					expense.setId(rs.getLong("id_transaction"));
					expense.setPaymenType(PaymentType.valueOf(rs.getString("payment_type")));
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
