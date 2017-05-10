package com.smartware.service;

import java.util.List;

import com.smartware.domain.BankMovement;
import com.smartware.domain.CreditCardMovement;
import com.smartware.domain.Expense;
import com.smartware.domain.MoneyMovement;
import com.smartware.domain.catalog.BankOperation;
import com.smartware.domain.catalog.CreditCardOperation;
import com.smartware.domain.catalog.PaymentType;
import com.smartware.domain.catalog.TransactionType;
import com.smartware.persistence.BankMovementDAO;
import com.smartware.persistence.CreditCardMovementDAO;
import com.smartware.persistence.ExpenseDAO;
import com.smartware.persistence.MoneyMovementDAO;

public class ExpenseService {

	private ExpenseDAO expenseDAO = new ExpenseDAO();
	private MoneyMovementDAO moneyMovementDAO = new MoneyMovementDAO();
	private BankMovementDAO bankMovementDAO = new BankMovementDAO();
	private CreditCardMovementDAO creditCardMovementDAO = new CreditCardMovementDAO();

	public Expense getExpense(long id) {
		return expenseDAO.getExpense(id);
	}

	public List<Expense> getExpenses() {
		return expenseDAO.getExpenses();
	}

	public long insertExpense(Expense expense) {
		long id = -1;

		MoneyMovement moneyMovement = new MoneyMovement();
		moneyMovement.setType(TransactionType.EXPENSE);
		moneyMovement.setDate(expense.getDate());
		moneyMovement.setAmount(expense.getAmount());
		moneyMovement.setCurrency(expense.getCurrency());

		id = moneyMovementDAO.insertMoneyMovement(moneyMovement);

		if (expense.getPaymenType().equals(PaymentType.DEBIT)) {
			BankMovement bankMovement = new BankMovement();
			bankMovement.setId(id);
			bankMovement.setOperation(BankOperation.DEBIT);
			bankMovement.setRemarks("FROM EXPENSE");

			bankMovementDAO.insertBankMovement(bankMovement);
		}
		else
		if (expense.getPaymenType().equals(PaymentType.CREDIT)) {
			CreditCardMovement creditCardMovement = new CreditCardMovement();
			creditCardMovement.setId(id);
			creditCardMovement.setOperation(CreditCardOperation.CREDIT);
			creditCardMovement.setRemarks("FROM EXPENSE");

			creditCardMovementDAO.insertCreditCardMovement(creditCardMovement);
		}

		expense.setId(id);
		expenseDAO.insertExpense(expense);

		return id;
	}

}
