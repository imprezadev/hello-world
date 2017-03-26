package com.smartware.app;

import java.util.logging.Logger;

import com.smartware.domain.Expense;
import com.smartware.service.ExpenseService;

public class App {
	final static Logger logger = Logger.getLogger(App.class.getName());

	public static void main(String[] args) {
		ExpenseService expenseService = new ExpenseService();
		
		Expense expense = expenseService.getExpense(3L);

		logger.info(expense.toString());
	}

}