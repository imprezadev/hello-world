package com.smartware.service;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

import junit.framework.TestCase;

public class MoneyTrackServiceTest extends TestCase {

	private MoneyTrackService moneyTrackService = new MoneyTrackService();
	private MoneyMovementDAO moneyMovementDAO = new MoneyMovementDAO(); 
	private BankMovementDAO bankMovementDAO = new BankMovementDAO();
	private CreditCardMovementDAO creditCardMovementDAO = new CreditCardMovementDAO();
	private ExpenseDAO expenseDAO = new ExpenseDAO();

	private Expense getTestExpenseCashPayment() {
		Expense expense = new Expense();
		expense.setDate(new GregorianCalendar(2017, Calendar.MARCH, 22, 11, 58).getTime());
		expense.setAmount(10f);
 		expense.setCurrency(Currency.PEN);
 		expense.setPaymenType(PaymentType.CASH);
 		expense.setDetail("Altoque Menu Delivery");
 		expense.setCategory(ExpenseCategory.LUNCH);

 		return expense;
	}

	private Expense getTestExpenseDebitPayment() {
		Expense expense = new Expense();
		expense.setDate(new GregorianCalendar(2017, Calendar.MAY, 6, 14, 36).getTime());
		expense.setAmount(23f);
 		expense.setCurrency(Currency.PEN);
 		expense.setPaymenType(PaymentType.DEBIT);
 		expense.setDetail("MASS Car Washer");
 		expense.setCategory(ExpenseCategory.IMPREZA_CLEAN);

 		return expense;
	}

	private Expense getTestExpenseCreditPayment() {
		Expense expense = new Expense();
		expense.setDate(new GregorianCalendar(2017, Calendar.MAY, 1, 10, 46).getTime());
		expense.setAmount(48.90f);
 		expense.setCurrency(Currency.PEN);
 		expense.setPaymenType(PaymentType.CREDIT);
 		expense.setDetail("Limbus Restobar");
 		expense.setCategory(ExpenseCategory.FUN_TASTE);

 		return expense;
	}

	private CreditCardMovement getTestCreditCardMovementPayment() {
		CreditCardMovement creditCardMovement = new CreditCardMovement();
		creditCardMovement.setDate(new GregorianCalendar(2017, Calendar.MARCH, 01, 14, 34).getTime());
		creditCardMovement.setAmount(4790.45f);
		creditCardMovement.setCurrency(Currency.PEN);
		creditCardMovement.setOperation(CreditCardOperation.PAYMENT);
		creditCardMovement.setRemarks("TOTAL FACTURADO 23908");

 		return creditCardMovement;
	}

	private BankMovement getTestBankMovementWithdraw() {
		BankMovement bankMovement = new BankMovement();
		bankMovement.setDate(new GregorianCalendar(2017, Calendar.JANUARY, 15, 23, 36).getTime());
		bankMovement.setAmount(80f);
		bankMovement.setCurrency(Currency.PEN);
		bankMovement.setOperation(BankOperation.DEBIT);
		bankMovement.setRemarks("TIPICAL WITHDRAW");
		return bankMovement;
	}

	private BankMovement getTestBankMovementGotSalary() {
		BankMovement bankMovement = new BankMovement();
		bankMovement.setDate(new GregorianCalendar(2017, Calendar.JANUARY, 30, 14, 15).getTime());
		bankMovement.setAmount(5545f);
		bankMovement.setCurrency(Currency.PEN);
		bankMovement.setOperation(BankOperation.TRANSFER_IN);
		bankMovement.setRemarks("SALARY JAN2017");
		return bankMovement;
	}

	private boolean sameExpensesData(Expense testExpense, Expense savedExpense) {
		return      (testExpense.getDate().compareTo(savedExpense.getDate()) == 0)
				&&	(testExpense.getAmount().compareTo(savedExpense.getAmount()) == 0)
				&&	(testExpense.getCurrency().compareTo(savedExpense.getCurrency()) == 0)
				&&	(testExpense.getCategory().compareTo(savedExpense.getCategory()) == 0);
	}

	private boolean sameCreditCardMovementData(CreditCardMovement testCreditCardMovement, CreditCardMovement savedCreditCardMovement) {
		return      (testCreditCardMovement.getDate().compareTo(savedCreditCardMovement.getDate()) == 0)
				&&	(testCreditCardMovement.getAmount().compareTo(savedCreditCardMovement.getAmount()) == 0)
				&&	(testCreditCardMovement.getCurrency().compareTo(savedCreditCardMovement.getCurrency()) == 0)
				&&	(testCreditCardMovement.getOperation().compareTo(savedCreditCardMovement.getOperation()) == 0)
		        &&	(testCreditCardMovement.getRemarks().compareTo(savedCreditCardMovement.getRemarks()) == 0);
	}

