package com.smartware.app;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import com.smartware.domain.Expense;
import com.smartware.domain.Transaction;
import com.smartware.domain.catalog.Currency;
import com.smartware.domain.catalog.ExpenseCategory;
import com.smartware.domain.catalog.PaymentType;
import com.smartware.service.ExpenseService;
import com.smartware.service.TransactionService;

public class App {
	final static Logger logger = Logger.getLogger(App.class.getName());

	public static void main(String[] args) {
		long id = -1;
		// transactions
		for (Transaction transaction: getTransactionsFromDB()) {
			logger.info("TRANSACTIONS:");
			logger.info(transaction.toString());
		}

		Transaction transaction = buildTransaction();
		logger.info("BUILDED TRANSACTION:");
		logger.info(transaction.toString());

		id = insertTransaction(transaction);

 		Transaction transactionFromDB = getTransactionFromDB(id);
		logger.info("BUILDED TRANSACTION FROM DB:");
		logger.info(transactionFromDB.toString());

		//expenses
		for (Expense expense: getExpensesFromDB()) {
			logger.info("EXPENSES:");
			logger.info(expense.toString());
		}

 		Expense expense = buildExpense();
		logger.info("BUILDED EXPENSE:");
		logger.info(expense.toString());

		id = insertExpense(expense);

 		Expense expenseFromDB = getExpenseFromDB(id);
		logger.info("BUILDED EXPENSE FROM DB:");
		logger.info(expenseFromDB.toString());
	}

	private static List<Transaction> getTransactionsFromDB() {
		TransactionService transactionService = new TransactionService();
		return transactionService.getTransactions();
	}

	private static Transaction buildTransaction() {
		Transaction transaction = new Transaction();
		transaction.setDate(new GregorianCalendar(2017, Calendar.JANUARY, 22, 15, 25).getTime());
		transaction.setAmount(80f);
		transaction.setCurrency(Currency.PEN);
 		
 		return transaction;
	}

	private static long insertTransaction(Transaction transaction) {
		TransactionService transactionService = new TransactionService();
		return transactionService.insertTransaction(transaction);
	}

	private static Transaction getTransactionFromDB(long id) {
		TransactionService transactionService = new TransactionService();
		return transactionService.getTransaction(id);
	}

	private static List<Expense> getExpensesFromDB() {
		ExpenseService expenseService = new ExpenseService();
		return expenseService.getExpenses();
	}

	private static Expense buildExpense() {
		Expense expense = new Expense();
		expense.setDate(new GregorianCalendar(2017, Calendar.MARCH, 22, 11, 58).getTime());
		expense.setAmount(10f);
 		expense.setCurrency(Currency.PEN);
 		expense.setPaymenType(PaymentType.CASH);
 		expense.setDetail("Altoque Menu Delivery");
 		expense.setCategory(ExpenseCategory.LUNCH);
 		
 		return expense;
	}

	private static long insertExpense(Expense expense) {
		ExpenseService expenseService = new ExpenseService();
		return expenseService.insertExpense(expense);

	}

	private static Expense getExpenseFromDB(long id) {
		ExpenseService expenseService = new ExpenseService();
		return expenseService.getExpense(id);
	}

}