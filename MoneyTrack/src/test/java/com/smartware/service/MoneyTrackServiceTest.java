package com.smartware.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.smartware.domain.BankMovement;
import com.smartware.domain.CreditCardMovement;
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

	private Expense getTestRecordExpense_CashPayment() {
		Expense testExpense_CashPayment = new Expense();
		testExpense_CashPayment.setDate(new GregorianCalendar(2017, Calendar.MARCH, 22, 11, 58).getTime());
		testExpense_CashPayment.setAmount(10f);
		testExpense_CashPayment.setCurrency(Currency.PEN);
		testExpense_CashPayment.setPaymentType(ExpensePaymentType.CASH);
		testExpense_CashPayment.setDetail("Altoque Menu Delivery");
		testExpense_CashPayment.setCategory(ExpenseCategory.LUNCH);
		
		return testExpense_CashPayment;
	}

	private Expense getTestRecordExpense_DebitPayment() {
		Expense testExpense_DebitPayment = new Expense();
		testExpense_DebitPayment.setDate(new GregorianCalendar(2017, Calendar.MAY, 6, 14, 36).getTime());
		testExpense_DebitPayment.setAmount(23f);
		testExpense_DebitPayment.setCurrency(Currency.PEN);
		testExpense_DebitPayment.setPaymentType(ExpensePaymentType.DEBIT);
		testExpense_DebitPayment.setDetail("MASS Car Washer");
		testExpense_DebitPayment.setCategory(ExpenseCategory.IMPREZA_CLEAN);
		
		return testExpense_DebitPayment;
	}

	private Expense getTestRecordExpense_CreditCardPayment() {
		Expense testExpense_CreditPayment = new Expense();
		testExpense_CreditPayment.setDate(new GregorianCalendar(2017, Calendar.MAY, 1, 10, 46).getTime());
		testExpense_CreditPayment.setAmount(48.90f);
		testExpense_CreditPayment.setCurrency(Currency.PEN);
		testExpense_CreditPayment.setPaymentType(ExpensePaymentType.CREDIT_CARD);
		testExpense_CreditPayment.setDetail("Limbus Restobar");
		testExpense_CreditPayment.setCategory(ExpenseCategory.FUN_TASTE);

		return testExpense_CreditPayment;
	}

	private Expense getTestRecordExpense_BankTransferPayment() {
		Expense testExpense_CreditPayment = new Expense();
		testExpense_CreditPayment.setDate(new GregorianCalendar(2017, Calendar.AUGUST, 23, 10, 46).getTime());
		testExpense_CreditPayment.setAmount(3006f);
		testExpense_CreditPayment.setCurrency(Currency.PEN);
		testExpense_CreditPayment.setPaymentType(ExpensePaymentType.BANK_TRANSFER);
		testExpense_CreditPayment.setDetail("PMC LATAM, ITIL PPO");
		testExpense_CreditPayment.setCategory(ExpenseCategory.CAREER_DEV);

		return testExpense_CreditPayment;
	}

	private CreditCardPayment getTestRecordCreditCardPayment_BankDeposit() {
		CreditCardPayment testCreditCardPayment = new CreditCardPayment();
		testCreditCardPayment.setDate(new GregorianCalendar(2017, Calendar.MARCH, 01, 14, 34).getTime());
		testCreditCardPayment.setAmount(4790.45f);
		testCreditCardPayment.setCurrency(Currency.PEN);
		testCreditCardPayment.setPaymentType(CreditCardPaymentType.DEPOSIT);
		testCreditCardPayment.setRemarks("TOTAL FACTURADO 23908");

		return testCreditCardPayment;
	}

	private CreditCardPayment getTestRecordCreditCardPayment_BankTransfer() {
		CreditCardPayment testCreditCardPayment = new CreditCardPayment();
		testCreditCardPayment.setDate(new GregorianCalendar(2017, Calendar.MARCH, 01, 14, 34).getTime());
		testCreditCardPayment.setAmount(4790.45f);
		testCreditCardPayment.setCurrency(Currency.PEN);
		testCreditCardPayment.setPaymentType(CreditCardPaymentType.BANK_TRANSFER);
		testCreditCardPayment.setRemarks("TOTAL FACTURADO 23908");

		return testCreditCardPayment;
	}

	private Withdrawal getTestWithdrawal() {
		Withdrawal testWithdrawal = new Withdrawal();
		testWithdrawal.setDate(new GregorianCalendar(2017, Calendar.JANUARY, 15, 23, 36).getTime());
		testWithdrawal.setAmount(80f);
		testWithdrawal.setCurrency(Currency.PEN);
		testWithdrawal.setRemarks("TIPICAL WITHDRAWAL");

		return testWithdrawal;
	}

	private GotSalary getTestGotSalary() {
		GotSalary testGotSalary = new GotSalary();
		testGotSalary.setDate(new GregorianCalendar(2017, Calendar.JANUARY, 30, 14, 15).getTime());
		testGotSalary.setAmount(5545f);
		testGotSalary.setRemarks("SALARY JAN2017");

		return testGotSalary;
	}

	@Test
	public void testRecordExpense_CashPayment() {
		Expense testExpense_CashPayment = getTestRecordExpense_CashPayment();
		long id = moneyTrackService.recordExpense(testExpense_CashPayment);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);

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

		BankMovement bankMovement = bankMovementDAO.getBankMovement(id);
		assertNotNull(bankMovement);
		assertTrue(bankMovement.getBankOperation().equals(BankOperation.DEBIT));

		Expense savedExpense = expenseDAO.getExpense(id);
		assertTrue(sameExpensesData(testExpense_DebitPayment, savedExpense));
	}

	@Test
	public void testRecordExpense_CreditCardPayment() {
		Expense testExpense_CreditPayment = getTestRecordExpense_CreditCardPayment();
 		long id = moneyTrackService.recordExpense(testExpense_CreditPayment);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);

		CreditCardMovement creditCardMovement = creditCardMovementDAO.getCreditCardMovement(id);
		assertNotNull(creditCardMovement);
		assertTrue(creditCardMovement.getCreditCardOperation().equals(CreditCardOperation.CREDIT));

		Expense savedExpense = expenseDAO.getExpense(id);
		assertTrue(sameExpensesData(testExpense_CreditPayment, savedExpense));
	}

	@Test
	public void testRecordExpense_BankTransferPayment() {
		Expense testExpense_BankTransferPayment = getTestRecordExpense_BankTransferPayment();
		long id = moneyTrackService.recordExpense(testExpense_BankTransferPayment);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);

		BankMovement bankMovement = bankMovementDAO.getBankMovement(id);
		assertNotNull(bankMovement);
		assertTrue(bankMovement.getBankOperation().equals(BankOperation.TRANSFER_OUT));

		Expense savedExpense = expenseDAO.getExpense(id);
		assertTrue(sameExpensesData(testExpense_BankTransferPayment, savedExpense));
	}

	@Test
	public void testRecordCreditCardPayment_BankDeposit() {
		CreditCardPayment testCreditCardPayment = getTestRecordCreditCardPayment_BankDeposit();
		long id = moneyTrackService.recordCreditCardPayment(testCreditCardPayment);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);

		CreditCardMovement savedCreditCardMovement = creditCardMovementDAO.getCreditCardMovement(id);
		assertNotNull(savedCreditCardMovement);
	}

	@Test
	public void testRecordCreditCardPayment_BankTransfer() {
		CreditCardPayment testCreditCardPayment = getTestRecordCreditCardPayment_BankTransfer();
		long id = moneyTrackService.recordCreditCardPayment(testCreditCardPayment);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);

		BankMovement bankMovement = bankMovementDAO.getBankMovement(id);
		assertNotNull(bankMovement);
		assertTrue(bankMovement.getBankOperation().equals(BankOperation.DEBIT));

		CreditCardMovement savedCreditCardMovement = creditCardMovementDAO.getCreditCardMovement(id);
		assertNotNull(savedCreditCardMovement);
	}

	@Test
	public void testRecordWithdrawal() {
		Withdrawal testWithdrawal = getTestWithdrawal();
		long id = moneyTrackService.recordWithdrawal(testWithdrawal);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);

		BankMovement savedBankMovement = bankMovementDAO.getBankMovement(id);
		assertNotNull(savedBankMovement);
		assertTrue(savedBankMovement.getBankOperation().equals(BankOperation.WITHDRAWAL));
	}

	@Test
	public void testRecordGotSalary() {
		GotSalary testGotSalary = getTestGotSalary();
		long id = moneyTrackService.recordGotSalary(testGotSalary);

		assertTrue(id > 0);

		MoneyMovement moneyMovement = moneyMovementDAO.getMoneyMovement(id);
		assertNotNull(moneyMovement);

		BankMovement savedBankMovement = bankMovementDAO.getBankMovement(id);
		assertNotNull(savedBankMovement);
		assertTrue(savedBankMovement.getBankOperation().equals(BankOperation.TRANSFER_IN));
	}

	@Test
	public void testGetMoneyMovements() {
		long idCashExpense = moneyTrackService.recordExpense(getTestRecordExpense_CashPayment());
		long idDebitExpense = moneyTrackService.recordExpense(getTestRecordExpense_DebitPayment());
		long idCreditExpense =  moneyTrackService.recordExpense(getTestRecordExpense_CreditCardPayment());
		long idCreditCardPayment = moneyTrackService.recordCreditCardPayment(getTestRecordCreditCardPayment_BankTransfer());
		long idWithDrawal = moneyTrackService.recordWithdrawal(getTestWithdrawal());
		long idGotSalary = moneyTrackService.recordGotSalary(getTestGotSalary());

		List<MoneyMovement> moneyMovements = moneyTrackService.getMoneyMovements();
		assertFalse(moneyMovements.isEmpty());
		assertTrue(moneyMovements.size() >= 6);

		for(MoneyMovement moneyMovement: moneyMovements) {
			if (moneyMovement.getId().equals(idCashExpense) || moneyMovement.getId().equals(idDebitExpense) || moneyMovement.getId().equals(idCreditExpense)) {
				assertTrue(moneyMovement.getOperation().equals(MoneyMovementOperation.EXPENSE));
			}
			else
			if (moneyMovement.getId().equals(idCreditCardPayment)) {
				assertTrue(moneyMovement.getOperation().equals(MoneyMovementOperation.CREDIT_CARD_PAYMENT));
			}
			else
			if (moneyMovement.getId().equals(idWithDrawal)) {
				assertTrue(moneyMovement.getOperation().equals(MoneyMovementOperation.WITHDRAWAL));
			}
			else
			if (moneyMovement.getId().equals(idGotSalary)) {
				assertTrue(moneyMovement.getOperation().equals(MoneyMovementOperation.GOT_SALARY));
			}
		}
	}
}
