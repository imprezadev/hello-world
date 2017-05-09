package com.smartware.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import junit.framework.TestCase;

import com.smartware.domain.Expense;
import com.smartware.domain.Transaction;
import com.smartware.domain.catalog.Currency;
import com.smartware.domain.catalog.ExpenseCategory;
import com.smartware.domain.catalog.PaymentType;
import com.smartware.domain.catalog.TransactionType;

public class ExpenseServiceTest extends TestCase {

	private final Logger logger = Logger.getLogger(ExpenseServiceTest.class.getName());

	private final Date            testDate            = new GregorianCalendar(2017, Calendar.MARCH, 22, 11, 58).getTime();
	private final Float           testAmount          = 10f;
	private final Currency        testCurrency        = Currency.PEN;
	private final PaymentType     testPaymentType     = PaymentType.CREDIT;
	private final String          testDetail          = "Altoque Menu Delivery";
	private final ExpenseCategory testExpenseCategory = ExpenseCategory.LUNCH;

	private Expense getTestExpense() {
		Expense expense = new Expense();
		expense.setDate(testDate);
		expense.setAmount(testAmount);
 		expense.setCurrency(testCurrency);
 		expense.setPaymenType(testPaymentType);
 		expense.setDetail(testDetail);
 		expense.setCategory(testExpenseCategory);

 		return expense;
	}

	private boolean compareToTestExpense(Expense expense) {
		return      (expense.getDate().compareTo(testDate) == 0) 
				&&	(expense.getAmount().compareTo(testAmount) == 0) 
				&&	(expense.getCurrency().compareTo(testCurrency) == 0)
				&&	(expense.getCategory().compareTo(testExpenseCategory) == 0);
	}

	public void testGetExpenses() {
		ExpenseService expenseService = new ExpenseService();
		boolean notEmpty = !expenseService.getExpenses().isEmpty();
		assertTrue(notEmpty);
	}

	public void testInsertExpense() {
		ExpenseService expenseService = new ExpenseService();
		TransactionService transactionService = new TransactionService();

		Expense newExpense= getTestExpense();
		long id = expenseService.insertExpense(newExpense);

		Transaction insertedTransaction = transactionService.getTransaction(id);
		logger.info(insertedTransaction.toString());

		Expense insertedExpense = expenseService.getExpense(id);
		logger.info(insertedExpense.toString());

		boolean testResult = compareToTestExpense(insertedExpense) && insertedTransaction.getType().equals(TransactionType.EXPENSE);
		assertTrue(testResult);
	}

}
