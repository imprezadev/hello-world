package com.smartware.service;

import java.util.List;

import com.smartware.domain.Expense;
import com.smartware.domain.Transaction;
import com.smartware.persistence.ExpenseDAO;
import com.smartware.persistence.TransactionDAO;

public class ExpenseService {
	
	private ExpenseDAO expenseDAO = new ExpenseDAO();
	private TransactionDAO transactionDAO = new TransactionDAO();
	
	public Expense getExpense(long id) {
		return expenseDAO.getExpense(id);
	}

	public List<Expense> getExpenses() {
		return expenseDAO.getExpenses();
	}
	
	public void insertExpense(Expense expense) {
		Transaction transaction = new Transaction();
		transaction.setAmount(expense.getAmount());
		transaction.setDate(expense.getDate());
		transaction.setCurrency(expense.getCurrency());
		transaction.setConcept(expense.getConcept());

		long id = transactionDAO.insertTransaction(transaction);
		
		expense.setId(id);
		expenseDAO.insertExpense(expense);
	}

}
