package com.smartware.service;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

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

public class MoneyTrackServiceTest {
	private static MoneyTrackService moneyTrackService;
	private static MoneyMovementDAO moneyMovementDAO; 
	private static BankMovementDAO bankMovementDAO;
	private static CreditCardMovementDAO creditCardMovementDAO;
	private static ExpenseDAO expenseDAO;

	@BeforeClass
	public static void initDAOsServices() {
		moneyTrackService = new MoneyTrackService();
		moneyMovementDAO = new MoneyMovementDAO(); 
		bankMovementDAO = new BankMovementDAO();
		creditCardMovementDAO = new CreditCardMovementDAO();
		expenseDAO = new ExpenseDAO();
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

	private Expense getTestRecordExpense_CashPayment() {
		Expense testExpense_CashPayment = new Expense();
		testExpense_CashPayment.setDate(new GregorianCalendar(2017, Calendar.MARCH, 22, 11, 58).getTime());
		testExpense_CashPayment.setAmount(10f);
		testExpense_CashPayment.setCurrency(Currency.PEN);
		testExpense_CashPayment.setPaymentType(PaymentType.CASH);
		testExpense_CashPayment.setDetail("Altoque Menu Delivery");
		testExpense_CashPayment.setCategory(ExpenseCategory.LUNCH);
		
		return testExpense_CashPayment;
	}

	private Expense getTestRecordExpense_DebitPayment() {
		Expense testExpense_DebitPayment = new Expense();
		testExpense_DebitPayment.setDate(new GregorianCalendar(2017, Calendar.MAY, 6, 14, 36).getTime());
		testExpense_DebitPayment.setAmount(23f);
		testExpense_DebitPayment.setCurrency(Currency.PEN);
		testExpense_DebitPayment.setPaymentType(PaymentType.DEBIT);
		testExpense_DebitPayment.setDetail("MASS Car Washer");
		testExpense_DebitPayment.setCategory(ExpenseCategory.IMPREZA_CLEAN);
		
		return testExpense_DebitPayment;
	}

	private Expense getTestRecordExpense_CreditPayment() {
		Expense testExpense_CreditPayment = new Expense();
		testExpense_CreditPayment.setDate(new GregorianCalendar(2017, Calendar.MAY, 1, 10, 46).getTime());
		testExpense_CreditPayment.setAmount(48.90f);
		testExpense_CreditPayment.setCurrency(Currency.PEN);
		testExpense_CreditPayment.setPaymentType(PaymentType.CREDIT);
		testExpense_CreditPayment.setDetail("Limbus Restobar");
		testExpense_CreditPayment.setCategory(ExpenseCategory.FUN_TASTE);

		return testExpense_CreditPayment;
	}

	private CreditCardMovement getTestRecordCreditCardPayment() {
		CreditCardMovement testCreditCardPayment = new CreditCardMovement();
		testCreditCardPayment.setDate(new GregorianCalendar(2017, Calendar.MARCH, 01, 14, 34).getTime());
		testCreditCardPayment.setAmount(4790.45f);
		testCreditCardPayment.setCurrency(Currency.PEN);
		testCreditCardPayment.setOperation(CreditCardOperation.PAYMENT);
		testCreditCardPayment.setRemarks("TOTAL FACTURADO 23908");

		return testCreditCardPayment;
	}

	private BankMovement getTestWithdrawal() {
		BankMovement testWithdrawal = new BankMovement();
		testWithdrawal.setDate(new GregorianCalendar(2017, Calendar.JANUARY, 15, 23, 36).getTime());
		testWithdrawal.setAmount(80f);
		testWithdrawal.setCurrency(Currency.PEN);
		testWithdrawal.setOperation(BankOperation.DEBIT);
		testWithdrawal.setRemarks("TIPICAL WITHDRAW");

		return testWithdrawal;
	}

	private BankMovement getTestGotSalary() {
		BankMovement testGotSalary = new BankMovement();
		testGotSalary.setDate(new GregorianCalendar(2017, Calendar.JANUARY, 30, 14, 15).getTime());
		testGotSalary.setAmount(5545f);
		testGotSalary.setCurrency(Currency.PEN);
		testGotSalary.setOperation(BankOperation.TRANSFER_IN);
		testGotSalary.setRemarks("SALARY JAN2017");

		return testGotSalary;
	}

	public void testRecordExpense_CashPayment() {
		Expense testExpense_CashPayment = getTestRecordExpense_CashPayment();
		long id = moneyTrackService.recordExpense(testExpense_CashPayment);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);
		assertTrue(moneyMovement.getType().equals(TransactionType.EXPENSE));

		Expense savedExpense = expenseDAO.getExpense(id);
		assertTrue(sameExpensesData(testExpense_CashPayment, savedExpense));
	}

