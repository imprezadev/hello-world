package com.smartware.app;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import com.smartware.domain.Expense;
import com.smartware.domain.catalog.Currency;
import com.smartware.domain.catalog.ExpenseCategory;
import com.smartware.domain.catalog.PaymentType;
import com.smartware.service.ExpenseService;

public class App {
	final static Logger logger = Logger.getLogger(App.class.getName());

	public static void main(String[] args) {
		ExpenseService expenseService = new ExpenseService();

		Expense expense = new Expense();
		expense.setDate(new GregorianCalendar(2017, Calendar.MARCH, 22, 11, 58).getTime());
		expense.setAmount(10f);
		expense.setCurrency(Currency.PEN);
		expense.setPaymenType(PaymentType.CASH);
		expense.setConcept("Altoque Menu Delivery");
		expense.setCategory(ExpenseCategory.LUNCH);

		expenseService.insertExpense(expense);

		logger.info(expense.toString());
	}

}