	private boolean sameBankMovementData(BankMovement testBankMovement, BankMovement savedBankMovement) {
		return      (testBankMovement.getDate().compareTo(savedBankMovement.getDate()) == 0)
				&&	(testBankMovement.getAmount().compareTo(savedBankMovement.getAmount()) == 0)
				&&	(testBankMovement.getCurrency().compareTo(savedBankMovement.getCurrency()) == 0)
				&&	(testBankMovement.getOperation().compareTo(savedBankMovement.getOperation()) == 0)
		        &&	(testBankMovement.getRemarks().compareTo(savedBankMovement.getRemarks()) == 0);
	}

	public void testRecordExpense() {
		Expense testExpense = getTestExpenseCashPayment();
		long id = moneyTrackService.recordExpense(testExpense);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);
		assertTrue(moneyMovement.getType().equals(TransactionType.EXPENSE));

		Expense savedExpense = expenseDAO.getExpense(id);
		assertTrue(sameExpensesData(testExpense, savedExpense));
	}

	public void testRecordExpenseDebitPayment() {
		Expense testExpense = getTestExpenseDebitPayment();
		long id = moneyTrackService.recordExpense(testExpense);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);
		assertTrue(moneyMovement.getType().equals(TransactionType.EXPENSE));

		BankMovement bankMovement = bankMovementDAO.getBankMovement(id);
		assertNotNull(bankMovement);
		assertTrue(bankMovement.getOperation().equals(BankOperation.DEBIT));

		Expense savedExpense = expenseDAO.getExpense(id);
		assertTrue(sameExpensesData(testExpense, savedExpense));
	}

	public void testRecordExpenseCreditPayment() {
		Expense testExpense = getTestExpenseCreditPayment();
		long id = moneyTrackService.recordExpense(testExpense);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);
		assertTrue(moneyMovement.getType().equals(TransactionType.EXPENSE));

		CreditCardMovement creditCardMovement = creditCardMovementDAO.getCreditCardMovement(id);
		assertNotNull(creditCardMovement);
		assertTrue(creditCardMovement.getOperation().equals(CreditCardOperation.CREDIT));

		Expense savedExpense = expenseDAO.getExpense(id);
		assertTrue(sameExpensesData(testExpense, savedExpense));
	}

	public void testRecordCreditCardPaymentCash() {
		CreditCardMovement testCreditCardMovement = getTestCreditCardMovementPayment();
		long id = moneyTrackService.recordCreditCardPayment(testCreditCardMovement, PaymentType.CASH);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);
		assertTrue(moneyMovement.getType().equals(TransactionType.CREDIT_CARD_MOVEMENT));

		CreditCardMovement savedCreditCardMovement = creditCardMovementDAO.getCreditCardMovement(id);
		assertNotNull(savedCreditCardMovement);
		assertTrue(sameCreditCardMovementData(testCreditCardMovement, savedCreditCardMovement));
	}

	public void testRecordCreditCardPaymentDebit() {
		CreditCardMovement testCreditCardMovement = getTestCreditCardMovementPayment();
		long id = moneyTrackService.recordCreditCardPayment(testCreditCardMovement, PaymentType.DEBIT);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);
		assertTrue(moneyMovement.getType().equals(TransactionType.CREDIT_CARD_MOVEMENT));

		BankMovement bankMovement = bankMovementDAO.getBankMovement(id);
		assertNotNull(bankMovement);
		assertTrue(bankMovement.getOperation().equals(BankOperation.DEBIT));

		CreditCardMovement savedCreditCardMovement = creditCardMovementDAO.getCreditCardMovement(id);
		assertNotNull(savedCreditCardMovement);
		assertTrue(sameCreditCardMovementData(testCreditCardMovement, savedCreditCardMovement));
	}

	public void testRecordWithdrawal() {
		BankMovement testBankMovement = getTestBankMovementWithdraw();
		long id = moneyTrackService.recordWithdrawal(testBankMovement);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);
		assertTrue(moneyMovement.getType().equals(TransactionType.BANK_MOVEMENT));

		BankMovement savedBankMovement = bankMovementDAO.getBankMovement(id);
		assertNotNull(savedBankMovement);
		assertTrue(sameBankMovementData(testBankMovement, savedBankMovement));
	}

	public void testRecordGotSalary() {
		BankMovement testBankMovement = getTestBankMovementGotSalary();
		long id = moneyTrackService.recordGotSalary(testBankMovement);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);
		assertTrue(moneyMovement.getType().equals(TransactionType.BANK_MOVEMENT));

		BankMovement savedBankMovement = bankMovementDAO.getBankMovement(id);
		assertNotNull(savedBankMovement);
		assertTrue(sameBankMovementData(testBankMovement, savedBankMovement));
	}

}