	@Test
	public void testRecordExpense_DebitPayment() {
		Expense testExpense_DebitPayment = getTestRecordExpense_DebitPayment();
		long id = moneyTrackService.recordExpense(testExpense_DebitPayment);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);
		assertTrue(moneyMovement.getType().equals(TransactionType.EXPENSE));

		BankMovement bankMovement = bankMovementDAO.getBankMovement(id);
		assertNotNull(bankMovement);
		assertTrue(bankMovement.getOperation().equals(BankOperation.DEBIT));

		Expense savedExpense = expenseDAO.getExpense(id);
		assertTrue(sameExpensesData(testExpense_DebitPayment, savedExpense));
	}

	@Test
	public void testRecordExpense_CreditPayment() {
		Expense testExpense_CreditPayment = getTestRecordExpense_CreditPayment();
 		long id = moneyTrackService.recordExpense(testExpense_CreditPayment);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);
		assertTrue(moneyMovement.getType().equals(TransactionType.EXPENSE));

		CreditCardMovement creditCardMovement = creditCardMovementDAO.getCreditCardMovement(id);
		assertNotNull(creditCardMovement);
		assertTrue(creditCardMovement.getOperation().equals(CreditCardOperation.CREDIT));

		Expense savedExpense = expenseDAO.getExpense(id);
		assertTrue(sameExpensesData(testExpense_CreditPayment, savedExpense));
	}

	@Test
	public void testRecordCreditCardPayment_Cash() {
		CreditCardMovement testCreditCardPayment = getTestRecordCreditCardPayment();
		long id = moneyTrackService.recordCreditCardPayment(testCreditCardPayment, PaymentType.CASH);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);
		assertTrue(moneyMovement.getType().equals(TransactionType.CREDIT_CARD_MOVEMENT));

		CreditCardMovement savedCreditCardMovement = creditCardMovementDAO.getCreditCardMovement(id);
		assertNotNull(savedCreditCardMovement);
		assertTrue(sameCreditCardMovementData(testCreditCardPayment, savedCreditCardMovement));
	}

	@Test
	public void testRecordCreditCardPayment_Debit() {
		CreditCardMovement testCreditCardPayment = getTestRecordCreditCardPayment();
		long id = moneyTrackService.recordCreditCardPayment(testCreditCardPayment, PaymentType.DEBIT);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);
		assertTrue(moneyMovement.getType().equals(TransactionType.CREDIT_CARD_MOVEMENT));

		BankMovement bankMovement = bankMovementDAO.getBankMovement(id);
		assertNotNull(bankMovement);
		assertTrue(bankMovement.getOperation().equals(BankOperation.DEBIT));

		CreditCardMovement savedCreditCardMovement = creditCardMovementDAO.getCreditCardMovement(id);
		assertNotNull(savedCreditCardMovement);
		assertTrue(sameCreditCardMovementData(testCreditCardPayment, savedCreditCardMovement));
	}

	@Test
	public void testRecordWithdrawal() {
		BankMovement testWithdrawal = getTestWithdrawal();
		long id = moneyTrackService.recordWithdrawal(testWithdrawal);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);
		assertTrue(moneyMovement.getType().equals(TransactionType.BANK_MOVEMENT));

		BankMovement savedBankMovement = bankMovementDAO.getBankMovement(id);
		assertNotNull(savedBankMovement);
		assertTrue(savedBankMovement.getOperation().equals(BankOperation.WITHDRAWAL));
		assertTrue(sameBankMovementData(testWithdrawal, savedBankMovement));
	}

	@Test
	public void testRecordGotSalary() {
		BankMovement testGotSalary = getTestGotSalary();
		long id = moneyTrackService.recordGotSalary(testGotSalary);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);
		assertTrue(moneyMovement.getType().equals(TransactionType.BANK_MOVEMENT));

		BankMovement savedBankMovement = bankMovementDAO.getBankMovement(id);
		assertNotNull(savedBankMovement);
		assertTrue(savedBankMovement.getOperation().equals(BankOperation.TRANSFER_IN));
		assertTrue(sameBankMovementData(testGotSalary, savedBankMovement));
	}

	@Test
	public void testGetMoneyMovements() {
		moneyTrackService.recordExpense(getTestRecordExpense_CashPayment());
		moneyTrackService.recordExpense(getTestRecordExpense_DebitPayment());
		moneyTrackService.recordExpense(getTestRecordExpense_CreditPayment());
		moneyTrackService.recordCreditCardPayment(getTestRecordCreditCardPayment(), PaymentType.CASH);
		moneyTrackService.recordWithdrawal(getTestWithdrawal());
		moneyTrackService.recordGotSalary(getTestGotSalary());

		assertFalse(moneyTrackService.getMoneyMovements().isEmpty());
		assertTrue(moneyTrackService.getMoneyMovements().size() >= 6);
	}
}
