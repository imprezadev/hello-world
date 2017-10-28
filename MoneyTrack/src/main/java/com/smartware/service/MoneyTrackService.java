package com.smartware.service;

import java.util.List;

import com.smartware.domain.BankMovement;
import com.smartware.domain.CreditCardPayment;
import com.smartware.domain.Expense;
import com.smartware.domain.GotSalary;
import com.smartware.domain.MoneyMovement;
import com.smartware.domain.Withdrawal;
import com.smartware.domain.catalog.BankOperation;
import com.smartware.domain.catalog.CreditCardOperation;
import com.smartware.domain.catalog.CreditCardPaymentType;
import com.smartware.domain.catalog.Currency;
import com.smartware.domain.catalog.ExpenseCategory;
import com.smartware.domain.catalog.ExpensePaymentType;
import com.smartware.domain.catalog.MoneyMovementOperation;
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

	public ExpensePaymentType[] getExpensePaymentTypes() {
		return ExpensePaymentType.values();
	}

	public ExpenseCategory[] getExpenseCategories() {
		return ExpenseCategory.values();
	}

	public CreditCardPaymentType[] getCreditCardPaymentTypes() {
		return CreditCardPaymentType.values();
	}

	public long recordExpense(Expense expense) {
		long id = moneyMovementDAO.insertMoneyMovement(expense.getDate(), expense.getAmount(), expense.getCurrency());

		if (expense.getPaymentType().equals(ExpensePaymentType.DEBIT)) {
			bankMovementDAO.insertBankMovement(id, BankOperation.DEBIT, MoneyMovementOperation.EXPENSE.name());
		}
		else
		if (expense.getPaymentType().equals(ExpensePaymentType.CREDIT_CARD)) {
			creditCardMovementDAO.insertCreditCardMovement(id, CreditCardOperation.CREDIT, MoneyMovementOperation.EXPENSE.name());
		}
		else
		if (expense.getPaymentType().equals(ExpensePaymentType.BANK_TRANSFER)) {
			bankMovementDAO.insertBankMovement(id, BankOperation.TRANSFER_OUT, MoneyMovementOperation.EXPENSE.name());
		}

		expense.setId(id);
		expenseDAO.insertExpense(expense);

		return id;
	}

	public long recordCreditCardPayment(CreditCardPayment creditCardPayment) {
		long id = moneyMovementDAO.insertMoneyMovement(creditCardPayment.getDate(), creditCardPayment.getAmount(), creditCardPayment.getCurrency());

		if (creditCardPayment.getPaymentType().equals(CreditCardPaymentType.BANK_TRANSFER)) {
			bankMovementDAO.insertBankMovement(id, BankOperation.DEBIT, MoneyMovementOperation.CREDIT_CARD_PAYMENT.name());
		}

		creditCardMovementDAO.insertCreditCardMovement(id, CreditCardOperation.PAYMENT, creditCardPayment.getRemarks());

		return id;
	}

	public long recordWithdrawal(Withdrawal withdrawal) {
		long id = moneyMovementDAO.insertMoneyMovement(withdrawal.getDate(), withdrawal.getAmount(), withdrawal.getCurrency());

		BankMovement bankMovement = new BankMovement();
		bankMovement.setId(id);
		bankMovement.setBankOperation(BankOperation.WITHDRAWAL);
		bankMovement.setRemarks(withdrawal.getRemarks());
		bankMovementDAO.insertBankMovement(bankMovement);

		return id;
	}

	public long recordGotSalary(GotSalary gotSalary) {
		long id = moneyMovementDAO.insertMoneyMovement(gotSalary.getDate(), gotSalary.getAmount(), Currency.PEN);

		BankMovement bankMovement = new BankMovement();
		bankMovement.setId(id);
		bankMovement.setBankOperation(BankOperation.TRANSFER_IN);
		bankMovement.setRemarks("SALARY " + gotSalary.getRemarks());
		bankMovementDAO.insertBankMovement(bankMovement);

		return id;
	}

	public List<MoneyMovement> getMoneyMovements() {
		return moneyMovementDAO.getMoneyMovements();
	}

	public Expense getExpense(long id) {
		return expenseDAO.getExpense(id);
	}

	public CreditCardPayment getCreditCardPayment(long id) {
		return creditCardMovementDAO.getCreditCardPayment(id);
	}

	public GotSalary getGotSalary(long id) {
		return bankMovementDAO.getGotSalary(id);
	}

	public Withdrawal getWithdrawal(long id) {
		return bankMovementDAO.getWithdrawal(id);
	}

}
