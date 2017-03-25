package com.smartware.app;

import java.util.Date;
import java.util.logging.Logger;

import com.smartware.domain.Transaction;
import com.smartware.domain.catalog.Currency;
import com.smartware.service.TransactionService;

public class App {
	final static Logger logger = Logger.getLogger(App.class.getName());

	public static void main(String[] args) {
		TransactionService transactionService = new TransactionService();

		Transaction transaction = new Transaction();
		transaction.setDate(new Date());
		transaction.setAmount(23.70f);
		transaction.setCurrency(Currency.PEN);
		transaction.setConcept("Almuerzo Liverpool");

		logger.info(transaction.toString());

		long id = transactionService.insertTransaction(transaction);
		logger.info("transaction generated id:" + id);
	}

}