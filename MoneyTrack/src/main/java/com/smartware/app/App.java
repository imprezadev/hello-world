package com.smartware.app;

import com.smartware.domain.Transaction;
import com.smartware.service.TransactionService;

public class App {

	public static void main(String[] args) {
		TransactionService transactionService = new TransactionService();

		Transaction trans = transactionService.getTransaction();

		System.out.println("Transaction: " + trans);
	}

}