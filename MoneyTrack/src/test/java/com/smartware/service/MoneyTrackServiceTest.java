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
		Expense testExpense = new Expense();
		testExpense.setDate(new GregorianCalendar(2017, Calendar.MARCH, 22, 11, 58).getTime());
		testExpense.setAmount(10f);
		testExpense.setCurrency(Currency.PEN);
		testExpense.setPaymenType(PaymentType.CASH);
		testExpense.setDetail("Altoque Menu Delivery");
		testExpense.setCategory(ExpenseCategory.LUNCH);

		long id = moneyTrackService.recordExpense(testExpense);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);
		assertTrue(moneyMovement.getType().equals(TransactionType.EXPENSE));

		Expense savedExpense = expenseDAO.getExpense(id);
		assertTrue(sameExpensesData(testExpense, savedExpense));
	}

	public void testRecordExpenseDebitPayment() {
		Expense testExpense = new Expense();
		testExpense.setDate(new GregorianCalendar(2017, Calendar.MAY, 6, 14, 36).getTime());
		testExpense.setAmount(23f);
		testExpense.setCurrency(Currency.PEN);
		testExpense.setPaymenType(PaymentType.DEBIT);
		testExpense.setDetail("MASS Car Washer");
		testExpense.setCategory(ExpenseCategory.IMPREZA_CLEAN);

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
		Expense testExpense = new Expense();
		testExpense.setDate(new GregorianCalendar(2017, Calendar.MAY, 1, 10, 46).getTime());
		testExpense.setAmount(48.90f);
		testExpense.setCurrency(Currency.PEN);
		testExpense.setPaymenType(PaymentType.CREDIT);
		testExpense.setDetail("Limbus Restobar");
		testExpense.setCategory(ExpenseCategory.FUN_TASTE);

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
		CreditCardMovement testCreditCardMovement = new CreditCardMovement();
		testCreditCardMovement.setDate(new GregorianCalendar(2017, Calendar.MARCH, 01, 14, 34).getTime());
		testCreditCardMovement.setAmount(4790.45f);
		testCreditCardMovement.setCurrency(Currency.PEN);
		testCreditCardMovement.setOperation(CreditCardOperation.PAYMENT);
		testCreditCardMovement.setRemarks("TOTAL FACTURADO 23908");

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
		CreditCardMovement testCreditCardMovement = new CreditCardMovement();
		testCreditCardMovement.setDate(new GregorianCalendar(2017, Calendar.MARCH, 01, 14, 34).getTime());
		testCreditCardMovement.setAmount(4790.45f);
		testCreditCardMovement.setCurrency(Currency.PEN);
		testCreditCardMovement.setOperation(CreditCardOperation.PAYMENT);
		testCreditCardMovement.setRemarks("TOTAL FACTURADO 23908");

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
		BankMovement testBankMovement = new BankMovement();
		testBankMovement.setDate(new GregorianCalendar(2017, Calendar.JANUARY, 15, 23, 36).getTime());
		testBankMovement.setAmount(80f);
		testBankMovement.setCurrency(Currency.PEN);
		testBankMovement.setOperation(BankOperation.DEBIT);
		testBankMovement.setRemarks("TIPICAL WITHDRAW");

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
		BankMovement testBankMovement = new BankMovement();
		testBankMovement.setDate(new GregorianCalendar(2017, Calendar.JANUARY, 30, 14, 15).getTime());
		testBankMovement.setAmount(5545f);
		testBankMovement.setCurrency(Currency.PEN);
		testBankMovement.setOperation(BankOperation.TRANSFER_IN);
		testBankMovement.setRemarks("SALARY JAN2017");

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
