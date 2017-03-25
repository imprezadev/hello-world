package com.smartware.service;

import java.util.List;

import com.smartware.domain.Expense;
import com.smartware.persistence.ExpenseDAO;

public class ExpenseService {
	
	private ExpenseDAO expenseDAO = new ExpenseDAO();

	public List<Expense> getExpenses() {
		return expenseDAO.getExpenses();
	}

}
