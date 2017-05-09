package com.smartware.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import com.smartware.domain.Transaction;
import com.smartware.domain.catalog.Currency;
import com.smartware.domain.catalog.TransactionType;

import junit.framework.TestCase;

public class TransactionServiceTest extends TestCase {

	private static final Logger logger = Logger.getLogger(TransactionServiceTest.class.getName());

	private final Date            testDate            = new GregorianCalendar(2017, Calendar.JANUARY, 22, 15, 25).getTime();
	private final Float           testAmount          = 80f;
	private final Currency        testCurrency        = Currency.PEN;
	private final TransactionType testTransactionType = TransactionType.EXPENSE;  

	private Transaction getTestTransaction() {
		Transaction transaction = new Transaction();
		transaction.setDate(testDate);
		transaction.setAmount(testAmount);
		transaction.setCurrency(testCurrency);
		transaction.setType(testTransactionType);

 		return transaction;
	}

	private boolean compareToTestTransaction(Transaction transaction) {
		return      (transaction.getDate().compareTo(testDate) == 0)
				&&	(transaction.getAmount().compareTo(testAmount) == 0)
				&&	(transaction.getCurrency().compareTo(testCurrency) == 0)
				&& 	(transaction.getType().compareTo(testTransactionType) == 0);
	}

	public void testGetTransactions() {
		TransactionService transactionService = new TransactionService();
		boolean notEmpty = !transactionService.getTransactions().isEmpty();
		assertTrue(notEmpty);
	}

	public void testInsertTransaction() {
		TransactionService transactionService = new TransactionService();

		Transaction newTransaction = getTestTransaction();
		long id = transactionService.insertTransaction(newTransaction);

		Transaction insertedTransaction = transactionService.getTransaction(id);
		logger.info(insertedTransaction.toString());

		boolean testResult = compareToTestTransaction(insertedTransaction);
		assertTrue(testResult);
	}

}
