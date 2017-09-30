package com.smartware.service;

import java.util.List;

import com.smartware.domain.BankMovement;
import com.smartware.domain.CreditCardMovement;
import com.smartware.domain.Expense;
import com.smartware.domain.MoneyMovement;
import com.smartware.domain.catalog.BankOperation;
import com.smartware.domain.catalog.CreditCardOperation;
import com.smartware.domain.catalog.Currency;
import com.smartware.domain.catalog.ExpenseCategory;
import com.smartware.domain.catalog.PaymentType;
import com.smartware.domain.catalog.TransactionType;
import com.smartware.persistence.BankMovementDAO;
import com.smartware.persistence.CreditCardMovementDAO;
import com.smartware.persistence.ExpenseDAO;
import com.smartware.persistence.MoneyMovementDAO;

public class MoneyTrackService {

	private MoneyMovementDAO moneyMovementDAO = new MoneyMovementDAO();
	private BankMovementDAO bankMovementDAO = new BankMovementDAO();
	private CreditCardMovementDAO creditCardMovementDAO = new CreditCardMovementDAO();
	private ExpenseDAO expenseDAO = new ExpenseDAO();

	public Currency[] getCurrencies() {
		return Currency.values();
	}

	public PaymentType[] getPaymentTypes() {
		return PaymentType.values();
	}

	public ExpenseCategory[] getExpenseCategories() {
		return ExpenseCategory.values();
	}

	public long recordExpense(Expense expense) {
		long id = moneyMovementDAO.insertMoneyMovement(TransactionType.EXPENSE, expense.getDate(), expense.getAmount(), expense.getCurrency());

		if (expense.getPaymenType().equals(PaymentType.DEBIT)) {
			bankMovementDAO.insertBankMovement(id, BankOperation.DEBIT, TransactionType.EXPENSE.name());
		}
		else
		if (expense.getPaymenType().equals(PaymentType.CREDIT)) {
			creditCardMovementDAO.insertCreditCardMovement(id, CreditCardOperation.CREDIT, TransactionType.EXPENSE.name());
		}

		expense.setId(id);
		expenseDAO.insertExpense(expense);

		return id;
	}

	public long recordCreditCardPayment(CreditCardMovement creditCardMovement, PaymentType paymentType) {
		long id = moneyMovementDAO.insertMoneyMovement(TransactionType.CREDIT_CARD_MOVEMENT, creditCardMovement.getDate(), creditCardMovement.getAmount(), creditCardMovement.getCurrency());

		if (paymentType.equals(PaymentType.DEBIT)) {
			bankMovementDAO.insertBankMovement(id, BankOperation.DEBIT, TransactionType.CREDIT_CARD_MOVEMENT.name());
		}

		creditCardMovementDAO.insertCreditCardMovement(id, CreditCardOperation.PAYMENT, creditCardMovement.getRemarks());

		return id;
	}

	public long recordWithdrawal(BankMovement bankMovement) {
		long id = moneyMovementDAO.insertMoneyMovement(TransactionType.BANK_MOVEMENT, bankMovement.getDate(), bankMovement.getAmount(), bankMovement.getCurrency());

		bankMovement.setId(id);
		bankMovement.setOperation(BankOperation.WITHDRAWAL);
		bankMovementDAO.insertBankMovement(bankMovement);

		return id;
	}

	public long recordGotSalary(BankMovement bankMovement) {
		long id = moneyMovementDAO.insertMoneyMovement(TransactionType.BANK_MOVEMENT, bankMovement.getDate(), bankMovement.getAmount(), bankMovement.getCurrency());

		bankMovement.setId(id);
		bankMovement.setOperation(BankOperation.TRANSFER_IN);
		bankMovementDAO.insertBankMovement(bankMovement);

		return id;
	}

	public List<MoneyMovement> getMoneyMovements() {
		return moneyMovementDAO.getMoneyMovements();
	}

	public Expense getExpense(long id) {
		return expenseDAO.getExpense(id);
	}

}
