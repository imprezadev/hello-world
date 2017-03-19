package com.smartware.app;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.smartware.domain.Transaction;
import com.smartware.service.TransactionService;

public class App {

	public static void main(String[] args) {
		TransactionService transactionService = new TransactionService();

		System.out.println("Transaction: " + transactionService.getTransaction(3L));

		// just to show the date in a desired format
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = transactionService.getTransaction(3L).getDate();
		System.out.println("Transaction date: " + dateFormat.format(date));
		
		for (Transaction trans: transactionService.getTransactions()) {
			System.out.println("Transaction: " + trans);
		}

	}

}