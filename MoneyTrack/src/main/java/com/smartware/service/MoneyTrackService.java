package com.smartware.service;

import com.smartware.domain.Expense;
import com.smartware.domain.MoneyMovement;
import com.smartware.domain.catalog.TransactionType;
import com.smartware.persistence.ExpenseDAO;
import com.smartware.persistence.MoneyMovementDAO;

public class MoneyTrackService {

	private ExpenseDAO expenseDAO = new ExpenseDAO();
	private MoneyMovementDAO moneyMovementDAO = new MoneyMovementDAO();

	public long performExpense(Expense expense) {
		long id = -1;

		MoneyMovement moneyMovement = new MoneyMovement();
		moneyMovement.setType(TransactionType.EXPENSE);
		moneyMovement.setDate(expense.getDate());
		moneyMovement.setAmount(expense.getAmount());
		moneyMovement.setCurrency(expense.getCurrency());

		id = moneyMovementDAO.insertMoneyMovement(moneyMovement);

		expense.setId(id);
		expenseDAO.insertExpense(expense);
		
		return id;
	}
	
	public Expense getExpense(long id) {
		return expenseDAO.getExpense(id);
	}

}
