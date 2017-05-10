package com.smartware.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.smartware.domain.Expense;
import com.smartware.domain.catalog.Currency;
import com.smartware.domain.catalog.ExpenseCategory;
import com.smartware.domain.catalog.PaymentType;

import junit.framework.TestCase;

public class MoneyTrackServiceTest extends TestCase {
	
	private final Date            testDate            = new GregorianCalendar(2017, Calendar.MARCH, 22, 11, 58).getTime();
	private final Float           testAmount          = 10f;
	private final Currency        testCurrency        = Currency.PEN;
	private final PaymentType     testPaymentType     = PaymentType.CASH;
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

	private boolean equalsToTestExpense(Expense expense) {
		return      (expense.getDate().compareTo(testDate) == 0) 
				&&	(expense.getAmount().compareTo(testAmount) == 0) 
				&&	(expense.getCurrency().compareTo(testCurrency) == 0)
				&&	(expense.getCategory().compareTo(testExpenseCategory) == 0);
	}

	public void testPerformExpense() {
		MoneyTrackService moneyTrackService = new MoneyTrackService();
		Expense testExpense = getTestExpense();
		long id = moneyTrackService.performExpense(testExpense);
		
		assertTrue(id > 0);
		
		Expense savedExpense = moneyTrackService.getExpense(id);
		assertTrue(equalsToTestExpense(savedExpense));
		
	}

}
