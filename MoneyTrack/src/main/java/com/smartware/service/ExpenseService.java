package com.smartware.service;

import java.util.List;

import com.smartware.domain.Expense;
import com.smartware.domain.Transaction;
import com.smartware.domain.catalog.TransactionType;
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
	
	public long insertExpense(Expense expense) {
		long id = -1;
		
		Transaction transaction = new Transaction();
		transaction.setType(TransactionType.EXPENSE);
		transaction.setDate(expense.getDate());
		transaction.setAmount(expense.getAmount());
		transaction.setCurrency(expense.getCurrency());

		id = transactionDAO.insertTransaction(transaction);
		
		expense.setId(id);
		expenseDAO.insertExpense(expense);
		
		return id;
	}

}
