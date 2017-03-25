package com.smartware.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import com.smartware.domain.Currency;
import com.smartware.domain.Expense;
import com.smartware.domain.Transaction;
import com.smartware.service.ExpenseService;
import com.smartware.service.TransactionService;

public class App {
	
	final static Logger logger = Logger.getLogger(App.class.getName());

	public static void main(String[] args) {
		TransactionService transactionService = new TransactionService();
		ExpenseService expenseService = new ExpenseService();

		logger.info("Transaction: " + transactionService.getTransaction(3L));

		// just to show the date in a desired format
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = transactionService.getTransaction(5L).getDate();
		logger.info("Transaction date: " + dateFormat.format(date));

		String transactionList = "";
		for (Transaction trans: transactionService.getTransactions()) {
			transactionList += "\n\t" + trans;
		}
		logger.info("Transaction:" + transactionList);
		
		for (Currency cur: Currency.values()) {
			logger.info(cur.name());
		}

		Transaction transaction = new Transaction();
		transaction.setDate(new Date());
		transaction.setAmount(23.70f);
		transaction.setCurrency(Currency.PEN);
		transaction.setConcept("Almuerzo Liverpool");

		logger.info(transaction.toString());

		transactionService.insertTransaction(transaction);

		String expenseList = "";
		for (Expense expense: expenseService.getExpenses()) {
			expenseList += "\n\t" + expense;
		}
		logger.info("Expenses:" + expenseList);
	}

}